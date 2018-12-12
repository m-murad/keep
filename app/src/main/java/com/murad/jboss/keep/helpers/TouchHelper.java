package com.murad.jboss.keep.helpers;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.murad.jboss.keep.adapters.TaskAdapter;

/**
 * Created by murad on 05/01/18.
 */

public class TouchHelper extends ItemTouchHelper.SimpleCallback{

    private TaskAdapter taskAdapter;

    public TouchHelper(TaskAdapter taskAdapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
        this.taskAdapter = taskAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        taskAdapter.deleteTask(viewHolder.getAdapterPosition());
    }
}
