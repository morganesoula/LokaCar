package fr.eni.lokacar.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


@Entity(tableName = "user")
public class User implements Parcelable, Serializable {


    @PrimaryKey(autoGenerate = true)
    private int idUser;
    private String name;
    private String firstname;
    private String phoneNumber;
    private String email;

    @Ignore
    public User(int idUser, String name, String firstname, String phoneNumber, String email) {
        this.idUser = idUser;
        this.name = name;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String name, String firstname, String phoneNumber, String email) {
        this.name = name;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Ignore
    protected User(Parcel in) {
        idUser = in.readInt();
        name = in.readString();
        firstname = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
        return "User{" +
                "idUser=" + idUser +
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
        parcel.writeInt(idUser);
        parcel.writeString(name);
        parcel.writeString(firstname);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
    }
}
