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
List<OptimizedTask> optimizedTasks;

public Overview_RecyclerViewAdapter(Context context, List<OptimizedTask> optimizedTasks){
    this.context =context;
    this.optimizedTasks = optimizedTasks;
}
    public void update(int position){
        this.notifyItemInserted(position);
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

    holder.description.setText(optimizedTasks.get(position).getDescription());
    holder.date.setText(optimizedTasks.get(position).getEnd());

    }

    @Override
    public int getItemCount() {
        return optimizedTasks.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        TextView description, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.completed_checkBox);
            //description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.display_end_date);
        }
    }
}
