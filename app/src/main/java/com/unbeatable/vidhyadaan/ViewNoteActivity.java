package com.unbeatable.vidhyadaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseError;
import com.unbeatable.vidhyadaan.adapter.NotesAdapter;
import com.unbeatable.vidhyadaan.firebasemodle.Note;

public class ViewNoteActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private NotesAdapter adaapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        mToolbar = (Toolbar) findViewById(R.id.toolViewNote);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvNotes = (RecyclerView) findViewById(R.id.rv_notes);

        rvNotes.setHasFixedSize(true);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        adaapter = new NotesAdapter(this);

        rvNotes.setAdapter(adaapter);

        getUserNotes();

    }


    private void getUserNotes() {

        final String uid = ((App) getApplication()).getUser().getUser_id();

        if (!uid.isEmpty()) {
            Note.getUserNotes(uid, new Note.OnGetNotesCallback() {
                @Override
                public void onGetNotesSuccess(Note userNote) {

                    adaapter.addNewNote(new com.unbeatable.vidhyadaan.model.Note(userNote.getCurrentNote(),
                            userNote.getNextNote(), userNote.getDate(), userNote.getStandard()));

                }

                @Override
                public void onGetNotesFailed(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ViewNoteActivity.this, HomeActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
