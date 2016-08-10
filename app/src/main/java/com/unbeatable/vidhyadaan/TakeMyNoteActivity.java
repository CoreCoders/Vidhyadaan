package com.unbeatable.vidhyadaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TakeMyNoteActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView tvSelectDate;
    private AppCompatSpinner spStdNote;
    private EditText etTodayNote, etNextNote;
    private Button btnTakeNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_my_note);

        mToolbar = (Toolbar) findViewById(R.id.toolNote);
        setSupportActionBar(mToolbar);

        tvSelectDate = (TextView) findViewById(R.id.tv_selectDate);
        spStdNote = (AppCompatSpinner) findViewById(R.id.sp_std_note);
        etTodayNote = (EditText) findViewById(R.id.et_todayNote);
        etNextNote = (EditText) findViewById(R.id.et_nextNote);
        btnTakeNote = (Button) findViewById(R.id.btn_takeMyNote);
    }
}
