package com.user.technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Objects;

public class FinishedTaskDetails extends AppCompatActivity {

    private ImageView TaskDetailsBackBtn;
    private TextView TaskType,TaskDescription,CustomerName,CustomerAddress,CustomerOrderNo,Date,Status,Rating
            ,RatingDescription,TechnicianFeedbackView;
    private EditText TechnicianFeedbackInput;
    private Button TechnicianFeedbackBtn;
    private DatabaseReference TaskRef;
    private LinearLayout TechnicianFeedback,TechnicianAddFeedback;

    String key;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_task_details);

        TaskType = findViewById(R.id.task_type);
        TaskDescription = findViewById(R.id.task_description);
        CustomerName = findViewById(R.id.customer_name);
        CustomerAddress = findViewById(R.id.customer_address);
        CustomerOrderNo = findViewById(R.id.customer_order_no);
        Date = findViewById(R.id.date);
        Status = findViewById(R.id.task_status);
        Rating = findViewById(R.id.rating);
        RatingDescription = findViewById(R.id.rating_description);
        TechnicianFeedbackView = findViewById(R.id.technician_feedback_view);
        TechnicianFeedbackInput = findViewById(R.id.technician_feedback_input);
        TechnicianFeedbackBtn = findViewById(R.id.technician_feedback_btn);
        TaskDetailsBackBtn = findViewById(R.id.finished_task_back_btn);
        TechnicianFeedback = findViewById(R.id.technician_feedback_layout);
        TechnicianAddFeedback = findViewById(R.id.technician_add_feedback_layout);

        Bundle extra = getIntent().getExtras();
        key = extra.getString("taskNo");

        getTaskDetails();

        TaskDetailsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishedTaskDetails.this,TechnicianFinishedTasks.class);
                startActivity(intent);
            }
        });

        TechnicianFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String feedback = TechnicianFeedbackInput.getText().toString().trim();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("technicianFeedback",feedback);

                FirebaseDatabase.getInstance().getReference("Tasks").child("Finished")
                        .child(currentFirebaseUser.getUid()).child(key).updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(FinishedTaskDetails.this,"Feedback Added",Toast.LENGTH_SHORT).show();
                                getTaskDetails();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FinishedTaskDetails.this,"Fail to add feedback",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getTaskDetails(){

        TaskRef = FirebaseDatabase.getInstance().getReference("Tasks").child("Finished")
                .child(currentFirebaseUser.getUid()).child(key);

        TaskRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();

                        TaskType.setText(String.valueOf(dataSnapshot.child("type").getValue()));
                        TaskDescription.setText(String.valueOf(dataSnapshot.child("description").getValue()));
                        CustomerName.setText(String.valueOf(dataSnapshot.child("customerName").getValue()));
                        CustomerAddress.setText(String.valueOf(dataSnapshot.child("customerAddress").getValue()));
                        CustomerOrderNo.setText(String.valueOf(dataSnapshot.child("customerOrderNo").getValue()));
                        Date.setText(String.valueOf(dataSnapshot.child("date").getValue()));
                        Status.setText(String.valueOf(dataSnapshot.child("status").getValue()));
                        Rating.setText(String.valueOf(dataSnapshot.child("rating").getValue()));
                        RatingDescription.setText(String.valueOf(dataSnapshot.child("ratingDescription").getValue()));

                        if(!String.valueOf(dataSnapshot.child("technicianFeedback").getValue()).isEmpty()){

                            TechnicianAddFeedback.setVisibility(View.GONE);
                            TechnicianFeedback.setVisibility(View.VISIBLE);
                            TechnicianFeedbackView.setText(String.valueOf(dataSnapshot.child("technicianFeedback").getValue()));
                        }

                    }
                    else{
                        Toast.makeText(FinishedTaskDetails.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(FinishedTaskDetails.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
