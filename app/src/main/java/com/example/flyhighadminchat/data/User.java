package com.example.flyhighadminchat.data;

import com.google.firebase.database.Exclude;

public class User {

    private Object _open;
    private Object _request;
    private String _username;

    public User() {
    }

    public User(Object _open, Object _request, String _username) {
        this._open = _open;
        this._request = _request;
        this._username = _username;
    }

    public Object get_open() {
        return _open;
    }

    public Object get_request() {
        return _request;
    }

    public String get_username() {
        return _username;
    }

    @Exclude
    public long get_long_request() {
        return (long) _request;
    }

    @Exclude
    public long get_long_open() {
        return (long) _open;
    }

}
