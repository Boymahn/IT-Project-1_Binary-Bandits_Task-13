package com.example.taskoptimizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Overview_RecyclerViewAdapter extends RecyclerView.Adapter<Overview_RecyclerViewAdapter.MyViewHolder> {
Context context;
List<Task> tasks;

public Overview_RecyclerViewAdapter(Context context, List<Task> tasks){
    this.context =context;
    this.tasks = tasks;
}


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    holder.description.setText(tasks.get(position).getDescription());
    holder.date.setText(tasks.get(position).getEnd());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        TextView description, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.completed_checkBox);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.display_end_date);
        }
    }
}
