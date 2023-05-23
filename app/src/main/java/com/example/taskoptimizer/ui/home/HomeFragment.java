package com.example.taskoptimizer.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskoptimizer.OptimizedRecyclerViewAdapter;
import com.example.taskoptimizer.OptimizedTask;
import com.example.taskoptimizer.R;
import com.example.taskoptimizer.SQLiteHandler;
import com.example.taskoptimizer.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView;
        OptimizedRecyclerViewAdapter adapter;

        try(SQLiteHandler db = new SQLiteHandler(binding.getRoot().getContext())){
            List<OptimizedTask> optimizedTaskList = db.getOptimizeTasks();
            recyclerView = binding.getRoot().findViewById(R.id.optimized_recyclerView);
            adapter = new OptimizedRecyclerViewAdapter(binding.getRoot().getContext(), optimizedTaskList);

        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}