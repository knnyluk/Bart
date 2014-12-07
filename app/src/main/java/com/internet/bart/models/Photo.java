package com.internet.bart.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 12/7/14.
 */
public class Photo implements Parcelable {
    String url;

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    public Photo() {
    }

    private Photo(Parcel in) {
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
