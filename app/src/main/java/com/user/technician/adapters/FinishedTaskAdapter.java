package com.user.technician.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.user.main.R;
import com.user.technician.FinishedTaskDetails;
import com.user.technician.TechnicianFinishedTasks;
import com.user.technician.modal.TaskModal;

public class FinishedTaskAdapter extends FirebaseRecyclerAdapter<TaskModal, FinishedTaskAdapter.taskViewHolder> {

    String key;

    public FinishedTaskAdapter(@NonNull FirebaseRecyclerOptions<TaskModal> options) {
        super(options);
    }


    @Override
    protected void
    onBindViewHolder(@NonNull taskViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull TaskModal model) {


        holder.taskType.setText(model.getType());
        holder.taskDescription.setText(model.getDescription());
        holder.taskDate.setText(model.getDate());


        holder.TaskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

//                DatabaseReference parent = getRef(position).getParent();
//                if (parent != null)
//                    intent.putExtra("ParentKey", parent.getKey());
//                context.startActivity(intent);

               String key =  getRef(position).getKey();

                Context context = view.getContext();
                Intent intent = new Intent(context,FinishedTaskDetails.class);
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

        return new FinishedTaskAdapter.taskViewHolder(view);

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