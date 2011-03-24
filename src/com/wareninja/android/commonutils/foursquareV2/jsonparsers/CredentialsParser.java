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

import com.wareninja.android.commonutils.foursquareV2.types.Credentials;

public class CredentialsParser extends AbstractParser<Credentials> {
    
    @Override
    public Credentials parse(JSONObject json) throws JSONException {
        Credentials obj = new Credentials();
        if (json.has("oauth_token")) {
            obj.setOauthToken(json.getString("oauth_token"));
        } 
        if (json.has("oauth_token_secret")) {
            obj.setOauthTokenSecret(json.getString("oauth_token_secret"));
        } 
        
        return obj;
    }
}