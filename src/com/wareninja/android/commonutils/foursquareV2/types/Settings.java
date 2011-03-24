/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;

import com.wareninja.android.commonutils.foursquareV2.util.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auto-generated: 2010-01-25 20:40:14.399949
 *
 * @author Joe LaPenna (joe@joelapenna.com)
 * @author Mark Wyszomierski (markww@gmail.com), implemented Parcelable.
 */
public class Settings implements FoursquareType, Parcelable, Serializable {

	private static final long serialVersionUID = 1L;

    private String mFeedsKey;
    private boolean mGetPings;
    private String mPings;
    private boolean mSendtofacebook;
    private boolean mSendtotwitter;

    public Settings() {
    }
    
    private Settings(Parcel in) {
        mFeedsKey = ParcelUtils.readStringFromParcel(in);
        mGetPings = in.readInt() == 1;
        mPings = ParcelUtils.readStringFromParcel(in);
        mSendtofacebook = in.readInt() == 1;
        mSendtotwitter = in.readInt() == 1;
    }
    
    public static final Parcelable.Creator<Settings> CREATOR = new Parcelable.Creator<Settings>() {
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    public String getFeedsKey() {
        return mFeedsKey;
    }

    public void setFeedsKey(String feedsKey) {
        mFeedsKey = feedsKey;
    }
    
    public boolean getGetPings() {
        return mGetPings;
    }

    public void setGetPings(boolean getPings) {
        mGetPings = getPings;
    }

    public String getPings() {
        return mPings;
    }

    public void setPings(String pings) {
        mPings = pings;
    }

    public boolean sendtofacebook() {
        return mSendtofacebook;
    }

    public void setSendtofacebook(boolean sendtofacebook) {
        mSendtofacebook = sendtofacebook;
    }

    public boolean sendtotwitter() {
        return mSendtotwitter;
    }

    public void setSendtotwitter(boolean sendtotwitter) {
        mSendtotwitter = sendtotwitter;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        ParcelUtils.writeStringToParcel(out, mFeedsKey);
        out.writeInt(mGetPings ? 1 : 0);
        ParcelUtils.writeStringToParcel(out, mPings);
        out.writeInt(mSendtofacebook ? 1 : 0);
        out.writeInt(mSendtotwitter ? 1 : 0);
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
		return "Settings [mFeedsKey=" + mFeedsKey + ", mGetPings=" + mGetPings
				+ ", mPings=" + mPings + ", mSendtofacebook=" + mSendtofacebook
				+ ", mSendtotwitter=" + mSendtotwitter + "]";
	}
    
    
} 