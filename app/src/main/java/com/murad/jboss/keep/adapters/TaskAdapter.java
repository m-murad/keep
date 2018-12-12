package com.murad.jboss.keep.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.murad.jboss.keep.R;
import com.murad.jboss.keep.fragments.AddTaskFragment;
import com.murad.jboss.keep.models.Task;
import com.murad.jboss.keep.utils.DateUtils;
import com.murad.jboss.keep.viewmodels.TaskViewModel;

import java.util.List;

/**
 * Created by murad on 17/11/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder> {

    private Context context;
    private List<Task> tasks;
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;

    public TaskAdapter(@NonNull Context context, TaskViewModel taskViewModel) {
        this.context = context;
        this.taskViewModel = taskViewModel;
    }

    public void setTasks(@Nullable List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public TaskAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapterViewHolder holder, int position) {
        Task task = tasks.get(position);

        switch (task.getPriority()) {
            case 0:
                holder.taskItem.setBackgroundColor(ContextCompat.getColor(context, R.color.priorityHigh));
                break;
            case 1:
                holder.taskItem.setBackgroundColor(ContextCompat.getColor(context, R.color.priorityMedium));
                break;
            case 2:
                holder.taskItem.setBackgroundColor(ContextCompat.getColor(context, R.color.priorityLow));
                break;
            default:
                holder.taskItem.setBackgroundColor(ContextCompat.getColor(context, R.color.priorityMedium));
        }
        holder.taskTitle.setText(task.getTitle());
        holder.taskDescription.setText(task.getDescription());
        holder.taskDueDate.setText(DateUtils.getDate(String.valueOf(task.getDueDate())));
    }

    @Override
    public int getItemCount() {
        if (tasks == null){
            return 0;
        }
        return tasks.size();
    }

    class TaskAdapterViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        CardView taskItem;
        TextView taskTitle;
        TextView taskDescription;
        TextView taskDueDate;

        private TaskAdapterViewHolder(View itemView) {
            super(itemView);

            taskItem = itemView.findViewById(R.id.single_list_item);
            taskTitle = itemView.findViewById(R.id.item_task_title);
            taskDescription = itemView.findViewById(R.id.item_task_description);
            taskDueDate = itemView.findViewById(R.id.item_task_duedate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AddTaskFragment.getInstance(tasks.get(getAdapterPosition()))
                    .show(((FragmentActivity)context).getSupportFragmentManager(), "editTask");
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    public void deleteTask(int position) {
        final int currentPosition = position;
        final Task removedTask = tasks.get(position);
        taskViewModel.delete(removedTask);
        tasks.remove(position);
        this.notifyItemRemoved(position);
        Snackbar.make(recyclerView, "Task removed", Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.add(currentPosition, removedTask);
                notifyItemInserted(currentPosition);
                taskViewModel.insert(removedTask);
            }
        }).show();
    }
}
