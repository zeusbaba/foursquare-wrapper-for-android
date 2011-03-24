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


package com.wareninja.android.commonutils.foursquareV2.util;

import android.text.TextUtils;
import android.util.Log;

import com.wareninja.android.commonutils.foursquareV2.error.FoursquareCredentialsException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareParseException;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.Parser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.TipParser;
import com.wareninja.android.commonutils.foursquareV2.types.Credentials;
import com.wareninja.android.commonutils.foursquareV2.types.FoursquareType;
import com.wareninja.android.commonutils.foursquareV2.LOGGING;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;




public class JSONUtils {
    
	private static final String TAG = "JSONUtils";
    private static final boolean DEBUG = LOGGING.DEBUG;
    private static final Logger LOG = Logger.getLogger(TipParser.class.getCanonicalName());
    
    /**
     * Takes a parser, a json string, and returns a foursquare type.
     */
    public static FoursquareType consume(Parser<? extends FoursquareType> parser, String content)
        throws FoursquareCredentialsException, FoursquareParseException, FoursquareException, IOException {
        
        if (DEBUG) {
            //LOG.log(Level.FINE, "http response: " + content);
            Log.d(TAG, "http response: " + content);
        }
/*
http response: {"meta":{"code":200},"response":{"checkins":{"count":56,"items":[{"id":"4d850643f9f3a1cdd9e5c264","createdAt":1300563523,"type":"checkin","timeZone":"Europe/Berlin","venue":{"id":"4b5a0ecbf964a5208faa28e3","name":"Brauerei zur Uel","contact":{},"location":{"address":"Ratinger Str. 16","city":"Düsseldorf","state":"Germany","postalCode":"40213","lat":51.2293602,"lng":6.7745873},"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":249,"usersCount":87},"todos":{"count":0}},"photos":{"count":0,"items":[]},"comments":{"count":0,"items":[]}},{"id":"4d84eb2961676dcb5caa7de4","createdAt":1300556585,"type":"checkin","shout":"kya","timeZone":"Europe/Berlin","venue":{"id":"4b686d1af964a520d7772be3","name":"Irish Pub","contact":{"phone":"0211442651"},"location":{"address":"Spichernstrasse 2","crossStreet":"Spichernplatz","city":"Düsseldorf","state":"Nordrhein-Westfalen","postalCode":"40476","country":"Germany","lat":51.2478087,"lng":6.7838782},"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":89,"usersCount":17},"todos":{"count":0}},"photos":{"count":0,"items":[]},"comments":{"count":0,"items":[]}},{"id":"4d84c51e81fdb1f70fb102c0","createdAt":1300546846,"type":"checkin","timeZone":"Europe/Berlin","venue":{"id":"4b697ce8f964a5203ea42be3","name":"Rheinpark Düsseldorf","contact":{},"location":{"address":"Theodor-Heuss-Brücke","crossStreet":"Cäcilienallee","city":"Düsseldorf","state":"Nordrhein-Westfalen","lat":51.24305894491732,"lng":6.768093109130859},"categories":[{"id":"4bf58dd8d48988d163941735","name":"Parks","icon":"http://foursquare.com/img/categories/parks_outdoors/default.png","parents":["Great Outdoors"],"primary":true},{"id":"4bf58dd8d48988d1df931735","name":"BBQ Joints","icon":"http://foursquare.com/img/categories/food/bbq.png","parents":["Food"]}],"verified":false,"stats":{"checkinsCount":150,"usersCount":81},"todos":{"count":0}},"photos":{"count":0,"items":[]},"comments":{"count":0,"items":[]}},{"id":"4d849d3d5091370495b2825b","createdAt":1300536637,"type":"checkin","shout":"Checkin Ninja!","timeZone":"Europe/Berlin","venue":{"id":"4b686d1af964a520d7772be3","name":"Irish Pub","contact":{"phone":"0211442651"},"location":{"address":"Spichernstrasse 2","crossStreet":"Spichernplatz","city":"Düsseldorf","state":"Nordrhein-Westfalen","postalCode":"40476","country":"Germany","lat":51.2478087,"lng":6.7838782},"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":89,"usersCount":17},"todos":{"count":0}},"photos":{"count":0,"items":[]},"comments":{"count":0,"items":[]}},{"id":"4d7cfdb7f260a093da1344ba","createdAt":1300037047,"type":"checkin","shout":"MayorMonster kullanarak checkin yaptim! :-)","timeZone":"Europe/Berlin","venue":{"id":"4b686d1af964a520d7772be3","name":"Irish Pub","contact":{"phone":"0211442651"},"location":{"address":"Spichernstrasse 2","crossStreet":"Spichernplatz","city":"Düsseldorf","state":"Nordrhein-Westfalen","postalCode":"40476","country":"Germany","lat":51.2478087,"lng":6.7838782},"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":89,"usersCount":17},"todos":{"count":0}},"photos":{"count":0,"items":[]},"comments":{"count":0,"items":[]}},{"id":"4d7cfdb78f89224bce15ae26","createdAt":1300037047,"type":"checkin","shout":"MayorMonster kullanarak checkin yaptim! :-)","timeZone":"Europe/Berlin","venue":{"id":"4bcd5149b6c49c7492459591","name":"Haltestelle Spichernplatz 707 / 715","contact":{},"location":{"city":

 */
        
        try {
        	// YG: adapted for V2 endpoint
            // Depending on the
            // type of API call, the content might be a JSONObject or a JSONArray.
            // Since JSONArray does not derive from JSONObject, we need to check for
            // either of these cases to parse correctly.
            JSONObject json = new JSONObject(content);
            
            JSONObject json1;
            if (json.has("meta")) {
            	json1 = json.getJSONObject("meta");
            	int respCode = json1.has("code")?json1.getInt("code"):0;
            	if (respCode!=200) {
            		throw new FoursquareException(
            				respCode
            				+"|"+(json1.has("errorType")?json1.getString("errorType"):"unknown_error")
            				);
            	}
            }
            
            String notifications = "";
            if(json.has("notifications")) {
            	notifications = json.getString("notifications");
            }
            
            if (json.has("response")) {
            	json = json.getJSONObject("response");
            }
            if(DEBUG)Log.d(TAG, "json-> " + json);
/*
json-> {"checkins":{"count":56,"items":[{"photos":{"count":0,"items":[]},"id":"4d850643f9f3a1cdd9e5c264","timeZone":"Europe\/Berlin","venue":{"id":"4b5a0ecbf964a5208faa28e3","todos":{"count":0},"location":{"state":"Germany","lng":6.7745873,"postalCode":"40213","address":"Ratinger Str. 16","lat":51.2293602,"city":"Düsseldorf"},"stats":{"checkinsCount":249,"usersCount":87},"verified":false,"name":"Brauerei zur Uel","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{}},"type":"checkin","comments":{"count":0,"items":[]},"createdAt":1300563523},{"photos":{"count":0,"items":[]},"id":"4d84eb2961676dcb5caa7de4","createdAt":1300556585,"timeZone":"Europe\/Berlin","venue":{"id":"4b686d1af964a520d7772be3","todos":{"count":0},"location":{"postalCode":"40476","address":"Spichernstrasse 2","state":"Nordrhein-Westfalen","lng":6.7838782,"lat":51.2478087,"country":"Germany","city":"Düsseldorf","crossStreet":"Spichernplatz"},"stats":{"checkinsCount":89,"usersCount":17},"verified":false,"name":"Irish Pub","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{"phone":"0211442651"}},"type":"checkin","comments":{"count":0,"items":[]},"shout":"kya"},{"photos":{"count":0,"items":[]},"id":"4d84c51e81fdb1f70fb102c0","timeZone":"Europe\/Berlin","venue":{"id":"4b697ce8f964a5203ea42be3","todos":{"count":0},"location":{"state":"Nordrhein-Westfalen","lng":6.768093109130859,"address":"Theodor-Heuss-Brücke","lat":51.24305894491732,"city":"Düsseldorf","crossStreet":"Cäcilienallee"},"stats":{"checkinsCount":150,"usersCount":81},"verified":false,"name":"Rheinpark Düsseldorf","categories":[{"id":"4bf58dd8d48988d163941735","icon":"http:\/\/foursquare.com\/img\/categories\/parks_outdoors\/default.png","parents":["Great Outdoors"],"primary":true,"name":"Parks"},{"id":"4bf58dd8d48988d1df931735","icon":"http:\/\/foursquare.com\/img\/categories\/food\/bbq.png","parents":["Food"],"name":"BBQ Joints"}],"contact":{}},"type":"checkin","comments":{"count":0,"items":[]},"createdAt":1300546846},{"photos":{"count":0,"items":[]},"id":"4d849d3d5091370495b2825b","createdAt":1300536637,"timeZone":"Europe\/Berlin","venue":{"id":"4b686d1af964a520d7772be3","todos":{"count":0},"location":{"postalCode":"40476","address":"Spichernstrasse 2","state":"Nordrhein-Westfalen","lng":6.7838782,"lat":51.2478087,"country":"Germany","city":"Düsseldorf","crossStreet":"Spichernplatz"},"stats":{"checkinsCount":89,"usersCount":17},"verified":false,"name":"Irish Pub","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{"phone":"0211442651"}},"type":"checkin","comments":{"count":0,"items":[]},"shout":"Checkin Ninja!"},{"photos":{"count":0,"items":[]},"id":"4d7cfdb7f260a093da1344ba","createdAt":1300037047,"timeZone":"Europe\/Berlin","venue":{"id":"4b686d1af964a520d7772be3","todos":{"count":0},"location":{"postalCode":"40476","address":"Spichernstrasse 2","state":"Nordrhein-Westfalen","lng":6.7838782,"lat":51.2478087,"country":"Germany","city":"Düsseldorf","crossStreet":"Spichernplatz"},"stats":{"checkinsCount":89,"usersCount":17},"verified":false,"name":"Irish Pub","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{"phone":"0211442651"}},"type":"checkin","comments":{"count":0,"items":[]},"shout":"MayorMonster kullanarak checkin yaptim! :-)"},{"photos":{"count":0,"items":[]},"id":"4d7cfdb78f89224bce15ae26","createdAt":1300037047,"timeZone":"Europe\/Berlin","venue":{"id":"4bcd5149b6c49c7492459591","todos":{"count":0},"location":{"state":"Nordrhein-Westfalen","lng":6.78339,"postalCode":"40476","lat":51.247444,"
 */
            
            Iterator<String> it = (Iterator<String>)json.keys();
            if (it.hasNext()) {
                String key = (String)it.next();
                
                Object obj = json.get(key);
                if (obj instanceof JSONArray) {
                	if(DEBUG)Log.d(TAG, "obj instanceof JSONArray-> " + obj);
                	
                    return parser.parse((JSONArray)obj);
                } else {
                	// append _type; e.g. checkins
                	//((JSONObject)obj).put("_type", key);
                	if (!TextUtils.isEmpty(notifications)) {
                		((JSONObject)obj).put( "notifications", new JSONArray(notifications) );
                	}
                	
                	if(DEBUG)Log.d(TAG, "obj instanceof JSONObject-> " + obj);
                    return parser.parse((JSONObject)obj);
                }
            } else {
                throw new FoursquareException("Error parsing JSON response, object had no single child key.");
            }
            
        } catch (JSONException ex) {
        	
        	// NOTE: added by YG: check if this is OAuth token response in XML format!
        	/*
        	01-09 18:25:01.480: DEBUG/JSONUtils(715): http response: <?xml version="1.0" encoding="UTF-8"?>
        	01-09 18:25:01.480: DEBUG/JSONUtils(715): 
        	<credentials><oauth_token>1X4QXFZIUIKQBEKZBUUXOQYIMB04B4FQQZYYGZCM4JMZVSJA</oauth_token>
        	<oauth_token_secret>J0HYVN5X3BFHPBDWNMB2AYZI0QRNPDEKS3HSXL114QGIUMB1</oauth_token_secret></credentials>  

        	 */    
        	if(DEBUG)Log.d(TAG, "JSONException:"+ex.toString());
        	if (content.contains("<oauth_token>")&&content.contains("<oauth_token_secret>")) {
        		
        		if(DEBUG)Log.d(TAG, "check/parse IF it is OAuth token!");
        	
        		//if(DEBUG)Log.d(TAG, "there is OAuth token in response -> parse it!");
        		
        		final String oauthToken = content.substring(
        				content.indexOf("<oauth_token>")+"<oauth_token>".length()
        				, content.indexOf("</oauth_token>"));
        		final String oauthTokenSecret = content.substring(
        				content.indexOf("<oauth_token_secret>")+"<oauth_token_secret>".length()
        				, content.indexOf("</oauth_token_secret>"));
        		/*
        		String temp = "";
        		StringTokenizer st = new StringTokenizer(content, "<oauth_token>");
        		temp = st.nextToken();
        		st = new StringTokenizer(temp, "</oauth_token><oauth_token_secret>");
        		final String oauthToken = st.nextToken();
        		
        		temp = st.nextToken();
        		st = new StringTokenizer(temp, "</oauth_token_secret>");
        		final String oauthTokenSecret = st.nextToken();
        		*/
        		
        		if(DEBUG)Log.d(TAG, "oauthToken:"+oauthToken + "|oauthTokenSecret:" + oauthTokenSecret);
        		Credentials credentials = new Credentials();
        		credentials.setOauthToken(oauthToken);
        		credentials.setOauthTokenSecret(oauthTokenSecret);
        		return credentials;
        	}
        	else
        		throw new FoursquareException("Error parsing JSON response: " + ex.getMessage());
        }
    }
}