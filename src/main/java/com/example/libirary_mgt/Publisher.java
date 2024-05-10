package com.example.libirary_mgt;

public class Publisher {
    private String publisherId;
    private String publisherName;
    private String bookName;
    private String date;
    private String numCopies; // Keep NumCopies as a String

    public Publisher(String publisherId, String publisherName, String bookName, String date, String numCopies) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.bookName = bookName;
        this.date = date;
        this.numCopies = numCopies;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumCopies() {
        return numCopies;
    }

    public void setNumCopies(String numCopies) {
        this.numCopies = numCopies;
    }
}
