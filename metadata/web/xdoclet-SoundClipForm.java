	public String[] getClipGenres() {
        com.beathive.model.Genre genreObject;
        String[] clipGenres = new String[this.genre.size()];
        int i = 0;
        for (java.util.Iterator iter = genre.iterator(); iter.hasNext();) {
        	genreObject = (com.beathive.model.Genre) iter.next();
            clipGenres[i] = genreObject.getId().toString();
            i++;
        }
        return clipGenres;
    }
	public void setClipGenres(String[] genreIds) {}

	
	
    public void setClipAudioFiles(String[] audioFiles) {}
    
	public String[] getAudioFiles() {
        com.beathive.model.AudioFile audioFormatObject;
        String[] audioFiles = new String[this.audioFormat.size()];
        int i = 0;
        for (java.util.Iterator iter = audioFormat.iterator(); iter.hasNext();) {
        	audioFormatObject = (com.beathive.model.AudioFile) iter.next();
        	audioFiles[i] = audioFormatObject.getId().toString();
            i++;
        }
        return audioFiles;
    }

	
	private String clean(String str){
		return str.replaceAll("'", "\\\\'");
	}
	private String quote(String str){
		return "'" + clean(str) + "'";
	}
	private String pair(String str1 , String str2){
		return quote(str1) + ":" + quote(str2);
	}
    
	public String getJSON() {
		String[] attribs = {"id","storeId","name","modified","created","statusId","searchable","keyword","numFormats","score",	"timesrated","bpm","keynote","scale",
		"timesignature","passage", "energy","feel","mood","origin","sonority",
				"texture","viewerScore","ownedByViewer","AFavorite" , "priceForm" ,"audioFormat"};
		Object o = null;
		StringBuffer buf = new StringBuffer("{");
		for (int y = 0; y < attribs.length; y++){
			String key= attribs[y];
			try {
				o = org.apache.commons.beanutils.PropertyUtils.getProperty(this , key);
				if (o!=null){
					if (key.equals("priceForm")){
						buf.append(pair(key ,  ((com.beathive.webapp.form.PriceForm)o).getAmount()));
					}else if (key.equals("audioFormat")){
						java.util.Set<AudioFileForm> frms = (java.util.Set)o;
						buf.append("swfs:{");
						int stop = frms.size() - 1;
						int b = 0;
						java.util.Iterator it = frms.iterator();
						while(it.hasNext()){
							AudioFileForm file = (AudioFileForm) it.next();
							buf.append(pair(file.getFormatId() , file.getSamplePath()));
							buf.append((stop > b++)?",":"");
							
						}
						buf.append("}");
					}else{
						buf.append(pair(key , o.toString()));
					}
				}
				else{
					buf.append(pair(key , ""));
				}
			} catch (Exception e) {
				continue;
			}
				buf.append(",");
		}
		// also stubs the comma above
		buf.append("'ready':"+(this.isReady()? "true":"false"));
		buf.append("}");
		return buf.toString();
	}
