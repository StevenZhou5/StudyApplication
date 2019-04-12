package com.example.myservice;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    private String userName;

    public UserInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
    }

    public void readFromParcel(Parcel parcel) {
        userName = parcel.readString();
    }

    protected UserInfo(Parcel in) {
        this.userName = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
