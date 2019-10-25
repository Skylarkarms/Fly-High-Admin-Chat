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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListValueDataSnapshot extends LiveData<List<DataSnapshot>> {
    private static final String TAG = "ListWatcherDataSnapshot";

    private FirebaseDatabase instance;
    private DatabaseReference ref;


    public ListValueDataSnapshot() {
        instance = FirebaseDatabase.getInstance();
    }

    ArrayList<DataSnapshot> snapshots;


    private void add2List(DataSnapshot dataSnapshot) {

        Log.d(TAG, "add2List: o is: " + dataSnapshot.toString());
        if (getValue() == null) {
            snapshots = new ArrayList<>();
        }

        snapshots.add(dataSnapshot);
        setValue(snapshots);
    }


    public ListValueDataSnapshot inside(String path) {
        Log.d(TAG, "inside: ");
        ref = instance.getReference(path);

        Log.d(TAG, "inside: ref is: " + ref.toString());
        return this;
    }


    @Override
    protected void onActive() {
        super.onActive();

        ref.addListenerForSingleValueEvent(listenList());
    }


    @Override
    protected void onInactive() {
        super.onInactive();

        snapshots.clear();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<DataSnapshot>> observer) {
        super.observe(owner, observer);
    }

    private ValueEventListener listenList() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()
                     ) {
                    add2List(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }
}
