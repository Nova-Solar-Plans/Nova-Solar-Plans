package com.user.visitor.adapters;

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
import com.user.main.R;
import com.user.technician.FinishedTaskDetails;
import com.user.technician.adapters.FinishedTaskAdapter;
import com.user.technician.modal.TaskModal;
import com.user.visitor.modal.TechnicianModal;
import com.user.visitor.ViewTechnician;

public class ViewTechnicianAdapter extends FirebaseRecyclerAdapter<TechnicianModal,ViewTechnicianAdapter.technicianViewHolder> {


    String key;

    public ViewTechnicianAdapter(@NonNull FirebaseRecyclerOptions<TechnicianModal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull technicianViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull TechnicianModal model) {

        holder.technicianName.setText(model.getName());
        String rating = model.getRating();

        if(rating.equals("0")){
            holder.Rating1.setVisibility(View.GONE);
            holder.Rating2.setVisibility(View.GONE);
            holder.Rating3.setVisibility(View.GONE);
            holder.Rating4.setVisibility(View.GONE);
            holder.Rating5.setVisibility(View.GONE);
        }
        if(rating.equals("1")){
            holder.Rating1.setVisibility(View.VISIBLE);
            holder.Rating2.setVisibility(View.GONE);
            holder.Rating3.setVisibility(View.GONE);
            holder.Rating4.setVisibility(View.GONE);
            holder.Rating5.setVisibility(View.GONE);
        }
        if(rating.equals("2")){
            holder.Rating1.setVisibility(View.VISIBLE);
            holder.Rating2.setVisibility(View.VISIBLE);
            holder.Rating3.setVisibility(View.GONE);
            holder.Rating4.setVisibility(View.GONE);
            holder.Rating5.setVisibility(View.GONE);
        }
        if(rating.equals("3")){
            holder.Rating1.setVisibility(View.VISIBLE);
            holder.Rating2.setVisibility(View.VISIBLE);
            holder.Rating3.setVisibility(View.VISIBLE);
            holder.Rating4.setVisibility(View.GONE);
            holder.Rating5.setVisibility(View.GONE);
        }
        if(rating.equals("4")){
            holder.Rating1.setVisibility(View.VISIBLE);
            holder.Rating2.setVisibility(View.VISIBLE);
            holder.Rating3.setVisibility(View.VISIBLE);
            holder.Rating4.setVisibility(View.VISIBLE);
            holder.Rating5.setVisibility(View.GONE);
        }
        if(rating.equals("5")){
            holder.Rating1.setVisibility(View.VISIBLE);
            holder.Rating2.setVisibility(View.VISIBLE);
            holder.Rating3.setVisibility(View.VISIBLE);
            holder.Rating4.setVisibility(View.VISIBLE);
            holder.Rating5.setVisibility(View.VISIBLE);
        }

        holder.TechnicianCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                key =  getRef(position).getKey();
                Context context = view.getContext();
                Intent intent = new Intent(context, ViewTechnician.class);
                intent.putExtra("technician",key);
                context.startActivity(intent);
            }
        });

    }



    @NonNull
    @Override
    public technicianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.technician_list, parent, false);

        return new ViewTechnicianAdapter.technicianViewHolder(view);

    }

    class technicianViewHolder extends RecyclerView.ViewHolder {

        TextView technicianName;
        ImageView technicianImage,Rating1,Rating2,Rating3,Rating4,Rating5;
        CardView TechnicianCard;

        public technicianViewHolder(@NonNull View itemView) {
            super(itemView);

            technicianName = itemView.findViewById(R.id.technician_name);
            technicianImage = itemView.findViewById(R.id.technician_imgView);
            Rating1 = itemView.findViewById(R.id.rating_1);
            Rating2 = itemView.findViewById(R.id.rating_2);
            Rating3 = itemView.findViewById(R.id.rating_3);
            Rating4 = itemView.findViewById(R.id.rating_4);
            Rating5 = itemView.findViewById(R.id.rating_5);

            TechnicianCard = itemView.findViewById(R.id.technician_card);
        }
    }
}
