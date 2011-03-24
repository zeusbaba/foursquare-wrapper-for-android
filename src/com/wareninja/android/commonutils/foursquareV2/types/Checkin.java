/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.wareninja.android.commonutils.foursquareV2.util.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auto-generated: 2009-11-22 20:21:34.324313
 *
 * @author Joe LaPenna (joe@joelapenna.com)
 * @author Mark Wyszomierski (markww@gmail.com), implemented Parcelable.
 */
public class Checkin implements FoursquareType, Parcelable, Serializable {

	private static final long serialVersionUID = 1L;

	private long mCreatedAt = 0L;
	private String mTimeZone;
	
    private String mCreated;
    private String mDisplay;
    private String mDistance;
    private String mId;
    private boolean mIsmayor;
    private boolean mPing;
    private String mShout;
    private User mUser;
    private Venue mVenue;

    public Checkin() {
        mPing = false;
    }
    
    private Checkin(Parcel in) {
        mCreated = ParcelUtils.readStringFromParcel(in);
        mDisplay = ParcelUtils.readStringFromParcel(in);
        mDistance = ParcelUtils.readStringFromParcel(in);
        mId = ParcelUtils.readStringFromParcel(in);
        mIsmayor = in.readInt() == 1;
        mPing = in.readInt() == 1;
        mShout = ParcelUtils.readStringFromParcel(in);
        
        if (in.readInt() == 1) {
            mUser = in.readParcelable(User.class.getClassLoader());
        }
        
        if (in.readInt() == 1) {
            mVenue = in.readParcelable(Venue.class.getClassLoader());
        }
    }
    
    public static final Parcelable.Creator<Checkin> CREATOR = new Parcelable.Creator<Checkin>() {
        public Checkin createFromParcel(Parcel in) {
            return new Checkin(in);
        }

        @Override
        public Checkin[] newArray(int size) {
            return new Checkin[size];
        }
    };

    public long getCreatedAt() {
        return mCreatedAt;
    }
    public void setCreatedAt(long created) {
        mCreatedAt = created;
        
        if (created!=0L) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);//("EEEE, MMMM d, yyyy HH:mm");
        	String dateString = formatter.format(new Date(created * 1000L));
        	setCreated(dateString);
        }
    }
    public String getTimeZone() {
        return mTimeZone;
    }
    public void setTimeZone(String timeZone) {
    	mTimeZone = timeZone;
    }
    
    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }

    public String getDisplay() {
        return mDisplay;
    }

    public void setDisplay(String display) {
        mDisplay = display;
    }
    
    public String getDistance() {
        return mDistance;
    }
    
    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean ismayor() {
        return mIsmayor;
    }

    public void setIsmayor(boolean ismayor) {
        mIsmayor = ismayor;
    }
    
    public boolean getPing() {
        return mPing;
    }
    
    public void setPing(boolean ping) {
        mPing = ping;
    }

    public String getShout() {
        return mShout;
    }

    public void setShout(String shout) {
        mShout = shout;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Venue getVenue() {
        return mVenue;
    }

    public void setVenue(Venue venue) {
        mVenue = venue;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        ParcelUtils.writeStringToParcel(out, mCreated);
        ParcelUtils.writeStringToParcel(out, mDisplay);
        ParcelUtils.writeStringToParcel(out, mDistance);
        ParcelUtils.writeStringToParcel(out, mId);
        out.writeInt(mIsmayor ? 1 : 0);
        out.writeInt(mPing ? 1 : 0);
        ParcelUtils.writeStringToParcel(out, mShout);
        
        if (mUser != null) {
            out.writeInt(1);
            out.writeParcelable(mUser, flags);
        } else {
            out.writeInt(0);
        }
        
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

	@Override
	public String toString() {
		return "Checkin [mCreated=" + mCreated
				+ ", mCreatedAt="+mCreatedAt + ", mTimeZone="+mTimeZone
				+ ", mId=" + mId 
				+ ", mDisplay=" + mDisplay
				+ ", mDistance=" + mDistance + ", mIsmayor="
				+ mIsmayor + ", mPing=" + mPing + ", mShout=" + mShout
				+ ", mUser=" + mUser + ", mVenue=" + mVenue
				+ "]";
	}
    
}
