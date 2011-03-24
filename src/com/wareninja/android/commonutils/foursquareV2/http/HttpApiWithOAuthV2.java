/***
 * 	Copyright (c) 2010-2011 WareNinja.com
 * 	Author: yg@wareninja.com
 * 	Adapted to V2 endpoint using V1 reference from http://code.google.com/p/foursquared/	
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/

package com.wareninja.android.commonutils.foursquareV2.http;

import android.util.Log;

import com.wareninja.android.commonutils.foursquareV2.LOGGING;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareCredentialsException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareError;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareParseException;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.Parser;
import com.wareninja.android.commonutils.foursquareV2.types.FoursquareType;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.SignatureMethod;

import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpApiWithOAuthV2 extends AbstractHttpApi {
	protected static final String TAG = "HttpApiWithOAuthV2";
    protected static final Logger LOG = Logger.getLogger(HttpApiWithOAuthV2.class.getCanonicalName());
    protected static final boolean DEBUG = LOGGING.DEBUG;

    private OAuthConsumer mConsumer;

    public HttpApiWithOAuthV2(DefaultHttpClient httpClient, String clientVersion) {
        super(httpClient, clientVersion);
    }

    public FoursquareType doHttpRequest(HttpRequestBase httpRequest,
            Parser<? extends FoursquareType> parser) throws FoursquareCredentialsException,
            FoursquareParseException, FoursquareException, IOException {
        if(DEBUG)Log.d(TAG, "doHttpRequest: " + httpRequest.getURI());
            try {
                
            	/*
            	// TODO: append oauth_token for every request!!!
            	if (httpRequest.getMethod().equalsIgnoreCase("GET")) {
                	HttpParams httpParams = httpRequest.getParams();
                	httpParams.setParameter("oauth_token", getOAuthToken());
                	httpRequest.setParams( httpParams );
            	}
            	else if (httpRequest.getMethod().equalsIgnoreCase("POST")) {
            	}
            	*/
            	
            	
            	if(DEBUG)Log.d(TAG, "Signing request: " + httpRequest.getURI());
            	if(DEBUG)Log.d(TAG, "Consumer: " + mConsumer.getConsumerKey() + ", "
                        + mConsumer.getConsumerSecret());
            	if(DEBUG)Log.d(TAG, "Token: " + mConsumer.getToken() + "|secret:" + mConsumer.getTokenSecret());
                mConsumer.sign(httpRequest);
                
            } catch (OAuthMessageSignerException e) {
            	if(DEBUG)Log.d(TAG, "OAuthMessageSignerException", e);
                throw new RuntimeException(e);
            } catch (OAuthExpectationFailedException e) {
            	if(DEBUG)Log.d(TAG, "OAuthExpectationFailedException", e);
                throw new RuntimeException(e);
            }
        return executeHttpRequest(httpRequest, parser);
    }

    public String doHttpPost(String url, NameValuePair... nameValuePairs) throws FoursquareError,
            FoursquareParseException, IOException, FoursquareCredentialsException {
        throw new RuntimeException("Haven't written this method yet.");
    }

    public void setOAuthConsumerCredentials(String key, String secret) {
        mConsumer = new CommonsHttpOAuthConsumer(key, secret, SignatureMethod.HMAC_SHA1);
    }
    
    public String getOAuthConsumerCredentials() {
    	if (mConsumer!=null)
    		return "consumerKey:" + mConsumer.getConsumerKey() + " |:|consumerSecret: " + mConsumer.getConsumerSecret();
    	else
    		return "mConsumer is NULL!!!";
    }

    public void setOAuthTokenWithSecret(String token, String tokenSecret) {
        verifyConsumer();
        if (token == null && tokenSecret == null) {
            if (DEBUG) LOG.log(Level.FINE, "Resetting consumer due to null token/secret.");
            String consumerKey = mConsumer.getConsumerKey();
            String consumerSecret = mConsumer.getConsumerSecret();
            mConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret,
                    SignatureMethod.HMAC_SHA1);
        } else {
            mConsumer.setTokenWithSecret(token, tokenSecret);
        }
    }

    public boolean hasOAuthTokenWithSecret() {
        verifyConsumer();
        return (mConsumer.getToken() != null) && (mConsumer.getTokenSecret() != null);
    }

    private void verifyConsumer() {
        if (mConsumer == null) {
            throw new IllegalStateException(
                    "Cannot call method without setting consumer credentials.");
        }
    }
    
    public String getOAuthTokenWithSecret() {
        return mConsumer.getToken() + "|" + mConsumer.getTokenSecret();
    }
    
    
    public String getOAuthToken() {
        return mConsumer.getToken();
    }
}
