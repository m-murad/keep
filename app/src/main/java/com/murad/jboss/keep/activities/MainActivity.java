package com.murad.jboss.keep.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.murad.jboss.keep.R;
import com.murad.jboss.keep.adapters.TaskAdapter;
import com.murad.jboss.keep.db.TaskRepository;
import com.murad.jboss.keep.fragments.AddTaskFragment;
import com.murad.jboss.keep.helpers.ItemOffsetDecorator;
import com.murad.jboss.keep.helpers.TouchHelper;
import com.murad.jboss.keep.models.Task;
import com.murad.jboss.keep.viewmodels.TaskViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tasks_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txt_no_tracks) TextView noTasks;

    private TaskAdapter taskAdapter;
    private LiveData<List<Task>> tasks;
    private ItemTouchHelper.Callback touchCallback;
    private TaskRepository taskRepository;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskRepository = new TaskRepository(getApplication());
        tasks = taskRepository.getAllTasks();
        taskAdapter = new TaskAdapter(this, taskViewModel);
        touchCallback = new TouchHelper(taskAdapter);
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

    @OnClick(R.id.fab)
    public void addTask() {
        AddTaskFragment.getInstance(null).show(getSupportFragmentManager(), "addTask");
    }
}
