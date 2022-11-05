package com.user.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.user.technician.TechnicianLogin;

public class UserNavigation extends AppCompatActivity {

    private Button TechnicianBtn,CompanyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_navigation);

        this.TechnicianBtn = findViewById(R.id.technician_navigate_btn);
        this.CompanyBtn = findViewById(R.id.company_navigate_btn);


        this.TechnicianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserNavigation.this, TechnicianLogin.class);
                startActivity(intent);
                finish();
            }
        });


    }
}