package fr.eni.lokacar.model;

import java.util.List;

public class StatusReport {
    private int statusReportId;
    private List<String> listImagesPathBefore;
    private List<String> listImagesPathAfter;

    public StatusReport(int statusReportId, List<String> listImagesPathBefore, List<String> listImagesPathAfter) {
        this.statusReportId = statusReportId;
        this.listImagesPathBefore = listImagesPathBefore;
        this.listImagesPathAfter = listImagesPathAfter;
    }

    public int getStatusReportId() {
        return statusReportId;
    }

    public void setStatusReportId(int statusReportId) {
        this.statusReportId = statusReportId;
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
                "statusReportId=" + statusReportId +
                ", listImagesPathBefore=" + listImagesPathBefore +
                ", listImagesPathAfter=" + listImagesPathAfter +
                '}';
    }
}
