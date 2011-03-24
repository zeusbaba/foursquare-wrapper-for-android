/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Types;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 */
public class TypesParser extends AbstractParser<Types> {
    
    @Override
    public Types parse(JSONObject json) throws JSONException {
        Types obj = new Types();
        if (json.has("type")) {
            obj.add(json.getString("type"));
        }
        
        return obj;
    }
    
    public Types parseAsJSONArray(JSONArray array) throws JSONException {
        Types obj = new Types();
        for (int i = 0, m = array.length(); i < m; i++) {
            obj.add(array.getString(i));
        }
        
        return obj;
    }
}