package com.example.myeatup.ui.inspiration;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myeatup.R;
import com.example.myeatup.firebasedata.IngredientDTO;
import com.example.myeatup.firebasedata.RecipieDTO;
import com.example.myeatup.ui.AddIngredient;
import com.example.myeatup.ui.GridviewAdapter;
import com.example.myeatup.ui.IngredientAdaptor;

import java.util.ArrayList;

public class InsipirationFragment extends Fragment {

    private InspirationViewModel inspirationViewModel;
    private ArrayList<IngredientDTO> arraylistForGridviewIngredient;
    private ArrayList<RecipieDTO> arraylistForGridviewRecipe;
    private ListView listView;
    private GridView gridView;
    private IngredientAdaptor adaptor;
    private ArrayList<IngredientDTO> ingredientListFromDatabase;



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
        //Array med ingrediens objekter (SKIFT NAVN)
        arraylistForGridviewIngredient = new ArrayList<>();

        adaptor = new IngredientAdaptor(getActivity().getApplicationContext(), R.layout.gridview_single_object2, arraylistForGridviewIngredient);
        gridView.setAdapter(adaptor);


        adaptor.add(new IngredientDTO("fake", "Add Ingredient"));

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
                System.out.println(t);
                //ADD INGREDIENT TO ADAPTER
                if (t != null) {
                    adaptor.add(new IngredientDTO(t, getIngredientFromDataBase(Integer.parseInt(t)).getName()));
                    adaptor.notifyDataSetChanged();
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

    private IngredientDTO getIngredientFromDataBase(int ingredientID) {
        IngredientDTO ingredientToReturn = null;

        //GETTING THE INGREDIENT ONLINE!



        //FAKING DATA
        if (ingredientID == 0) {
            ingredientToReturn = new IngredientDTO("0", "Appel");
        } else if (ingredientID == 1) {
            ingredientToReturn = new IngredientDTO("1", "Orange");
        } else if (ingredientID == 2) {
            ingredientToReturn = new IngredientDTO("2", "Pineappel");
        }

        //Returning the object
        if (ingredientToReturn != null) {
            return ingredientToReturn;
        } else {
            return null;
        }
    }
}