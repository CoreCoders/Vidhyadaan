package com.unbeatable.vidhyadaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddStudentActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText etStudentName, etFatherName, etMotherName, etContact, etAddress, etOccupation, etSchoolName,
            etLastStdPer, etTeacherName;
    private AppCompatSpinner spStd;
    private Button btnAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        mToolbar = (Toolbar) findViewById(R.id.tool_addStudent);
        setSupportActionBar(mToolbar);

        etStudentName = (EditText) findViewById(R.id.et_studentName);
        etFatherName = (EditText) findViewById(R.id.et_fatherName);
        etMotherName = (EditText) findViewById(R.id.et_motherName);
        etContact = (EditText) findViewById(R.id.et_studentContact);
        etAddress = (EditText) findViewById(R.id.et_studentAddress);
        etOccupation = (EditText) findViewById(R.id.et_occupation);
        etSchoolName = (EditText) findViewById(R.id.et_schoolName);
        etLastStdPer = (EditText) findViewById(R.id.et_lastStdPercentage);
        etTeacherName = (EditText) findViewById(R.id.et_teacherName);

        spStd = (AppCompatSpinner) findViewById(R.id.sp_studentStd);

        btnAddStudent = (Button) findViewById(R.id.btn_addStudent);

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
