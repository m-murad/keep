package com.murad.jboss.keep.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.murad.jboss.keep.R;
import com.murad.jboss.keep.fragments.AddTaskFragment;
import com.murad.jboss.keep.models.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by murad on 17/11/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder> {

    private Context context;
    private List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public TaskAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapterViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.taskTitle.setText(task.getTitle());
        holder.taskDescription.setText(task.getTaskDescription());
        holder.taskDueDate.setText(task.getDueDate().toString());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskAdapterViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        @BindView(R.id.item_task_title) TextView taskTitle;
        @BindView(R.id.item_task_description) TextView taskDescription;
        @BindView(R.id.item_task_duedate) TextView taskDueDate;

        private TaskAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AddTaskFragment.getInstance(tasks.get(getAdapterPosition()), getAdapterPosition())
                    .show(((FragmentActivity)context).getSupportFragmentManager(), "editTask");
        }
    }
}
