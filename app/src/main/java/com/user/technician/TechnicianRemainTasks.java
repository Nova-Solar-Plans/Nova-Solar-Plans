package com.user.technician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.main.databinding.ActivityTechnicianRemainTasksBinding;
import com.user.technician.adapters.FinishedTaskAdapter;
import com.user.technician.adapters.RemainTaskAdapter;
import com.user.technician.modal.TaskModal;

import java.util.ArrayList;

public class TechnicianRemainTasks extends DrawerBase {

    ActivityTechnicianRemainTasksBinding activityTechnicianRemainTasksBinding;

    private TextView RemainTaskCount;
    private ImageView FavStar;
    private RecyclerView RemainTaskRecyclerView;

    RemainTaskAdapter remainTaskAdapter;
    public static ArrayList<TaskModal> taskModalArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTechnicianRemainTasksBinding = ActivityTechnicianRemainTasksBinding.inflate(getLayoutInflater());
        setContentView(activityTechnicianRemainTasksBinding.getRoot());
        allocateActivityTitle("Remaining Tasks");

        RemainTaskCount = findViewById(R.id.remain_task_count);
        FavStar = findViewById(R.id.remain_task_fav_star);
        RemainTaskRecyclerView = findViewById(R.id.remain_task_list);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        RemainTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TaskModal> options =
                new FirebaseRecyclerOptions.Builder<TaskModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Tasks")
                                .child("Remain").child(currentFirebaseUser.getUid()), TaskModal.class)
                        .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        remainTaskAdapter = new RemainTaskAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        RemainTaskRecyclerView.setAdapter(remainTaskAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        remainTaskAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        remainTaskAdapter.stopListening();
    }
}