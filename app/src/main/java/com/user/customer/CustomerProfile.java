package com.user.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.user.customer.database.CustomerDatabase;
import com.user.main.R;
import com.user.main.databinding.ActivityCustomerDashboardBinding;
import com.user.main.databinding.ActivityCustomerProfileBinding;
import com.user.technician.TechnicianLogin;
import com.user.technician.TechnicianProfile;
import com.user.technician.database.TechnicianDatabase;
import com.user.visitor.ViewTechnician;

import java.util.HashMap;

public class CustomerProfile extends CustomerDrawerBase {

    ActivityCustomerProfileBinding activityCustomerProfileBinding;

    private EditText Name,Email,Address;
    private Button Delete,Update;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCustomerProfileBinding = ActivityCustomerProfileBinding.inflate(getLayoutInflater());
        setContentView(activityCustomerProfileBinding.getRoot());
        allocateActivityTitle("Profile");


        Name = findViewById(R.id.customer_name);
        Email = findViewById(R.id.customer_email);
        Address = findViewById(R.id.customer_address);
        Delete = findViewById(R.id.customer_delete_btn);
        Update = findViewById(R.id.customer_update_btn);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        CustomerDatabase customerDatabase = new CustomerDatabase();

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerProfile.this);

                builder.setMessage("Do you want to delete account ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    customerDatabase.customerDelete(currentFirebaseUser.getUid())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CustomerProfile.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CustomerProfile.this, CustomerLogin.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(CustomerProfile.this, "Deleting Failed!", Toast.LENGTH_SHORT).show();
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

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = Name.getText().toString().trim();
                String address = Address.getText().toString().trim();
                boolean success = true;

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",name);
                hashMap.put("address",address);

                if(name.isEmpty()){
                    Name.setError("Please enter name ! ");
                    Name.requestFocus();
                    success=false;
                }
                if(address.isEmpty()){
                    Address.setError("Please enter address ! ");
                    Address.requestFocus();
                    success=false;
                }

                if(success){

                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;


                    customerDatabase.customerUpdate(currentFirebaseUser.getUid(),hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CustomerProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CustomerProfile.this, "Updating Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(CustomerProfile.this, "Updating Failed!(Invalid inputs)", Toast.LENGTH_SHORT).show();
                }
            }
        });



        databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(currentFirebaseUser.getUid());

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String address = String.valueOf(dataSnapshot.child("address").getValue());

                        Name.setText(name);
                        Email.setText(email);
                        Address.setText(address);
                    }
                    else{
                        Toast.makeText(CustomerProfile.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(CustomerProfile.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}