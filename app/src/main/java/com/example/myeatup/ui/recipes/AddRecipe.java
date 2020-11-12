package com.example.myeatup.ui.recipes;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myeatup.R;
import com.example.myeatup.firebasedata.IngredientDTO;
import com.example.myeatup.firebasedata.RecipieDTO;
import com.example.myeatup.firebasedata.Storage;
import com.example.myeatup.firebasedata.UnitDTO;
import com.example.myeatup.ui.AddIngredient;
import com.example.myeatup.ui.UnitSpinnerAdapter;
import com.example.myeatup.ui.AddIngredientAdapter;
import com.example.myeatup.ui.RecipeIngredient;
import com.example.myeatup.ui.StepAdapter;
import com.example.myeatup.ui.Steps;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    private ArrayAdapter<Steps> itemsAdapter;
    private int counterSteps;
    private int counterIngredients;
    private DatabaseReference mDatabase;
    private ArrayList<RecipeIngredient> ingredientObjects = new ArrayList();
    private AddIngredientAdapter ingredientAdapter;
    private int btn_id;
    private String key = "";
    private IngredientDTO ingredientToReturn = null;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private ImageButton photoButton;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Bitmap photo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);


        final ViewGroup viewGroup = new ViewGroup(getBaseContext()) {
            @Override
            protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

            }
        };

        //this.imageView = (ImageView)this.findViewById(R.id.image_recipe);
        photoButton = (ImageButton) this.findViewById(R.id.btn_image);
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






        getSupportActionBar().hide();


        final EditText recipeName = findViewById(R.id.edit_recipe_name);


        final Button add_step = findViewById(R.id.btn_add_step);
        final ArrayList<Steps> stepObjects = new ArrayList();
        final StepAdapter stepAdapter = new StepAdapter(this, R.layout.adapter_steps, stepObjects);
        final ListView list_step = findViewById(R.id.list_steps);
        list_step.setAdapter(stepAdapter);



        Button add_ingredient = findViewById(R.id.btn_add_ingrident);

        ingredientAdapter = new AddIngredientAdapter(this, R.layout.adapter_ingrediens, ingredientObjects);
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


        final Button upload = findViewById(R.id.btn_upload);


        mDatabase = FirebaseDatabase.getInstance().getReference();


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference();

                RecipieDTO recipe = new RecipieDTO();


                recipe.setName(recipeName.getText().toString());


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
                    mDatabase.child("recipies").child("3").setValue(recipe);

                }
                else{
                    stepAdapter.notifyDataSetChanged();
                    ArrayList<String> stepStrings = new ArrayList<>();
                    recipe.setSteps(stepStrings);
                    stepAdapter.notifyDataSetChanged();
                    for (int i = 0;i < stepObjects.size();i++) {
                        //stepStrings.add(stepObjects.get(i).getStepText());
                        stepAdapter.notifyDataSetChanged();
                        stepStrings.add(stepAdapter.getList().get(i).getStepText());
                    }
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

                Steps step = new Steps("test");

                //stepObjects.add(step);

                stepAdapter.notifyDataSetChanged();
                stepAdapter.add(step);
                stepAdapter.notifyDataSetChanged();
                //step.setStepText(stepAdapter.setEditView(view, viewGroup));
                //stepAdapter.notifyDataSetChanged();
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

                String t = data.getStringExtra("ingredientID");
                //System.out.println(t);
                //ADD INGREDIENT TO ADAPTER
                if (t != null) {
                    //IngredientDTO ingredientDTO = new IngredientDTO(t, getIngredientFromDataBase(t).getName());
                    getIngredientFromDataBase(t);



                }
            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            photoButton.setImageBitmap(photo);

        }

    }

    private void getIngredientFromDataBase(final String ingredientID) {


        //GETTING THE INGREDIENT ONLINE!
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ingredients");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ingredientToReturn = dataSnapshot.child(ingredientID).getValue(IngredientDTO.class);
                //adaptor.add(ingredientToReturn);
                ingredientObjects.get(btn_id).setIngredient(ingredientToReturn.getName());
                ingredientObjects.get(btn_id).setId(ingredientToReturn.getID());
                ingredientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);
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

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }*/

}