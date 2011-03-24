/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 *
 */
public class StringArrayParser {

    public static List<String> parse(JSONArray json) throws JSONException {
        List<String> array = new ArrayList<String>();
        for (int i = 0, m = json.length(); i < m; i++) {
            array.add(json.getString(i));
        }
        
        return array; 
    }
}