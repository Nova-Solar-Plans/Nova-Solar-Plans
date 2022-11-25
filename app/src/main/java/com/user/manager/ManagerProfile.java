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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.main.databinding.ActivityManagerDashboardBinding;
import com.user.main.databinding.ActivityManagerProfileBinding;
import com.user.technician.TechnicianLogin;
import com.user.technician.TechnicianProfile;
import com.user.technician.database.TechnicianDatabase;

import java.util.HashMap;

public class ManagerProfile extends ManagerDrawerBase {

    ActivityManagerProfileBinding activityManagerProfileBinding;

    private EditText Name,Email,Address,NIC;
    private Button UpdateBtn;
    private TextView DeleteBtn;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManagerProfileBinding = ActivityManagerProfileBinding.inflate(getLayoutInflater());
        setContentView(activityManagerProfileBinding.getRoot());
        allocateActivityTitle("Profile");

        Name = findViewById(R.id.manager_name);
        Email = findViewById(R.id.manager_email);
        Address = findViewById(R.id.manager_address);
        NIC = findViewById(R.id.manager_nic);
        DeleteBtn = findViewById(R.id.manager_delete_btn);
        UpdateBtn = findViewById(R.id.manager_update_btn);

        getManagerData();

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = Name.getText().toString().trim();
                String address = Address.getText().toString().trim();
                String nic = NIC.getText().toString().trim();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",name);
                hashMap.put("address",address);
                hashMap.put("nic",nic);

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                databaseReference = db.getReference("Manager");
                databaseReference.child(currentFirebaseUser.getUid()).updateChildren(hashMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ManagerProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ManagerProfile.this, "Updating Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ManagerProfile.this);

                builder.setMessage("Do you want to delete account ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(true);


                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    databaseReference = db.getReference("Manager");

                    databaseReference.child(currentFirebaseUser.getUid()).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ManagerProfile.this,"Deleted",Toast.LENGTH_SHORT).show();
                                    Intent intent =  new Intent(ManagerProfile.this,ManagerLogin.class);
                                    startActivity(intent);
                                    finish();
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

    public void getManagerData(){

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Manager").child(currentFirebaseUser.getUid());

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String address = String.valueOf(dataSnapshot.child("address").getValue());
                        String nic = String.valueOf(dataSnapshot.child("nic").getValue());

                        Name.setText(name);
                        Email.setText(email);
                        Address.setText(address);
                        NIC.setText(nic);
                    }
                    else{
                        Toast.makeText(ManagerProfile.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(ManagerProfile.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}