package com.user.visitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.visitor.modal.TechnicianModal;
import com.user.visitor.adapters.ViewTechnicianAdapter;

public class VisitorTechnician extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private ImageView BackBtn;

    ViewTechnicianAdapter viewTechnicianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_technician);


        recyclerView = findViewById(R.id.technician_list);
        BackBtn = findViewById(R.id.dashboard);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitorTechnician.this,VisitorDashboard.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TechnicianModal> options =
                new FirebaseRecyclerOptions.Builder<TechnicianModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Technician") ,TechnicianModal.class)
                        .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        viewTechnicianAdapter = new ViewTechnicianAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(viewTechnicianAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewTechnicianAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewTechnicianAdapter.stopListening();
    }
}