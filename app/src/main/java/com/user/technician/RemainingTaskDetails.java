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
    private TextView TaskType,TaskDescription,CustomerName,CustomerAddress,CustomerOrderNo,Date,Status;
    private Button MarkAsFinishedBtn;

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

        Bundle extra = getIntent().getExtras();
        key = extra.getString("taskNo");

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status","Finished");

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