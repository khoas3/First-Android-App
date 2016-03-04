package com.example.khoa.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.khoa.activities.R;

/**
 * Created by khoa.nguyen on 3/4/2016.
 */
public class EditPostDialog extends DialogFragment {
    private EditText mEditText;

    public EditPostDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }



    public static EditPostDialog newInstance(String title) {
        EditPostDialog frag = new EditPostDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_post, container);
    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_post);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Title");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
