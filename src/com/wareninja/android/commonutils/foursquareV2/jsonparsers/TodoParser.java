/**
 * Copyright 2010 Mark Wyszomierski
 */
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Todo;


/**
 * @date September 2, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 */
public class TodoParser extends AbstractParser<Todo> {
  
    @Override
    public Todo parse(JSONObject json) throws JSONException {
  
        Todo obj = new Todo();
        if (json.has("created")) {
            obj.setCreated(json.getString("created"));
        }
        if (json.has("tip")) {
            obj.setTip(new TipParser().parse(json.getJSONObject("tip")));
        }
        if (json.has("todoid")) {
            obj.setId(json.getString("todoid"));
        }
        
        return obj;
    }
}
