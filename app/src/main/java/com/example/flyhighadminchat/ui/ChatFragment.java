package com.example.flyhighadminchat.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.flyhighadminchat.R;
import com.example.flyhighadminchat.adapters.ChatListRecyclerViewAdapter;
import com.example.flyhighadminchat.data.InstantMessage;
import com.example.flyhighadminchat.data.Sessions;
import com.example.flyhighadminchat.databinding.FragmentChatBinding;
import com.example.flyhighadminchat.fireBase_user_package.FlyHighUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.lang.ref.WeakReference;
import java.util.List;


public class ChatFragment extends Fragment implements FlyHighActivity.ListWatcherListener {

    private static final String TAG = "ChatFragment";

    private FlyHighUser mFlyHighUser;
    private WeakReference<FlyHighActivity> mContextWeakReference;
    private ChatListRecyclerViewAdapter adapter;

    private FragmentChatBinding binding;

    private String chatRef;
    private Sessions session;
//    private String userID;


    public static ChatFragment newInstance(FlyHighUser flyHighUser) {

        Bundle args = new Bundle();
        args.putString(LoginActivity.USER_NAME, flyHighUser.get_username());
        args.putString(LoginActivity.USER_ID, flyHighUser.get_uId());

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
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
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        String userName = getArguments().getString(LoginActivity.USER_NAME);
        String uId = getArguments().getString(LoginActivity.USER_ID);
        
        mFlyHighUser = new FlyHighUser(uId, userName);

        FlyHighActivity activity = mContextWeakReference.get();

        adapter = new ChatListRecyclerViewAdapter(mFlyHighUser.get_username());


//        activity.observeListChatMessages(this);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_chat, container, false);

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        binding.chatListView.setLayoutManager(new LinearLayoutManager(mContextWeakReference.get()));
        binding.chatListView.setHasFixedSize(true);
        
        binding.chatListView.setAdapter(adapter);

        binding.messageInput.setOnEditorActionListener(editorAction(binding, chatRef, mDatabaseReference));
        binding.sendButton.setOnClickListener(sendMessageButton(binding, chatRef, mDatabaseReference));


        return binding.getRoot();
    }

    private EditText.OnEditorActionListener editorAction (FragmentChatBinding binding,
                                                          String chatRef,
                                                          DatabaseReference mDatabaseReference) {
        return (v, actionId, event) -> {
            sendMessage(binding, chatRef, mDatabaseReference);
            return true;
        };
    }

    private View.OnClickListener sendMessageButton(FragmentChatBinding binding,
                                                   String chatRef,
                                                   DatabaseReference mDatabaseReference) {
        return v -> sendMessage(binding, chatRef, mDatabaseReference);
    }

    private void sendMessage(FragmentChatBinding binding,
                             String chatRefs,
                             DatabaseReference mDatabaseReference) {
        Log.d(TAG, "sendMessage: chatRef is: " + chatRef);

        String input = binding.messageInput.getText().toString();
        Log.d(TAG, "sendMessage: Message is " + input);
        if (!input.equals("")) {
            InstantMessage chat = new InstantMessage(input, "admin", ServerValue.TIMESTAMP,0);
            mDatabaseReference
                    .child("chats").child(chatRef)
                    .push()
                    .setValue(chat);
        }
        binding.messageInput.setText(null);

    }

//    private Object timeStampCreated() {
//        HashMap<String, Object> timestampCreated;
//        timestampCreated = new HashMap<>();
//        timestampCreated.put("timestamp", ServerValue.TIMESTAMP);
//        return timestampCreated.get("timestamp");
//    }


    @Override
    public void getList(List<DataSnapshot> dataSnapshot) {
        Log.d(TAG, "getList: snapshot is: " + dataSnapshot);
        adapter.submitList(dataSnapshot);
        binding.chatListView.smoothScrollToPosition(adapter.getItemCount());
    }

    public void updateData(String chatRef, Sessions session) {
        Log.d(TAG, "updateData: chatRef is: " + chatRef);
        this.chatRef = chatRef;
        this.session = session;
//        this.userID = userID;
        mContextWeakReference.get().observeUserChat(getChat(), chatRef);

    }

    private FlyHighActivity.ListWatcherListener getChat() {
        return new FlyHighActivity.ListWatcherListener() {
            @Override
            public void getList(List<DataSnapshot> dataSnapshot) {
                adapter.submitList(dataSnapshot);
                binding.chatListView.smoothScrollToPosition(adapter.getItemCount());
            }
        };
    }
}
