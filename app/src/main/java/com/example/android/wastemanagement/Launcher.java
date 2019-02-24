package com.example.android.wastemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Launcher extends AppCompatActivity {

    ProgressDialog loginProgress;
    private FirebaseAuth auth;
    private CardView donor_card, volunteer_card, ngo_card, industry_card, admin_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        auth = FirebaseAuth.getInstance();
        loginProgress = ProgressDialog.show(this, null, "Please wait...", true);
        loginProgress.setCancelable(false);

        if (auth.getCurrentUser() != null) {
            loginProgress.dismiss();
            startActivity(new Intent(Launcher.this, Home.class));
            finish();
            return;
        }
        loginProgress.dismiss();
        donor_card = findViewById(R.id.donor_card);
        volunteer_card = findViewById(R.id.volunteer_card);
        ngo_card = findViewById(R.id.ngo_card);
        industry_card = findViewById(R.id.industry_card);
        admin_card = findViewById(R.id.admin_card);

        donor_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Launcher.this,Register.class);
                intent.putExtra("type","donor");
                startActivity(intent);
            }
        });

        volunteer_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Launcher.this,Register.class);
                intent.putExtra("type","volunteer");
                startActivity(intent);
            }
        });

        ngo_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Launcher.this,Register.class);
                intent.putExtra("type","ngo");
                startActivity(intent);
            }
        });
        industry_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Launcher.this,Register.class);
                intent.putExtra("type","industry");
                startActivity(intent);
            }
        });
        admin_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Launcher.this,Login.class);
                intent.putExtra("type","admin");
                startActivity(intent);
            }
        });

    }
}
