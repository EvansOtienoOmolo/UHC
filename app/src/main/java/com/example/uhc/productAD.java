package com.example.uhc;

public class productAD {
    private String County, Hospital, Department, Comment, Date;
    private double Serial;

    public productAD(){

    }

    public productAD(String county, String hospital, String department, String comment, double serial, String date) {
        this.County = county;
        this.Hospital = hospital;
        this.Department = department;
        this.Comment = comment;
        this.Serial = serial;
        this.Date = date;
    }

    public String getCounty() { return County; }

    public String getHospital() {
        return Hospital;
    }

    public String getDepartment() {
        return Department;
    }

    public String getComment() {
        return Comment;
    }

    public double getSerial() {
        return Serial;
    }

    public String getDate() { return Date; }


}
