package fr.eni.lokacar.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = "car")
public class Car implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int idCar;
    private String immatriculation;
    private float price;
    private boolean isRestore;
    private String imagePath;
    private String model;

    @Embedded
    private CarType carType;

    @Ignore
    public Car() {
    }

    public Car(int idCar, String immatriculation, float price, boolean isRestore, String imagePath, String model, CarType carType) {
        this.idCar = idCar;
        this.immatriculation = immatriculation;
        this.price = price;
        this.isRestore = isRestore;
        this.imagePath = imagePath;
        this.model = model;
        this.carType = carType;
    }


    protected Car(Parcel in) {
        idCar = in.readInt();
        immatriculation = in.readString();
        price = in.readFloat();
        isRestore = in.readByte() != 0;
        imagePath = in.readString();
        model = in.readString();
        carType = in.readParcelable(CarType.class.getClassLoader());
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isRestore() {
        return isRestore;
    }

    public void setRestore(boolean restore) {
        isRestore = restore;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "idCar=" + idCar +
                ", immatriculation='" + immatriculation + '\'' +
                ", price=" + price +
                ", isRestore=" + isRestore +
                ", imagePath='" + imagePath + '\'' +
                ", model='" + model + '\'' +
                ", carType=" + carType +
                '}';
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(idCar);
        parcel.writeString(immatriculation);
        parcel.writeFloat(price);

        // Pour écrire un boolean
        parcel.writeValue(isRestore);
        parcel.writeString(imagePath);
        parcel.writeString(model);

        parcel.writeTypedObject(carType, 0);

    }

}
