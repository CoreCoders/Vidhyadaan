package com.unbeatable.vidhyadaan.firebasemodle;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unbeatable.vidhyadaan.extra.AppUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by rutvik on 05-08-2016 at 03:21 PM.
 */

public class Note {

    private static final String TAG = AppUtil.APP_TAG + Note.class.getSimpleName();

    final String date, standard, currentNote, nextNote;

    public String getNextNote() {
        return nextNote;
    }

    public String getCurrentNote() {
        return currentNote;
    }

    public String getDate() {
        return date;
    }

    public String getStandard() {
        return standard;
    }

    public Note(String date, String standard, String currentNote, String nextNote) {
        this.date = date;
        this.standard = standard;
        this.currentNote = currentNote;
        this.nextNote = nextNote;
    }

    public static Note fromMap(final Map<String, String> noteData) {
        return new Note(noteData.get("date"),
                noteData.get("standard"),
                noteData.get("current-note"),
                noteData.get("next-note"));
    }



    private Map toMap() {
        //double brace initialization
        return new HashMap() {{

            put("date", date);
            put("standard", standard);
            put("current-note", currentNote);
            put("next-note", nextNote);

        }};
    }

    public static void addNote(final Note note, final String uid, final DatabaseReference dbRef, final OnAddNoteCallback callback) {
        dbRef.getRoot();
        final String key = dbRef.child("notes").push().getKey();
        final Map childUpdates = new HashMap();
        childUpdates.put("/notes/" + key + "/", note.toMap());
        childUpdates.put("/user-notes/" + uid + "/" + key + "/", key);
        dbRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    callback.onNoteAdded(task, OnAddNoteCallback.CallbackStatus.SUCCESS);
                } else {
                    callback.onNoteAdded(task, OnAddNoteCallback.CallbackStatus.FAILED);
                }
            }
        });

    }


    public static void getUserNotes(String userId, final OnGetNotesCallback callback) {
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.getRoot();
        dbRef.child("user-notes").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange: " + dataSnapshot.getValue());
                Map noteKeysMap = (Map) dataSnapshot.getValue();
                Set<Map.Entry<Object, String>> noteKeysEntry = noteKeysMap.entrySet();
                Iterator<Map.Entry<Object, String>> noteKeysIterator = noteKeysEntry.iterator();
                final List<String> noteKeys = new LinkedList<>();
                while (noteKeysIterator.hasNext()) {
                    noteKeys.add(noteKeysIterator.next().getValue());
                }
                dbRef.getRoot();
                for (String noteKey : noteKeys) {
                    dbRef.child("notes").child(noteKey).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            callback.onGetNotesSuccess(fromMap((Map<String, String>) dataSnapshot.getValue()));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            callback.onGetNotesFailed(databaseError);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onGetNotesFailed(databaseError);
            }
        });
    }

    public interface OnAddNoteCallback {
        enum CallbackStatus {
            SUCCESS,
            FAILED,
        }

        void onNoteAdded(Task task, CallbackStatus status);

    }

    public interface OnGetNotesCallback {

        void onGetNotesSuccess(Note userNote);

        void onGetNotesFailed(DatabaseError databaseError);

    }

}
