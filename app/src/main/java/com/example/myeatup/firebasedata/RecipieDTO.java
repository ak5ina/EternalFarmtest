package com.example.myeatup.firebasedata;


import java.util.ArrayList;

public class RecipieDTO implements IRecipieDTO {


    private String ID;
    private String name;
    private int time;
    private int price;
    private ArrayList<String> ingredientList;
    private ArrayList<String> steps;
    private ArrayList<String> voteProfiles;
    private ArrayList<Integer> voteList;
    private ArrayList<String> unknownIngredients;
    private ArrayList<UnitDTO> unitList;
    private ArrayList<Integer> unitAmount;

    public RecipieDTO(){

    }


    public RecipieDTO(String ID, String name, int time, int price){

        this.ID = ID;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
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

    public ArrayList<Integer> getVoteList() {
        return voteList;
    }

    public void setVoteList(ArrayList<Integer> voteList) {
        this.voteList = voteList;
    }

    public ArrayList<UnitDTO> getUnitList() {
        return unitList;
    }

    public void setUnitList(ArrayList<UnitDTO> unitList) {
        this.unitList = unitList;
    }

    public ArrayList<String> getUnknownIngredients() {
        return unknownIngredients;
    }

    public void setUnknownIngredients(ArrayList<String> unknownIngredients) {
        this.unknownIngredients = unknownIngredients;
    }

    public ArrayList<Integer> getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(ArrayList<Integer> unitAmount) {
        this.unitAmount = unitAmount;
    }
}
