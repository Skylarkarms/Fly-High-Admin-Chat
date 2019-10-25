package com.example.flyhighadminchat.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.flyhighadminchat.R;
import com.example.flyhighadminchat.adapters.SessionsRecyclerViewAdapter;
import com.example.flyhighadminchat.adapters.UsersRecyclerViewAdapter;
import com.example.flyhighadminchat.data.Sessions;
import com.example.flyhighadminchat.data.User;
import com.example.flyhighadminchat.databinding.FragmentSessionsBinding;
import com.example.flyhighadminchat.databinding.FragmentUsersBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.List;


public class SessionsFragment extends Fragment implements FlyHighActivity.ListWatcherListener, SessionsRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = "SessionsFragment";


    private WeakReference<FlyHighActivity> mContextWeakReference;
    private SessionsRecyclerViewAdapter adapter;

    private String uid;

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
        if (context instanceof FlyHighActivity) {

            mContextWeakReference = new WeakReference<>((FlyHighActivity)context);

        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

        adapter = new SessionsRecyclerViewAdapter();
        adapter.setOnItemClickListener(this::onItemClick);
        FlyHighActivity activity = mContextWeakReference.get();

//        /*TODO: This is a fucking placeHolder*/
//        String Uid = "6MgdjiA4ZvONeFVdWVw8d55eqa82";

        if (uid != null) {

            activity.observeUserSessions(this, uid);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");

        FragmentSessionsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_sessions, container, false);

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        binding.itineraryRecyclerView.setLayoutManager(new LinearLayoutManager(mContextWeakReference.get()));
        binding.itineraryRecyclerView.setHasFixedSize(true);

        binding.itineraryRecyclerView.setAdapter(adapter);

        binding.floatingActionButton.setOnClickListener(floatingActionButtonListener(mDatabaseReference));


        return binding.getRoot();
    }

    private View.OnClickListener floatingActionButtonListener (DatabaseReference mDatabaseReference) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adaptercount = adapter.getItemCount();
                String number = String.valueOf(adaptercount+1);
                String dataSnapshotKey = "Session" + number;

                DatabaseReference reference = mDatabaseReference.child("users").child(uid).child("_open");
                reference.setValue(ServerValue.TIMESTAMP).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        reference.addListenerForSingleValueEvent(getOpenObject(dataSnapshotKey));
                    }
                });

//                reference.addListenerForSingleValueEvent(getOpenObject());

                Log.d(TAG, "onClick: number of items is: " + adaptercount);
            }
        };
    }

    private ValueEventListener getOpenObject (String dataSnapshotKey) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: open TimeStamp is: " + dataSnapshot);

                Sessions session = new Sessions(dataSnapshot.getValue(Object.class), 0);


                DatabaseReference mDBReference = FirebaseDatabase.getInstance().getReference();
                mDBReference
                        .child("sessions")
                        .child(uid)
                        .child(dataSnapshotKey)
                        .setValue(session);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

//    @Override
//    public void itineraryList(List<DataSnapshot> dataSnapshots) {
//        Log.d(TAG, "itineraryList: snaps are: " + dataSnapshots.toString());
//        adapter.submitList(dataSnapshots);
//    }

    @Override
    public void getList(List<DataSnapshot> dataSnapshot) {
        adapter.submitList(dataSnapshot);
    }

    public void updateData(String uid) {
        Log.d(TAG, "updateData: ");
        this.uid = uid;
        mContextWeakReference.get().observeUserSessions(this::getList, uid);
    }

    private SessionFragmentInterface mSessionFragmentInterface;

    @Override
    public void onItemClick(String sessionTitle, Sessions session) {
        Log.d(TAG, "onItemClick: session Title is: " + sessionTitle);
        String chatRef = uid + "/" + sessionTitle;
        Log.d(TAG, "onItemClick: chatRef is: " + chatRef);
        mSessionFragmentInterface.setSession(chatRef, session);
    }

    public void setSessionFragmentInterface(SessionFragmentInterface sessionFragmentInterface) {
        mSessionFragmentInterface = sessionFragmentInterface;
    }


//    private FlyHighActivity.ListValueListener listenUserSessions () {
//        return new FlyHighActivity.ListValueListener() {
//            @Override
//            public void getList(List<DataSnapshot> dataSnapshot) {
//                adapter.submitList(dataSnapshot);
//            }
//        };
//    }

    public interface SessionFragmentInterface {
        void setSession(String chatRef, Sessions session);
    }

}
