package com.murad.jboss.keep.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.murad.jboss.keep.R;
import com.murad.jboss.keep.models.Task;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {


    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment getInstance(@Nullable Task task, @Nullable Integer index) {
        return new AddTaskFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }
}
