/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckinResult implements FoursquareType, Serializable {

	private static final long serialVersionUID = 1L;

	private long mCreatedAt = 0L;
	private String mTimeZone;
	private String mShout;
	private String mMessage;
	private String mCreated;
    private String mId;
    
    private Group<Badge> mBadges;
    private String mMarkup;
    private Mayor mMayor;
    private Group<Score> mScoring;
    private Group<Special> mSpecials;
    private Venue mVenue;
    
	
    public CheckinResult() {
    }

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
    public String getShout() {
        return mShout;
    }
    public void setShout(String shout) {
    	mShout = shout;
    }
    
    public Group<Badge> getBadges() {
        return mBadges;
    }

    public void setBadges(Group<Badge> badges) {
        mBadges = badges;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMarkup() {
        return mMarkup;
    }
    
    public void setMarkup(String markup) {
        mMarkup = markup;
    }
    
    public Mayor getMayor() {
        return mMayor;
    }

    public void setMayor(Mayor mayor) {
        mMayor = mayor;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Group<Score> getScoring() {
        return mScoring;
    }

    public void setScoring(Group<Score> scoring) {
        mScoring = scoring;
    }

    public Group<Special> getSpecials() {
        return mSpecials;
    }
    
    public void setSpecials(Group<Special> specials) {
        mSpecials = specials;
    }

    public Venue getVenue() {
        return mVenue;
    }

    public void setVenue(Venue venue) {
        mVenue = venue;
    }


	@Override
	public String toString() {
		return "CheckinResult [mBadges=" + mBadges + ", mCreated=" + mCreated
				+ ", mCreatedAt=" + mCreatedAt + ", mId=" + mId + ", mMarkup="
				+ mMarkup + ", mMayor=" + mMayor + ", mMessage=" + mMessage
				+ ", mScoring=" + mScoring + ", mShout=" + mShout
				+ ", mSpecials=" + mSpecials + ", mTimeZone=" + mTimeZone
				+ ", mVenue=" + mVenue + "]";
	}    
}
