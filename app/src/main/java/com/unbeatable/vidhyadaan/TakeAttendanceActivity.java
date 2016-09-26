package com.unbeatable.vidhyadaan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.unbeatable.vidhyadaan.adapter.NotesAdapter;
import com.unbeatable.vidhyadaan.extra.AppUtil;
import com.unbeatable.vidhyadaan.firebasemodle.Attendance;
import com.unbeatable.vidhyadaan.firebasemodle.Student;

import java.util.Map;
import java.util.Set;

public class TakeAttendanceActivity extends AppCompatActivity implements Student.StudentListListener {


    private static final String TAG = AppUtil.APP_TAG + TakeAttendanceActivity.class.getSimpleName();

    RecyclerView rvTakeAttendance;

    NotesAdapter adapter;

    private Button btnTakeAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        rvTakeAttendance = (RecyclerView) findViewById(R.id.rv_takeAttendance);

        btnTakeAttendance = (Button) findViewById(R.id.btn_takeAttendance);

        rvTakeAttendance.setHasFixedSize(true);

        rvTakeAttendance.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotesAdapter(this);

        rvTakeAttendance.setAdapter(adapter);

        Student.getStudents(((App) getApplication()).getUser().getUser_id(), this);

        btnTakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Attendance.submitAttendance(new Attendance.OnAttendanceSubmitListener() {
                    @Override
                    public void submitSuccessful() {
                        Toast.makeText(TakeAttendanceActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void submitFailed(DatabaseError error) {
                        Log.i(TAG, error.toString());
                        Toast.makeText(TakeAttendanceActivity.this, "Failed to Add", Toast.LENGTH_SHORT).show();
                    }
                }, adapter.prepareAttendanceData(), ((App) getApplication()).getUser());
            }
        });

    }


    @Override
    public void onGetStudent(Map<String, Object> studentMap) {
        Set<Map.Entry<String, Object>> entrySet = studentMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            Map<String, String> singleStudentMap = (Map<String, String>) entry.getValue();
            adapter.addStudent(Student.fromMap(singleStudentMap, entry.getKey()));
        }
    }
}
