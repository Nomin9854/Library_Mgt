package com.example.libirary_mgt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MemberAdapter extends ArrayAdapter<Member> {

    private OnEditButtonClickListener onEditButtonClickListener;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public interface OnEditButtonClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteClick(int position);
    }

    public MemberAdapter(Context context, List<Member> members, OnEditButtonClickListener editListener, OnDeleteButtonClickListener deleteListener) {
        super(context, 0, members);
        this.onEditButtonClickListener = editListener;
        this.onDeleteButtonClickListener = deleteListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_member, parent, false);
        }

        final Member member = getItem(position);

        TextView tvMemberId = convertView.findViewById(R.id.tvMemberId);
        TextView tvMemberName = convertView.findViewById(R.id.tvMemberName);
        TextView tvMemberGender = convertView.findViewById(R.id.tvMemberGender);
        TextView tvMemberEmail = convertView.findViewById(R.id.tvMemberEmail);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        if (member != null) {
            tvMemberId.setText("Member ID: " + member.getMemberId());
            tvMemberName.setText("Name: " + member.getName());
            tvMemberGender.setText("Gender: " + member.getGender());
            tvMemberEmail.setText("Email: " + member.getEmail());

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
