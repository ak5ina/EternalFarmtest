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

public class Login extends AppCompatActivity {

    private EditText ed_password, ed_email;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FIREBASE
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        //Getting all the elements on the layout.
        ed_email = findViewById(R.id.et_login_email);
        ed_password = findViewById(R.id.et_login_password);
        Button btn_login = findViewById(R.id.btn_login);
        TextView btn_create_account = findViewById(R.id.tv_create_account);


        //Setting up buttons. (First Create account  then login)
        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivityForResult(intent, 420);

            }
        });

        //LOGIN
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(ed_email.getText().toString(), ed_password.getText().toString())
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(getApplicationContext(), "Login succeed", Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Login.this, user.getUid(), Toast.LENGTH_LONG).show();
                                    setResult(Activity.RESULT_OK);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 420) {
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