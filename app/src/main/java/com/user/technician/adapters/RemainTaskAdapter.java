package com.user.technician.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.user.main.R;
import com.user.technician.FinishedTaskDetails;
import com.user.technician.RemainingTaskDetails;
import com.user.technician.modal.TaskModal;

public class RemainTaskAdapter extends FirebaseRecyclerAdapter<TaskModal,RemainTaskAdapter.taskViewHolder> {


    public RemainTaskAdapter(@NonNull FirebaseRecyclerOptions<TaskModal> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull RemainTaskAdapter.taskViewHolder holder,int position, @NonNull TaskModal model) {

        holder.taskType.setText(model.getType());
        holder.taskDescription.setText(model.getDescription());
        holder.taskDate.setText(model.getDate());

        holder.TaskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key =  getRef(position).getKey();
                Context context = view.getContext();
                Intent intent = new Intent(context, RemainingTaskDetails.class);
                intent.putExtra("type",model.getType());
                intent.putExtra("taskNo",key);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list, parent, false);

        return new RemainTaskAdapter.taskViewHolder(view);
    }

    class taskViewHolder extends RecyclerView.ViewHolder {

        TextView taskType, taskDescription, taskDate;
        CardView TaskCard;

        public taskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskType = itemView.findViewById(R.id.task_type);
            taskDescription = itemView.findViewById(R.id.task_description);
            taskDate = itemView.findViewById(R.id.date);

            TaskCard = itemView.findViewById(R.id.task_card);
        }
    }
}
