package com.user.technician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.user.main.R;
import com.user.main.databinding.ActivityTechnicianRemainTasksBinding;

public class TechnicianRemainTasks extends DrawerBase {

    ActivityTechnicianRemainTasksBinding activityTechnicianRemainTasksBinding;

    private CardView Task1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTechnicianRemainTasksBinding = ActivityTechnicianRemainTasksBinding.inflate(getLayoutInflater());
        setContentView(activityTechnicianRemainTasksBinding.getRoot());
        allocateActivityTitle("Remaining Tasks");

        Task1 = findViewById(R.id.task1);

        Task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TechnicianRemainTasks.this,RemainingTaskDetails.class);
                startActivity(intent);
            }
        });
    }
}