	
	private String quote(String str){
		return "'" + str + "'";
	}
	private String pair(String str1 , String str2){
		return quote(str1) + ":" + quote(str2);
	}

	public String getJSON() {
		String[] attribs = {"userId","hideOwned","hideFav","locale","format"};
		Object o = null;
		StringBuffer buf = new StringBuffer("{");
		for (int y = 0; y < attribs.length; y++){
			String key= attribs[y];
			try {
				o = org.apache.commons.beanutils.PropertyUtils.getProperty(this , key);
				if (o!=null){
					buf.append(pair(key , o.toString()));
					if ((y+1)<attribs.length){
						buf.append(",");
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		buf.append("}");
		return buf.toString();
	}
