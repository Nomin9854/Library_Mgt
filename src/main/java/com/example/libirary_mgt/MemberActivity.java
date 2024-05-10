package com.example.libirary_mgt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MemberActivity extends AppCompatActivity implements MemberAdapter.OnEditButtonClickListener, MemberAdapter.OnDeleteButtonClickListener {

    private EditText etMemberID, etMemberName, etMemberGender, etMemberEmail;
    private Button btnAddMember, btnBack;
    private ListView listViewMembers;
    private MemberAdapter memberAdapter;
    private List<Member> memberList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        etMemberID = findViewById(R.id.etMemberID);
        etMemberName = findViewById(R.id.etMemberName);
        etMemberGender = findViewById(R.id.etMemberGender);
        etMemberEmail = findViewById(R.id.etMemberEmail);
        btnAddMember = findViewById(R.id.btnAddMember);
        listViewMembers = findViewById(R.id.listViewMembers);

        memberList = new ArrayList<>();
        memberAdapter = new MemberAdapter(this, memberList, this, this); // Pass MainActivity as both edit and delete listener
        listViewMembers.setAdapter(memberAdapter);
        ImageView btnBack = findViewById(R.id.btnBack);

        databaseHelper = new DatabaseHelper(this);

        // Set click listener for the Add Member button
        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memberId = etMemberID.getText().toString().trim();
                String name = etMemberName.getText().toString().trim();
                String gender = etMemberGender.getText().toString().trim();
                String email = etMemberEmail.getText().toString().trim();

                if (!memberId.isEmpty() && !name.isEmpty() && !gender.isEmpty() && !email.isEmpty()) {
                    addOrUpdateMember(memberId, name, gender, email);
                } else {
                    Toast.makeText(MemberActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                finish();
            }
        });

    }

    private void addOrUpdateMember(String memberId, String name, String gender, String email) {
        boolean isNewMember = true;
        for (Member member : memberList) {
            if (member.getMemberId().equals(memberId)) {
                // Member already exists, update details
                member.setName(name);
                member.setGender(gender);
                member.setEmail(email);
                isNewMember = false;
                break;
            }
        }

        if (isNewMember) {
            // Add new member to the list
            Member newMember = new Member(memberId, name, gender, email);
            memberList.add(newMember);
        }

        // Notify adapter of data change
        memberAdapter.notifyDataSetChanged();

        // Clear input fields
        clearFields();
    }

    @Override
    public void onEditClick(int position) {
        // Retrieve selected member from the list
        final Member selectedMember = memberList.get(position);

        // Populate EditText fields with selected member details for editing
        etMemberID.setText(selectedMember.getMemberId());
        etMemberName.setText(selectedMember.getName());
        etMemberGender.setText(selectedMember.getGender());
        etMemberEmail.setText(selectedMember.getEmail());

        // Update Add Member button text to "Save Changes"
        btnAddMember.setText("Save Changes");

        // Set onClickListener for Save Changes button to update the member
        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMemberId = etMemberID.getText().toString().trim();
                String name = etMemberName.getText().toString().trim();
                String gender = etMemberGender.getText().toString().trim();
                String email = etMemberEmail.getText().toString().trim();

                if (!newMemberId.isEmpty() && !name.isEmpty() && !gender.isEmpty() && !email.isEmpty()) {
                    // Check if the new member ID is different from the current one
                    if (!newMemberId.equals(selectedMember.getMemberId())) {
                        // Check if the new member ID already exists in the list
                        if (isMemberIdExists(newMemberId)) {
                            Toast.makeText(MemberActivity.this, "Member ID already exists", Toast.LENGTH_SHORT).show();
                            return; // Exit without updating
                        }
                    }

                    // Update the selected member's details
                    selectedMember.setMemberId(newMemberId);
                    selectedMember.setName(name);
                    selectedMember.setGender(gender);
                    selectedMember.setEmail(email);

                    // Notify adapter of data change
                    memberAdapter.notifyDataSetChanged();

                    // Reset UI and button text after editing
                    clearFields();
                    btnAddMember.setText("Add Member");
                    btnAddMember.setOnClickListener(addMemberClickListener); // Restore original onClickListener
                } else {
                    Toast.makeText(MemberActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        // Remove the member from the list
        memberList.remove(position);

        // Notify adapter of data change
        memberAdapter.notifyDataSetChanged();

        // Show deletion confirmation toast
        Toast.makeText(this, "Member deleted", Toast.LENGTH_SHORT).show();
    }

    // OnClickListener for the original "Add Member" button
    private View.OnClickListener addMemberClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Retrieve input values
            String memberId = etMemberID.getText().toString().trim();
            String name = etMemberName.getText().toString().trim();
            String gender = etMemberGender.getText().toString().trim();
            String email = etMemberEmail.getText().toString().trim();

            // Add or update member based on input values
            addOrUpdateMember(memberId, name, gender, email);
        }
    };

    private boolean isMemberIdExists(String memberId) {
        // Check if the given member ID already exists in the list of members
        for (Member member : memberList) {
            if (member.getMemberId().equals(memberId)) {
                return true; // Member ID already exists
            }
        }
        return false; // Member ID does not exist
    }

    private void clearFields() {
        etMemberID.setText("");
        etMemberName.setText("");
        etMemberGender.setText("");
        etMemberEmail.setText("");
    }
}
