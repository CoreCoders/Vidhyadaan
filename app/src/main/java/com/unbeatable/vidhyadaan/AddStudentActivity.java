package com.unbeatable.vidhyadaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.unbeatable.vidhyadaan.extra.Util;
import com.unbeatable.vidhyadaan.firebasemodle.Student;

import java.util.ArrayList;
import java.util.List;

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

        List<String> std = new ArrayList<>();
        std.add("Select Standard");
        for (int i = 3; i < 12; i++) {
            std.add(String.valueOf(i));
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, std);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spStd.setAdapter(dataAdapter);

        btnAddStudent = (Button) findViewById(R.id.btn_addStudent);

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Utils.isFormValid((ViewGroup) findViewById(R.id.ll_addStudent))){
                    Toast.makeText(AddStudentActivity.this, "Please fill up details", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String name = etStudentName.getText().toString().trim();
                final String std = spStd.getSelectedItem().toString();
                final String fatherName = etFatherName.getText().toString().trim();
                final String motherName = etMotherName.getText().toString().trim();
                final String contact = etContact.getText().toString().trim();
                final String address = etAddress.getText().toString().trim();
                final String occupation = etOccupation.getText().toString().trim();
                final String schoolName = etSchoolName.getText().toString().trim();
                final String lastYearPer = etLastStdPer.getText().toString().trim();
                final String teacherName = etTeacherName.getText().toString().trim();

                Student.addStudent(new Student(name, std, fatherName, motherName, contact,
                        address, occupation, schoolName, lastYearPer, teacherName), new Student.AddStudentResponseCallback() {
                    @Override
                    public void onAddStudentCallback(Task task, AddStudentResponse response) {
                        if (response == AddStudentResponse.SUCCESS) {
                            Toast.makeText(AddStudentActivity.this, "Student Added.", Toast.LENGTH_SHORT).show();
                            Utils.clearForm((ViewGroup) findViewById(R.id.ll_addStudent));
                        } else {
                            Toast.makeText(AddStudentActivity.this, "Failed to add student.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
