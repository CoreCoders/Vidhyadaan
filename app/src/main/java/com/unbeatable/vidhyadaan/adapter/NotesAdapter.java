package com.unbeatable.vidhyadaan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.unbeatable.vidhyadaan.component.NoteComponent;
import com.unbeatable.vidhyadaan.firebasemodle.Attendance;
import com.unbeatable.vidhyadaan.firebasemodle.Student;
import com.unbeatable.vidhyadaan.firebasemodle.User;
import com.unbeatable.vidhyadaan.model.Note;
import com.unbeatable.vidhyadaan.viewholder.NoteVH;
import com.unbeatable.vidhyadaan.viewholder.StudentVH;
import com.unbeatable.vidhyadaan.viewholder.UserVH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rakshit on 06-09-2016 at 14:45.
 */
public class NotesAdapter extends RecyclerView.Adapter {


    final List<NoteComponent> noteComponentList;

    final Context context;

    public NotesAdapter(Context context) {
        this.context = context;
        noteComponentList = new ArrayList<>();
    }

    public void addNewNote(Note note) {
        noteComponentList.add(new NoteComponent(note, NoteComponent.NOTE));
        notifyItemInserted(noteComponentList.size());
    }

    public void addUser(User user) {
        noteComponentList.add(new NoteComponent(user, NoteComponent.USER));
        notifyItemInserted(noteComponentList.size());
    }

    public void addStudent(Student user) {
        noteComponentList.add(new NoteComponent(user, NoteComponent.STUDENT));
        notifyItemInserted(noteComponentList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return noteComponentList.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NoteComponent.NOTE:
                return NoteVH.create(context, parent);
            case NoteComponent.USER:
                return UserVH.create(context, parent);
            case NoteComponent.STUDENT:
                return StudentVH.create(context, parent);
            case NoteComponent.CONTACT:
                return null;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case NoteComponent.NOTE:
                NoteVH.bind((NoteVH) holder, (Note) noteComponentList.get(position).getObject());
                break;
            case NoteComponent.USER:
                UserVH.bind((UserVH) holder, (User) noteComponentList.get(position).getObject());
                break;
            case NoteComponent.STUDENT:
                StudentVH.bind((StudentVH) holder, (Student) noteComponentList.get(position).getObject());
                break;
            case NoteComponent.CONTACT:

                break;
        }

    }

    @Override
    public int getItemCount() {
        return noteComponentList.size();
    }


    public Attendance prepareAttendanceData() {
        final Map<String, Integer> attendanceListMap = new HashMap<>();
        for (NoteComponent component : noteComponentList) {
            if (component.getObject() instanceof Student) {
                final Student s = (Student) component.getObject();
                attendanceListMap.put(s.getStudentKey(), s.isPresent() ? 1 : 0);
            }
        }
        return new Attendance(attendanceListMap);
    }

}
