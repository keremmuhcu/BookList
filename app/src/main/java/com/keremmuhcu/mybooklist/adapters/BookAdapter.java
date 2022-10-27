package com.keremmuhcu.mybooklist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.keremmuhcu.mybooklist.Book;
import com.keremmuhcu.mybooklist.databinding.BookItemBinding;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private ArrayList<Book> bookList;
    private OnItemClickListener listener;

    public BookAdapter(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookItemBinding bookItemBinding = BookItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookHolder(bookItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book book = bookList.get(position);
        holder.setData(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookHolder extends RecyclerView.ViewHolder {
        private BookItemBinding binding;
        TextView txtBookName, txtBookAuthor, txtBookSummary;
        ImageView imgBookImage;

        public BookHolder(BookItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            txtBookName = binding.bookItemTextViewBookName;
            txtBookAuthor = binding.bookItemTextViewBookAuthor;
            txtBookSummary = binding.bookItemTextViewBookSummary;
            imgBookImage = binding.bookItemImageViewBookImage;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(bookList.get(position));
                    }
                }
            });
        }

        public void setData(Book book) {
            this.txtBookName.setText(book.getBookName());
            this.txtBookAuthor.setText(book.getAuthorName());
            this.txtBookSummary.setText(book.getSummary());
            this.imgBookImage.setImageBitmap(book.getBookImg());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
