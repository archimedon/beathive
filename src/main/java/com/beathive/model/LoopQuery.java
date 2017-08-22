/**
 * 
 */
package com.beathive.model;

import com.beathive.Constants;
import com.beathive.QueryMeta;
import com.beathive.QueryMetaImpl;


/**
 * @author ron
 * @struts.form include-all="true" extends="LoopForm"
 */
public class LoopQuery extends Loop implements SoundClipQuery{
	private QueryMeta queryMeta = null;
	private Class queryClass = Loop.class;
	private boolean searchable = true;

	public void setSearchable(boolean flag){
		this.searchable = flag;
	}
	/**
	 * @param viewer
	 */
	public LoopQuery() {
		super();
	}

	/**
	 * @param viewer
	 */
	public LoopQuery(User viewer) {
		this();
		queryMeta.setViewer(viewer);
	}

	/**
	 * @return the queryMeta
	 */
	public QueryMeta getQueryMeta() {
		return queryMeta;
	}

	/**
	 * @param queryMeta the queryMeta to set
	 */
	public void setQueryMeta(QueryMeta queryMeta) {
		this.queryMeta = queryMeta;
	}
	public Class getQueryClass() {
		return super.getClass();
	}
	public void setQueryClass(Class queryClass) {
//		this.queryClass = queryClass;
	}
	

	
}
