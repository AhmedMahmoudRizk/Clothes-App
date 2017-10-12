package com.example.ahmedrizk.clothesownerapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahmed Rizk on 19/08/2017.
 */
public class product implements Parcelable {

    private String price;
    private String id;
    private String image;
    private String size;
    private String colors;
    private String phone;

    public product(String price, String id, String image, String size, String colors, String phone) {
        this.price = price;
        this.id = id;
        this.image = image;
        this.size = size;
        this.colors = colors;
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getSize() {
        return size;
    }

    public String getColors() {
        return colors;
    }

    public String getPhone() {
        return phone;
    }

    protected product(Parcel in) {
    }

    public static final Creator<product> CREATOR = new Creator<product>() {
        @Override
        public product createFromParcel(Parcel in) {
            return new product(in);
        }

        @Override
        public product[] newArray(int size) {
            return new product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(price);
        parcel.writeString(id);
        parcel.writeString(size);
        parcel.writeString(colors);
        parcel.writeString(phone);
        parcel.writeString(image);
    }
}
