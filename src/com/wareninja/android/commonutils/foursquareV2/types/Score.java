/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;

/**
 * Auto-generated: 2009-11-12 21:45:34.267218
 *
 * @author Joe LaPenna (joe@joelapenna.com)
 */
public class Score implements FoursquareType, Serializable {

	private static final long serialVersionUID = 1L;

    private String mIcon;
    private String mMessage;
    private String mPoints;

    public Score() {
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getPoints() {
        return mPoints;
    }

    public void setPoints(String points) {
        mPoints = points;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Score [mIcon=" + mIcon + ", mMessage=" + mMessage
				+ ", mPoints=" + mPoints + "]";
	}

    
    
}
