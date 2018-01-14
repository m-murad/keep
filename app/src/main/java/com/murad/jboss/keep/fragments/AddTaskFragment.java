package com.murad.jboss.keep.fragments;


import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.murad.jboss.keep.R;
import com.murad.jboss.keep.models.Task;
import com.murad.jboss.keep.viewmodels.TaskViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.ButterKnife;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {

    private static Task currentTask;
    private static Integer currentIndex;
    private Long taskDueDate;
    private Long todayDate;
    private String fragmentTitle;
    private Calendar calendar;
    private TaskViewModel taskViewModel;

    private EditText taskTitle;
    private EditText taskDescription;
    private Spinner taskPrioritySpinner;
    private DatePicker taskDueDatePicker;

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
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        View view =  inflater.inflate(R.layout.fragment_add_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialog = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_task, null);

        setTitle();

        taskTitle = ButterKnife.findById(dialog, R.id.task_title_et);
        taskDescription = ButterKnife.findById(dialog, R.id.task_description_et);
        taskDueDatePicker = ButterKnife.findById(dialog, R.id.task_due_date_picker);
        taskDueDatePicker.setMinDate(System.currentTimeMillis() - 1000);
        taskPrioritySpinner = ButterKnife.findById(dialog, R.id.task_priority_spinner);

        calendar = Calendar.getInstance();
        todayDate = calendar.getTime().getTime();
        taskDueDate = todayDate;

        if (currentTask != null) {
            taskTitle.setText(currentTask.getTitle());
            taskDescription.setText(currentTask.getDescription());
            taskPrioritySpinner.setSelection(currentTask.getPriority());
            taskDueDate = currentTask.getDueDate();
        }

        calendar.setTimeInMillis(taskDueDate);
        taskDueDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = new GregorianCalendar(year, monthOfYear , dayOfMonth);
                taskDueDate = calendar.getTimeInMillis();
            }
        });

        AlertDialog.Builder addDialogBuilder = new AlertDialog.Builder(getContext());
        addDialogBuilder.setView(dialog);
        addDialogBuilder.setTitle(fragmentTitle);
        addDialogBuilder.setPositiveButton("Save", null);
        addDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return addDialogBuilder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        AlertDialog addDialog = (AlertDialog)getDialog();
        addDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTask != null) {
                    currentTask.setTitle(taskTitle.getText().toString().trim());
                    currentTask.setDescription(taskDescription.getText().toString().trim());
                    currentTask.setPriority(taskPrioritySpinner.getSelectedItemPosition());
                    currentTask.setDueDate(taskDueDate);
                    taskViewModel.update(currentTask);
                    dismiss();
                } else {
                    currentTask = new Task();
                    currentTask.setCreatedOn(todayDate);
                    currentTask.setTitle(taskTitle.getText().toString().trim());
                    currentTask.setDescription(taskDescription.getText().toString().trim());
                    currentTask.setPriority(taskPrioritySpinner.getSelectedItemPosition());
                    currentTask.setDueDate(taskDueDate);
                    taskViewModel.insert(currentTask);
                    dismiss();
                }
            }
        });
    }

    private boolean taskValid() {
        if (TextUtils.isEmpty(currentTask.getTitle())) {
            taskTitle.requestFocus();
            taskTitle.setError("Task title cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(currentTask.getDescription())) {
            taskDescription.requestFocus();
            taskDescription.setError("Task description cannot be empty");
            return false;
        }
        return true;
    }

    private void setTitle() {
        if ("addTask".equals(getTag())) {
            fragmentTitle = "Add task";
        } else if ("editTask".equals(getTag())) {
            fragmentTitle = "Edit task";
        }
    }
}
