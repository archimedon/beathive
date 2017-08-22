package com.beathive.webapp.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.displaytag.tags.el.ExpressionEvaluator;

import com.beathive.Constants;
import com.beathive.model.LabelValue;
import com.beathive.webapp.form.DescriptorForm;

/**
 * 
 * Borrowed and c.ustomized
 * 
 * Tag for creating multiple &lt;select&gt; options for displaying a list of
 * screen menus.
 *
 * <p>To be used as follows:
 * <pre>&lt;tag:menu listName="descriptorName" property="format" [ [prompt="promptValue"] | [promptKey="promptValueKey"]]
 * 					[default="value"] [toScope="page|request|session|application"]/&gt;</pre>
 * 
 * <pre>&lt;tag:menu listName="descriptorName" property="format" [ [prompt="promptValue"] | [promptKey="promptValueKey"]]
 * 					[default="value"]/&gt;</pre>
 * 
 * <pre>&lt;tag:menu listName="descriptorName" property="format" toScope="page|request|session|application"/&gt;</pre>
 * </p>
 *
 * @jsp.tag name="menu" bodycontent="empty"
 */
public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 3462586123555699823L;
    protected Log log = LogFactory.getLog(MenuTag.class);
	private String property;
    private String listName;
    private String prompt;
    private String promptKey;
    private String scope;
    private String selected;
    private String styleClass;
    private String styleId;

    /**
     * @param property The property to set.
     * @jsp.attribute required="true" rtexprvalue="true"
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @param prompt The prompt to set.
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

	/**
	 * @param promptKey The promptKey to set.
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setPromptKey(String promptKey) {
		this.promptKey = promptKey;
	}

    /**
     * @param selected The selected option.
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setDefault(String selected) {
        this.selected = selected;
    }

	/**
	 * @param listName The listName to set.
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}

    /**
     * Property used to simply stuff the list into a
     * specified scope.
     * @param scope
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setToScope(String scope) {
        this.scope = scope;
    }

    /**
     * Process the start of this tag.
     *
     * @return
     *
     * @exception JspException if a JSP exception has occurred
     *
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        

        Locale userLocale = pageContext.getRequest().getLocale();
        if (scope != null){
	        List descMenu = this.getMenuList(userLocale);
	
	        toScope(descMenu);
        }else{
	        	ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);
	        	TagUtils tagUtils = TagUtils.getInstance();
	        	String style = (styleClass!=null)? styleClass :  listName +"Menu";
	        	String styleid = (styleId!=null)? styleId :  null;
	        	String promptv = (promptKey!=null)
	        		? tagUtils.message(pageContext,Globals.MESSAGES_KEY, userLocale.getDisplayName(), eval.evalString("promptKey",promptKey)) 
				: (prompt!=null
						? eval.evalString("prompt", prompt)
						: null);
	        		log.debug("listName:sel val 1: " +listName+":" + selected);
	            if (selected != null) {
	                selected = eval.evalString("default", selected);
	                log.debug("listName:sel val 2: " +listName+":" + selected);
	            }
	            List descMenu = (List) ((Map) pageContext.getServletContext()
	    				.getAttribute(Constants.DESCRIPTOR_KEY)).get(listName);
	            if (descMenu == null){
	                JspException e =
	                    new JspException("Cannot find listName: " + listName);
	                tagUtils.saveException(pageContext, e);
	                throw e;
	            }
    			StringBuffer sb = new StringBuffer();
    			sb.append("<select name=\"" + property + "\" class=\"" + style + "\"" + ((styleid!=null)?" id=\"" + styleId+ "\"" : "") +">\n");
    			if (promptv != null) {
    				sb.append("    <option value=\"\""+ ((selected != null)?"":" selected=\"selected\"") +">");
    				sb.append(promptv + "</option>\n");
    			}
    			DescriptorForm desc = null;
    			for (Iterator it = descMenu.iterator(); it.hasNext(); ) {
    				desc = (DescriptorForm) it.next();
    				sb.append("    <option value=\"" + desc.getId() + "\"");
                if ((selected != null) && selected.equals(desc.getId())) {
                    sb.append(" selected=\"selected\"");
                }
                sb.append(">" + tagUtils.message(pageContext,
						Globals.MESSAGES_KEY, userLocale.getDisplayName(), desc
						.getLabelKey()) + "</option>\n");
            }
            sb.append("</select>\n");
//          	Print the retrieved message to our output writer
//            	tagUtils.write(pageContext, sb.toString());
                try {
					pageContext.getOut().write(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return (SKIP_BODY);
    }

    /**
     * Release aquired resources to enable tag reusage.
     *
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
        property = null;
        listName = null;
        prompt = null;
        scope = null;
        selected = null;
        styleClass = null;
    }

    private void toScope(Object o) throws JspException{
    	if (scope != null){
	    if (scope.equals("page")) {
	    pageContext.setAttribute(property, o);
		} else if (scope.equals("request")) {
		    pageContext.getRequest().setAttribute(property, o);
		} else if (scope.equals("session")) {
		    pageContext.getSession().setAttribute(property, o);
		} else if (scope.equals("application")) {
		    pageContext.getServletContext().setAttribute(property, o);
		} else {
		    throw new JspException("Attribute 'scope' must be: page, request, session or application");
	    }
    	}
    }
    
    protected List getMenuList(Locale locale) {
		List itemList = new ArrayList();
		TagUtils tagUtils = TagUtils.getInstance();
		List list = (List) ((Map) super.pageContext.getServletContext()
				.getAttribute(Constants.DESCRIPTOR_KEY)).get(listName);
		try {
			LabelValue tmp = null;
			DescriptorForm desc = null;
			for (Iterator it = list.iterator(); it.hasNext(); ) {
				desc = (DescriptorForm) it.next();
				tmp = new LabelValue(tagUtils.message(pageContext,
						Globals.MESSAGES_KEY, locale.getDisplayName(), desc
						.getLabelKey()) , desc.getId());
				itemList.add(tmp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemList;
	}

	/**
	 * @return the styleClass
	 */
	public void setStyleClass(String styleclass) {
		this.styleClass = styleclass;
	}

	/**
	 * @param styleId the styleId to set
	 */
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	
}
