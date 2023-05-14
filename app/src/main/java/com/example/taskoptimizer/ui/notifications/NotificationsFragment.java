package com.example.taskoptimizer.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskoptimizer.CalendarAdapter;
import com.example.taskoptimizer.R;
import com.example.taskoptimizer.databinding.FragmentNotificationsBinding;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment  implements CalendarAdapter.OnItemListener{

    private FragmentNotificationsBinding binding;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        selectedDate = LocalDate.now();
        calendarRecyclerView = binding.getRoot().findViewById(R.id.calendarRecyclerView);
        monthYearText = binding.getRoot().findViewById(R.id.textView);

        Button nextMonth = binding.getRoot().findViewById(R.id.nextMonthAction);
        Button previousMonth = binding.getRoot().findViewById(R.id.previousMonthAction);
        setMonthView();

        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        });
        previousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(){
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String>daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date){
       ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);

        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i =1; i<=42; i++){
            if(i <= dayOfWeek || i> daysInMonth + dayOfWeek){
                daysInMonthArray.add("");
            }
            else{
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");

        return date.format(formatter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {

            String message = "Selected Date "+ dayText +" "+ monthYearFromDate(selectedDate);
            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

    }
}