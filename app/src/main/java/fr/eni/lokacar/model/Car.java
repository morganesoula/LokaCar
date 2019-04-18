package fr.eni.lokacar.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "car", foreignKeys =
        {
                @ForeignKey(entity = CarType.class,
                parentColumns = "idCarType",
                childColumns = "carTypeId")
        })
public class Car implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int idCar;
    private String immatriculation;
    private float price;
    private boolean isRestore;
    private String imagePath;
    private String model;
    private int carTypeId;

    @Ignore
    public Car() {
    }

    public Car(int idCar, String immatriculation, float price, boolean isRestore, String imagePath, String model, int carTypeId) {
        this.idCar = idCar;
        this.immatriculation = immatriculation;
        this.price = price;
        this.isRestore = isRestore;
        this.imagePath = imagePath;
        this.model = model;
        this.carTypeId = carTypeId;
    }

    protected Car(Parcel in) {
        idCar = in.readInt();
        immatriculation = in.readString();
        price = in.readFloat();
        isRestore = in.readByte() != 0;
        imagePath = in.readString();
        model = in.readString();
        carTypeId = in.readInt();
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

    public int getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(int carTypeId) {
        this.carTypeId = carTypeId;
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
                ", carTypeId=" + carTypeId +
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

        parcel.writeInt(carTypeId);

    }

}
