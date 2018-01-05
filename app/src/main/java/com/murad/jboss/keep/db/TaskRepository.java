package com.murad.jboss.keep.db;

import android.app.Application;
import android.os.AsyncTask;

import com.murad.jboss.keep.models.Task;

import java.util.List;

/**
 * Created by murad on 05/01/18.
 */

public class TaskRepository {

    private TaskDao mTaskDao;
    private List<Task> mTasks;

    TaskRepository(Application application) {
        TaskDatabase db = TaskDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mTasks = mTaskDao.getAllTasks();
    }

    List<Task> getAllTasks() {
        return mTasks;
    }

    public void insert (Task task) {
        new insertAsyncTask(mTaskDao).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao taskDao) {
            mAsyncTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insertTask(params[0]);
            return null;
        }
    }
}
