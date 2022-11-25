package com.user.visitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;

import java.util.HashMap;

public class RequestQuote extends AppCompatActivity {

    private EditText Name,Email,Contact,Address,Height,Width,Material,Description;
    private Button CreateRequest;
    private DatabaseReference databaseReference;
    private ImageView BackBtn;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_quote);

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("product");

        Name = findViewById(R.id.customer_name);
        Email = findViewById(R.id.customer_email);
        Contact = findViewById(R.id.customer_contact);
        Address = findViewById(R.id.customer_address);
        Height = findViewById(R.id.customer_roof_height);
        Width = findViewById(R.id.customer_roof_width);
        Material = findViewById(R.id.roof_material);
        Description = findViewById(R.id.description);
        CreateRequest = findViewById(R.id.create_req_btn);
        BackBtn = findViewById(R.id.product_view_back_btn);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestQuote.this,VisitorViewProduct.class);
                intent.putExtra("product",key);
                startActivity(intent);
            }
        });

        CreateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",Name.getText().toString());
                hashMap.put("email",Email.getText().toString());
                hashMap.put("contact",Contact.getText().toString());
                hashMap.put("address",Address.getText().toString());
                hashMap.put("roofHeight",Height.getText().toString());
                hashMap.put("roofWidth",Width.getText().toString());
                hashMap.put("material",Material.getText().toString());
                hashMap.put("description",Description.getText().toString());

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                databaseReference = db.getReference("Tasks").child("New Planting");

                databaseReference.push().setValue(hashMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RequestQuote.this,"Request Submitted",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RequestQuote.this,VisitorProduct.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RequestQuote.this,"Request Submitting Failed",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}