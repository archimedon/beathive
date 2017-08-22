
    public boolean equals(Object o ){
    	if (id!= null && o != null && o instanceof StoreForm){
    		StoreForm rhs = (StoreForm)o;
    		return this.id.equals(rhs.getId());
    	}
    	return false; 
    }
    public int hashCode() {
    	if (id==null){
    		new String("").hashCode();
    	}
        return id.hashCode();
    }
