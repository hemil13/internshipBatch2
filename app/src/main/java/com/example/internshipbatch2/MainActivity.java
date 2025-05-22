package com.example.internshipbatch2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email, password;

    TextView forget_password, create_account;

    ImageView openIV, closeIV;

    String email_pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    SQLiteDatabase db;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        login = findViewById(R.id.main_login);
        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);
        forget_password = findViewById(R.id.main_forget_password);
        create_account = findViewById(R.id.main_create_account);
        openIV = findViewById(R.id.openIV);
        closeIV = findViewById(R.id.closeIV);

        db = openOrCreateDatabase("InternshipBatch2.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(25))";
        db.execSQL(tableQuery);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup_intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signup_intent);
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forget_intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(forget_intent);
            }
        });

        openIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeIV.setVisibility(View.VISIBLE);
                openIV.setVisibility(View.GONE);
                password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        closeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeIV.setVisibility(View.GONE);
                openIV.setVisibility(View.VISIBLE);
                password.setTransformationMethod(new HideReturnsTransformationMethod());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().equals("")){
                    email.setError("Enter Email");
                }


                else if (password.getText().toString().trim().equals("")) {
                    password.setError("Enter password");
                }

                else if (password.getText().toString().length()<6) {
                    password.setError("Minimum 6 characters");
                }


                else{

                    String loginQuery = "SELECT * FROM user WHERE (email = '"+email.getText().toString()+"' OR contact = '"+email.getText().toString()+"') AND password = '"+password.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(loginQuery, null);
                    if (cursor.getCount()>0){

                        while(cursor.moveToNext()){
                            sp.edit().putString(ConstantSp.userid, cursor.getString(0)).commit();
                            sp.edit().putString(ConstantSp.name, cursor.getString(1)).commit();
                            sp.edit().putString(ConstantSp.email, cursor.getString(2)).commit();
                            sp.edit().putString(ConstantSp.contact, cursor.getString(3)).commit();
                            sp.edit().putString(ConstantSp.password, cursor.getString(4)).commit();
                        }



                        Toast.makeText(MainActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }

                    else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();                    }
                }

//                Snackbar.make(view,"Login Sucessfull", Snackbar.LENGTH_LONG).show();
            }
        });

    }
}