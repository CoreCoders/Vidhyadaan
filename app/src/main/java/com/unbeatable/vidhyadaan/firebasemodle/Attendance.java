package com.unbeatable.vidhyadaan.firebasemodle;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rutvik on 9/26/2016 at 3:23 PM.
 */

public class Attendance {

    Map<String, Integer> attendanceDataMap;

    public Attendance(final Map<String, Integer> attendanceDataMap) {
        this.attendanceDataMap = attendanceDataMap;
    }

    public void addSingleAttendance(String studentKey, int status) {
        if (status > 1 || status < 0) {
            throw new IllegalArgumentException("attendance status can be 0 or 1");
        } else {
            attendanceDataMap.put(studentKey, status);
        }

    }

    public HashMap<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("attendance-list", attendanceDataMap);
            put("time-stamp", Calendar.getInstance().getTimeInMillis());

        }};
    }

    public static void submitAttendance(final OnAttendanceSubmitListener listener, final Attendance attendance, final User user) {

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.getRoot();
        final String attendanceKey = dbRef.child("attendance").child(user.getUser_id()).child(user.getStandard()).push().getKey();

        final Map<String, Object> postData = new HashMap<>();
        postData.put("/attendance/" + user.getUser_id() + "/" + user.getStandard() + "/" + attendanceKey + "/", attendance.toMap());

        dbRef.updateChildren(postData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    listener.submitSuccessful();
                } else {
                    listener.submitFailed(databaseError);
                }

            }
        });
    }

    public interface OnAttendanceSubmitListener {
        void submitSuccessful();

        void submitFailed(DatabaseError error);
    }

}
