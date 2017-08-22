/**
 * 
 */
package com.beathive.model;

import java.util.Date;
import java.util.List;

import com.beathive.PaginatedListImpl;

/**
 * @author ron ron@beathive.com
 * @version 1.9 Date: May 3, 2009
 * @struts.form include-all="true" extends="BaseForm"
 */
public class ShopReport extends PaginatedListImpl{
	
	private Long storeId;
	private Date startDate;
	private Date endDate;
	private Integer loopVolume;
	private Integer trackpackVolume;
	private Double totalLoopYeild;
	private Double totalTrackpackYeild;
	private Boolean groupByClip = Boolean.FALSE;
	
	
	/**
	 * @return the storeId
	 */
	public Long getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the loopVolume
	 */
	public Integer getLoopVolume() {
		return loopVolume;
	}

	/**
	 * @param loopVolume the loopVolume to set
	 */
	public void setLoopVolume(Integer loopVolume) {
		this.loopVolume = loopVolume;
	}

	/**
	 * @return the trackpackVolume
	 */
	public Integer getTrackpackVolume() {
		return trackpackVolume;
	}

	/**
	 * @param trackpackVolume the trackpackVolume to set
	 */
	public void setTrackpackVolume(Integer trackpackVolume) {
		this.trackpackVolume = trackpackVolume;
	}

	/**
	 * @return the totalLoopYeild
	 */
	public Double getTotalLoopYeild() {
		if (totalLoopYeild==null){return new Double(0);}
//		return new Double (totalLoopYeild / 2);
		return totalLoopYeild;
	}

	/**
	 * @param totalLoopYeild the totalLoopYeild to set
	 */
	public void setTotalLoopYeild(Double totalLoopYeild) {
		this.totalLoopYeild = totalLoopYeild;
	}

	/**
	 * @return the totalTrackpackYeild
	 */
	public Double getTotalTrackpackYeild() {
		if (totalTrackpackYeild==null){return new Double(0);}
//		return new Double (totalTrackpackYeild / 2);
		return totalTrackpackYeild;
	}

	/**
	 * @param totalTrackpackYeild the totalTrackpackYeild to set
	 */
	public void setTotalTrackpackYeild(Double totalTrackpackYeild) {
		this.totalTrackpackYeild = totalTrackpackYeild;
	}

	/**
	 * @return the groupByClip
	 */
	public Boolean getGroupByClip() {
		return groupByClip;
	}

	/**
	 * @param groupByClip the groupByClip to set
	 */
	public void setGroupByClip(Boolean groupByClip) {
		this.groupByClip = groupByClip;
	}

}
