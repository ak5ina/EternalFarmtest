package com.example.myeatup.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.myeatup.R;
import com.example.myeatup.firebasedata.IngredientDTO;

import java.util.ArrayList;

public class AddIngredient extends AppCompatActivity {

    GridView dialogGridview;
    GridviewAdapter gvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);


        getSupportActionBar().hide();

                //OPEN GRIDVIEW
                dialogGridview = (GridView) findViewById(R.id.gridview);
                gvAdapter = new GridviewAdapter(this ,R.layout.gridview_single_object, getIngredientListFromDataBase());


                dialogGridview.setAdapter(gvAdapter);
    }

    private ArrayList<IngredientDTO> getIngredientListFromDataBase() {
        ArrayList<IngredientDTO> listToReturn = new ArrayList<>();
        listToReturn.add(new IngredientDTO("0","Appel"));
        listToReturn.add(new IngredientDTO("1","Orange"));
        listToReturn.add(new IngredientDTO("2","Pineappel"));

        return listToReturn;
    }
}