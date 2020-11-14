package com.scrippy2.myeatup.ui.recipes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.scrippy2.myeatup.MainActivity;
import com.scrippy2.myeatup.R;
import com.scrippy2.myeatup.firebasedata.IngredientDTO;
import com.scrippy2.myeatup.firebasedata.RecipieDTO;
import com.scrippy2.myeatup.firebasedata.Storage;
import com.scrippy2.myeatup.ui.AddIngredient;
import com.scrippy2.myeatup.ui.UnitSpinnerAdapter;
import com.scrippy2.myeatup.ui.AddIngredientAdapter;
import com.scrippy2.myeatup.ui.RecipeIngredient;
import com.scrippy2.myeatup.ui.StepAdapter;
import com.scrippy2.myeatup.ui.Steps;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    private int counterSteps;
    private int counterIngredients;
    private DatabaseReference mDatabase;
    private ArrayList<RecipeIngredient> ingredientObjects = new ArrayList();
    private AddIngredientAdapter ingredientAdapter;
    private int btn_id;
    private String key = "";
    private IngredientDTO ingredientToReturn = null;
    private static final int CAMERA_REQUEST = 1888;
    private ImageButton photoButton;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Bitmap photo;
    private EditText recipeName;
    private Button add_step;
    private ArrayList<Steps> stepObjects = new ArrayList();
    private ListView list_step;
    private StepAdapter stepAdapter;
    private ListView list_ingredint;
    private Button add_ingredient;
    private Spinner spinner;
    private UnitSpinnerAdapter spinAdapter;
    private EditText time;
    private Spinner price;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        getSupportActionBar().hide();
        photoButton = (ImageButton) this.findViewById(R.id.btn_image);
        recipeName = findViewById(R.id.edit_recipe_name);
        add_step = findViewById(R.id.btn_add_step);
        list_step = findViewById(R.id.list_steps);
        stepAdapter = new StepAdapter(this, R.layout.adapter_steps, stepObjects);
        list_step.setAdapter(stepAdapter);
        add_ingredient = findViewById(R.id.btn_add_ingrident);
        ingredientAdapter = new AddIngredientAdapter(this, R.layout.adapter_ingrediens, ingredientObjects);
        list_ingredint = findViewById(R.id.list_add_ingridient);
        list_ingredint.setAdapter(ingredientAdapter);
        time = findViewById(R.id.edit_time);
        price = findViewById(R.id.spin_price);



        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_ingrediens,null);
        final ArrayList<String> units = new ArrayList<String>(){};
        units.add("dL");
        units.add("g");
        units.add("Stk");
        units.add("spsk");
        units.add("tsk");
        spinAdapter = new UnitSpinnerAdapter(this, android.R.layout.simple_spinner_item, units);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = view.findViewById(R.id.spin_adapt_ingredient_unit);
        spinner.setAdapter(spinAdapter);


        final Button upload = findViewById(R.id.btn_upload);


        mDatabase = FirebaseDatabase.getInstance().getReference();


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!tjek()){

                }
                else{

                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    RecipieDTO recipe = new RecipieDTO();


                    recipe.setName(recipeName.getText().toString());
                    recipe.setTime(time.getText().toString());
                    recipe.setPrice(price.getSelectedItem().toString());


                    ArrayList<String> ingreUp = new ArrayList<>();
                    for (int i = 0;i < ingredientObjects.size();i++) {
                        ingreUp.add(ingredientObjects.get(i).getId());
                    }
                    recipe.setIngredientList(ingreUp);


                    ingredientAdapter.notifyDataSetChanged();
                    ArrayList<String> amount = new ArrayList<>();
                    for (int i = 0;i < ingredientObjects.size();i++){
                        amount.add(ingredientObjects.get(i).getAmount());
                    }
                    recipe.setUnitAmount(amount);


                    ArrayList<String> units = new ArrayList<>();
                    for (int i = 0;i < ingredientObjects.size();i++){
                        units.add(ingredientObjects.get(i).getUnit());
                    }
                    recipe.setUnitList(units);


                    if (upload.getText().equals("Upload")){

                        stepAdapter.notifyDataSetChanged();

                        key = mDatabase.child("recipies").push().getKey();

                        ArrayList<String> overWrite = new ArrayList<>();
                        for (int i = 0; i < stepObjects.size();i++) {
                            overWrite.add(stepObjects.get(i).getStepText());

                        }

                        stepAdapter.notifyDataSetChanged();
                        recipe.setSteps(overWrite);
                        stepAdapter.notifyDataSetChanged();
                        mDatabase.child("recipies").child(key).setValue(recipe);

                    }
                    else{

                        stepAdapter.notifyDataSetChanged();
                        ArrayList<String> stepStrings = new ArrayList<>();
                        recipe.setSteps(stepStrings);
                        stepAdapter.notifyDataSetChanged();

                        for (int i = 0;i < stepObjects.size();i++) {
                            stepAdapter.notifyDataSetChanged();
                            stepStrings.add(stepAdapter.getList().get(i).getStepText());
                        }


                        Storage storage = new Storage();
                        storage.upload(key, photo);

                        recipe.setSteps(stepStrings);
                        recipe.setID(key);
                        mDatabase.child("recipies").child(key).setValue(recipe);


                        for (int i = 0;i < recipe.getIngredientList().size();i++){
                            mDatabase.child("ingredients").child(recipe.getIngredientList().get(i)).child("recipeUses").push().setValue(recipe.getID());
                        }
                    }
                    if (upload.getText().equals("Confirm")) {
                        finish();
                    }
                    else {
                        upload.setText("Confirm");
                    }
                }
            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });



        add_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterSteps = counterSteps + 1;
                ViewGroup.LayoutParams params = list_step.getLayoutParams();
                params.height = 130 * counterSteps;
                list_step.setLayoutParams(params);
                Steps step = new Steps("test");
                stepAdapter.notifyDataSetChanged();
                stepAdapter.add(step);
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
                ingredientAdapter.add(new RecipeIngredient("0","Ingredient", "0", "unit"));
                ingredientAdapter.notifyDataSetChanged();
            }
        });



        final Button btn_add_ingre = findViewById(R.id.recipe_btn_add_ingredient);
        btn_add_ingre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_id = Integer.parseInt(btn_add_ingre.getText().toString());
                Intent intent = new Intent(AddRecipe.this, AddIngredient.class);
                startActivityForResult(intent,1);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == this.RESULT_OK){
                int b = data.getIntExtra("ingredientID",0);
                String t = "" + b;
                System.out.println(t);
                //ADD INGREDIENT TO ADAPTER
                if (t != null) {
                    IngredientDTO ingredientAdded = MainActivity.INGREDIENTLIST.get(Integer.parseInt(t));
                    ingredientObjects.get(btn_id).setIngredient(ingredientAdded.getName());
                    ingredientObjects.get(btn_id).setId(ingredientAdded.getID());
                    ingredientAdapter.notifyDataSetChanged();
                }
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            photoButton.setImageBitmap(photo);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    public boolean tjek(){
        boolean tjek = true;
        if (recipeName.getText().toString().equals("")){
            tjek = false;
            Toast.makeText(getApplicationContext(), "Give the recipe a name", Toast.LENGTH_SHORT).show();
        }
        if (stepAdapter.getList().size() == 0){
            tjek = false;
            Toast.makeText(getApplicationContext(), "Add some steps", Toast.LENGTH_SHORT).show();
        }
        for (int i = 0;i < stepObjects.size();i++){
            stepAdapter.setAdaptText(i);
            if (stepObjects.get(i).getStepText().equals("")){
                tjek = false;
                Toast.makeText(getApplicationContext(), "Fill out all steps", Toast.LENGTH_SHORT).show();
            }
        }
        if (ingredientAdapter.getCount() == 0){
            tjek = false;
            Toast.makeText(getApplicationContext(), "Add some ingredients", Toast.LENGTH_SHORT).show();
        }
        for (int i = 0;i < ingredientAdapter.getCount();i++){
            if (ingredientAdapter.getItem(i).getId().equals("0")){
                tjek = false;
                Toast.makeText(getApplicationContext(), "Choose ingredients", Toast.LENGTH_SHORT).show();
            }
        }
        for (int i = 0;i < ingredientAdapter.getCount();i++){
            ingredientAdapter.setAdaptTextAmount(i);
            if (ingredientAdapter.getItem(i).getAmount().equals("")){
                tjek = false;
                Toast.makeText(getApplicationContext(), "Fill out ingredient amounts", Toast.LENGTH_SHORT).show();
            }
        }
        if (time.getEditableText().toString().equals("")){
            tjek = false;
            Toast.makeText(getApplicationContext(), "Write how long this recipe takes", Toast.LENGTH_SHORT).show();
        }
        if (photo == null){
            tjek = false;
            Toast.makeText(getApplicationContext(), "Take a photo of the food", Toast.LENGTH_SHORT).show();
        }
        return tjek;
    }
}