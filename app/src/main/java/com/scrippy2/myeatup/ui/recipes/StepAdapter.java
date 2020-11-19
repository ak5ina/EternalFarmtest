package com.scrippy2.myeatup.ui.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scrippy2.myeatup.R;

import java.util.ArrayList;

public class StepAdapter extends ArrayAdapter<Steps> {

    private TextView stepTextView;
    private boolean newClick;
    private ArrayList<Steps> listSteps;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_steps, parent, false);
        }

        TextView stepNum = convertView.findViewById(R.id.text_adapt_step_num);
        stepTextView = convertView.findViewById(R.id.text_adapt_step_text);

        listSteps.set(position, new Steps(stepTextView.getEditableText().toString()));
        stepNum.setText("Step: " + (position + 1));
        listSteps.get(position).setStepText(this.stepTextView.getEditableText().toString());


        return convertView;
    }

    public StepAdapter(@NonNull Context context, int resource, ArrayList<Steps> listSteps) {
        super(context, resource, listSteps);
        this.listSteps = listSteps;

    }

    public ArrayList<Steps> getList(){
        return this.listSteps;
    }

    public void setAdaptText(int position){
        listSteps.get(position).setStepText(this.stepTextView.getEditableText().toString());
    }

}
