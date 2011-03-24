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
 */
public interface Parser<T extends FoursquareType> {

    public abstract T parse(JSONObject json) throws JSONException;
    public Group parse(JSONArray array) throws JSONException;
}
