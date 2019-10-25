package com.example.flyhighadminchat.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.flyhighadminchat.R;
import com.example.flyhighadminchat.adapters.MyFragmentPagerAdapter;
import com.example.flyhighadminchat.custom_artifacts.MyViewPager;
import com.example.flyhighadminchat.data.Sessions;
import com.example.flyhighadminchat.databinding.FragmentFlyhighMainBinding;
import com.example.flyhighadminchat.drawable_engine.DrawableBank;
import com.example.flyhighadminchat.drawable_engine.MenuItemRecolorer;
import com.example.flyhighadminchat.fireBase_user_package.FlyHighUser;

import java.lang.ref.WeakReference;


public class FlyHighMainFragment extends Fragment implements FlyHighActivity.BooleanListener, UsersFragment.UserFragmentInterface, SessionsFragment.SessionFragmentInterface {

    private static final String TAG = "FlyHighMainFragment";

    private WeakReference<FlyHighActivity> mContextWeakReference;
    private MyFragmentPagerAdapter mAdapter;
    private FragmentFlyhighMainBinding binding;

    public FlyHighMainFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FlyHighActivity) {

            mContextWeakReference = new WeakReference<>((FlyHighActivity)context);

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        FlyHighActivity activity = mContextWeakReference.get();
        FlyHighUser flyHighUser = getUser();

        mAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                flyHighUser,
                this::setUID,
                this::setSession);
//        activity.observeOpenChatBoolean(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_flyhigh_main, container, false);
        binding.mainContainer.setAdapter(mAdapter);

        final View.OnClickListener menuButton = pagerMenu(binding);
        binding.page1.setOnClickListener(menuButton);
        binding.page2.setOnClickListener(menuButton);
        binding.page3.setOnClickListener(menuButton);

        final MenuItemRecolorer recolorer = new MenuItemRecolorer(getActivity());
        final DrawableBank[] bank = {
                new DrawableBank(R.drawable.circle, binding.page1),
                new DrawableBank(R.drawable.triangle, binding.page2),
                new DrawableBank(R.drawable.square, binding.page3)
        };

        recolorer
                .recolorBank(bank)
                .fromPassiveColor(R.color.colorInactive)
                .towardsActiveColor(R.color.colorActive);

        binding.mainContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                recolorer.accordingTo(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.mainContainer.setCurrentItem(1, true);
        binding.mainContainer.setCurrentItem(0, true);


        return binding.getRoot();
    }

    private View.OnClickListener pagerMenu(FragmentFlyhighMainBinding mBinding) {
        return new View.OnClickListener() {

            MyViewPager mMyViewPager = mBinding.mainContainer;
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == mBinding.page1.getId()) {
                    mMyViewPager.setCurrentItem(0, true);
                } else if (id == mBinding.page2.getId()) {
                    mMyViewPager.setCurrentItem(1, true);
                } else if (id == mBinding.page3.getId()) {
                    mMyViewPager.setCurrentItem(2, true);
                }
            }
        };
    }


    private FlyHighUser getUser() {
        SharedPreferences prefs = getActivity().getSharedPreferences(LoginActivity.CHAT_PREFS, getActivity().MODE_PRIVATE);

        String uID = prefs.getString(LoginActivity.USER_ID, null);
        String userName = prefs.getString(LoginActivity.USER_NAME, null);

        Log.d(TAG, "getUser: Strings are: " + uID + " and " + userName);

        return new FlyHighUser(uID, userName);
    }

    @Override
    public void getBoolean(boolean b) {
        Log.d(TAG, "getBoolean: ");
        mAdapter.setBoolean(b);
    }

    @Override
    public void setUID(String uid) {
        mAdapter.setUID(uid);
        mAdapter.notifyDataSetChanged();
        MyViewPager mMyViewPager = binding.mainContainer;
        mMyViewPager.setCurrentItem(1, true);
    }

    @Override
    public void setSession(String chatRef, Sessions session) {
        Log.d(TAG, "setSession: chatRef is: " + chatRef);
        mAdapter.setSessions(chatRef, session);
        mAdapter.notifyDataSetChanged();
        MyViewPager mMyViewPager = binding.mainContainer;
        mMyViewPager.setCurrentItem(2, true);

    }
}
