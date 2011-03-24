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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Emails;
import com.wareninja.android.commonutils.foursquareV2.types.FriendInvitesResult;

public class FriendInvitesResultParser extends AbstractParser<FriendInvitesResult> {
    
    @Override
    public FriendInvitesResult parse(JSONObject json) throws JSONException {
        
        FriendInvitesResult obj = new FriendInvitesResult();
        if (json.has("users")) {
            obj.setContactsOnFoursquare(
                new GroupParser(
                    new UserParser()).parse(json.getJSONArray("users")));
        } 
        if (json.has("emails")) {
            Emails emails = new Emails();
            if (json.optJSONObject("emails") != null) {
                JSONObject emailsAsObject = json.getJSONObject("emails");
                emails.add(emailsAsObject.getString("email"));
            } else if (json.optJSONArray("emails") != null) {
                JSONArray emailsAsArray = json.getJSONArray("emails");
                for (int i = 0; i < emailsAsArray.length(); i++) {
                    emails.add(emailsAsArray.getString(i));
                }
            }
            obj.setContactEmailsOnNotOnFoursquare(emails);
        }
        if (json.has("invited")) {
            Emails emails = new Emails();
            JSONArray array = json.getJSONArray("invited");
            for (int i = 0; i < array.length(); i++) {
                emails.add(array.getString(i));
            }
            obj.setContactEmailsOnNotOnFoursquareAlreadyInvited(emails);
        }
        return obj;
    }
}