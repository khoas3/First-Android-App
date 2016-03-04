package com.example.khoa.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.khoa.activities.R;

/**
 * Created by khoa.nguyen on 3/4/2016.
 */
public class EditPostDialog extends DialogFragment {
    private EditText mEditText;
    private Button mBtnSave;
    public EditPostDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditPostDialog newInstance(String title, int position) {
        EditPostDialog frag = new EditPostDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("position", position);
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
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_post);
        mBtnSave =(Button)view.findViewById(R.id.btnSave);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Title");
        final int position = getArguments().getInt("position", -1);
        //getDialog().setTitle(title);
        mEditText.setText(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        // Save event button
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtChange = mEditText.getText().toString();
                dismiss();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MyAlertDialogFragment alertDialog = MyAlertDialogFragment.newInstance(txtChange, position);
                alertDialog.show(fm, "fragment_alert");
            }
        });
    }
}
