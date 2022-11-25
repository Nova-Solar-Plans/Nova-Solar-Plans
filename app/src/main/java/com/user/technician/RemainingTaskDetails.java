package com.user.technician;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.main.R;

import java.util.HashMap;

public class RemainingTaskDetails extends AppCompatActivity {

    ImageView RemainTaskBackBtn;
    private TextView TaskType,TaskDescription,CustomerName,CustomerAddress,CustomerOrderNo,Date,Status,Contact,Email;
    private Button MarkAsFinishedBtn;
    private DatabaseReference taskRef;

    private DatabaseReference fromPath,toPath;

    String key;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaining_task_details);


        TaskType = findViewById(R.id.task_type);
        TaskDescription = findViewById(R.id.task_description);
        CustomerName = findViewById(R.id.customer_name);
        CustomerAddress = findViewById(R.id.customer_address);
        CustomerOrderNo = findViewById(R.id.customer_order_no);
        Date = findViewById(R.id.date);
        Status = findViewById(R.id.task_status);
        MarkAsFinishedBtn = findViewById(R.id.mark_as_finished_btn);
        RemainTaskBackBtn = findViewById(R.id.remain_task_back_btn);
        Contact = findViewById(R.id.customer_contact);
        Email = findViewById(R.id.customer_email);

        Bundle extra = getIntent().getExtras();
        key = extra.getString("taskNo");

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status","Finished");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        taskRef = FirebaseDatabase.getInstance().getReference("Tasks").child("Remain").child(currentFirebaseUser.getUid()).child(key);

        taskRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String customerAddress = String.valueOf(dataSnapshot.child("customerAddress").getValue());
                        String customerContact = String.valueOf(dataSnapshot.child("customerContact").getValue());
                        String customerEmail = String.valueOf(dataSnapshot.child("customerEmail").getValue());
                        String customerName = String.valueOf(dataSnapshot.child("customerName").getValue());
                        String customerOrderNo = String.valueOf(dataSnapshot.child("customerOrderNo").getValue());
                        String date = String.valueOf(dataSnapshot.child("date").getValue());
                        String description = String.valueOf(dataSnapshot.child("description").getValue());
                        String type = String.valueOf(dataSnapshot.child("type").getValue());


                        TaskType.setText(type);
                        TaskDescription.setText(description);
                        CustomerAddress.setText(customerAddress);
                        CustomerName.setText(customerName);
                        CustomerOrderNo.setText(customerOrderNo);
                        Date.setText(date);
                        Status.setText("Not Finished");
                        Contact.setText(customerContact);
                        Email.setText(customerEmail);




                    }else{
                        Toast.makeText(RemainingTaskDetails.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RemainingTaskDetails.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });

        MarkAsFinishedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Tasks").child("Remain")
                        .child(currentFirebaseUser.getUid()).child(key).updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RemainingTaskDetails.this,"Success Update",Toast.LENGTH_SHORT).show();

                                fromPath = FirebaseDatabase.getInstance().getReference("Tasks").child("Remain")
                                        .child(currentFirebaseUser.getUid()).child(key);

                                toPath = FirebaseDatabase.getInstance().getReference("Tasks").child("Finished")
                                        .child(currentFirebaseUser.getUid()).child(key);

                                taskMoveToFinish(fromPath,toPath);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RemainingTaskDetails.this,"Fail Update",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        RemainTaskBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemainingTaskDetails.this,TechnicianRemainTasks.class);
                startActivity(intent);
            }
        });
    }

    public void taskMoveToFinish(DatabaseReference fromPath,final DatabaseReference toPath){

        ValueEventListener valueEventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                toPath.setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Toast.makeText(RemainingTaskDetails.this,"Moving Success",Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Success!");
                            fromPath.removeValue();
                        } else {
                            Toast.makeText(RemainingTaskDetails.this,"Moving Fail",Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Copy failed!");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", error.getMessage());
            }
        };
        fromPath.addListenerForSingleValueEvent(valueEventListener);

    }
}