package com.scrippy2.myeatup.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;

import com.scrippy2.myeatup.MainActivity;
import com.scrippy2.myeatup.R;
import com.scrippy2.myeatup.firebasedata.IngredientDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddIngredient extends AppCompatActivity {

    GridView dialogGridview;
    GridviewAdapter gvAdapter;
    private DatabaseReference mDatabase;
    private EditText edittext;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        getWindow().setBackgroundDrawableResource(R.drawable.backgroundpic);

        getSupportActionBar().hide();
        edittext = findViewById(R.id.ingredient_search_bar);

        final ArrayList<IngredientDTO> listToReturn = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ingredients");

        //OPEN GRIDVIEW
        dialogGridview = (GridView) findViewById(R.id.gridview);
        gvAdapter = new GridviewAdapter(this,R.layout.gridview_single_object, MainActivity.INGREDIENTLIST);
        dialogGridview.setAdapter(gvAdapter);


        //OLD WAYS OF GETTING THE DATA FROM THE DATABASE
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
//
//                    IngredientDTO ingre = postSnapshot.getValue(IngredientDTO.class);
//                    String id = ingre.getID();
//                    String name = ingre.getName();
//                    listToReturn.add(new IngredientDTO(id, name));
//                }
//                gvAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mDatabase.addListenerForSingleValueEvent(postListener);
    }
}