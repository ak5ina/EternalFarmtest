package com.example.myeatup.ui;

public class RecipeIngredient {

    String ingredient;
    int amount;
    String unit;

    public RecipeIngredient(String ingredient, int amount, String unit){

        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;

    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getUnit() {
        return unit;
    }



}
