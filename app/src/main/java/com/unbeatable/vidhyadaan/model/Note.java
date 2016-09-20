package com.unbeatable.vidhyadaan.model;

/**
 * Created by Rakshit on 06-09-2016 at 14:57.
 */
public class Note {

    public final String todayNote, nextNote, time, std;

    public Note(final String todayNote, final String nextNote, final String time, final String std) {
        this.time = time;
        this.todayNote = todayNote;
        this.nextNote = nextNote;
        this.std = std;
    }

    public String getTodayNote() {
        return todayNote;
    }

    public String getNextNote() {
        return nextNote;
    }

    public String getTime() {
        return time;
    }

    public String getStd() {
        return std;
    }
}
