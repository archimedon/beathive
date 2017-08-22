    public boolean equals(Object o ){
    	if (o != null && o instanceof CartItemForm){
    		return this.clipId.equals(((CartItemForm)o).getClipId());
    	}
    	return false; 
    }

    public int hashCode() {
        return new org.apache.commons.lang.builder.HashCodeBuilder()
        .append(this.clipId).toHashCode();
    }
    
    public String toString() {
    	return this.clipId +"_" + this.fileId;
    }
    public String type;
    
    public String getType() {
    	return this.type;
    }
    public void setType(String clazz) {
    	this.type = clazz;
    }
    
    