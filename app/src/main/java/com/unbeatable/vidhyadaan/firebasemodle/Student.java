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

public class Student {

    String standard, name, fatherName, motherName, contact, address, occupation, schoolName, lastYearPer, teacherName;

    public Student(String name, String standard, String fatherName, String motherName, String contact, String address,
                   String occupation, String schoolName, String lastYearPer, String teacherName) {
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

    public String getName() {
        return name;
    }

    public String getStandard() {
        return standard;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getLastYearPer() {
        return lastYearPer;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public static void addStudent(Student student, final AddStudentResponseCallback callback) {
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
        dbRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    callback.onAddStudentCallback(task, AddStudentResponseCallback.AddStudentResponse.SUCCESS);
                } else {
                    callback.onAddStudentCallback(task, AddStudentResponseCallback.AddStudentResponse.FAILED);
                }
            }
        });
    }

    public interface AddStudentResponseCallback {
        enum AddStudentResponse {
            SUCCESS,
            FAILED,
        }

        void onAddStudentCallback(Task task, AddStudentResponse response);
    }


}
