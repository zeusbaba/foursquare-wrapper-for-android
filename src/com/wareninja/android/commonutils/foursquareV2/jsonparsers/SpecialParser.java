/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Special;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 *
 */
public class SpecialParser extends AbstractParser<Special> {

    @Override
    public Special parse(JSONObject json) throws JSONException {
        
        Special obj = new Special();
        if (json.has("id")) {
            obj.setId(json.getString("id"));
        } 
        if (json.has("message")) {
            obj.setMessage(json.getString("message"));
        } 
        if (json.has("type")) {
            obj.setType(json.getString("type"));
        } 
        if (json.has("venue")) {
            obj.setVenue(new VenueParser().parse(json.getJSONObject("venue")));
        }
        
        return obj;
    }
}