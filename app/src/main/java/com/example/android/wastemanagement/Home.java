package com.example.android.wastemanagement;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.wastemanagement.Models.Industry;
import com.example.android.wastemanagement.Models.Ngo;
import com.example.android.wastemanagement.Models.User;
import com.example.android.wastemanagement.Models.Volunteer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class Home extends AppCompatActivity implements OnMapReadyCallback {

    android.support.v7.widget.Toolbar toolbar;
    public boolean closeview = false;
    private FirebaseAuth auth;
    private FirebaseUser userF;
    private TextView userName, userEmail;
    DatabaseReference dbuser, dbtoken;
    ImageView userImg;
    String userType;
    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LocationListener locationListener;


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
                        if(menuItem.getItemId() == R.id.scan){
                            Intent intent = new Intent(Home.this, Scan.class);
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


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //get the location name from latitude and longitude
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses =
                            geocoder.getFromLocation(latitude, longitude, 1);
                    String result = addresses.get(0).getLocality()+":";
                    result += addresses.get(0).getCountryName();
                    Toast.makeText(Home.this, "result is ="+result, Toast.LENGTH_LONG).show();
                    LatLng latLng = new LatLng(latitude, longitude);
                    if (marker != null){
                        marker.remove();
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                        mMap.setMaxZoomPreference(20);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                    }
                    else{
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                        mMap.setMaxZoomPreference(20);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
