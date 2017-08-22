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
import com.beathive.model.Promo;
import com.beathive.service.Manager;
import com.beathive.service.PromoManager;
import com.beathive.webapp.form.PromoForm;

/**
 * Action class to handle CRUD on a Promo object
 *
 * @struts.action name="promoForm" path="/admin/promos" scope="request"
 *  validate="false" parameter="method" input="mainMenu"
 * @struts.action name="promoForm" path="/admin/editPromo" scope="request"
 *  validate="false" parameter="method" input="list"
 * @struts.action name="promoForm" path="/admin/savePromo" scope="request"
 *  validate="true" parameter="method" input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit" path="/WEB-INF/pages/admin/promoForm.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/admin/promoList.jsp"
 * @struts.action-forward name="search" path="/admin/promos.html" redirect="true"
 */
public final class PromoAction extends BaseAction {
    public ActionForward cancel(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
        return mapping.findForward("search");
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'delete' method");
        }

        ActionMessages messages = new ActionMessages();
        PromoForm promoForm = (PromoForm) form;

        // Exceptions are caught by ActionExceptionHandler
        Manager mgr = (Manager) getBean("manager");
        Promo promo = (Promo) convert(promoForm);
        mgr.removeObject(Promo.class, promo.getCode());

        messages.add(ActionMessages.GLOBAL_MESSAGE,
                     new ActionMessage("promo.deleted"));

        // save messages in session, so they'll survive the redirect
        saveMessages(request.getSession(), messages);

        return mapping.findForward("search");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'edit' method");
        }

        PromoForm promoForm = (PromoForm) form;

        // if an id is passed in, look up the user - otherwise
        // don't do anything - user is doing an add
        if (promoForm.getId() != null) {
        	PromoManager promoManager = (PromoManager) getBean("promoManager");
            Promo promo = (Promo) convert(promoForm);
            promo = (Promo) promoManager.getPromo(new Long(promo.getId()));
            promoForm = (PromoForm) convert(promo);
            updateFormBean(mapping, request, promoForm);
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
        PromoForm promoForm = (PromoForm) form;
        boolean isNew = ("".equals(promoForm.getId()) || promoForm.getId() == null);

        PromoManager promoManager = (PromoManager) getBean("promoManager");
        Promo promo = (Promo) convert(promoForm);
        promoManager.savePromo(promo);

        // add success messages
        if (isNew) {
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("promo.added"));

            // save messages in session to survive a redirect
            saveMessages(request.getSession(), messages);

            return mapping.findForward("search");
        } else {
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                         new ActionMessage("promo.updated"));
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

        Manager mgr = (Manager) getBean("manager");
        request.setAttribute(Constants.PROMO_LIST, mgr.getObjects(Promo.class));

        return mapping.findForward("list");
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    throws Exception {
        return search(mapping, form, request, response);
    }
}
