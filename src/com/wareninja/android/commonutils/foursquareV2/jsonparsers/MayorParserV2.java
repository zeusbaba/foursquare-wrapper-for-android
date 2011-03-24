
package com.wareninja.android.commonutils.foursquareV2.jsonparsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.wareninja.android.commonutils.foursquareV2.types.Mayor;


public class MayorParserV2 extends AbstractParser<Mayor> {
    
    @Override
    public Mayor parse(JSONObject json) throws JSONException {
        
    	/*
{\"message\":\"Grischa S. is the Mayor of Irish Pub.\"
,\"daysBehind\":13
,\"image\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/G4WY1VIAKUUY4SHO.jpg\"
,\"checkins\":15
,\"type\":\"nochange\"
,\"user\":{\"id\":\"3221562\",\"gender\":\"male\",\"lastName\":\"S.\",\"firstName\":\"Grischa\",\"homeCity\":\"DŸsseldorf, Germany\",\"photo\":\"https:\\\/\\\/playfoursquare.s3.amazonaws.com\\\/userpix_thumbs\\\/G4WY1VIAKUUY4SHO.jpg\"}
}
    	 */
    	
        Mayor obj = new Mayor();
        
        obj.setMessage(json.has("message")?json.getString("message"):"");
        obj.setCheckins(json.has("checkins")?""+json.getInt("checkins"):"");
        obj.setType(json.has("type")?json.getString("type"):"");
        obj.setCount(json.has("daysBehind")?json.getString("daysBehind"):"");
        if (json.has("user")) {
            obj.setUser(new UserParserV2().parse(json.getJSONObject("user")));
        }
                    
        return obj;
    }
}