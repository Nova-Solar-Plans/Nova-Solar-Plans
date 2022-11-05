package com.user.technician;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.user.main.R;

public class FinishedTaskDetails extends AppCompatActivity {

    private ImageView TaskDetailsBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_task_details);


        TaskDetailsBackBtn = findViewById(R.id.finished_task_back_btn);

        TaskDetailsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishedTaskDetails.this,TechnicianFinishedTasks.class);
                startActivity(intent);
            }
        });
    }
}