package com.unbeatable.vidhyadaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.unbeatable.vidhyadaan.extra.AppUtil;
import com.unbeatable.vidhyadaan.extra.Log;
import com.unbeatable.vidhyadaan.extra.Util;
import com.unbeatable.vidhyadaan.firebasemodle.User;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUserId, etFullName, etPassword, etRePassword;
    private AppCompatSpinner spStd;
    private Button btnSignup;

    private static final String TAG = AppUtil.APP_TAG + RegisterActivity.class.getSimpleName();

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


        //Start from here
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "SIGN UP CLICKED");


                User.register(FirebaseDatabase.getInstance().getReference(),
                        etUserId.getText().toString().trim().toLowerCase(),
                        etFullName.getText().toString().trim().toLowerCase(),
                        etPassword.getText().toString().trim().toLowerCase(),
                        spStd.getSelectedItem().toString(),
                        new User.UserRegistrationCallback() {
                            @Override
                            public void onRegistrationComplete(int registrationStatus) {
                                if (registrationStatus == User.UserRegistrationCallback.REGISTERED) {
                                    Toast.makeText(RegisterActivity.this, "Registered.!!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                } else if (registrationStatus == User.UserRegistrationCallback.DUPLICATE) {
                                    Toast.makeText(RegisterActivity.this, "Duplicate User id.!!!", Toast.LENGTH_SHORT).show();
                                } else if (registrationStatus == User.UserRegistrationCallback.FAILED) {
                                    Toast.makeText(RegisterActivity.this, "Failed.!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
