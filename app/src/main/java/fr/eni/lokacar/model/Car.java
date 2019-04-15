package fr.eni.lokacar.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = "car")
public class Car {

    @PrimaryKey(autoGenerate = true)
    private int idCar;
    private String immatriculation;
    private float price;
    private boolean isRestore;
    private String imagePath;
    private String model;
    @Embedded
    private CarType type;

    @Ignore
    public Car() {
    }

    public Car(int idCar, String immatriculation, float price, boolean isRestore, String imagePath, String model, CarType type) {
        this.idCar = idCar;
        this.immatriculation = immatriculation;
        this.price = price;
        this.isRestore = isRestore;
        this.imagePath = imagePath;
        this.model = model;
        this.type = type;
    }

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

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
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
                ", type=" + type +
                '}';
    }
}
