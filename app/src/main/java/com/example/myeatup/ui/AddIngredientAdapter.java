package com.example.myeatup.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myeatup.R;

import java.util.ArrayList;

public class AddIngredientAdapter extends ArrayAdapter<RecipeIngredient> {

    private ArrayList<RecipeIngredient> listIngredients;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_ingrediens, parent, false);
        }


        ImageButton ingredientImage = convertView.findViewById(R.id.image_adapt_ingredient);
        TextView ingredientName = convertView.findViewById(R.id.text_adapt_ingredient_name);
        TextView ingredientAmountText = convertView.findViewById(R.id.text_adapt_ingredient_amount);
        EditText ingredientAmount = convertView.findViewById(R.id.edit_adapt_ingredient_amount);
        TextView ingredientUnit = convertView.findViewById(R.id.text_adapt_ingredient_unit);
        Spinner unitSpin = convertView.findViewById(R.id.spin_adapt_ingredient_unit);

        position = position + 1;

        //ingredientName.setText("Step: " + position);
        //ingredientAmount.setText(ingredientAmount.getEditableText());
        //listSteps.set();


        return convertView;
    }

    public AddIngredientAdapter(@NonNull Context context, int resource, ArrayList<RecipeIngredient> listIngredients) {
        super(context, resource, listIngredients);
        this.listIngredients = listIngredients;

    }



}
