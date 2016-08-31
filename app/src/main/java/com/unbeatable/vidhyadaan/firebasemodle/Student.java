package com.unbeatable.vidhyadaan.firebasemodle;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rutvik on 05-08-2016 at 03:21 PM.
 */

public class Student
{

    String standard, name;

    public Student(String name, String standard)
    {
        this.name = name;
        this.standard = standard;
    }

    public String getName()
    {
        return name;
    }

    public String getStandard()
    {
        return standard;
    }

    public static void addStudent(Student student, final AddStudentResponseCallback callback)
    {
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.getRoot();

        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("name", student.getName());

        final String key = dbRef.child("student").child(student.getStandard()).push().getKey();

        final Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/students/" + student.getStandard() + "/" + key + "/", studentMap);
        dbRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener()
        {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if (task.isSuccessful())
                {
                    callback.onAddStudentCallback(task, AddStudentResponseCallback.AddStudentResponse.SUCCESS);
                } else
                {
                    callback.onAddStudentCallback(task, AddStudentResponseCallback.AddStudentResponse.FAILED);
                }
            }
        });
    }

    interface AddStudentResponseCallback
    {
        enum AddStudentResponse
        {
            SUCCESS,
            FAILED,
        }

        void onAddStudentCallback(Task task, AddStudentResponse response);
    }


}
