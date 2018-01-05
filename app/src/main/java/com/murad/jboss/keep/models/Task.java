package com.murad.jboss.keep.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by murad on 17/11/17.
 */
@Entity
public class Task implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String taskDescription;
    private int priority;
    private Long createdOn;
    private Long dueDate;

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public int getPriority() {
        return priority;
    }
    public Long getCreatedOn() {
        return createdOn;
    }
    public Long getDueDate() {
        return dueDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }
    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public Task(){
    }

    protected Task(Parcel in) {
        id = in.readInt();
        title = in.readString();
        taskDescription = in.readString();
        priority = in.readInt();
        createdOn = in.readLong();
        dueDate = in.readLong();
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
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(taskDescription);
        dest.writeInt(priority);
        dest.writeLong(createdOn);
        dest.writeLong(dueDate);
    }
}
