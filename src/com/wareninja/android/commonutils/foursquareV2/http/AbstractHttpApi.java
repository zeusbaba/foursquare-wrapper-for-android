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
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareParseException;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.Parser;
import com.wareninja.android.commonutils.foursquareV2.types.FoursquareType;
import com.wareninja.android.commonutils.foursquareV2.util.JSONUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


abstract public class AbstractHttpApi implements HttpApi {
	
	private static final String TAG = "AbstractHttpApi";
	
    protected static final Logger LOG = Logger.getLogger(AbstractHttpApi.class.getCanonicalName());
    protected static final boolean DEBUG = LOGGING.DEBUG;

    private static final String DEFAULT_CLIENT_VERSION = "com.wareninja.android";
    private static final String CLIENT_VERSION_HEADER = "User-Agent";
    private static final int TIMEOUT = 60;

    private final DefaultHttpClient mHttpClient;
    private final String mClientVersion;

    public AbstractHttpApi(DefaultHttpClient httpClient, String clientVersion) {
        mHttpClient = httpClient;
        if (clientVersion != null) {
            mClientVersion = clientVersion;
        } else {
            mClientVersion = DEFAULT_CLIENT_VERSION;
        }
    }

    public FoursquareType executeHttpRequest(HttpRequestBase httpRequest,
            Parser<? extends FoursquareType> parser) throws FoursquareCredentialsException,
            FoursquareParseException, FoursquareException, IOException {
        if (DEBUG) Log.d(TAG, "doHttpRequest: " + httpRequest.getURI());

        HttpResponse response = executeHttpRequest(httpRequest);
        if (DEBUG) Log.d(TAG, "executed HttpRequest for: "
                + httpRequest.getURI().toString());

        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
            case 200:
                String content = EntityUtils.toString(response.getEntity());
                if (DEBUG) Log.d(TAG, "content: " + content);
                return JSONUtils.consume(parser, content);
                
            case 400:
                if (DEBUG) Log.d(TAG, "HTTP Code: 400");
                throw new FoursquareException(
                        response.getStatusLine().toString(),
                        EntityUtils.toString(response.getEntity()));

            case 401:
                response.getEntity().consumeContent();
                if (DEBUG) Log.d(TAG, "HTTP Code: 401");
                throw new FoursquareCredentialsException(response.getStatusLine().toString());

            case 404:
                response.getEntity().consumeContent();
                if (DEBUG) Log.d(TAG, "HTTP Code: 404");
                throw new FoursquareException(response.getStatusLine().toString());

            case 500:
                response.getEntity().consumeContent();
                if (DEBUG) Log.d(TAG, "HTTP Code: 500");
                throw new FoursquareException("Foursquare is down. Try again later.");

            default:
                if (DEBUG) Log.d(TAG, "Default case for status code reached: "
                        + response.getStatusLine().toString());
                response.getEntity().consumeContent();
                throw new FoursquareException("Error connecting to Foursquare: " + statusCode + ". Try again later.");
        }
    }

    public String doHttpPost(String url, NameValuePair... nameValuePairs)
            throws FoursquareCredentialsException, FoursquareParseException, FoursquareException,
            IOException {
        if (DEBUG) Log.d(TAG, "doHttpPost: " + url);
        HttpPost httpPost = createHttpPost(url, nameValuePairs);

        HttpResponse response = executeHttpRequest(httpPost);
        if (DEBUG) Log.d(TAG, "executed HttpRequest for: " + httpPost.getURI().toString());

        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                try {
                    return EntityUtils.toString(response.getEntity());
                } catch (ParseException e) {
                    throw new FoursquareParseException(e.getMessage());
                }

            case 401:
                response.getEntity().consumeContent();
                throw new FoursquareCredentialsException(response.getStatusLine().toString());

            case 404:
                response.getEntity().consumeContent();
                throw new FoursquareException(response.getStatusLine().toString());

            default:
                response.getEntity().consumeContent();
                throw new FoursquareException(response.getStatusLine().toString());
        }
    }

    /**
     * execute() an httpRequest catching exceptions and returning null instead.
     *
     * @param httpRequest
     * @return
     * @throws IOException
     */
    public HttpResponse executeHttpRequest(HttpRequestBase httpRequest) throws IOException {
        if (DEBUG) Log.d(TAG, "executing HttpRequest for: "
                + httpRequest.getURI().toString()
                + "|" + httpRequest.getMethod()
                //+ "|" + httpRequest.getParams()
                //+ "|" + httpRequest.getAllHeaders()
                );
        /*
        // YG: just for debugging
        String headersStr = "";
        Header[] headers = httpRequest.getAllHeaders();
        for (Header header:headers) {
        	headersStr += "|" + header.getName() + ":" + header.getValue();
        }
        String paramsStr = "";
        HttpParams params = httpRequest.getParams();
        paramsStr += "|fs_username=" + params.getParameter("fs_username");
        paramsStr += "|fs_password=" + params.getParameter("fs_password");
        if (DEBUG) Log.d(TAG, "HttpRequest Headers for: "
                + httpRequest.getURI().toString()
                + "|" + headersStr
                + "|" + paramsStr
                );
        */
/*
executing HttpRequest for: https://api.foursquare.com/v1/authexchange.json|POST
HttpRequest Headers for: https://api.foursquare.com/v1/authexchange.json|
|User-Agent:com.wareninja.android.mayormonster:1|Authorization:OAuth oauth_version="1.0",oauth_nonce="3568494401228",oauth_signature_method="HMAC-SHA1",oauth_consumer_key="P5BV4EIC5RC5MF1STSVPNLPRBF3M5S0OVXKRLIIN0QMU3BEA",oauth_token="",oauth_timestamp="1294862657",oauth_signature="dq7ej2ChkH8uXqLKJ2qICrcIUgk%3D"|
|fs_username=null|fs_password=null

HttpRequest Headers for: https://api.foursquare.com/v1/authexchange.json||User-Agent:com.wareninja.android.mayormonster:1|Authorization:OAuth oauth_version="1.0",oauth_nonce="3568497175618",oauth_signature_method="HMAC-SHA1",oauth_consumer_key="P5BV4EIC5RC5MF1STSVPNLPRBF3M5S0OVXKRLIIN0QMU3BEA",oauth_token="",oauth_timestamp="1294862657",oauth_signature="5BHzUcaSioV%2BdIX5HiB9C2AyuzA%3D"|
|fs_username=null|fs_password=null


 */
        
        //-WareNinjaUtils.trustEveryone();//YG
        
        try {
            mHttpClient.getConnectionManager().closeExpiredConnections();
            return mHttpClient.execute(httpRequest);
        } catch (IOException e) {
            httpRequest.abort();
            throw e;
        }
    }

    public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs) {
    	
        if (DEBUG) Log.d(TAG, "creating HttpGet for: " + url + "|nameValuePairs->"+nameValuePairs);
        
        String query = URLEncodedUtils.format(stripNulls(nameValuePairs), HTTP.UTF_8);
        HttpGet httpGet = new HttpGet(url + "?" + query);
        httpGet.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        if (DEBUG) Log.d(TAG, "Created: " + httpGet.getURI());
        return httpGet;
    }

    public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs) {
    	
        if (DEBUG) Log.d(TAG, "creating HttpPost for: " + url + "|nameValuePairs->"+nameValuePairs);
        
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(stripNulls(nameValuePairs), HTTP.UTF_8));
        } catch (UnsupportedEncodingException e1) {
            throw new IllegalArgumentException("Unable to encode http parameters.");
        }
        if (DEBUG) Log.d(TAG, "Created: " + httpPost);
        return httpPost;
    }
    
    public HttpURLConnection createHttpURLConnectionPost(URL url, String boundary) 
        throws IOException {
    	
    	//-WareNinjaUtils.trustEveryone();//YG
    	
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
        conn.setDoInput(true);        
        conn.setDoOutput(true); 
        conn.setUseCaches(false); 
        conn.setConnectTimeout(TIMEOUT * 1000);
        conn.setRequestMethod("POST");

        conn.setRequestProperty(CLIENT_VERSION_HEADER, mClientVersion);
        conn.setRequestProperty("Connection", "Keep-Alive"); 
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        
        return conn;
    }

    private List<NameValuePair> stripNulls(NameValuePair... nameValuePairs) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (int i = 0; i < nameValuePairs.length; i++) {
            NameValuePair param = nameValuePairs[i];
            if (param.getValue() != null) {
                //-if (DEBUG) Log.d(TAG, "Param: " + param);
                params.add(param);
            }
        }
        return params;
    }

    /**
     * Create a thread-safe client. This client does not do redirecting, to allow us to capture
     * correct "error" codes.
     *
     * @return HttpClient
     */
    public static final DefaultHttpClient createHttpClient() {
        // Sets up the http part of the service.
        final SchemeRegistry supportedSchemes = new SchemeRegistry();

        // Register the "http" protocol scheme, it is required
        // by the default operator to look up socket factories.
        final SocketFactory sf = PlainSocketFactory.getSocketFactory();
        supportedSchemes.register(new Scheme("http", sf, 80));
        supportedSchemes.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        
        // Set some client http client parameter defaults.
        final HttpParams httpParams = createHttpParams();
        HttpClientParams.setRedirecting(httpParams, false);

        final ClientConnectionManager ccm = new ThreadSafeClientConnManager(httpParams,
                supportedSchemes);
        return new DefaultHttpClient(ccm, httpParams);
    }

    /**
     * Create the default HTTP protocol parameters.
     */
    private static final HttpParams createHttpParams() {
        final HttpParams params = new BasicHttpParams();

        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSoTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        return params;
    }

}
