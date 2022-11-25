package com.user.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.visitor.VisitorViewProduct;

public class CustomerViewProduct extends AppCompatActivity {

    private TextView Title,Description,Price,Highlight;
    private Button RequestQuote;

    private DatabaseReference databaseReference;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_product);

        Title = findViewById(R.id.product_title);
        Description = findViewById(R.id.product_description);
        Price = findViewById(R.id.product_price);
        Highlight = findViewById(R.id.product_highlight);
        RequestQuote = findViewById(R.id.request_quote);

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("product");

        Toast.makeText(CustomerViewProduct.this,key,Toast.LENGTH_LONG).show();

        getProductDetails();

        RequestQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerViewProduct.this,CustomerRequests.class);
                intent.putExtra("productId",key);
                startActivity(intent);
            }
        });


    }

    public void getProductDetails(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(key);

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();

                        Title.setText(String.valueOf(dataSnapshot.child("title").getValue()));
                        Description.setText(String.valueOf(dataSnapshot.child("description").getValue()));
                        Price.setText(String.valueOf(dataSnapshot.child("price").getValue()));
                        Highlight.setText(String.valueOf(dataSnapshot.child("highlight").getValue()));

                    }
                    else{
                        Toast.makeText(CustomerViewProduct.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(CustomerViewProduct.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}