/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.CheckinResult;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 *
 */
public class CheckinResultParser extends AbstractParser<CheckinResult> {
    
    @Override
    public CheckinResult parse(JSONObject json) throws JSONException {
        CheckinResult obj = new CheckinResult();
        if (json.has("badges")) {
            obj.setBadges(
                new GroupParser(
                    new BadgeParser()).parse(json.getJSONArray("badges")));
        } 
        if (json.has("created")) {
            obj.setCreated(json.getString("created"));
        } 
        if (json.has("id")) {
            obj.setId(json.getString("id"));
        } 
        if (json.has("markup")) {
            obj.setMarkup(json.getString("markup"));
        } 
        if (json.has("mayor")) {
            obj.setMayor(new MayorParser().parse(json.getJSONObject("mayor")));
        } 
        if (json.has("message")) {
            obj.setMessage(json.getString("message"));
        } 
        if (json.has("scores")) {
            obj.setScoring(
                new GroupParser(
                    new ScoreParser()).parse(json.getJSONArray("scores")));
        } 
        if (json.has("specials")) {
            obj.setSpecials(
                new GroupParser(
                    new SpecialParser()).parse(json.getJSONArray("specials")));
        } 
        if (json.has("venue")) {
            obj.setVenue(new VenueParser().parse(json.getJSONObject("venue")));
        }
            
        return obj;
    }
}