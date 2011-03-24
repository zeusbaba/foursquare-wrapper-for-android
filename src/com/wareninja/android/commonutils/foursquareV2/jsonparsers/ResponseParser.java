/**
 * Copyright 2010 Mark Wyszomierski
 */

package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Response;

/**
 * @date April 28, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 */
public class ResponseParser extends AbstractParser<Response> {

    @Override
    public Response parse(JSONObject json) throws JSONException {
        Response response = new Response();
        response.setValue(json.getString("response"));
        return response;
    }
}