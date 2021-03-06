package fr.eni.lokacar.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity(tableName = "location", foreignKeys = {
        @ForeignKey(
                onDelete = ForeignKey.CASCADE,
                entity = User.class,
                parentColumns = "idUser",
                childColumns = "userId"),
        @ForeignKey(
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE,
                entity = Car.class,
                parentColumns = "idCar",
                childColumns = "carId")
})

public class Location implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int idLocation;

    private Date dateStart;
    private Date dateEnd;
    private int userId;
    private int carId;


    public Location(int idLocation, Date dateStart, Date dateEnd, int userId, int carId) {
        this.idLocation = idLocation;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.userId = userId;
        this.carId = carId;
    }


    protected Location(Parcel in) {
        idLocation = in.readInt();
        userId = in.readInt();
        carId = in.readInt();

    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int id) {
        this.idLocation = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }


    @Override
    public String toString() {
        return "Location{" +
                "idLocation=" + idLocation +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", userId=" + userId +
                ", carId=" + carId +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
