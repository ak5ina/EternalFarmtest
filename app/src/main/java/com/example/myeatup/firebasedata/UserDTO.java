package com.example.myeatup.firebasedata;

import java.util.ArrayList;

public class UserDTO implements IUserDTO {

    private String name;
    private String email;
    private ArrayList<String> uploadedRecipies;
    private ArrayList<String> savedRecipies;



    public UserDTO(){

    }


    public UserDTO(String name, String email){
        this.name = name;
        this.email = email;
        uploadedRecipies = new ArrayList<>();
        savedRecipies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getUploadedRecipies(){ return uploadedRecipies; }

    public void setUploadedRecipies(ArrayList<String> uploadedRecipies) {
        this.uploadedRecipies = uploadedRecipies;
    }

    public ArrayList<String> getSavedRecipies(){ return savedRecipies; }

    public void setSavedRecipies(ArrayList<String> savedRecipies) {
        this.savedRecipies = savedRecipies;
    }




}
