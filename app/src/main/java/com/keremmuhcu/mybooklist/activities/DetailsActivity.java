package com.keremmuhcu.mybooklist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keremmuhcu.mybooklist.databinding.ActivityAddBookBinding;
import com.keremmuhcu.mybooklist.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private ImageView imgViewBookImage;
    private TextView textViewBookName, textViewBookAuthor, textViewBookSummary;
    private String bookName, bookAuthor, bookSummary;
    private Bitmap bookImg;

    public void init() {
        imgViewBookImage = binding.detailsActivityImageViewBookImage;
        textViewBookName = binding.detailsActivityTextViewBookName;
        textViewBookAuthor = binding.detailsActivityTextViewBookAuthor;
        textViewBookSummary = binding.detailsActivityTextViewBookSummary;

        bookName = MainActivity.bookDetail.getBookName();
        bookAuthor = MainActivity.bookDetail.getAuthorName();
        bookSummary = MainActivity.bookDetail.getSummary();
        bookImg = MainActivity.bookDetail.getBookImg();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();

        if (!bookName.isEmpty() && !bookAuthor.isEmpty() && !bookSummary.isEmpty()) {
            textViewBookName.setText(bookName);
            textViewBookAuthor.setText(bookAuthor);
            textViewBookSummary.setText(bookSummary);
            imgViewBookImage.setImageBitmap(bookImg);
        }
    }
}