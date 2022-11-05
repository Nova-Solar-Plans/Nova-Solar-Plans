package com.user.technician.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.user.main.R;
import com.user.technician.modal.TaskModal;

public class FinishedTaskAdapter extends FirebaseRecyclerAdapter<TaskModal, FinishedTaskAdapter.taskViewHolder> {

    public FinishedTaskAdapter(
            @NonNull FirebaseRecyclerOptions<TaskModal> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull taskViewHolder holder,
                     int position, @NonNull TaskModal model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.taskType.setText(model.getType());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.taskDescription.setText(model.getDescription());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.taskDate.setText(model.getDate());
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public taskViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list, parent, false);

        return new FinishedTaskAdapter.taskViewHolder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class taskViewHolder extends RecyclerView.ViewHolder {
        TextView taskType, taskDescription, taskDate;

        public taskViewHolder(@NonNull View itemView)
        {
            super(itemView);

            taskType = itemView.findViewById(R.id.task_type);
            taskDescription = itemView.findViewById(R.id.task_description);
            taskDate = itemView.findViewById(R.id.date);
        }
    }
}