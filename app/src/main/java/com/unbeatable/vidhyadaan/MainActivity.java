package com.unbeatable.vidhyadaan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.unbeatable.vidhyadaan.extra.Log;
import com.unbeatable.vidhyadaan.firebasemodle.User;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = App.TAG + MainActivity.class.getName();
    private EditText etEmailId, etPassword;
    private Button btnSignIn, btnSignUp;

    private FrameLayout flProcessing;
    private LinearLayout llUserLoginOptions;
    private AppCompatCheckBox cbRememberMe;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = PreferenceManager.getDefaultSharedPreferences(this);


        //Init Component

        etEmailId = (EditText) findViewById(R.id.et_emailId);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnSignIn = (Button) findViewById(R.id.btn_signIn);
        btnSignUp = (Button) findViewById(R.id.btn_signUp);

        flProcessing = (FrameLayout) findViewById(R.id.fl_processing);
        llUserLoginOptions = (LinearLayout) findViewById(R.id.ll_loginOptions);

        cbRememberMe = (AppCompatCheckBox) findViewById(R.id.cb_rememberMe);

        //Start from here
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userName = etEmailId.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                if (userName.isEmpty() && password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "enter valid user name and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                showProcessingView(true);

                User.tryLogin(FirebaseDatabase.getInstance().getReference(),
                        userName,
                        password,
                        new User.LoginResponseCallback() {
                            @Override
                            public void onLoginResult(DataSnapshot dataSnapshot, int loginResult) {
                                if (loginResult == SUCCESS) {
                                    Log.i(TAG, dataSnapshot.toString());
                                    if (cbRememberMe.isChecked()) {
                                        sp.edit().putString("user_name", etEmailId.getText().toString()).apply();
                                        sp.edit().putString("password", etPassword.getText().toString()).apply();
                                    } else {
                                        sp.edit().remove("user_name").apply();
                                        sp.edit().remove("password").apply();
                                    }
                                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                    ((App) getApplication()).setUser(User.fromMap(dataSnapshot.getKey(), (Map<String, Object>) dataSnapshot.getValue()));
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, "invalid username or password", Toast.LENGTH_SHORT).show();
                                    showProcessingView(false);
                                }
                            }
                        });
            }
        });
        checkRememberMe();
    }

    public void showProcessingView(boolean show) {
        if (llUserLoginOptions != null && flProcessing != null) {

            if (show) {
                flProcessing.setVisibility(View.VISIBLE);
                llUserLoginOptions.setVisibility(View.GONE);
            } else {
                flProcessing.setVisibility(View.GONE);
                llUserLoginOptions.setVisibility(View.VISIBLE);
            }

        }
    }

    private void checkRememberMe() {
        if (sp.contains("user_name")) {
            cbRememberMe.setChecked(true);
            etEmailId.setText(sp.getString("user_name", ""));
            etPassword.setText(sp.getString("password", ""));
        } else {
            cbRememberMe.setChecked(false);
        }
    }

}
