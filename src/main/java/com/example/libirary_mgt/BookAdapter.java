package com.example.libirary_mgt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private OnEditButtonClickListener onEditButtonClickListener;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public interface OnEditButtonClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteClick(int position);
    }

    public BookAdapter(Context context, List<Book> books, OnEditButtonClickListener editListener, OnDeleteButtonClickListener deleteListener) {
        super(context, 0, books);
        this.onEditButtonClickListener = editListener;
        this.onDeleteButtonClickListener = deleteListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_book, parent, false);
        }

        final Book book = getItem(position);

        TextView tvBookId = convertView.findViewById(R.id.tvBookId);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvAuthor = convertView.findViewById(R.id.tvAuthor);
        TextView tvPublisher = convertView.findViewById(R.id.tvPublisher);
        TextView tvCategory = convertView.findViewById(R.id.tvCategory);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        if (book != null) {
            tvBookId.setText("Book ID: " + book.getBookId());
            tvTitle.setText("Title: " + book.getTitle());
            tvAuthor.setText("Author: " + book.getAuthor());
            tvPublisher.setText("Publisher: " + book.getPublisher());
            tvCategory.setText("Category: " + book.getCategory());

            // Set click listener for edit button
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEditButtonClickListener != null) {
                        onEditButtonClickListener.onEditClick(position);
                    }
                }
            });

            // Set click listener for delete button
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteButtonClickListener != null) {
                        onDeleteButtonClickListener.onDeleteClick(position);
                    }
                }
            });
        }

        return convertView;
    }
}
