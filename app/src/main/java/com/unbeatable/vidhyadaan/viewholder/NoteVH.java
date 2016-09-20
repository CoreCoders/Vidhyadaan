package com.unbeatable.vidhyadaan.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unbeatable.vidhyadaan.R;
import com.unbeatable.vidhyadaan.model.Note;

/**
 * Created by Rakshit on 06-09-2016 at 15:01.
 */
public class NoteVH extends RecyclerView.ViewHolder {

    TextView tvTodayNote, tvNextNote, tvDateTime, tvStd;

    Note model;

    Context context;

    private NoteVH(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tvTodayNote = (TextView) itemView.findViewById(R.id.tv_todayNoteDetails);
        tvDateTime = (TextView) itemView.findViewById(R.id.tv_noteDateTime);
        tvNextNote = (TextView) itemView.findViewById(R.id.tv_nextDayNoteDetails);
        tvStd = (TextView) itemView.findViewById(R.id.tv_stdViewNote);
    }

    public static NoteVH create(Context context, ViewGroup parent) {
        return new NoteVH(context, LayoutInflater.from(context).inflate(R.layout.single_view_note_row, parent, false));
    }

    public static void bind(NoteVH vh, Note model) {
        vh.model = model;
        vh.tvDateTime.setText(DateUtils.getRelativeDateTimeString(vh.context, Long.valueOf(model.getTime()),
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS, 0));
        vh.tvNextNote.setText(model.getNextNote());
        vh.tvTodayNote.setText(model.getTodayNote());
        vh.tvStd.setText(model.getStd());
    }

}
