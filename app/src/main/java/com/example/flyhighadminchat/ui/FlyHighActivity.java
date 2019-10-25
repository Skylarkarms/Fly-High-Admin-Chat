package com.example.flyhighadminchat.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.flyhighadminchat.R;
import com.example.flyhighadminchat.data.Sessions;
import com.example.flyhighadminchat.databinding.FlyhighActivityBinding;
import com.example.flyhighadminchat.fireBase_user_package.FlyHighUser;
import com.example.flyhighadminchat.fireBase_user_package.UserFactory;
import com.example.flyhighadminchat.view_model.MyViewModelFactory;
import com.example.flyhighadminchat.view_model.SharedViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.lang.ref.WeakReference;
import java.util.List;

public class FlyHighActivity extends AppCompatActivity {

    private static final String TAG = "FlyHighActivity";

    public FlyHighActivity() {
    }

    private WeakReference<DrawerLayout> mDrawerLayoutWeakReference;
    private NavController mNavController;

    private SharedViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







        FlyhighActivityBinding binding = DataBindingUtil.setContentView(this,
                R.layout.flyhigh_activity);

        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FlyHighUser flyHighUser = getUser();

        if (auth.getUid() == null /*|| flyHighUser == null*/) {
            Intent intent = new Intent(FlyHighActivity.this, LoginActivity.class);

            finish();
            startActivity(intent);

        } else {

            Log.d(TAG, "onCreate: uid is: " + auth.getUid());

            auth.addAuthStateListener(authStateListener);

//            String uid = auth.getUid();




//            Log.d(TAG, "onCreate: Uid is: " + flyHighUser.get_uId());
//            Log.d(TAG, "onCreate: userName is: " + flyHighUser.get_username());

//            UserNotif userNotif = UserFactory.getInstance().getNewUserNotif(null, null);
//
//            UserCheckAndPush checkAndPush = new UserCheckAndPush();
//            checkAndPush.setOnCompleteInterface();
//            checkAndPush.inside("users/" + flyHighUser.get_uId())
//                    .checkForField("_request")
//                    .thenPush(userNotif);































            mDrawerLayoutWeakReference = new WeakReference<>(binding.drawerLayout);


            setSupportActionBar(binding.toolbar);
            mNavController = Navigation.findNavController(this, R.id.flyhigh_nav_fragment);
            NavigationUI.setupActionBarWithNavController(this, mNavController, mDrawerLayoutWeakReference.get());
            NavigationUI.setupWithNavController(binding.navigationView, mNavController);


            binding.navigationView.setNavigationItemSelectedListener(onItemSelected(auth));

            MyViewModelFactory factory = new MyViewModelFactory(/*uid*/);
            viewModel = ViewModelProviders.of(this, factory).get(SharedViewModel.class);
//            viewModel.getItinerariosData().setLiveDataListListener(onFullItineraryList());

        }

    }

    public void observeUsers (ListWatcherListener listener) {

        viewModel.getUsers().observe(this, new Observer<List<DataSnapshot>>() {
            @Override
            public void onChanged(List<DataSnapshot> snapshots) {
                listener.getList(snapshots);
            }
        });
    }

    public void pagerAdapterHandler (String uid) {

    }

    public void observeUserSessions (ListWatcherListener listWatcherListener, String userID) {

        if (userID != null) {
            viewModel.getUserSessions(userID).observe(this, new Observer<List<DataSnapshot>>() {
                @Override
                public void onChanged(List<DataSnapshot> snapshots) {
                    listWatcherListener.getList(snapshots);
                }
            });

        }
    }

    public void observeUserChat (ListWatcherListener listWatcherListener, String chatRef) {
        Log.d(TAG, "observeUserChat: chatRef is: " + chatRef);

        viewModel.getChatListSnapshots(chatRef).observe(this, new Observer<List<DataSnapshot>>() {
            @Override
            public void onChanged(List<DataSnapshot> snapshots) {
                listWatcherListener.getList(snapshots);
            }
        });
    }


//    public void observeOpenChatBoolean(BooleanListener listener) {
//        Log.d(TAG, "observeOpenChatBoolean: ");
//
//        viewModel.getOpenChatBoolean().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                listener.getBoolean(aBoolean);
//            }
//        });
//    }

//    public void observeListChatMessages (ListWatcherListener listener) {
//
//        Log.d(TAG, "getChatMessages: ");
//
//        viewModel.getChatListSnapshots().observe(this, new Observer<List<DataSnapshot>>() {
//            @Override
//            public void onChanged(List<DataSnapshot> snapshots) {
//                listener.getList(snapshots);
//            }
//        });
//    }


//    public void observeAllItineraryItems(ListListener listener) {
//
//        Log.d(TAG, "observeAllItineraryItems: ");
//
//        viewModel.getItinerariosData().observe(this, new Observer<List<DataSnapshot>>() {
//            @Override
//            public void onChanged(List<DataSnapshot> dataSnapshots) {
//                Log.d(TAG, "onChanged: datasnapshots are: " + dataSnapshots.toString());
//                listener.itineraryList(dataSnapshots);
//            }
//        });
//
//    }









//    private DataSnapshotLiveData.LiveDataListListener onFullItineraryList(ListListener listener) {
//        return new DataSnapshotLiveData.LiveDataListListener() {
//            @Override
//            public void onFullList(List<DataSnapshot> snapshots) {
//                Log.d(TAG, "onFullList: itinerayFullList is: " + snapshots.toString());
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        listener.itineraryList(snapshots);
//
//                    }
//                });
//
//            }
//        };
//    }



    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mDrawerLayoutWeakReference.get());
    }

    @Override
    public void onBackPressed() {

        Log.d(TAG, "onBackPressed: ");
        if (mDrawerLayoutWeakReference.get().isDrawerOpen(GravityCompat.START)) {
            Log.d(TAG, "onBackPressed: Drawer Open");
            mDrawerLayoutWeakReference.get().closeDrawer(GravityCompat.START);
            Log.d(TAG, "onBackPressed: Drawer Closed because of Back Pressed");
        } else {
            Log.d(TAG, "onBackPressed: Drawer Closed continue with Back Pressed");
            super.onBackPressed();
        }
    }

    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
            FirebaseUser firebaseUser = auth.getCurrentUser();
            if (firebaseUser == null) {
                Log.d(TAG, "onAuthStateChanged: ");
                Intent intent = new Intent(FlyHighActivity.this, LoginActivity.class);

                finish();
                startActivity(intent);
            }
        }
    };


    private NavigationView.OnNavigationItemSelectedListener onItemSelected(FirebaseAuth auth) {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.log_out_button){
                    Log.d(TAG, "onNavigationItemSelected: signed out");
                    auth.signOut();
                }
                return false;
            }
        };
    }

//    private FlyHighUser getUser() {
//        SharedPreferences prefs = getSharedPreferences(LoginActivity.CHAT_PREFS, MODE_PRIVATE);
//
//        String uID = prefs.getString(LoginActivity.USER_ID, null);
//        String userName = prefs.getString(LoginActivity.USER_NAME, null);
//
//        Log.d(TAG, "getUser: Strings are: " + uID + " and " + userName);
//
//        return UserFactory.getInstance().getNewFlyHighUser(uID, userName);
//    }

    public interface ListListener {
        void itineraryList(List<DataSnapshot> dataSnapshots);
    }

    public interface BooleanListener {
        void getBoolean(boolean b);
    }

    public interface ListWatcherListener {
        void getList(List<DataSnapshot> dataSnapshot);
    }

    public interface ListValueListener {
        void getList(List<DataSnapshot> dataSnapshot);
    }

    public interface SingleSnapshotFromListListener {
        void getSnapFromList(DataSnapshot dataSnapshot);
    }

}
