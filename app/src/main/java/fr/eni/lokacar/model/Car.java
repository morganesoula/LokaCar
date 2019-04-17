package fr.eni.lokacar.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "car", foreignKeys =
        @ForeignKey(entity = CarType.class,
                parentColumns = "typeId",
                childColumns = "idCarType"))
public class Car implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int idCar;
    private String immatriculation;
    private float price;
    private boolean isRestore;
    private String imagePath;
    private String model;
    private int typeId;

    public Car(int idCar, String immatriculation, float price, boolean isRestore, String imagePath, String model, int typeId) {
        this.idCar = idCar;
        this.immatriculation = immatriculation;
        this.price = price;
        this.isRestore = isRestore;
        this.imagePath = imagePath;
        this.model = model;
        this.typeId = typeId;
    }

    protected Car(Parcel in) {
        idCar = in.readInt();
        immatriculation = in.readString();
        price = in.readFloat();
        isRestore = in.readByte() != 0;
        imagePath = in.readString();
        model = in.readString();
        typeId = in.readInt();
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
                ", typeId=" + typeId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idCar);
        parcel.writeString(immatriculation);
        parcel.writeFloat(price);
        parcel.writeByte((byte) (isRestore ? 1 : 0));
        parcel.writeString(imagePath);
        parcel.writeString(model);
        parcel.writeInt(typeId);
    }
}
