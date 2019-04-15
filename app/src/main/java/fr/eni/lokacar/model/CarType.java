package fr.eni.lokacar.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CarType {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String label;

    @Ignore
    public CarType() {
    }

    public CarType(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
