package com.unbeatable.vidhyadaan.firebasemodle;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unbeatable.vidhyadaan.extra.AppUtil;
import com.unbeatable.vidhyadaan.extra.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rutvik on 05-08-2016 at 03:21 PM.
 */

public class Student
{
    private static final String TAG = AppUtil.APP_TAG + Student.class.getSimpleName();

    String standard, name, fatherName, motherName, contact, address, occupation, schoolName, lastYearPer, teacherName;

    boolean isPresent=false;

    public Student(String name, String standard, String fatherName, String motherName, String contact, String address,
                   String occupation, String schoolName, String lastYearPer, String teacherName)
    {
        this.name = name;
        this.standard = standard;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.contact = contact;
        this.address = address;
        this.occupation = occupation;
        this.schoolName = schoolName;
        this.lastYearPer = lastYearPer;
        this.teacherName = teacherName;
    }

    public String getName()
    {
        return name;
    }

    public String getStandard()
    {
        return standard;
    }

    public String getFatherName()
    {
        return fatherName;
    }

    public String getMotherName()
    {
        return motherName;
    }

    public String getContact()
    {
        return contact;
    }

    public String getAddress()
    {
        return address;
    }

    public String getOccupation()
    {
        return occupation;
    }

    public String getSchoolName()
    {
        return schoolName;
    }

    public String getLastYearPer()
    {
        return lastYearPer;
    }

    public String getTeacherName()
    {
        return teacherName;
    }

    public boolean isPresent()
    {
        return isPresent;
    }

    public void setPresent(boolean present)
    {
        isPresent = present;
    }

    public static Student fromMap(Map<String, String> stringStudentMap)
    {
        return new Student(stringStudentMap.get("name"),
                stringStudentMap.get("standard"),
                stringStudentMap.get("fatherName"),
                stringStudentMap.get("motherName"),
                stringStudentMap.get("contact"),
                stringStudentMap.get("address"),
                stringStudentMap.get("occupation"),
                stringStudentMap.get("schoolName"),
                stringStudentMap.get("lastYearPer"),
                stringStudentMap.get("teacherName"));
    }

    public static void addStudent(Student student, final AddStudentResponseCallback callback)
    {
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.getRoot();

        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("name", student.getName());
        studentMap.put("fathername", student.getFatherName());
        studentMap.put("mothername", student.getMotherName());
        studentMap.put("contact", student.getContact());
        studentMap.put("address", student.getAddress());
        studentMap.put("occupation", student.getOccupation());
        studentMap.put("schoolname", student.getSchoolName());
        studentMap.put("lastyearper", student.getLastYearPer());
        studentMap.put("teachername", student.getTeacherName());

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

    public static void getStudents(final String uid, final StudentListListener studentListListener)
    {

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.getRoot();

        dbRef.child("user").child(uid).child("standard").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                final String standard = dataSnapshot.getValue().toString();

                dbRef.getRoot();

                dbRef.child("students").child(standard).addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        Log.i(TAG,dataSnapshot.toString());
                        Map<String, Object> studentMap = (Map<String, Object>) dataSnapshot.getValue();
                        studentListListener.onGetStudent(studentMap);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

    }

    public interface AddStudentResponseCallback
    {
        enum AddStudentResponse
        {
            SUCCESS,
            FAILED,
        }

        void onAddStudentCallback(Task task, AddStudentResponse response);
    }


    public interface StudentListListener
    {
        void onGetStudent(Map<String, Object> studentMap);
    }

}
