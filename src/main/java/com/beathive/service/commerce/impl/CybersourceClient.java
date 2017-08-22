package com.beathive.service.commerce.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.beathive.Constants;


public class CybersourceClient extends Object {
    //~ Static fields/initializers =============================================

    private static Log log =
        LogFactory.getFactory().getInstance(CybersourceClient.class);
    
    private static  Properties properties = null;


    //~ Methods ================================================================

    /**
     * Retrieves a Mail session from Tomcat's Resource Factory (JNDI)
     */
    public static Properties getProperties() {
    		if (properties == null){
			properties = new Properties();
			ResourceBundle cybProps = ResourceBundle.getBundle("cyb");
			Enumeration en = cybProps.getKeys();
			String key;
			while (en.hasMoreElements()) {
				key = (String) en.nextElement();
				properties.setProperty(key, cybProps.getString(key));
			}
		}
		return properties;
	}

    /**
	 * This method is used to send a Message with a pre-defined mime-type.
	 * 
	 * @param from
	 *            e-mail address of sender
	 * @param to
	 *            e-mail address(es) of recipients
	 * @param subject
	 *            subject of e-mail
	 * @param content
	 *            the body of the e-mail
	 * @param mimeType
	 *            type of message, i.e. text/plain or text/html
	 * @throws MessagingException
	 *             the exception to indicate failure
	 */
    public static String authorize(CybCredentials credentials) throws MessagingException {
	   	HashMap request = new HashMap();
	   	
		request.put( "ccAuthService_run", "true" );
		
		// We will let the Client get the merchantID from props and insert it
		// into the request Map.
		
		// this is your own tracking number.  CyberSource recommends that you
		// use a unique one for each order.
		request.put( "merchantReferenceCode", "MRC-14344" );
		
		request.put( "billTo_firstName", "John" );
		request.put( "billTo_lastName", "Doe" );
		request.put( "billTo_street1", "1295 Charleston Road" );
		request.put( "billTo_city", "Mountain View" );
		request.put( "billTo_state", "CA" );
		request.put( "billTo_postalCode", "94043" );
		request.put( "billTo_country", "US" );
		request.put( "billTo_email", "nobody@cybersource.com" );
		request.put( "billTo_ipAddress", "10.7.7.7" );
		request.put( "billTo_phoneNumber", "650-965-6000" );
		request.put( "shipTo_firstName", "Jane" );
		request.put( "shipTo_lastName", "Doe" );
		request.put( "shipTo_street1", "100 Elm Street" );
		request.put( "shipTo_city", "San Mateo" );
		request.put( "shipTo_state", "CA" );
		request.put( "shipTo_postalCode", "94401" );
		request.put( "shipTo_country", "US" );
		request.put( "card_accountNumber", "4111111111111111" );
		request.put( "card_expirationMonth", "12" );
		request.put( "card_expirationYear", "2020" );
		request.put( "purchaseTotals_currency", "USD" );

		// there are two items in this sample
		request.put( "item_0_unitPrice", "12.34" );
		request.put( "item_1_unitPrice", "56.78" );
		return "reply";
 
    }
}
