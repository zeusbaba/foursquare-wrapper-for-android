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

import com.wareninja.android.commonutils.foursquareV2.types.Beenhere;


public class BeenhereParser extends AbstractParser<Beenhere> {
    
    @Override
    public Beenhere parse(JSONObject json) throws JSONException {
        Beenhere obj = new Beenhere();
        if (json.has("friends")) {
            obj.setFriends(json.getBoolean("friends"));
        } 
        if (json.has("me")) {
            obj.setMe(json.getBoolean("me"));
        }
        
        return obj;
    }
}