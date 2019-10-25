package com.example.flyhighadminchat.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flyhighadminchat.R;
import com.example.flyhighadminchat.data.User;
import com.example.flyhighadminchat.databinding.ListItemUsersBinding;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class UsersRecyclerViewAdapter extends ListAdapter<DataSnapshot, RecyclerView.ViewHolder> {

    private static final String TAG = "ItineraryRecyclerViewAd";

    private OnItemClickListener listener;

    public UsersRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
        Log.d(TAG, "UsersRecyclerViewAdapter: ");
    }

    private LayoutInflater layoutInflater;

    private static final DiffUtil.ItemCallback<DataSnapshot> DIFF_CALLBACK = new DiffUtil.ItemCallback<DataSnapshot>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataSnapshot oldItem, @NonNull DataSnapshot newItem) {
            return oldItem.getKey() == newItem.getKey();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DataSnapshot oldItem, @NonNull DataSnapshot newItem) {
                    return oldItem.getValue() == newItem.getValue();
        }
    };

    @Override
    public void submitList(@Nullable List<DataSnapshot> list) {
        Log.d(TAG, "submitList: list is: " + list.toString());

        notifyDataSetChanged();
        super.submitList(list);
    }


    @Override
    protected DataSnapshot getItem(int position) {
        return super.getItem(position);
    }

    private User getCustomItem(int position) {
        return (getItem(position)).getValue(User.class);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ListItemUsersBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.list_item_users,
                parent,
                false);

        return new UserHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: getItem is: " + getCustomItem(position));

        ((UserHolder) holder).bind(getCustomItem(position), createClickListener(getItem(position)));
    }

    private View.OnClickListener createClickListener(DataSnapshot snapshot) {
        return view -> {
            String uID = snapshot.getKey();
            Log.d(TAG, "createClickListener: uID is: " + uID);
            listener.onItemClick(uID);
        };
    }

    class UserHolder extends RecyclerView.ViewHolder {

        ListItemUsersBinding binding;

        public UserHolder(@NonNull ListItemUsersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(User info, View.OnClickListener onClickListener) {
            binding.setUserInfo(info);
            binding.setClickListener(onClickListener);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(String uID);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



}
