package com.example.flyhighadminchat.firebase_package.firebase_list_watcher;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SingleSnapshotFromListWatcher extends LiveData<DataSnapshot> {
    private static final String TAG = "ListWatcherDataSnapshot";

    private FirebaseDatabase instance;
    private DatabaseReference ref;


    public SingleSnapshotFromListWatcher() {
        instance = FirebaseDatabase.getInstance();
    }

    ArrayList<DataSnapshot> snapshots;


    public SingleSnapshotFromListWatcher inside(String path) {
        Log.d(TAG, "inside: ");
        ref = instance.getReference(path);

        Log.d(TAG, "inside: ref is: " + ref.toString());
        return this;
    }


    @Override
    protected void onActive() {
        super.onActive();

        ref.addChildEventListener(listenBoolean());
    }


    @Override
    protected void onInactive() {
        super.onInactive();

        ref.removeEventListener(listenBoolean());
        snapshots.clear();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super DataSnapshot> observer) {
        super.observe(owner, observer);
    }

    private ChildEventListener listenBoolean() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                setValue(dataSnapshot);

                Log.d(TAG, "onChildAdded: Value is: " + getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }
}
