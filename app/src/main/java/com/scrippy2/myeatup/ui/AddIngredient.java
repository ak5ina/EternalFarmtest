package com.scrippy2.myeatup.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;

import com.scrippy2.myeatup.MainActivity;
import com.scrippy2.myeatup.R;
import com.scrippy2.myeatup.firebasedata.IngredientDTO;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;

public class AddIngredient extends AppCompatActivity {

    GridView dialogGridview;
    GridviewAdapter gvAdapter;
    private DatabaseReference mDatabase;
    private EditText edittext;
    private ArrayList<IngredientDTO> listForIngre;
    private ArrayList<IngredientDTO> listForIngre2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundpic);

        getSupportActionBar().hide();
        edittext = findViewById(R.id.ingredient_search_bar);


        //OPEN GRIDVIEW

        StartGridView();

        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0)
                    try {
                        UpdateGridView();
                    } catch (Exception e){
                        System.out.println("FAIL");
                        System.out.println(e);
                    }
                else {
                    UpdateGridView();
                }
            }
        });

    }

    private void StartGridView(){
        listForIngre = new ArrayList<IngredientDTO>(MainActivity.INGREDIENTLIST);
        listForIngre2 = MainActivity.INGREDIENTLIST;



        dialogGridview = (GridView) findViewById(R.id.gridview);
        gvAdapter = new GridviewAdapter(this,R.layout.gridview_single_object, listForIngre);
        dialogGridview.setAdapter(gvAdapter);
    }

    private void UpdateGridView(){

        System.out.println(listForIngre2.size());
        gvAdapter.clear();

        for (IngredientDTO ing : listForIngre2){
            System.out.println(ing.getName().toLowerCase());
            System.out.println(ing.getName().toLowerCase().startsWith(edittext.getText().toString().toLowerCase()));
            if (ing.getName().toLowerCase().startsWith(edittext.getText().toString().toLowerCase()) || edittext.toString().toLowerCase().length() == 0){
                gvAdapter.add(ing);
            }
        }

        gvAdapter.notifyDataSetChanged();
        System.out.println("HEJ fra updategridview");

    }
}