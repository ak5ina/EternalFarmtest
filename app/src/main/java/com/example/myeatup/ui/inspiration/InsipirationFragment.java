package com.example.myeatup.ui.inspiration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myeatup.R;
import com.example.myeatup.ui.DataModel;
import com.example.myeatup.ui.IngredientAdaptor;

import java.util.ArrayList;

public class InsipirationFragment extends Fragment {

    private InspirationViewModel inspirationViewModel;
    private ArrayList<DataModel> test;
    private ListView listView;
    private IngredientAdaptor adaptor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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


        Button btn_add_ingre = root.findViewById(R.id.inspiration_btn_add_ingridiant);
        btn_add_ingre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adaptor.add(new DataModel("ITS WORKING ", 420));
                adaptor.notifyDataSetChanged();
            }
        });



        return root;
    }

    private void UpdateIngredientList() {

        test.add(new DataModel("HEJ", 0));
        test.add(new DataModel("MED", 1));
        test.add(new DataModel("DIG", 2));

    }
}