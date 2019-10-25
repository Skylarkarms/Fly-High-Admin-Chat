package com.example.flyhighadminchat.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.flyhighadminchat.R;
import com.example.flyhighadminchat.databinding.FragmentContainerBinding;
import com.example.flyhighadminchat.fireBase_user_package.FlyHighUser;


public class ContainerFragment extends Fragment {
    
    private static final String TAG = "Fragment4";
    private static final String EXTRA_CLIENT_ATTENDED = "com.maidenhair.flyhigh.ui.ContainerFragment.EXTRA_CLIENT_ATTENDED";

    private boolean clientAttended;
    private int mFragmentContainerId;
    private FlyHighUser mFlyHighUser;

    public static ContainerFragment newInstance(FlyHighUser user, boolean clientAttended) {

        Log.d(TAG, "newInstance: ");
        
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_CLIENT_ATTENDED, clientAttended);
        args.putString(LoginActivity.USER_NAME, user.get_username());
        args.putString(LoginActivity.USER_ID, user.get_uId());
        
        ContainerFragment fragment = new ContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        clientAttended = getArguments().getBoolean(EXTRA_CLIENT_ATTENDED);
        String uId = getArguments().getString(LoginActivity.USER_ID);
        String userName = getArguments().getString(LoginActivity.USER_NAME);
        mFlyHighUser = new FlyHighUser(uId, userName);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
    }    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");

        FragmentContainerBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_container, container, false);

        FrameLayout fragmentContainer = (FrameLayout) binding.fragmentContainer;
        int fragmentContainerId = R.id.fragmentContainer;
        mFragmentContainerId = fragmentContainerId;
        fragmentContainer.setId(fragmentContainerId);
        
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment oldFragment = fm.findFragmentById(fragmentContainerId);
        Fragment newFragment;
        if (oldFragment != null) {
            ft.remove(oldFragment);
        }
        if (clientAttended) {
            newFragment = ChatFragment.newInstance(mFlyHighUser);
        } else {
            newFragment = RequestHelpFragment.newInstance(mFlyHighUser.get_uId());
        }
        ft.add(fragmentContainerId, newFragment);
        ft.commit();
        Log.d(TAG, "add fragment " + newFragment.getClass().getSimpleName());
        
        return binding.getRoot();
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
    }
    
    public void updateData(boolean clientAttended) {
        Log.d(TAG, "updateData: ");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (this.clientAttended != clientAttended) {
            Log.d(TAG, "replace fragment");
            this.clientAttended = clientAttended;
            if (clientAttended) {
                ft.replace(mFragmentContainerId, ChatFragment.newInstance(mFlyHighUser));
            } else {
                ft.replace(mFragmentContainerId, RequestHelpFragment.newInstance(mFlyHighUser.get_uId()));
            }
            ft.commit();        
        }
    }
}
