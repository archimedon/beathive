
package com.beathive.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.beathive.webapp.action.BaseAction;
import com.beathive.Constants;
import com.beathive.model.Zip;
import com.beathive.service.ZipManager;
import com.beathive.webapp.form.ZipForm;

/**
 * Action class to handle CRUD on a Zip object
 *
 * @struts.action name="zipForm" path="/user/zips" scope="request"
 *  validate="false" parameter="method" input="mainMenu"
 * @struts.action name="zipForm" path="/editZip" scope="request"
 *  validate="false" parameter="method" input="list"
 * @struts.action name="zipForm" path="/saveZip" scope="request"
 *  validate="true" parameter="method" input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit" path="/WEB-INF/pages/zipForm.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/zipList.jsp"
 * @struts.action-forward name="search" path="/user/zips.html" redirect="true"
 */
public final class ZipAction extends BaseAction {
    public ActionForward cancel(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
        return mapping.findForward("search");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'edit' method");
        }

        ZipForm zipForm = (ZipForm) form;

        // if an id is passed in, look up the user - otherwise
        // don't do anything - user is doing an add
        if (zipForm.getId() != null) {
            ZipManager mgr = (ZipManager) getBean("zipManager");
            Zip zip = mgr.getZip(zipForm.getId());
            zipForm = (ZipForm) convert(zip);
            updateFormBean(mapping, request, zipForm);
        }

        return mapping.findForward("edit");
    }

    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'save' method");
        }

        // Extract attributes and parameters we will need
        ActionMessages messages = new ActionMessages();
        ZipForm zipForm = (ZipForm) form;
        boolean isNew = ("".equals(zipForm.getId()) || zipForm.getId() == null);

        ZipManager mgr = (ZipManager) getBean("zipManager");
        Zip zip = (Zip) convert(zipForm);
        mgr.saveZip(zip);

        // add success messages
        if (isNew) {
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("zip.added"));

            // save messages in session to survive a redirect
            saveMessages(request.getSession(), messages);

            return mapping.findForward("search");
        } else {
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("zip.updated"));
            saveMessages(request, messages);

            return mapping.findForward("edit");
        }
    }

    public ActionForward search(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'search' method");
        }

        ZipForm zipForm = (ZipForm) form;
        Zip zip = (Zip) convert(zipForm);

        ZipManager mgr = (ZipManager) getBean("zipManager");
        request.setAttribute(Constants.ZIP_LIST, mgr.getUserZips(request.getRemoteUser()));

        return mapping.findForward("list");
    }
    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
            throws Exception {
        return search(mapping, form, request, response);
    }
}
