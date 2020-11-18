package com.scrippy2.myeatup.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.scrippy2.myeatup.R;
import com.scrippy2.myeatup.ui.recipes.AddRecipe;
import com.scrippy2.myeatup.ui.recipes.RecipeFragment;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    GridView gridView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        gridView = root.findViewById(R.id.gridview_profile);

        Button add_Recipie = root.findViewById(R.id.btn_add_recipe_profile);

        add_Recipie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecipe = new Intent(ProfileFragment.this.getContext(), AddRecipe.class);
                startActivity(addRecipe);
            }
        });




        return root;
    }
}