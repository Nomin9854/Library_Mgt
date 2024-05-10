package com.example.libirary_mgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Library.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for Books
        String createBooksTable = "CREATE TABLE Books (" +
                "book_id TEXT PRIMARY KEY," +
                "title TEXT," +
                "author TEXT," +
                "publisher TEXT," +
                "category TEXT)";
        db.execSQL(createBooksTable);

        // Create table for Users (Registered Users)
        String createUsersTable = "CREATE TABLE Users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT," +
                "email TEXT)";
        db.execSQL(createUsersTable);

        String createMembersTable = "CREATE TABLE Members (" +
                "memberId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "gender TEXT," +
                "email TEXT)";
        db.execSQL(createMembersTable);

        String createPublishersTable = "CREATE TABLE Publishers (" +
                "publisher_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "publisher_name TEXT," +
                "book_name TEXT," +
                "date TEXT," +
                "num_copies INTEGER)";
        db.execSQL(createPublishersTable);

        String createCopiesTable = "CREATE TABLE Copies (" +
                "copy_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "book_name TEXT," +
                "publisher TEXT," +
                "date TEXT," +
                "num_copies TEXT)";
        db.execSQL(createCopiesTable);
    }
        // Create table for Members
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS Books");
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Members");
        db.execSQL("DROP TABLE IF EXISTS Publisher");
        // Recreate all tables
        onCreate(db);

    }

    // Method to insert a new user into the Users table
    public long insertUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);
        long newRowId = db.insert("Users", null, values);
        db.close();
        return newRowId;
    }

    public long insertMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("memberId", member.getMemberId());
        values.put("name", member.getName());
        values.put("gender", member.getGender());
        values.put("email", member.getEmail());
        // Insert the member into the Members table and retrieve the auto-generated member_id
        long newRowId = db.insert("Members", null, values);
        db.close();
        return newRowId;
    }

    public long insertPublisher(Publisher publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("publisher_name", publisher.getPublisherName());
        values.put("book_name", publisher.getBookName());
        values.put("date", publisher.getDate());
        values.put("num_copies", publisher.getNumCopies());

        // Insert the publisher into the Publishers table and retrieve the auto-generated publisher_id
        long newRowId = db.insert("Publishers", null, values);
        db.close();
        return newRowId;
    }

    public long insertCopy(Copy copy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_name", copy.getBookName());
        values.put("publisher", copy.getPublisher());
        values.put("date", copy.getDate());
        values.put("num_copies", copy.getNumCopies());

        // Insert the copy details into the Copies table and retrieve the auto-generated copy_id
        long newRowId = db.insert("Copies", null, values);
        db.close();
        return newRowId;
    }


}
