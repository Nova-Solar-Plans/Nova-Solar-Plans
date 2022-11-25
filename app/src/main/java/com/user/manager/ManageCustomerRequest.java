package com.user.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.user.main.R;
import com.user.main.databinding.ActivityManageCustomerRequestBinding;
import com.user.main.databinding.ActivityManagerDashboardBinding;

public class ManageCustomerRequest extends ManagerDrawerBase {

    ActivityManageCustomerRequestBinding activityManageCustomerRequestBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManageCustomerRequestBinding = ActivityManageCustomerRequestBinding.inflate(getLayoutInflater());
        setContentView(activityManageCustomerRequestBinding.getRoot());
        allocateActivityTitle("Customer Requests");
    }
}