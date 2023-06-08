package com.example.taskoptimizer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class AddTaskDialog  extends AppCompatDialogFragment {

    private EditText editDescription;
    private Button dateButton;
    private String date;



    CheckBox checkBox;
    private int priority=0;
    private int  difficulty=0;



    public Dialog onCreateDialog( Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_task_dialog,null);



        builder.setView(view).setTitle("Add Task").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {



                String description = editDescription.getText().toString();


                RecyclerView recyclerView;

                try (SQLiteHandler db = new SQLiteHandler(getActivity())) {

                    List<OptimizedTask> optimizedTasks;
                    if(checkBox.isChecked()){

                        db.addOptimizedTask(new OptimizedTask(description, getTodayDate(), date, priority, difficulty, 1,getContext()));
                        db.updateOptimizedTasks();
                        optimizedTasks = db.getOptimizeTasks();
                        recyclerView = getActivity().findViewById(R.id.optimized_recyclerView);
                        OptimizedRecyclerViewAdapter adapter = new OptimizedRecyclerViewAdapter(getContext(), optimizedTasks);
                        adapter.updateAdded(optimizedTasks.size()-1);
                        recyclerView.setAdapter(adapter);


                    }else{
                        db.addTask(new Task(description, getTodayDate(), date, 1));
                        recyclerView = getActivity().findViewById(R.id.recycler_view);
                        Overview_RecyclerViewAdapter adapter = new Overview_RecyclerViewAdapter(getContext(),db.allTasks());
                        adapter.update(db.allTasks().size()-1);
                        recyclerView.setAdapter(adapter);
                    }


                }

            }
        });


        editDescription = view.findViewById(R.id.description_input);
        dateButton =view.findViewById(R.id.set_date_btn);
        SeekBar difficultySeekBar = view.findViewById(R.id.difficultySeekBar);
        SeekBar prioritySeekBar = view.findViewById(R.id.prioritySeekBar);
        checkBox = view.findViewById(R.id.optimizeCheckBox);
        TextView priorityTextView = view.findViewById(R.id.priorityTextView);
        TextView difficultyTextView = view.findViewById(R.id.difficultyTextView);
        dateButton.setText(getTodayDate());
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker();
            }
        });
        difficultySeekBar.setVisibility(View.GONE);
        prioritySeekBar.setVisibility(View.GONE);
        priorityTextView.setVisibility(View.GONE);
        difficultyTextView.setVisibility(View.GONE);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkBox.isChecked()){
                    difficultySeekBar.setVisibility(View.VISIBLE);
                    prioritySeekBar.setVisibility(View.VISIBLE);
                    priorityTextView.setVisibility(View.VISIBLE);
                    difficultyTextView.setVisibility(View.VISIBLE);
                }
                else{
                    difficultySeekBar.setVisibility(View.GONE);
                    prioritySeekBar.setVisibility(View.GONE);
                    priorityTextView.setVisibility(View.GONE);
                    difficultyTextView.setVisibility(View.GONE);
                }

            }
        });

        difficultySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String diff = "Easy";
                switch(progress){
                    case 1:
                        diff = "Easy";
                        break;
                    case 2:
                        diff = "Medium";
                        break;
                    case 3:
                        diff = "Hard";
                        break;
                }
                difficulty = progress;
                Toast.makeText(getContext(), "Difficulty: "+ diff,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        prioritySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String pri = "low";
                switch(progress){
                    case 1:
                        pri = "Low";

                        break;
                    case 2:
                        pri = "Medium";
                        break;
                    case 3:
                        pri = "High";
                        break;
                }
                priority = progress;
                Toast.makeText(getContext(), "Difficulty: "+ pri,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    private String getTodayDate(){
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day,month,year);

    }
    private void initDatePicker()
    {


        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        date= makeDateString(day, month, year);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

}
