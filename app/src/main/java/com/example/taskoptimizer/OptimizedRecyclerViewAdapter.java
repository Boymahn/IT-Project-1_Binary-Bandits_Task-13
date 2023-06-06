package com.example.taskoptimizer;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.List;

public class OptimizedRecyclerViewAdapter extends RecyclerView.Adapter<OptimizedRecyclerViewAdapter.OptimizedViewHolder> {

    Context context;
    FocusTimer focusTimer;

    List<OptimizedTask> optimizedTaskList;



    public OptimizedRecyclerViewAdapter(Context context, List<OptimizedTask> optimizedTasks){
        this.context = context;
        this.optimizedTaskList = optimizedTasks;
        focusTimer = new FocusTimer(context);
    }

    public void updateAdded(int position){
        this.notifyItemInserted(position);
    }

    public void updateDeleted(int position){
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, optimizedTaskList.size());

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

        holder.date.setText(MessageFormat.format(" {0}", optimizedTaskList.get(position).getEnd()));
        holder.description.setText(optimizedTaskList.get(position).getDescription());
        holder.priority.setText(MessageFormat.format("Priority: {0}", optimizedTaskList.get(position).getPriority()));
        holder.difficulty.setText(MessageFormat.format("Estimated Time: {0}", optimizedTaskList.get(position).getDifficulty()));
        holder.timeRemaining.setText(MessageFormat.format("Remaining Time: {0}days", (int) optimizedTaskList.get(position).getRemainingTime()));
        holder.timeWorkedOn.setText(MessageFormat.format("Time spent on: {0}", (int) optimizedTaskList.get(position).getTimeWorked()));
        holder.timeToWork.setText(MessageFormat.format("Suggested time daily: {0} minutes", optimizedTaskList.get(position).getRecommendedTime()));

        holder.focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focusTimer.isNotificationPolicyAccessGranted() && focusTimer.areNotificationsEnabled()) {
                    focusTimer.enableDoNotDisturb();
                    if (focusTimer.isDoNotDisturbEnabled()) {
                        focusTimer.disableDoNotDisturb();
                    }
                    focusTimer.startfocustimer(10000);
                } else {
                    focusTimer.requestNotificationPolicyAccess();
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHandler db = new SQLiteHandler(context);
                db.deleteOptimizedTask(optimizedTaskList.get(holder.getAdapterPosition()).getId());
                optimizedTaskList.remove(holder.getAdapterPosition());
                updateDeleted(holder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {

        return optimizedTaskList.size();
    }

    public static class OptimizedViewHolder extends RecyclerView.ViewHolder {

        TextView priority, description, date, timeRemaining, timeWorkedOn, timeToWork, difficulty;
        ImageButton delete, edit, focus;
        CheckBox completed_checkbox;
        public OptimizedViewHolder(@NonNull View itemView) {
            super(itemView);
            priority = itemView.findViewById(R.id.priority_label);
            difficulty = itemView.findViewById(R.id.difficulty_textView);
            description = itemView.findViewById(R.id.description_textview);
            date = itemView.findViewById(R.id.date_textView);
            timeRemaining = itemView.findViewById(R.id.remainingTime_textView);
            timeWorkedOn = itemView.findViewById(R.id.timeWorkedOnTask_textview);
            timeToWork = itemView.findViewById(R.id.suggestedDailyTime_textView);
            focus = itemView.findViewById(R.id.focus_btn);
            delete = itemView.findViewById(R.id.delete_btn);
            edit = itemView.findViewById(R.id.edit_btn);
            completed_checkbox = itemView.findViewById(R.id.completed_checkBox);

        }
    }
}
