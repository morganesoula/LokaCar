package fr.eni.lokacar.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "car_type")
public class CarType implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int idCarType;
    private String label;

    @Ignore
    public CarType() {
    }

    public CarType(String label)
    {
        this.label = label;
    }

    @Ignore
    public CarType(int id, String label) {
        this.idCarType = id;
        this.label = label;
    }

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

    @Ignore
    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel parcel) {
            return new Car(parcel);
        }

        @Override
        public Car[] newArray(int i) {
            return new Car[0];
        }
    };
}
