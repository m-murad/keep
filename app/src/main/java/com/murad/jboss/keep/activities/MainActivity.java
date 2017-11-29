package com.murad.jboss.keep.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.murad.jboss.keep.R;
import com.murad.jboss.keep.adapters.TaskAdapter;
import com.murad.jboss.keep.fragments.AddTaskFragment;
import com.murad.jboss.keep.models.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tasks_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txt_no_tracks) TextView noTasks;

    private TaskAdapter taskAdapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        tasks = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, tasks);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        hideOrShowList();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(taskAdapter);
    }

    private void hideOrShowList() {
        if (tasks.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            noTasks.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noTasks.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.fab)
    public void addTask(){
        AddTaskFragment.getInstance(null, null).show(getSupportFragmentManager(), "addTask");
    }
}
