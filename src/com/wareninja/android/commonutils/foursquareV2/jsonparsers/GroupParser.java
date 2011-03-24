/**
 * Copyright 2010 Mark Wyszomierski
 */

package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class GroupParser extends AbstractParser<Group> {

    private Parser<? extends FoursquareType> mSubParser;

    public GroupParser(Parser<? extends FoursquareType> subParser) {
        mSubParser = subParser;
    }
    
    /**
     * When we encounter a JSONObject in a GroupParser, we expect one attribute
     * named 'type', and then another JSONArray attribute.
     */
    public Group<FoursquareType> parse(JSONObject json) throws JSONException {

        Group<FoursquareType> group = new Group<FoursquareType>();
        
        Iterator<String> it = (Iterator<String>)json.keys();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("type")) {
                group.setType(json.getString(key));
            } else {
                Object obj = json.get(key);
                if (obj instanceof JSONArray) {  
                    parse(group, (JSONArray)obj);
                } else {
                    throw new JSONException("Could not parse data.");
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
