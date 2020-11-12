package com.example.myeatup.ui;

import android.text.Editable;
import android.widget.EditText;

public class Steps {

    private String stepNum;
    private String stepText;

    public Steps(String stepText){

        //this.stepNum = stepNum;
        this.stepText = stepText;

    }

    public Steps(){

    }


    public String getStepNum() {
        return stepNum;
    }

    public void setStepNum(String stepNum) {
        this.stepNum = stepNum;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }


}
