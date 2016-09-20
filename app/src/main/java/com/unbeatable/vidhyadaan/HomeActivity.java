package com.unbeatable.vidhyadaan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.unbeatable.vidhyadaan.extra.AppUtil;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = AppUtil.APP_TAG + HomeActivity.class.getSimpleName();

    private CardView cardTakeAttandance, cardTakeNote, cardAddStudent, cardViewNote;
    private Dialog dialog;
    private Toolbar mToolbar;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        cardTakeAttandance = (CardView) findViewById(R.id.card_takeAttandance);
        cardTakeNote = (CardView) findViewById(R.id.card_takeNote);
        cardAddStudent = (CardView) findViewById(R.id.card_addStudent);
        cardViewNote = (CardView) findViewById(R.id.card_viewNote);

        cardTakeAttandance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(HomeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_take_attendance);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView ownAttendance = (TextView) dialog.findViewById(R.id.tv_takeOwnAttendance);
                TextView otherAttendance = (TextView) dialog.findViewById(R.id.tv_takeOtherAttendance);

                ownAttendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentOwn = new Intent(HomeActivity.this, TakeAttendanceActivity.class);
                        intentOwn.putExtra("view_type", "own");
                        startActivity(intentOwn);
                        dialog.dismiss();
                    }
                });

                otherAttendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, TakeAttendanceActivity.class);
                        intent.putExtra("view_type", "other");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        cardTakeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(HomeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_take_note);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView myNote = (TextView) dialog.findViewById(R.id.tv_takeMyNote);
                myNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HomeActivity.this, TakeMyNoteActivity.class));
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        cardAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddStudentActivity.class));
            }
        });

        cardViewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ViewNoteActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
