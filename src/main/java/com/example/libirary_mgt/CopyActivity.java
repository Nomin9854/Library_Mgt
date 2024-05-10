package com.example.libirary_mgt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CopyActivity extends AppCompatActivity implements CopyAdapter.OnEditButtonClickListener, CopyAdapter.OnDeleteButtonClickListener {

    private EditText etCopyID, etBookName, etPublisher, etDate, etNumCopies;
    private Button btnAddCopy, btnBack;
    private ListView listViewCopies;
    private CopyAdapter copyAdapter;
    private List<Copy> copyList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy);

        etCopyID = findViewById(R.id.etCopyID);
        etBookName = findViewById(R.id.etBookName);
        etPublisher = findViewById(R.id.etPublisher);
        etDate = findViewById(R.id.etDate);
        etNumCopies = findViewById(R.id.etNumCopies);
        btnAddCopy = findViewById(R.id.btnAddCopy);
        listViewCopies = findViewById(R.id.listViewCopies);
        ImageView btnBack = findViewById(R.id.btnBack);

        copyList = new ArrayList<>();
        copyAdapter = new CopyAdapter(this, copyList, this, this);
        listViewCopies.setAdapter(copyAdapter);

        databaseHelper = new DatabaseHelper(this);

        // Set click listener for the Add Copy button
        btnAddCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String copyId = etCopyID.getText().toString().trim();
                String bookName = etBookName.getText().toString().trim();
                String publisher = etPublisher.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String numCopies = etNumCopies.getText().toString().trim();

                if (!copyId.isEmpty() && !bookName.isEmpty() && !publisher.isEmpty() && !date.isEmpty() && !numCopies.isEmpty()) {
                    addOrUpdateCopy(copyId, bookName, publisher, date, numCopies);
                } else {
                    Toast.makeText(CopyActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for the Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish CopyActivity and navigate back
            }
        });
    }

    private void addOrUpdateCopy(String copyId, String bookName, String publisher, String date, String numCopies) {
        boolean isNewCopy = true;
        for (Copy copy : copyList) {
            if (copy.getCopyId().equals(copyId)) {
                // Copy already exists, update details
                copy.setBookName(bookName);
                copy.setPublisher(publisher);
                copy.setDate(date);
                copy.setNumCopies(numCopies);
                isNewCopy = false;
                break;
            }
        }

        if (isNewCopy) {
            // Add new copy to the list
            Copy newCopy = new Copy(copyId, bookName, publisher, date, numCopies);
            copyList.add(newCopy);
        }

        // Notify adapter of data change
        copyAdapter.notifyDataSetChanged();

        // Clear input fields
        clearFields();
    }

    @Override
    public void onEditClick(int position) {
        // Retrieve selected copy from the list
        final Copy selectedCopy = copyList.get(position);

        // Populate EditText fields with selected copy details for editing
        etCopyID.setText(selectedCopy.getCopyId());
        etBookName.setText(selectedCopy.getBookName());
        etPublisher.setText(selectedCopy.getPublisher());
        etDate.setText(selectedCopy.getDate());
        etNumCopies.setText(selectedCopy.getNumCopies());

        // Update Add Copy button text to "Save Changes"
        btnAddCopy.setText("Save Changes");

        // Set onClickListener for Save Changes button to update the copy
        btnAddCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCopyId = etCopyID.getText().toString().trim();
                String bookName = etBookName.getText().toString().trim();
                String publisher = etPublisher.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String numCopies = etNumCopies.getText().toString().trim();

                if (!newCopyId.isEmpty() && !bookName.isEmpty() && !publisher.isEmpty() && !date.isEmpty() && !numCopies.isEmpty()) {
                    // Check if the new copy ID is different from the current one
                    if (!newCopyId.equals(selectedCopy.getCopyId())) {
                        // Check if the new copy ID already exists in the list
                        if (isCopyIdExists(newCopyId)) {
                            Toast.makeText(CopyActivity.this, "Copy ID already exists", Toast.LENGTH_SHORT).show();
                            return; // Exit without updating
                        }
                    }

                    // Update the selected copy's details
                    selectedCopy.setCopyId(newCopyId);
                    selectedCopy.setBookName(bookName);
                    selectedCopy.setPublisher(publisher);
                    selectedCopy.setDate(date);
                    selectedCopy.setNumCopies(numCopies);

                    // Notify adapter of data change
                    copyAdapter.notifyDataSetChanged();

                    // Reset UI and button text after editing
                    clearFields();
                    btnAddCopy.setText("Add Copy");
                    btnAddCopy.setOnClickListener(addCopyClickListener); // Restore original onClickListener
                } else {
                    Toast.makeText(CopyActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        // Remove the copy from the list
        copyList.remove(position);

        // Notify adapter of data change
        copyAdapter.notifyDataSetChanged();

        // Show deletion confirmation toast
        Toast.makeText(this, "Copy deleted", Toast.LENGTH_SHORT).show();
    }

    // OnClickListener for the original "Add Copy" button
    private View.OnClickListener addCopyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Retrieve input values
            String copyId = etCopyID.getText().toString().trim();
            String bookName = etBookName.getText().toString().trim();
            String publisher = etPublisher.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String numCopies = etNumCopies.getText().toString().trim();

            // Add or update copy based on input values
            addOrUpdateCopy(copyId, bookName, publisher, date, numCopies);
        }
    };

    private boolean isCopyIdExists(String copyId) {
        // Check if the given copy ID already exists in the list of copies
        for (Copy copy : copyList) {
            if (copy.getCopyId().equals(copyId)) {
                return true; // Copy ID already exists
            }
        }
        return false; // Copy ID does not exist
    }

    private void clearFields() {
        etCopyID.setText("");
        etBookName.setText("");
        etPublisher.setText("");
        etDate.setText("");
        etNumCopies.setText("");
    }
}
