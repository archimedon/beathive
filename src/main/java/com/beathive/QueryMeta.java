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
public interface QueryMeta {
	public int DEFAULT_FETCHSIZE = 10;//Constants.FETCH_SIZE;
	public int DEFAULT_STARTINDEX = 0;

	
	public Long getViewerId();
	
	public User getViewer();
	public void setViewer(User viewer);
	
	public int getFetchSize();
	public void setFetchSize(int fetchSize);
	
	public int getCurrIndex() ;
	public void setCurrIndex(int currIndex);

	public int getTotal();
	public void setTotal(int total);

	public int getOtherTotal();
	public void setOtherTotal(int total);

	public void setSortBy(String[] sortColumns);
	public String[] getSortBy();
	
	public List getResults();
	public void setResults(List results);
	
	public boolean getHasNext();
	
	public boolean getHasPrev();
	
	public int getNext();
	
	public int getPrev();
}
