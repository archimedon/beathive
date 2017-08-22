    public boolean equals(Object o ){
    	if (id!= null && o != null && o instanceof InstrumentgroupForm){
    		InstrumentgroupForm rhs = (InstrumentgroupForm)o;
    		return this.id.equals(rhs.getId());
    	}
    	return false; 
    }
    public int hashCode() {
    	if (id==null){
    		new Integer(0).hashCode();
    	}
        return id.hashCode();
    }
    
