/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Rank;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 *
 */
public class RankParser extends AbstractParser<Rank> {
    
    @Override
    public Rank parse(JSONObject json) throws JSONException {
        
        Rank obj = new Rank();
        if (json.has("city")) {
            obj.setCity(json.getString("city"));
        } 
        if (json.has("message")) {
            obj.setMessage(json.getString("message"));
        } 
        if (json.has("position")) {
            obj.setPosition(json.getString("position"));
        }
        
        return obj;
    }
}