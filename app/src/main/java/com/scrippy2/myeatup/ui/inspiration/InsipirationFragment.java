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

public class InsipirationFragment extends Fragment {

    private InspirationViewModel inspirationViewModel;
    private ArrayList<IngredientDTO> arraylistForGridviewIngredient;
    private ArrayList<RecipieDTO> arraylistForGridviewRecipe;
    private ListView listView;
    private GridView gridView, gridViewRecipy;
    private IngredientAdaptor adaptorForIngredients;
    private GridviewAdapter_Recipy adaptorForRecipy;
    private DatabaseReference mDatabase;
    private IngredientDTO ingredientToReturn = null;


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

                String t = data.getStringExtra("ingredientID");
                //ADD INGREDIENT TO ADAPTER
                if (t != null) {
                    //adaptor.add(new IngredientDTO(t, getIngredientFromDataBase(t).getName()));
                    getIngredientFromDataBase(t);
                    adaptorForIngredients.notifyDataSetChanged();
                    GetRecipyBasedOnIngredient();
                }
            }
        }
    }

    private ArrayList<IngredientDTO> getIngredientListFromDataBase() {
        ArrayList<IngredientDTO> listToReturn = new ArrayList<>();


        //TEST OBJEKTER.
        listToReturn.add(new IngredientDTO("1","Appel"));
        listToReturn.add(new IngredientDTO("2","Orange"));
        listToReturn.add(new IngredientDTO("3","Pineappel"));

        return listToReturn;
    }

    private void getIngredientFromDataBase(final String ingredientID) {


        //GETTING THE INGREDIENT ONLINE!
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ingredients").child(ingredientID);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ingredientToReturn = dataSnapshot.getValue(IngredientDTO.class);
                adaptorForIngredients.add(ingredientToReturn);
                

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);


    }

    private void GetRecipyBasedOnIngredient() {

        System.out.println("HEJ");
        System.out.println(adaptorForIngredients.getCount());
        //FIRST INGREDIENT
        if (adaptorForRecipy.getCount() == 0){
            System.out.println("TEST");
        }
        //SECOND INGREDIENT
        else {

            System.out.println("TEST2");
        }

    }
}