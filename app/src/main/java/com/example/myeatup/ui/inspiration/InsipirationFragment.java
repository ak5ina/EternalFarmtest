package com.example.myeatup.ui.inspiration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.myeatup.ui.DataModel;
import com.example.myeatup.ui.GridviewAdapter;
import com.example.myeatup.ui.IngredientAdaptor;

import java.util.ArrayList;

public class InsipirationFragment extends Fragment {

    private InspirationViewModel inspirationViewModel;
    private ArrayList<DataModel> test;
    private ListView listView;
    private IngredientAdaptor adaptor;

    //alertBuilder For Adding Ingredient
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText dialogSearchBar;
    private Button btnCancel;
    private GridView dialogGridview;
    private GridviewAdapter gvAdapter;


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

        //Listview
        listView = (ListView) root.findViewById(R.id.listview_inspiration);
        //Array med ingrediens objekter (SKIFT NAVN)
        test = new ArrayList<>();
        //Metoder som opdater ingredient listen.
        UpdateIngredientList();

        adaptor = new IngredientAdaptor(getActivity().getApplicationContext(), R.layout.inpiration_listview_object, test);
        listView.setAdapter(adaptor);


        //ADD TO LIST VIEW BTN
        Button btn_add_ingre = root.findViewById(R.id.inspiration_btn_add_ingridiant);
        btn_add_ingre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view234 = inflater.inflate(R.layout.gridview_single_object,null);

                //OPEN POP UP
                dialogBuilder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
                dialogBuilder.setView(getLayoutInflater().inflate(R.layout.gridviewobjekt,null));
                dialog = dialogBuilder.create();


                dialog.show();
                //OPEN GRIDVIEW
                dialogGridview = (GridView) view234.findViewById(R.id.gridview);
                gvAdapter = new GridviewAdapter(getActivity().getApplicationContext(),R.layout.gridview_single_object, getIngredientListFromDataBase());


                dialogGridview.setAdapter(gvAdapter);


                //RETURN A INGREDIENT

                //ADD INGREDIENT TO ADAPTER
                adaptor.add(new DataModel("ITS WORKING ", 420));
                adaptor.notifyDataSetChanged();
            }

            private ArrayList<IngredientDTO> getIngredientListFromDataBase() {

                ArrayList<IngredientDTO> listToReturn = new ArrayList<>();
                listToReturn.add(new IngredientDTO("1","tester1"));
                listToReturn.add(new IngredientDTO("2","tester2"));

                return listToReturn;
            }

        });

        return root;
    }

    private void UpdateIngredientList() {

    }



}