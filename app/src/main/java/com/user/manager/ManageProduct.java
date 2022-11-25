package com.user.manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.user.main.R;
import com.user.main.databinding.ActivityManageProductBinding;
import com.user.main.databinding.ActivityManagerDashboardBinding;
import com.user.manager.modal.ProductModal;
import com.user.visitor.adapters.ViewTechnicianAdapter;
import com.user.visitor.modal.TechnicianModal;

public class ManageProduct extends ManagerDrawerBase {

    ActivityManageProductBinding activityManageProductBinding;

    private ImageView AddProductBtn;
    private RecyclerView recyclerView;

    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManageProductBinding = ActivityManageProductBinding.inflate(getLayoutInflater());
        setContentView(activityManageProductBinding.getRoot());
        allocateActivityTitle("Products");

        AddProductBtn = findViewById(R.id.add_product_btn);
        recyclerView = findViewById(R.id.product_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ProductModal> options =
                new FirebaseRecyclerOptions.Builder<ProductModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Products") , ProductModal.class)
                        .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        productAdapter = new ProductAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(productAdapter);


        AddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageProduct.this,AddProduct.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        productAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        productAdapter.stopListening();
    }
}