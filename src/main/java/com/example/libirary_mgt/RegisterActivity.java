package com.example.libirary_mgt;

import android.content.Intent;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNewUsername, etNewPassword, etEmail;
    private Button btnCreateAccount,btnBack;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        etEmail = findViewById(R.id.etEmail);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnBack = findViewById(R.id.btnBack);


        databaseHelper = new DatabaseHelper(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click to navigate back to LoginActivity
                finish();
            }
        });
         btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = etNewUsername.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                if (!newUsername.isEmpty() && !newPassword.isEmpty() && !email.isEmpty()) {
                    // Insert new account details into database
                    long result = insertAccount(newUsername, newPassword, email);
                    if (result != -1) {
                        Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Finish RegisterActivity and return to LoginActivity
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private long insertAccount(String username, String password, String email) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);

        long result = db.insert("users", null, values); // Assuming "users" is your table name

        db.close();
        return result;
    }
}
