package com.example.android.wastemanagement;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wastemanagement.Models.Bandwidth;
import com.example.android.wastemanagement.Models.Ngo;
import com.example.android.wastemanagement.Models.User;
import com.example.android.wastemanagement.Models.Volunteer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApplyAsNgo extends AppCompatActivity {

    EditText name,email,mobile_no,address, reg_no, city, cardinal;
    EditText clothes, packedfood, grains, stationary, household, furniture, electronics;
    Button submit, submitstep1, submitstep2;
    LinearLayout clicksubmit,step1,step2,step3;
    TextView afterText;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private static final int GALLERY_INTETN=2;
    public ProgressDialog dialog;
    private StorageReference mStorage;
    private DatabaseReference accountref, db;
    FirebaseAuth auth;
    Bitmap compressed;
    Uri path;
    User user;
    String radioSelected, userImageUrl;
    String Nname, Nemail, Naddress, Ncity, Ncardinal, Nregno;
    Long Nclothes, NpackedFood, Ngrains, Nstationary, Nhousehold, Nfurniture, Nelectronics;
    Long Nmobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_as_ngo);

        name = findViewById(R.id.form_name);
        email = findViewById(R.id.form_email);
        mobile_no = findViewById(R.id.form_mobile);
        address = findViewById(R.id.form_address);
        reg_no = findViewById(R.id.regno);
        city = findViewById(R.id.city);
        cardinal = findViewById(R.id.cardinal);
        clothes = findViewById(R.id.clothes);
        packedfood = findViewById(R.id.packedFood);
        grains = findViewById(R.id.grains);
        stationary = findViewById(R.id.stationary);
        household = findViewById(R.id.householdProduct);
        furniture = findViewById(R.id.furniture);
        electronics = findViewById(R.id.electronics);
        submit = findViewById(R.id.form_submit);
        submitstep1 = findViewById(R.id.form_step1submit);
        submitstep2 = findViewById(R.id.form_step2submit);
        clicksubmit = findViewById(R.id.clicksubmit);
        afterText = findViewById(R.id.afterText);
        radioGroup = findViewById(R.id.radio);
        step1 = findViewById(R.id.step1);
        step2 = findViewById(R.id.step2);
        step3 = findViewById(R.id.step3);

        dialog = new ProgressDialog(ApplyAsNgo.this);
        dialog.setMessage("Updating please wait...");

        mStorage = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        accountref = FirebaseDatabase.getInstance().getReference().child("ngo").child(auth.getUid());
        accountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Ngo volunteer = dataSnapshot.getValue(Ngo.class);
                name.setText(volunteer.getName());
                email.setText(volunteer.getNgoEmail());
                userImageUrl = volunteer.getUserImgUrl();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        clicksubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(ApplyAsNgo.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ApplyAsNgo.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {
                    Intent mintetnt = new Intent(Intent.ACTION_PICK);
                    mintetnt.setType("image/*");
                    startActivityForResult(mintetnt, GALLERY_INTETN);
                }
            }
        });

        submitstep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nname = name.getText().toString().trim();
                Nemail = email.getText().toString().trim();
                Nmobile = Long.valueOf(mobile_no.getText().toString());
                Naddress = address.getText().toString().trim();
                Ncity = city.getText().toString().trim();
                Ncardinal = cardinal.getText().toString().trim();
                Nregno = reg_no.getText().toString().trim();

                if(TextUtils.isEmpty(Nname)){
                    Toast.makeText(ApplyAsNgo.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Nemail)){
                    Toast.makeText(ApplyAsNgo.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Naddress)){
                    Toast.makeText(ApplyAsNgo.this, "Enter your Address", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Ncity)){
                    Toast.makeText(ApplyAsNgo.this, "Enter your City", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Ncardinal)){
                    Toast.makeText(ApplyAsNgo.this, "Enter your Cardinal Direction", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Nregno)){
                    Toast.makeText(ApplyAsNgo.this, "Enter your NGO registration number", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(mobile_no.getText().toString().trim())){
                    Toast.makeText(ApplyAsNgo.this, "Enter your Mobile Number", Toast.LENGTH_SHORT).show();
                }else{
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                }
            }
        });

        submitstep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nclothes = Long.valueOf(clothes.getText().toString());
                NpackedFood = Long.valueOf(packedfood.getText().toString());
                Ngrains = Long.valueOf(grains.getText().toString());
                Nstationary = Long.valueOf(stationary.getText().toString());
                Nhousehold = Long.valueOf(household.getText().toString());
                Nfurniture = Long.valueOf(furniture.getText().toString());
                Nelectronics = Long.valueOf(electronics.getText().toString());

                if(TextUtils.isEmpty(clothes.getText().toString().trim())){
                    Nclothes = (long)0;
                }
                if(TextUtils.isEmpty(packedfood.getText().toString().trim())){
                    NpackedFood = (long)0;
                }
                if(TextUtils.isEmpty(grains.getText().toString().trim())){
                    Ngrains = (long)0;
                }
                if(TextUtils.isEmpty(stationary.getText().toString().trim())){
                    Nstationary = (long)0;
                }
                if(TextUtils.isEmpty(household.getText().toString().trim())){
                    Nhousehold = (long)0;
                }
                if(TextUtils.isEmpty(furniture.getText().toString().trim())){
                    Nfurniture = (long)0;
                }
                if(TextUtils.isEmpty(electronics.getText().toString().trim())){
                    Nelectronics = (long)0;
                }
                step2.setVisibility(View.GONE);
                step3.setVisibility(View.VISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submit code
                if(afterText.getVisibility()==View.GONE){
                    Toast.makeText(ApplyAsNgo.this, "Upload official NGO document", Toast.LENGTH_SHORT).show();
                }else{
                    Ngo ngo = new Ngo(Nname, Nemail, Nmobile, userImageUrl, Naddress, Ncity, Ncardinal, 1,
                            new Bandwidth(Nclothes, NpackedFood, Ngrains, Nstationary, Nhousehold, Nfurniture, Nelectronics),
                            new Volunteer(null,null,0,null,
                                    null,0,0,null,null),
                            new HashMap<String, Boolean>(), Nregno, String.valueOf(path));
                    accountref.setValue(ngo);
                    Toast.makeText(ApplyAsNgo.this, "Response Recorded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ApplyAsNgo.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GALLERY_INTETN && resultCode==RESULT_OK) {
            dialog.setMessage("Uploading");
            dialog.show();
            Uri uri= data.getData();

            StorageReference filepath= mStorage.child("ngo_official_pic").child(uri.getLastPathSegment());
            try
            {
                compressed = MediaStore.Images.Media.getBitmap(ApplyAsNgo.this.getContentResolver(), uri);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressed.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            byte[] cimg = baos.toByteArray();
            filepath.putBytes(cimg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    path = taskSnapshot.getDownloadUrl();
                    //accountref = FirebaseDatabase.getInstance().getReference().child("user_details").child(auth.getUid());
                    //accountref.child("userImgUrl").setValue(String.valueOf(path));
                    Toast.makeText(ApplyAsNgo.this, "Document uploaded", Toast.LENGTH_LONG).show();
                    //finish();
                    //startActivity(getIntent());
                    afterText.setVisibility(View.VISIBLE);
                    clicksubmit.setVisibility(View.GONE);
                    dialog.dismiss();
                }
            });
        }
    }

    public boolean onSupportNavigateUp(){

        onBackPressed();
        return true;
    }
}
