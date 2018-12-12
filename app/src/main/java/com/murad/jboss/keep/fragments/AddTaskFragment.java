package com.murad.jboss.keep.fragments;


import android.app.Dialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
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

/**
 * A simple {@link DialogFragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {

    private static Task currentTask;
    private Long taskDueDate;
    private Long todayDate;
    private String fragmentTitle;
    private TaskViewModel taskViewModel;

    private EditText taskTitle;
    private EditText taskDescription;
    private Spinner taskPrioritySpinner;

    public static AddTaskFragment getInstance(@Nullable Task task) {
        currentTask = task;
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

        taskTitle = dialog.findViewById(R.id.task_title_et);
        taskDescription = dialog.findViewById(R.id.task_description_et);
        DatePicker taskDueDatePicker = dialog.findViewById(R.id.task_due_date_picker);
        taskDueDatePicker.setMinDate(System.currentTimeMillis() - 1000);
        taskPrioritySpinner = dialog.findViewById(R.id.task_priority_spinner);

        Calendar calendar = Calendar.getInstance();
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
                // It will dismiss the dialog
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
                    if (!taskValid()) return;
                    taskViewModel.update(currentTask);
                    dismiss();
                } else {
                    currentTask = new Task();
                    currentTask.setCreatedOn(todayDate);
                    currentTask.setTitle(taskTitle.getText().toString().trim());
                    currentTask.setDescription(taskDescription.getText().toString().trim());
                    currentTask.setPriority(taskPrioritySpinner.getSelectedItemPosition());
                    currentTask.setDueDate(taskDueDate);
                    if (!taskValid()) return;
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
