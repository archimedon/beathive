package com.beathive.webapp.util;

import java.util.LinkedList;
import java.util.List;

public class MenuHash extends HashColumn {

	private static final long serialVersionUID = -5931056865174565329L;

	public MenuHash(List colNames){
		super();
		for(int h = 0; h < colNames.size(); h++){
	    	List inner = new LinkedList();
			super.put(colNames.get(h),inner );
		}
	}
	
	public MenuHash(String[] colNames){
		super();
		for(int h = 0; h < colNames.length; h++){
			List inner = new LinkedList();
			super.put(colNames[h], inner);
		}
	}
}
