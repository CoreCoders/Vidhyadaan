package com.unbeatable.vidhyadaan;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unbeatable.vidhyadaan.firebasemodle.Note;

public class TakeMyNoteActivity extends AppCompatActivity
{

    private Toolbar mToolbar;
    private TextView tvSelectDate;
    private AppCompatSpinner spStdNote;
    private EditText etTodayNote, etNextNote;
    private Button btnTakeNote;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_my_note);

        mToolbar = (Toolbar) findViewById(R.id.toolNote);
        setSupportActionBar(mToolbar);

        tvSelectDate = (TextView) findViewById(R.id.tv_selectDate);
        spStdNote = (AppCompatSpinner) findViewById(R.id.sp_std_note);
        etTodayNote = (EditText) findViewById(R.id.et_todayNote);
        etNextNote = (EditText) findViewById(R.id.et_nextNote);
        btnTakeNote = (Button) findViewById(R.id.btn_takeMyNote);
        btnTakeNote.setOnClickListener(new AddNote(this, "rutvik106"));
    }

    class AddNote implements View.OnClickListener, Note.OnAddNoteCallback
    {
        final String uid;
        final Context context;

        public AddNote(final Context context, final String uid)
        {
            this.context = context;
            this.uid = uid;
        }

        @Override
        public void onClick(View view)
        {

            final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

            Note.addNote(new Note("14-08-2016", "5", "hello this is what i did today", "on next sunday"),
                    uid,
                    dbRef,
                    this);
        }

        @Override
        public void onNoteAdded(Task task, CallbackStatus status)
        {
            if (status == CallbackStatus.SUCCESS)
            {
                Toast.makeText(context, "note added successfully", Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(context, "failed to add note", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
