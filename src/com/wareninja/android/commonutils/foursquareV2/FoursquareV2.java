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

import com.wareninja.android.commonutils.foursquareV2.error.FoursquareCredentialsException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareError;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareException;
import com.wareninja.android.commonutils.foursquareV2.error.FoursquareParseException;
import com.wareninja.android.commonutils.foursquareV2.types.Category;
import com.wareninja.android.commonutils.foursquareV2.types.Checkin;
import com.wareninja.android.commonutils.foursquareV2.types.CheckinResult;
import com.wareninja.android.commonutils.foursquareV2.types.Credentials;
import com.wareninja.android.commonutils.foursquareV2.types.FriendInvitesResult;
import com.wareninja.android.commonutils.foursquareV2.types.Group;
import com.wareninja.android.commonutils.foursquareV2.types.Response;
import com.wareninja.android.commonutils.foursquareV2.types.Settings;
import com.wareninja.android.commonutils.foursquareV2.types.Tip;
import com.wareninja.android.commonutils.foursquareV2.types.Todo;
import com.wareninja.android.commonutils.foursquareV2.types.User;
import com.wareninja.android.commonutils.foursquareV2.types.Venue;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;

/**
 * @author YG (yg@wareninja.com)
 */
public class FoursquareV2 {
	private static final String TAG = "FoursquareV2";
	
    public static final boolean DEBUG = LOGGING.DEBUG;
    public static final boolean PARSER_DEBUG = LOGGING.DEBUG;

    public static final String FOURSQUARE_API_DOMAIN = "api.foursquare.com";
    
    public static final String FOURSQUARE_MOBILE_ADDFRIENDS = "http://m.foursquare.com/addfriends";
    public static final String FOURSQUARE_MOBILE_FRIENDS = "http://m.foursquare.com/friends";
    public static final String FOURSQUARE_MOBILE_SIGNUP = "http://m.foursquare.com/signup";
    public static final String FOURSQUARE_PREFERENCES = "http://foursquare.com/settings";

    public static final String MALE = "male";
    public static final String FEMALE = "female";

    private String mPhone;
    private String mPassword;

    private FoursquareHttpApiV2 mFoursquareV2;
    public FoursquareV2(FoursquareHttpApiV2 httpApi){
        mFoursquareV2 = httpApi;
    }
    
    public void setCredentials(String phone, String password) {
        mPhone = phone;
        mPassword = password;
        mFoursquareV2.setCredentials(phone, password);
    }
    
	 
    public void setOAuthToken(String token, String secret) {
		
		/*// we moved this part into Application!
		if (DEBUG) Log.d(TAG,  "fqsApiBaseUrl():"+mFoursquareV2.getApiBaseUrl());
		//added by YG
		if (mFoursquareV2.getApiBaseUrl().contains("/v1"))
			mFoursquareV2.setOAuthTokenWithSecret(token, secret);
		else
			mFoursquareV2.setOAuthTokenWithSecret(secret, token);
		*/
		mFoursquareV2.setOAuthTokenWithSecret(token, secret);
    }
    
    // added by YG
	public String getApiBaseUrl() {
		return mFoursquareV2.getApiBaseUrl();
	}
    public boolean hasOAuthTokenWithSecret() {
    	return mFoursquareV2.hasOAuthTokenWithSecret();
    }
    public String getOAuthTokenWithSecret() {
    	return mFoursquareV2.getOAuthTokenWithSecret();
    }
    public String getOAuthToken() {
    	return mFoursquareV2.getOAuthToken();
    }
    public String getUserName() {
		return mPhone;
	}
	public String getPassword() {
		return mPassword;
	}
	public String getOAuthConsumerCredentials() {
        return mFoursquareV2.getOAuthConsumerCredentials();
    }

     
    public void setOAuthConsumerCredentials(String oAuthConsumerKey, String oAuthConsumerSecret) {
        mFoursquareV2.setOAuthConsumerCredentials(oAuthConsumerKey, oAuthConsumerSecret);
    }

    public void clearAllCredentials() {
        setCredentials(null, null);
        setOAuthToken(null, null);
    }

     
    public boolean hasCredentials() {
        return mFoursquareV2.hasCredentials() && mFoursquareV2.hasOAuthTokenWithSecret();
    }

     
    public boolean hasLoginAndPassword() {
        return mFoursquareV2.hasCredentials();
    }

     
    public Credentials authExchange() throws FoursquareException, FoursquareError,
            FoursquareCredentialsException, IOException {
        if (mFoursquareV2 == null) {
            throw new NoSuchMethodError(
                    "authExchange is unavailable without a consumer key/secret.");
        }
        return mFoursquareV2.authExchange(mPhone, mPassword);
    }

     
    public Tip addTip(String vid, String text, String type, Location location)
            throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.addtip(vid, text, type, location.geolat, location.geolong,
                location.geohacc, location.geovacc, location.geoalt);
    }
    
     
    public Tip tipDetail(String tid) 
            throws FoursquareException, FoursquareError, IOException {
    	return mFoursquareV2.tipDetail(tid);
    }

     
    @LocationRequired
    public Venue addVenue(String name, String address, String crossstreet, String city,
            String state, String zip, String phone, String categoryId, Location location) 
            throws FoursquareException,
            FoursquareError, IOException {
        return mFoursquareV2.addvenue(name, address, crossstreet, city, state, zip, phone,
                categoryId, location.geolat, location.geolong, location.geohacc, location.geovacc,
                location.geoalt);
    }

     
    public CheckinResult checkin(String venueId, String venueName, Location location, String shout,
            boolean isPrivate, boolean tellFollowers, boolean twitter, boolean facebook) 
            throws FoursquareException,
            FoursquareError,
            IOException {
        return mFoursquareV2.checkin(venueId, venueName, location.geolat, location.geolong,
                location.geohacc, location.geovacc, location.geoalt, shout, isPrivate, 
                tellFollowers, twitter, facebook);
    }
    public CheckinResult checkinV2(String venueId, String venueName, Location location, String shout,
            boolean isPrivate, boolean tellFollowers, boolean twitter, boolean facebook) 
            throws FoursquareException,
            FoursquareError,
            IOException {
        return mFoursquareV2.checkinV2(venueId, venueName, location.geolat, location.geolong,
                location.geohacc, location.geovacc, location.geoalt, shout, isPrivate, 
                tellFollowers, twitter, facebook);
    }

     
    public Group<Checkin> checkins(Location location) throws FoursquareException, FoursquareError,
            IOException {
        return mFoursquareV2.checkins(location.geolat, location.geolong, location.geohacc,
                location.geovacc, location.geoalt);
    }

     
    public Group<User> friends(String userId, Location location) throws FoursquareException,
            FoursquareError, IOException {
        return mFoursquareV2.friends(userId, location.geolat, location.geolong,
                location.geohacc, location.geovacc, location.geoalt);
    }

     
    public Group<User> friendRequests() throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.friendRequests();
    }

     
    public User friendApprove(String userId) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.friendApprove(userId);
    }

     
    public User friendDeny(String userId) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.friendDeny(userId);
    }

     
    public User friendSendrequest(String userId) throws FoursquareException,
            FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.friendSendrequest(userId);
    }

     
    public Group<Tip> tips(Location location, String uid, String filter, String sort, int limit) 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.tips(location.geolat, location.geolong, location.geohacc,
                location.geovacc, location.geoalt, uid, filter, sort, limit);
    }

     
    public Group<Todo> todos(Location location, String uid, boolean recent, boolean nearby, int limit) 
            throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.todos(uid, location.geolat, location.geolong, location.geohacc,
                location.geovacc, location.geoalt, recent, nearby, limit);
    }

     
    public User user(String user, boolean mayor, boolean badges, boolean stats, Location location)
            throws FoursquareException, FoursquareError, IOException {
        if (location != null) {
            return mFoursquareV2.user(user, mayor, badges, stats, location.geolat, location.geolong,
                    location.geohacc, location.geovacc, location.geoalt);
        } else {
            return mFoursquareV2.user(user, mayor, badges, stats, null, null, null, null, null);
        }
    }
    public User userV2(String user) throws FoursquareException, FoursquareError, IOException {
    	
    	return mFoursquareV2.userV2(user);
	}
    
     
    public Venue venue(String id, Location location) throws FoursquareException, FoursquareError,
            IOException {
        return mFoursquareV2.venue(id, location.geolat, location.geolong, location.geohacc,
                location.geovacc, location.geoalt);
    }

     
    @LocationRequired
    public Group<Group<Venue>> venues(Location location, String query, int limit)
            throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.venues(location.geolat, location.geolong, location.geohacc,
                location.geovacc, location.geoalt, query, limit);
    }
    
    @LocationRequired
    public Group<Group<Venue>> venuesV2(Location location, String query, int limit)
            throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.venuesV2(location.geolat, location.geolong, query, limit);
    }
    
     
    public Group<User> findFriendsByName(String text) 
    	throws FoursquareException, FoursquareError, IOException {
    	return mFoursquareV2.findFriendsByName(text);
    }
    
     
    public Group<User> findFriendsByPhone(String text) 
    	throws FoursquareException, FoursquareError, IOException {
    	return mFoursquareV2.findFriendsByPhone(text);
    }
    
     
    public Group<User> findFriendsByFacebook(String text) 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.findFriendsByFacebook(text);
    }
    
     
    public Group<User> findFriendsByTwitter(String text) 
    	throws FoursquareException, FoursquareError, IOException {
    	return mFoursquareV2.findFriendsByTwitter(text);
    }

     
    public Group<Category> categories() 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.categories();
    }
    
     
    public Group<Checkin> history(String limit, String sinceid) 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.history(limit, sinceid);
    }
    public Group<Checkin> historyV2(String uid, String limit, String offset
    		,String afterTimestamp, String beforeTimestamp) 
	    throws FoursquareException, FoursquareError, IOException {
	    return mFoursquareV2.historyV2(uid, limit, offset, afterTimestamp, beforeTimestamp);
	}
     
    public Todo markTodo(String tid) 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.markTodo(tid);
    }
    
      
    public Todo markTodoVenue(String vid) 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.markTodoVenue(vid);
    }

     
    public Tip markIgnore(String tid) 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.markIgnore(tid);
    }

     
    public Tip markDone(String tid) 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.markDone(tid);
    }

     
    public Tip unmarkTodo(String tid) 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.unmarkTodo(tid);
    }
    
     
    public Tip unmarkDone(String tid) 
        throws FoursquareException, FoursquareError, IOException {
        return mFoursquareV2.unmarkDone(tid);
    }
    
     
    public FriendInvitesResult findFriendsByPhoneOrEmail(String phones, String emails)
        throws FoursquareException, FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.findFriendsByPhoneOrEmail(phones, emails);
    }
    
     
    public Response inviteByEmail(String emails) 
        throws FoursquareException, FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.inviteByEmail(emails);
    }
    
     
    public Settings setpings(boolean on) 
        throws FoursquareException, FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.setpings(on);
    }
    
     
    public Settings setpings(String userid, boolean on) 
        throws FoursquareException, FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.setpings(userid, on);
    }
    
     
    public Response flagclosed(String venueid) 
        throws FoursquareException, FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.flagclosed(venueid);
    }

     
    public Response flagmislocated(String venueid) 
        throws FoursquareException, FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.flagmislocated(venueid);
    }

     
    public Response flagduplicate(String venueid) 
        throws FoursquareException, FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.flagduplicate(venueid);
    }
    
     
    public Response proposeedit(String venueId, String name, String address, String crossstreet, 
        String city, String state, String zip, String phone, String categoryId, Location location) 
        throws FoursquareException, FoursquareCredentialsException, FoursquareError, IOException {
        return mFoursquareV2.proposeedit(venueId, name, address, crossstreet, city, state, zip, 
                phone, categoryId, location.geolat, location.geolong, location.geohacc, 
                location.geovacc, location.geoalt);
    }    
    
     /*
    public User userUpdate(String imagePathToJpg, String username, String password) 
        throws SocketTimeoutException, IOException, FoursquareError, FoursquareParseException {
        return mFoursquareV2.userUpdate(imagePathToJpg, username, password);
    }*/
    
    public static final FoursquareHttpApiV2 createHttpApi(String domain, String clientVersion,
            boolean useOAuth) {
        if(DEBUG)Log.d(TAG, "Using "+domain+" for requests. clientVersion:"+clientVersion);
        return new FoursquareHttpApiV2(domain, clientVersion, useOAuth);
    }

    public static final FoursquareHttpApiV2 createHttpApi(String clientVersion, boolean useOAuth) {
        return createHttpApi(FOURSQUARE_API_DOMAIN, clientVersion, useOAuth);
    }
    
    
 // added by YG:
    public static final FoursquareHttpApiV2 createHttpApiV2(String domain, String clientVersion
    		, int apiVersion,
            boolean useOAuth) {
        if(DEBUG)Log.d(TAG, "Using "+domain+" for requests. apiVersion:"+apiVersion+"|clientVersion:"+clientVersion);
        return new FoursquareHttpApiV2(domain, clientVersion, useOAuth);
    }
    public static final FoursquareHttpApiV2 createHttpApiV2(String clientVersion, int apiVersion, boolean useOAuth) {
        return createHttpApiV2(FOURSQUARE_API_DOMAIN, clientVersion, apiVersion,useOAuth);
    }

    public static final String createLeaderboardUrl(String userId, Location location) {
        Uri.Builder builder = new Uri.Builder() //
                .scheme("http") //
                .authority("foursquare.com") //
                .appendEncodedPath("/iphone/me") //
                .appendQueryParameter("view", "all") //
                .appendQueryParameter("scope", "friends") //
                .appendQueryParameter("uid", userId);
        if (!TextUtils.isEmpty(location.geolat)) {
            builder.appendQueryParameter("geolat", location.geolat);
        }
        if (!TextUtils.isEmpty(location.geolong)) {
            builder.appendQueryParameter("geolong", location.geolong);
        }
        if (!TextUtils.isEmpty(location.geohacc)) {
            builder.appendQueryParameter("geohacc", location.geohacc);
        }
        if (!TextUtils.isEmpty(location.geovacc)) {
            builder.appendQueryParameter("geovacc", location.geovacc);
        }
        return builder.build().toString();
    }
    
    /**
     * This api is supported in the V1 API documented at:
     * http://groups.google.com/group/foursquare-api/web/api-documentation
     */
    @interface V1 {
    }

    /**
     * This api call requires a location.
     */
    @interface LocationRequired {
    }

    public static class Location implements Serializable {//Serializable  added by YG, to be able to store it
        String geolat = null;
        String geolong = null;
        String geohacc = null;
        String geovacc = null;
        String geoalt = null;

        public Location() {
        }

        public Location(final String geolat, final String geolong, final String geohacc,
                final String geovacc, final String geoalt) {
            this.geolat = geolat;
            this.geolong = geolong;
            this.geohacc = geohacc;
            this.geovacc = geovacc;
            this.geoalt = geovacc;
        }

        public Location(final String geolat, final String geolong) {
            this(geolat, geolong, null, null, null);
        }
        
		public String getGeolat() {
			return geolat;
		}
		public void setGeolat(String geolat) {
			this.geolat = geolat;
		}
		public String getGeolong() {
			return geolong;
		}
		public void setGeolong(String geolong) {
			this.geolong = geolong;
		}
		public String getGeohacc() {
			return geohacc;
		}
		public void setGeohacc(String geohacc) {
			this.geohacc = geohacc;
		}
		public String getGeovacc() {
			return geovacc;
		}
		public void setGeovacc(String geovacc) {
			this.geovacc = geovacc;
		}
		public String getGeoalt() {
			return geoalt;
		}
		public void setGeoalt(String geoalt) {
			this.geoalt = geoalt;
		}

		@Override
		public String toString() {
			return "Location [geoalt=" + geoalt + ", geohacc=" + geohacc
					+ ", geolat=" + geolat + ", geolong=" + geolong
					+ ", geovacc=" + geovacc + "]";
		}
        
    }

}
