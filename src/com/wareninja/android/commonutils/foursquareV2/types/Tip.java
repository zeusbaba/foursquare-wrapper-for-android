/**
 * Copyright 2009 Joe LaPenna
 */

package com.wareninja.android.commonutils.foursquareV2.types;

import java.io.Serializable;

import com.wareninja.android.commonutils.foursquareV2.util.ParcelUtils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auto-generated: 2009-11-12 21:45:35.385718
 *
 * @author Joe LaPenna (joe@joelapenna.com)
 * @author Mark Wyszomierski (markww@gmail.com), implemented Parcelable.
 */
public class Tip implements FoursquareType, Parcelable, Serializable {

	private static final long serialVersionUID = 1L;

    private String mCreated;
    private String mDistance;
    private String mId;
    private Tip.Stats mStats;
    private String mStatus;
    private String mText;
    private User mUser;
    private Venue mVenue;


    public Tip() {
    }

    private Tip(Parcel in) {
        mCreated = ParcelUtils.readStringFromParcel(in);
        mDistance = ParcelUtils.readStringFromParcel(in);
        mId = ParcelUtils.readStringFromParcel(in);
        
        if (in.readInt() == 1) {
            mStats = in.readParcelable(Tip.Stats.class.getClassLoader());
        }
        
        mStatus = ParcelUtils.readStringFromParcel(in);
        mText = ParcelUtils.readStringFromParcel(in);
        
        if (in.readInt() == 1) {
            mUser = in.readParcelable(User.class.getClassLoader());
        }
        
        if (in.readInt() == 1) {
            mVenue = in.readParcelable(Venue.class.getClassLoader());
        }
    }
    
    public static final Parcelable.Creator<Tip> CREATOR = new Parcelable.Creator<Tip>() {
        public Tip createFromParcel(Parcel in) {
            return new Tip(in);
        }

        @Override
        public Tip[] newArray(int size) {
            return new Tip[size];
        }
    };
    
    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
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
    
    public Tip.Stats getStats() {
        return mStats;
    }
    
    public void setStats(Tip.Stats stats) {
        mStats = stats;
    }
    
    public String getStatus() {
        return mStatus;
    }
    
    public void setStatus(String status) {
        mStatus = status;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
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
        ParcelUtils.writeStringToParcel(out, mDistance);
        ParcelUtils.writeStringToParcel(out, mId);
        
        if (mStats != null) {
            out.writeInt(1);
            out.writeParcelable(mStats, flags);
        } else {
            out.writeInt(0);
        }

        ParcelUtils.writeStringToParcel(out, mStatus);
        ParcelUtils.writeStringToParcel(out, mText);
        
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
    
    
    public static class Stats implements FoursquareType, Parcelable {

        private int mDoneCount;
        private int mTodoCount;
        
        public Stats() {
        }

        private Stats(Parcel in) {
            mDoneCount = in.readInt();
            mTodoCount = in.readInt();
        }
        
        public static final Parcelable.Creator<Tip.Stats> CREATOR = new Parcelable.Creator<Tip.Stats>() {
            public Tip.Stats createFromParcel(Parcel in) {
                return new Tip.Stats(in);
            }

            @Override
            public Tip.Stats[] newArray(int size) {
                return new Tip.Stats[size];
            }
        };
        
        public int getDoneCount() {
            return mDoneCount;
        }
        
        public void setDoneCount(int doneCount) {
            mDoneCount = doneCount;
        }
        
        public int getTodoCount() {
            return mTodoCount;
        }
        
        public void setTodoCount(int todoCount) {
            mTodoCount = todoCount;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mDoneCount);
            out.writeInt(mTodoCount);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
    
    public static class Status implements FoursquareType, Parcelable {

        private int mDone;
        private int mTodo;
        
        public Status() {
        }

        private Status(Parcel in) {
            mDone = in.readInt();
            mTodo = in.readInt();
        }
        
        public static final Parcelable.Creator<Tip.Status> CREATOR = new Parcelable.Creator<Tip.Status>() {
            public Tip.Status createFromParcel(Parcel in) {
                return new Tip.Status(in);
            }

            @Override
            public Tip.Status[] newArray(int size) {
                return new Tip.Status[size];
            }
        };
        
        public int getDone() {
            return mDone;
        }
        
        public void setDone(int done) {
            mDone = done;
        }
        
        public int getTodo() {
            return mTodo;
        }
        
        public void setTodo(int todo) {
            mTodo = todo;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(mDone);
            out.writeInt(mTodo);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tip [mCreated=" + mCreated + ", mDistance=" + mDistance
				+ ", mId=" + mId + ", mStats=" + mStats + ", mStatus="
				+ mStatus + ", mText=" + mText + ", mUser=" + mUser
				+ ", mVenue=" + mVenue + "]";
	}
    
    
}
