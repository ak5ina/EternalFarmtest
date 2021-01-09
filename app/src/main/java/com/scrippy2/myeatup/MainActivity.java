package com.scrippy2.myeatup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrippy2.myeatup.firebasedata.IngredientDTO;
import com.scrippy2.myeatup.ui.GridviewAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<IngredientDTO> INGREDIENTLIST;

    public static ArrayList<IngredientDTO> ReturnIngreList(){
        return INGREDIENTLIST;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INGREDIENTLIST = new ArrayList<>();

        //Storage storage = new Storage();
        //storage.upload("C:/Users/Andreas/Desktop/EUFBtest.PNG", "EUFBtest.PNG");
        //storage.download("EUFBtest.png", "gs://eatupdatabase-cbe42.appspot.com/EUFBtest.png");


        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getSupportActionBar().hide();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_inspiration, R.id.navigation_recipe, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ingredients");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    IngredientDTO ingre = postSnapshot.getValue(IngredientDTO.class);

                    for (DataSnapshot secoundChild: postSnapshot.child("recipeUses").getChildren()){
                        ingre.getRecipies().add(secoundChild.getValue().toString());
                    }

                    INGREDIENTLIST.add(ingre);
                    System.out.println(ingre.getRecipies().size());

                }

                Collections.sort(INGREDIENTLIST, new Comparator<IngredientDTO>() {
                    @Override
                    public int compare(IngredientDTO o1, IngredientDTO o2) {

                        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);





    }





}