/**
 * 
 */
package com.beathive.model;

import java.util.Date;

import com.beathive.PaginatedListImpl;

/**
 * @author ron ron@beathive.com
 * @version 1.9 Date: May 3, 2009
  */
public class UserReport extends PaginatedListImpl{
	
	private Date startDate;
	private Date endDate;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String middleName;
	private Boolean enabled;
	private String modified;
	private String roleName;

	public UserReport(){
		super();
//		super.setSortCriterion("created");
	}

    public String getFirstName()
    {
        return this.firstName;
    }
   /**
    * @struts.validator type="required"
    */

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }
   /**
    * @struts.validator type="required"
    */

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getUsername()
    {
        return this.username;
    }
   /**
    * @struts.validator type="required"
    */

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getEmail()
    {
        return this.email;
    }
   /**
    * @struts.validator type="required"
    * @struts.validator type="email"
    */

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getMiddleName()
    {
        return this.middleName;
    }
   /**
    */

    public void setMiddleName( String middleName )
    {
        this.middleName = middleName;
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }
   /**
    */

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    public String getModified()
    {
        return this.modified;
    }
   /**
    */

    public void setModified( String modified )
    {
        this.modified = modified;
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
	 * @return the roleId
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
