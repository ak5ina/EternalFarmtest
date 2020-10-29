package com.example.myeatup.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myeatup.R;
import com.example.myeatup.firebasedata.IngredientDTO;

import java.util.ArrayList;

public class AddIngredientAdapter extends ArrayAdapter<RecipeIngredient> {

    Activity activity;

    private ArrayList<RecipeIngredient> listIngredients;


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_ingrediens, parent, false);
        }


        ImageButton ingredientImage = convertView.findViewById(R.id.image_adapt_ingredient);
        TextView ingredientName = convertView.findViewById(R.id.text_adapt_ingredient_name);
        ingredientName.setText(listIngredients.get(position).getIngredient());

        EditText ingredientAmount = convertView.findViewById(R.id.edit_adapt_ingredient_amount);

        Spinner unitSpin = convertView.findViewById(R.id.spin_adapt_ingredient_unit);



        ingredientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button btn = parent.getRootView().findViewById(R.id.recipe_btn_add_ingredient);
                btn.setText("" + position);
                btn.callOnClick();



            }
        });



        return convertView;
    }









    public AddIngredientAdapter(@NonNull Context context, int resource, ArrayList<RecipeIngredient> listIngredients) {
        super(context, resource, listIngredients);
        this.listIngredients = listIngredients;

    }



}
