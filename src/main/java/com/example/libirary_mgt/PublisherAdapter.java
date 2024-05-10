package com.example.libirary_mgt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PublisherAdapter extends ArrayAdapter<Publisher> {

    private OnEditButtonClickListener onEditButtonClickListener;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public interface OnEditButtonClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteClick(int position);
    }

    public PublisherAdapter(Context context, List<Publisher> publishers, OnEditButtonClickListener editListener, OnDeleteButtonClickListener deleteListener) {
        super(context, 0, publishers);
        this.onEditButtonClickListener = editListener;
        this.onDeleteButtonClickListener = deleteListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_publisher, parent, false);
        }

        final Publisher publisher = getItem(position);

        TextView tvPublisherId = convertView.findViewById(R.id.tvPublisherId);
        TextView tvPublisherName = convertView.findViewById(R.id.tvPublisherName);
        TextView tvBookName = convertView.findViewById(R.id.tvBookName);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvNumCopies = convertView.findViewById(R.id.tvNumCopies);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        if (publisher != null) {
            tvPublisherId.setText("Publisher ID: " + publisher.getPublisherId());
            tvPublisherName.setText("Name: " + publisher.getPublisherName());
            tvBookName.setText("Book Name: " + publisher.getBookName());
            tvDate.setText("Date: " + publisher.getDate());
            tvNumCopies.setText("Number of Copies: " + publisher.getNumCopies());

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
