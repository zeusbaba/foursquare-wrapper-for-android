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

import com.wareninja.android.commonutils.foursquareV2.types.Category;
import com.wareninja.android.commonutils.foursquareV2.util.IconUtils;


public class CategoryParser extends AbstractParser<Category> {
    
    @Override
    public Category parse(JSONObject json) throws JSONException {
        Category obj = new Category();
        if (json.has("id")) {
            obj.setId(json.getString("id"));
        } 
        if (json.has("fullpathname")) {
            obj.setFullPathName(json.getString("fullpathname"));
        } 
        if (json.has("nodename")) {
            obj.setNodeName(json.getString("nodename"));
        } 
        if (json.has("iconurl")) {
            // TODO: Remove this once api v2 allows icon request.
            String iconUrl = json.getString("iconurl");
            if (IconUtils.get().getRequestHighDensityIcons()) {
                iconUrl = iconUrl.replace(".png", "_64.png");
            }
            obj.setIconUrl(iconUrl);
        } 
        if (json.has("categories")) {
            obj.setChildCategories(
                new GroupParser(
                    new CategoryParser()).parse(json.getJSONArray("categories")));
        }
        
        return obj;
    }
}