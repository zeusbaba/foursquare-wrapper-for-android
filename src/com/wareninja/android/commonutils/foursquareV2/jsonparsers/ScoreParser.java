/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Score;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 *
 */
public class ScoreParser extends AbstractParser<Score> {
    
    @Override
    public Score parse(JSONObject json) throws JSONException {
        Score obj = new Score();
        if (json.has("icon")) {
            obj.setIcon(json.getString("icon"));
        } 
        if (json.has("message")) {
            obj.setMessage(json.getString("message"));
        } 
        if (json.has("points")) {
            obj.setPoints(json.getString("points"));
        }
        
        return obj;
    }
}