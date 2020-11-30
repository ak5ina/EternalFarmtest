package com.scrippy2.myeatup.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scrippy2.myeatup.R;
import com.scrippy2.myeatup.firebasedata.IngredientDTO;

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


//        ImageView ingredientImage = convertView.findViewById(R.id.inspiration_listview_object_pic);
            TextView ingredientName = convertView.findViewById(R.id.gridview_name);
            ImageView closeBtn = convertView.findViewById(R.id.inspiration_listview_object_closebtn);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingredientList.remove(position);
                    notifyDataSetChanged();

                    Button btn = parent.getRootView().findViewById(R.id.inspiration_btn_resetrecipylist);
                    btn.callOnClick();

                }
            });

//        ingredientImage.setImageResource(ingredientList.get(position).getImageID());
            ingredientName.setText(ingredientList.get(position).getName());



        return convertView;
    }

    public IngredientAdaptor(@NonNull Context context, int resource, ArrayList<IngredientDTO> ingredientList) {
        super(context, resource, ingredientList);
        this.con = context;
        this.ingredientList = ingredientList;

    }
}
