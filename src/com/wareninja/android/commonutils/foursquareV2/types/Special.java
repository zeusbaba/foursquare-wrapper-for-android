/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;

import com.wareninja.android.commonutils.foursquareV2.util.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auto-generated: 2010-01-09 17:54:53.266438
 *
 * @author Joe LaPenna (joe@joelapenna.com)
 * @author Mark Wyszomierski (markww@gmail.com), implemented Parcelable.
 */
public class Special implements FoursquareType, Parcelable, Serializable {

	private static final long serialVersionUID = 1L;

    private String mId;
    private String mMessage;
    private String mType;
    private Venue mVenue;

    public Special() {
    }
    
    private Special(Parcel in) {
        mId = ParcelUtils.readStringFromParcel(in);
        mMessage = ParcelUtils.readStringFromParcel(in);
        mType = ParcelUtils.readStringFromParcel(in);
        
        if (in.readInt() == 1) {
            mVenue = in.readParcelable(Venue.class.getClassLoader());
        }
    }
    
    public static final Parcelable.Creator<Special> CREATOR = new Parcelable.Creator<Special>() {
        public Special createFromParcel(Parcel in) {
            return new Special(in);
        }

        @Override
        public Special[] newArray(int size) {
            return new Special[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public Venue getVenue() {
        return mVenue;
    }

    public void setVenue(Venue venue) {
        mVenue = venue;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        ParcelUtils.writeStringToParcel(out, mId);
        ParcelUtils.writeStringToParcel(out, mMessage);
        ParcelUtils.writeStringToParcel(out, mType);
        
        if (mVenue != null) {
            out.writeInt(1);
            out.writeParcelable(mVenue, flags);
        } else {
            out.writeInt(0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Special [mId=" + mId + ", mMessage=" + mMessage + ", mType="
				+ mType + ", mVenue=" + mVenue + "]";
	}
    
    
}
