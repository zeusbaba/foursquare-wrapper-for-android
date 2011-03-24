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


package com.wareninja.android.commonutils.foursquareV2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.text.TextUtils;
import android.util.Log;

import com.wareninja.android.commonutils.foursquareV2.WUtils;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareCredentialsException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareError;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareParseException;
import com.wareninja.android.commonutils.foursquareV2.http.AbstractHttpApi;
import com.wareninja.android.commonutils.foursquareV2.http.HttpApi;
import com.wareninja.android.commonutils.foursquareV2.http.HttpApiWithOAuthV2;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.CategoryParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.CheckinParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.CheckinParserV2;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.CheckinResultParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.CheckinResultParserV2;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.CityParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.CredentialsParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.FriendInvitesResultParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.GroupParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.GroupParserV2;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.ResponseParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.SettingsParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.TipParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.TodoParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.UserParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.UserParserV2;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.VenueParser;
import com.wareninja.android.commonutils.foursquareV2.jsonparsers.VenueParserV2;
import com.wareninja.android.commonutils.foursquareV2.types.Category;
import com.wareninja.android.commonutils.foursquareV2.types.Checkin;
import com.wareninja.android.commonutils.foursquareV2.types.CheckinResult;
import com.wareninja.android.commonutils.foursquareV2.types.City;
import com.wareninja.android.commonutils.foursquareV2.types.Credentials;
import com.wareninja.android.commonutils.foursquareV2.types.FriendInvitesResult;
import com.wareninja.android.commonutils.foursquareV2.types.Group;
import com.wareninja.android.commonutils.foursquareV2.types.Response;
import com.wareninja.android.commonutils.foursquareV2.types.Settings;
import com.wareninja.android.commonutils.foursquareV2.types.Tip;
import com.wareninja.android.commonutils.foursquareV2.types.Todo;
import com.wareninja.android.commonutils.foursquareV2.types.User;
import com.wareninja.android.commonutils.foursquareV2.types.Venue;
import com.wareninja.android.commonutils.foursquareV2.util.JSONUtils;

/**
 * @author YG (yg@wareninja.com)
 */
class FoursquareHttpApiV2 {
    private static final Logger LOG = Logger
            .getLogger(FoursquareHttpApiV2.class.getCanonicalName());
    private static final boolean DEBUG = FoursquareV2.DEBUG;
    private static final String TAG = "FoursquareHttpApiV2";

    private static final String DATATYPE = ".json";
    
    
    // V2 endpoints
    private static final String URL_API_AUTHENTICATE = "/oauth2/authenticate";
    private static final String URL_API_ACCESSTOKEN = "/oauth2/access_token";
    
    // %s -> self for current user, USER_ID for others
    private static final String URL_API_USERS_ASPECTS_SELF = "/users/self";
    private static final String URL_API_USERS_ASPECTS_DETAILS = "/users/%s";
    private static final String URL_API_USERS_ASPECTS_CHECKINS = "/users/%s/checkins";
    private static final String URL_API_USERS_ASPECTS_BADGES = "/users/%s/badges";
    private static final String URL_API_USERS_ASPECTS_FRIENDS = "/users/%s/friends";
    private static final String URL_API_USERS_ASPECTS_TIPS = "/users/%s/tips";
    private static final String URL_API_USERS_ASPECTS_TODOS = "/users/%s/todos";
    private static final String URL_API_USERS_ASPECTS_VENUEHISTORY = "/users/%s/venuehistory";
    private static final String URL_API_USERS_GENERAL_SEARCH = "/users/search";
    private static final String URL_API_USERS_GENERAL_REQUESTS = "/users/requests";
    // TODO: add USERS_ACTIONS!!!
    
    // %s -> VENUE_ID
    private static final String URL_API_VENUES_ASPECTS_HERENOW = "/venues/%s/herenow";
    private static final String URL_API_VENUES_ASPECTS_TIPS = "/venues/%s/tips";
    private static final String URL_API_VENUES_ASPECTS_PHOTOS = "/venues/%s/photos";
    private static final String URL_API_VENUES_ASPECTS_LINKS = "/venues/%s/links";
    private static final String URL_API_VENUES_GENERAL_ADD = "/venues/add";
    private static final String URL_API_VENUES_GENERAL_CATEGORIES = "/venues/categories";
    private static final String URL_API_VENUES_GENERAL_SEARCH = "/venues/search";
    private static final String URL_API_VENUES_GENERAL_TRENDING = "/venues/trending";
    // TODO: add VENUES_ACTIONS!!!
    
    private static final String URL_API_CHECKINS_GENERAL_DETAILS = "/checkins/%s";//%s=CHECKIN_ID
    private static final String URL_API_CHECKINS_GENERAL_ADD = "/checkins/add";
    private static final String URL_API_CHECKINS_GENERAL_RECENT = "/checkins/recent";// Returns a list of recent checkins from friends. 
    private static final String URL_API_CHECKINS_ACTIONS_ADDCOMMENT = "/checkins/%s/addcomment";
    private static final String URL_API_CHECKINS_ACTIONS_DELETECOMMENT = "/checkins/%s/deletecomment";
    
    private static final String URL_API_TIPS_GENERAL_ADD = "/tips/add";
    private static final String URL_API_TIPS_GENERAL_SEARCH = "/tips/search";
    // TODO: add TIPS_ACTIONS
    
    private static final String URL_API_SETTINGS_GENERAL_ALL = "/settings/all";
    private static final String URL_API_SETTINGS_ACTIONS_SET = "/settings/%s/set";
    
    private static final String URL_API_SPECIALS_GENERAL_SEARCH = "/specials/search";
    
    
    // V1 endpoints
    private static final String URL_API_AUTHEXCHANGE = "/authexchange";

    private static final String URL_API_ADDVENUE = "/addvenue";
    private static final String URL_API_ADDTIP = "/addtip";
    private static final String URL_API_CITIES = "/cities";
    private static final String URL_API_CHECKINS = "/checkins";
    private static final String URL_API_CHECKIN = "/checkin";
    private static final String URL_API_USER = "/user";
    private static final String URL_API_VENUE = "/venue";
    private static final String URL_API_VENUES = "/venues";
    private static final String URL_API_TIPS = "/tips";
    private static final String URL_API_TODOS = "/todos";
    private static final String URL_API_FRIEND_REQUESTS = "/friend/requests";
    private static final String URL_API_FRIEND_APPROVE = "/friend/approve";
    private static final String URL_API_FRIEND_DENY = "/friend/deny";
    private static final String URL_API_FRIEND_SENDREQUEST = "/friend/sendrequest";
    private static final String URL_API_FRIENDS = "/friends";
    private static final String URL_API_FIND_FRIENDS_BY_NAME = "/findfriends/byname";
    private static final String URL_API_FIND_FRIENDS_BY_PHONE = "/findfriends/byphone";
    private static final String URL_API_FIND_FRIENDS_BY_FACEBOOK = "/findfriends/byfacebook";
    private static final String URL_API_FIND_FRIENDS_BY_TWITTER = "/findfriends/bytwitter";
    private static final String URL_API_CATEGORIES = "/categories";
    private static final String URL_API_HISTORY = "/history";
    private static final String URL_API_FIND_FRIENDS_BY_PHONE_OR_EMAIL = "/findfriends/byphoneoremail";
    private static final String URL_API_INVITE_BY_EMAIL = "/invite/byemail";
    private static final String URL_API_SETPINGS = "/settings/setpings";
    private static final String URL_API_VENUE_FLAG_CLOSED = "/venue/flagclosed";
    private static final String URL_API_VENUE_FLAG_MISLOCATED = "/venue/flagmislocated";
    private static final String URL_API_VENUE_FLAG_DUPLICATE = "/venue/flagduplicate";
    private static final String URL_API_VENUE_PROPOSE_EDIT = "/venue/proposeedit";
    private static final String URL_API_USER_UPDATE = "/user/update";
    private static final String URL_API_MARK_TODO = "/mark/todo";
    private static final String URL_API_MARK_IGNORE = "/mark/ignore";
    private static final String URL_API_MARK_DONE = "/mark/done";
    private static final String URL_API_UNMARK_TODO = "/unmark/todo";
    private static final String URL_API_UNMARK_DONE = "/unmark/done";
    private static final String URL_API_TIP_DETAIL = "/tip/detail";
    
    private final DefaultHttpClient mHttpClient = AbstractHttpApi.createHttpClient();
    private HttpApi mHttpApi;

    private final String mApiBaseUrl;
    private final String mApiBaseUrlMulti;
    private final AuthScope mAuthScope;
    
    // by YG
    private final String mApiBaseUrl4Auth;
    public static final String FOURSQUARE_API_DOMAIN4AUTH = "foursquare.com";

    public FoursquareHttpApiV2(String domain, String clientVersion, boolean useOAuth) {
        
    	mApiBaseUrl = "https://" + domain + "/v2";
    	mApiBaseUrlMulti = "https://" + domain + "/v2/multi";
    	mApiBaseUrl4Auth = "https://" + FOURSQUARE_API_DOMAIN4AUTH;
        
    	mAuthScope = new AuthScope(domain, 80);
        
    	mHttpApi = new HttpApiWithOAuthV2(mHttpClient, clientVersion);
    }
    
    // added by YG
	public String getApiBaseUrl() {
		return mApiBaseUrl;
	}

	void setCredentials(String phone, String password) {
        if (phone == null || phone.length() == 0 || password == null || password.length() == 0) {
            if (DEBUG) Log.d(TAG,  "Clearing Credentials");
            mHttpClient.getCredentialsProvider().clear();
        } else {
            if (DEBUG) Log.d(TAG,  "Setting Phone/Password: " + phone + "/******");
            mHttpClient.getCredentialsProvider().setCredentials(mAuthScope,
                    new UsernamePasswordCredentials(phone, password));
        }
    }

    public boolean hasCredentials() {
        return mHttpClient.getCredentialsProvider().getCredentials(mAuthScope) != null;
    }

    public void setOAuthConsumerCredentials(String oAuthConsumerKey, String oAuthConsumerSecret) {
        if (DEBUG) {
            Log.d(TAG,  "Setting consumer key/secret-> key: " + oAuthConsumerKey 
            		+ " |-|secret: " + oAuthConsumerSecret);
        }
        ((HttpApiWithOAuthV2) mHttpApi).setOAuthConsumerCredentials(oAuthConsumerKey,
                oAuthConsumerSecret);
    }
    
    //added by YG
    public String getOAuthConsumerCredentials() {
        return ((HttpApiWithOAuthV2) mHttpApi).getOAuthConsumerCredentials();
    }

    public void setOAuthTokenWithSecret(String token, String secret) {
        if (DEBUG) Log.d(TAG,  "Setting oauth token/secret: " + token + " " + secret);
        ((HttpApiWithOAuthV2) mHttpApi).setOAuthTokenWithSecret(token, secret);
    }

    public boolean hasOAuthTokenWithSecret() {
        return ((HttpApiWithOAuthV2) mHttpApi).hasOAuthTokenWithSecret();
    }
    
    //added by YG
    public String getOAuthTokenWithSecret() {
        return ((HttpApiWithOAuthV2) mHttpApi).getOAuthTokenWithSecret();
    }
    public String getOAuthToken() {
        return ((HttpApiWithOAuthV2) mHttpApi).getOAuthToken();
    }

    /*
     * /authexchange?oauth_consumer_key=d123...a1bffb5&oauth_consumer_secret=fec...
     * 18
     */
    public Credentials authExchange(String phone, String password) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        if ( (mHttpApi instanceof HttpApiWithOAuthV2)
        		&& ((HttpApiWithOAuthV2) mHttpApi).hasOAuthTokenWithSecret()) {
            throw new IllegalStateException("Cannot do authExchange with OAuthToken already set");
        }
        
        WUtils.trustEveryone();//YG
        
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_AUTHEXCHANGE), //
                new BasicNameValuePair("fs_username", phone), //
                new BasicNameValuePair("fs_password", password));
        return (Credentials) mHttpApi.doHttpRequest(httpPost, new CredentialsParser());
    }

    /*
     * /addtip?vid=1234&text=I%20added%20a%20tip&type=todo (type defaults "tip")
     */
    Tip addtip(String vid, String text, String type, String geolat, String geolong, String geohacc,
            String geovacc, String geoalt) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_ADDTIP), //
                new BasicNameValuePair("vid", vid), //
                new BasicNameValuePair("text", text), //
                new BasicNameValuePair("type", type), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt));
        return (Tip) mHttpApi.doHttpRequest(httpPost, new TipParser());
    }

    /**
     * @param name the name of the venue
     * @param address the address of the venue (e.g., "202 1st Avenue")
     * @param crossstreet the cross streets (e.g., "btw Grand & Broome")
     * @param city the city name where this venue is
     * @param state the state where the city is
     * @param zip (optional) the ZIP code for the venue
     * @param phone (optional) the phone number for the venue
     * @return
     * @throws FoursquareException
     * @throws FoursquareCredentialsException
     * @throws FoursquareError
     * @throws IOException
     */
    Venue addvenue(String name, String address, String crossstreet, String city, String state,
            String zip, String phone, String categoryId, String geolat, String geolong, String geohacc,
            String geovacc, String geoalt) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_ADDVENUE), //
                new BasicNameValuePair("name", name), //
                new BasicNameValuePair("address", address), //
                new BasicNameValuePair("crossstreet", crossstreet), //
                new BasicNameValuePair("city", city), //
                new BasicNameValuePair("state", state), //
                new BasicNameValuePair("zip", zip), //
                new BasicNameValuePair("phone", phone), //
                new BasicNameValuePair("primarycategoryid", categoryId), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt) //
                );
        return (Venue) mHttpApi.doHttpRequest(httpPost, new VenueParser());
    }

    /*
     * /cities
     */
    @SuppressWarnings("unchecked")
    Group<City> cities() throws FoursquareException, FoursquareCredentialsException,
            FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_CITIES));
        return (Group<City>) mHttpApi.doHttpRequest(httpGet, new GroupParser(new CityParser()));
    }

    /*
     * /checkins?
     */
    @SuppressWarnings("unchecked")
    Group<Checkin> checkins(String geolat, String geolong, String geohacc, String geovacc,
            String geoalt) throws FoursquareException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_CHECKINS), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt));
        return (Group<Checkin>) mHttpApi.doHttpRequest(httpGet,
                new GroupParser(new CheckinParser()));
    }

    /*
     * /checkin?vid=1234&venue=Noc%20Noc&shout=Come%20here&private=0&twitter=1
     */
    CheckinResult checkin(String vid, String venue, String geolat, String geolong, String geohacc,
            String geovacc, String geoalt, String shout, boolean isPrivate, boolean tellFollowers,
            boolean twitter, boolean facebook) throws FoursquareException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_CHECKIN), //
                new BasicNameValuePair("vid", vid), //
                new BasicNameValuePair("venue", venue), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt), //
                new BasicNameValuePair("shout", shout), //
                new BasicNameValuePair("private", (isPrivate) ? "1" : "0"), //
                new BasicNameValuePair("followers", (tellFollowers) ? "1" : "0"), //
                new BasicNameValuePair("twitter", (twitter) ? "1" : "0"), //
                new BasicNameValuePair("facebook", (facebook) ? "1" : "0"), //
                new BasicNameValuePair("markup", "android")); // used only by android for checkin result 'extras'.
        return (CheckinResult) mHttpApi.doHttpRequest(httpPost, new CheckinResultParser());
    }
    /*
     * 
     */
    CheckinResult checkinV2(String vid, String venue, String geolat, String geolong, String geohacc,
            String geovacc, String geoalt, String shout, boolean isPrivate, boolean tellFollowers,
            boolean twitter, boolean facebook) throws FoursquareException, FoursquareError, IOException {
    	String broadcast = "public";
    	if (isPrivate) {
    		broadcast="private";
    	}
    	else {
	    	if (tellFollowers)broadcast="public";
	    	if (facebook)broadcast+=",facebook";
	    	if (twitter)broadcast+=",twitter";
    	}
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrlV2(URL_API_CHECKINS_GENERAL_ADD,"")
                , new BasicNameValuePair("venueId", vid)
                , new BasicNameValuePair("venue", venue)
                , new BasicNameValuePair("shout", shout)
                , new BasicNameValuePair("ll", geolat+","+geolong)
                
                , new BasicNameValuePair("llAcc", "1")
                , new BasicNameValuePair("alt", "0")
                , new BasicNameValuePair("altAcc", "1")
                
                , new BasicNameValuePair("broadcast", broadcast)
        		, new BasicNameValuePair("oauth_token", ((HttpApiWithOAuthV2) mHttpApi).getOAuthToken())
                /*
                new BasicNameValuePair("private", (isPrivate) ? "1" : "0"), //
                new BasicNameValuePair("followers", (tellFollowers) ? "1" : "0"), //
                new BasicNameValuePair("twitter", (twitter) ? "1" : "0"), //
                new BasicNameValuePair("facebook", (facebook) ? "1" : "0"), //
                new BasicNameValuePair("markup", "android")); // used only by android for checkin result 'extras'.
        		*/
        	);
        return (CheckinResult) mHttpApi.doHttpRequest(httpPost, new CheckinResultParserV2());
    }

    /**
     * /user?uid=9937
     */
    User user(String uid, boolean mayor, boolean badges, boolean stats, String geolat, String geolong,
            String geohacc, String geovacc, String geoalt) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_USER), //
                new BasicNameValuePair("uid", uid), //
                new BasicNameValuePair("mayor", (mayor) ? "1" : "0"), //
                new BasicNameValuePair("badges", (badges) ? "1" : "0"), //
                new BasicNameValuePair("stats", (stats) ? "1" : "0"), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt) //
                );
        return (User) mHttpApi.doHttpRequest(httpGet, new UserParser());
    }
    /**
     * NEW
     * /users/USER_ID
     */
    User userV2(String uid) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(
        		//mApiBaseUrl + URL_API_USERS_ASPECTS_DETAILS,
        		fullUrlV2(URL_API_USERS_ASPECTS_DETAILS, uid)
                , new BasicNameValuePair("USER_ID", uid)
        		, new BasicNameValuePair("oauth_token", ((HttpApiWithOAuthV2) mHttpApi).getOAuthToken())
        	);
        return (User) mHttpApi.doHttpRequest(httpGet, new UserParserV2());
    }
    

    /**
     * /venues?geolat=37.770900&geolong=-122.43698
     */
    @SuppressWarnings("unchecked")
    Group<Group<Venue>> venues(String geolat, String geolong, String geohacc, String geovacc,
            String geoalt, String query, int limit) throws FoursquareException, FoursquareError,
            IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_VENUES), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt), //
                new BasicNameValuePair("q", query), //
                new BasicNameValuePair("l", String.valueOf(limit)));
        return (Group<Group<Venue>>) mHttpApi.doHttpRequest(httpGet, new GroupParser(
                new GroupParser(new VenueParser())));
    }
    /**
     * 
     */
    @SuppressWarnings("unchecked")
    Group<Group<Venue>> venuesV2(String geolat, String geolong, String query, int limit) throws FoursquareException, FoursquareError,
            IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrlV2(URL_API_VENUES_GENERAL_SEARCH,"") 
                , new BasicNameValuePair("ll", geolat+","+geolong)
                , new BasicNameValuePair("query", query) 
                , new BasicNameValuePair("limit", String.valueOf(limit))
        		, new BasicNameValuePair("intent", "checkin")
        		, new BasicNameValuePair("oauth_token", ((HttpApiWithOAuthV2) mHttpApi).getOAuthToken())
        	);
        		 
        return (Group<Group<Venue>>) mHttpApi.doHttpRequest(httpGet
        		, new GroupParserV2(
        				new GroupParserV2(new VenueParserV2())
        				)
        	); 
    }

    /**
     * /venue?vid=1234
     */
    Venue venue(String vid, String geolat, String geolong, String geohacc, String geovacc,
            String geoalt) throws FoursquareException, FoursquareCredentialsException,
            FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_VENUE), //
                new BasicNameValuePair("vid", vid), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt) //
                );
        return (Venue) mHttpApi.doHttpRequest(httpGet, new VenueParser());
    }
    
    /**
     * /tips?geolat=37.770900&geolong=-122.436987&l=1
     */
    @SuppressWarnings("unchecked")
    Group<Tip> tips(String geolat, String geolong, String geohacc, String geovacc,
            String geoalt, String uid, String filter, String sort, int limit) throws FoursquareException, 
            FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_TIPS), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt), //
                new BasicNameValuePair("uid", uid), //
                new BasicNameValuePair("filter", filter), //
                new BasicNameValuePair("sort", sort), //
                new BasicNameValuePair("l", String.valueOf(limit)) //
                );
        return (Group<Tip>) mHttpApi.doHttpRequest(httpGet, new GroupParser(
                new TipParser()));
    }
    
    /**
     * /todos?geolat=37.770900&geolong=-122.436987&l=1&sort=[recent|nearby]
     */
    @SuppressWarnings("unchecked")
    Group<Todo> todos(String uid, String geolat, String geolong, String geohacc, String geovacc,
            String geoalt, boolean recent, boolean nearby, int limit) 
            throws FoursquareException, FoursquareError, IOException {
        String sort = null;
        if (recent) {
            sort = "recent";
        } else if (nearby) {
            sort = "nearby";
        }
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_TODOS), //
                new BasicNameValuePair("uid", uid), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt), //
                new BasicNameValuePair("sort", sort), //
                new BasicNameValuePair("l", String.valueOf(limit)) //
               );
        return (Group<Todo>) mHttpApi.doHttpRequest(httpGet, new GroupParser(
                new TodoParser()));
    }
    
    /*
     * /friends?uid=9937
     */
    @SuppressWarnings("unchecked")
    Group<User> friends(String uid, String geolat, String geolong, String geohacc, String geovacc,
            String geoalt) throws FoursquareException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_FRIENDS), //
                new BasicNameValuePair("uid", uid), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt) //
                );
        return (Group<User>) mHttpApi.doHttpRequest(httpGet, new GroupParser(new UserParser()));
    }

    /*
     * /friend/requests
     */
    @SuppressWarnings("unchecked")
    Group<User> friendRequests() throws FoursquareException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_FRIEND_REQUESTS));
        return (Group<User>) mHttpApi.doHttpRequest(httpGet, new GroupParser(new UserParser()));
    }

    /*
     * /friend/approve?uid=9937
     */
    User friendApprove(String uid) throws FoursquareException, FoursquareCredentialsException,
            FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_FRIEND_APPROVE), //
                new BasicNameValuePair("uid", uid));
        return (User) mHttpApi.doHttpRequest(httpPost, new UserParser());
    }

    /*
     * /friend/deny?uid=9937
     */
    User friendDeny(String uid) throws FoursquareException, FoursquareCredentialsException,
            FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_FRIEND_DENY), //
                new BasicNameValuePair("uid", uid));
        return (User) mHttpApi.doHttpRequest(httpPost, new UserParser());
    }

    /*
     * /friend/sendrequest?uid=9937
     */
    User friendSendrequest(String uid) throws FoursquareException, FoursquareCredentialsException,
            FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_FRIEND_SENDREQUEST), //
                new BasicNameValuePair("uid", uid));
        return (User) mHttpApi.doHttpRequest(httpPost, new UserParser());
    }

    /**
     * /findfriends/byname?q=john doe, mary smith
     */
    @SuppressWarnings("unchecked")
    public Group<User> findFriendsByName(String text) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_FIND_FRIENDS_BY_NAME), //
                new BasicNameValuePair("q", text));
        return (Group<User>) mHttpApi.doHttpRequest(httpGet, new GroupParser(new UserParser()));
    }

    /**
     * /findfriends/byphone?q=555-5555,555-5556
     */
    @SuppressWarnings("unchecked")
    public Group<User> findFriendsByPhone(String text) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_FIND_FRIENDS_BY_PHONE), //
                new BasicNameValuePair("q", text));
        return (Group<User>) mHttpApi.doHttpRequest(httpPost, new GroupParser(new UserParser()));
    }

    /**
     * /findfriends/byfacebook?q=friendid,friendid,friendid
     */
    @SuppressWarnings("unchecked")
    public Group<User> findFriendsByFacebook(String text) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_FIND_FRIENDS_BY_FACEBOOK), //
                new BasicNameValuePair("q", text));
        return (Group<User>) mHttpApi.doHttpRequest(httpPost, new GroupParser(new UserParser()));
    }
    
    /**
     * /findfriends/bytwitter?q=yourtwittername
     */
    @SuppressWarnings("unchecked")
    public Group<User> findFriendsByTwitter(String text) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_FIND_FRIENDS_BY_TWITTER), //
                new BasicNameValuePair("q", text));
        return (Group<User>) mHttpApi.doHttpRequest(httpGet, new GroupParser(new UserParser()));
    }
    
    /**
     * /categories
     */
    @SuppressWarnings("unchecked")
    public Group<Category> categories() throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_CATEGORIES));
        return (Group<Category>) mHttpApi.doHttpRequest(httpGet, new GroupParser(new CategoryParser()));
    }

    /**
     * /history
     */
    @SuppressWarnings("unchecked")
    public Group<Checkin> history(String limit, String sinceid) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_HISTORY),
            new BasicNameValuePair("l", limit),
            new BasicNameValuePair("sinceid", sinceid));
        return (Group<Checkin>) mHttpApi.doHttpRequest(httpGet, new GroupParser(new CheckinParser()));
    }
    
    /** NEW
     * /historyV2
     */
    @SuppressWarnings("unchecked")
    public Group<Checkin> historyV2(String uid, String limit, String offset
    		,String afterTimestamp, String beforeTimestamp) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrlV2(URL_API_USERS_ASPECTS_CHECKINS, uid)
        		, new BasicNameValuePair("USER_ID", uid)
        		, new BasicNameValuePair("limit", limit)
        		, new BasicNameValuePair("offset", offset)
        		, new BasicNameValuePair("afterTimestamp", afterTimestamp)
        		, new BasicNameValuePair("beforeTimestamp", beforeTimestamp)
        		, new BasicNameValuePair("oauth_token", ((HttpApiWithOAuthV2) mHttpApi).getOAuthToken())
        	);
        return (Group<Checkin>) mHttpApi.doHttpRequest(httpGet, new GroupParserV2(new CheckinParserV2()));
    }
    
    /**
     * /mark/todo
     */
    public Todo markTodo(String tid) throws FoursquareException,
        FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_MARK_TODO), //
                new BasicNameValuePair("tid", tid));
        return (Todo) mHttpApi.doHttpRequest(httpPost, new TodoParser());
    }
    
    /**
     * This is a hacky special case, hopefully the api will be updated in v2 for this.
     * /mark/todo
     */
    public Todo markTodoVenue(String vid) throws FoursquareException,
        FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_MARK_TODO), //
                new BasicNameValuePair("vid", vid));
        return (Todo) mHttpApi.doHttpRequest(httpPost, new TodoParser());
    }
    
    /**
     * /mark/ignore
     */
    public Tip markIgnore(String tid) throws FoursquareException,
        FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_MARK_IGNORE), //
                new BasicNameValuePair("tid", tid));
        return (Tip) mHttpApi.doHttpRequest(httpPost, new TipParser());
    }
    
    /**
     * /mark/done
     */
    public Tip markDone(String tid) throws FoursquareException,
        FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_MARK_DONE), //
                new BasicNameValuePair("tid", tid));
        return (Tip) mHttpApi.doHttpRequest(httpPost, new TipParser());
    }
    
    /**
     * /unmark/todo
     */
    public Tip unmarkTodo(String tid) throws FoursquareException,
        FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_UNMARK_TODO), //
                    new BasicNameValuePair("tid", tid));
        return (Tip) mHttpApi.doHttpRequest(httpPost, new TipParser());
    }
    
    /**
     * /unmark/done
     */
    public Tip unmarkDone(String tid) throws FoursquareException,
        FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_UNMARK_DONE), //
                    new BasicNameValuePair("tid", tid));
        return (Tip) mHttpApi.doHttpRequest(httpPost, new TipParser());
    }

    /**
     * /tip/detail?tid=1234
     */
    public Tip tipDetail(String tid) throws FoursquareException,
        FoursquareCredentialsException, FoursquareError, IOException {
        HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_TIP_DETAIL), //
                new BasicNameValuePair("tid", tid));
        return (Tip) mHttpApi.doHttpRequest(httpGet, new TipParser());
    }

    /**
     * /findfriends/byphoneoremail?p=comma-sep-list-of-phones&e=comma-sep-list-of-emails
     */
    public FriendInvitesResult findFriendsByPhoneOrEmail(String phones, String emails) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_FIND_FRIENDS_BY_PHONE_OR_EMAIL), //
                new BasicNameValuePair("p", phones),
                new BasicNameValuePair("e", emails));
        return (FriendInvitesResult) mHttpApi.doHttpRequest(httpPost, new FriendInvitesResultParser());
    }
    
    /**
     * /invite/byemail?q=comma-sep-list-of-emails
     */
    public Response inviteByEmail(String emails) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_INVITE_BY_EMAIL), //
                new BasicNameValuePair("q", emails));
        return (Response) mHttpApi.doHttpRequest(httpPost, new ResponseParser());
    }
    
    /**
     * /settings/setpings?self=[on|off]
     */
    public Settings setpings(boolean on) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_SETPINGS), //
                new BasicNameValuePair("self", on ? "on" : "off"));
        return (Settings) mHttpApi.doHttpRequest(httpPost, new SettingsParser());
    }
    
    /**
     * /settings/setpings?uid=userid
     */
    public Settings setpings(String userid, boolean on) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_SETPINGS), //
                new BasicNameValuePair(userid, on ? "on" : "off"));
        return (Settings) mHttpApi.doHttpRequest(httpPost, new SettingsParser());
    }
    
    /**
     * /venue/flagclosed?vid=venueid
     */
    public Response flagclosed(String venueId) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_VENUE_FLAG_CLOSED), //
                new BasicNameValuePair("vid", venueId));
        return (Response) mHttpApi.doHttpRequest(httpPost, new ResponseParser());
    }

    /**
     * /venue/flagmislocated?vid=venueid
     */
    public Response flagmislocated(String venueId) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_VENUE_FLAG_MISLOCATED), //
                new BasicNameValuePair("vid", venueId));
        return (Response) mHttpApi.doHttpRequest(httpPost, new ResponseParser());
    }

    /**
     * /venue/flagduplicate?vid=venueid
     */
    public Response flagduplicate(String venueId) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_VENUE_FLAG_DUPLICATE), //
                new BasicNameValuePair("vid", venueId));
        return (Response) mHttpApi.doHttpRequest(httpPost, new ResponseParser());
    }
    
    /**
     * /venue/prposeedit?vid=venueid&name=...
     */
    public Response proposeedit(String venueId, String name, String address, String crossstreet, 
            String city, String state, String zip, String phone, String categoryId, String geolat, 
            String geolong, String geohacc, String geovacc, String geoalt) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        HttpPost httpPost = mHttpApi.createHttpPost(fullUrl(URL_API_VENUE_PROPOSE_EDIT), //
                new BasicNameValuePair("vid", venueId), //
                new BasicNameValuePair("name", name), //
                new BasicNameValuePair("address", address), //
                new BasicNameValuePair("crossstreet", crossstreet), //
                new BasicNameValuePair("city", city), //
                new BasicNameValuePair("state", state), //
                new BasicNameValuePair("zip", zip), //
                new BasicNameValuePair("phone", phone), //
                new BasicNameValuePair("primarycategoryid", categoryId), //
                new BasicNameValuePair("geolat", geolat), //
                new BasicNameValuePair("geolong", geolong), //
                new BasicNameValuePair("geohacc", geohacc), //
                new BasicNameValuePair("geovacc", geovacc), //
                new BasicNameValuePair("geoalt", geoalt) //
                );
        return (Response) mHttpApi.doHttpRequest(httpPost, new ResponseParser());
    }
    
    private String fullUrlV2(String url, String id) {
    	String fullUrl = "";
    	
    	// String.format("/users/%s/friends",username)
    	if (!TextUtils.isEmpty(id)) {
    		fullUrl = mApiBaseUrl + String.format(url, id);
    	}
    	else {
    		fullUrl = mApiBaseUrl + url;
    	}
    	
    	return fullUrl;
    }
    private String fullUrl(String url) {
        return mApiBaseUrl + url + DATATYPE;
    }
    

}
