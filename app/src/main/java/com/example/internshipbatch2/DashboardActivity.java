package com.example.internshipbatch2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    TextView welcome;
    Button profile, logout, delete_profile;

    SharedPreferences sp;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sp=getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        db = openOrCreateDatabase("InternshipBatch2.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(25))";
        db.execSQL(tableQuery);

        welcome = findViewById(R.id.dashboard_welcome);
        profile = findViewById(R.id.dashboard_profile);
        logout = findViewById(R.id.dashboard_logout);
        delete_profile = findViewById(R.id.dashboard_delete_profile);

        welcome.setText("WELCOME "+sp.getString(ConstantSp.name,""));


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                Intent intent = new Intent (DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        delete_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteQuery = "DELETE FROM user WHERE userid = '"+sp.getString(ConstantSp.userid, "")+"'";
                db.execSQL(deleteQuery);
                sp.edit().clear().commit();
                Toast.makeText(DashboardActivity.this, "Profile Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        welcome.setText("WELCOME "+sp.getString(ConstantSp.name,""));

    }
}


