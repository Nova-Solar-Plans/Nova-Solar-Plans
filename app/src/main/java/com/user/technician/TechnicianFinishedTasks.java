package com.user.technician;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.main.R;
import com.user.main.databinding.ActivityTechnicianFinishedTasksBinding;
import com.user.main.databinding.ActivityTechnicianProfileBinding;
import com.user.technician.adapters.FinishedTaskAdapter;
import com.user.technician.modal.TaskModal;

import java.util.ArrayList;

public class TechnicianFinishedTasks extends DrawerBase{

    ActivityTechnicianFinishedTasksBinding activityTechnicianFinishedTasksBinding;

    private ListView ListView;
    private TextView TaskCount;
    private ImageView FavStar;
    private RecyclerView recyclerView;
    FinishedTaskAdapter finishedTaskAdapter;
    public static ArrayList<TaskModal> taskModalArrayList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTechnicianFinishedTasksBinding = ActivityTechnicianFinishedTasksBinding.inflate(getLayoutInflater());
        setContentView(activityTechnicianFinishedTasksBinding.getRoot());
        allocateActivityTitle("Finished Tasks");

        recyclerView = findViewById(R.id.finished_task_list);
        TaskCount = findViewById(R.id.task_count);
        FavStar = findViewById(R.id.fav_star);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Tasks").child("Reapir").child("Finished").child("IpFskCxOwvSHC3BUus9SJOL3f3M2");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TaskModal> options = new FirebaseRecyclerOptions.Builder<TaskModal>()
                .setQuery(databaseReference, TaskModal.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        finishedTaskAdapter = new FinishedTaskAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(finishedTaskAdapter);


    }

    public void retrieveFinishedTasks(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){

                    TaskModal taskModal=ds.getValue(TaskModal.class);


                    if(taskModal !=null){
                        TaskModal taskModal1 = new TaskModal(taskModal.getType(),taskModal.getDescription(),taskModal.getCustomerName()
                                ,taskModal.getCustomerAddress(),taskModal.getCustomerOrderNo(),taskModal.getDate(),taskModal.getStatus()
                                ,taskModal.getRating(),taskModal.getRatingDescription(),taskModal.getTechnicianFeedback());
                        taskModalArrayList.add(taskModal1);
                        finishedTaskAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}