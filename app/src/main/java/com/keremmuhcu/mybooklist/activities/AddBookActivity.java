package com.keremmuhcu.mybooklist.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.keremmuhcu.mybooklist.R;
import com.keremmuhcu.mybooklist.databinding.ActivityAddBookBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddBookActivity extends AppCompatActivity {
    private ActivityAddBookBinding binding;
    private EditText editTextBookName, editTextBookAuthor, editTextBookSummary;
    private ImageView imgViewBookImage;
    private String bookName, bookAuthor, bookSummary;
    private int imgPermissionFalseCode = 0, imgPermissionTrueCode = 1;
    private Bitmap selectedImage, editedImage, defaultImageView;
    private Button saveBTN;

    private void initialize() {
        editTextBookName = binding.addBookActivityBookNameEditText;
        editTextBookAuthor = binding.addBookActivityBookAuthorEditText;
        editTextBookSummary = binding.addBookActivityBookSummaryEditText;
        imgViewBookImage = binding.addBookActivityImageView;
        saveBTN = binding.addBookActivitySaveBTN;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initialize();

    }

    public void saveBook(View view) {
        bookName = editTextBookName.getText().toString();
        bookAuthor = editTextBookAuthor.getText().toString();
        bookSummary = editTextBookSummary.getText().toString();

        if (!bookName.isEmpty()) {
            if (!bookAuthor.isEmpty()) {
                if (!bookSummary.isEmpty()) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    editedImage = makeSmaller(selectedImage);
                    editedImage.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                    byte[] imgBytes = outputStream.toByteArray();

                    try {
                        SQLiteDatabase database = this.openOrCreateDatabase("Books", MODE_PRIVATE, null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY, bookName VARCHAR, bookAuthor VARCHAR, bookSummary VARCHAR, bookImage BLOB)");

                        String sqlQuery ="INSERT INTO books (bookName, bookAuthor, bookSummary, bookImage) VALUES (?, ?, ?, ?)";
                        SQLiteStatement sqLiteStatement = database.compileStatement(sqlQuery);
                        sqLiteStatement.bindString(1 , bookName);
                        sqLiteStatement.bindString(2 , bookAuthor);
                        sqLiteStatement.bindString(3 , bookSummary);
                        sqLiteStatement.bindBlob(4, imgBytes);
                        sqLiteStatement.execute();

                        cleanTheObjects();
                        showToast("Successfully Saved");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else
                    showToast("The book summary is can not empty!");
            } else
                showToast("The book author is can not empty!");
        } else
            showToast("The book name is can not empty!");
    }

    private Bitmap makeSmaller(Bitmap image) {
        return Bitmap.createScaledBitmap(image, 120, 150, true);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void cleanTheObjects() {
        editTextBookName.setText("");
        editTextBookAuthor.setText("");
        editTextBookSummary.setText("");
        defaultImageView = BitmapFactory.decodeResource(this.getResources(), R.drawable.selectimage);
        imgViewBookImage.setImageBitmap(defaultImageView);
        saveBTN.setEnabled(false);
    }

    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, imgPermissionFalseCode);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, imgPermissionTrueCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == imgPermissionFalseCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, imgPermissionTrueCode);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == imgPermissionTrueCode) {
            if (resultCode == RESULT_OK && data != null) {
                Uri imgURI  = data.getData();

                try {
                    if (Build.VERSION.SDK_INT >= 28) {
                        ImageDecoder.Source imgSource = ImageDecoder.createSource(this.getContentResolver(), imgURI);
                        selectedImage = ImageDecoder.decodeBitmap(imgSource);
                    } else {
                        selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgURI);
                    }
                    saveBTN.setEnabled(true);
                    imgViewBookImage.setImageBitmap(selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(backIntent);
    }
}