package com.example.taskoptimizer.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskoptimizer.Overview_RecyclerViewAdapter;
import com.example.taskoptimizer.R;
import com.example.taskoptimizer.SQLiteHandler;
import com.example.taskoptimizer.Task;
import com.example.taskoptimizer.databinding.FragmentDashboardBinding;

import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView;
        Overview_RecyclerViewAdapter adapter;
        try (SQLiteHandler db = new SQLiteHandler(binding.getRoot().getContext())) {


            List<Task> tasks = db.allTasks();
            recyclerView = binding.getRoot().findViewById(R.id.recycler_view);
            adapter = new Overview_RecyclerViewAdapter(binding.getRoot().getContext(), tasks);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //final TextView textView = binding.textDashboard;
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}