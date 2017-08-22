package com.beathive.webapp.form;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

/**
 * @author ron
 * 
 * @struts.form name="sampleForm"
 */
public class UploadForm extends BaseForm implements java.io.Serializable{
	public static final String ERROR_PROPERTY_MAX_LENGTH_EXCEEDED = "MaxLengthExceeded";

	protected static final Log log = LogFactory.getLog(UploadForm.class);
	private static final int BUFFER = 2048;
	
	private List uploads = new ArrayList();
	public List getUploads() { return this.uploads; }
    
    public void setUploads(int iIndex, FormFile formFile){
    	if (formFile!= null && formFile.getFileSize() >0){
	    	int size = uploads.size();
	    	if (iIndex == 0 && size == 0){this.uploads.add(formFile);}
	    	else if (iIndex < size){this.uploads.set(iIndex , formFile);}
	    	else {
		    	for(; size <= iIndex; size++){
	    			this.uploads.add(null);	
		    	}
	    		this.uploads.set(iIndex , formFile);
	    	}
    	}
    }

    /**
     * Retrieve a representation of the file the user has uploaded
     *
     * @return FormFile the uploaded file
     */
    public FormFile getFile(int index) {
        return (FormFile)uploads.get(index);
    }


	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
    	this.uploads = new ArrayList();
	}

    /**
     * Check to make sure the client hasn't exceeded the maximum allowed upload size inside of this
     * validate method.
     */
     public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
         
         ActionErrors errors = super.validate(mapping, request);
         
         // has the maximum length been exceeded?
         Boolean maxLengthExceeded =
             (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
         if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())) {
             if (errors == null) {
                 errors = new ActionErrors();
             }
             errors.add(ERROR_PROPERTY_MAX_LENGTH_EXCEEDED, new ActionMessage("maxLengthExceeded"));
         }
         
         return errors;
     }
	
	private File[] unzip(InputStream inFile, File outFolder) {
		List fileHandles = new LinkedList();

		try {
			BufferedOutputStream out = null;
			ZipInputStream in = new ZipInputStream(new BufferedInputStream(inFile));
			ZipEntry entry;
			while ((entry = in.getNextEntry()) != null) {
				String name = entry.getName();
          	  if(! 	(
        			  name.indexOf(".")==0
        			  || name.indexOf("/.") >-1
        			  || name.indexOf("__MACOSX")== 0
        			  || name.indexOf("/__MACOSX")> -1
        			  || name.indexOf(">") >-1
        			  || name.indexOf("|") >-1
        			 )
        	  ){
					int count;
					byte data[] = new byte[BUFFER];
					String apath = outFolder.getPath() + "/" + name;
					File tmp = new File(apath);
					if (entry.isDirectory()) {
						out = null;

						if (!tmp.exists()) {
							tmp.mkdirs();
						}
					} else {
						// write the files to the disk
						out = new BufferedOutputStream(new FileOutputStream(tmp), BUFFER);
						while ((count = in.read(data, 0, BUFFER)) != -1) {
							out.write(data, 0, count);
						}
						cleanUp(out);
					}
					fileHandles.add(tmp);
				}
			}
			cleanUp(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		File[] ret = new File[fileHandles.size()];
		fileHandles.toArray(ret);
		return ret;
	}
    
    private void cleanUp(InputStream in) throws Exception
    {
         in.close();
    }
    
    private void cleanUp(OutputStream out) throws Exception
    {
         out.flush();
         out.close();
    }
}
