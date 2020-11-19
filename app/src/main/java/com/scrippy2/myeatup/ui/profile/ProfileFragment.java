package com.scrippy2.myeatup.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.scrippy2.myeatup.Login;
import com.scrippy2.myeatup.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    GridView gridView;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();


        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (mAuth.getUid() != null) {
                    textView.setText("");

                } else {
                    textView.setText("YOUR NOT LOGGED IN!");
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivityForResult(intent, 69);
                }
            }
        });

        gridView = root.findViewById(R.id.gridview_profile);

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