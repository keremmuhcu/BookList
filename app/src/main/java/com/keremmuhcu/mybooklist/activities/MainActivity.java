package com.keremmuhcu.mybooklist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.keremmuhcu.mybooklist.Book;
import com.keremmuhcu.mybooklist.adapters.BookAdapter;
import com.keremmuhcu.mybooklist.BookDetail;
import com.keremmuhcu.mybooklist.R;
import com.keremmuhcu.mybooklist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    public static BookDetail bookDetail;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_menu_add_book) {
            Intent addBookIntent = new Intent(this, AddBookActivity.class);
            finish();
            startActivity(addBookIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        adapter = new BookAdapter(Book.getData(this));
        recyclerView = binding.mainActivityRecyclerView;

        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 1); // spanCount: one row how many item
        recyclerView.addItemDecoration(new GridManagerDecoration());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                bookDetail = new BookDetail(book.getBookName(), book.getAuthorName(), book.getSummary(), book.getBookImg());

                Intent detailIntent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(detailIntent);
            }
        });
    }

    private class GridManagerDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = 20;
        }
    }
}