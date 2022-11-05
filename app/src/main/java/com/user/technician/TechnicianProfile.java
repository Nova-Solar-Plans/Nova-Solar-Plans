package com.user.technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.main.databinding.ActivityTechnicianProfileBinding;
import com.user.technician.database.TechnicianDatabase;

public class TechnicianProfile extends DrawerBase {

    ActivityTechnicianProfileBinding activityTechnicianProfileBinding;
    private DatabaseReference technicianRef;
    private EditText Name,Email,Address,NIC;
    private ImageView r1,r2,r3,r4,r5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTechnicianProfileBinding = ActivityTechnicianProfileBinding.inflate(getLayoutInflater());
        setContentView(activityTechnicianProfileBinding.getRoot());
        allocateActivityTitle("Profile");

        Name = findViewById(R.id.register_name);
        Email = findViewById(R.id.register_email);
        Address = findViewById(R.id.register_address);
        NIC = findViewById(R.id.register_nic);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
        r5 = findViewById(R.id.r5);






        TechnicianDatabase technicianDatabase = new TechnicianDatabase();

        technicianRef = FirebaseDatabase.getInstance().getReference("technician");

        technicianRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String address = String.valueOf(dataSnapshot.child("address").getValue());
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String password = String.valueOf(dataSnapshot.child("password").getValue());
                        String nic = String.valueOf(dataSnapshot.child("nic").getValue());
                        String rating = String.valueOf(dataSnapshot.child("rating").getValue());

                        Name.setText(name);
                        Email.setText(email);
                        Address.setText(address);
                        NIC.setText(nic);

                        if(rating.equals("0")){

                            r1.setVisibility(View.GONE);
                            r2.setVisibility(View.GONE);
                            r3.setVisibility(View.GONE);
                            r4.setVisibility(View.GONE);
                            r5.setVisibility(View.GONE);

                        }

                        if(rating.equals("1")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.GONE);
                            r3.setVisibility(View.GONE);
                            r4.setVisibility(View.GONE);
                            r5.setVisibility(View.GONE);
                        }
                        if(rating.equals("2")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.GONE);
                            r4.setVisibility(View.GONE);
                            r5.setVisibility(View.GONE);
                        }
                        if(rating.equals("3")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.GONE);
                            r5.setVisibility(View.GONE);
                        }
                        if(rating.equals("4")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.VISIBLE);
                            r5.setVisibility(View.GONE);
                        }
                        if(rating.equals("5")){
                            r1.setVisibility(View.VISIBLE);
                            r2.setVisibility(View.VISIBLE);
                            r3.setVisibility(View.VISIBLE);
                            r4.setVisibility(View.VISIBLE);
                            r5.setVisibility(View.VISIBLE);
                        }


                    }else{
                        Toast.makeText(TechnicianProfile.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(TechnicianProfile.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
