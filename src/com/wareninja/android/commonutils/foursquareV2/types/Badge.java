/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;

import com.wareninja.android.commonutils.foursquareV2.util.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auto-generated: 2009-11-12 21:45:35.596207
 *
 * @author Joe LaPenna (joe@joelapenna.com)
 * @author Mark Wyszomierski (markww@gmail.com), implemented Parcelable.
 */
public class Badge implements FoursquareType, Parcelable, Serializable {

	private static final long serialVersionUID = 1L;

    private String mDescription;
    private String mIcon;
    private String mId;
    private String mName;

    public Badge() {
    }
    
    private Badge(Parcel in) {
        mDescription = ParcelUtils.readStringFromParcel(in);
        mIcon = ParcelUtils.readStringFromParcel(in);
        mId = ParcelUtils.readStringFromParcel(in);
        mName = ParcelUtils.readStringFromParcel(in);
    }
    
    public static final Parcelable.Creator<Badge> CREATOR = new Parcelable.Creator<Badge>() {
        public Badge createFromParcel(Parcel in) {
            return new Badge(in);
        }

        @Override
        public Badge[] newArray(int size) {
            return new Badge[size];
        }
    };

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        ParcelUtils.writeStringToParcel(out, mDescription);
        ParcelUtils.writeStringToParcel(out, mIcon);
        ParcelUtils.writeStringToParcel(out, mId);
        ParcelUtils.writeStringToParcel(out, mName);
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
		return "Badge [mDescription=" + mDescription + ", mIcon=" + mIcon
				+ ", mId=" + mId + ", mName=" + mName + "]";
	}
    
    
}
