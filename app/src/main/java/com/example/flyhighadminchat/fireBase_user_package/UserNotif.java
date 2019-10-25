package com.example.flyhighadminchat.fireBase_user_package;


public class UserNotif {

    private Object _request;
    private Object _open;

    public UserNotif() {
    }

    public UserNotif(Object _request, Object _open) {
        this._request = _request;
        this._open = _open;
    }

    public void set_request(long _request) {
        this._request = _request;
    }

    public void set_open(long _open) {
        this._open = _open;
    }

    public Object get_request() {
        return _request;
    }

    public Object get_open() {
        return _open;
    }
}
