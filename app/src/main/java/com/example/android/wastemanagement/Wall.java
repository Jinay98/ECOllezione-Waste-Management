package com.example.android.wastemanagement;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.wastemanagement.Models.Industry;
import com.example.android.wastemanagement.Models.Ngo;
import com.example.android.wastemanagement.Models.Post;
import com.example.android.wastemanagement.Models.User;
import com.example.android.wastemanagement.Models.Volunteer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
import java.util.Locale;

public class Wall extends AppCompatActivity {

    EditText chatText;
    ImageView send, mic, attach;
    TextView isAttached;
    View view;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    String name, imgUrl, userType;
    FirebaseRecyclerAdapter<Post, PostAdapterViewHolder> adapter;
    android.support.v7.widget.Toolbar toolbar;
    private static final int GALLERY_INTETN=2;
    public ProgressDialog dialog;
    private StorageReference mStorage;
    Bitmap compressed;
    Uri path;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        toolbar = findViewById(R.id.accout_setting);
        toolbar.setTitle("Wall");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chatText = findViewById(R.id.create_post_edittext);
        send = findViewById(R.id.create_post_upload);
        mic = findViewById(R.id.create_post_stt);
        attach = findViewById(R.id.create_post_attachment);
        isAttached = findViewById(R.id.isAttached);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.trainChats);

        dialog = new ProgressDialog(Wall.this);
        dialog.setMessage("Updating please wait...");

        mStorage = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            userType = bundle.getString("userType");
            Log.d("userType",userType);
            if(userType.equals("donor")){
                Log.d("userType", "inside if");
                mic.setEnabled(false);
                send.setEnabled(false);
                chatText.setEnabled(false);
            }
        }
        mic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Wall.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Wall.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {
                    Intent mintetnt = new Intent(Intent.ACTION_PICK);
                    mintetnt.setType("image/*");
                    startActivityForResult(mintetnt, GALLERY_INTETN);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String msg = chatText.getText().toString();
                if(msg.isEmpty()){
                    Toast.makeText(Wall.this,"Please Enter Message First", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("user_details")
                            .child(auth.getUid());
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String userType = dataSnapshot.getValue(String.class);
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(userType).child(auth.getUid());
                            db.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                    if(userType.equals("donor")){
                                        User user = dataSnapshot1.getValue(User.class);
                                        name = user.getName();
                                        imgUrl = user.getUserImgUrl();
                                    }else if(userType.equals("volunteer")){
                                        Volunteer user = dataSnapshot1.getValue(Volunteer.class);
                                        name = user.getName();
                                        imgUrl = user.getUserImgUrl();
                                    }else if(userType.equals("ngo")){
                                        Ngo user = dataSnapshot1.getValue(Ngo.class);
                                        name = user.getName();
                                        imgUrl = user.getUserImgUrl();
                                    }else if(userType.equals("industry")){
                                        Industry user = dataSnapshot1.getValue(Industry.class);
                                        name = user.getName();
                                        imgUrl = user.getUserImgUrl();
                                    }
                                    if(isAttached.getVisibility()==View.VISIBLE){
                                        Post post = new Post(name,imgUrl,chatText.getText().toString(),String.valueOf(path),auth.getUid());
                                        DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                                                .child("wall").push();
                                        String key = db.getKey().toString();
                                        db.setValue(post);
                                        isAttached.setVisibility(View.GONE);
                                    }else{
                                        Post post = new Post(name,imgUrl,chatText.getText().toString(),"no",auth.getUid());
                                        DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                                                .child("wall").push();
                                        String key = db.getKey().toString();
                                        db.setValue(post);
                                        isAttached.setVisibility(View.GONE);
                                    }

                                    /*DatabaseReference dblikeCount = FirebaseDatabase.getInstance().getReference()
                                            .child("likeCount").child(key);
                                    dblikeCount.setValue((int)0);*/
                                    chatText.setText("");
                                    chatText.clearFocus();
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
                }
            }
        });

        loaddata();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(Wall.this,
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SPEECH_INPUT && resultCode == RESULT_OK && null != data) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                chatText.setText(result.get(0));
        }
        if (requestCode == GALLERY_INTETN && resultCode==RESULT_OK) {
            dialog.setMessage("Uploading");
            dialog.show();
            Uri uri= data.getData();

            StorageReference filepath= mStorage.child("wall_pic").child(uri.getLastPathSegment());
            try
            {
                compressed = MediaStore.Images.Media.getBitmap(Wall.this.getContentResolver(), uri);
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
                    Toast.makeText(Wall.this, "Document uploaded", Toast.LENGTH_LONG).show();
                    //finish();
                    //startActivity(getIntent());
                    isAttached.setVisibility(View.VISIBLE);
                    attach.setVisibility(View.GONE);
                    dialog.dismiss();
                }
            });
        }
    }
    private void loaddata(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("wall");
        dbref.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<Post, PostAdapterViewHolder>
                (Post.class, R.layout.post_item_list,
                        PostAdapterViewHolder.class,
                        dbref) {

            public void populateViewHolder(final PostAdapterViewHolder postAdapterViewHolder,
                                           final Post post, final int position) {
                String key = this.getRef(position).getKey().toString();
                Log.d("key",key);
                Log.d("Position", String.valueOf(position));

                postAdapterViewHolder.setContext(Wall.this);
                postAdapterViewHolder.setName(post.getName());
                postAdapterViewHolder.setMessage(post.getMessage());
                if(!post.getUserImgUrl().equals("no")){
                    postAdapterViewHolder.setUserImage(post.getUserImgUrl());
                }
                postAdapterViewHolder.setPostImg(post.getPostImgUrl());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public boolean onSupportNavigateUp(){

        onBackPressed();
        return true;
    }
}
