/**
 * Copyright 2011 WareNinja
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Tags;
import com.wareninja.android.commonutils.foursquareV2.types.Venue;


/**
 * @date Mar, 2011
 * @author YG (yg@wareninja.com)
 */
public class VenueParserV2 extends AbstractParser<Venue> {
 
	private static final String TAG = "VenueParserV2";
	
    @Override
    public Venue parse(JSONObject json) throws JSONException {

/*
{"id":"4c13cc83a1010f477e464b18","name":"YG castle :)","contact":{}
,"location":{"address":"Geistenstr","city":"DŸsseldorf","state":"North Rhine-Westphal","lat":51.2471131,"lng":6.7874551}
,"categories":[
	{"id":"4bf58dd8d48988d103941735"
	,"name":"Homes"
	,"icon":"http://foursquare.com/img/categories/building/home.png"
	,"parents":["Homes, Work, Others"]
	,"primary":true}
	]
,"verified":false,"stats":{"checkinsCount":62,"usersCount":1}
,"todos":{"count":0}},


 */ 	
        Venue obj = new Venue();
        
        JSONObject json1;
        
        if (json.has("location")) {
    		json1 = json.getJSONObject("location");
    		
    		obj.setGeolat(json1.has("lat")?json1.getDouble("lat")+"":"");
            obj.setGeolong(json1.has("lng")?json1.getDouble("lng")+"":"");
            obj.setAddress(json1.has("address")?json1.getString("address"):"");
            obj.setCity(json1.has("city")?json1.getString("city"):"");
            obj.setState(json1.has("state")?json1.getString("state"):"");
            obj.setZip(json1.has("postalCode")?json1.getString("postalCode"):"");
            obj.setCrossstreet(json1.has("crossStreet")?json1.getString("crossStreet"):"");
            obj.setDistance(json1.has("distance")?(""+json1.getInt("distance")):"");
    	}
        obj.setId(json.has("id")?json.getString("id"):"");
        obj.setName(json.has("name")?json.getString("name"):"");
        if (json.has("contact")) {
    		json1 = json.getJSONObject("contact");
    		
    		obj.setPhone(json1.has("phone")?json1.getDouble("phone")+"":"");
        }
        
        if (json.has("categories")) {
        	obj.setCategories(new GroupParser(
                    new CategoryParserV2()).parse(json.getJSONArray("categories"))
                    );
        }
        
        
        /*// 
        if (json.has("checkins")) {
            obj.setCheckins(
                new GroupParser(
                    new CheckinParser()).parse(json.getJSONArray("checkins")));
        } 
        
        if (json.has("cityid")) {
            obj.setCityid(json.getString("cityid"));
        }
        if (json.has("distance")) {
            obj.setDistance(json.getString("distance"));
        }
        if (json.has("hasTodo")) {
        	obj.setHasTodo(json.getBoolean("hasTodo"));
        }
        if (json.has("primarycategory")) {
             obj.setCategory(new CategoryParser().parse(json.getJSONObject("primarycategory")));
        } 
        if (json.has("specials")) {
            obj.setSpecials(
                new GroupParser(
                    new SpecialParser()).parse(json.getJSONArray("specials")));
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
        */

        return obj;
    }
}