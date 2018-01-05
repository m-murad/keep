package com.murad.jboss.keep.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.murad.jboss.keep.db.TaskRepository;
import com.murad.jboss.keep.models.Task;

import java.util.List;

/**
 * Created by murad on 06/01/18.
 */

public class TaskViewModel extends AndroidViewModel {

    private LiveData<List<Task>> tasks;
    private TaskRepository taskRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        taskRepository = new TaskRepository(application);
        tasks = taskRepository.getAllTasks();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    public void insert(@NonNull Task task) {
        taskRepository.insert(task);
    }

    public void delete(@NonNull Task task) {
        taskRepository.delete(task);
    }

    public void update(@NonNull Task task) {
        taskRepository.update(task);
    }
}
