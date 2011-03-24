
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.City;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 *
 */
public class CityParser extends AbstractParser<City> {

    @Override
    public City parse(JSONObject json) throws JSONException {
        
        City obj = new City();
        if (json.has("geolat")) {
            obj.setGeolat(json.getString("geolat"));
        } 
        if (json.has("geolong")) {
            obj.setGeolong(json.getString("geolong"));
        } 
        if (json.has("id")) {
            obj.setId(json.getString("id"));
        } 
        if (json.has("name")) {
            obj.setName(json.getString("name"));
        } 
        if (json.has("shortname")) {
            obj.setShortname(json.getString("shortname"));
        } 
        if (json.has("timezone")) {
            obj.setTimezone(json.getString("timezone"));
        }
        
        return obj;
    }
}