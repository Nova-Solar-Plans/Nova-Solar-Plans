package com.user.manager;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.manager.modal.ProductModal;

public class AddProduct extends AppCompatActivity {

    private ImageView AddImage,BackBtn;
    private EditText Title,Description,Price,Highlight;
    private Button Publish;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        AddImage = findViewById(R.id.product_image);
        Title = findViewById(R.id.product_title);
        Description = findViewById(R.id.product_description);
        Price = findViewById(R.id.product_price);
        Highlight = findViewById(R.id.product_highlight);
        Publish = findViewById(R.id.product_publish_btn);
        BackBtn = findViewById(R.id.manage_product_back_btn2);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProduct.this,ManageProduct.class);
                startActivity(intent);
            }
        });


        Publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = Title.getText().toString().trim();
                String description = Description.getText().toString().trim();
                String price = Price.getText().toString().trim();
                String highlight = Highlight.getText().toString().trim();

                ProductModal productModal = new ProductModal(title,description,price,highlight);


                FirebaseDatabase db = FirebaseDatabase.getInstance();
                databaseReference = db.getReference("Products");

                databaseReference.push().setValue(productModal)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(AddProduct.this,"Published",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddProduct.this,ManageProduct.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(AddProduct.this,"Fail to publish",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
}