package com.user.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.technician.FinishedTaskDetails;

import java.util.HashMap;

public class ProductView extends AppCompatActivity {

    private ImageView ProductImage,BackBtn;
    private EditText Title,Description,Price,Highlight;
    private Button Update;
    private TextView Delete;

    private DatabaseReference databaseReference;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        ProductImage = findViewById(R.id.product_image);
        Title = findViewById(R.id.product_title);
        Description = findViewById(R.id.product_description);
        Price = findViewById(R.id.product_price);
        Highlight = findViewById(R.id.product_highlight);
        Update = findViewById(R.id.product_update_btn);
        Delete = findViewById(R.id.product_delete_btn);
        BackBtn = findViewById(R.id.manage_product_back_btn);

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("product");

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductView.this,ManageProduct.class);
                startActivity(intent);
            }
        });



        getProductDetails();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("title",Title.getText().toString().trim());
                hashMap.put("description",Description.getText().toString().trim());
                hashMap.put("price",Price.getText().toString().trim());
                hashMap.put("highlight",Highlight.getText().toString().trim());

                FirebaseDatabase.getInstance().getReference("Products").child(key).updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProductView.this,"Product Updated",Toast.LENGTH_SHORT).show();
                                getProductDetails();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductView.this,"Fail to update product",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ProductView.this);

                builder.setMessage("Do you want to delete product ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    FirebaseDatabase.getInstance().getReference("Products").child(key).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()) {
                                        Toast.makeText(ProductView.this, "Deleted", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProductView.this, ManageProduct.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(ProductView.this, "Fail to delete", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();


            }
        });
    }

    public void getProductDetails(){

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Products").child(key);

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
                        Toast.makeText(ProductView.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                        Toast.makeText(ProductView.this,"Error fetch data results",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}