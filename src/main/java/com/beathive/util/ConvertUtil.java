package com.beathive.util;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.beathive.model.BaseObject;
import com.beathive.model.LabelValue;
import org.springframework.aop.support.AopUtils;


/**
 * Utility class to convert one object to another.
 * 
 * <p>
 * <a href="ConvertUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Ronald Dennison
 */
public final class ConvertUtil {
    //~ Static fields/initializers =============================================

    private static Log log = LogFactory.getLog(ConvertUtil.class);

    //~ Methods ================================================================

    /**
     * Method to convert a ResourceBundle to a Map object.
     * @param rb a given resource bundle
     * @return Map a populated map
     */
    public static Map convertBundleToMap(ResourceBundle rb) {
        Map map = new HashMap();

        for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = (String) keys.nextElement();
            map.put(key, rb.getString(key));
        }

        return map;
    }
    
    public static Map convertListToMap(List list) {
        Map map = new LinkedHashMap();
        
        for (Iterator it = list.iterator(); it.hasNext();) {
            LabelValue option = (LabelValue) it.next();
            map.put(option.getLabel(), option.getValue());
        }
        
        return map;
    }

    /**
     * Method to convert a ResourceBundle to a Properties object.
     * @param rb a given resource bundle
     * @return Properties a populated properties object
     */
    public static Properties convertBundleToProperties(ResourceBundle rb) {
        Properties props = new Properties();

        for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = (String) keys.nextElement();
            props.put(key, rb.getString(key));
        }

        return props;
    }

    /**
     * Convenience method used by tests to populate an object from a
     * ResourceBundle
     * @param obj an initialized object
     * @param rb a resource bundle
     * @return a populated object
     */
    public static Object populateObject(Object obj, ResourceBundle rb) {
        try {
            Map map = convertBundleToMap(rb);

            BeanUtils.copyProperties(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception occured populating object: " + e.getMessage());
        }

        return obj;
    }

    /**
     * This method inspects a POJO or Form and figures out its pojo/form
     * equivalent.
     *
     * @param o the object to inspect
     * @return the Class of the persistable object
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Object getOpposingObject(Object o) throws ClassNotFoundException,
                                                InstantiationException,
                                                IllegalAccessException {
        String name = o.getClass().getName();

        if (o instanceof BaseObject) {
            if (log.isDebugEnabled()) {
                log.debug("getting form equivalent of pojo...");
            }

            name = StringUtils.replace(name, ".model.", ".webapp.form.");
          //  if (AopUtils.isCglibProxy(o))  {
          //      name = name.substring(0, name.indexOf("$$"));
          //  }
            int ptr = name.indexOf("$$");
            if (ptr >-1)  {
                name = name.substring(0, ptr);
            }
            name += "Form";
        } else {
            if (log.isDebugEnabled()) {
                log.debug("getting pojo equivalent of form...");
            }
            name = StringUtils.replace(name, ".webapp.form.", ".model.");
            name = name.substring(0, name.lastIndexOf("Form"));
        }
        // NOTE added strip non word from name (exclud '.' period) Mar 13 2009
		name = name.replaceAll("[^a-z A-Z 0-9 \\.]", "");
        Class obj = Class.forName(name);

        if (log.isDebugEnabled()) {
            log.debug("returning className: " + obj.getName());
        }

        return obj.newInstance();
    }

    /**
     * Convenience method to convert a form to a POJO and back again
     *
     * @param o the object to tranfer properties from
     * @return converted object
     */
    public static Object convert(Object o) throws Exception {
        if (o == null) {
        	return null;
        }
        Object target = getOpposingObject(o);
        BeanUtils.copyProperties(target, o);
        return target;
    }

    
	public static Object convertNamedList(Object o, String property, Class clazz)
			throws Exception {
		if (o == null) {
			// System.out.println("Object NULL");
			return null;
		}
		if (clazz != null) {
			Collection list = (Collection) PropertyUtils.getProperty(o,
					property);
			if (list != null) {
				PropertyUtils
						.setProperty(o, property, convertList(list, clazz));
			}
		}
		return o;
	}

    /**
     * Convenience method to convert Lists (in a Form) from POJOs to Forms.
     * Also checks for and formats dates.
     *
     * @param o
     * @return Object with converted lists
     * @throws Exception
     */
    public static Object convertLists(Object o) throws Exception {
        if (o == null) {
            return null;
        }

        Object target = null;

        PropertyDescriptor[] origDescriptors =
                PropertyUtils.getPropertyDescriptors(o);

        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if (PropertyUtils.isWriteable(o, name)){
            if (origDescriptors[i].getPropertyType().equals(List.class)) {
                List list = (List) PropertyUtils.getProperty(o, name);
                for (int j=0; j < list.size(); j++) {
                    Object origin = list.get(j);
                    target = convert(origin);
                    list.set(j, target);
                }
                PropertyUtils.setProperty(o, name, list);
            }
            else if(origDescriptors[i].getPropertyType().equals(Set.class)){
            	Set list = (Set) PropertyUtils.getProperty(o, name);
            	Iterator it = list.iterator();
            	Set tmpList = ((Set)list).getClass().newInstance();
				while(it.hasNext()){
					tmpList.add(convert(it.next()));
				}
				PropertyUtils.setProperty(o, name, tmpList);
            }
            }
        }
        return o;
    }
    
    /**
     * Convenience method to convert a form to a POJO and back again
     *
     * @param o the object to tranfer properties from
     * @return converted object
     */
    public static Object convert(Object o, Class clazz) throws Exception {
        if (o == null) {
        	return null;
        }
        Object target = clazz.newInstance();
        BeanUtils.copyProperties(target, o);
        return target;
    }

	public static Map convertMap(Map map) throws Exception {
		if (map == null) {
			//			//System.out.println("List or Class is NULL");
			return null;
		}
		
		Iterator it = map.keySet().iterator();
		Object target = null;
		while(it.hasNext()){
			Object key = it.next();
			Object val = map.get(key);
			map.put(key, convert(val));
		}
		return map;
	}

    
	/**
	 * Convenience method to convert items in a List to the Class provided.
	 * Converts Items in their place
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static Collection convertList(Collection list, Class clazz) throws Exception {
		if ((list == null) || (clazz == null)) {
			//			//System.out.println("List or Class is NULL");
			return null;
		}
		Collection tmpList = null;
		if (list instanceof List){
			tmpList = new LinkedList();
		}
		// must be a Set
		else if (list instanceof Set){
			tmpList = new LinkedHashSet();
		}
		Iterator it = list.iterator();
		Object target = null;
		while(it.hasNext()){
			target = clazz.newInstance();
			BeanUtils.copyProperties(target, it.next());
			tmpList.add(target);
		}
		return tmpList;
	}

}
