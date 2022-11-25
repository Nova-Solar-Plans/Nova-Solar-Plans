package com.user.customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.user.customer.CustomerViewProduct;
import com.user.main.R;
import com.user.manager.modal.ProductModal;
import com.user.visitor.RequestQuote;
import com.user.visitor.adapters.ViewProductAdapter;

public class CustomerProductAdapter extends FirebaseRecyclerAdapter<ProductModal, CustomerProductAdapter.productViewHolder> {


    String key;
    public CustomerProductAdapter(@NonNull FirebaseRecyclerOptions<ProductModal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CustomerProductAdapter.productViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull ProductModal model) {

        holder.Title.setText(model.getTitle());
        holder.Price.setText(model.getPrice());

        holder.ProductCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                key =  getRef(position).getKey();
                Context context = view.getContext();
                Intent intent = new Intent(context, CustomerViewProduct.class);
                intent.putExtra("product",key);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);

        return new CustomerProductAdapter.productViewHolder(view);
    }

    class productViewHolder extends RecyclerView.ViewHolder {

        TextView Title,Price;
        ImageView Image;
        CardView ProductCard;

        public productViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.product_title);
            Price = itemView.findViewById(R.id.product_price);
            Image = itemView.findViewById(R.id.product_image);

            ProductCard = itemView.findViewById(R.id.product_card);
        }
    }

}
