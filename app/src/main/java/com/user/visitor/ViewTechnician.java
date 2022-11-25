package com.user.visitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.technician.FinishedTaskDetails;
import com.user.visitor.modal.RequestModal;

public class ViewTechnician extends AppCompatActivity {

    private EditText CustomerName,CustomerEmail,CustomerNumber,CustomerAddress,OrderNo,Description,RequestType;
    private Button CreateReqBtn;
    private ImageView TechnicianPhoto,Rating1,Rating2,Rating3,Rating4,Rating5,ViewTechnicianBackBtn;
    private TextView TechnicianName;
    private DatabaseReference TaskRef;
    private DatabaseReference databaseReference;

    String key,rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_technician);

        CustomerName = findViewById(R.id.customer_name);
        CustomerEmail = findViewById(R.id.customer_email);
        CustomerNumber = findViewById(R.id.customer_contact);
        CustomerAddress = findViewById(R.id.customer_address);
        OrderNo = findViewById(R.id.customer_order_no);
        Description = findViewById(R.id.customer_order_description);
        RequestType = findViewById(R.id.request_type);
        ViewTechnicianBackBtn = findViewById(R.id.technician_list_back_btn);

        TechnicianName = findViewById(R.id.technician_name);

        CreateReqBtn = findViewById(R.id.create_hire_req_btn);

        TechnicianPhoto = findViewById(R.id.technician_imgView);
        Rating1 = findViewById(R.id.rating_1);
        Rating2 = findViewById(R.id.rating_2);
        Rating3 = findViewById(R.id.rating_3);
        Rating4 = findViewById(R.id.rating_4);
        Rating5 = findViewById(R.id.rating_5);

        ViewTechnicianBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTechnician.this,VisitorTechnician.class);
                startActivity(intent);
            }
        });


        Bundle extra = getIntent().getExtras();
        key = extra.getString("technician");

        getTechnicianDetails();

        CreateReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestModal requestModal;

                requestModal = new RequestModal(RequestType.getText().toString(),Description.getText().toString(),CustomerName.getText().toString(),
                        CustomerAddress.getText().toString(),OrderNo.getText().toString(),CustomerNumber.getText().toString(),
                        CustomerEmail.getText().toString());
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                databaseReference = db.getReference("Tasks").child("Remain");

                databaseReference.child(key).push().setValue(requestModal)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ViewTechnician.this,"Request Submitted",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ViewTechnician.this,VisitorDashboard.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ViewTechnician.this,"Request Submitting Failed",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


    }

    public void getTechnicianDetails(){

        TaskRef = FirebaseDatabase.getInstance().getReference("Technician").child(key);

        TaskRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        TechnicianName.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                        rating = String.valueOf(dataSnapshot.child("rating").getValue());

                        if(rating.equals("0")){
                            Rating1.setVisibility(View.GONE);
                            Rating2.setVisibility(View.GONE);
                            Rating3.setVisibility(View.GONE);
                            Rating4.setVisibility(View.GONE);
                            Rating5.setVisibility(View.GONE);
                        }
                        if(rating.equals("1")){
                            Rating1.setVisibility(View.VISIBLE);
                            Rating2.setVisibility(View.GONE);
                            Rating3.setVisibility(View.GONE);
                            Rating4.setVisibility(View.GONE);
                            Rating5.setVisibility(View.GONE);
                        }
                        if(rating.equals("2")){
                            Rating1.setVisibility(View.VISIBLE);
                            Rating2.setVisibility(View.VISIBLE);
                            Rating3.setVisibility(View.GONE);
                            Rating4.setVisibility(View.GONE);
                            Rating5.setVisibility(View.GONE);
                        }
                        if(rating.equals("3")){
                            Rating1.setVisibility(View.VISIBLE);
                            Rating2.setVisibility(View.VISIBLE);
                            Rating3.setVisibility(View.VISIBLE);
                            Rating4.setVisibility(View.GONE);
                            Rating5.setVisibility(View.GONE);
                        }
                        if(rating.equals("4")){
                            Rating1.setVisibility(View.VISIBLE);
                            Rating2.setVisibility(View.VISIBLE);
                            Rating3.setVisibility(View.VISIBLE);
                            Rating4.setVisibility(View.VISIBLE);
                            Rating5.setVisibility(View.GONE);
                        }
                        if(rating.equals("5")){
                            Rating1.setVisibility(View.VISIBLE);
                            Rating2.setVisibility(View.VISIBLE);
                            Rating3.setVisibility(View.VISIBLE);
                            Rating4.setVisibility(View.VISIBLE);
                            Rating5.setVisibility(View.VISIBLE);
                        }


                    }
                    else{
                        Toast.makeText(ViewTechnician.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ViewTechnician.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}