package com.beathive.service.commerce.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.beathive.service.commerce.CcRequest;

public class CaptureRequest extends CcRequestImpl implements CcRequest {
    protected static Log log = LogFactory.getLog(CaptureRequest.class);

		public CaptureRequest(){
			super();
			put( CcRequest.SERVICE_CAPTURE, "true" );
			
		}
		public CaptureRequest(String merchantReferenceCode){
			super(merchantReferenceCode);
			put( CcRequest.SERVICE_CAPTURE, "true" );
			
		}
		public CaptureRequest(String merchantReferenceCode , String authRequestID){
			this(merchantReferenceCode);
			setAuthRequestID(authRequestID );
			
		}
		
		public CaptureRequest(Map data){
			this();
			putAll(cleanse(data));
		}
		
		public CaptureRequest(Map data ,String merchantReferenceCode , String authRequestID){
			this(data);
			super.setMerchantReferenceCode(merchantReferenceCode);
			setAuthRequestID(authRequestID );
		}


		private Map cleanse(Map data){
			String[] mand = {CAPTURE_REQUEST_ID  , SERVICE_CAPTURE, MERCHANT_REF_CODE,CURRENCY_KEY};
			
			Map rem = new java.util.HashMap();
			Object val = null;
			for(int y = 0; y< mand.length; y++){
				val = data.get(mand[y]);
				if (val != null ){
					rem.put(mand[y] , val);
				}
			}
			String key = null;

			Iterator it = data.keySet().iterator();
			while(it.hasNext()){
				key = (String)it.next();
				if(key.startsWith(ITEM_PREFIX)){
					rem.put(key , data.get(key));
				}
			}
			System.out.println("made: " + rem);
			return rem;
		}
		
		
}
