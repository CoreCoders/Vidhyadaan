package com.unbeatable.vidhyadaan.firebasemodle;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rutvik on 05-08-2016 at 03:21 PM.
 */

public class Note
{
    final String date, standard, currentNote, nextNote;

    public Note(String date, String standard, String currentNote, String nextNote)
    {
        this.date = date;
        this.standard = standard;
        this.currentNote = currentNote;
        this.nextNote = nextNote;
    }

    private Map toMap()
    {
        //double brace initialization
        return new HashMap()
        {{

            put("date", date);
            put("standard", standard);
            put("current-note", currentNote);
            put("next-note", nextNote);

        }};
    }

    public static void addNote(final Note note, final String uid, final DatabaseReference dbRef, final OnAddNoteCallback callback)
    {
        dbRef.getRoot();
        final String key = dbRef.child("notes").push().getKey();
        final Map childUpdates = new HashMap();
        childUpdates.put("/notes/" + key + "/", note.toMap());
        childUpdates.put("/user-notes/" + uid + "/" + key + "/", key);
        dbRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener()
        {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if (task.isSuccessful())
                {
                    callback.onNoteAdded(task, OnAddNoteCallback.CallbackStatus.SUCCESS);
                } else
                {
                    callback.onNoteAdded(task, OnAddNoteCallback.CallbackStatus.FAILED);
                }
            }
        });

    }

    public interface OnAddNoteCallback
    {
        enum CallbackStatus
        {
            SUCCESS,
            FAILED,
        }

        void onNoteAdded(Task task, CallbackStatus status);

    }

}
