package com.scrippy2.myeatup.ui.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    private Button collection_btn;
    private RatingBar ratingBar;
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
        ratingBar = findViewById(R.id.rating_bar);
        collection_btn = findViewById(R.id.add_collect);
        storage.download(id, photo);


        list_step = findViewById(R.id.list_steps_view);
        stepAdapter = new ViewStepAdapter(this, R.layout.adapter_steps_view, stepObjects);
        list_step.setAdapter(stepAdapter);



        list_ingredint = findViewById(R.id.list_add_ingridient_view);
        ingredientAdapter = new ViewIngredientAdapter(this, R.layout.adapter_view_ingredient, ingredientObjects);
        list_ingredint.setAdapter(ingredientAdapter);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        //editor.clear().apply();


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float v, boolean b) {

                Toast.makeText(getApplicationContext(), "Rating " + v, Toast.LENGTH_SHORT).show();

                ArrayList<String> profileIDs = new ArrayList<>();
                ArrayList<Float> voteList = new ArrayList<>();

                if (FirebaseAuth.getInstance().getCurrentUser().getUid() == null){
                    Toast.makeText(getApplicationContext(), "You must login to rate recipes ", Toast.LENGTH_SHORT).show();
                }
                else {

                    String testuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    if (recipieDTO.getVoteProfiles() == null) {
                        profileIDs.add(testuser);
                        recipieDTO.setVoteProfiles(profileIDs);
                        voteList.add(v);
                        recipieDTO.setVoteList(voteList);
                        Toast.makeText(getApplicationContext(), "First ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        profileIDs = recipieDTO.getVoteProfiles();
                        voteList = recipieDTO.getVoteList();
                        boolean fundID = false;
                        for (int i = 0;i < recipieDTO.getVoteProfiles().size();i++){
                            if (recipieDTO.getVoteProfiles().get(i).equals(testuser)){
                                fundID = true;
                                voteList.set(i, v);
                                recipieDTO.setVoteList(voteList);
                                Toast.makeText(getApplicationContext(), "Second ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (!fundID){
                            profileIDs.add(testuser);
                            voteList.add(v);
                            recipieDTO.setVoteProfiles(profileIDs);
                            recipieDTO.setVoteList(voteList);
                            Toast.makeText(getApplicationContext(), "Third ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    mDatabase.child("recipies").child(id).child("voteList").setValue(voteList);
                    mDatabase.child("recipies").child(id).child("voteProfiles").setValue(profileIDs);
                }

            }
        });


        collection_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!preferences.contains(id)){

                    editor.putString(id,"saved");

                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Added recipe to your collection\nFind it in profile", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Recipe is allready in your collection", Toast.LENGTH_SHORT).show();

                }

            }
        });



        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //System.out.println(id + "---------------");
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