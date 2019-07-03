package com.example.mrrobot.ihome.ui.chat;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrrobot.ihome.R;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class ChatFragment extends Fragment {

    // Attributes

    private ChatViewModel chatViewModel; // this is ViewModel

    private MessagesList messagesList; // list of messages
    private MessageInput inputMessage;
    private RecyclerView participantsRecyclerView;
    // End Attributes



    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.chat_fragment, container, false);

        this.messagesList = (MessagesList) view.findViewById(R.id.messagesList);
        this.inputMessage = (MessageInput) view.findViewById(R.id.input);
        this.participantsRecyclerView = view.findViewById(R.id.recyclerViewListParticipants);


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        initListOfMessages();
        initMessageInput();
        initRecyclerViewAdapter();

    }

    private void initRecyclerViewAdapter() {

        this.participantsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.participantsRecyclerView.setAdapter(this.chatViewModel.participantsAdapter);
    }

    private void initListOfMessages() {
        this.messagesList.setAdapter(this.chatViewModel.getMessagesAdapter());
    }

    private void initMessageInput() {

        inputMessage.setInputListener(this.chatViewModel);
        inputMessage.setTypingListener(this.chatViewModel);
        inputMessage.setAttachmentsListener(this.chatViewModel);
    }

}
