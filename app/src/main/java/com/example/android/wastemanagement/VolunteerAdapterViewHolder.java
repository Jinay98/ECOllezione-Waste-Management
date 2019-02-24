package com.example.android.wastemanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VolunteerAdapterViewHolder extends RecyclerView.ViewHolder {

    Context context;
    TextView name,email,mobile;
    ImageView profile, aadhar;
    Boolean tapOpen = false;
    ImageView accept, reject;
    TextView tap;
    FirebaseAuth auth;
    String userType, currentVolunteer;

    public VolunteerAdapterViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.volunteerName);
        email = itemView.findViewById(R.id.volunteerEmail);
        mobile = itemView.findViewById(R.id.volunteerMobile);
        profile = itemView.findViewById(R.id.volunteerImage);
        aadhar = itemView.findViewById(R.id.volunteerAadhar);
        accept = itemView.findViewById(R.id.accept);
        reject = itemView.findViewById(R.id.reject);
        tap = itemView.findViewById(R.id.tap);
        auth = FirebaseAuth.getInstance();

        tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tapOpen){
                    tapOpen = true;
                    aadhar.setVisibility(View.VISIBLE);
                    tap.setText("Tap to Close");
                }else{
                    tapOpen = false;
                    aadhar.setVisibility(View.GONE);
                    tap.setText("Tap to view Aadhar card");
                }
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbrefx = FirebaseDatabase.getInstance().getReference()
                        .child("volunteer").child(currentVolunteer).child("accept_status");
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(userType).child(auth.getUid())
                        .child("toApprove").child(currentVolunteer);
                DatabaseReference dbVolunteer = FirebaseDatabase.getInstance().getReference().child(userType).child(auth.getUid())
                        .child("approvedList").child(currentVolunteer);
                dbVolunteer.setValue(true);
                dbrefx.setValue((long)1);
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            dataSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbrefx = FirebaseDatabase.getInstance().getReference()
                        .child("volunteer").child(currentVolunteer).child("reg_status");
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(userType).child(auth.getUid())
                        .child("toApprove").child(currentVolunteer);
                dbrefx.setValue((long)0);
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            dataSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    public void setContext(Context context){
        this.context = context;
    }
    public void setKey(String key){
        currentVolunteer = key;
    }
    public void setName(String username){
        name.setText(username);
    }
    public void setEmail(String userEmail){
        email.setText(userEmail);
    }
    public void setMobile(long userContact){
        mobile.setText(String.valueOf(userContact));
    }
    public void setImage(String image){
        Glide.with(context).load(image).into(profile);
    }
    public void setAadhar(String aadharUri){
        Glide.with(context).load(aadharUri).into(aadhar);
    }
    public void setMyType(String userType){
        this.userType = userType;
    }
}
