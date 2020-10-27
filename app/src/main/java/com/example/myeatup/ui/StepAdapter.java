package com.example.myeatup.ui;

import android.content.Context;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myeatup.R;

import java.util.ArrayList;

public class StepAdapter extends ArrayAdapter<Steps> {

    private TextView stepText;

    private ArrayList<Steps> listSteps;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_steps, parent, false);
        }

        TextView stepNum = convertView.findViewById(R.id.text_adapt_step_num);
        stepText = convertView.findViewById(R.id.text_adapt_step_text);

        position = position + 1;

        stepNum.setText("Step: " + position);
        stepText.setText(stepText.getEditableText());
        listSteps.set(position - 1, new Steps(stepText.getEditableText().toString()));



        return convertView;
    }

    public StepAdapter(@NonNull Context context, int resource, ArrayList<Steps> listSteps) {
        super(context, resource, listSteps);
        this.listSteps = listSteps;

    }

    /*
    public Editable getStepText() {
        return stepText.getEditableText();
    }
*/

    public void update (int position, @Nullable View convertView, @NonNull final ViewGroup parent){


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_steps, parent, false);
        }

        //TextView stepNum = convertView.findViewById(R.id.text_adapt_step_num);
        stepText = convertView.findViewById(R.id.text_adapt_step_text);

        //position = position + 1;

        //stepNum.setText("Step: " + position);
        stepText.setText(stepText.getEditableText());
        listSteps.set(position - 1, new Steps(stepText.getEditableText().toString()));



    }
/*
    public getArrayList (){
        return listSteps;
    }
*/
}
