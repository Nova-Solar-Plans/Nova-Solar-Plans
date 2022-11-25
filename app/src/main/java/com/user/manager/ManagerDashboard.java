
package com.user.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.user.main.R;
import com.user.main.databinding.ActivityDashBoardBinding;
import com.user.main.databinding.ActivityManagerDashboardBinding;

public class ManagerDashboard extends ManagerDrawerBase {

    ActivityManagerDashboardBinding activityManagerDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManagerDashboardBinding = ActivityManagerDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityManagerDashboardBinding.getRoot());
        allocateActivityTitle("Dashboard");
    }
}