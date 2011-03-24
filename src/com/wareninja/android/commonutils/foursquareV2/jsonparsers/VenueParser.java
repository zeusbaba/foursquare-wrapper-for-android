/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Tags;
import com.wareninja.android.commonutils.foursquareV2.types.Venue;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 */
public class VenueParser extends AbstractParser<Venue> {
 
    @Override
    public Venue parse(JSONObject json) throws JSONException {

        Venue obj = new Venue();
        if (json.has("address")) {
            obj.setAddress(json.getString("address"));
        } 
        if (json.has("checkins")) {
            obj.setCheckins(
                new GroupParser(
                    new CheckinParser()).parse(json.getJSONArray("checkins")));
        } 
        if (json.has("city")) {
            obj.setCity(json.getString("city"));
        } 
        if (json.has("cityid")) {
            obj.setCityid(json.getString("cityid"));
        } 
        if (json.has("crossstreet")) {
            obj.setCrossstreet(json.getString("crossstreet"));
        } 
        if (json.has("distance")) {
            obj.setDistance(json.getString("distance"));
        } 
        if (json.has("geolat")) {
            obj.setGeolat(json.getString("geolat"));
        }
        if (json.has("geolong")) {
            obj.setGeolong(json.getString("geolong"));
        } 
        if (json.has("hasTodo")) {
        	obj.setHasTodo(json.getBoolean("hasTodo"));
        }
        if (json.has("id")) {
            obj.setId(json.getString("id"));
        } 
        if (json.has("name")) {
            obj.setName(json.getString("name"));
        } 
        if (json.has("phone")) {
            obj.setPhone(json.getString("phone"));
        } 
        if (json.has("primarycategory")) {
             obj.setCategory(new CategoryParser().parse(json.getJSONObject("primarycategory")));
        } 
        if (json.has("specials")) {
            obj.setSpecials(
                new GroupParser(
                    new SpecialParser()).parse(json.getJSONArray("specials")));
        } 
        if (json.has("state")) {
            obj.setState(json.getString("state"));
        } 
        if (json.has("stats")) {
             obj.setStats(new StatsParser().parse(json.getJSONObject("stats")));
        } 
        if (json.has("tags")) {
            obj.setTags(
                new Tags(StringArrayParser.parse(json.getJSONArray("tags"))));
        }
        if (json.has("tips")) {
            obj.setTips(
                new GroupParser(
                    new TipParser()).parse(json.getJSONArray("tips")));
        } 
        if (json.has("todos")) {
            obj.setTodos(
                new GroupParser(
                    new TodoParser()).parse(json.getJSONArray("todos")));
        } 
        if (json.has("twitter")) {
            obj.setTwitter(json.getString("twitter"));
        } 
        if (json.has("zip")) {
            obj.setZip(json.getString("zip"));
        }

        return obj;
    }
}