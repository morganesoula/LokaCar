package fr.eni.lokacar.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


@Entity(tableName = "renter")
public class Renter implements Parcelable, Serializable {


    @PrimaryKey(autoGenerate = true)
    private int idRenter;
    private String name;
    private String firstname;
    private String phoneNumber;
    private String email;

    @Ignore
    public Renter(int idRenter, String name, String firstname, String phoneNumber, String email) {
        this.idRenter = idRenter;
        this.name = name;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Renter(String name, String firstname, String phoneNumber, String email) {
        this.name = name;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Ignore
    protected Renter(Parcel in) {
        idRenter = in.readInt();
        name = in.readString();
        firstname = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
    }

    public static final Creator<Renter> CREATOR = new Creator<Renter>() {
        @Override
        public Renter createFromParcel(Parcel in) {
            return new Renter(in);
        }

        @Override
        public Renter[] newArray(int size) {
            return new Renter[size];
        }
    };

    public int getIdRenter() {
        return idRenter;
    }

    public void setIdRenter(int idRenter) {
        this.idRenter = idRenter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Renter{" +
                "idRenter=" + idRenter +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idRenter);
        parcel.writeString(name);
        parcel.writeString(firstname);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
    }
}
