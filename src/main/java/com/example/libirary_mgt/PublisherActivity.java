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

public class PublisherActivity extends AppCompatActivity implements PublisherAdapter.OnEditButtonClickListener, PublisherAdapter.OnDeleteButtonClickListener {

    private EditText etPublisherID, etPublisherName, etBookName, etDate, etNumCopies;
    private Button btnAddPublisher, btnBack;
    private ListView listViewPublishers;
    private PublisherAdapter publisherAdapter;
    private List<Publisher> publisherList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);

        etPublisherID = findViewById(R.id.etPublisherID);
        etPublisherName = findViewById(R.id.etPublisherName);
        etBookName = findViewById(R.id.etBookName);
        etDate = findViewById(R.id.etDate);
        etNumCopies = findViewById(R.id.etNumCopies);
        btnAddPublisher = findViewById(R.id.btnAddPublisher);
        listViewPublishers = findViewById(R.id.listViewPublishers);
        ImageView btnBack = findViewById(R.id.btnBack);

        publisherList = new ArrayList<>();
        publisherAdapter = new PublisherAdapter(this, publisherList, this, this);
        listViewPublishers.setAdapter(publisherAdapter);

        databaseHelper = new DatabaseHelper(this);

        // Set click listener for the Add Publisher button
        btnAddPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String publisherId = etPublisherID.getText().toString().trim();
                String publisherName = etPublisherName.getText().toString().trim();
                String bookName = etBookName.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String numCopies = etNumCopies.getText().toString().trim();

                if (!publisherId.isEmpty() && !publisherName.isEmpty() && !bookName.isEmpty() && !date.isEmpty() && !numCopies.isEmpty()) {
                    addOrUpdatePublisher(publisherId, publisherName, bookName, date, numCopies);
                } else {
                    Toast.makeText(PublisherActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for the Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish PublisherActivity and navigate back
            }
        });
    }

    private void addOrUpdatePublisher(String publisherId, String publisherName, String bookName, String date, String numCopies) {
        boolean isNewPublisher = true;
        for (Publisher publisher : publisherList) {
            if (publisher.getPublisherId().equals(publisherId)) {
                // Publisher already exists, update details
                publisher.setPublisherName(publisherName);
                publisher.setBookName(bookName);
                publisher.setDate(date);
                publisher.setNumCopies(numCopies);
                isNewPublisher = false;
                break;
            }
        }

        if (isNewPublisher) {
            // Add new publisher to the list
            Publisher newPublisher = new Publisher(publisherId, publisherName, bookName, date, numCopies);
            publisherList.add(newPublisher);
        }

        // Notify adapter of data change
        publisherAdapter.notifyDataSetChanged();

        // Clear input fields
        clearFields();
    }

    @Override
    public void onEditClick(int position) {
        // Retrieve selected publisher from the list
        final Publisher selectedPublisher = publisherList.get(position);

        // Populate EditText fields with selected publisher details for editing
        etPublisherID.setText(selectedPublisher.getPublisherId());
        etPublisherName.setText(selectedPublisher.getPublisherName());
        etBookName.setText(selectedPublisher.getBookName());
        etDate.setText(selectedPublisher.getDate());
        etNumCopies.setText(selectedPublisher.getNumCopies());

        // Update Add Publisher button text to "Save Changes"
        btnAddPublisher.setText("Save Changes");

        // Set onClickListener for Save Changes button to update the publisher
        btnAddPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPublisherId = etPublisherID.getText().toString().trim();
                String publisherName = etPublisherName.getText().toString().trim();
                String bookName = etBookName.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String numCopies = etNumCopies.getText().toString().trim();

                if (!newPublisherId.isEmpty() && !publisherName.isEmpty() && !bookName.isEmpty() && !date.isEmpty() && !numCopies.isEmpty()) {
                    // Check if the new publisher ID is different from the current one
                    if (!newPublisherId.equals(selectedPublisher.getPublisherId())) {
                        // Check if the new publisher ID already exists in the list
                        if (isPublisherIdExists(newPublisherId)) {
                            Toast.makeText(PublisherActivity.this, "Publisher ID already exists", Toast.LENGTH_SHORT).show();
                            return; // Exit without updating
                        }
                    }

                    // Update the selected publisher's details
                    selectedPublisher.setPublisherId(newPublisherId);
                    selectedPublisher.setPublisherName(publisherName);
                    selectedPublisher.setBookName(bookName);
                    selectedPublisher.setDate(date);
                    selectedPublisher.setNumCopies(numCopies);

                    // Notify adapter of data change
                    publisherAdapter.notifyDataSetChanged();

                    // Reset UI and button text after editing
                    clearFields();
                    btnAddPublisher.setText("Add Publisher");
                    btnAddPublisher.setOnClickListener(addPublisherClickListener); // Restore original onClickListener
                } else {
                    Toast.makeText(PublisherActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        // Remove the publisher from the list
        publisherList.remove(position);

        // Notify adapter of data change
        publisherAdapter.notifyDataSetChanged();

        // Show deletion confirmation toast
        Toast.makeText(this, "Publisher deleted", Toast.LENGTH_SHORT).show();
    }

    // OnClickListener for the original "Add Publisher" button
    private View.OnClickListener addPublisherClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Retrieve input values
            String publisherId = etPublisherID.getText().toString().trim();
            String publisherName = etPublisherName.getText().toString().trim();
            String bookName = etBookName.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String numCopies = etNumCopies.getText().toString().trim();

            // Add or update publisher based on input values
            addOrUpdatePublisher(publisherId, publisherName, bookName, date, numCopies);
        }
    };

    private boolean isPublisherIdExists(String publisherId) {
        // Check if the given publisher ID already exists in the list of publishers
        for (Publisher publisher : publisherList) {
            if (publisher.getPublisherId().equals(publisherId)) {
                return true; // Publisher ID already exists
            }
        }
        return false; // Publisher ID does not exist
    }

    private void clearFields() {
        etPublisherID.setText("");
        etPublisherName.setText("");
        etBookName.setText("");
        etDate.setText("");
        etNumCopies.setText("");
    }
}
