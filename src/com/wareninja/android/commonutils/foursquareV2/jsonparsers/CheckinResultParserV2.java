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

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.wareninja.android.commonutils.foursquareV2.LOGGING;
import com.wareninja.android.commonutils.foursquareV2.types.CheckinResult;

public class CheckinResultParserV2 extends AbstractParser<CheckinResult> {
	private static final String TAG = "CheckinResultParserV2";
	
    @Override
    public CheckinResult parse(JSONObject json) throws JSONException {
        CheckinResult obj = new CheckinResult();
        
        if(LOGGING.DEBUG)Log.d(TAG, "json->"+json);
/*


http response: 
	{"meta":{"code":200}
	,"notifications":[
		{"type":"message"
			,"item":{"message":"OK! We've got you @ Irish Pub. You've been here 18 times."}
		}
		,{"type":"mayorship"
			,"item":{"type":"nochange","checkins":14,"daysBehind":13,"user":{"id":"3221562","firstName":"Grischa","lastName":"S.","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/G4WY1VIAKUUY4SHO.jpg","gender":"male","homeCity":"DŸsseldorf, Germany"},"message":"Grischa S. is the Mayor of Irish Pub.","image":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/G4WY1VIAKUUY4SHO.jpg"}
			}
		,{"type":"leaderboard"
			,"item":{"leaderboard":[{"user":{"id":"402676","firstName":"yg","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/LX1MW5CTKN411NFZ.jpg","gender":"male","homeCity":"Antioch","relationship":"friend"},"scores":{"recent":42,"max":56,"checkinsCount":14},"rank":1},{"user":{"id":"5854980","firstName":"WareNinja.net","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/RV3FPN0H5I2XIHD1.png","gender":"male","homeCity":"DŸsseldorf, Deutschland","relationship":"self"},"scores":{"recent":23,"max":59,"checkinsCount":6},"rank":2}],"message":"With that last check-in, you're closing in on yg!","scores":[{"points":5,"icon":"https://playfoursquare.s3.amazonaws.com/static/img/points/birthday.png","message":"Happy birthday from foursquare!  You look nice today."}],"total":5}
			}
		,{"type":"score"
			,"item":{"scores":[{"points":5,"icon":"/img/points/birthday.png","message":"Happy birthday from foursquare!  You look nice today."}],"total":5}
		}
	]
	,"response":{
		"checkin":{
			"id":"4d87dff726a36ea84de7c1ad"
			,"createdAt":1300750327
			,"type":"checkin"
			,"shout":"kyaa"
			,"timeZone":"Europe/Berlin"
			,"venue":{
				"id":"4b686d1af964a520d7772be3"
				,"name":"Irish Pub"
				,"contact":{"phone":"0211442651"}
				,"location":{
					"address":"Spichernstrasse 2"
					,"crossStreet":"Spichernplatz"
					,"city":"DŸsseldorf"
					,"state":"Nordrhein-Westfalen"
					,"postalCode":"40476"
					,"country":"Germany"
					,"lat":51.2478087
					,"lng":6.7838782
					,"distance":0
					}
				,"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}]
				,"verified":false
				,"stats":{"checkinsCount":89,"usersCount":17},"todos":{"count":0}
				}
			}
		}
	}

json-> {"checkin":{"id":"4d87dff726a36ea84de7c1ad","timeZone":"Europe\/Berlin","venue":{"id":"4b686d1af964a520d7772be3","todos":{"count":0},"location":{"distance":0,"postalCode":"40476","address":"Spichernstrasse 2","state":"Nordrhein-Westfalen","lng":6.7838782,"lat":51.2478087,"country":"Germany","city":"DŸsseldorf","crossStreet":"Spichernplatz"},"stats":{"checkinsCount":89,"usersCount":17},"verified":false,"name":"Irish Pub","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{"phone":"0211442651"}},"type":"checkin","shout":"kyaa","createdAt":1300750327}}
03-22 00:32:07.514: DEBUG/JSONUtils(1218): obj instanceof JSONObject-> {"id":"4d87dff726a36ea84de7c1ad","_type":"checkin","timeZone":"Europe\/Berlin","venue":{"id":"4b686d1af964a520d7772be3","todos":{"count":0},"location":{"distance":0,"postalCode":"40476","address":"Spichernstrasse 2","state":"Nordrhein-Westfalen","lng":6.7838782,"lat":51.2478087,"country":"Germany","city":"DŸsseldorf","crossStreet":"Spichernplatz"},"stats":{"checkinsCount":89,"usersCount":17},"verified":false,"name":"Irish Pub","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{"phone":"0211442651"}},"type":"checkin","shout":"kyaa","createdAt":1300750327}


https://api.foursquare.com/v2/checkins/add
03-23 22:41:18.002: DEBUG/AbstractHttpApi(413): content: {"meta":{"code":200},"notifications":[{"type":"message","item":{"message":"OK! We've got you @ Irish Pub. You've been here 19 times."}},{"type":"mayorship","item":{"type":"nochange","checkins":15,"daysBehind":13,"user":{"id":"3221562","firstName":"Grischa","lastName":"S.","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/G4WY1VIAKUUY4SHO.jpg","gender":"male","homeCity":"DŸsseldorf, Germany"},"message":"Grischa S. is the Mayor of Irish Pub.","image":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/G4WY1VIAKUUY4SHO.jpg"}},{"type":"leaderboard","item":{"leaderboard":[{"user":{"id":"402676","firstName":"yg","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/LX1MW5CTKN411NFZ.jpg","gender":"male","homeCity":"Antioch","relationship":"friend"},"scores":{"recent":30,"max":56,"checkinsCount":11},"rank":1},{"user":{"id":"5854980","firstName":"WareNinja.net","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/RV3FPN0H5I2XIHD1.png","gender":"male","homeCity":"DŸsseldorf, Deutschland","relationship":"self"},"scores":{"recent":24,"max":59,"checkinsCount":7},"rank":2}],"message":"Big check-in - now you're hot on yg's tail.","scores":[{"points":1,"icon":"https://playfoursquare.s3.amazonaws.com/static/img/points/defaultpointsicon2.png","message":"Every check-in counts!"}],"total":1}},{"type":"score","item":{"scores":[{"points":1,"icon":"/img/points/defaultpointsicon2.png","message":"Every check-in counts!"}],"total":1}}],"response":{"checkin":{"id":"4d8a68fef607a09348e1fe86","createdAt":1300916478,"type":"checkin","shout":"Checkin NINJA!","timeZone":"Europe/Berlin","venue":{"id":"4b686d1af964a520d7772be3","name":"Irish Pub","contact":{"phone":"0211442651"},"location":{"address":"Spichernstrasse 2","crossStreet":"Spichernplatz","city":"DŸsseldorf","state":"Nordrhein-Westfalen","postalCode":"40476","country":"Germany","lat":51.2478087,"lng":6.7838782,"distance":0},"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":91,"usersCount":17},"todos":{"count":0}}}}}
03-23 22:41:18.002: DEBUG/JSONUtils(413): http response: {"meta":{"code":200},"notifications":[{"type":"message","item":{"message":"OK! We've got you @ Irish Pub. You've been here 19 times."}},{"type":"mayorship","item":{"type":"nochange","checkins":15,"daysBehind":13,"user":{"id":"3221562","firstName":"Grischa","lastName":"S.","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/G4WY1VIAKUUY4SHO.jpg","gender":"male","homeCity":"DŸsseldorf, Germany"},"message":"Grischa S. is the Mayor of Irish Pub.","image":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/G4WY1VIAKUUY4SHO.jpg"}},{"type":"leaderboard","item":{"leaderboard":[{"user":{"id":"402676","firstName":"yg","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/LX1MW5CTKN411NFZ.jpg","gender":"male","homeCity":"Antioch","relationship":"friend"},"scores":{"recent":30,"max":56,"checkinsCount":11},"rank":1},{"user":{"id":"5854980","firstName":"WareNinja.net","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/RV3FPN0H5I2XIHD1.png","gender":"male","homeCity":"DŸsseldorf, Deutschland","relationship":"self"},"scores":{"recent":24,"max":59,"checkinsCount":7},"rank":2}],"message":"Big check-in - now you're hot on yg's tail.","scores":[{"points":1,"icon":"https://playfoursquare.s3.amazonaws.com/static/img/points/defaultpointsicon2.png","message":"Every check-in counts!"}],"total":1}},{"type":"score","item":{"scores":[{"points":1,"icon":"/img/points/defaultpointsicon2.png","message":"Every check-in counts!"}],"total":1}}],"response":{"checkin":{"id":"4d8a68fef607a09348e1fe86","createdAt":1300916478,"type":"checkin","shout":"Checkin NINJA!","timeZone":"Europe/Berlin","venue":{"id":"4b686d1af964a520d7772be3","name":"Irish Pub","contact":{"phone":"0211442651"},"location":{"address":"Spichernstrasse 2","crossStreet":"Spichernplatz","city":"DŸsseldorf","state":"Nordrhein-Westfalen","postalCode":"40476","country":"Germany","lat":51.2478087,"lng":6.7838782,"distance":0},"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":91,"usersCount":17},"todos":{"count":0}}}}}
json-> {"checkin":{"id":"4d8a68fef607a09348e1fe86","timeZone":"Europe\/Berlin","venue":{"id":"4b686d1af964a520d7772be3","todos":{"count":0},"location":{"distance":0,"postalCode":"40476","address":"Spichernstrasse 2","state":"Nordrhein-Westfalen","lng":6.7838782,"lat":51.2478087,"country":"Germany","city":"DŸsseldorf","crossStreet":"Spichernplatz"},"stats":{"checkinsCount":91,"usersCount":17},"verified":false,"name":"Irish Pub","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{"phone":"0211442651"}},"type":"checkin","shout":"Checkin NINJA!","createdAt":1300916478}}
03-23 22:41:18.052: DEBUG/JSONUtils(413): obj instanceof JSONObject-> {"id":"4d8a68fef607a09348e1fe86","_type":"checkin","notifications":"[{\"type\":\"message\",\"item\":{\"message\":\"OK! We've got you @ Irish Pub. You've been here 19 times.\"}},{\"type\":\"mayorship\",\"item\":{\"message\":\"Grischa S. is the Mayor of Irish Pub.\",\"daysBehind\":13,\"image\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/G4WY1VIAKUUY4SHO.jpg\",\"checkins\":15,\"type\":\"nochange\",\"user\":{\"id\":\"3221562\",\"gender\":\"male\",\"lastName\":\"S.\",\"firstName\":\"Grischa\",\"homeCity\":\"DŸsseldorf, Germany\",\"photo\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/G4WY1VIAKUUY4SHO.jpg\"}}},{\"type\":\"leaderboard\",\"item\":{\"total\":1,\"scores\":[{\"message\":\"Every check-in counts!\",\"icon\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/static\\\/img\\\/points\\\/defaultpointsicon2.png\",\"points\":1}],\"message\":\"Big check-in - now you're hot on yg's tail.\",\"leaderboard\":[{\"scores\":{\"checkinsCount\":11,\"recent\":30,\"max\":56},\"user\":{\"relationship\":\"friend\",\"id\":\"402676\",\"gender\":\"male\",\"firstName\":\"yg\",\"homeCity\":\"Antioch\",\"photo\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/LX1MW5CTKN411NFZ.jpg\"},\"rank\":1},{\"scores\":{\"checkinsCount\":7,\"recent\":24,\"max\":59},\"user\":{\"relationship\":\"self\",\"id\":\"5854980\",\"gender\":\"male\",\"firstName\":\"WareNinja.net\",\"homeCity\":\"DŸsseldorf, Deutschland\",\"photo\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/RV3FPN0H5I2XIHD1.png\"},\"rank\":2}]}},{\"type\":\"score\",\"item\":{\"total\":1,\"scores\":[{\"message\":\"Every check-in counts!\",\"icon\":\"\\\/img\\\/points\\\/defaultpointsicon2.png\",\"points\":1}]}}]","createdAt":1300916478,"timeZone":"Europe\/Berlin","venue":{"id":"4b686d1af964a520d7772be3","todos":{"count":0},"location":{"distance":0,"postalCode":"40476","address":"Spichernstrasse 2","state":"Nordrhein-Westfalen","lng":6.7838782,"lat":51.2478087,"country":"Germany","city":"DŸsseldorf","crossStreet":"Spichernplatz"},"stats":{"checkinsCount":91,"usersCount":17},"verified":false,"name":"Irish Pub","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{"phone":"0211442651"}},"type":"checkin","shout":"Checkin NINJA!"}

03-23 22:41:18.072: DEBUG/CheckinResultParserV2(413): 
json->{"id":"4d8a68fef607a09348e1fe86"
	,"_type":"checkin"
	,"notifications":"[
		{\"type\":\"message\"
			,\"item\":{\"message\":\"OK! We've got you @ Irish Pub. You've been here 19 times.\"}
		}
		,{\"type\":\"mayorship\"
			,\"item\":{\"message\":\"Grischa S. is the Mayor of Irish Pub.\",\"daysBehind\":13,\"image\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/G4WY1VIAKUUY4SHO.jpg\",\"checkins\":15,\"type\":\"nochange\",\"user\":{\"id\":\"3221562\",\"gender\":\"male\",\"lastName\":\"S.\",\"firstName\":\"Grischa\",\"homeCity\":\"DŸsseldorf, Germany\",\"photo\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/G4WY1VIAKUUY4SHO.jpg\"}
			}
		}
		,{\"type\":\"leaderboard\"
			,\"item\":{\"total\":1
				,\"scores\":[{\"message\":\"Every check-in counts!\",\"icon\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/static\\\/img\\\/points\\\/defaultpointsicon2.png\",\"points\":1}]
				,\"message\":\"Big check-in - now you're hot on yg's tail.\"
				,\"leaderboard\":[{\"scores\":{\"checkinsCount\":11,\"recent\":30,\"max\":56},\"user\":{\"relationship\":\"friend\",\"id\":\"402676\",\"gender\":\"male\",\"firstName\":\"yg\",\"homeCity\":\"Antioch\",\"photo\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/LX1MW5CTKN411NFZ.jpg\"},\"rank\":1}
					,{\"scores\":{\"checkinsCount\":7,\"recent\":24,\"max\":59},\"user\":{\"relationship\":\"self\",\"id\":\"5854980\",\"gender\":\"male\",\"firstName\":\"WareNinja.net\",\"homeCity\":\"DŸsseldorf, Deutschland\",\"photo\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/RV3FPN0H5I2XIHD1.png\"},\"rank\":2}
				]
			}
		}
		,{\"type\":\"score\"
			,\"item\":{\"total\":1,\"scores\":[{\"message\":\"Every check-in counts!\",\"icon\":\"\\\/img\\\/points\\\/defaultpointsicon2.png\",\"points\":1}]
			}
		}
	]"
,"createdAt":1300916478,"timeZone":"Europe\/Berlin"
,"venue":{"id":"4b686d1af964a520d7772be3","todos":{"count":0},"location":{"distance":0,"postalCode":"40476","address":"Spichernstrasse 2","state":"Nordrhein-Westfalen","lng":6.7838782,"lat":51.2478087,"country":"Germany","city":"DŸsseldorf","crossStreet":"Spichernplatz"},"stats":{"checkinsCount":91,"usersCount":17},"verified":false,"name":"Irish Pub","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{"phone":"0211442651"}}
,"type":"checkin","shout":"Checkin NINJA!"}


json->{"id":"4d8a7ef526a36ea89a4ff6ad","timeZone":"Europe\/Berlin","venue":{"id":"4b686d1af964a520d7772be3","todos":{"count":0},"location":{"distance":0,"postalCode":"40476","address":"Spichernstrasse 2","state":"Nordrhein-Westfalen","lng":6.7838782,"lat":51.2478087,"country":"Germany","city":"DŸsseldorf","crossStreet":"Spichernplatz"},"stats":{"checkinsCount":91,"usersCount":17},"verified":false,"name":"Irish Pub","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{"phone":"0211442651"}},"type":"checkin","notifications":[{"type":"message","item":{"message":"OK! We've got you @ Irish Pub. You've been here 20 times."}},{"type":"mayorship","item":{"daysBehind":13,"message":"Grischa S. is the Mayor of Irish Pub.","checkins":15,"image":"https:\/\/playfoursquare.s3.amazonaws.com\/userpix_thumbs\/G4WY1VIAKUUY4SHO.jpg","type":"nochange","user":{"id":"3221562","lastName":"S.","gender":"male","firstName":"Grischa","photo":"https:\/\/playfoursquare.s3.amazonaws.com\/userpix_thumbs\/G4WY1VIAKUUY4SHO.jpg","homeCity":"DŸsseldorf, Germany"}}},{"type":"leaderboard","item":{"message":"After that last check-in, you're closing the gap with yg!","scores":[],"total":0,"leaderboard":[{"scores":{"recent":30,"checkinsCount":11,"max":56},"user":{"id":"402676","relationship":"friend","gender":"male","firstName":"yg","photo":"https:\/\/playfoursquare.s3.amazonaws.com\/userpix_thumbs\/LX1MW5CTKN411NFZ.jpg","homeCity":"Antioch"},"rank":1},{"scores":{"recent":26,"checkinsCount":8,"max":59},"user":{"id":"5854980","relationship":"self","gender":"male","firstName":"WareNinja.net","photo":"https:\/\/playfoursquare.s3.amazonaws.com\/userpix_thumbs\/RV3FPN0H5I2XIHD1.png","homeCity":"DŸsseldorf, Deutschland"},"rank":2}]}},{"type":"score","item":{"scores":[],"total":0}}],"shout":"ninja test","createdAt":1300922101}
obj->CheckinResult [mBadges=null, mCreated=2011-03-24 00:15, mCreatedAt=1300922101
, mId=4d8a7ef526a36ea89a4ff6ad, mMarkup=null, mMayor=Mayor [mCheckins=15, mCount=13, mMessage=Grischa S. is the Mayor of Irish Pub., mType=nochange, mUser=User [mBadgeCount=0, mBadges=null, mCheckin=null, mCheckinCount=0, mCreated=null, mEmail=null, mFacebook=null, mFirstname=Grischa, mFollowerCount=0, mFriendCount=0, mFriendsInCommon=null, mFriendstatus=null, mGender=male, mHometown=DŸsseldorf, Germany, mId=3221562, mLastname=S., mMayorCount=0, mMayorships=null, mPhone=null, mPhoto=https://playfoursquare.s3.amazonaws.com/userpix_thumbs/G4WY1VIAKUUY4SHO.jpg, mSettings=null, mTipCount=0, mTodoCount=0, mTwitter=null, mTypes=null]]
, mMessage=OK! We've got you @ Irish Pub. You've been here 20 times.
, mScoring=null
, mShout=ninja test, mSpecials=null, mTimeZone=Europe/Berlin
, mVenue=Venue [mAddress=Spichernstrasse 2, mCategory=Category [mChildCategories=[], mFullPathName=["Nightlife Spots"], mIconUrl=http://foursquare.com/img/categories/nightlife/default.png, mId=4bf58dd8d48988d11b941735, mNodeName=Pubs, isPrimary=true], mCategories=[Category [mChildCategories=[], mFullPathName=["Nightlife Spots"], mIconUrl=http://foursquare.com/img/categories/nightlife/default.png, mId=4bf58dd8d48988d11b941735, mNodeName=Pubs, isPrimary=true]], mCheckins=null, mCity=DŸsseldorf, mCityid=null, mCrossstreet=Spichernplatz, mDistance=0, mGeolat=51.2478087, mGeolong=6.7838782, mHasTodo=false, mId=4b686d1af964a520d7772be3, mName=Irish Pub, mPhone=2.11442651E8, mSpecials=null, mState=Nordrhein-Westfalen, mStats=null, mTags=null, mTips=null, mTodos=null, mTwitter=null, mZip=40476]
]

 */
        obj.setId(json.has("id")?json.getString("id"):"");
        obj.setCreatedAt(json.has("createdAt")?(json.getLong("createdAt")):0L);
        obj.setTimeZone(json.has("timeZone")?json.getString("timeZone"):"");
        obj.setShout(json.has("shout")?json.getString("shout"):"");
        if (json.has("venue")) {
            obj.setVenue(new VenueParserV2().parse(json.getJSONObject("venue")));
        }
        
        if (json.has("notifications")) {
        	
        	// parse JSONArray from notifications!!!
        	JSONArray notifs = json.getJSONArray("notifications");
        	
        	//Object obj1;
        	JSONObject json1;
        	JSONObject json2;
        	String type = "";
        	for (int i=0; i<notifs.length();i++) {
        		/*obj1 = notifs.get(i);
        		if (obj1 instanceof JSONArray) {
            	} 
                else {
            	}*/
        		
        		json1 = notifs.getJSONObject(i);
        		type = json1.has("type")?json1.getString("type"):"";
        		if (!TextUtils.isEmpty(type) && json1.has("item")) {
        			json2 = json1.getJSONObject("item");
        			if (type.equalsIgnoreCase("message")) {
        				obj.setMessage( json2.has("message")?json2.getString("message"):"" );
        			}
        			else if (type.equalsIgnoreCase("mayorship")) {
        				obj.setMayor(new MayorParserV2().parse(json2));
        			}
        			else if (type.equalsIgnoreCase("leaderboard")) {
        				// TODO: add LeaderBoard obj and parser!!!
        			}
        			else if (type.equalsIgnoreCase("score")) {
        				
        				if (json2.has("scores")) {
        		            obj.setScoring(
        		                new GroupParserV2(
        		                    new ScoreParserV2()).parse(json2.getJSONArray("scores")));
        		        } 
        			}
        		}
        	}
        	
                
        }
        
        /*
        if (json.has("badges")) {
            obj.setBadges(
                new GroupParser(
                    new BadgeParser()).parse(json.getJSONArray("badges")));
        }
        if (json.has("markup")) {
            obj.setMarkup(json.getString("markup"));
        }
        
        if (json.has("specials")) {
            obj.setSpecials(
                new GroupParser(
                    new SpecialParser()).parse(json.getJSONArray("specials")));
        } 
        */
           
        
        if(LOGGING.DEBUG)Log.d(TAG, "obj->"+obj);
        
        return obj;
    }
}