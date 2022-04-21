package com.mohhafez.dataflowdemo.model;

import java.util.Objects;

public class DataModel {
    String collation;
    Integer processLines;
    Boolean toDownload;

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public Integer getProcessLines() {
        return processLines;
    }

    public void setProcessLines(Integer processLines) {
        this.processLines = processLines;
    }

    public Boolean getToDownload() {
        return toDownload;
    }

    public void setToDownload(Boolean toDownload) {
        this.toDownload = toDownload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModel dataModel = (DataModel) o;
        return Objects.equals(collation, dataModel.collation) && Objects.equals(processLines, dataModel.processLines) && Objects.equals(toDownload, dataModel.toDownload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collation, processLines, toDownload);
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "collation='" + collation + '\'' +
                ", processLines=" + processLines +
                ", toDownload=" + toDownload +
                '}';
    }
}
