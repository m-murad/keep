package com.murad.jboss.keep.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by murad on 17/11/17.
 */

public class Task implements Parcelable{

    private String title;
    private String task;
    private int priority;
    private String createdOn;
    private String dueDate;

    public String getTitle() {
        return title;
    }
    public String getTask() {
        return task;
    }
    public int getPriority() {
        return priority;
    }
    public String getCreatedOn() {
        return createdOn;
    }
    public String getDueDate() {
        return dueDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Task(){
    }

    protected Task(Parcel in) {
        title = in.readString();
        task = in.readString();
        priority = in.readInt();
        createdOn = in.readString();
        dueDate = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(task);
        dest.writeInt(priority);
        dest.writeString(createdOn);
        dest.writeString(dueDate);
    }
}
