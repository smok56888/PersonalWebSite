package com.smok.web.model;

import java.util.Date;

/**
 * Created by smok on 2016/1/5.
 */
public class ExcelTestModel {
    private long id;
    private String name;
    private String date;
    private int success;
    private Date addTime;

    public ExcelTestModel() {
    }

    public ExcelTestModel(long id, String name, String date, int success, Date addTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.success = success;
        this.addTime = addTime;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
