package com.example.libirary_mgt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CopyAdapter extends ArrayAdapter<Copy> {

    private OnEditButtonClickListener onEditButtonClickListener;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public interface OnEditButtonClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteClick(int position);
    }

    public CopyAdapter(Context context, List<Copy> copies, OnEditButtonClickListener editListener, OnDeleteButtonClickListener deleteListener) {
        super(context, 0, copies);
        this.onEditButtonClickListener = editListener;
        this.onDeleteButtonClickListener = deleteListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_copy, parent, false);
        }

        final Copy copy = getItem(position);

        TextView tvCopyId = convertView.findViewById(R.id.tvCopyId);
        TextView tvBookName = convertView.findViewById(R.id.tvBookName);
        TextView tvPublisher = convertView.findViewById(R.id.tvPublisher);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvNumCopies = convertView.findViewById(R.id.tvNumCopies);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        if (copy != null) {
            tvCopyId.setText("Copy ID: " + copy.getCopyId());
            tvBookName.setText("Book Name: " + copy.getBookName());
            tvPublisher.setText("Publisher: " + copy.getPublisher());
            tvDate.setText("Date: " + copy.getDate());
            tvNumCopies.setText("Number of Copies: " + copy.getNumCopies());

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
