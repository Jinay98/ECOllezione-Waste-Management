package com.example.android.wastemanagement;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.wastemanagement.Models.Industry;
import com.example.android.wastemanagement.Models.Ngo;
import com.example.android.wastemanagement.Models.User;
import com.example.android.wastemanagement.Models.Volunteer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    public boolean closeview = false;
    private FirebaseAuth auth;
    private FirebaseUser userF;
    private TextView userName, userEmail;
    DatabaseReference dbuser, dbtoken;
    ImageView userImg;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.feature_req_toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        auth = FirebaseAuth.getInstance();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawer.closeDrawers();

                        if(menuItem.getItemId() == R.id.logout){
                            /*dbtoken = FirebaseDatabase.getInstance().getReference().child("user_details")
                                    .child(auth.getUid()).child("token");
                            dbtoken.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        dataSnapshot.getRef().removeValue();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/
                            auth.signOut();
                            userF = FirebaseAuth.getInstance().getCurrentUser();
                            if (userF == null) {
                                // user auth state is changed - user is null
                                // launch login activity
                                Intent intent = new Intent(Home.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                            return true;
                        }

                        if(menuItem.getItemId() == R.id.accSetting){
                            Intent intent = new Intent(Home.this, AccountSetting.class);
                            startActivity(intent);
                        }
                        if(menuItem.getItemId() == R.id.approve_volunteers){
                            Intent intent = new Intent(Home.this, ApproveVolunteer.class);
                            intent.putExtra("userType", userType);
                            startActivity(intent);
                        }
                        if(menuItem.getItemId() == R.id.wall){
                            Intent intent = new Intent(Home.this, Wall.class);
                            intent.putExtra("userType", userType);
                            startActivity(intent);
                        }
                        if(menuItem.getItemId() == R.id.map){
                            Intent intent = new Intent(Home.this, MapsActivity.class);
                            startActivity(intent);
                        }
                        /*if(menuItem.getItemId() == R.id.show_maps) {
                            Intent intent = new Intent(Home.this, MapsActivity.class);
                            startActivity(intent);
                        }
                        if(menuItem.getItemId() == R.id.ambulance){
                            Intent intent = new Intent(Home.this, MapsActivity2.class);
                            startActivity(intent);
                        }

                        if(menuItem.getItemId() == R.id.createZone){
                            Intent intent = new Intent(Home.this, CreateZone.class);
                            startActivity(intent);
                        }
                        if(menuItem.getItemId() == R.id.fineOfficer){
                            Intent intent = new Intent(Home.this, Fine.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.viewFine) {

                            Intent intent = new Intent(Home.this, UserFine.class);

                            startActivity(intent);
                        }
                        if(menuItem.getItemId()==R.id.finerates){
                            Intent intent = new Intent(Home.this, FineRules.class);
                            startActivity(intent);
                        }*/
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        navigationView.setItemIconTintList(null);
        userName = navigationView.getHeaderView(0).findViewById(R.id.head_name);
        userEmail = navigationView.getHeaderView(0).findViewById(R.id.head_email);
        userImg = navigationView.getHeaderView(0).findViewById(R.id.user_image);

        Log.d("auth_id",auth.getUid());
        dbuser = FirebaseDatabase.getInstance().getReference().child("user_details").child(auth.getUid());
        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userType = dataSnapshot.getValue(String.class);
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child(userType).child(auth.getUid());
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot1) {
                        if(userType.equals("donor")){
                            User user = dataSnapshot1.getValue(User.class);
                            userName.setText(user.getName());
                            userEmail.setText(user.getEmail());
                            if(!user.getUserImgUrl().equals("no")){
                                Glide.with(getApplicationContext()).load(user.getUserImgUrl()).into(userImg);
                            }
                            if(user.getReg_status()==0){
                                //open applyAsDonor form
                                Intent intent = new Intent(Home.this, ApplyAsDonor.class);
                                startActivity(intent);
                            }
                        }else if(userType.equals("volunteer")){
                            Volunteer user = dataSnapshot1.getValue(Volunteer.class);
                            userName.setText(user.getName());
                            userEmail.setText(user.getVolunteerEmail());
                            if(!user.getUserImgUrl().equals("no")){
                                Glide.with(getApplicationContext()).load(user.getUserImgUrl()).into(userImg);
                            }
                            if(user.getReg_status()==0){
                                //open applyAsVolunteer form
                                Intent intent = new Intent(Home.this, ApplyAsVolunteer.class);
                                startActivity(intent);
                            }
                        }else if(userType.equals("ngo")){
                            Ngo user = dataSnapshot1.getValue(Ngo.class);
                            userName.setText(user.getName());
                            userEmail.setText(user.getNgoEmail());
                            if(!user.getUserImgUrl().equals("no")){
                                Glide.with(getApplicationContext()).load(user.getUserImgUrl()).into(userImg);
                            }
                            if(user.getReg_status()==0){
                                //open applyAsNgo form
                                Intent intent = new Intent(Home.this, ApplyAsNgo.class);
                                startActivity(intent);
                            }else{
                                navigationView.getMenu().findItem(R.id.approve_volunteers).setVisible(true);
                            }
                        }else if(userType.equals("industry")){
                            Industry user = dataSnapshot1.getValue(Industry.class);
                            userName.setText(user.getName());
                            userEmail.setText(user.getIndustryEmail());
                            if(!user.getUserImgUrl().equals("no")){
                                Glide.with(getApplicationContext()).load(user.getUserImgUrl()).into(userImg);
                            }
                            if(user.getReg_status()==0){
                                //open applyAsIndustry form
                            }else{
                                navigationView.getMenu().findItem(R.id.approve_volunteers).setVisible(true);
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

    }

    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (closeview) {
            closeview = false;
            finish();
            startActivity(getIntent());

        } else {
            super.onBackPressed();
        }
    }

    public void closeview(Boolean value) {
        closeview = value;
    }
}
