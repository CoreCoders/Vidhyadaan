package com.unbeatable.vidhyadaan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.unbeatable.vidhyadaan.component.NoteComponent;
import com.unbeatable.vidhyadaan.model.Note;
import com.unbeatable.vidhyadaan.viewholder.NoteVH;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public int getItemViewType(int position) {
        return noteComponentList.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NoteComponent.NOTE:
                return NoteVH.create(context, parent);
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
            case NoteComponent.CONTACT:

                break;
        }

    }

    @Override
    public int getItemCount() {
        return noteComponentList.size();
    }
}
