package com.unbeatable.vidhyadaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etEmailId, etPassword;
    private Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Component

        etEmailId = (EditText) findViewById(R.id.et_emailId);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnSignIn = (Button) findViewById(R.id.btn_signIn);
        btnSignUp = (Button) findViewById(R.id.btn_signUp);

        //Start from here
    }
}
