package com.scrippy2.myeatup.ui.inspiration;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.scrippy2.myeatup.MainActivity;
import com.scrippy2.myeatup.R;
import com.scrippy2.myeatup.firebasedata.IngredientDTO;
import com.scrippy2.myeatup.firebasedata.RecipieDTO;
import com.scrippy2.myeatup.ui.AddIngredient;
import com.scrippy2.myeatup.ui.GridviewAdapter_Recipy;
import com.scrippy2.myeatup.ui.IngredientAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrippy2.myeatup.ui.recipes.ViewRecipe;

import java.util.ArrayList;
import java.util.Collections;

public class InsipirationFragment extends Fragment {

    private InspirationViewModel inspirationViewModel;
    private ArrayList<IngredientDTO> arraylistForGridviewIngredient;
    private ArrayList<RecipieDTO> arraylistForGridviewRecipe;
    private GridView gridViewRecipy;
    private ListView listViewIngre;
    private IngredientAdaptor adaptorForIngredients;
    private GridviewAdapter_Recipy adaptorForRecipy;
    private DatabaseReference mDatabase;
    private IngredientDTO ingredientToReturn = null;
    private ArrayList<String> recipyIDarray, acceptableID;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        inspirationViewModel =
                ViewModelProviders.of(this).get(InspirationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inspiration, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        inspirationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });




        //Gridview for ingredienser
        listViewIngre = (ListView) root.findViewById(R.id.gridview_inspiration);
        gridViewRecipy = (GridView) root.findViewById(R.id.gridview_inspiration_results);
        //Array med ingrediens objekter (SKIFT NAVN)
        arraylistForGridviewIngredient = new ArrayList<>();
        arraylistForGridviewRecipe = new ArrayList<>();
        recipyIDarray = new ArrayList<>();
        acceptableID = new ArrayList<>();

        adaptorForIngredients = new IngredientAdaptor(getActivity().getApplicationContext(), R.layout.gridview_single_object2, arraylistForGridviewIngredient);
        listViewIngre.setAdapter(adaptorForIngredients);

        adaptorForRecipy = new GridviewAdapter_Recipy(getActivity().getApplicationContext(), R.layout.gridview_recipe_object, arraylistForGridviewRecipe);
        gridViewRecipy.setAdapter(adaptorForRecipy);


        //Gridview for recipies
//        gridView = (GridView) root.findViewById(R.id.gridview_inspiration_results);
//        //Array med ingrediens objekter (SKIFT NAVN)
//        arraylistForGridviewRecipe = new ArrayList<>();
//
//        adaptor = new IngredientAdaptor(getActivity().getApplicationContext(), R.layout.gridview_single_object2, arraylistForGridviewRecipe);
//        gridView.setAdapter(adaptor);



        Button btn_resetlist = root.findViewById(R.id.inspiration_btn_resetrecipylist);
        btn_resetlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });


        //ADD TO LIST VIEW BTN
        Button btn_add_ingre = root.findViewById(R.id.inspiration_btn_add_ingridiant);
        btn_add_ingre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddIngredient.class);
                startActivityForResult(intent,1);



            }

        });


        final Button btn_view_recipe = root.findViewById(R.id.btn_view_recipe_frag);
        btn_view_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("_______");
                Intent intent = new Intent(InsipirationFragment.this.getContext(), ViewRecipe.class);
                System.out.println(btn_view_recipe.getText().toString() + "__________________");
                intent.putExtra("Recipe", btn_view_recipe.getText().toString());
                startActivity(intent);
            }
        });



        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == getActivity().RESULT_OK){
                adaptorForRecipy.clear();
                recipyIDarray.clear();
                acceptableID.clear();


                System.out.println("HER SKAL DU SE " + data.getIntExtra("ingredientID", 0));
                int b = data.getIntExtra("ingredientID", 0);

                String t = Integer.toString(b);
                //ADD INGREDIENT TO ADAPTER
                if (t != null) {
                    IngredientDTO ingredientAdded = null;

                    for (IngredientDTO ing : MainActivity.INGREDIENTLIST){
                        if (Integer.parseInt(ing.getID()) == Integer.parseInt(t)){
                            ingredientAdded = ing;
                        }
                    }

                    if (ingredientAdded != null) {
                        adaptorForIngredients.add(ingredientAdded);
                        adaptorForIngredients.notifyDataSetChanged();

                        for (int i = 0; i < adaptorForIngredients.getCount(); i++) {
                            if (adaptorForIngredients.getItem(i).getRecipies() != null) {
                                for (String recipyID : adaptorForIngredients.getItem(i).getRecipies()) {

                                    recipyIDarray.add(recipyID);

                                }
                            }
                        }

                        Collections.sort(recipyIDarray);

                        String lastID = "bliverSatSennere", currentID;
                        int idCounter = 0;

                        for (int i = 0; i < recipyIDarray.size(); i++) {
                            currentID = recipyIDarray.get(i);
                            System.out.println("TEST1");

                            if (lastID.contentEquals(currentID)) {
                                System.out.println("TEST2");
                                idCounter++;
                                lastID = currentID;
                            } else {
                                System.out.println("TEST4");
                                idCounter = 0;
                                lastID = currentID;
                            }


                            if (idCounter == adaptorForIngredients.getCount() - 1) {
                                System.out.println("TEST3");
                                System.out.println(currentID + " | " + idCounter);
                                acceptableID.add(currentID);
                            }

                            System.out.println("Recipy id | " + recipyIDarray.get(i));
                        }

                        AddToRecipyGridview(acceptableID);
                    }
                }
            }
        }
    }

    private void AddToRecipyGridview(final ArrayList<String> recipyID) {

            DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("recipies");
            mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot childSnap : snapshot.getChildren()){
                        for (int i = 0; i < recipyID.size(); i++) {
                            if (childSnap.getKey().contentEquals(recipyID.get(i))) {
                                adaptorForRecipy.add(childSnap.getValue(RecipieDTO.class));
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



    }

    public void test(){


        adaptorForRecipy.clear();
        recipyIDarray.clear();
        acceptableID.clear();

        for (int i = 0; i < adaptorForIngredients.getCount(); i++){
            if (adaptorForIngredients.getItem(i).getRecipies() != null) {
                for (String recipyID : adaptorForIngredients.getItem(i).getRecipies()) {

                    recipyIDarray.add(recipyID);

                }
            }
        }

        Collections.sort(recipyIDarray);

        String lastID = "bliverSatSennere", currentID;
        int idCounter = 0;

        for (int i = 0; i < recipyIDarray.size(); i++) {
            currentID = recipyIDarray.get(i);
            System.out.println("TEST1");

            if(lastID.contentEquals(currentID)){
                System.out.println("TEST2");
                idCounter++;
                lastID = currentID;
            } else {
                System.out.println("TEST4");
                idCounter = 0;
                lastID = currentID;
            }


            if (idCounter == adaptorForIngredients.getCount()-1){
                System.out.println("TEST3");
                System.out.println(currentID + " | " + idCounter);
                acceptableID.add(currentID);
            }

            System.out.println("Recipy id | " + recipyIDarray.get(i));
        }

        AddToRecipyGridview(acceptableID);
    }

}