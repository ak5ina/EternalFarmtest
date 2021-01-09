package com.scrippy2.myeatup.firebasedata;


import android.graphics.Bitmap;

import java.util.ArrayList;

public class RecipieDTO implements IRecipieDTO {


    private String ID;
    private String auther;
    private String name;
    private String time;
    private String price;
    private ArrayList<String> ingredientList;
    private ArrayList<String> steps;
    private ArrayList<String> voteProfiles;
    private ArrayList<Float> voteList;
    private ArrayList<String> unknownIngredients;
    private ArrayList<String> unitList;
    private ArrayList<String> unitAmount;

    public RecipieDTO(){

    }


    public RecipieDTO(String ID, String auther, String name, String time, String price){

        this.ID = ID;
        this.auther = auther;
        this.name = name;
        this.time = time;
        this.price = price;
        ingredientList = new ArrayList<>();
        steps = new ArrayList<>();
        voteProfiles = new ArrayList<>();
        voteList = new ArrayList<>();
        unknownIngredients = new ArrayList<>();
        unitList = new ArrayList<>();
        unitAmount = new ArrayList<>();



    }



    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTime() {
        return time;
    }


    public void setTime(String time) {
        this.time = time;
    }


    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public ArrayList<String> getVoteProfiles() {
        return voteProfiles;
    }

    public void setVoteProfiles(ArrayList<String> voteProfiles) {
        this.voteProfiles = voteProfiles;
    }

    public ArrayList<Float> getVoteList() {
        return voteList;
    }

    public void setVoteList(ArrayList<Float> voteList) {
        this.voteList = voteList;
    }

    public ArrayList<String> getUnitList() {
        return unitList;
    }

    public void setUnitList(ArrayList<String> unitList) {
        this.unitList = unitList;
    }

    public ArrayList<String> getUnknownIngredients() {
        return unknownIngredients;
    }

    public void setUnknownIngredients(ArrayList<String> unknownIngredients) {
        this.unknownIngredients = unknownIngredients;
    }

    public ArrayList<String> getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(ArrayList<String> unitAmount) {
        this.unitAmount = unitAmount;
    }
}
