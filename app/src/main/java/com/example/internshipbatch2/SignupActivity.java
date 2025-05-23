package com.example.internshipbatch2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.SingleDateSelector;

public class SignupActivity extends AppCompatActivity {

    TextView already_account;
    Button signup;

    EditText email, password, name, contact, cnfpassword;
    String email_pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        db = openOrCreateDatabase("InternshipBatch2.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(25))";
        db.execSQL(tableQuery);

        already_account = findViewById(R.id.signup_already_account);
        signup = findViewById(R.id.signup_button);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.singup_password);
        name = findViewById(R.id.singup_name);
        contact = findViewById(R.id.singup_contact);
        cnfpassword = findViewById(R.id.signup_cnfpassword);


        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")){
                    email.setError("Enter Email");
                }

                else if (!email.getText().toString().matches(email_pattern)) {
                    email.setError("Enter valid email");
                }

                else if (name.getText().toString().equals("")) {
                    name.setError("Enter Name");

                }

                else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Enter Contact Number");
                }



                else if (password.getText().toString().trim().equals("")) {
                    password.setError("Enter password");
                }

                else if (password.getText().toString().length()<6) {
                    password.setError("Minimum 6 characters");
                }

                else if (cnfpassword.getText().toString().trim().equals("")) {
                    cnfpassword.setError("Enter password");
                }

                else if (cnfpassword.getText().toString().length()<6) {
                    cnfpassword.setError("Minimum 6 characters");
                }

                else if(!password.getText().toString().matches(cnfpassword.getText().toString())){
                    cnfpassword.setError("Password not matched");
                }

                else{

                    String checkEmail = "SELECT * FROM user WHERE email = '"+email.getText().toString()+"' OR contact = '"+contact.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(checkEmail, null);

                    if (cursor.getCount()>0){
                        Toast.makeText(SignupActivity.this, "Email or Contact already exists", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        String insertQuery = "INSERT INTO user VALUES(null, '"+name.getText().toString()+"', '"+email.getText().toString()+"', '"+contact.getText().toString()+"', '"+password.getText().toString()+"')";
                        db.execSQL(insertQuery);

                        Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    }

//                    Intent intent = new Intent(SignupActivityty.class);
//                    startActivity(intent);
                }
            }
        });



    }
}