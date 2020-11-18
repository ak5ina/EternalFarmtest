package com.scrippy2.myeatup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.scrippy2.myeatup.firebasedata.UserDTO;

public class CreateAccount extends AppCompatActivity {


    private EditText ed_password, ed_email, ed_name;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        //FIREBASE
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        //Getting all the elements on the layout.
        ed_name = findViewById(R.id.et_signup_name);
        ed_email = findViewById(R.id.et_signup_mail);
        ed_password = findViewById(R.id.et_signup_password);
        Button btn_login = findViewById(R.id.btn_create_account);
        TextView tv_signin = findViewById(R.id.tv_signup);




        //LOGIN
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ed_email.getText().toString();
                String password = ed_password.getText().toString();

                if (email.length() > 0 && password.length() > 0) {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        database.getReference("users").child(mAuth.getUid()).setValue(new UserDTO( ed_name.getText().toString(), ed_email.getText().toString()));
                                        setResult(Activity.RESULT_OK);
                                        finish();
                                    } else {
                                        Toast.makeText(CreateAccount.this, "Failed to create account", Toast.LENGTH_LONG);
                                    }

                                    // ...
                                }
                            });
                } else {
                    Toast.makeText(CreateAccount.this, "Please input email and password.", Toast.LENGTH_LONG);
                }

            }
        });

        //Setting up buttons. (First Create account  then login)
        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount.this, Login.class);
                startActivityForResult(intent, 421);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 421) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                setResult(Activity.RESULT_CANCELED);
                finish();
            }

        }

    }

}