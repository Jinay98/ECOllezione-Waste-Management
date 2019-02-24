package com.example.android.wastemanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.android.wastemanagement.Models.Volunteer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApproveVolunteer extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    private FirebaseAuth auth;
    String userType;
    FirebaseRecyclerAdapter<Volunteer, VolunteerAdapterViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_volunteer);

        toolbar = findViewById(R.id.aprroveVolunteerToolbar);
        toolbar.setTitle("Approve Volunteer");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            userType = bundle.getString("userType");
        }
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.rv_volunteer);
        loaddata();
    }

    public void loaddata(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("ngo")
                .child(auth.getUid()).child("toApprove");
        dbref.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<Volunteer, VolunteerAdapterViewHolder>
                (Volunteer.class, R.layout.volunteer_list_item,
                        VolunteerAdapterViewHolder.class,
                        dbref) {

            public void populateViewHolder(final VolunteerAdapterViewHolder volunteerAdapterViewHolder,
                                           final Volunteer volunteer, final int position) {
                String key = this.getRef(position).getKey().toString();
                Log.d("key",key);
                Log.d("Position", String.valueOf(position));

                volunteerAdapterViewHolder.setKey(key);
                volunteerAdapterViewHolder.setContext(ApproveVolunteer.this);
                volunteerAdapterViewHolder.setName(volunteer.getName());
                volunteerAdapterViewHolder.setEmail(volunteer.getVolunteerEmail());
                volunteerAdapterViewHolder.setMobile(volunteer.getVolunteerContact());
                if(!volunteer.getUserImgUrl().equals("no")){
                    volunteerAdapterViewHolder.setImage(volunteer.getUserImgUrl());
                }
                volunteerAdapterViewHolder.setAadhar(volunteer.getGovnImgUrl());
                volunteerAdapterViewHolder.setMyType(userType);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public boolean onSupportNavigateUp(){

        onBackPressed();
        return true;
    }

}
