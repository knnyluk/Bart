package com.internet.bart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseUser;

/**
 * Created on 12/3/14.
 */
public class User implements Parcelable {
    String objectId, username;

    public User() {
    }

    public User(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }
//
//    public String toString() {
//        return getObjectId();
//    }

    public String toString() {
        return getObjectId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.objectId);
    }

    private User(Parcel in) {
        this.objectId = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
