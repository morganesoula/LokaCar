package fr.eni.lokacar.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@Entity(tableName = "car_type")
public class CarType implements Parcelable, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int idCarType;
    private String label;

    @Ignore
    public CarType() {
    }

    public CarType(int idCarType, String label) {
        this.idCarType = idCarType;
        this.label = label;
    }

    public static final Creator<CarType> CREATOR = new Creator<CarType>() {
        @Override
        public CarType createFromParcel(Parcel in) {
            return new CarType(in);
        }

        @Override
        public CarType[] newArray(int size) {
            return new CarType[size];
        }
    };

    public int getIdCarType() {
        return idCarType;
    }

    public void setIdCarType(int idCarType) {
        this.idCarType = idCarType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "CarType{" +
                "idCarType=" + idCarType +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(idCarType);
        parcel.writeString(label);
    }

    @Ignore
    public CarType(Parcel parcel)
    {
        idCarType = parcel.readInt();
        label = parcel.readString();
    }


}
