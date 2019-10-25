package com.example.flyhighadminchat.data;

import com.google.firebase.database.Exclude;

public class Sessions {

    private Object _open;
    private Object _closed;

//    @Exclude
//    private String _title;

    public Sessions() {
    }

    public Sessions(Object _open, Object _closed/*, String _title*/) {
        this._open = _open;
        this._closed = _closed;
//        this._title = _title;
    }

    public Object get_open() {
        return _open;
    }

    public Object get_closed() {
        return _closed;
    }

//    @Exclude
//    public String get_title() {
//        return _title;
//    }

    @Exclude
    public long get_long_closed() {
        return (long) _closed;
    }

    @Exclude
    public long get_long_open() {
        return (long) _open;
    }

}
