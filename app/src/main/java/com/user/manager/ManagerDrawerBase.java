package com.user.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.user.main.R;
import com.user.technician.DashBoard;
import com.user.technician.TechnicianFinishedTasks;
import com.user.technician.TechnicianProfile;
import com.user.technician.TechnicianRemainTasks;

public class ManagerDrawerBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

        DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_manager_drawer_base,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.manager_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_drawer_open,R.string.menu_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){

            case R.id.manager_menu_item_dashboard:
                startActivity(new Intent(this, ManagerDashboard.class));
                overridePendingTransition(0,0);
                break;

            case R.id.manager_menu_item_profile:
                startActivity(new Intent(this, ManagerProfile.class));
                overridePendingTransition(0,0);
                break;
            case R.id.manager_menu_item__product:
                startActivity(new Intent(this, ManageProduct.class));
                overridePendingTransition(0,0);
                break;
            case R.id.manager_menu_item_customer_request:
                startActivity(new Intent(this, ManageCustomerRequest.class));
                overridePendingTransition(0,0);
                break;

        }
        return false;
    }
    protected void allocateActivityTitle(String titleString){
        if(getSupportActionBar() !=null){
            getSupportActionBar().setTitle(titleString);
        }
    }
}