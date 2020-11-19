package com.scrippy2.myeatup.ui.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scrippy2.myeatup.R;

import java.util.ArrayList;

public class ViewIngredientAdapter extends ArrayAdapter<RecipeIngredient> {

    private ArrayList<RecipeIngredient> listIngredients;
    private ImageView ingredientImage;
    private TextView ingredientName;
    private TextView ingredientAmount;
    private TextView ingredientUnit;


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view_ingredient, parent, false);
        }

        ingredientImage = convertView.findViewById(R.id.image_adapt_ingredient);
        ingredientName = convertView.findViewById(R.id.text_adapt_ingredient_name);
        ingredientName.setText(listIngredients.get(position).getIngredient());
        ingredientAmount = convertView.findViewById(R.id.text_adapt_ingredient_amount);
        ingredientAmount.setText(listIngredients.get(position).getAmount());
        ingredientUnit = convertView.findViewById(R.id.text_adapt_ingredient_unit);
        ingredientUnit.setText(listIngredients.get(position).getUnit());


        return convertView;
    }



    public ViewIngredientAdapter(@NonNull Context context, int resource, ArrayList<RecipeIngredient> listIngredients) {
        super(context, resource, listIngredients);
        this.listIngredients = listIngredients;
    }

    public void setListIngredients(int position){
        ingredientName.setText(listIngredients.get(position).getIngredient());
        ingredientAmount.setText(listIngredients.get(position).getAmount());
        ingredientUnit.setText(listIngredients.get(position).getUnit());

    }

}
