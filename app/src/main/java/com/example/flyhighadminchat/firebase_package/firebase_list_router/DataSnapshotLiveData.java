package com.example.flyhighadminchat.firebase_package.firebase_list_router;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DataSnapshotLiveData extends LiveData<List<DataSnapshot>> {

    private static final String TAG = "DataSnapshotLiveData";

    private Captain mCaptain;

    private LiveDataListListener listListener;


    public void setVal(DataSnapshot dataSnapshot) {

        Log.d(TAG, "setVal: o is: " + dataSnapshot.toString());
        if (getValue() == null) {
            ArrayList<DataSnapshot> snapshots = new ArrayList<>();
            snapshots.add(dataSnapshot);
            setValue(snapshots);
        } else {
            getValue().add(dataSnapshot);
        }
    }

    public DataSnapshotLiveData(QueriesBank[] queriesBank) {
        Log.d(TAG, "DataSnapshotLiveData: queries");
        this.mCaptain = new Captain(queriesBank);

    }

    public DataSnapshotLiveData() {
        Log.d(TAG, "DataSnapshotLiveData: ");
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive: ");

        if (listListener != null) {
            mCaptain.runThisLittleBitchOnComplete(this, new BulletSupplier.FirebaseRouterInterface() {
                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete: LiveData value is: " + getValue().toString());
                    listListener.onFullList(getValue());


                }
            });
        } else {
        mCaptain.runThisLittleBitch(this);
        }

    }

    private DateArranger mArranger;

    public void rearrange() {
        if (mArranger != null) {
            setValue(rearrangedList(getValue(), mArranger.dateFormat, mArranger.childName));
        }
    }


    private class DateArranger {

        String childName;
        String dateFormat;

        DateArranger(String childName, String dateFormat) {
            this.childName = childName;
            this.dateFormat = dateFormat;
        }
    }

    public void arrangeByDate(String childName, String dateFormat) {
        mArranger = new DateArranger(childName, dateFormat);
    }



    private List<DataSnapshot> rearrangedList(List<DataSnapshot> list, String dateFormatPattern, String childName) {
        List<DataSnapshot> mSnapshots = list;
        Collections.sort(mSnapshots, new Comparator<DataSnapshot>() {
            @Override
            public int compare(DataSnapshot dataSnapshot, DataSnapshot t1) {
                return getSnapshotDate(dataSnapshot.child(childName).getValue(String.class), dateFormatPattern).compareTo(getSnapshotDate(t1.child(childName).getValue(String.class), dateFormatPattern));
            }
        });
        return mSnapshots;
    }

    @SuppressLint("NewApi")
    private Date getSnapshotDate(String sDate, String dateFormatPattern) {
        try {
            return (new SimpleDateFormat(dateFormatPattern)).parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onInactive() {
        if (getValue() != null) {
        getValue().clear();
        }
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<DataSnapshot>> observer) {
        super.observe(owner, observer);
    }

    public void setLiveDataListListener(LiveDataListListener listListener) {
        this.listListener = listListener;
    }

    public interface LiveDataListListener {
        void onFullList(List<DataSnapshot> snapshots);
    }


}
