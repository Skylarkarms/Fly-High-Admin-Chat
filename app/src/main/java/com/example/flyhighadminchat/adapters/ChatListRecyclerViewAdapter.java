package com.example.flyhighadminchat.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flyhighadminchat.R;
import com.example.flyhighadminchat.data.InstantMessage;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class ChatListRecyclerViewAdapter extends ListAdapter<DataSnapshot, RecyclerView.ViewHolder> {

    private static final String TAG = "ItineraryRecyclerViewAd";

    private LayoutInflater layoutInflater;
    private String mDisplayName;
    private int ME = 0;


    public ChatListRecyclerViewAdapter(String displayName) {
        super(DIFF_CALLBACK);
        Log.d(TAG, "UsersRecyclerViewAdapter: ");

        this.mDisplayName = displayName;
    }

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

    public InstantMessage getMessageItem(int position) {
        DataSnapshot snapshot = getItem(position);
        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public int getItemViewType(int position) {

        int AGENT = 1;
        return isItMe(position)? ME : AGENT;


    }

    private boolean isItMe(int position) {
        return getMessageItem(position).getAuthor().equals(mDisplayName);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        View v = layoutInflater.inflate(R.layout.chat_msg_row, parent, false);
        final ViewHolder holder = new ViewHolder(v);

        setChatRowAppearance(viewType, holder);

        return holder;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            authorName = itemView.findViewById(R.id.author);
            body = itemView.findViewById(R.id.message);

            params = (LinearLayout.LayoutParams)authorName.getLayoutParams();

        }
    }

    private void setChatRowAppearance(int isItMe, ViewHolder holder) {

        if (isItMe == ME) {

            Log.d(TAG, "setChatRowAppearance: ME: Gravity.END");
            holder.params.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.GREEN);

            // If you want to use colours from colors.xml
            // int colourAsARGB = ContextCompat.getColor(mActivity.getApplicationContext(), R.color.yellow);
            // holder.authorName.setTextColor(colourAsARGB);

            holder.body.setBackgroundResource(R.drawable.bubble2);
        } else {

            Log.d(TAG, "setChatRowAppearance: AGENT: Gravity.START");

            holder.params.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }

        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final InstantMessage message = getMessageItem(position);

        ((ViewHolder) holder).authorName.setText(message.getAuthor());
        ((ViewHolder) holder).body.setText(message.getMessage());
    }

}
