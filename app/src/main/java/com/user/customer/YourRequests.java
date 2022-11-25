package com.user.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.user.customer.adapter.CustomerRequestAdapter;
import com.user.customer.modal.CustomerTaskModal;
import com.user.main.R;
import com.user.main.databinding.ActivityCustomerProfileBinding;
import com.user.main.databinding.ActivityYourRequestsBinding;
import com.user.visitor.adapters.ViewTechnicianAdapter;
import com.user.visitor.modal.TechnicianModal;

public class YourRequests extends CustomerDrawerBase implements AdapterView.OnItemSelectedListener{

    ActivityYourRequestsBinding activityYourRequestsBinding;

    private ImageView CreateReqBtn;
    private RecyclerView recyclerView;

    CustomerRequestAdapter customerRequestAdapter;

    String[] filter = { "Finished", "Remain"};

    String filterItem = "Remain";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityYourRequestsBinding = ActivityYourRequestsBinding.inflate(getLayoutInflater());
        setContentView(activityYourRequestsBinding.getRoot());
        allocateActivityTitle("Your Requests");

        CreateReqBtn = findViewById(R.id.create_req_btn);
        recyclerView = findViewById(R.id.request_list);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,filter);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        CreateReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YourRequests.this,CustomerViewTechnician.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CustomerTaskModal> options;
        if(filterItem.equals("Remain")){

            options = new FirebaseRecyclerOptions.Builder<CustomerTaskModal>()
                    .setQuery(FirebaseDatabase.getInstance().getReference("Tasks").child("Remain")
                            .child(currentFirebaseUser.getUid()), CustomerTaskModal.class)
                    .build();
            // Connecting object of required Adapter class to
            // the Adapter class itself
            // Connecting Adapter class with the Recycler view*/
        }
        else{
            options = new FirebaseRecyclerOptions.Builder<CustomerTaskModal>()
                    .setQuery(FirebaseDatabase.getInstance().getReference("Tasks").child("Finished")
                            .child(currentFirebaseUser.getUid()), CustomerTaskModal.class)
                    .build();
            // Connecting object of required Adapter class to
            // the Adapter class itself
            // Connecting Adapter class with the Recycler view*/
        }
        customerRequestAdapter = new CustomerRequestAdapter(options);
        recyclerView.setAdapter(customerRequestAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        customerRequestAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        customerRequestAdapter.stopListening();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(getApplicationContext(),filter[i] , Toast.LENGTH_LONG).show();
        filterItem = filter[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}