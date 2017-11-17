package com.murad.jboss.keep.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.murad.jboss.keep.R;
import com.murad.jboss.keep.models.Task;

import java.util.List;

/**
 * Created by murad on 17/11/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder>{

    private Context context;
    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
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
        holder.taskDescription.setText(task.getTask());
        holder.taskTitle.setText(task.getTitle());
        holder.taskPriority.setText(task.getPriority());
        holder.taskDueDate.setText(task.getDueDate());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskAdapterViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        private TextView taskTitle;
        private TextView taskDescription;
        private TextView taskPriority;
        private TextView taskDueDate;

        public TaskAdapterViewHolder(View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.item_task_title);
            taskDescription = itemView.findViewById(R.id.item_task_description);
            taskPriority = itemView.findViewById(R.id.item_task_priority);
            taskDueDate = itemView.findViewById(R.id.item_task_duedate);
        }

        @Override
        public void onClick(View v) {
            // TODO: Do something with task.
        }
    }
}
