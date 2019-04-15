package fr.eni.lokacar.model;

import fr.eni.lokacar.model.CarType;

public class Car {

    private int id;
    private String immatriculation;
    private float prix;
    private boolean isRestore;
    private String imagePath;
    private CarType type;

    public Car() {
    }

    public Car(int id, String immatriculation, float prix, boolean isRestore, String imagePath, CarType type) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.prix = prix;
        this.isRestore = isRestore;
        this.imagePath = imagePath;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
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

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", immatriculation='" + immatriculation + '\'' +
                ", prix=" + prix +
                ", isRestore=" + isRestore +
                ", imagePath='" + imagePath + '\'' +
                ", type=" + type +
                '}';
    }
}
