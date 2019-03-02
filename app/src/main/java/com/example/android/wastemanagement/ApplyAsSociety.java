package com.example.android.wastemanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.wastemanagement.Models.Society;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApplyAsSociety extends AppCompatActivity {
    EditText name, email, mobile_no, address;
    Button submitstep1;
    String Nname, Nemail, Naddress;
    Long Nmobile;

    private DatabaseReference accountref;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_as_society);
        name = findViewById(R.id.form_name);
        email = findViewById(R.id.form_email);
        mobile_no = findViewById(R.id.form_mobile);
        address = findViewById(R.id.form_address);
        submitstep1 = findViewById(R.id.form_step1submit);
        auth = FirebaseAuth.getInstance();
        accountref = FirebaseDatabase.getInstance().getReference().child("society").child(auth.getUid());
        submitstep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nname = name.getText().toString().trim();
                Nemail = email.getText().toString().trim();
                Nmobile = Long.valueOf(mobile_no.getText().toString());
                Naddress = address.getText().toString().trim();
                if(TextUtils.isEmpty(Nname)){
                    Toast.makeText(ApplyAsSociety.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Nemail)){
                    Toast.makeText(ApplyAsSociety.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Naddress)) {
                    Toast.makeText(ApplyAsSociety.this, "Enter your Address", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(mobile_no.getText().toString().trim())){
                    Toast.makeText(ApplyAsSociety.this, "Enter your Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Society society=new Society(Nname,Nemail,0,0,0,Nmobile,Naddress,null,null);
                    accountref.setValue(society);
                }
            }
        });
    }
}
