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

    public TaskRepository(Application application) {
        TaskDatabase taskDatabase = TaskDatabase.getDatabase(application);
        mTaskDao = taskDatabase.taskDao();
        mTasks = mTaskDao.getAllTasks();
    }

    public List<Task> getAllTasks() {
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


    public void update (Task task) {
        new updateAsyncTask(mTaskDao).execute(task);
    }

    private static class updateAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        updateAsyncTask(TaskDao taskDao) {
            mAsyncTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.updateTask(params[0]);
            return null;
        }
    }

    public void delete (Task task) {
        new deleteAsyncTask(mTaskDao).execute(task);
    }

    private static class deleteAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        deleteAsyncTask(TaskDao taskDao) {
            mAsyncTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.deleteTask(params[0]);
            return null;
        }
    }
}
