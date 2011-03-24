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

package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.wareninja.android.commonutils.foursquareV2.LOGGING;
import com.wareninja.android.commonutils.foursquareV2.types.Checkin;

public class CheckinParserV2 extends AbstractParser<Checkin> {

	private static final String TAG = "CheckinParserV2";
	
    @Override
    public Checkin parse(JSONObject json) throws JSONException {
        
    	Checkin obj = new Checkin();
    	
    	if(LOGGING.DEBUG)Log.d(TAG, "json->" + json);
    	
    	/*
    	if (json.has("checkins")) {
			json = new JSONObject( json.getString("checkins") );
		}
    	JSONObject json1;
    	
    	json1 = json.has("items")?json.getJSONObject("items"):null;
    	
    	if(LOGGING.DEBUG)Log.d(TAG, "json-2->" + json);
    	if(LOGGING.DEBUG)Log.d(TAG, "json1->" + json1);
        
    	if (json1==null)
    		return obj;
    	*/
/*
json->{
	"photos":{"count":0,"items":[]}
	,"id":"4d850643f9f3a1cdd9e5c264","timeZone":"Europe\/Berlin"
	,"venue":{
		"id":"4b5a0ecbf964a5208faa28e3"
		,"todos":{"count":0}
		,"location":{
			"state":"Germany"
			,"lng":6.7745873
			,"postalCode":"40213"
			,"address":"Ratinger Str. 16"
			,"lat":51.2293602
			,"city":"DŸsseldorf"
			}
		,"stats":{"checkinsCount":249,"usersCount":87}
		,"verified":false
		,"name":"Brauerei zur Uel"
		,"categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}]
		,"contact":{}}
	,"type":"checkin","comments":{"count":0,"items":[]}
	,"createdAt":1300563523
	}


obj->Checkin [
	mCreated=1300563523
	, mCreatedAt=1300563523
	, mTimeZone=Europe/Berlin
	, mId=4d850643f9f3a1cdd9e5c264
	, mDisplay=null, mDistance=null, mIsmayor=false, mPing=false, mShout=, mUser=null
	, mVenue=Venue [mAddress=Ratinger Str. 16
		, mCategory=Category [mChildCategories=[]
			, mFullPathName=["Nightlife Spots"]
			, mIconUrl=http://foursquare.com/img/categories/nightlife/default.png
			, mId=4bf58dd8d48988d11b941735
			, mNodeName=Pubs
			, isPrimary=true
			]
		, mCategories=[Category [mChildCategories=[], mFullPathName=["Nightlife Spots"], mIconUrl=http://foursquare.com/img/categories/nightlife/default.png, mId=4bf58dd8d48988d11b941735, mNodeName=Pubs, isPrimary=true]]
		, mCheckins=null, mCity=DŸsseldorf, mCityid=null, mCrossstreet=, mDistance=null
		, mGeolat=51.2293602, mGeolong=6.7745873
		, mHasTodo=false
		, mId=4b5a0ecbf964a5208faa28e3
		, mName=Brauerei zur Uel, mPhone=, mSpecials=null, mState=Germany, mStats=null, mTags=null, mTips=null, mTodos=null, mTwitter=null, mZip=40213
		]
	]

 */ 	
    	obj.setId(json.has("id")?json.getString("id"):"");
    	// TODO: convert createdAt into human readable format; use timezone as reference!
        //obj.setCreated(json.has("createdAt")?(""+json.getLong("createdAt")):"");
        obj.setCreatedAt(json.has("createdAt")?(json.getLong("createdAt")):0L);
        obj.setTimeZone(json.has("timeZone")?json.getString("timeZone"):"");
        if (json.has("venue")) {
            obj.setVenue(new VenueParserV2().parse(json.getJSONObject("venue")));
        }
        obj.setShout(json.has("shout")?json.getString("shout"):"");
         
        /*
        if (json.has("display")) {
            obj.setDisplay(json.getString("display"));
        }
        if (json.has("distance")) {
            obj.setDistance(json.getString("distance"));
        }
        if (json.has("ismayor")) {
            obj.setIsmayor(json.getBoolean("ismayor"));
        } 
        if (json.has("ping")) {
            obj.setPing(json.getBoolean("ping"));
        } 
        
        if (json.has("user")) {
            obj.setUser(new UserParser().parse(json.getJSONObject("user")));
        } 
        */
        if(LOGGING.DEBUG)Log.d(TAG, "obj->" + obj);
        
        return obj;
    }
}