/***
	Copyright (c) 2010-2011 WareNinja.com
	Author: yg@wareninja.com
*/

package com.wareninja.android.commonutils.foursquareV2;

public class FSQ_CONSTANTS {

	public static final boolean DEBUG = LOGGING.DEBUG;// enable/disable logging
	
	public static final String PACKAGE_APPSTORE = "ANDROID";//"AMAZON";// "ANDROID";
	
	public static final String PACKAGE_NAME = "com.wareninja.android.mayormonster";
	public static final String APP_MARKET_URL = "market://details?id=" + PACKAGE_NAME;
	public static final String WARENINJAAPPS_MARKET_URL = "market://search?q=wareninja";
	
	public static final String AMAZON_APP_MARKET_URL = 
		"http://www.amazon.com/gp/mas/dl/android/" + PACKAGE_NAME;
	public static final String AMAZON_WARENINJAAPPS_MARKET_URL = 
		"http://www.amazon.com/gp/mas/dl/android/com.wareninja.android";
	
	public static final String GRAPH_BASE_URL = "http://graph.facebook.com/";
	public static final String GRAPH_BASE_URL_SSL = "https://graph.facebook.com/";
	
    // used to inform user ONCE per login, about other WareNinja apps
    public static final String WARENINJAAPPS_DISPLAYPOPUP = "WARENINJAAPPS_DISPLAYPOPUP";
    public static final String WARENINJAAPPS_DISPLAYPOPUP_ALREADYDONE = "WARENINJAAPPS_DISPLAYPOPUP_ALREADYDONE";
    
    
    
}
