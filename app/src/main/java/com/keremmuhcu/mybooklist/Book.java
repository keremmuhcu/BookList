package com.keremmuhcu.mybooklist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Book {
    private String bookName, authorName, summary;
    private Bitmap bookImg;

    public Book() {

    }

    public Book(String bookName, String authorName, String summary, Bitmap bookImg) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.summary = summary;
        this.bookImg = bookImg;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Bitmap getBookImg() {
        return bookImg;
    }

    public void setBookImg(Bitmap bookImg) {
        this.bookImg = bookImg;
    }

    public static ArrayList<Book> getData(Context context) {
        ArrayList<Book> bookList = new ArrayList<>();

        ArrayList<String> bookNameList = new ArrayList<>();
        ArrayList<String> bookAuthorList = new ArrayList<>();
        ArrayList<String> bookSummaryList = new ArrayList<>();
        ArrayList<Bitmap> bookImgList = new ArrayList<>();

        try {
            SQLiteDatabase database = context.openOrCreateDatabase("Books", Context.MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM books", null);

            int bookNameIndex = cursor.getColumnIndex("bookName");
            int bookAuthorIndex = cursor.getColumnIndex("bookAuthor");
            int bookSummaryIndex = cursor.getColumnIndex("bookSummary");
            int bookImageIndex = cursor.getColumnIndex("bookImage");

            while (cursor.moveToNext()) {
                bookNameList.add(cursor.getString(bookNameIndex));
                bookAuthorList.add(cursor.getString(bookAuthorIndex));
                bookSummaryList.add(cursor.getString(bookSummaryIndex));

                byte[] bringedImageByte = cursor.getBlob(bookImageIndex);
                Bitmap bringedImage = BitmapFactory.decodeByteArray(bringedImageByte, 0, bringedImageByte.length);

                bookImgList.add(bringedImage);
            }

            cursor.close();

            for (int i = 0; i < bookNameList.size(); i++) {
                Book book = new Book();
                book.setBookName(bookNameList.get(i));
                book.setAuthorName(bookAuthorList.get(i));
                book.setSummary(bookSummaryList.get(i));
                book.setBookImg(bookImgList.get(i));

                bookList.add(book);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return bookList;
    }
}
