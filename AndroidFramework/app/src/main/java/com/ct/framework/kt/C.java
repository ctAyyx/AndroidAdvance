package com.ct.framework.kt;

import android.os.Parcel;
import android.os.Parcelable;

public class C implements Parcelable {

    public String first;
    public int xx;

    public C() {
    }

    protected C(Parcel in) {
        first = in.readString();
        xx = in.readInt();
    }

    public static final Creator<C> CREATOR = new Creator<C>() {
        @Override
        public C createFromParcel(Parcel in) {
            return new C(in);
        }

        @Override
        public C[] newArray(int size) {
            return new C[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(first);
        dest.writeInt(xx);
    }
}
