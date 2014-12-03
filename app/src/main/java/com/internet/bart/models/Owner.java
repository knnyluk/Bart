package com.internet.bart.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 12/3/14.
 */
public class Owner implements Parcelable {
    String objectId;

    public Owner() {
    }

    public String getObjectId() {
        return objectId;
    }
//
//    public String toString() {
//        return getObjectId();
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.objectId);
    }

    private Owner(Parcel in) {
        this.objectId = in.readString();
    }

    public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
        public Owner createFromParcel(Parcel source) {
            return new Owner(source);
        }

        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
}
