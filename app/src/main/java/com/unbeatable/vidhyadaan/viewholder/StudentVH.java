package com.unbeatable.vidhyadaan.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.unbeatable.vidhyadaan.R;
import com.unbeatable.vidhyadaan.firebasemodle.Student;

/**
 * Created by Rakshit on 20-09-2016 at 16:13.
 */

public class StudentVH extends RecyclerView.ViewHolder
{

    CheckBox cbStudent;

    Student model;

    final Context context;

    private StudentVH(final Context context, View itemView)
    {
        super(itemView);
        this.context = context;
        cbStudent = (CheckBox) itemView.findViewById(R.id.cb_studentName);
        cbStudent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isPresent)
            {
                model.setPresent(isPresent);
            }
        });
    }

    public static StudentVH create(final Context context, final ViewGroup parent)
    {
        return new StudentVH(context, LayoutInflater.from(context).inflate(R.layout.single_view_attendence_row, parent, false));
    }

    public static void bind(StudentVH vh, Student model)
    {
        vh.model = model;
        vh.cbStudent.setText(model.getName());
    }

}
