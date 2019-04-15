package fr.eni.lokacar.model;

public class CarType {

    private int id;
    private String label;

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
