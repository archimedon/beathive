package com.beathive.webapp.util;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author rdennison
 */
public class HashColumn  extends LinkedHashMap implements Map{
	private static final long serialVersionUID = -6470323874034416750L;
	private static Log log = LogFactory.getLog(HashColumn.class);

	public HashColumn(){
		super();
	}
	
	public HashColumn(List colNames){
		super();
		for(int h = 0; h < colNames.size(); h++){
			super.put(colNames.get(h), new LinkedList());
		}
	}
	
	public HashColumn(String[] colNames){
		super();
		for(int h = 0; h < colNames.length; h++){
			super.put(colNames[h], new LinkedList());
		}
	}
	
	public List getList(String o){
		return (List)get(o);
	}
	
	public Object getCell(String colname ,String propname , Object propvalue) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if(! containsKey(colname)){return null;}
		List tmp = (List)get(colname);
		Object keyval = null;
		for (int g = 0 ; g < tmp.size(); g++){
			keyval = BeanUtils.getProperty(tmp.get(g), propname);
			if (keyval.equals(propvalue)){
				return tmp.get(g);
			}
		}
		return null;
	}
	
	/**
	 * Creates a hosh of Lists
	 * @throws Exception
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public void add(String arg0, Object arg1) throws Exception {
		if(! super.containsKey(arg0)){
			throw new Exception("not a valid column");
		}
		((List)get(arg0)).add(arg1);
	}
}
