/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Tip;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 */
public class TipParser extends AbstractParser<Tip> {
  
    @Override
    public Tip parse(JSONObject json) throws JSONException {
  
        Tip obj = new Tip();
        if (json.has("created")) {
            obj.setCreated(json.getString("created"));
        }
        if (json.has("distance")) {
            obj.setDistance(json.getString("distance"));
        } 
        if (json.has("id")) {
            obj.setId(json.getString("id"));
        }
        if (json.has("stats")) {
            obj.setStats(new TipParser.StatsParser().parse(json.getJSONObject("stats")));
        }
        if (json.has("status")) {
            obj.setStatus(json.getString("status"));
        }
        if (json.has("text")) {
            obj.setText(json.getString("text"));
        } 
        if (json.has("user")) {
            obj.setUser(new UserParser().parse(json.getJSONObject("user")));
        } 
        if (json.has("venue")) {
            obj.setVenue(new VenueParser().parse(json.getJSONObject("venue")));
        }   
        
        return obj;
    }
        
    public static class StatsParser extends AbstractParser<Tip.Stats> {
        @Override
        public Tip.Stats parse(JSONObject json) throws JSONException {
            Tip.Stats stats = new Tip.Stats();
            if (json.has("donecount")) {
                stats.setDoneCount(json.getInt("donecount"));
            } 
            if (json.has("todocount")) {
                stats.setTodoCount(json.getInt("todocount"));               
            }
            
            return stats;
        }
    }
}
