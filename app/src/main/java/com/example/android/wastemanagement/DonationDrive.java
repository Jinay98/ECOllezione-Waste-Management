package com.example.android.wastemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.android.wastemanagement.Models.DonationDriveData;
import com.example.android.wastemanagement.Models.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonationDrive extends AppCompatActivity {

    TextView textView;
    EditText et1 , et2 , et3 , et4,date1;
    Button bt;
    DatabaseReference ref,refwall;
    FirebaseDatabase fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_drive);

        ref = FirebaseDatabase.getInstance().getReference("donation_drive");
        refwall=FirebaseDatabase.getInstance().getReference().child("wall").push();

        textView = findViewById(R.id.titleDonationDrive);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        date1=findViewById(R.id.date);
        bt = findViewById(R.id.submitDonationDrive);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String set1 = et1.getText().toString().trim();
                String set2 = et2.getText().toString().trim();
                long set3 = Long.parseLong(et3.getText().toString().trim());
                String set4 = et4.getText().toString().trim();
                String date = "20-10-72";
                String time = "10:00";

                String id = ref.push().getKey();

                DonationDriveData donateM = new DonationDriveData(set1 ,set2,set4 , set3,date , time , id);
                ref.child(id).setValue(donateM);
                String msg="Title = "+set1+"   \nDetails = "+set2+"    \nNo.Of Volunteers = "+String.valueOf(set3)+"    \nComments = "+set4+"     \nDate = "+date1.getText().toString();
                Post post=new Post("Donation Drive","no",msg,"no","no");
                refwall.setValue(post);
                Intent intent=new Intent(DonationDrive.this,Home.class);
                startActivity(intent);




            }
        });



    }
}
