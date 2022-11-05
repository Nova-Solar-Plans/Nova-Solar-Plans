package com.user.technician;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.user.main.R;
import com.user.main.databinding.ActivityDashBoardBinding;
import com.user.main.databinding.ActivityTechnicianFinishedTasksBinding;

public class DashBoard extends DrawerBase {

    ActivityDashBoardBinding activityDashBoardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashBoardBinding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(activityDashBoardBinding.getRoot());
        allocateActivityTitle("Dashboard");
    }
}