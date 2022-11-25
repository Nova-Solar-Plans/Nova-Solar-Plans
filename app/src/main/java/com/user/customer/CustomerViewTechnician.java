package com.user.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.user.customer.adapter.CustomerTechnicianAdapter;
import com.user.customer.modal.CustomerTechnicianModal;
import com.user.main.R;
import com.user.main.databinding.ActivityCustomerViewTechnicianBinding;
import com.user.visitor.adapters.ViewTechnicianAdapter;
import com.user.visitor.modal.TechnicianModal;

public class CustomerViewTechnician extends AppCompatActivity {

    private ImageView BackBtn;
    private RecyclerView recyclerView;

    CustomerTechnicianAdapter customerTechnicianAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_technician);

        BackBtn = findViewById(R.id.req_list);
        recyclerView = findViewById(R.id.technician_list);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerViewTechnician.this,YourRequests.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CustomerTechnicianModal> options =
                new FirebaseRecyclerOptions.Builder<CustomerTechnicianModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Technician") , CustomerTechnicianModal.class)
                        .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        customerTechnicianAdapter = new CustomerTechnicianAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(customerTechnicianAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        customerTechnicianAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        customerTechnicianAdapter.stopListening();
    }
}