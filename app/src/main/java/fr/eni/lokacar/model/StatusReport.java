package fr.eni.lokacar.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity (tableName = "entity")
public class StatusReport {

    @PrimaryKey (autoGenerate = true)
    private int statusId;
    private List<String> listImagesPathBefore;
    private List<String> listImagesPathAfter;


    public StatusReport(int statusId, List<String> listImagesPathBefore, List<String> listImagesPathAfter) {
        this.statusId = statusId;
        this.listImagesPathBefore = listImagesPathBefore;
        this.listImagesPathAfter = listImagesPathAfter;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusReportId) {
        this.statusId = statusReportId;
    }

    public List<String> getListImagesPathBefore() {
        return listImagesPathBefore;
    }

    public void setListImagesPathBefore(List<String> listImagesPathBefore) {
        this.listImagesPathBefore = listImagesPathBefore;
    }

    public List<String> getListImagesPathAfter() {
        return listImagesPathAfter;
    }

    public void setListImagesPathAfter(List<String> listImagesPathAfter) {
        this.listImagesPathAfter = listImagesPathAfter;
    }

    @Override
    public String toString() {
        return "StatusReport{" +
                "statusId=" + statusId +
                ", listImagesPathBefore=" + listImagesPathBefore +
                ", listImagesPathAfter=" + listImagesPathAfter +
                '}';
    }
}
