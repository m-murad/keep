package com.murad.jboss.keep.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.murad.jboss.keep.models.Task;

import java.util.List;

/**
 * Created by murad on 05/01/18.
 */

public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mTasks;

    public TaskRepository(Application application) {
        TaskDatabase taskDatabase = TaskDatabase.getDatabase(application);
        mTaskDao = taskDatabase.taskDao();
        mTasks = mTaskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mTasks;
    }

    public void insert(final Task task) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDao.insertTask(task);
            }
        });
    }

    public void update(final Task task) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDao.updateTask(task);
            }
        });
    }

    public void delete(final Task task) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDao.deleteTask(task);
            }
        });
    }
}
