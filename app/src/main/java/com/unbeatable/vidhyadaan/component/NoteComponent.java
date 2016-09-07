package com.unbeatable.vidhyadaan.component;

/**
 * Created by Rakshit on 06-09-2016 at 14:48.
 */
public class NoteComponent<T> {

    public static final int NOTE = 0;
    public static final int CONTACT = 1;

    private final T object;

    private final int viewType;

    public NoteComponent(final T object, final int viewType) {
        this.object = object;
        this.viewType = viewType;
    }

    public T getObject() {
        return object;
    }

    public int getViewType() {
        return viewType;
    }
}
