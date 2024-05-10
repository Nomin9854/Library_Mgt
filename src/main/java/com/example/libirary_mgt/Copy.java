package com.example.libirary_mgt;

public class Copy {
    private String copyId;
    private String bookName;
    private String publisher;
    private String date;
    private String numCopies;

    public Copy(String copyId, String bookName, String publisher, String date, String numCopies) {
        this.copyId = copyId;
        this.bookName = bookName;
        this.publisher = publisher;
        this.date = date;
        this.numCopies = numCopies;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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
