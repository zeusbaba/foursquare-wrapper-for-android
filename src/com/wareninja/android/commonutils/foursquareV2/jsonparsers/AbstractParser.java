/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.FoursquareType;
import com.wareninja.android.commonutils.foursquareV2.types.Group;


/**
 * @date July 13, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 *
 */
public abstract class AbstractParser<T extends FoursquareType> implements Parser<T> {

    /** 
     * All derived parsers must implement parsing a JSONObject instance of themselves. 
     */
    public abstract T parse(JSONObject json) throws JSONException;
    
    /**
     * Only the GroupParser needs to implement this.
     */
    public Group parse(JSONArray array) throws JSONException {
        throw new JSONException("Unexpected JSONArray parse type encountered.");
    }
}