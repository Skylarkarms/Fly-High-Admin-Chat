package com.example.flyhighadminchat.firebase_package.firebase_snapshot_to_adapter;

public class Relator {

    private String value;
    private int viewType;
    private Class mClass;

    public Relator(String value, Class aClass, int viewType) {
        this.value = value;
        mClass = aClass;
        this.viewType = viewType;
    }

    public String getValue() {
        return value;
    }

    public int getViewType() {
        return viewType;
    }

    public Class getAClass() {
        return mClass;
    }
}
