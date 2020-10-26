package com.example.myeatup.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myeatup.R;
import com.example.myeatup.firebasedata.IngredientDTO;
import com.example.myeatup.ui.inspiration.InspirationViewModel;

import java.util.ArrayList;

public class IngredientAdaptor extends ArrayAdapter<IngredientDTO> {
    private  ArrayList<IngredientDTO> ingredientList;
    private Context con;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_single_object2, parent, false);
        }

        if (ingredientList.size() == 1){
            System.out.println(ingredientList.get(position).getName());

            ImageView closeBtn = convertView.findViewById(R.id.inspiration_listview_object_closebtn);
            closeBtn.setVisibility(View.INVISIBLE);
            TextView ingredientName = convertView.findViewById(R.id.gridview_name);
            ingredientName.setText("Add Ingredient");
            ImageView ingredientImage = convertView.findViewById(R.id.gridview_pic);
            ingredientImage.setImageResource(R.drawable.icon_plus_sign);
            ingredientImage.setPadding(20,20,20,20);
            ingredientImage.setClickable(true);
            ingredientImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Button btn = parent.getRootView().findViewById(R.id.inspiration_btn_add_ingridiant);
                    btn.callOnClick();
//
//                    Intent intent = new Intent(getContext(), AddIngredient.class);
//                    ((Activity)con).startActivityForResult(intent, 1);





                }

            });

        } else {

//        ImageView ingredientImage = convertView.findViewById(R.id.inspiration_listview_object_pic);
            TextView ingredientName = convertView.findViewById(R.id.gridview_name);
            ImageView closeBtn = convertView.findViewById(R.id.inspiration_listview_object_closebtn);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingredientList.remove(position);
                    notifyDataSetChanged();
                }
            });

//        ingredientImage.setImageResource(ingredientList.get(position).getImageID());
            ingredientName.setText(ingredientList.get(position).getName());
        }


        return convertView;
    }

    public IngredientAdaptor(@NonNull Context context, int resource, ArrayList<IngredientDTO> ingredientList) {
        super(context, resource, ingredientList);
        this.con = context;
        this.ingredientList = ingredientList;

    }
}
