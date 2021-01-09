package com.scrippy2.myeatup.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.proto.ProtoOutputStream;
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

import com.google.android.material.internal.NavigationMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scrippy2.myeatup.Login;
import com.scrippy2.myeatup.R;
import com.scrippy2.myeatup.firebasedata.RecipieDTO;
import com.scrippy2.myeatup.ui.GridviewAdapter_Recipy;
import com.scrippy2.myeatup.ui.inspiration.InsipirationFragment;
import com.scrippy2.myeatup.ui.recipes.AddRecipe;
import com.scrippy2.myeatup.ui.recipes.ViewRecipe;

import java.util.ArrayList;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private GridView gridViewProflie;
    private FirebaseAuth mAuth;
    private Button savedRecipies;
    private ArrayList<RecipieDTO> arraylistForGridviewRecipe;
    private GridviewAdapter_Recipy adaptorForRecipy;
    private DatabaseReference mDatabase;
    private TextView userName;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();


        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        userName = root.findViewById(R.id.profile_user_email);

        final TextView textView = root.findViewById(R.id.text_notifications);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (mAuth.getUid() != null) {
                    textView.setText("");

                    userName.setText("User: " + FirebaseAuth.getInstance().getCurrentUser().getEmail());

                } else {
                    //textView.setText("YOUR NOT LOGGED IN!");
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivityForResult(intent, 69);
                }
            }
        });




        gridViewProflie = root.findViewById(R.id.gridview_profile);

        Button btn_add_recipe = root.findViewById(R.id.btn_add_recipe_profile);
        btn_add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddRecipe.class);
                startActivity(intent);
            }
        });

        savedRecipies = root.findViewById(R.id.profile_btn_saved_recipes);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        Map map = preferences.getAll();

        System.out.println("Recipies " + map);
        System.out.println(map.toString());
        String mapalt = map.toString().replace("{", "").replace("}", "");
        System.out.println(mapalt);
        String mapalt2 = mapalt.replaceAll(" ", "");
        System.out.println(mapalt2);
        String[] prefRecipies = mapalt2.split(",");
        final ArrayList<String> prefRecipies2 = new ArrayList<>();
        for (int i = 0; i < prefRecipies.length;i++){
            System.out.println(prefRecipies[i]);
            prefRecipies2.add(prefRecipies[i]);
        }

        final ArrayList<String> prefRecSav =  prefRecipies2;

        for (int i = 0;i < prefRecSav.size();i++){
            if (!prefRecSav.get(i).contains("=saved")){
                prefRecSav.set(i, "");

            }
            System.out.println(i + " " + prefRecSav.get(i));
        }

        savedRecipies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0;i < prefRecSav.size();i++){
                    if (prefRecSav.get(i).contains("=saved")){
                        prefRecSav.set(i, prefRecSav.get(i).replace("=saved", ""));
                    }
                    System.out.println(prefRecSav.get(i));
                }

                mDatabase = FirebaseDatabase.getInstance().getReference();

                ValueEventListener savedListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        arraylistForGridviewRecipe = new ArrayList<>();
                        for (int i = 0;i < prefRecSav.size();i++) {
                            if (!prefRecSav.get(i).equals("")){
                                RecipieDTO recipieDTO = snapshot.child("recipies").child(prefRecSav.get(i)).getValue(RecipieDTO.class);
                                arraylistForGridviewRecipe.add(recipieDTO);
                            }
                        }
                        adaptorForRecipy = new GridviewAdapter_Recipy(getActivity().getApplicationContext(), R.layout.gridview_recipe_object, arraylistForGridviewRecipe);
                        gridViewProflie.setAdapter(adaptorForRecipy);
                        adaptorForRecipy.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                mDatabase.addListenerForSingleValueEvent(savedListener);
            }
        });

        final Button btn_view_recipe = root.findViewById(R.id.btn_view_recipe_frag);
        btn_view_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("_______");
                Intent intent = new Intent(ProfileFragment.this.getContext(), ViewRecipe.class);
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
        if (requestCode == 69) {
            if (resultCode == Activity.RESULT_OK) {
                UpdateAfterLogin();
                System.out.println("LOGGED IN!! YEAY");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                System.out.println("NO LOGIN WHY NOT!! WHY !!!");
            }
        }
    }

    private void UpdateAfterLogin() {
    }
}