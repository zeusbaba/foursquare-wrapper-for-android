/***
 * 	Copyright (c) 2010-2011 WareNinja.com
 * 	Author: yg@wareninja.com
 * 	Adapted to V2 endpoint using V1 reference from http://code.google.com/p/foursquared/	
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
*/

package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Badge;

public class BadgeParser extends AbstractParser<Badge> {

    @Override
    public Badge parse(JSONObject json) throws JSONException {
        
        Badge obj = new Badge();
        if (json.has("description")) {
            obj.setDescription(json.getString("description"));
        } 
        if (json.has("icon")) {
            obj.setIcon(json.getString("icon"));
        } 
        if (json.has("id")) {
            obj.setId(json.getString("id"));
        } 
        if (json.has("name")) {
            obj.setName(json.getString("name"));
        }
        
        return obj;
    }
}