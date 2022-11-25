package com.user.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.user.technician.FinishedTaskDetails;

public class ViewCustomerTask extends AppCompatActivity {

    private ImageView TaskDetailsBackBtn;
    private TextView TaskType,TaskDescription,CustomerName,CustomerAddress,CustomerOrderNo,Date,Status;
    private EditText FeedbackInput;
    private Button AddFeedbackBtn;
    private DatabaseReference databaseReference;
    private Spinner Spinner;

    String key;

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_task);

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("taskNo");

        TaskType = findViewById(R.id.task_type);
        TaskDescription = findViewById(R.id.task_description);
        CustomerName = findViewById(R.id.customer_name);
        CustomerAddress = findViewById(R.id.customer_address);
        CustomerOrderNo = findViewById(R.id.customer_order_no);
        Date = findViewById(R.id.date);
        Status = findViewById(R.id.task_status);

        FeedbackInput = findViewById(R.id.rating_description);
        AddFeedbackBtn = findViewById(R.id.add_feedback_btn);
        Spinner = findViewById(R.id.spinner);

        AddFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getTaskDetails();
    }

    public void getTaskDetails(){

        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks").child("Finished")
                .child(currentFirebaseUser.getUid()).child(key);

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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


                    }
                    else{
                        Toast.makeText(ViewCustomerTask.this,"Error fetch data result",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ViewCustomerTask.this,"Error fetch data unsuccessful",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}