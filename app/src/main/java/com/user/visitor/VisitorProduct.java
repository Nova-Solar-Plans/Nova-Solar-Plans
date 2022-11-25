package com.user.visitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.manager.modal.ProductModal;
import com.user.visitor.adapters.ViewProductAdapter;
import com.user.visitor.adapters.ViewTechnicianAdapter;
import com.user.visitor.modal.TechnicianModal;

public class VisitorProduct extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private ImageView BackBtn;

    ViewProductAdapter viewProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_product);

        recyclerView = findViewById(R.id.product_list);
        BackBtn = findViewById(R.id.dashboard);

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitorProduct.this,VisitorDashboard.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ProductModal> options =
                new FirebaseRecyclerOptions.Builder<ProductModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Products") ,ProductModal.class)
                        .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        viewProductAdapter = new ViewProductAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(viewProductAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        viewProductAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewProductAdapter.stopListening();
    }
}