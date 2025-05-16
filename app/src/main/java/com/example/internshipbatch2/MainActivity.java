package com.example.internshipbatch2;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email, password;

    String email_pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.main_login);
        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().equals("")){
                    email.setError("Enter Email");
                }

                else if (!email.getText().toString().matches(email_pattern)) {
                    email.setError("Enter valid email");
                }

                else if (password.getText().toString().equals("")) {
                    password.setError("Enter password");
                }

                else if (password.getText().toString().length()<6) {
                    password.setError("Minimum 6 characters");
                }

                else{

                    Toast.makeText(MainActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                }

//                Snackbar.make(view,"Login Sucessfull", Snackbar.LENGTH_LONG).show();
            }
        });

    }
}