package com.user.technician;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.user.main.R;

public class RemainingTaskDetails extends AppCompatActivity {

    ImageView RemainTaskBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaining_task_details);

        RemainTaskBackBtn = findViewById(R.id.remain_task_back_btn);

        RemainTaskBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemainingTaskDetails.this,TechnicianRemainTasks.class);
                startActivity(intent);
            }
        });
    }
}