package com.example.khoa.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.khoa.fragments.EditPostDialog;
import com.example.khoa.fragments.MyAlertDialogFragment;

/**
 * Created by khoa.nguyen on 3/4/2016.
 */
public class DialogPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAlertDialog();
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditPostDialog editPostDialog = EditPostDialog.newInstance("Some Title");
        editPostDialog.show(fm, "fragment_edit_post");
    }

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        MyAlertDialogFragment alertDialog = MyAlertDialogFragment.newInstance("Some title");
        alertDialog.show(fm, "fragment_alert");

    }
}
