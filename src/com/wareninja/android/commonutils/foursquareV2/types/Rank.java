/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;

/**
 * Auto-generated: 2009-11-12 21:45:34.487112
 *
 * @author Joe LaPenna (joe@joelapenna.com)
 */
public class Rank implements FoursquareType, Serializable {

	private static final long serialVersionUID = 1L;

    private String mCity;
    private String mMessage;
    private String mPosition;

    public Rank() {
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rank [mCity=" + mCity + ", mMessage=" + mMessage
				+ ", mPosition=" + mPosition + "]";
	}

    
}
