package fr.eni.lokacar.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "car_type")
public class CarType {

    @PrimaryKey(autoGenerate = true)
    private int idCarType;
    private String label;

    public CarType() {
    }

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
}
