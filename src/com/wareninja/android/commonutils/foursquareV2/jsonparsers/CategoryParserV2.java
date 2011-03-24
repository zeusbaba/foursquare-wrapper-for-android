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


public class CategoryParserV2 extends AbstractParser<Category> {
    
    @Override
    public Category parse(JSONObject json) throws JSONException {
        Category obj = new Category();
        
    	/*
    {"id":"4bf58dd8d48988d103941735"
    	,"name":"Homes"
    	,"icon":"http://foursquare.com/img/categories/building/home.png"
    	,"parents":["Homes, Work, Others"]
    	,"primary":true}
    	 */
        
        obj.setId(json.has("id")?json.getString("id"):"");
        obj.setNodeName(json.has("name")?json.getString("name"):"");
        obj.setIconUrl(json.has("icon")?json.getString("icon"):"");
        obj.setFullPathName(json.has("parents")?json.getString("parents"):"");
        obj.setPrimary(json.has("primary")?json.getBoolean("primary"):false);
        
        /*
        if (json.has("nodename")) {
            obj.setNodeName(json.getString("nodename"));
        } 
        if (json.has("categories")) {
            obj.setChildCategories(
                new GroupParser(
                    new CategoryParserV2()).parse(json.getJSONArray("categories")));
        }
        */
        
        return obj;
    }
}