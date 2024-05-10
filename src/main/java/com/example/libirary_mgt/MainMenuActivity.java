package com.example.libirary_mgt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Find views for menu items
        LinearLayout booksLayout = findViewById(R.id.booksLayout);
        LinearLayout membersLayout = findViewById(R.id.membersLayout);
        LinearLayout publishersLayout = findViewById(R.id.publishersLayout);
        LinearLayout copiesLayout = findViewById(R.id.copiesLayout);

        // Set click listeners for menu items
        booksLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, MainActivity.class));
            }
        });

        membersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, MemberActivity.class));
            }
        });

        publishersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, PublisherActivity.class));
            }
        });

        copiesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, CopyActivity.class));
            }
        });

        // Additional text (quote) at the bottom
        TextView additionalText = findViewById(R.id.additionalText);
        additionalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Optionally add custom behavior here
            }
        });

        // Exit button click listener
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
