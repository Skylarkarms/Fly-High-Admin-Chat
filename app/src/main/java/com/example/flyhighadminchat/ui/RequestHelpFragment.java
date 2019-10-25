package com.example.flyhighadminchat.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.flyhighadminchat.R;
import com.example.flyhighadminchat.databinding.FragmentHelpRequestBinding;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;


public class RequestHelpFragment extends Fragment {

    private static final String TAG = "RequestHelpFragment";

    public static final String USER_ID = "User ID";


    public static RequestHelpFragment newInstance(String uId) {
        Log.d(TAG, "newInstance: userID is: " + uId);
        Bundle args = new Bundle();
        args.putString(USER_ID, uId);

        RequestHelpFragment fragment = new RequestHelpFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentHelpRequestBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_help_request, container, false);

        assert getArguments() != null;
        String uId = getArguments().getString(USER_ID);
        Log.d(TAG, "onCreateView: uID is: " + uId);

        DatabaseReference helpRef = FirebaseDatabase.getInstance().getReference().child("users").child(uId).child("_request");

        binding.requestHelpButton.setOnClickListener(requestHelp(helpRef));

        return binding.getRoot();

    }


    private View.OnClickListener requestHelp (DatabaseReference ref) {
        return v -> showCautionDialog("Are you sure?", ref);
    }

    private DialogInterface.OnClickListener reassuredRequestHelp(DatabaseReference ref) {
        return (dialog, which) -> sendSignal(ref);
    }


    private void sendSignal(DatabaseReference ref) {

        ref.setValue(/*TimeStampUtils.getLongTimeStamp()*//*timeStampCreated()*/ServerValue.TIMESTAMP, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(getActivity(), "Distress Signal sent", Toast.LENGTH_LONG).show();

                Log.d(TAG, "onComplete: ");

            }
        });


    }

    private Object timeStampCreated() {
        HashMap<String, Object> timestampCreated;
        timestampCreated = new HashMap<>();
        timestampCreated.put("timestamp", ServerValue.TIMESTAMP);
        return timestampCreated.get("timestamp");
    }

//    private Long timeStampCreated() {
//        String tmstmp = String.valueOf(ServerValue.TIMESTAMP);
//        return Long.parseLong(tmstmp);
//    }




    // TODO: Show error on screen with an alert dialog

    private void showCautionDialog (String s, DatabaseReference ref) {
        assert getActivity() != null;
        new AlertDialog.Builder(getActivity())
        .setTitle("You are about to send a distress signal")
        .setMessage(s)
        .setPositiveButton(android.R.string.ok, reassuredRequestHelp(ref))
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();

    }

}