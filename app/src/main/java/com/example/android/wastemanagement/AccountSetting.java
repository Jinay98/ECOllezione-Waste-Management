package com.example.android.wastemanagement;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class AccountSetting extends AppCompatActivity {

    TextView accName, accType, accEmail;
    ImageView userImg;
    DatabaseReference dbref;
    FirebaseAuth auth;
    public ProgressDialog dialog;
    AlertDialog.Builder AlertName;
    EditText edittext;
    Bitmap compressed;
    String userType;
    private DatabaseReference accountref, db;
    private StorageReference mStorage;
    android.support.v7.widget.Toolbar toolbar;
    private static final int GALLERY_INTETN=2;

    Button btnQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        toolbar = findViewById(R.id.accout_setting);
        toolbar.setTitle("Account Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dialog = new ProgressDialog(AccountSetting.this);
        dialog.setMessage("Updating please wait...");

        auth = FirebaseAuth.getInstance();
        accName = findViewById(R.id.accName);
        accType = findViewById(R.id.accType);
        accEmail = findViewById(R.id.email);
        userImg = findViewById(R.id.userimg);

        btnQR = (Button)findViewById(R.id.getQR);

        mStorage = FirebaseStorage.getInstance().getReference();
        dbref = FirebaseDatabase.getInstance().getReference().child("user_details").child(auth.getUid());
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userType = dataSnapshot.getValue(String.class);
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child(userType).child(auth.getUid());
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if(userType.equals("donor")){
                            User user = dataSnapshot1.getValue(User.class);
                            accName.setText(user.getName());
                            accEmail.setText(user.getEmail());
                            if(!user.getUserImgUrl().equals("no")){
                                Glide.with(getApplicationContext()).load(user.getUserImgUrl()).into(userImg);
                            }
                        }else if(userType.equals("volunteer")){
                            Volunteer user = dataSnapshot1.getValue(Volunteer.class);
                            accName.setText(user.getName());
                            accEmail.setText(user.getVolunteerEmail());
                            if(!user.getUserImgUrl().equals("no")){
                                Glide.with(getApplicationContext()).load(user.getUserImgUrl()).into(userImg);
                            }
                        }else if(userType.equals("ngo")){
                            Ngo user = dataSnapshot1.getValue(Ngo.class);
                            accName.setText(user.getName());
                            accEmail.setText(user.getNgoEmail());
                            if(!user.getUserImgUrl().equals("no")){
                                Glide.with(getApplicationContext()).load(user.getUserImgUrl()).into(userImg);
                            }
                        }else if(userType.equals("industry")){
                            Industry user = dataSnapshot1.getValue(Industry.class);
                            accName.setText(user.getName());
                            accEmail.setText(user.getIndustryEmail());
                            if(!user.getUserImgUrl().equals("no")){
                                Glide.with(getApplicationContext()).load(user.getUserImgUrl()).into(userImg);
                            }
                        }



                        /*if(user.getUserType().equals("volunteer")){
                            if(user.getVolunteer_req_status()==0){
                                //open new form page
                                Intent intent = new Intent(Home.this, ApplyAsVolunteer.class);
                                startActivity(intent);
                            }else{
                                //text not yet accepted
                                navigationView.getMenu().findItem(R.id.apply_volunteership).setVisible(true);
                                navigationView.getMenu().findItem(R.id.status).setVisible(true);
                            }
                        }
                        if(user.getUserType().equals("ngo")) {
                            if (user.getNgo_req_status() == 0) {
                                //open new form page
                                Intent intent = new Intent(Home.this, ApplyAsNgo.class);
                                startActivity(intent);
                            }else{
                                //if (user.getNgo_accepted_status() == 1) {
                                //text not yet accepted
                                navigationView.getMenu().findItem(R.id.approve_volunteers).setVisible(true);
                            }
                        }  */
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db = FirebaseDatabase.getInstance().getReference().child(userType).child(auth.getUid()).child("userImgUrl");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Glide.with(getApplicationContext()).load(dataSnapshot.getValue(String.class)).apply(new RequestOptions().circleCrop()).into(userImg);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        accName.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                dialog.show();
                AlertName = new AlertDialog.Builder(AccountSetting.this);
                edittext = new EditText(AccountSetting.this);
                edittext.setText(accName.getText().toString());
                AlertName.setTitle("Change User Name");
                //alert.setMessage("Enter Your Title");
                AlertName.setView(edittext);
                AlertName.setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    public void onClick(final DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        final String newName = String.valueOf(edittext.getText());
                        DatabaseReference accountref = FirebaseDatabase.getInstance().getReference()
                                .child(userType)
                                .child(auth.getUid())
                                .child("name");

                        accountref.setValue(newName);
                        finish();
                        startActivity(getIntent());
                        dialog.dismiss();
                    }
                });

                AlertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        dialog.dismiss();
                    }
                });
                AlertName.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        dialog.dismiss();
                    }
                });
                AlertName.show();
            }
        });

        /*btnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent obj = new Intent(AccountSetting.this , QR.class);
                obj.putExtra("Email", accEmail.getText().toString().trim());
                startActivity(obj);
            }
        });*/

        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(AccountSetting.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(AccountSetting.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                }else {
                    Intent mintetnt =new Intent(Intent.ACTION_PICK);
                    mintetnt.setType("image/*");
                    startActivityForResult(mintetnt,GALLERY_INTETN);
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

            StorageReference filepath= mStorage.child("user_profile_pic").child(uri.getLastPathSegment());
            try
            {
                compressed = MediaStore.Images.Media.getBitmap(AccountSetting.this.getContentResolver(), uri);
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
                    Uri path= taskSnapshot.getDownloadUrl();
                    accountref = FirebaseDatabase.getInstance().getReference().child(userType).child(auth.getUid());
                    accountref.child("userImgUrl").setValue(String.valueOf(path));
                    Toast.makeText(AccountSetting.this, "Profile Changed", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
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
