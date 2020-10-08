package com.example.myeatup.ui;

public class DataModel {
    private String name;
    private int imageID;

    public DataModel(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }


    public int getImageID() {
        return imageID;
    }

}
