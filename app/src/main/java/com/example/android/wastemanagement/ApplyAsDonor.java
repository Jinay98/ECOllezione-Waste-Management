package com.example.android.wastemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.wastemanagement.Models.User;
import com.example.android.wastemanagement.Models.Volunteer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApplyAsDonor extends AppCompatActivity {

    EditText name,email,mobile,address,city,cardinal;
    Button submit;
    private DatabaseReference accountref, db;
    String userImageUrl, token;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_as_donor);
        name = findViewById(R.id.form_name);
        email = findViewById(R.id.form_email);
        mobile = findViewById(R.id.form_mobile);
        address = findViewById(R.id.form_address);
        city = findViewById(R.id.form_city);
        cardinal = findViewById(R.id.form_cardinality);
        submit = findViewById(R.id.form_submit);

        auth = FirebaseAuth.getInstance();

        accountref = FirebaseDatabase.getInstance().getReference().child("donor").child(auth.getUid());
        accountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User volunteer = dataSnapshot.getValue(User.class);
                name.setText(volunteer.getName());
                email.setText(volunteer.getEmail());
                userImageUrl = volunteer.getUserImgUrl();
                token = volunteer.getToken();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Dname = name.getText().toString().trim();
                String Demail = email.getText().toString().trim();
                //Long Dmobile = Long.valueOf(mobile_no.getText().toString());
                String Daddress = address.getText().toString().trim();
                String Dcity = city.getText().toString().trim();
                String Dcardinal = cardinal.getText().toString().trim();

                if(TextUtils.isEmpty(Dname)){
                    Toast.makeText(ApplyAsDonor.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Demail)){
                    Toast.makeText(ApplyAsDonor.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Daddress)){
                    Toast.makeText(ApplyAsDonor.this, "Enter your Address", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Dcity)){
                    Toast.makeText(ApplyAsDonor.this, "Enter your City", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Dcardinal)){
                    Toast.makeText(ApplyAsDonor.this, "Enter your Cardinal Direction", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(mobile.getText().toString().trim())){
                    Toast.makeText(ApplyAsDonor.this, "Enter your Mobile Number", Toast.LENGTH_SHORT).show();
                }else{
                    db = FirebaseDatabase.getInstance().getReference().child("donor").child(auth.getUid());
                    db.setValue(new User(Dname,Demail,Long.valueOf(mobile.getText().toString().trim()),userImageUrl,
                            Daddress, Dcity, Dcardinal, 1, 0, token));
                    Toast.makeText(ApplyAsDonor.this, "Response Recorded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ApplyAsDonor.this, Home.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
