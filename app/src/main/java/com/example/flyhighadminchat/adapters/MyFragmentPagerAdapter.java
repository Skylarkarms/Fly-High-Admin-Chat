package com.example.flyhighadminchat.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.flyhighadminchat.data.Sessions;
import com.example.flyhighadminchat.data.User;
import com.example.flyhighadminchat.fireBase_user_package.FlyHighUser;
import com.example.flyhighadminchat.ui.ChatFragment;
import com.example.flyhighadminchat.ui.ContainerFragment;
import com.example.flyhighadminchat.ui.SessionsFragment;
import com.example.flyhighadminchat.ui.UsersFragment;
import com.example.flyhighadminchat.ui.RequestHelpFragment;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "MyFragmentStatePagerAda";

    private RequestHelpFragment mRequestHelpFragment;
    private ChatFragment mChatFragment;
    private ContainerFragment mContainerFragment;
    private UsersFragment mUsersFragment;
    private SessionsFragment mSessionsFragment;

    String sessionTitle;
    String uid;
    Sessions session;
    String chatRef;


    private boolean clientAttended;

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm,
                                  FlyHighUser flyHighUser,
                                  UsersFragment.UserFragmentInterface userFragmentInterface,
                                  SessionsFragment.SessionFragmentInterface sessionFragmentInterface) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        Log.d(TAG, "MyFragmentStatePagerAdapter: uId is: " + flyHighUser.get_uId() + ", and username is: " + flyHighUser.get_username());

        mRequestHelpFragment = RequestHelpFragment.newInstance(flyHighUser.get_uId());
        mChatFragment = ChatFragment.newInstance(flyHighUser);
        mContainerFragment = ContainerFragment.newInstance(flyHighUser, clientAttended);
        mUsersFragment = new UsersFragment();
        mUsersFragment.setUserFragmentInterface(userFragmentInterface);
        mSessionsFragment = new SessionsFragment();
        mSessionsFragment.setSessionFragmentInterface(sessionFragmentInterface);
    }

    private static final int NUMBER_OF_FRAGMENTS = 3;

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mUsersFragment;
            case 1:
                return  mSessionsFragment;
            case 2:
                return  mChatFragment;

        }
        return null;
    }



    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENTS;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (object instanceof SessionsFragment) {
            ((SessionsFragment) object).updateData(uid);
        } else if (object instanceof ChatFragment ) {
            ((ChatFragment) object).updateData(chatRef, session);
        }
        return super.getItemPosition(object);
    }

    public void setBoolean(boolean aBoolean) {
        Log.d(TAG, "setBoolean: ");
        clientAttended = aBoolean;
        Log.d(TAG, "setBoolean: clientAttended is: " + clientAttended);
        notifyDataSetChanged();
    }

    public void setUID(String uid) {
        this.uid = uid;
        notifyDataSetChanged();

    }

    public void setSessions(String chatRef, Sessions session) {
        Log.d(TAG, "setSessions: chatRef is: " + chatRef);
        this.chatRef = chatRef;
        this.session = session;
        notifyDataSetChanged();
    }
}
