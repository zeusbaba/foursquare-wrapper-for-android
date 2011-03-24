/**
 * Copyright 2010 Mark Wyszomierski
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;

/**
 * @date April 28, 2010
 * @author Mark Wyszomierski (markww@gmail.com)
 */
public class Response implements FoursquareType, Serializable {

	private static final long serialVersionUID = 1L;

    private String mValue;

    public Response() {
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
