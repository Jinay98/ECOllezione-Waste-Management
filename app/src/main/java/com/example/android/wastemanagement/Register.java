package com.example.android.wastemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wastemanagement.Models.Bandwidth;
import com.example.android.wastemanagement.Models.Industry;
import com.example.android.wastemanagement.Models.Ngo;
import com.example.android.wastemanagement.Models.User;
import com.example.android.wastemanagement.Models.Volunteer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    public ProgressDialog dialog;
    ProgressDialog loginProgress;
    String username,email,password;
    private EditText inputEmail, inputPassword, Name;
    private TextView btnSignIn;
    private Button btnSignUp;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    String userType;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            userType = bundle.getString("type");
        }
        btnSignUp = findViewById(R.id.sign_up_button);
        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        Name = findViewById(R.id.name);

        mAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(Register.this);
        dialog.setMessage("Creating your account...");
        auth = FirebaseAuth.getInstance();
        mDatabase =  FirebaseDatabase.getInstance().getReference();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                username = Name.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter your Username!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user    tv.setText(text); // tv is null

                loginProgress = ProgressDialog.show(Register.this, null, "Please wait...", true);
                loginProgress.setCancelable(false);

                Log.d("Info", "Creating...");
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Log.d("Info", "inside onComplete()");

                                if (!task.isSuccessful()) {
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "Authentication failed. Try again after some time", Toast.LENGTH_SHORT).show();

                                } else {

                                    Log.d("Info", "Task successful..");
                                    Log.d("Info", "Commit...");
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance()
                                            .getReference("user_details");
                                    String token = FirebaseInstanceId.getInstance().getToken();
                                    Log.d("Info", "Username: " + username);
                                    Log.d("Info", "Email:" + email);
                                    mDatabase.child(auth.getUid()).setValue(userType);

                                    if(userType.equals("donor")){
                                        User user = new User(username, email,0, "no", "no", "no",
                                                "no", 0, 0, token);
                                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("donor").child(auth.getUid());
                                        db.setValue(user);
                                    }else if(userType.equals("volunteer")){
                                        Volunteer user = new Volunteer(username, email,0, "no", "no", 0,0,"no","no");
                                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("volunteer").child(auth.getUid());
                                        db.setValue(user);
                                    }else if(userType.equals("ngo")){
                                        Ngo user = new Ngo(username, email,0, "no", "no", "no", "no",
                                                0,new Bandwidth(0,0,0,0,0,0,0),
                                                //new Volunteer(null,null,0,null,
                                                //        null,0,0,null,null)
                                                new Volunteer(),new HashMap<String, Boolean>(),"no", "no");
                                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("ngo").child(auth.getUid());
                                        db.setValue(user);
                                    }else if(userType.equals("industry")) {
                                        Industry user = new Industry(username, email, 0, "no", "no", "no", "no",
                                                0, "no", new Volunteer(null, null, 0, null,
                                                null, 0, 0,null, null), new HashMap<String, Boolean>(), "no", "no");
                                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("industry").child(auth.getUid());
                                        db.setValue(user);
                                    }

                                    dialog.dismiss();
                                    startActivity(new Intent(Register.this, Home.class));
                                    finish();
                                }

                            }
                        });
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //mAuth.addAuthStateListener(mAuthListener);
    }
    public void signin(View view) {

        Intent i = new Intent(Register.this, Login.class);
        startActivity(i);
        finish();

    }
}
