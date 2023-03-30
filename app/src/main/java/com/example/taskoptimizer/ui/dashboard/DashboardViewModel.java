package com.example.taskoptimizer.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskoptimizer.Overview_RecyclerViewAdapter;
import com.example.taskoptimizer.Task;
import com.example.taskoptimizer.SQLiteHandler;

import java.util.List;


public class DashboardViewModel extends ViewModel {
    private SQLiteHandler db;
    //private final MutableLiveData<String> mText;
    private final MutableLiveData<RecyclerView> recyclerview = new MutableLiveData<>();
    private final MutableLiveData<List> taskList;



    public DashboardViewModel() {

        taskList = new MutableLiveData<>();
        //taskList.setValue(db.allTasks());
    }

    //public LiveData<String> getText() {
        //return mText;
    //}

    public LiveData<List>getTasks(){
        return taskList;
    }

}