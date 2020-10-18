package com.example.myeatup.ui.recipes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myeatup.R;
import com.example.myeatup.ui.StepAdapter;
import com.example.myeatup.ui.Steps;
import com.example.myeatup.ui.inspiration.InspirationViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    private ArrayAdapter<Steps> itemsAdapter;
    private int counter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        getSupportActionBar().hide();

        Button add_step = findViewById(R.id.btn_add_step);

        final ArrayList stepObjects = new ArrayList();

        itemsAdapter = new ArrayAdapter<>(this, R.layout.adapter_steps, R.id.text_adapt_step_text, stepObjects);

        final StepAdapter stepAdapter = new StepAdapter(this, R.layout.adapter_steps, stepObjects);

        final ListView list_step = findViewById(R.id.list_steps);
        //list_step.setAdapter(itemsAdapter);
        list_step.setAdapter(stepAdapter);




        add_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.adapter_steps);
                //View root = inflater.inflate(R.layout.adapter_steps);
               // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_host_fragment);
                //View hView = navigationView.getHeaderView(0);


                //TextView stepNum = hView.findViewById(R.id.text_adapt_step_num);
                //TextView stepText = hView.findViewById(R.id.text_adapt_step_text);
                counter = counter + 1;
                ViewGroup.LayoutParams params = list_step.getLayoutParams();
                params.height = 130 * counter;
                list_step.setLayoutParams(params);

                //Steps steps = new Steps("Step " + counter, "test");

                //stepNum.setText("Step: " + stepObjects.size());
                //stepText.setText("test");

                //itemsAdapter.add(steps);
                //itemsAdapter.notifyDataSetChanged();

                stepAdapter.add(new Steps("Step: " + counter, "test"));
                stepAdapter.notifyDataSetChanged();
            }
        });





    }
}