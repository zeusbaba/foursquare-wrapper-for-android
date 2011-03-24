/**
 * Copyright 2011 WareNinja
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.wareninja.android.commonutils.foursquareV2.LOGGING;
import com.wareninja.android.commonutils.foursquareV2.types.User;


/**
 * @date Mar, 2011
 * @author YG (yg@wareninja.com)
 */
public class UserParserV2 extends AbstractParser<User> {

	private static final String TAG = "UserParserV2";
	
    @Override
    public User parse(JSONObject json) throws JSONException {
    	
    	
    	User user = new User();
        // parse according to V2 endpoint!!!
    	if(LOGGING.DEBUG)Log.d(TAG, "json->" + json);
/*
{"meta":{"code":200}
,"response":{
	"user":{"id":"5854980","firstName":"WareNinja.net","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/RV3FPN0H5I2XIHD1.png","gender":"male","homeCity":"DŸsseldorf, Deutschland","relationship":"self","type":"user","pings":false,"contact":{"email":"yg@wareninja.com","twitter":"wareninja"},"badges":{"count":3},"mayorships":{"count":0,"items":[]},"checkins":{"count":56,"items":[{"id":"4d850643f9f3a1cdd9e5c264","createdAt":1300563523,"type":"checkin","timeZone":"Europe/Berlin","venue":{"id":"4b5a0ecbf964a5208faa28e3","name":"Brauerei zur Uel","contact":{},"location":{"address":"Ratinger Str. 16","city":"DŸsseldorf","state":"Germany","postalCode":"40213","lat":51.2293602,"lng":6.7745873},"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":249,"usersCount":87},"todos":{"count":0}}}]},"friends":{"count":1,"groups":[{"type":"others","name":"other friends","count":1,"items":[]}]},"following":{"count":0},"requests":{"count":0},"tips":{"count":0},"todos":{"count":1},"scores":{"recent":18,"max":59,"checkinsCount":5}}}}
	

 */

    	/* NO NEED!!!
    	if(LOGGING.DEBUG)Log.d("UserParser", "json1->" + json);
    	if (json.has("meta") && json.has("response")) {// means V2 response
    		String temp = json.getString("response");
    		json = new JSONObject(temp);
    		if (json.has("user")) {
    			temp = json.getString("user");
    			json = new JSONObject(temp);
    		}
    	}
    	if(LOGGING.DEBUG)Log.d("UserParser", "json2->" + json);
    	*/
        
    	/*
        executed HttpRequest for: https://api.foursquare.com/v2/users/self?USER_ID=self&oauth_token=3IUADH4MKTA5Y4LF0QTQPGLNXWT0JSDYP00GALFOO5I3V3VC

{"user":{
	"checkins":{"count":56,"items":[{"id":"4d850643f9f3a1cdd9e5c264","timeZone":"Europe\/Berlin","venue":{"id":"4b5a0ecbf964a5208faa28e3","todos":{"count":0},"location":{"state":"Germany","lng":6.7745873,"postalCode":"40213","address":"Ratinger Str. 16","lat":51.2293602,"city":"Dusseldorf"},"stats":{"checkinsCount":249,"usersCount":87},"verified":false,"name":"Brauerei zur Uel","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{}},"type":"checkin","createdAt":1300563523}]}
	,"todos":{"count":1}
	,"pings":false
	,"type":"user"
	,"contact":{"twitter":"wareninja","email":"yg@wareninja.com"}
	,"photo":"https:\/\/playfoursquare.s3.amazonaws.com\/userpix_thumbs\/RV3FPN0H5I2XIHD1.png"
	,"relationship":"self"
	,"id":"5854980"
	,"requests":{"count":0}
	,"badges":{"count":3}
	,"following":{"count":0}
	,"friends":{"count":1,"groups":[{"count":1,"type":"others","items":[],"name":"other friends"}]}
	,"scores":{"checkinsCount":5,"recent":18,"max":59}
	,"mayorships":{"count":0,"items":[]}
	,"homeCity":"Dusseldorf, Deutschland"
	,"gender":"male"
	,"tips":{"count":0}
	,"firstName":"WareNinja.net"
	}
}

json->
{"user":{
"checkins":{"count":622,"items":[{"id":"4d8529715ad3a09393ccdffd","timeZone":"Europe\/Berlin","venue":{"id":"4b64bd87f964a52077cc2ae3","todos":{"count":0},"location":{"state":"Nordrhein-Westfalen","lng":6.7729198,"postalCode":"40213","address":"Kurze Strasse 12","lat":51.2268897,"city":"Dusseldorf"},"stats":{"checkinsCount":79,"usersCount":39},"verified":false,"name":"Melody Bar","categories":[{"id":"4bf58dd8d48988d11e941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/cocktails.png","parents":["Nightlife Spots"],"primary":true,"name":"Cocktails Bars"}],"contact":{}},"type":"checkin","createdAt":1300572529}]}
,"todos":{"count":0},"pings":false,"type":"user"
,"contact":{"email":"yilmaz@guleryuz.net","facebook":"596017921"}
,"photo":"https:\/\/playfoursquare.s3.amazonaws.com\/userpix_thumbs\/LX1MW5CTKN411NFZ.jpg","relationship":"self"
,"id":"402676"
,"requests":{"count":3},"badges":{"count":14},"following":{"count":0},"friends":{"count":45,"groups":[{"count":45,"type":"others","items":[],"name":"other friends"}]},"scores":{"checkinsCount":14,"recent":49,"max":56},"mayorships":{"count":7,"items":[{"id":"4c13cc83a1010f477e464b18","todos":{"count":0},"location":{"state":"North Rhine-Westphal","lng":6.7874551,"address":"Geistenstr","lat":51.2471131,"city":"Dusseldorf"},"stats":{"checkinsCount":62,"usersCount":1},"verified":false,"name":"YG castle :)","categories":[{"id":"4bf58dd8d48988d103941735","icon":"http:\/\/foursquare.com\/img\/categories\/building\/home.png","parents":["Homes, Work, Others"],"primary":true,"name":"Homes"}],"contact":{}},{"id":"4b067ef7f964a5205aec22e3","todos":{"count":0},"location":{"state":"Germany","lng":13.3092256,"postalCode":"10629","address":"Giesebrechtstr. 4","lat":52.5024275,"city":"Berlin"},"stats":{"checkinsCount":33,"usersCount":19},"verified":false,"name":"Die Kurbel","categories":[{"id":"4bf58dd8d48988d17f941735","icon":"http:\/\/foursquare.com\/img\/categories\/arts_entertainment\/movietheater.png","parents":["Arts & Entertainment"],"primary":true,"name":"Movie Theaters"}],"contact":{}},{"id":"4bd30b6077b29c74adc48f82","todos":{"count":0},"location":{"lng":36.160819,"lat":36.19969},"stats":{"checkinsCount":12,"usersCount":9},"verified":false,"name":"Ortodoks Kilisesi","categories":[{"id":"4bf58dd8d48988d1f6931735","icon":"http:\/\/foursquare.com\/img\/categories\/travel\/default.png","parents":["Travel Spots"],"primary":true,"name":"General Travel"}],"contact":{}},{"id":"4b7d9c25f964a52093c92fe3","todos":{"count":0},"location":{"lng":0.111802,"lat":52.213334},"stats":{"checkinsCount":41,"usersCount":19},"verified":false,"name":"Isaac Newton","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{}},{"id":"4bcace94b6c49c74312c9191","todos":{"count":0},"location":{"state":"Hatay","lng":35.946144,"lat":36.082159,"city":"Samandag"},"stats":{"checkinsCount":12,"usersCount":8},"verified":false,"name":"Samandagi","categories":[],"contact":{}},{"id":"4d08139684ec224bd0d12762","todos":{"count":0},"location":{"state":"Budapest","lng":19.0682792,"postalCode":"1072","address":"Ak‡cfa utca 13","lat":47.4980675,"city":"Budapest","crossStreet":"Doh‡ny utca"},"stats":{"checkinsCount":47,"usersCount":31},"verified":false,"name":"Old Man's Music Pub Budapest","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"},{"id":"4bf58dd8d48988d1d9941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/musicvenue.png","parents":["Nightlife Spots"],"name":"Music Venues"},{"id":"4bf58dd8d48988d147941735","icon":"http:\/\/foursquare.com\/img\/categories\/food\/default.png","parents":["Food"],"name":"Diners"}],"contact":{"phone":"003613227645"}},{"id":"4c66d2f58e9120a114fcd864","todos":{"count":0},"location":{"lng":6.80107,"lat":51.210604},"stats":{"checkinsCount":5,"usersCount":1}

03-20 23:21:02.424: DEBUG/JSONUtils(1879): http response: {"meta":{"code":200},"response":{"user":{"id":"402676","firstName":"yg","photo":"https://playfoursquare.s3.amazonaws.com/userpix_thumbs/LX1MW5CTKN411NFZ.jpg","gender":"male","homeCity":"Antioch","relationship":"self","type":"user","pings":false,"contact":{"email":"yilmaz@guleryuz.net","facebook":"596017921"},"badges":{"count":14},"mayorships":{"count":7,"items":[{"id":"4c13cc83a1010f477e464b18","name":"YG castle :)","contact":{},"location":{"address":"Geistenstr","city":"DŸsseldorf","state":"North Rhine-Westphal","lat":51.2471131,"lng":6.7874551},"categories":[{"id":"4bf58dd8d48988d103941735","name":"Homes","icon":"http://foursquare.com/img/categories/building/home.png","parents":["Homes, Work, Others"],"primary":true}],"verified":false,"stats":{"checkinsCount":62,"usersCount":1},"todos":{"count":0}},{"id":"4b067ef7f964a5205aec22e3","name":"Die Kurbel","contact":{},"location":{"address":"Giesebrechtstr. 4","city":"Berlin","state":"Germany","postalCode":"10629","lat":52.5024275,"lng":13.3092256},"categories":[{"id":"4bf58dd8d48988d17f941735","name":"Movie Theaters","icon":"http://foursquare.com/img/categories/arts_entertainment/movietheater.png","parents":["Arts & Entertainment"],"primary":true}],"verified":false,"stats":{"checkinsCount":33,"usersCount":19},"todos":{"count":0}},{"id":"4bd30b6077b29c74adc48f82","name":"Ortodoks Kilisesi","contact":{},"location":{"lat":36.19969,"lng":36.160819},"categories":[{"id":"4bf58dd8d48988d1f6931735","name":"General Travel","icon":"http://foursquare.com/img/categories/travel/default.png","parents":["Travel Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":12,"usersCount":9},"todos":{"count":0}},{"id":"4b7d9c25f964a52093c92fe3","name":"Isaac Newton","contact":{},"location":{"lat":52.213334,"lng":0.111802},"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":41,"usersCount":19},"todos":{"count":0}},{"id":"4bcace94b6c49c74312c9191","name":"Samandagi","contact":{},"location":{"city":"Samanda","state":"Hatay","lat":36.082159,"lng":35.946144},"categories":[],"verified":false,"stats":{"checkinsCount":12,"usersCount":8},"todos":{"count":0}},{"id":"4d08139684ec224bd0d12762","name":"Old Man's Music Pub Budapest","contact":{"phone":"003613227645"},"location":{"address":"Ak‡cfa utca 13","crossStreet":"Doh‡ny utca","city":"Budapest","state":"Budapest","postalCode":"1072","lat":47.4980675,"lng":19.0682792},"categories":[{"id":"4bf58dd8d48988d11b941735","name":"Pubs","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true},{"id":"4bf58dd8d48988d1d9941735","name":"Music Venues","icon":"http://foursquare.com/img/categories/nightlife/musicvenue.png","parents":["Nightlife Spots"]},{"id":"4bf58dd8d48988d147941735","name":"Diners","icon":"http://foursquare.com/img/categories/food/default.png","parents":["Food"]}],"verified":false,"stats":{"checkinsCount":47,"usersCount":31},"todos":{"count":0}},{"id":"4c66d2f58e9120a114fcd864","name":"Party ppl","contact":{},"location":{"lat":51.210604,"lng":6.80107},"categories":[{"id":"4bf58dd8d48988d11a941735","name":"Other Nightlife","icon":"http://foursquare.com/img/categories/nightlife/default.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stats":{"checkinsCount":5,"usersCount":1},"todos":{"count":0}}]},"checkins":{"count":622,"items":[{"id":"4d8529715ad3a09393ccdffd","createdAt":1300572529,"type":"checkin","timeZone":"Europe/Berlin","venue":{"id":"4b64bd87f964a52077cc2ae3","name":"Melody Bar","contact":{},"location":{"address":"Kurze Strasse 12","city":"DŸsseldorf","state":"Nordrhein-Westfalen","postalCode":"40213","lat":51.2268897,"lng":6.7729198},"categories":[{"id":"4bf58dd8d48988d11e941735","name":"Cocktails Bars","icon":"http://foursquare.com/img/categories/nightlife/cocktails.png","parents":["Nightlife Spots"],"primary":true}],"verified":false,"stat
03-20 23:21:02.504: DEBUG/UserParserV2(1879): json->{"user":{"checkins":{"count":622,"items":[{"id":"4d8529715ad3a09393ccdffd","timeZone":"Europe\/Berlin","venue":{"id":"4b64bd87f964a52077cc2ae3","todos":{"count":0},"location":{"state":"Nordrhein-Westfalen","lng":6.7729198,"postalCode":"40213","address":"Kurze Strasse 12","lat":51.2268897,"city":"DŸsseldorf"},"stats":{"checkinsCount":79,"usersCount":39},"verified":false,"name":"Melody Bar","categories":[{"id":"4bf58dd8d48988d11e941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/cocktails.png","parents":["Nightlife Spots"],"primary":true,"name":"Cocktails Bars"}],"contact":{}},"type":"checkin","createdAt":1300572529}]},"todos":{"count":0},"pings":false,"type":"user","contact":{"email":"yilmaz@guleryuz.net","facebook":"596017921"},"photo":"https:\/\/playfoursquare.s3.amazonaws.com\/userpix_thumbs\/LX1MW5CTKN411NFZ.jpg","relationship":"self","id":"402676","requests":{"count":3},"badges":{"count":14},"following":{"count":0},"friends":{"count":45,"groups":[{"count":45,"type":"others","items":[],"name":"other friends"}]},"scores":{"checkinsCount":14,"recent":49,"max":56},"mayorships":{"count":7,"items":[{"id":"4c13cc83a1010f477e464b18","todos":{"count":0},"location":{"state":"North Rhine-Westphal","lng":6.7874551,"address":"Geistenstr","lat":51.2471131,"city":"DŸsseldorf"},"stats":{"checkinsCount":62,"usersCount":1},"verified":false,"name":"YG castle :)","categories":[{"id":"4bf58dd8d48988d103941735","icon":"http:\/\/foursquare.com\/img\/categories\/building\/home.png","parents":["Homes, Work, Others"],"primary":true,"name":"Homes"}],"contact":{}},{"id":"4b067ef7f964a5205aec22e3","todos":{"count":0},"location":{"state":"Germany","lng":13.3092256,"postalCode":"10629","address":"Giesebrechtstr. 4","lat":52.5024275,"city":"Berlin"},"stats":{"checkinsCount":33,"usersCount":19},"verified":false,"name":"Die Kurbel","categories":[{"id":"4bf58dd8d48988d17f941735","icon":"http:\/\/foursquare.com\/img\/categories\/arts_entertainment\/movietheater.png","parents":["Arts & Entertainment"],"primary":true,"name":"Movie Theaters"}],"contact":{}},{"id":"4bd30b6077b29c74adc48f82","todos":{"count":0},"location":{"lng":36.160819,"lat":36.19969},"stats":{"checkinsCount":12,"usersCount":9},"verified":false,"name":"Ortodoks Kilisesi","categories":[{"id":"4bf58dd8d48988d1f6931735","icon":"http:\/\/foursquare.com\/img\/categories\/travel\/default.png","parents":["Travel Spots"],"primary":true,"name":"General Travel"}],"contact":{}},{"id":"4b7d9c25f964a52093c92fe3","todos":{"count":0},"location":{"lng":0.111802,"lat":52.213334},"stats":{"checkinsCount":41,"usersCount":19},"verified":false,"name":"Isaac Newton","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{}},{"id":"4bcace94b6c49c74312c9191","todos":{"count":0},"location":{"state":"Hatay","lng":35.946144,"lat":36.082159,"city":"Samandag"},"stats":{"checkinsCount":12,"usersCount":8},"verified":false,"name":"Samandagi","categories":[],"contact":{}},{"id":"4d08139684ec224bd0d12762","todos":{"count":0},"location":{"state":"Budapest","lng":19.0682792,"postalCode":"1072","address":"Ak‡cfa utca 13","lat":47.4980675,"city":"Budapest","crossStreet":"Doh‡ny utca"},"stats":{"checkinsCount":47,"usersCount":31},"verified":false,"name":"Old Man's Music Pub Budapest","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"},{"id":"4bf58dd8d48988d1d9941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/musicvenue.png","parents":["Nightlife Spots"],"name":"Music Venues"},{"id":"4bf58dd8d48988d147941735","icon":"http:\/\/foursquare.com\/img\/categories\/food\/default.png","parents":["Food"],"name":"Diners"}],"contact":{"phone":"003613227645"}},{"id":"4c66d2f58e9120a114fcd864","todos":{"count":0},"location":{"lng":6.80107,"lat":51.210604},"stats":{"checkinsCount":5,"usersCount":1}
03-20 23:21:02.584: DEBUG/JSONUtils(1879): JSONException:org.json.JSONException: Value {"count":0} at todos of type org.json.JSONObject cannot be converted to JSONArray|check/parse IF it is OAuth token!

         */
    	
		if (json.has("user")) {
			json = new JSONObject( json.getString("user") );
		}
    	
    	JSONObject json1;
    	
    	user.setId(json.has("id")?json.getString("id"):"");
    	if (json.has("contact")) {
    		json1 = json.getJSONObject("contact");
    		
            user.setEmail(json1.has("email")?json1.getString("email"):"");
            user.setFacebook(json1.has("facebook")?json1.getString("facebook"):"");
            user.setTwitter(json1.has("twitter")?json1.getString("twitter"):"");
    	}
    	user.setPhoto(json.has("photo")?json.getString("photo"):"");
    	user.setFirstname(json.has("firstName")?json.getString("firstName"):"");
        user.setLastname(json.has("lastName")?json.getString("lastName"):"");
        user.setGender(json.has("gender")?json.getString("gender"):"");
        user.setHometown(json.has("homeCity")?json.getString("homeCity"):"");
        
    	if (json.has("checkins")) {
//"checkins":{"count":56,"items":[{"id":"4d850643f9f3a1cdd9e5c264","timeZone":"Europe\/Berlin","venue":{"id":"4b5a0ecbf964a5208faa28e3","todos":{"count":0},"location":{"state":"Germany","lng":6.7745873,"postalCode":"40213","address":"Ratinger Str. 16","lat":51.2293602,"city":"Dusseldorf"},"stats":{"checkinsCount":249,"usersCount":87},"verified":false,"name":"Brauerei zur Uel","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{}},"type":"checkin","createdAt":1300563523}]}    		
    		json1 = json.getJSONObject("checkins");
    		user.setCheckinCount(json1.has("count")?json1.getInt("count"):0);
    		
    		// TODO: parse Checkins!!! (not sure it makes sense to put them User????)
    	}
    	if (json.has("todos")) {
    		// ,"todos":{"count":1}
    		json1 = json.getJSONObject("todos");
    		user.setTodoCount(json1.getInt("count"));
    	}
    	if (json.has("mayorships")) {
    		// ,"mayorships":{"count":0,"items":[]}
    		json1 = json.getJSONObject("mayorships");
    		user.setMayorCount(json1.getInt("count"));
    		
    		/*// NO NEED to overload User object, we can load this data seperately!
    		if (json1.has("items")) {
    			user.setMayorships(
    	                new GroupParser(
    	                    new VenueParserV2()).parse(json1.getJSONArray("items")));
    		}
    		*/
    	}
    	if (json.has("badges")) {
    		// ,"badges":{"count":3}
    		json1 = json.getJSONObject("badges");
    		user.setBadgeCount(json1.getInt("count"));
    		
    		// TODO: parse Badges!!!
    		/*if (json1.has("items")) {
    			user.setBadges(
    	                new GroupParser(
    	                    new BadgeParser()).parse(json.getJSONArray("items")));
    		}*/
    	}
        /*
        if (json.has("checkin")) {
            user.setCheckin(new CheckinParser().parse(json.getJSONObject("checkin")));
        }
        if (json.has("checkincount")) {
            user.setCheckinCount(json.getInt("checkincount"));
        }
        if (json.has("created")) {
            user.setCreated(json.getString("created"));
        }
        if (json.has("followercount")) {
            user.setFollowerCount(json.getInt("followercount"));
        }
        if (json.has("friendcount")) {
            user.setFriendCount(json.getInt("friendcount"));
        }
        if (json.has("friendsincommon")) {
            user.setFriendsInCommon(
                new GroupParser(
                    new UserParserV2()).parse(json.getJSONArray("friendsincommon")));
        }  
        if (json.has("friendstatus")) {
            user.setFriendstatus(json.getString("friendstatus"));
        }
        if (json.has("phone")) {
            user.setPhone(json.getString("phone"));
        }
        if (json.has("settings")) {
            user.setSettings(new SettingsParser().parse(json.getJSONObject("settings")));
        } 
        if (json.has("tipcount")) {
            user.setTipCount(json.getInt("tipcount"));
        }
        if (json.has("types")) {
            user.setTypes(new TypesParser().parseAsJSONArray(json.getJSONArray("types")));
        }
        */

    	if(LOGGING.DEBUG)Log.d(TAG, "user->"+user);
/*
json->{"user":{"checkins":{"count":622,"items":[{"id":"4d8529715ad3a09393ccdffd","timeZone":"Europe\/Berlin","venue":{"id":"4b64bd87f964a52077cc2ae3","todos":{"count":0},"location":{"state":"Nordrhein-Westfalen","lng":6.7729198,"postalCode":"40213","address":"Kurze Strasse 12","lat":51.2268897,"city":"DŸsseldorf"},"stats":{"checkinsCount":79,"usersCount":39},"verified":false,"name":"Melody Bar","categories":[{"id":"4bf58dd8d48988d11e941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/cocktails.png","parents":["Nightlife Spots"],"primary":true,"name":"Cocktails Bars"}],"contact":{}},"type":"checkin","createdAt":1300572529}]},"todos":{"count":0},"pings":false,"type":"user","contact":{"email":"yilmaz@guleryuz.net","facebook":"596017921"},"photo":"https:\/\/playfoursquare.s3.amazonaws.com\/userpix_thumbs\/LX1MW5CTKN411NFZ.jpg","relationship":"self","id":"402676","requests":{"count":3},"badges":{"count":14},"following":{"count":0},"friends":{"count":45,"groups":[{"count":45,"type":"others","items":[],"name":"other friends"}]},"scores":{"checkinsCount":14,"recent":49,"max":56},"mayorships":{"count":7,"items":[{"id":"4c13cc83a1010f477e464b18","todos":{"count":0},"location":{"state":"North Rhine-Westphal","lng":6.7874551,"address":"Geistenstr","lat":51.2471131,"city":"DŸsseldorf"},"stats":{"checkinsCount":62,"usersCount":1},"verified":false,"name":"YG castle :)","categories":[{"id":"4bf58dd8d48988d103941735","icon":"http:\/\/foursquare.com\/img\/categories\/building\/home.png","parents":["Homes, Work, Others"],"primary":true,"name":"Homes"}],"contact":{}},{"id":"4b067ef7f964a5205aec22e3","todos":{"count":0},"location":{"state":"Germany","lng":13.3092256,"postalCode":"10629","address":"Giesebrechtstr. 4","lat":52.5024275,"city":"Berlin"},"stats":{"checkinsCount":33,"usersCount":19},"verified":false,"name":"Die Kurbel","categories":[{"id":"4bf58dd8d48988d17f941735","icon":"http:\/\/foursquare.com\/img\/categories\/arts_entertainment\/movietheater.png","parents":["Arts & Entertainment"],"primary":true,"name":"Movie Theaters"}],"contact":{}},{"id":"4bd30b6077b29c74adc48f82","todos":{"count":0},"location":{"lng":36.160819,"lat":36.19969},"stats":{"checkinsCount":12,"usersCount":9},"verified":false,"name":"Ortodoks Kilisesi","categories":[{"id":"4bf58dd8d48988d1f6931735","icon":"http:\/\/foursquare.com\/img\/categories\/travel\/default.png","parents":["Travel Spots"],"primary":true,"name":"General Travel"}],"contact":{}},{"id":"4b7d9c25f964a52093c92fe3","todos":{"count":0},"location":{"lng":0.111802,"lat":52.213334},"stats":{"checkinsCount":41,"usersCount":19},"verified":false,"name":"Isaac Newton","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"}],"contact":{}},{"id":"4bcace94b6c49c74312c9191","todos":{"count":0},"location":{"state":"Hatay","lng":35.946144,"lat":36.082159,"city":"Samandag"},"stats":{"checkinsCount":12,"usersCount":8},"verified":false,"name":"Samandagi","categories":[],"contact":{}},{"id":"4d08139684ec224bd0d12762","todos":{"count":0},"location":{"state":"Budapest","lng":19.0682792,"postalCode":"1072","address":"Ak‡cfa utca 13","lat":47.4980675,"city":"Budapest","crossStreet":"Doh‡ny utca"},"stats":{"checkinsCount":48,"usersCount":32},"verified":false,"name":"Old Man's Music Pub Budapest","categories":[{"id":"4bf58dd8d48988d11b941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/default.png","parents":["Nightlife Spots"],"primary":true,"name":"Pubs"},{"id":"4bf58dd8d48988d1d9941735","icon":"http:\/\/foursquare.com\/img\/categories\/nightlife\/musicvenue.png","parents":["Nightlife Spots"],"name":"Music Venues"},{"id":"4bf58dd8d48988d147941735","icon":"http:\/\/foursquare.com\/img\/categories\/food\/default.png","parents":["Food"],"name":"Diners"}],"contact":{"phone":"003613227645"}},{"id":"4c66d2f58e9120a114fcd864","todos":{"count":0},"location":{"lng":6.80107,"lat":51.210604},"stats":{"checkinsCount":5,"usersCount":1}
user->User [mBadgeCount=14, mBadges=null, mCheckin=null, mCheckinCount=622, mCreated=null, mEmail=yilmaz@guleryuz.net, mFacebook=596017921, mFirstname=yg, mFollowerCount=0, mFriendCount=0, mFriendsInCommon=null, mFriendstatus=null, mGender=male, mHometown=Antioch, mId=402676, mLastname=, mMayorCount=7, mMayorships=[Venue [mAddress=Geistenstr, mCategory=null, mCheckins=null, mCity=DŸsseldorf, mCityid=null, mCrossstreet=, mDistance=null, mGeolat=51.2471131, mGeolong=6.7874551, mHasTodo=false, mId=4c13cc83a1010f477e464b18, mName=YG castle :), mPhone=, mSpecials=null, mState=North Rhine-Westphal, mStats=null, mTags=null, mTips=null, mTodos=null, mTwitter=null, mZip=], Venue [mAddress=Giesebrechtstr. 4, mCategory=null, mCheckins=null, mCity=Berlin, mCityid=null, mCrossstreet=, mDistance=null, mGeolat=52.5024275, mGeolong=13.3092256, mHasTodo=false, mId=4b067ef7f964a5205aec22e3, mName=Die Kurbel, mPhone=, mSpecials=null, mState=Germany, mStats=null, mTags=null, mTips=null, mTodos=null, mTwitter=null, mZip=10629], Venue [mAddress=, mCategory=null, mCheckins=null, mCity=, mCityid=null, mCrossstreet=, mDistance=null, mGeolat=36.19969, mGeolong=36.160819, mHasTodo=false, mId=4bd30b6077b29c74adc48f82, mName=Ortodoks Kilisesi, mPhone=, mSpecials=null, mState=, mStats=null, mTags=null, mTips=null, mTodos=null, mTwitter=null, mZip=], Venue [mAddress=, mCategory=null, mCheckins=null, mCity=, mCityid=null, mCrossstreet=, mDistance=null, mGeolat=52.213334, mGeolong=0.111802, mHasTodo=false, mId=4b7d9c25f964a52093c92fe3, mName=Isaac Newton, mPhone=, mSpecials=null, mState=, mStats=null, mTags=null, mTips=null, mTodos=null, mTwitter=null, mZip=], Venue [mAddress=, mCategory=null, mCheckins=null, mCity=Samandag, mCityid=null, mCrossstreet=, mDistance=null, mGeolat=36.082159, mGeolong=35.946144, mHasTodo=false, mId=4bcace94b6c49c74312c9191, mName=Samandagi, mPhone=, mSpecials=null, mState=Hatay, mStats=null, mTags=null, mTips=null, mTodos=null, mTwitter=null, mZip=], Venue [mAddress=Ak‡cfa utca 13, mCategory=null, mCheckins=null, mCity=Budapest, mCityid=null, mCrossstreet=Doh‡ny utca, mDistance=null, mGeolat=47.4980675, mGeolong=19.0682792, mHasTodo=false, mId=4d08139684ec224bd0d12762, mName=Old Man's Music Pub Budapest, mPhone=3.613227645E9, mSpecials=null, mState=Budapest, mStats=null, mTags=null, mTips=null, mTodos=null, mTwitter=null, mZip=1072], Venue [mAddress=, mCategory=null, mCheckins=null, mCity=, mCityid=null, mCrossstreet=, mDistance=null, mGeolat=51.210604, mGeolong=6.80107, mHasTodo=false, mId=4c66d2f58e9120a114fcd864, mName=Party ppl, mPhone=, mSpecials=null, mState=, mStats=null, mTags=null, mTips=null, mTodos=null, mTwitter=null, mZip=]], mPhone=null, mPhoto=https://playfoursquare.s3.amazonaws.com/userpix_thumbs/LX1MW5CTKN411NFZ.jpg, mSettings=null, mTipCount=0, mTodoCount=0, mTwitter=, mTypes=null]

 */
        return user;
    }
    
    //@Override
    //public String getObjectName() {
    //    return "user";
    //}
}