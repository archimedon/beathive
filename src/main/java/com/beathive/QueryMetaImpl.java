/**
 * 
 */
package com.beathive;

import java.util.List;

import com.beathive.model.User;

/**
 * @author ron
 *
 */
public class QueryMetaImpl implements QueryMeta {
	private User viewer = new User(new Long(-1));
	private int fetchSize = QueryMeta.DEFAULT_FETCHSIZE;
	private int currIndex = 0;//QueryMeta.DEFAULT_STARTINDEX;
	private int total = 0;
	private int otherTotal = 0;
	
	private String[] sortBy = {"score","timesrated"};
	private List results;
	
	public QueryMetaImpl(){
		super();
	}
	
	/**
	 * Creates a QueryMetaImpl
	 * @param viewer if null then user is a non-existent user. setViewer(User) to make the viewer actually null
	 */
	public QueryMetaImpl(User viewer){
		this();
		if (viewer != null){
			setViewer(viewer);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#getCountLoop()
	 */
	public int getTotal() {
		// TODO Auto-generated method stub
		return this.total;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#getFetchSize()
	 */
	public int getFetchSize() {
		// TODO Auto-generated method stub
		return this.fetchSize;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#getCurrIndex()
	 */
	public int getCurrIndex() {
		// TODO Auto-generated method stub
		return this.currIndex;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#getViewer()
	 */
	public User getViewer() {
		// TODO Auto-generated method stub
		return this.viewer;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#setCountLoop(int)
	 */
	public void setTotal(int total) {
		this.total = total;
		
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#setFetchSize(int)
	 */
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#setCurrIndex(int)
	 */
	public void setCurrIndex(int currIndex) {
		this.currIndex = currIndex;
	}

	/* (non-Javadoc)
	 * @see com.beathive.model.QueryMeta#setViewer(com.beathive.model.User)
	 */
	public void setViewer(User viewer) {
		this.viewer = viewer;
	}

	public String[] getSortBy() {
		return sortBy;
	}

	public void setSortBy(String[] sortBy) {
		this.sortBy = sortBy;
	}
	
	/**
	 * @return the results
	 */
	public List getResults() {
		return results;
	}
	
	/**
	 * @param results the results to set
	 */
	public void setResults(List results) {
		this.results = results;
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.QueryMeta#getViewerId()
	 */
	public Long getViewerId() {
        if (viewer==null){
        	viewer = new User(new Long(-1));
        }
		return viewer.getId();
	}
	
	/**
	 * @return the otherTotal
	 */
	public int getOtherTotal() {
		return otherTotal;
	}
	
	/**
	 * @param otherTotal the otherTotal to set
	 */
	public void setOtherTotal(int otherTotal) {
		this.otherTotal = otherTotal;
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.QueryMeta#getNext()
	 */
	public int getNext() {
		return currIndex++;
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.QueryMeta#getPrev()
	 */
	public int getPrev() {
		return currIndex--;
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.QueryMeta#hasNext()
	 */
	public boolean getHasNext() {
		return total > fetchSize * currIndex;
	}
	
	/* (non-Javadoc)
	 * @see com.beathive.QueryMeta#hasPrev()
	 */
	public boolean getHasPrev() {
		return currIndex > 0;
	}

}
