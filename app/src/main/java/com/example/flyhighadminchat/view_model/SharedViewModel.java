package com.example.flyhighadminchat.view_model;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.flyhighadminchat.fireBase_user_package.FlyHighUser;
import com.example.flyhighadminchat.firebase_package.firebase_boolean_watcher.BooleanDataSnapshot;
import com.example.flyhighadminchat.firebase_package.firebase_list_router.AmmoTable;
import com.example.flyhighadminchat.firebase_package.firebase_list_router.DataSnapshotLiveData;
import com.example.flyhighadminchat.firebase_package.firebase_list_router.QueriesBank;
import com.example.flyhighadminchat.firebase_package.firebase_list_watcher.ListValueDataSnapshot;
import com.example.flyhighadminchat.firebase_package.firebase_list_watcher.ListWatcherDataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class SharedViewModel extends ViewModel {

    private static final String TAG = "SharedViewModel";

    private QueriesBank[] queries;
    private DataSnapshotLiveData itinerariosData;
    private BooleanDataSnapshot openChatFrag;
    private ListWatcherDataSnapshot chatListSnapshots;

    private ListWatcherDataSnapshot users;

//    private ListWatcherDataSnapshot userSessions;
    private ListWatcherDataSnapshot userSessions;

    public SharedViewModel(/*FlyHighUser flyHighUser*/) {

        Log.d(TAG, "SharedViewModel: ");

//        queries = new QueriesBank[] {
//                new QueriesBank(FirebaseDatabase.getInstance().getReference(flyHighUser.get_uId()).child("actividad"), AmmoTable.CHILDS),
//                new QueriesBank(FirebaseDatabase.getInstance().getReference(flyHighUser.get_uId()).child("infodevuelo"), AmmoTable.CHILDS)
//
//        };
//
//        itinerariosData = new DataSnapshotLiveData(queries);
//        itinerariosData.arrangeByDate("_fecha", "dd-M-yyyy");
//
//        openChatFrag = new BooleanDataSnapshot().inside("users/" + flyHighUser.get_uId() + "/_open");
//
//        chatListSnapshots = new ListWatcherDataSnapshot().inside("chats/" + flyHighUser.get_uId());

        users = new ListWatcherDataSnapshot().inside("users");


    }

    public DataSnapshotLiveData getItinerariosData() {
        return itinerariosData;
    }

    public BooleanDataSnapshot getOpenChatBoolean() {
        return openChatFrag;
    }

    public ListWatcherDataSnapshot getChatListSnapshots(String chatRef) {
        chatListSnapshots = new ListWatcherDataSnapshot().inside("chats/" + chatRef);
        return chatListSnapshots;
    }

    public ListWatcherDataSnapshot getUsers() {
        return users;
    }

    public ListWatcherDataSnapshot getUserSessions(String userId) {
        userSessions = new ListWatcherDataSnapshot().inside("sessions/" + userId);
        return userSessions;
    }

}
