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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

public class ApplyAsVolunteer extends AppCompatActivity {

    EditText name,email,mobile_no,address;
    Button submit, submitstep1;
    LinearLayout clicksubmit,step1,step2;
    TextView afterText;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner spinner;
    private static final int GALLERY_INTETN=2;
    public ProgressDialog dialog;
    private StorageReference mStorage;
    private DatabaseReference accountref, db;
    FirebaseAuth auth;
    Bitmap compressed;
    Uri path;
    String radioSelected="";
    List<String> ngoName = new ArrayList<>();
    List<String> ngoAuth = new ArrayList<>();
    String ngoAuthKey, userImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_as_volunteer);

        name = findViewById(R.id.form_name);
        email = findViewById(R.id.form_email);
        mobile_no = findViewById(R.id.form_mobile);
        address = findViewById(R.id.form_address);
        submit = findViewById(R.id.form_submit);
        submitstep1 = findViewById(R.id.form_step1submit);
        clicksubmit = findViewById(R.id.clicksubmit);
        afterText = findViewById(R.id.afterText);
        radioGroup = findViewById(R.id.radio);
        step1 = findViewById(R.id.step1);
        step2 = findViewById(R.id.step2);
        spinner = findViewById(R.id.form_spinner);

        dialog = new ProgressDialog(ApplyAsVolunteer.this);
        dialog.setMessage("Updating please wait...");

        mStorage = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        accountref = FirebaseDatabase.getInstance().getReference().child("volunteer").child(auth.getUid());
        accountref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Volunteer volunteer = dataSnapshot.getValue(Volunteer.class);
                name.setText(volunteer.getName());
                email.setText(volunteer.getVolunteerEmail());
                userImageUrl = volunteer.getUserImgUrl();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        clicksubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(ApplyAsVolunteer.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ApplyAsVolunteer.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

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
                int selected = radioGroup.getCheckedRadioButtonId();
                if(selected==-1){
                    Toast.makeText(ApplyAsVolunteer.this, "Please select one before submitting", Toast.LENGTH_SHORT).show();
                }else{
                    radioButton = findViewById(selected);
                    radioSelected = radioButton.getText().toString();
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                    dbref.child(radioSelected).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                ngoAuth.add(snapshot.getKey().toString());
                                ngoName.add(snapshot.child("name").getValue(String.class));
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    ApplyAsVolunteer.this, android.R.layout.simple_spinner_item, ngoName);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //spinner.setSelection(0,true);
                            spinner.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ApplyAsVolunteer.this, ngoName.get(i)+" : "+ngoAuth.get(i), Toast.LENGTH_SHORT).show();
                ngoAuthKey = ngoAuth.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submit code
                String Vname = name.getText().toString().trim();
                String Vemail = email.getText().toString().trim();
                //Long Vmobile = Long.valueOf(mobile_no.getText().toString());
                String Vaddress = address.getText().toString().trim();

                Log.d("formSubmissionVname",String.valueOf(TextUtils.isEmpty(Vname)));
                Log.d("formSubmissionEmail",String.valueOf(TextUtils.isEmpty(Vemail)));
                Log.d("formSubmissionAddress",String.valueOf(TextUtils.isEmpty(Vaddress)));

                if(TextUtils.isEmpty(Vname)){
                    Toast.makeText(ApplyAsVolunteer.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Vemail)){
                    Toast.makeText(ApplyAsVolunteer.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(Vaddress)){
                    Toast.makeText(ApplyAsVolunteer.this, "Enter your Address", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(mobile_no.getText().toString().trim())){
                    Toast.makeText(ApplyAsVolunteer.this, "Enter your Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else if(afterText.getVisibility()==View.GONE){
                    Toast.makeText(ApplyAsVolunteer.this, "Upload government ID", Toast.LENGTH_SHORT).show();
                }else{
                    db = FirebaseDatabase.getInstance().getReference().child(radioSelected).child(ngoAuthKey)
                            .child("toApprove").child(auth.getUid());
                    DatabaseReference dbrefx = FirebaseDatabase.getInstance().getReference()
                            .child("volunteer").child(auth.getUid());
                    db.setValue(new Volunteer(Vname,Vemail,Long.valueOf(mobile_no.getText().toString().trim()),userImageUrl,
                            Vaddress,1,0,ngoAuthKey, String.valueOf(path)));
                    dbrefx.setValue(new Volunteer(Vname,Vemail,Long.valueOf(mobile_no.getText().toString().trim()),userImageUrl,
                            Vaddress,1,0,ngoAuthKey, String.valueOf(path)));
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

            StorageReference filepath= mStorage.child("volunteer_aadhar_pic").child(uri.getLastPathSegment());
            try
            {
                compressed = MediaStore.Images.Media.getBitmap(ApplyAsVolunteer.this.getContentResolver(), uri);
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
                    Toast.makeText(ApplyAsVolunteer.this, "Document uploaded", Toast.LENGTH_LONG).show();
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
