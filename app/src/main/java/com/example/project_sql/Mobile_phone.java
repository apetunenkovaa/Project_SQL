package com.example.project_sql;
import android.os.Parcel;
import android.os.Parcelable;





public class Mobile_phone implements Parcelable {

    private int ID;
    private String Manufacturer;
    private String Model;
    private int Storage;
    private String Color;
    private int Cost;
    private String Image;

    protected Mobile_phone(Parcel in) {
        ID = in.readInt();
        Manufacturer = in.readString();
        Model = in.readString();
        Storage = in.readInt();
        Color = in.readString();
        Cost = in.readInt();
        Image = in.readString();
    }

    public static final Creator<Mobile_phone> CREATOR = new Creator<Mobile_phone>() {
        @Override
        public Mobile_phone createFromParcel(Parcel in) {
            return new Mobile_phone(in);
        }

        @Override
        public Mobile_phone[] newArray(int size) {
            return new Mobile_phone[size];
        }
    };


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public void setModel(String model) {
        Model = model;
    }

    public void setStorage(int storage) {
        Storage = storage;
    }

    public void setColor(String color) {
        Color = color;
    }
    public void setCost(int cost) {
        Cost = cost;
    }

    public int getID() {
        return ID;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getManufacturer() {
        return Manufacturer;
    }
    public String getModel() {
        return Model;
    }
    public int getStorage() {
        return Storage;
    }
    public String getColor() {
        return Color;
    }
    public int getCost() {
        return Cost;
    }
    public String getImage() {
        return Image;
    }

    public Mobile_phone(int ID, String manufacturer,String model,int storage, String color,int cost,String image) {
        this.ID = ID;
        Manufacturer = manufacturer;
        Model = model;
        Storage = storage;
        Color = color;
        Cost = cost;
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Manufacturer);
        dest.writeString(Model);
        dest.writeInt(Storage);
        dest.writeString(Color);
        dest.writeInt(Cost);
        dest.writeString(Image);
    }




}