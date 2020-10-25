package com.example.myeatup.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myeatup.R;
import com.example.myeatup.firebasedata.RecipieDTO;
import com.example.myeatup.ui.UnitSpinnerAdapter;
import com.example.myeatup.ui.AddIngredientAdapter;
import com.example.myeatup.ui.RecipeIngredient;
import com.example.myeatup.ui.StepAdapter;
import com.example.myeatup.ui.Steps;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    private ArrayAdapter<Steps> itemsAdapter;
    private int counterSteps;
    private int counterIngredients;
    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        getSupportActionBar().hide();


        final EditText recipeName = findViewById(R.id.edit_recipe_name);


        Button add_step = findViewById(R.id.btn_add_step);
        final ArrayList stepObjects = new ArrayList();
        final StepAdapter stepAdapter = new StepAdapter(this, R.layout.adapter_steps, stepObjects);
        final ListView list_step = findViewById(R.id.list_steps);
        list_step.setAdapter(stepAdapter);



        Button add_ingredient = findViewById(R.id.btn_add_ingrident);
        final ArrayList ingredientObjects = new ArrayList();
        final AddIngredientAdapter ingredientAdapter = new AddIngredientAdapter(this, R.layout.adapter_ingrediens, ingredientObjects);
        final ListView list_ingredint = findViewById(R.id.list_add_ingridient);
        list_ingredint.setAdapter(ingredientAdapter);



        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_ingrediens,null);
//        final TextView unitText = findViewById(R.id.text_adapt_ingredient_unit);
        final ArrayList<String> units = new ArrayList<String>(){};
        units.add("dL");
        units.add("g");
        units.add("Stk");
        units.add("spsk");
        units.add("tsk");
        UnitSpinnerAdapter spinAdapter = new UnitSpinnerAdapter(this, android.R.layout.simple_spinner_item, units);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner;
        spinner = view.findViewById(R.id.spin_adapt_ingredient_unit);
        spinner.setAdapter(spinAdapter);


        Button upload = findViewById(R.id.btn_upload);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RecipieDTO recipe = new RecipieDTO();
                recipe.setID("3");
                recipe.setName(recipeName.getText().toString());
                stepAdapter.notifyDataSetChanged();
                recipe.setSteps(stepObjects);


                mDatabase.child("recipies").child("3").setValue(recipe);

            }
        });






        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //unitText.setText(units.get(i - 1));
                //ingredientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        add_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counterSteps = counterSteps + 1;
                ViewGroup.LayoutParams params = list_step.getLayoutParams();
                params.height = 130 * counterSteps;
                list_step.setLayoutParams(params);

                stepAdapter.add(new Steps("test"));
                stepAdapter.notifyDataSetChanged();
            }
        });


        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counterIngredients = counterIngredients + 1;
                ViewGroup.LayoutParams params = list_ingredint.getLayoutParams();
                params.height = 130 * counterIngredients;
                list_ingredint.setLayoutParams(params);

                ingredientAdapter.add(new RecipeIngredient("Ingredient", 0, "unit"));
                ingredientAdapter.notifyDataSetChanged();

            }
        });






    }
}