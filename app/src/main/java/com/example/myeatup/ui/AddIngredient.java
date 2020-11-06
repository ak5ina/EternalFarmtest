package com.example.myeatup.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.myeatup.R;
import com.example.myeatup.firebasedata.IngredientDTO;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);


        getSupportActionBar().hide();

                //OPEN GRIDVIEW
                dialogGridview = (GridView) findViewById(R.id.gridview);
                gvAdapter = new GridviewAdapter(this ,R.layout.gridview_single_object, getIngredientListFromDataBase());


                dialogGridview.setAdapter(gvAdapter);
                gvAdapter.notifyDataSetChanged();
    }

    private ArrayList<IngredientDTO> getIngredientListFromDataBase() {

        final ArrayList<IngredientDTO> listToReturn = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ingredients");


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    IngredientDTO ingre = postSnapshot.getValue(IngredientDTO.class);
                    String id = ingre.getID();
                    String name = ingre.getName();
                    listToReturn.add(new IngredientDTO(id, name));
                    System.out.println(listToReturn.get(i).getName() + i + "inder");
                    i++;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);



        //listToReturn.add(new IngredientDTO("0","Appel"));
        //listToReturn.add(new IngredientDTO("1","Orange"));
        //listToReturn.add(new IngredientDTO("2","Pineappel"));
        //for(int i = 0; i < listToReturn.size();i++){
        //    System.out.println(listToReturn.get(i).getName() + i + "ydre");

       // }




        return listToReturn;
    }
}