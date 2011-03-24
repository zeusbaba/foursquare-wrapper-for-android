/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Checkin;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 *
 */
public class CheckinParser extends AbstractParser<Checkin> {

    @Override
    public Checkin parse(JSONObject json) throws JSONException {
        
        Checkin obj = new Checkin();
        if (json.has("created")) {
            obj.setCreated(json.getString("created"));
        } 
        if (json.has("display")) {
            obj.setDisplay(json.getString("display"));
        }
        if (json.has("distance")) {
            obj.setDistance(json.getString("distance"));
        } 
        if (json.has("id")) {
            obj.setId(json.getString("id"));
        } 
        if (json.has("ismayor")) {
            obj.setIsmayor(json.getBoolean("ismayor"));
        } 
        if (json.has("ping")) {
            obj.setPing(json.getBoolean("ping"));
        } 
        if (json.has("shout")) {
            obj.setShout(json.getString("shout"));
        } 
        if (json.has("user")) {
            obj.setUser(new UserParser().parse(json.getJSONObject("user")));
        } 
        if (json.has("venue")) {
            obj.setVenue(new VenueParser().parse(json.getJSONObject("venue")));
        }
        
        return obj;
    }
}