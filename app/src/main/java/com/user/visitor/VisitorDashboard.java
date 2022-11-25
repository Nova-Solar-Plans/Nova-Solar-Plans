package com.user.visitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.user.main.R;

public class VisitorDashboard extends AppCompatActivity {

    private CardView TechnicianCard,ProductCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_dashboard);


        TechnicianCard = findViewById(R.id.technician_card);
        ProductCard = findViewById(R.id.product_card);

        TechnicianCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitorDashboard.this, VisitorTechnician.class);
                Toast.makeText(VisitorDashboard.this,"Technicians",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        ProductCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitorDashboard.this,VisitorProduct.class);
                Toast.makeText(VisitorDashboard.this,"Products",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}