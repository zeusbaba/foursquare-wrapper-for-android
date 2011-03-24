
package com.wareninja.android.commonutils.foursquareV2.util;

import android.os.Parcel;

public class ParcelUtils {

    public static void writeStringToParcel(Parcel out, String str) {
        if (str != null) {
            out.writeInt(1);
            out.writeString(str);
        } else {
            out.writeInt(0);
        }
    }
    
    public static String readStringFromParcel(Parcel in) {
        int flag = in.readInt();
        if (flag == 1) {
            return in.readString();
        } else {
            return null;
        }
    }
}