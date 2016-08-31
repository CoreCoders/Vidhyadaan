package com.unbeatable.vidhyadaan;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unbeatable.vidhyadaan.firebasemodle.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TakeMyNoteActivity extends AppCompatActivity
{

    private Toolbar mToolbar;
    //private TextView tvSelectDate;
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

        //tvSelectDate = (TextView) findViewById(R.id.tv_selectDate);
        spStdNote = (AppCompatSpinner) findViewById(R.id.sp_std_note);


        List<String> std = new ArrayList<>();
        std.add("Select Standard");
        for (int i = 3; i < 12; i++)
        {
            std.add(String.valueOf(i));
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, std);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spStdNote.setAdapter(dataAdapter);


        etTodayNote = (EditText) findViewById(R.id.et_todayNote);
        etNextNote = (EditText) findViewById(R.id.et_nextNote);
        btnTakeNote = (Button) findViewById(R.id.btn_takeMyNote);

        final String uid = ((App) getApplication()).getUid();

        if (uid.isEmpty())
        {
            btnTakeNote.setOnClickListener(new AddNote(this, uid));
        }

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

            final String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());

            final String standard = spStdNote.getSelectedItem().toString();

            final String todayNote = etTodayNote.getText().toString().trim();

            final String nextNote = etNextNote.getText().toString().trim();

            if(!todayNote.isEmpty() && !nextNote.isEmpty()){
                Note.addNote(new Note(timestamp, standard, todayNote, nextNote),
                        uid,
                        dbRef,
                        this);
            }else{
                Toast.makeText(TakeMyNoteActivity.this, "please enter note", Toast.LENGTH_SHORT).show();
            }


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
