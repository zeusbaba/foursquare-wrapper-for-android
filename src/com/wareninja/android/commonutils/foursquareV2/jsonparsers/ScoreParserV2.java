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
public class ScoreParserV2 extends AbstractParser<Score> {
    
    @Override
    public Score parse(JSONObject json) throws JSONException {
    	
        Score obj = new Score();
        obj.setIcon(json.has("icon")?json.getString("icon"):"");
        obj.setMessage(json.has("message")?json.getString("message"):"");
        obj.setPoints(json.has("points")?""+json.getInt("points"):"");
        
        return obj;
    }
}