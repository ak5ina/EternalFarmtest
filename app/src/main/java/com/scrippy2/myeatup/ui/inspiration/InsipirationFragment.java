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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class InsipirationFragment extends Fragment {

    private InspirationViewModel inspirationViewModel;
    private ArrayList<IngredientDTO> arraylistForGridviewIngredient;
    private ArrayList<RecipieDTO> arraylistForGridviewRecipe;
    private GridView gridView, gridViewRecipy;
    private IngredientAdaptor adaptorForIngredients;
    private GridviewAdapter_Recipy adaptorForRecipy;
    private DatabaseReference mDatabase;
    private IngredientDTO ingredientToReturn = null;
    private ArrayList<String> recipyIDarray;


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
        gridView = (GridView) root.findViewById(R.id.gridview_inspiration);
        gridViewRecipy = (GridView) root.findViewById(R.id.gridview_inspiration_results);
        //Array med ingrediens objekter (SKIFT NAVN)
        arraylistForGridviewIngredient = new ArrayList<>();
        arraylistForGridviewRecipe = new ArrayList<>();
        recipyIDarray = new ArrayList<>();

        adaptorForIngredients = new IngredientAdaptor(getActivity().getApplicationContext(), R.layout.gridview_single_object2, arraylistForGridviewIngredient);
        gridView.setAdapter(adaptorForIngredients);

        adaptorForRecipy = new GridviewAdapter_Recipy(getActivity().getApplicationContext(), R.layout.gridview_recipe_object, arraylistForGridviewRecipe);
        gridViewRecipy.setAdapter(adaptorForRecipy);



        adaptorForIngredients.add(new IngredientDTO("fake", "Add Ingredient"));

        //Gridview for recipies
//        gridView = (GridView) root.findViewById(R.id.gridview_inspiration_results);
//        //Array med ingrediens objekter (SKIFT NAVN)
//        arraylistForGridviewRecipe = new ArrayList<>();
//
//        adaptor = new IngredientAdaptor(getActivity().getApplicationContext(), R.layout.gridview_single_object2, arraylistForGridviewRecipe);
//        gridView.setAdapter(adaptor);






        //ADD TO LIST VIEW BTN
        Button btn_add_ingre = root.findViewById(R.id.inspiration_btn_add_ingridiant);
        btn_add_ingre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddIngredient.class);
                startActivityForResult(intent,1);



            }

        });

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == getActivity().RESULT_OK){

                int b = data.getIntExtra("ingredientID", 0);
                String t = Integer.toString(b);
                //ADD INGREDIENT TO ADAPTER
                if (t != null) {

                    IngredientDTO ingredientAdded = MainActivity.INGREDIENTLIST.get(Integer.parseInt(t));

                    adaptorForIngredients.add(ingredientAdded);
                    adaptorForIngredients.notifyDataSetChanged();

                    if (ingredientAdded.getRecipies() != null) {
                        for (String recipyID : ingredientAdded.getRecipies()) {

                            recipyIDarray.add(recipyID);

                        }
                    }

                    Collections.sort(recipyIDarray);

                    for (int i = 0; i < recipyIDarray.size(); i++) {
                        System.out.println(recipyIDarray.get(i));
                    }

                }
            }
        }
    }

    private void AddIngredientsRecipe(String recipyID) {




    }

    private void AddToRecipyGridview(String recipyID) {

            mDatabase = FirebaseDatabase.getInstance().getReference("recipies").child(recipyID);
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    RecipieDTO newRecipy = snapshot.getValue(RecipieDTO.class);
                    arraylistForGridviewRecipe.add(newRecipy);

                    System.out.println("HEJEHJ " + newRecipy.getName() + " | " + arraylistForGridviewRecipe.size());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



    }

}