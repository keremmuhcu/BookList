package com.keremmuhcu.mybooklist;

import android.graphics.Bitmap;

public class BookDetail {
    private String bookName, authorName, summary;
    private Bitmap bookImg;

    public BookDetail(String bookName, String authorName, String summary, Bitmap bookImg) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.summary = summary;
        this.bookImg = bookImg;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getSummary() {
        return summary;
    }

    public Bitmap getBookImg() {
        return bookImg;
    }
}
