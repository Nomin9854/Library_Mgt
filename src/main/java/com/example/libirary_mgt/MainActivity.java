package com.example.libirary_mgt;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements BookAdapter.OnEditButtonClickListener, BookAdapter.OnDeleteButtonClickListener {

    private EditText etBookID, etBookTitle, etAuthor, etPublisher, etCategory;
    private Button btnAddBook, btnBack;
    private ListView listViewBooks;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBookID = findViewById(R.id.etBookID);
        etBookTitle = findViewById(R.id.etBookTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etPublisher = findViewById(R.id.etPublisher);
        etCategory = findViewById(R.id.etCategory);
        btnAddBook = findViewById(R.id.btnAddBook);
        listViewBooks = findViewById(R.id.listViewBooks);

        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList, this, this); // Pass MainActivity as both edit and delete listener
        listViewBooks.setAdapter(bookAdapter);
        ImageView btnBack = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);


        // Set click listener for the back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                finish();
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookId = etBookID.getText().toString().trim();
                String title = etBookTitle.getText().toString().trim();
                String author = etAuthor.getText().toString().trim();
                String publisher = etPublisher.getText().toString().trim();
                String category = etCategory.getText().toString().trim();

                if (!bookId.isEmpty() && !title.isEmpty() && !author.isEmpty() && !publisher.isEmpty() && !category.isEmpty()) {
                    addOrUpdateBook(bookId, title, author, publisher, category);
                } else {
                    Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish MainActivity and navigate back to the previous activity
            }
        });
    }

    private void addOrUpdateBook(String bookId, String title, String author, String publisher, String category) {
        boolean isNewBook = true;
        for (Book book : bookList) {
            if (book.getBookId().equals(bookId)) {
                // Book already exists, update details
                book.setTitle(title);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setCategory(category);
                isNewBook = false;
                break;
            }
        }

        if (isNewBook) {
            // Add new book to the list
            Book newBook = new Book(bookId, title, author, publisher, category);
            bookList.add(newBook);
        }

        // Notify adapter of data change
        bookAdapter.notifyDataSetChanged();

        // Clear input fields
        clearFields();
    }

    @Override
    public void onEditClick(final int position) {
        // Retrieve selected book from the list
        final Book selectedBook = bookList.get(position);

        // Populate EditText fields with selected book details for editing
        etBookID.setText(selectedBook.getBookId());
        etBookTitle.setText(selectedBook.getTitle());
        etAuthor.setText(selectedBook.getAuthor());
        etPublisher.setText(selectedBook.getPublisher());
        etCategory.setText(selectedBook.getCategory());

        // Update Add Book button text to "Save Changes"
        btnAddBook.setText("Save Changes");

        // Set onClickListener for Save Changes button to update the book
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newBookId = etBookID.getText().toString().trim();
                String title = etBookTitle.getText().toString().trim();
                String author = etAuthor.getText().toString().trim();
                String publisher = etPublisher.getText().toString().trim();
                String category = etCategory.getText().toString().trim();

                if (!newBookId.isEmpty() && !title.isEmpty() && !author.isEmpty() && !publisher.isEmpty() && !category.isEmpty()) {
                    // Check if the new book ID is different from the current one
                    if (!newBookId.equals(selectedBook.getBookId())) {
                        // Check if the new book ID already exists in the list
                        if (isBookIdExists(newBookId)) {
                            Toast.makeText(MainActivity.this, "Book ID already exists", Toast.LENGTH_SHORT).show();
                            return; // Exit without updating
                        }
                    }

                    // Update the selected book's details
                    selectedBook.setBookId(newBookId);
                    selectedBook.setTitle(title);
                    selectedBook.setAuthor(author);
                    selectedBook.setPublisher(publisher);
                    selectedBook.setCategory(category);

                    // Notify adapter of data change
                    bookAdapter.notifyDataSetChanged();

                    // Reset UI and button text after editing
                    clearFields();
                    btnAddBook.setText("Add Book");
                    btnAddBook.setOnClickListener(addBookClickListener); // Restore original onClickListener
                } else {
                    Toast.makeText(MainActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        // Remove the book from the list
        bookList.remove(position);

        // Notify adapter of data change
        bookAdapter.notifyDataSetChanged();

        // Show deletion confirmation toast
        Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show();
    }

    // OnClickListener for the original "Add Book" button
    private View.OnClickListener addBookClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Retrieve input values
            String bookId = etBookID.getText().toString().trim();
            String title = etBookTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();
            String publisher = etPublisher.getText().toString().trim();
            String category = etCategory.getText().toString().trim();

            // Add or update book based on input values
            addOrUpdateBook(bookId, title, author, publisher, category);
        }
    };

    private boolean isBookIdExists(String bookId) {
        // Check if the given book ID already exists in the list of books
        for (Book book : bookList) {
            if (book.getBookId().equals(bookId)) {
                return true; // Book ID already exists
            }
        }
        return false; // Book ID does not exist
    }

    private void clearFields() {
        etBookID.setText("");
        etBookTitle.setText("");
        etAuthor.setText("");
        etPublisher.setText("");
        etCategory.setText("");
    }
}
