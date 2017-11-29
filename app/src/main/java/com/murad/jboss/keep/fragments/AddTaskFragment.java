package com.murad.jboss.keep.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.murad.jboss.keep.R;
import com.murad.jboss.keep.models.Task;

import java.util.Calendar;

import butterknife.ButterKnife;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {

    private static Task currentTask;
    private static Integer currentIndex;
    private String taskDate, todaysDate;
    private String taskCreatedOn;

    private EditText taskTitle;
    private EditText taskDescription;
    private Spinner prioritySpinner;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment getInstance(@Nullable Task task, @Nullable Integer index) {
        currentTask = task;
        currentIndex = index;
        return new AddTaskFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialog = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_task, null);

        taskTitle = ButterKnife.findById(dialog, R.id.task_title_et);
        taskDescription = ButterKnife.findById(dialog, R.id.task_description_et);
        DatePicker taskDueDatePicker = ButterKnife.findById(dialog, R.id.task_due_date_picker);
        prioritySpinner = ButterKnife.findById(dialog, R.id.task_priority_spinner);

        final Calendar calendar = Calendar.getInstance();
        todaysDate = taskDueDatePicker.getDayOfMonth() + "-" + (taskDueDatePicker.getMonth() + 1) + "-" + taskDueDatePicker.getYear();
        taskDate = todaysDate;
        taskDueDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                taskDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
            }
        });

        return new AlertDialog.Builder(getContext())
                .setTitle("Add a Task")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(taskTitle.getText().toString().trim())) {
                            Toast.makeText(getContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ("".equals(taskDescription.getText().toString().trim())) {
                            Toast.makeText(getContext(), "Task cannot be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        currentTask = new Task();
                        currentTask.setCreatedOn(todaysDate);
                        currentTask.setTitle(taskTitle.getText().toString().trim());
                        currentTask.setTaskDescription(taskDescription.getText().toString().trim());
                        currentTask.setPriority(prioritySpinner.getSelectedItemPosition());
                        currentTask.setDueDate(taskDate);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setView(dialog)
                .create();
    }
}
