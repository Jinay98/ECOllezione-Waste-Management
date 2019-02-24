package com.example.android.wastemanagement;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.wastemanagement.Models.Industry;
import com.example.android.wastemanagement.Models.Ngo;
import com.example.android.wastemanagement.Models.Post;
import com.example.android.wastemanagement.Models.User;
import com.example.android.wastemanagement.Models.Volunteer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Wall extends AppCompatActivity {

    EditText chatText;
    ImageView send, mic;
    View view;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    String name, imgUrl, userType;
    FirebaseRecyclerAdapter<Post, PostAdapterViewHolder> adapter;
    android.support.v7.widget.Toolbar toolbar;

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
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.trainChats);

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
                                    Post post = new Post(name,imgUrl,chatText.getText().toString(),"no",auth.getUid());
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                                            .child("wall").push();
                                    String key = db.getKey().toString();
                                    db.setValue(post);
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
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    chatText.setText(result.get(0));
                }
                break;
            }

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
                if(!post.getPostImgUrl().equals("no")) {
                    postAdapterViewHolder.setPostImg(post.getPostImgUrl());
                }
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public boolean onSupportNavigateUp(){

        onBackPressed();
        return true;
    }
}
