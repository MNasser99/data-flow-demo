package com.mohhafez.dataflowdemo.model;

public class CSVModel {
    private Integer numb;
    private String column1;
    private String column2;

    public CSVModel(Integer numb, String column1, String column2) {
        this.numb = numb;
        this.column1 = column1;
        this.column2 = column2;
    }

    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }
}
