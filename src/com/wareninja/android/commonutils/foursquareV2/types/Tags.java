/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Joe LaPenna (joe@joelapenna.com)
 */
public class Tags extends ArrayList<String> implements FoursquareType, Serializable {

	private static final long serialVersionUID = 1L;
    
    public Tags() {
        super();
    }
    
    public Tags(List<String> values) {
        super();
        addAll(values);
    }
}