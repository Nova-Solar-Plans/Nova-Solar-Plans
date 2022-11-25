package com.user.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.user.customer.CustomerLogin;
import com.user.manager.ManagerLogin;
import com.user.technician.TechnicianLogin;
import com.user.visitor.VisitorDashboard;

public class UserNavigation extends AppCompatActivity {

    private Button TechnicianBtn,CompanyBtn,GuestBtn,CustomerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_navigation);

        this.TechnicianBtn = findViewById(R.id.technician_navigate_btn);
        this.CompanyBtn = findViewById(R.id.company_navigate_btn);
        this.GuestBtn = findViewById(R.id.guest);
        this.CustomerBtn = findViewById(R.id.customer_navigation_btn);


        this.TechnicianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserNavigation.this, TechnicianLogin.class);
                startActivity(intent);
                finish();
            }
        });

        this.GuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserNavigation.this, VisitorDashboard.class);
                startActivity(intent);
                finish();
            }
        });

        this.CompanyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserNavigation.this, ManagerLogin.class);
                startActivity(intent);
            }
        });

        this.CustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserNavigation.this, CustomerLogin.class);
                startActivity(intent);
            }
        });


    }
}