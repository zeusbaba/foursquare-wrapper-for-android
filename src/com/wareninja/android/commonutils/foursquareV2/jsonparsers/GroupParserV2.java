/**
 * Copyright 2010 Mark Wyszomierski
 */

package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.wareninja.android.commonutils.foursquareV2.LOGGING;
import com.wareninja.android.commonutils.foursquareV2.types.FoursquareType;
import com.wareninja.android.commonutils.foursquareV2.types.Group;

/**
 * Reference:
 * http://www.json.org/javadoc/org/json/JSONObject.html
 * http://www.json.org/javadoc/org/json/JSONArray.html
 * 
 * @author Mark Wyszomierski (markww@gmail.com)
 * @param <T>
 */
public class GroupParserV2 extends AbstractParser<Group> {

	private static final String TAG = "GroupParserV2";
	
    private Parser<? extends FoursquareType> mSubParser;

    public GroupParserV2(Parser<? extends FoursquareType> subParser) {
        mSubParser = subParser;
    }
     
    /**
     * When we encounter a JSONObject in a GroupParser, we expect one attribute
     * named 'type', and then another JSONArray attribute.
     */
    public Group<FoursquareType> parse(JSONObject json) throws JSONException {

        Group<FoursquareType> group = new Group<FoursquareType>();
          
        if(LOGGING.DEBUG)Log.d(TAG, "json->" + json);
        
        Iterator<String> it = (Iterator<String>)json.keys();
        //String firstKey = it.next();
        //group.setType(firstKey);// the first one
        //json = json.getJSONObject(firstKey);
        //if(LOGGING.DEBUG)Log.d(TAG, "json-2->" + json);
        
        group.setType(json.has("_type")?json.getString("_type"):"");
        
        it = (Iterator<String>)json.keys();
        while (it.hasNext()) {
            String key = it.next();
            /*if (key.equals("type")) {
                group.setType(json.getString(key));
            } else {*/
            if (key.equals("items")) {
                Object obj = json.get(key);
                if (obj instanceof JSONArray) {  
                    parse(group, (JSONArray)obj);
                } else {
                    throw new JSONException(TAG+"|Could not parse data.");
                }
            }
        }
        
        return group;
    }
    
    /**
     * Here we are getting a straight JSONArray and do not expect the 'type' attribute.
     */
    @Override
    public Group parse(JSONArray array) throws JSONException {
  
        Group<FoursquareType> group = new Group<FoursquareType>();
        parse(group, array);
        return group;
    }
    
    private void parse(Group group, JSONArray array) throws JSONException {
        for (int i = 0, m = array.length(); i < m; i++) {
            Object element = array.get(i);
            FoursquareType item = null;
            if (element instanceof JSONArray) {
                item = mSubParser.parse((JSONArray)element);
            } else {
                item = mSubParser.parse((JSONObject)element);
            }
            
            group.add(item);
        }
    }
}
