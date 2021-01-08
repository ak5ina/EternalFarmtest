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
import com.scrippy2.myeatup.firebasedata.RecipieDTO;
import com.scrippy2.myeatup.firebasedata.Storage;

import java.util.ArrayList;

public class GridviewAdapter_Recipy extends ArrayAdapter<RecipieDTO> {
    private  ArrayList<RecipieDTO> recipyList;
    private Context con;
    private RecipieDTO viewID;


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_recipe_object, parent, false);
            TextView tvTime = convertView.findViewById(R.id.gridview_time_recipy);
            TextView tvPrice = convertView.findViewById(R.id.gridview_price_recipy);
            TextView tvRating = convertView.findViewById(R.id.gridview_rating_recipy);
            TextView tvName = convertView.findViewById(R.id.gridview_name_recipy);
            ImageView ivImage = convertView.findViewById(R.id.gridview_pic_recipy);
            Storage storage = new Storage();
            storage.download(recipyList.get(position).getID(), ivImage);

            double avargaRating = 0;
            double combinedValue = 0;

            if (recipyList.get(position).getVoteList() == null){
                tvRating.setText("?");
            }
            else {
                for (int i = 0; i < recipyList.get(position).getVoteList().size(); i++){
                    combinedValue = combinedValue += Double.parseDouble(recipyList.get(position).getVoteList().get(i).toString());
                }

                if (combinedValue > 0){
                    avargaRating = (combinedValue/recipyList.get(position).getVoteList().size());
                }

            }


            tvTime.setText(recipyList.get(position).getTime());
            tvPrice.setText(recipyList.get(position).getPrice());
            tvRating.setText(Double.toString(avargaRating));
            tvName.setText(recipyList.get(position).getName());

            convertView.isClickable();
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btn_view_reciep = parent.getRootView().findViewById(R.id.btn_view_recipe_frag);
                    System.out.println(recipyList.get(position).getName());
                    viewID = recipyList.get(position);
                    btn_view_reciep.setText(viewID.getID());
                    btn_view_reciep.callOnClick();
                }
            });
        }


        return convertView;
    }

    public GridviewAdapter_Recipy(@NonNull Context context, int resource, ArrayList<RecipieDTO> ingredientList) {
        super(context, resource, ingredientList);
        this.con = context;
        this.recipyList = ingredientList;

    }

    public GridviewAdapter_Recipy(@NonNull Context context, int resource, ArrayList<RecipieDTO> ingredientList, RecipieDTO recipieDTO) {
        super(context, resource, ingredientList);
        this.con = context;
        this.recipyList = ingredientList;
        this.viewID = recipieDTO;

    }

    public RecipieDTO getViewRecipe(){
        return viewID;
    }
}
