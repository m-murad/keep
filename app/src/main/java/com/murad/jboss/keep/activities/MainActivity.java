package com.murad.jboss.keep.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.murad.jboss.keep.R;
import com.murad.jboss.keep.adapters.TaskAdapter;
import com.murad.jboss.keep.db.TaskRepository;
import com.murad.jboss.keep.fragments.AddTaskFragment;
import com.murad.jboss.keep.helpers.ItemOffsetDecorator;
import com.murad.jboss.keep.helpers.TouchHelper;
import com.murad.jboss.keep.models.Task;
import com.murad.jboss.keep.viewmodels.TaskViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView noTasks;
    private FloatingActionButton floatingActionButton;

    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.tasks_recycler_view);
        toolbar = findViewById(R.id.toolbar);
        noTasks = findViewById(R.id.txt_no_tracks);
        floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskFragment.getInstance(null).show(getSupportFragmentManager(), "addTask");
            }
        });

        setSupportActionBar(toolbar);

        TaskViewModel taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        TaskRepository taskRepository = new TaskRepository(getApplication());
        LiveData<List<Task>> tasks = taskRepository.getAllTasks();
        taskAdapter = new TaskAdapter(this, taskViewModel);
        ItemTouchHelper.Callback touchCallback = new TouchHelper(taskAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchCallback);
        touchHelper.attachToRecyclerView(recyclerView);

        taskViewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                if (tasks != null && tasks.size() == 0) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    noTasks.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noTasks.setVisibility(View.INVISIBLE);
                    taskAdapter.setTasks(tasks);
                }
            }
        });

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        ItemOffsetDecorator itemDecoration = new ItemOffsetDecorator(this, R.dimen.cardview_default_radius);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(taskAdapter);
    }
}
