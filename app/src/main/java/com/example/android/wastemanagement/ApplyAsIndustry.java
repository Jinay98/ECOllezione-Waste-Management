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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wastemanagement.Models.Bandwidth;
import com.example.android.wastemanagement.Models.Industry;
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
import java.util.HashMap;

public class ApplyAsIndustry extends AppCompatActivity {

    EditText name,email,mobile_no,address, reg_no, city, cardinal;
    Button submit, submitstep1;
    LinearLayout clicksubmit,step1,step2, filterLayout;
    TextView afterText, filterName;
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
    Long Nmobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_as_industry);
        name = findViewById(R.id.form_name);
        email = findViewById(R.id.form_email);
        mobile_no = findViewById(R.id.form_mobile);
        address = findViewById(R.id.form_address);
        reg_no = findViewById(R.id.regno);
        city = findViewById(R.id.city);
        cardinal = findViewById(R.id.cardinal);
        submit = findViewById(R.id.form_submit);
        submitstep1 = findViewById(R.id.form_step1submit);
        clicksubmit = findViewById(R.id.clicksubmit);
        afterText = findViewById(R.id.afterText);
        step1 = findViewById(R.id.step1);
        step2 = findViewById(R.id.step2);
        filterLayout = findViewById(R.id.filterLayout);
        filterName = findViewById(R.id.filterName);

        dialog = new ProgressDialog(ApplyAsIndustry.this);
        dialog.setMessage("Updating please wait...");

        mStorage = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        accountref = FirebaseDatabase.getInstance().getReference().child("industry").child(auth.getUid());
        accountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Industry volunteer = dataSnapshot.getValue(Industry.class);
                name.setText(volunteer.getName());
                email.setText(volunteer.getIndustryEmail());
                userImageUrl = volunteer.getUserImgUrl();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ApplyAsIndustry.this, filterLayout);
                // populate menu with 7 options
                popupMenu.getMenu().add(1, 1, 1, "Paper");
                popupMenu.getMenu().add(1, 2, 2, "Glass");
                popupMenu.getMenu().add(1, 3, 3, "Plastic");
                popupMenu.getMenu().add(1, 4, 4, "Organic");
                popupMenu.getMenu().add(1, 5, 5, "E-waste");
                // show the menu
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case 1 : filterName.setText("Paper");
                                break;
                            case 2 : filterName.setText("Glass");
                                break;
                            case 3 : filterName.setText("Plastic");
                                break;
                            case 4 : filterName.setText("Organic");
                                break;
                            case 5 : filterName.setText("E-waste");
                                break;
                        }
                        return true;
                    }
                });
            }
        });
        clicksubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(ApplyAsIndustry.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ApplyAsIndustry.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

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
                    Toast.makeText(ApplyAsIndustry.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Nemail)){
                    Toast.makeText(ApplyAsIndustry.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Naddress)){
                    Toast.makeText(ApplyAsIndustry.this, "Enter your Address", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Ncity)){
                    Toast.makeText(ApplyAsIndustry.this, "Enter your City", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Ncardinal)){
                    Toast.makeText(ApplyAsIndustry.this, "Enter your Cardinal Direction", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Nregno)){
                    Toast.makeText(ApplyAsIndustry.this, "Enter your NGO registration number", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(mobile_no.getText().toString().trim())){
                    Toast.makeText(ApplyAsIndustry.this, "Enter your Mobile Number", Toast.LENGTH_SHORT).show();
                }else{
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submit code
                if(afterText.getVisibility()==View.GONE){
                    Toast.makeText(ApplyAsIndustry.this, "Upload official NGO document", Toast.LENGTH_SHORT).show();
                }else if(filterName.getText().toString().equals("Select Type")){
                    Toast.makeText(ApplyAsIndustry.this, "Select type first", Toast.LENGTH_SHORT).show();
                }else{
                    Industry industry = new Industry(Nname, Nemail, Nmobile, userImageUrl, Naddress, Ncity, Ncardinal, 1,
                            filterName.getText().toString(),
                            new Volunteer(null,null,0,null,
                                    null,0,0,null,null),
                            new HashMap<String, Boolean>(), Nregno, String.valueOf(path));
                    accountref.setValue(industry);
                    Toast.makeText(ApplyAsIndustry.this, "Response Recorded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ApplyAsIndustry.this, Home.class);
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

            StorageReference filepath= mStorage.child("industry_official_pic").child(uri.getLastPathSegment());
            try
            {
                compressed = MediaStore.Images.Media.getBitmap(ApplyAsIndustry.this.getContentResolver(), uri);
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
                    Toast.makeText(ApplyAsIndustry.this, "Document uploaded", Toast.LENGTH_LONG).show();
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
