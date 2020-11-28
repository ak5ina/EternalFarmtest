package com.scrippy2.myeatup.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrippy2.myeatup.R;
import com.scrippy2.myeatup.firebasedata.IngredientDTO;
import com.scrippy2.myeatup.firebasedata.RecipieDTO;
import com.scrippy2.myeatup.firebasedata.Storage;

import java.io.File;
import java.util.ArrayList;

public class ViewRecipe extends AppCompatActivity {

    private ArrayList<Steps> stepObjects = new ArrayList();
    private ListView list_step;
    private ViewStepAdapter stepAdapter;
    private ListView list_ingredint;
    private ArrayList<RecipeIngredient> ingredientObjects = new ArrayList();
    private ViewIngredientAdapter ingredientAdapter;
    private DatabaseReference mDatabase;
    private String id;
    private RecipieDTO recipieDTO;
    private IngredientDTO ingredientDTO;
    private TextView recipeName;
    private TextView time;
    private TextView price;
    private ImageView photo;
    private Storage storage = new Storage();
    private Uri returnUri;
    private File localFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        getSupportActionBar().hide();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        id = getIntent().getStringExtra("Recipe");



        recipeName = findViewById(R.id.text_recipe_name_view);
        time = findViewById(R.id.text_time_view);
        price = findViewById(R.id.test_price_view);
        photo = findViewById(R.id.image_view);
        storage.download(id, photo);


        list_step = findViewById(R.id.list_steps_view);
        stepAdapter = new ViewStepAdapter(this, R.layout.adapter_steps_view, stepObjects);
        list_step.setAdapter(stepAdapter);



        list_ingredint = findViewById(R.id.list_add_ingridient_view);
        ingredientAdapter = new ViewIngredientAdapter(this, R.layout.adapter_view_ingredient, ingredientObjects);
        list_ingredint.setAdapter(ingredientAdapter);



        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                System.out.println(id + "---------------");
                recipieDTO = dataSnapshot.child("recipies").child(id).getValue(RecipieDTO.class);


                recipeName.setText(recipieDTO.getName());
                time.setText(recipieDTO.getTime() + " minutes");
                price.setText(recipieDTO.getPrice());


                for (int i = 0;i < recipieDTO.getIngredientList().size();i++) {

                    ingredientDTO = dataSnapshot.child("ingredients").child(recipieDTO.getIngredientList().get(i)).getValue(IngredientDTO.class);

                    ingredientObjects.add(new RecipeIngredient(recipieDTO.getIngredientList().get(i),
                            ingredientDTO.getName(),
                             recipieDTO.getUnitAmount().get(i),
                             recipieDTO.getUnitList().get(i)));
                    //ingredientAdapter.setListIngredients(i);
                    System.out.println(recipieDTO.getUnitAmount().get(i) + "!!!!!!!!!");
                    ingredientAdapter.notifyDataSetChanged();

                }
                for (int i = 0;i < recipieDTO.getSteps().size();i++){
                    stepObjects.add(new Steps(recipieDTO.getSteps().get(i)));
                }

                ViewGroup.LayoutParams paramsStep = list_step.getLayoutParams();
                paramsStep.height = 100 * stepAdapter.getCount();
                list_step.setLayoutParams(paramsStep);

                ViewGroup.LayoutParams paramsIngre = list_ingredint.getLayoutParams();
                paramsIngre.height = 140 * ingredientAdapter.getCount();
                list_ingredint.setLayoutParams(paramsIngre);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);



    }
}