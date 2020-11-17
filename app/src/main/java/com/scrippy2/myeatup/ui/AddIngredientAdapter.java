package com.scrippy2.myeatup.ui;

import android.content.Context;
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

import com.scrippy2.myeatup.R;

import java.util.ArrayList;

public class AddIngredientAdapter extends ArrayAdapter<RecipeIngredient> {


    private ArrayList<RecipeIngredient> listIngredients;
    private EditText ingredientAmount;
    private boolean newClick;


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_ingrediens, parent, false);
        }


        ImageButton ingredientImage = convertView.findViewById(R.id.image_adapt_ingredient);
        TextView ingredientName = convertView.findViewById(R.id.text_adapt_ingredient_name);
        ingredientName.setText(listIngredients.get(position).getIngredient());

        ingredientAmount = convertView.findViewById(R.id.edit_adapt_ingredient_amount);
        listIngredients.get(position).setAmount(ingredientAmount.getEditableText().toString());

        Spinner unitSpin = convertView.findViewById(R.id.spin_adapt_ingredient_unit);
        listIngredients.get(position).setUnit(unitSpin.getSelectedItem().toString());




        ingredientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button btn = parent.getRootView().findViewById(R.id.recipe_btn_add_ingredient);
                btn.setText("" + position);
                btn.callOnClick();

            }
        });

        if (newClick){
            Button btn = parent.getRootView().findViewById(R.id.recipe_btn_add_ingredient);
            btn.setText("" + (listIngredients.size() - 1));
            btn.callOnClick();
            newClick = false;
        }

        return convertView;
    }



    public AddIngredientAdapter(@NonNull Context context, int resource, ArrayList<RecipeIngredient> listIngredients) {
        super(context, resource, listIngredients);
        this.listIngredients = listIngredients;
    }


    public void setAdaptTextAmount(int position){
        listIngredients.get(position).setAmount(ingredientAmount.getEditableText().toString());
    }

    public void setNewClick(boolean newClick) {
        this.newClick = newClick;
    }
}
