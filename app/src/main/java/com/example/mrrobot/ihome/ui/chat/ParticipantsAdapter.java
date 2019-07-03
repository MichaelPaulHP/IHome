package com.example.mrrobot.ihome.ui.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.Services.GlideApp;
import com.example.mrrobot.ihome.models.User;

import java.util.List;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewHolder> {

    private List<User> participants;
    private ClickListener clickListener;

    public ParticipantsAdapter() {
        super();
        //this.participants = participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.participant, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //viewHolder.imageView.set

        GlideApp
                .with(viewHolder.imageView.getContext())
                .load(participants.get(i).getAvatar())
                .centerCrop()
                .into(viewHolder.imageView);
        viewHolder.textView.setText(participants.get(i).getName());
    }

    /**
     * notifyNewChatInserted
     *
     */
    public void notifyNewItemInserted() {
        this.notifyDataSetChanged();
        //notifyItemInserted(this.participants.size() - 1);
    }

    @Override
    public int getItemCount() {
        if (this.participants == null) {
            return 0;
        }
        return this.participants.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        /**
         * event on item's click
         *
         * @param position is list
         * @param v        is view
         */
        void onItemClick(int position, View v);
        //void onItemLongClick(int position, View v);
    }

    /**
     * view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textParticipant);
            imageView = itemView.findViewById(R.id.imgParticipant);

        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }


    }
}
