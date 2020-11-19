package com.scrippy2.myeatup.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.scrippy2.myeatup.R;
import com.scrippy2.myeatup.ui.AddIngredientAdapter;
import com.scrippy2.myeatup.ui.RecipeIngredient;
import com.scrippy2.myeatup.ui.StepAdapter;
import com.scrippy2.myeatup.ui.Steps;

import java.util.ArrayList;

public class ViewRecipe extends AppCompatActivity {

    private ArrayList<Steps> stepObjects = new ArrayList();
    private ListView list_step;
    private StepAdapter stepAdapter;
    private ListView list_ingredint;
    private ArrayList<RecipeIngredient> ingredientObjects = new ArrayList();
    private AddIngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        stepAdapter = new StepAdapter(this, R.layout.adapter_steps_view, stepObjects);
        list_step.setAdapter(stepAdapter);

        ingredientAdapter = new AddIngredientAdapter(this, R.layout.adapter_view_ingredient, ingredientObjects);
        list_ingredint.setAdapter(ingredientAdapter);


    }
}