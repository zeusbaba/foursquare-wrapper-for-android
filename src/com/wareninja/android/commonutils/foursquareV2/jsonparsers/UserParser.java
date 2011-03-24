/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.wareninja.android.commonutils.foursquareV2.LOGGING;
import com.wareninja.android.commonutils.foursquareV2.types.User;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 */
public class UserParser extends AbstractParser<User> {

    @Override
    public User parse(JSONObject json) throws JSONException {
    	
    	User user = new User();
    	
        if (json.has("badges")) {
            user.setBadges(
                new GroupParser(
                    new BadgeParser()).parse(json.getJSONArray("badges")));
        }  
        if (json.has("badgecount")) {
            user.setBadgeCount(json.getInt("badgecount"));
        }
        if (json.has("checkin")) {
            user.setCheckin(new CheckinParser().parse(json.getJSONObject("checkin")));
        }
        if (json.has("checkincount")) {
            user.setCheckinCount(json.getInt("checkincount"));
        }
        if (json.has("created")) {
            user.setCreated(json.getString("created"));
        }
        if (json.has("email")) {
            user.setEmail(json.getString("email"));
        }
        if (json.has("facebook")) {
            user.setFacebook(json.getString("facebook"));
        }
        if (json.has("firstname")) {
            user.setFirstname(json.getString("firstname"));
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
                    new UserParser()).parse(json.getJSONArray("friendsincommon")));
        }  
        if (json.has("friendstatus")) {
            user.setFriendstatus(json.getString("friendstatus"));
        }
        if (json.has("gender")) {
            user.setGender(json.getString("gender"));
        }
        if (json.has("hometown")) {
            user.setHometown(json.getString("hometown"));
        }
        if (json.has("id")) {
            user.setId(json.getString("id"));
        } 
        if (json.has("lastname")) {
            user.setLastname(json.getString("lastname"));
        }
        if (json.has("mayor")) {
            user.setMayorships(
                new GroupParser(
                    new VenueParser()).parse(json.getJSONArray("mayor")));
        }
        if (json.has("mayorcount")) {
            user.setMayorCount(json.getInt("mayorcount"));
        }
        if (json.has("phone")) {
            user.setPhone(json.getString("phone"));
        }
        if (json.has("photo")) {
            user.setPhoto(json.getString("photo"));
        }
        if (json.has("settings")) {
            user.setSettings(new SettingsParser().parse(json.getJSONObject("settings")));
        } 
        if (json.has("tipcount")) {
            user.setTipCount(json.getInt("tipcount"));
        }
        if (json.has("todocount")) {
            user.setTodoCount(json.getInt("todocount"));
        }
        if (json.has("twitter")) {
            user.setTwitter(json.getString("twitter"));
        } 
        if (json.has("types")) {
            user.setTypes(new TypesParser().parseAsJSONArray(json.getJSONArray("types")));
        }

           
        // V2 params + extra checks (to fill non-existent keys etc)
        if (user.getBadgeCount()==0 && user.getBadges().size()>0)
        	user.setBadgeCount(user.getBadges().size());
        
        
        
        return user;
    }
    
    //@Override
    //public String getObjectName() {
    //    return "user";
    //}
}