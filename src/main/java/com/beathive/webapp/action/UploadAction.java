package com.beathive.webapp.action;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.beathive.webapp.form.UploadForm;
import com.beathive.webapp.util.RequestUtil;


/**
 * This class handles the uploading of a resume (or any file) and writing it to
 * the filesystem.  Eventually, it will also add support for persisting the
 * files information into the database.
 *
 * <p>
 * <a href="UploadAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * 
 * @struts.action name="uploadForm" path="/uploadFile" scope="request" validate="true" input="failure"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="failure" path="/WEB-INF/pages/uploadForm.jsp"
 * @struts.action-forward name="banner" path="/WEB-INF/pages/uploadDisplay.jsp"
 */
public class UploadAction extends Action {
	
	private static Log log = LogFactory.getLog(UploadAction.class);
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
      throws Exception {
        // Did the user click the cancel button?
        if (isCancelled(request)) {    
            request.removeAttribute(mapping.getAttribute());
            request.setAttribute("code","cancel");
            return mapping.findForward("banner");
        }

        //this line is here for when the input page is upload-utf8.jsp,
        //it sets the correct character encoding for the response
        String encoding = request.getCharacterEncoding();

        UploadForm theForm = (UploadForm) form;

        // get parameters that are not sent in form
        //'type' may be: 'banner',...
        String actionType = request.getParameter("type");
        
        //the user directory under which to store the file(s)
        String subdir = request.getRemoteUser().replaceAll("\\W+", "_");
        
        String userpath = "/images/producer_banners/" + subdir +"/";
        File destdir = new File(request.getSession().getServletContext().getRealPath(userpath));
        if (!destdir.exists()) {
        	destdir.mkdirs();
		}
        List myFiles =(List) theForm.getUploads();
        List outList = new LinkedList();
        for(int i=0;i<myFiles.size();i++){
	        if(myFiles.get(i)!=null){
	        	FormFile file =(FormFile)myFiles.get(i) ;
	        	/*process ur file......*/
		        //retrieve the file representation
		        if (file != null) {
			        outList.add("'"+userpath + processFile(file, destdir )+"'");
		        }
	        }
        }
        
        // place the data into the request for retrieval on next page
        request.setAttribute("paths", outList);
        request.setAttribute("code","success");

        //return a forward to display.jsp
        return mapping.findForward("banner");
    }

	private Object processFile(FormFile file, File dest) throws IOException {

		String basename = "banner";
		
    	String ext = (file.getContentType().toLowerCase().indexOf("gif") > -1) ? "gif" : "jpg";
    	//String filename = RequestUtil.makeUniqueFileName(dest, basename, ext);
    	String filename = basename+"."+ ext;
    	
    	
    	File f = RequestUtil.fwrite(file.getInputStream(), new File (dest , filename));

    	return filename;

	}
	
}
