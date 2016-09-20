package com.unbeatable.vidhyadaan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unbeatable.vidhyadaan.adapter.NotesAdapter;
import com.unbeatable.vidhyadaan.extra.AppUtil;
import com.unbeatable.vidhyadaan.extra.Log;
import com.unbeatable.vidhyadaan.firebasemodle.Student;
import com.unbeatable.vidhyadaan.firebasemodle.User;

import java.util.Iterator;
import java.util.Map;

public class TakeAttendanceActivity extends AppCompatActivity implements Student.StudentListListener
{


    private static final String TAG = AppUtil.APP_TAG+TakeAttendanceActivity.class.getSimpleName();

    RecyclerView rvTakeAttendance;

    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        rvTakeAttendance = (RecyclerView) findViewById(R.id.rv_takeAttendance);

        rvTakeAttendance.setHasFixedSize(true);

        rvTakeAttendance.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotesAdapter(this);

        rvTakeAttendance.setAdapter(adapter);

        Student.getStudents(((App) getApplication()).getUid(), this);

    }


    @Override
    public void onGetStudent(Map<String, Object> studentMap)
    {
        Iterator<Object> iterator = studentMap.values().iterator();
        while (iterator.hasNext())
        {
            Map<String, String> singleStudentMap = (Map<String, String>) iterator.next();
            Log.i(TAG,singleStudentMap.toString());
            adapter.addStudent(Student.fromMap(singleStudentMap));

        }
    }
}
