
package com.beathive.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.beathive.webapp.action.BaseAction;
import com.beathive.Constants;
import com.beathive.model.Preference;
import com.beathive.model.User;
import com.beathive.service.PreferenceManager;
import com.beathive.service.UserManager;
import com.beathive.webapp.form.PreferenceForm;

/**
 * Action class to handle CRUD on a Preference object
 *
 * @struts.action name="preferenceForm" path="/preferences" scope="request"
 *  validate="false" parameter="method" input="mainMenu"
 * @struts.action name="preferenceForm" path="/editPreference" scope="request"
 *  validate="false" parameter="method" input="list"
 * @struts.action name="preferenceForm" path="/savePreference" scope="request"
 *  validate="true" parameter="method" input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit" path="/WEB-INF/pages/preferenceForm.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/preferenceList.jsp"
 * @struts.action-forward name="search" path="/preferences.html" redirect="true"
 */
public final class PreferenceAction extends BaseAction {
    public ActionForward cancel(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
        return mapping.findForward("success");
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'delete' method");
        }

        ActionMessages messages = new ActionMessages();
        PreferenceForm preferenceForm = (PreferenceForm) form;

        // Exceptions are caught by ActionExceptionHandler
        PreferenceManager mgr = (PreferenceManager) getBean("preferenceManager");
        mgr.removePreference(preferenceForm.getUserId());

        messages.add(ActionMessages.GLOBAL_MESSAGE,
                     new ActionMessage("preference.deleted"));

        // save messages in session, so they'll survive the redirect
        saveMessages(request.getSession(), messages);

        return mapping.findForward("userHome");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'edit' method");
        }

        PreferenceForm preferenceForm = (PreferenceForm) form;

        // if an id is passed in, look up the user - otherwise
        // don't do anything - user is doing an add
        if (preferenceForm.getUserId() != null) {
            PreferenceManager mgr = (PreferenceManager) getBean("preferenceManager");
            Preference preference = mgr.getPreference(preferenceForm.getUserId());
            preferenceForm = (PreferenceForm) convert(preference);
            updateFormBean(mapping, request, preferenceForm);
        }

        return mapping.findForward("edit");
    }
    public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'save' method");
		}

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		PreferenceForm preferenceForm = (PreferenceForm) form;
		boolean isNew = ("".equals(preferenceForm.getUserId()) || preferenceForm
				.getUserId() == null);

		UserManager mgr = (UserManager) getBean("userManager");
		Preference preference = (Preference) convert(preferenceForm);
		
		User user = mgr.getUser(preferenceForm.getUserId());
		if(user.getPreference()!=null){
			BeanUtils.copyProperties(user.getPreference() , preference);
		}else{
			user.setPreference(preference);
		}
		mgr.saveUser(user);
		// add success messages
		if (isNew) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"preference.added"));

			// save messages in session to survive a redirect
			saveMessages(request.getSession(), messages);

			return mapping.findForward("success");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"preference.updated"));
			saveMessages(request, messages);

			return mapping.findForward("success");
		}
	}

    public ActionForward savePreference(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'save' method");
        }

        // Extract attributes and parameters we will need
        ActionMessages messages = new ActionMessages();
        PreferenceForm preferenceForm = (PreferenceForm) form;
        boolean isNew = ("".equals(preferenceForm.getUserId()) || preferenceForm.getUserId() == null);

        PreferenceManager mgr = (PreferenceManager) getBean("preferenceManager");
        Preference preference = (Preference) convert(preferenceForm);
        mgr.savePreference(preference);

        // add success messages
        if (isNew) {
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("preference.added"));

            // save messages in session to survive a redirect
            saveMessages(request.getSession(), messages);

            return mapping.findForward("success");
        } else {
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("preference.updated"));
            saveMessages(request, messages);

            return mapping.findForward("success");
        }
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
            throws Exception {
        return edit(mapping, form, request, response);
    }
}
