package com.example.taskoptimizer;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OptimizedRecyclerViewAdapter extends RecyclerView.Adapter<OptimizedRecyclerViewAdapter.OptimizedViewHolder> {

    Context context;

    List<OptimizedTask> optimizedTaskList;

    public OptimizedRecyclerViewAdapter(Context context, List<OptimizedTask> optimizedTasks){
        this.context = context;
        this.optimizedTaskList = optimizedTasks;
    }

    @NonNull
    @Override
    public OptimizedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.optimized_recyclerview_row, parent, false);
        return new OptimizedViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull OptimizedRecyclerViewAdapter.OptimizedViewHolder holder, int position) {

        holder.date.setText(" "+optimizedTaskList.get(position).getEnd());
        holder.description.setText(optimizedTaskList.get(position).getDescription());
        holder.priority.setText("Priority: "+optimizedTaskList.get(position).getPriority());
        holder.difficulty.setText("Difficulty: "+optimizedTaskList.get(position).getDifficulty() );
        holder.timeRemaining.setText("Remaining Time: "+(int) optimizedTaskList.get(position).getRemainingTime() + "days");
        holder.timeWorkedOn.setText("Time spent on task: "+(int) optimizedTaskList.get(position).getTimeWorked());
        holder.timeToWork.setText("Suggested Time to work: "+(int)optimizedTaskList.get(position).getRecommendedTime() +" minutes");

    }

    @Override
    public int getItemCount() {

        return optimizedTaskList.size();
    }

    public static class OptimizedViewHolder extends RecyclerView.ViewHolder
    {
        TextView priority, description, date, timeRemaining, timeWorkedOn, timeToWork, difficulty;
        public OptimizedViewHolder(@NonNull View itemView) {
            super(itemView);
            priority = itemView.findViewById(R.id.priority_label);
            difficulty = itemView.findViewById(R.id.difficulty_textView);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date_textView);
            timeRemaining = itemView.findViewById(R.id.remainingTime_textView);
            timeWorkedOn = itemView.findViewById(R.id.timeWorkedOnTask_textview);
            timeToWork = itemView.findViewById(R.id.suggestedDaily_testview);
        }
    }
}
