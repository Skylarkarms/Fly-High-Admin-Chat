package com.example.flyhighadminchat.firebase_package.firebase_list_router;

import com.google.firebase.database.Query;

public class QueriesBank {

    private Query mQuery;
    private int mTypeOfListener;

    public QueriesBank(Query query, int typeOfListener) {
        mQuery = query;
        mTypeOfListener = typeOfListener;
    }

    public Query getQuery() {
        return mQuery;
    }

    public int getTypeOfListener() {
        return mTypeOfListener;
    }
}
