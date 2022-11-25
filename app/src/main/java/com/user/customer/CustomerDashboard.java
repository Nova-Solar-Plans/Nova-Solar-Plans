package com.user.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.customer.adapter.CustomerProductAdapter;
import com.user.main.R;
import com.user.main.databinding.ActivityCustomerDashboardBinding;
import com.user.main.databinding.ActivityManagerDashboardBinding;
import com.user.manager.ProductAdapter;
import com.user.manager.modal.ProductModal;
import com.user.visitor.adapters.ViewProductAdapter;

public class CustomerDashboard extends CustomerDrawerBase {

    ActivityCustomerDashboardBinding activityCustomerDashboardBinding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;

    CustomerProductAdapter customerProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCustomerDashboardBinding = ActivityCustomerDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityCustomerDashboardBinding.getRoot());
        allocateActivityTitle("Product Feed");

        recyclerView = findViewById(R.id.product_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ProductModal> options =
                new FirebaseRecyclerOptions.Builder<ProductModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Products") ,ProductModal.class)
                        .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        customerProductAdapter = new CustomerProductAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(customerProductAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        customerProductAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        customerProductAdapter.stopListening();
    }
}