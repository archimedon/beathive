package com.beathive.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * This class represents a gzipped file containing a customer's download.
 * A collection of loops and trackpacks
 *
 * <p><a href="Zip.java.do"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:ron@webaxe.com">Ronald Dennison</a>
 * @version $Revision: 1.3 $ $Date: 2004/05/16 02:16:44 $
 *
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="zip"
 */
public class Zip extends BaseObject {
    //~ Instance fields ========================================================

    private Long id;
    // just to keep sync with orders
    private Long purchaseId;
    // to allow users to name their collections 
    private String name;
    private String description;
    private String path;
    private Date creationTime = new Date();
    private Date accessTime;
    private String accessKey;
    private Long fileSize;
    private String customerName;
    private Long customerId;
    private Integer version;
    private Integer accessCount = new Integer(0);
    private Set<ZipItem> items = new HashSet<ZipItem>();
 
	private String downloadURL;
	/**
	 * @hibernate.property
	 * @return the downloadURL
	 */
	public String getDownloadURL() {
		return downloadURL;
	}

	/**
	 * @param downloadURL the downloadURL to set
	 */
	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}


    

    //~ Methods ================================================================

	/**
	 * Return human readable file size
	 */
	public String getFileSizeH(){
		return org.apache.commons.io.FileUtils.byteCountToDisplaySize(this.fileSize);
	}

    /**
     * Returns the name.
     * @hibernate.id generator-class="native" unsaved-value="null"
     * @return String
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Returns the description.
     * @return String
     * @hibernate.property
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Returns the version.
     * @hibernate.version
     */
    public Integer getVersion() {
        return version;
    }
    /**
     * @param version The version to set.
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

	/**
     * @hibernate.property
	 * @return Returns the path.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
     * @hibernate.property
	 * @return Returns the accessCount.
	 */
	public Integer getAccessCount() {
		return accessCount;
	}

	/**
	 * @param accessCount The accessCount to set.
	 */
	public void setAccessCount(Integer accessCount) {
		this.accessCount = accessCount;
	}

	/**
     * @hibernate.property update="false"
	 * @return Returns the accessTime.
	 */
	public Date getAccessTime() {
		return accessTime;
	}

	/**
	 * @param accessTime The accessTime to set.
	 */
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	/**
     * @hibernate.property update="false"
	 * @return Returns the creationTime.
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime The creationTime to set.
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
     * @hibernate.property
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * @hibernate.property
	 * @return Returns the orderId.
	 */
	public Long getPurchaseId() {
		return purchaseId;
	}

	/**
	 * @param orderId The orderId to set.
	 */
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zip)) return false;

        final Zip archive = (Zip) o;

        if (path != null ? !path.equals(archive.path) : archive.path != null) return false;

        return true;
    }

    public int hashCode() {
        return (path != null ? path.hashCode() : 0);
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("path", this.path).append("description",
                        this.description).toString();
    }

	/**
     * @hibernate.set cascade="save-update" lazy="true" where="zipId is not null"
     * @hibernate.collection-key column="zipId"
     * @hibernate.collection-one-to-many class="com.beathive.model.ZipItem"
	 * @return Returns the items.
	 */
	public Set getItems() {
		if (items == null){
			items = new HashSet();
		}
		return items;
	}

	/**
	 * @param items The items to set.
	 */
	public void setItems(Set items) {
		this.items = items;
	}

	/**
     * @hibernate.property
	 * @return Returns the key.
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * @param key the key to set
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
     * @hibernate.property
	 * @return Returns the fileSize.
	 */
	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @hibernate.property
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @hibernate.property
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}
