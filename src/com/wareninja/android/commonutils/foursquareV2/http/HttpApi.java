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

import com.wareninja.android.commonutils.foursquareV2.error.FoursquareCredentialsException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareParseException;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.Parser;
import com.wareninja.android.commonutils.foursquareV2.types.FoursquareType;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpApi {

    abstract public FoursquareType doHttpRequest(HttpRequestBase httpRequest,
            Parser<? extends FoursquareType> parser) throws FoursquareCredentialsException,
            FoursquareParseException, FoursquareException, IOException;

    abstract public String doHttpPost(String url, NameValuePair... nameValuePairs)
            throws FoursquareCredentialsException, FoursquareParseException, FoursquareException,
            IOException;

    abstract public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs);

    abstract public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs);
    
    abstract public HttpURLConnection createHttpURLConnectionPost(URL url, String boundary)
            throws IOException; 
    
    abstract public String getOAuthToken();
}
