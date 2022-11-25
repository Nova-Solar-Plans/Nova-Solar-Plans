package com.user.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.visitor.RequestQuote;
import com.user.visitor.VisitorProduct;

import java.util.HashMap;

public class CustomerRequests extends AppCompatActivity {

    private EditText Name,Email,Number,Address,RoofHeight,RoofWidth,RoofMaterial,Description;
    private Button CreateReqBtn;

    private DatabaseReference databaseReference;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_requests);

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("productId");

        Name = findViewById(R.id.customer_name);
        Email = findViewById(R.id.customer_email);
        Number = findViewById(R.id.customer_contact);
        Address = findViewById(R.id.customer_address);
        RoofHeight = findViewById(R.id.customer_roof_height);
        RoofWidth = findViewById(R.id.customer_roof_width);
        RoofMaterial = findViewById(R.id.roof_material);
        Description = findViewById(R.id.description);
        CreateReqBtn = findViewById(R.id.create_req_btn);


        CreateReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = Name.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String address = Address.getText().toString().trim();
                String number = Number.getText().toString().trim();
                String roofHeight = RoofHeight.getText().toString().trim();
                String roofWidth = RoofWidth.getText().toString().trim();
                String roofMaterial = RoofMaterial.getText().toString().trim();
                String description = Description.getText().toString().trim();
                boolean isSuccess = true;

                if(name.isEmpty()){
                    Name.setError("Field required");
                    Name.requestFocus();
                    isSuccess = false;
                }
                if(email.isEmpty()){
                    Email.setError("Field required");
                    Email.requestFocus();
                    isSuccess = false;
                }
                if(address.isEmpty()){
                    Address.setError("Field required");
                    Address.requestFocus();
                    isSuccess = false;
                }
                if(number.isEmpty()){
                    Number.setError("Field required");
                    Number.requestFocus();
                    isSuccess = false;
                }
                if(roofHeight.isEmpty()){
                    RoofHeight.setError("Field required");
                    RoofHeight.requestFocus();
                    isSuccess = false;
                }
                if(roofWidth.isEmpty()){
                    RoofWidth.setError("Field required");
                    RoofWidth.requestFocus();
                    isSuccess = false;
                }
                if(roofMaterial.isEmpty()){
                    RoofMaterial.setError("Field required");
                    RoofMaterial.requestFocus();
                    isSuccess = false;
                }
                if(description.isEmpty()){
                    Description.setError("Field required");
                    Description.requestFocus();
                    isSuccess = false;
                }

                if(isSuccess){

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("name",Name.getText().toString());
                    hashMap.put("email",Email.getText().toString());
                    hashMap.put("contact",Number.getText().toString());
                    hashMap.put("address",Address.getText().toString());
                    hashMap.put("roofHeight",RoofHeight.getText().toString());
                    hashMap.put("roofWidth",RoofWidth.getText().toString());
                    hashMap.put("material",RoofMaterial.getText().toString());
                    hashMap.put("description",Description.getText().toString());

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    databaseReference = db.getReference("Tasks").child("New Planting");

                    databaseReference.push().setValue(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(CustomerRequests.this,"Request Submitted",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(CustomerRequests.this, CustomerDashboard.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CustomerRequests.this,"Request Submitting Failed",Toast.LENGTH_LONG).show();
                                }
                            });

                }

            }
        });
    }
}