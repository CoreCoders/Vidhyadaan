package com.unbeatable.vidhyadaan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.unbeatable.vidhyadaan.extra.AppUtil;
import com.unbeatable.vidhyadaan.firebasemodle.Note;

public class HomeActivity extends AppCompatActivity
{

    private static final String TAG = AppUtil.APP_TAG + HomeActivity.class.getSimpleName();

    private CardView cardTakeAttandance, cardTakeNote, cardAddStudent, cardViewNote;
    private Dialog dialog;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        cardTakeNote = (CardView) findViewById(R.id.card_takeNote);

        cardTakeNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog = new Dialog(HomeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_take_note);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView myNote = (TextView) dialog.findViewById(R.id.tv_takeMyNote);
                myNote.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        startActivity(new Intent(HomeActivity.this, TakeMyNoteActivity.class));
                    }
                });

                dialog.show();
            }
        });
    }
}
