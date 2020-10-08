package com.example.myeatup.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myeatup.R;

import java.util.ArrayList;

public class IngredientAdaptor extends ArrayAdapter<DataModel> {
    private  ArrayList<DataModel> ingredientList;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inpiration_listview_object, parent, false);
        }

//        ImageView ingredientImage = convertView.findViewById(R.id.inspiration_listview_object_pic);
        TextView ingredientName = convertView.findViewById(R.id.inspiration_listview_object_name);
        ImageView closeBtn = convertView.findViewById(R.id.inspiration_listview_object_closebtn);

//        ingredientImage.setImageResource(ingredientList.get(position).getImageID());
        ingredientName.setText(ingredientList.get(position).getName());


        return convertView;
    }

    public IngredientAdaptor(@NonNull Context context, int resource, ArrayList<DataModel> ingredientList) {
        super(context, resource, ingredientList);
        this.ingredientList = ingredientList;

    }
}
