/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Stats;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 *
 */
public class StatsParser extends AbstractParser<Stats> {
    
    @Override
    public Stats parse(JSONObject json) throws JSONException {
        Stats obj = new Stats();
        if (json.has("beenhere")) {
            obj.setBeenhere(new BeenhereParser().parse(json.getJSONObject("beenhere")));
        } 
        if (json.has("checkins")) {
            obj.setCheckins(json.getString("checkins"));
        } 
        if (json.has("herenow")) {
            obj.setHereNow(json.getString("herenow"));
        } 
        if (json.has("mayor")) {
            obj.setMayor(new MayorParser().parse(json.getJSONObject("mayor")));
        } 
        
        return obj;
    }
}