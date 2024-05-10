package com.example.libirary_mgt;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        ImageView btnExit = findViewById(R.id.btnExit);

        databaseHelper = new DatabaseHelper(this);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {
                    // Check user credentials in the database
                    SQLiteDatabase db = databaseHelper.getReadableDatabase();
                    String[] columns = {"username", "password"};
                    String selection = "username=? AND password=?";
                    String[] selectionArgs = {username, password};
                    Cursor cursor = db.query("Users", columns, selection, selectionArgs, null, null, null);

                    if (cursor != null && cursor.getCount() > 0) {
                        // User authenticated, proceed to MainActivity or another screen
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Navigate to MainActivity or other screen
                        cursor.close();
                        db.close();
                        startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                        finish();
                    } else {
                        // Invalid credentials
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }

                    if (cursor != null) {
                        cursor.close();
                    }
                    db.close();
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RegisterActivity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit App?");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Exit the app
                finishAffinity(); // Close all activities
                System.exit(0); // Exit the app completely
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog (do nothing)
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
