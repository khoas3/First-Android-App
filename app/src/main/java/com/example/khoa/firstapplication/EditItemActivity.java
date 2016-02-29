package com.example.khoa.firstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Assign selected item to text field of edit form */
        String selectedItem = getIntent().getStringExtra("content");
        EditText mlEditText = (EditText) findViewById(R.id.mlEditText);
        mlEditText.setText(selectedItem);
        mlEditText.setSelection(mlEditText.getText().length());
        position = getIntent().getIntExtra("position", -1);
    }

    /* Save button */
    public void onSubmit(View view) {
        EditText mlEditText = (EditText) findViewById(R.id.mlEditText);
        Intent data = new Intent();
        data.putExtra("content", mlEditText.getText().toString());
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        /* Closes the activity and returns to first screen */
        finish();
    }
}
