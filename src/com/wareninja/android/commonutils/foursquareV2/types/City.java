/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;

/**
 * Auto-generated: 2009-11-12 21:45:34.909467
 *
 * @author Joe LaPenna (joe@joelapenna.com)
 */
public class City implements FoursquareType, Serializable {

	private static final long serialVersionUID = 1L;

    private String mGeolat;
    private String mGeolong;
    private String mId;
    private String mName;
    private String mShortname;
    private String mTimezone;

    public City() {
    }

    public String getGeolat() {
        return mGeolat;
    }

    public void setGeolat(String geolat) {
        mGeolat = geolat;
    }

    public String getGeolong() {
        return mGeolong;
    }

    public void setGeolong(String geolong) {
        mGeolong = geolong;
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

    public String getShortname() {
        return mShortname;
    }

    public void setShortname(String shortname) {
        mShortname = shortname;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "City [mGeolat=" + mGeolat + ", mGeolong=" + mGeolong + ", mId="
				+ mId + ", mName=" + mName + ", mShortname=" + mShortname
				+ ", mTimezone=" + mTimezone + "]";
	}
    
    

}
