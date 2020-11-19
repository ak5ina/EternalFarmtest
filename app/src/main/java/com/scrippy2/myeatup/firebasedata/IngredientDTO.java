package com.scrippy2.myeatup.firebasedata;


import com.scrippy2.myeatup.ui.recipes.RecipeIngredient;

import java.util.ArrayList;

public class IngredientDTO implements IIngredientDTO {

    private String ID;
    private String name;
    private ArrayList<String> recipies;


    public IngredientDTO(){

        recipies = new ArrayList<>();
    }

    public IngredientDTO(String ID, String name){
        this.ID = ID;
        this.name = name;
        recipies = new ArrayList<>();
    }

    public RecipeIngredient toRecipeIngregient(){
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(this.ID);
        recipeIngredient.setIngredient(this.name);
        return recipeIngredient;
    }



    public String getID(){ return ID; }

    public void setID(String ID){ this.ID = ID;}

    public String getName() { return name; }

    public void setName(String name) { this.name= name; }

    public ArrayList<String> getRecipies() {
        return recipies;
    }

    public void setRecipies(ArrayList<String> recipies) {
        this.recipies = recipies;
    }
}
