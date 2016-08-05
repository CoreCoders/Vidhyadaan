package com.unbeatable.vidhyadaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUserId, etFullName, etPassword, etRePassword;
    private AppCompatSpinner spStd;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Init Component

        etUserId = (EditText) findViewById(R.id.et_userId);
        etFullName = (EditText) findViewById(R.id.et_fullName);
        etPassword = (EditText) findViewById(R.id.et_password_register);
        etRePassword = (EditText) findViewById(R.id.et_reenterPassword);

        spStd = (AppCompatSpinner) findViewById(R.id.sp_std);

        btnSignup = (Button) findViewById(R.id.btnsignUp_register);

        //Start from here
    }
}
