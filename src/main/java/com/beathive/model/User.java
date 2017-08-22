package com.beathive.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.beathive.model.Address;


/**
 * User class
 *
 * <p>
 * <a href="User.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:ron@beathive.com">Ronald Dennison</a>
 * @version $Revision: 1.4 $ $Date: 2004/05/16 02:16:44 $
 *
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="app_user"
 * hibernate.query name="isUnique" query="select count(*) from User where username=?"
 * @hibernate.query name="loadUserByUsername" query="from User usr where usr.username=:username"
 * @hibernate.query name="loadUserByEmail" query="from User usr where usr.email=:email"
 */
public class User extends BaseObject implements Serializable , UserDetails {
	private static final long serialVersionUID = -4381002429954534428L;
	//~ Instance fields ========================================================

	// RBD - added June 13th 2008
    protected Long id;
    protected Integer version;
    protected String username;                    // required
    protected String password;                    // required
    protected String confirmPassword;
    protected String passwordHint;
    protected String firstName;                   // required
    protected String middleName;
    protected String lastName;                    // required
    protected String email;                       // required; unique
    protected boolean alert;
    protected Set roles = new HashSet();
    // member since ...
	private Date created;
	private Date modified;
	
    protected boolean enabled;
    protected boolean accountExpired;
    protected boolean accountLocked;
    protected boolean credentialsExpired;

    // cart can't be tied to user so non-logged in user can add to cart
    //protected Cart cart; // moved to session
    

	protected Set clipScore;
// ~ joins ==============================
	
	protected Preference preference;			// a joined table to keep user preference extendable
//	protected Store store;						// possibly null unless a producer
	protected Set stores = new LinkedHashSet();	// possibly empty unless a producer

	protected Address address;// = new Address();
	// the User's debit card - the means by which they pay us
//	private CreditCard billingMethod;
	// a produce's store rating is with the user not the store. A user's records follows person not the store.
//	private UserRank rank;
	protected Set purchases;
	private Set archives;

	private Date lastlogin;

	private List favorites = new LinkedList();
	
	private List cartItems = new LinkedList();


	//~ Contructors ================================================================
	/**
	 * No Arg constructor @see User(Long uid) and User(String uname)
	 */
	public User(){
		super();
		this.created =  new Date();
	}
	/**
	 * New User with userId
	 * @param userId
	 */
	public User(Long userId){
		this();
		this.id= userId;
	}
	public User(String uname){
		this();
		this.username= uname;
	}
    //~ Methods ================================================================
	/**
	 * @hibernate.property column="account_created" update="false" insert="true" type="date" default="now()"
	 * @return Returns the created.
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created The created to set.
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
   
    /**
     * Returns the firstName.
     * @struts.validator type="required"
     * @hibernate.property not-null="true"
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @hibernate.id generator-class="native" unsaved-value="null"
     */
    public Long getId() {
        return id;
    }

	/**
     * Returns the lastName.
     * @struts.validator type="required"
     * @hibernate.property not-null="true"
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

	/**
     * Returns the passwordHint.
     * @struts.validator type="required" page="1"
     * @hibernate.property not-null="false"
     * @return String
     */
	public String getPasswordHint() {
		return passwordHint;
	}
	

    /**
     * Sets the firstName.
     * @param firstName The firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
	public void setId(Long id) {
	    this.id = id;
	}

    /**
     * Sets the lastName.
     * @param lastName The lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @param passwordHint The password hint to set
     */
    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    /**
     * @struts.validator type="required"
     * @hibernate.property length="50" not-null="true" unique="true"
     */
    public String getUsername() {
        return username;
    }


    public void addRole(Role role) {
	    	if (role!=null){
	        getRoles().add(role);
	    	}
    }

    /**
     * @param enabled The enabled to set.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    

    /**
     * Returns the email.
     *
     * @struts.validator type="required"
     * @struts.validator type="email"
     * @hibernate.property column="email" not-null="true" unique="true"
     * @return String email addr
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the email.
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password.
     * @return String
     *
     * @struts.validator type="required"
     * @struts.validator type="twofields" msgkey="errors.twofields"
     * @struts.validator-args arg1resource="userForm.password"
     * @struts.validator-args arg1resource="userForm.confirmPassword"
     * @struts.validator-var name="secondProperty" value="confirmPassword"
     * @hibernate.property column="password" not-null="true"
     */
    public String getPassword() {
        return password;
    }

	/**
	 * Returns the confirmedPassword.
	 * @struts.validator type="required"
	 * 
	 * @return String
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	
    /**
     * Returns the user's roles.
     * @return Set
     * @hibernate.set table="user_role" cascade="save-update" lazy="false"
     * @hibernate.collection-key column="user_id"
     * @hibernate.collection-many-to-many class="com.beathive.model.Role"
     *                                    column="role_id"
     */
    public Set getRoles() {
	    	if (roles == null){
	    		roles = new HashSet();
	    	}
        return roles;
    }

    @Override
	public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }

        User rhs = (User) object;

        return new EqualsBuilder()
        .append(this.password, rhs.password)
        .append(this.passwordHint, rhs.passwordHint)
//                .append(this.address, rhs.address)
                .append(this.username, rhs.username)
                .append(this.email,rhs.email)
                .append(this.roles, rhs.roles)
                .append(this.enabled, rhs.enabled)
                .append(this.firstName, rhs.firstName)
                .append(this.lastName, rhs.lastName)
                .append(this.middleName, rhs.middleName)
                .isEquals();
    }

    /**
     * Generated using Commonclipse (http://commonclipse.sf.net)
     */
    @Override
	public int hashCode() {
        return new HashCodeBuilder(-2022315247, 1437659757)
        .append(this.password)
        .append(this.passwordHint)
//        .append(this.address)
        .append(this.username)
        .append(this.email)
        .append(this.roles)
        .append(this.firstName)
        .append(this.middleName)
        .append(this.enabled)
        .append(this.lastName).toHashCode();
    }

    /**
     * Sets the password.
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

	/**
	 * Sets the confirmedPassword.
	 * @param confirmPassword The confirmed password to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
    /**
     * Sets the username.
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the roles.
     * @param roles The roles to set
     */
    public void setRoles(Set roles) {
        this.roles = roles;
    }

    /**
     * @return Returns the updated version.
     * @hibernate.version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * The updated version to set.
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

	/**
	 * @return Returns the alert.
     * @hibernate.property column="alert" type="yes_no"
	 */
	public boolean isAlert() {
		return alert;
	}

	/**
	 * @param alert The alert to set.
	 */
	public void setAlert(boolean alert) {
		this.alert = alert;
	}

    /**
     * Sets the ratings.
     * @param roles The roles to set
     */
    public void setClipScore(Set ratings) {
        this.clipScore = ratings;
    }

    /**
     * Needed primarily for creating 'user_cliprating' SoundClip will search this table to calculate avg and timesrated
     * Returns the user's ratings.
     * @return Set
     * hibernate.set name="clipScore" lazy="true" cascade="all"
     * hibernate.key column="userId"
     * hibernate.collection-one-to-many class="com.beathive.model.UserClipScore"
     */
    public Set getClipScore() {
	    	if (clipScore == null){
	    		clipScore = new HashSet();
	    	}
	    	return clipScore;
    }
    /**
     * Adds a role for the user
     * @param rolename
     */
/* TODO considering replacing with a Custom UserCLipView Object that 
 * includes a join table to populate the result with the rating.
 * ex.  'join user_rank on username=? and clipid={this.id}'
 */
   public void addClipScore( Long clipid , int score ) {
	   UserClipScore rating = new UserClipScore();
        rating.setClipId(clipid);
        rating.setUserId(id);
        rating.setScore(new Integer(score));
        getClipScore().add(rating);
    }

    /**
	 * @param store The store to set.
	 */
	public void setStores(Set newstores) {
		this.stores = newstores;
	}

	/**
	 * @param address The address to set.
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @hibernate.property
	 * @return Returns the middleName.
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName The middleName to set.
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
    /**
     *  property-ref="userId"
     * Returns the address.
     * @hibernate.one-to-one cascade="all"
     * 
     * ?hibernate.component
     * 
     * @return Address
     */
    public Address getAddress() {
        return address;
    }

	/**
	 * @param userPreference The userPreference to set.
	 */
	public void setPreference(Preference preference) {
		this.preference = preference;
	}

	/**
	 * @return Returns the preference.
     * @hibernate.one-to-one cascade="all"
	 */
	public Preference getPreference() {
		return preference;
	}
	/** constrained="true"
	 * @hibernate.set table="user_store" cascade="delete" lazy="true" order-by="storeId"
     * @hibernate.collection-key column="userId"
	 * @hibernate.many-to-many  class="com.beathive.model.Store" unique="true" column="storeId"
	 * @return Returns the store.
	 */
	public Set getStores() {
		return stores;
	}
	public void addStore(Store store) {
		if (stores==null){
			stores= new LinkedHashSet();
		}
		stores.add(store);
	}
	/**
	 * Helper
	 * @return Returns the FullName.
	 */

	public String getFullName(){
		return firstName + " " + lastName;
	}
	/**
     * @hibernate.set table="user_order" cascade="all" lazy="true" order-by="purchaseId"
     * @hibernate.collection-key column="userId"
     * @hibernate.collection-many-to-many class="com.beathive.model.Purchase" unique="true"
     *                                    column="purchaseId"
	 * @return Returns the orders.
	 */
	public Set getPurchases() {
		return purchases;
	}
	/**
	 * @param orders The orders to set.
	 */
	public void setPurchases(Set purchases) {
		this.purchases = purchases;
	}
	/**
	 * (hib_docs/.../7.3.1/associations) "Notice that by specifying unique="true", we have changed the multiplicity from many-to-many to one-to-many."
     * @hibernate.set table="user_zip" cascade="all" lazy="true" order-by="zipId"
     * @hibernate.collection-key column="userId"
     * @hibernate.collection-many-to-many class="com.beathive.model.Zip" unique="true" column="zipId"
	 * @return Returns the orders.
	 */
	public Set getArchives() {
		return archives;
	}
	/**
	 * @param orders The orders to set.
	 */
	public void setArchives(Set archives) {
		this.archives = archives;
	}
	public void addArchive(Zip zip) {
		if (archives==null){
			archives= new LinkedHashSet();
		}
		archives.add(zip);
	}

	public void addPurchase(Purchase purchase) {
		if (purchases==null){
			purchases= new LinkedHashSet();
		}
		purchases.add(purchase);
	}
	
    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }
    
    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }
    
    /**
     * @hibernate.property column="account_expired" not-null="true" type="yes_no"
     */
    public boolean isAccountExpired() {
        return accountExpired;
    }
    
    /**
     * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired()
     */
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    /**
     * @hibernate.property column="account_locked" not-null="true" type="yes_no"
     */
    public boolean isAccountLocked() {
        return accountLocked;
    }
    
    /**
     * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
     */
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    /**
     * @hibernate.property column="credentials_expired" not-null="true"  type="yes_no"
     */
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }
    
    /**
     * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired()
     */
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }
    
    /**
     * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
     */
    public GrantedAuthority[] getAuthorities() {
    	GrantedAuthority[] authz = null;
    	if (roles!=null){
    		int i = 0;
    		Role tempRole = null;
    		authz = new GrantedAuthority[roles.size()];
    		for (Iterator it = roles.iterator(); it.hasNext(); ){
    			tempRole = (Role)it.next();
    			if (tempRole!=null){
    			authz[i++] = new GrantedAuthorityImpl(tempRole.getName());
    			}
    		}
    	}
        return authz;
    }
    
    /**
     * @hibernate.property column="account_enabled" type="yes_no"
     */
    public boolean isEnabled() {
        return enabled;
    }

    
    /**
     * @hibernate.property column="account_modified" type="timestamp"
     */
public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}


    @Override
	public String toString() {
    	return this.getFullName();
    }
	/**
	 * @param date
	 */
	public void setLastLogin(Date date) {
		this.lastlogin = date;
	}

    /**
     * @hibernate.property type="timestamp"
     */
	public Date getLastLogin() {
		return this.lastlogin;
	}
	
	/**
	 * @hibernate.bag name="favorites" inverse="false" lazy="false" cascade="all"
	 * @hibernate.key column="userId"
	 * @hibernate.one-to-many class="com.beathive.model.Favorite"
	 */
	public List<Favorite> getFavorites() {
		return favorites;
	}
	public void setFavorites(List favorites) {
		this.favorites = favorites;
	}
	

	/**
	 * @hibernate.bag name="cartItems" lazy="false" inverse="true" cascade="delete"
	 * @hibernate.key column="userId"
	 * @hibernate.one-to-many class="com.beathive.model.CartItem"
	 */
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List cartItems) {
		this.cartItems = cartItems;
	}
	

	/**
     * hibernate.one-to-one cascade="all"
     * @return Cart
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	 */

}
