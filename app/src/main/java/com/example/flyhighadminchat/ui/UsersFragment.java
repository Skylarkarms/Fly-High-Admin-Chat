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
import com.example.flyhighadminchat.adapters.MyFragmentPagerAdapter;
import com.example.flyhighadminchat.adapters.UsersRecyclerViewAdapter;
import com.example.flyhighadminchat.data.User;
import com.example.flyhighadminchat.databinding.FragmentUsersBinding;
import com.google.firebase.database.DataSnapshot;

import java.lang.ref.WeakReference;
import java.util.List;


public class UsersFragment extends Fragment implements FlyHighActivity.ListWatcherListener, UsersRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = "UsersFragment";


    private WeakReference<FlyHighActivity> mContextWeakReference;
    private UsersRecyclerViewAdapter adapter;
    private FragmentUsersBinding binding;



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

        adapter = new UsersRecyclerViewAdapter();
        adapter.setOnItemClickListener(this::onItemClick);
        FlyHighActivity activity = mContextWeakReference.get();

        activity.observeUsers(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");

         binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_users, container, false);



        binding.itineraryRecyclerView.setLayoutManager(new LinearLayoutManager(mContextWeakReference.get()));
        binding.itineraryRecyclerView.setHasFixedSize(true);

        binding.itineraryRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

//    @Override
//    public void itineraryList(List<DataSnapshot> dataSnapshots) {
//        Log.d(TAG, "itineraryList: snaps are: " + dataSnapshots.toString());
//        adapter.submitList(dataSnapshots);
//    }

    @Override
    public void getList(List<DataSnapshot> dataSnapshot) {
        adapter.submitList(dataSnapshot);
        binding.itineraryRecyclerView.smoothScrollToPosition(0);

    }

    public void setUserFragmentInterface(UserFragmentInterface pagerAdapterInterface) {
        mUserFragmentInterface = pagerAdapterInterface;
    }

    private UserFragmentInterface mUserFragmentInterface;

    @Override
    public void onItemClick(String uID) {
        mUserFragmentInterface.setUID(uID);
    }

    public interface UserFragmentInterface {
        void setUID(String uid);
    }

}
