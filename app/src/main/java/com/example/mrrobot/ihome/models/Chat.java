package com.example.mrrobot.ihome.models;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mrrobot.ihome.Firebase.DB.ChatData;
import com.example.mrrobot.ihome.Firebase.DB.MessageData;
import com.example.mrrobot.ihome.Firebase.DB.UserData;
import com.example.mrrobot.ihome.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

@IgnoreExtraProperties
public class Chat {

    private String name;
    private String createdBy;
    private Date createdAt;
    private String key;
    private Double numOfParticipants = 0.0;
    private Double numOfMessage = 0.0;

    private List<Message> messages = new ArrayList<>();
    private List<User> participants = new ArrayList<>();

    //public IChatListener chatListener;
    public ChatEventManager chatEventManager= new ChatEventManager();


    public int icon = R.drawable.ic_location_on_black_24dp;

    public Chat() {

    }

    public Chat(String name, String createdBy) {
        this.name = name;
        this.createdBy = createdBy;
        this.numOfParticipants = 0.0;
        this.numOfMessage = 0.0;
        this.createdAt = Calendar.getInstance().getTime();
    }

    public void saveMessage(final Message message) {
        MessageData.saveMessage(this.key, this.numOfMessage, message);
    }

    public void requestMyMessages() {
        ChatData.getMyMessages(this.key, myMessagesSaved);
    }

    public void setListenerForNewMessagesFromDB() {
        ChatData.setListenerOnNewMessage(this.key, onNewMessageListener);
    }

    public void requestParticipants(){
        ChatData.getParticipants(this.key,getParticipantsFromDB);
    }



    /**
     * add a message in list
     */
    public void addMessage(Message message) {
        try{
        this.messages.add(message);
        this.numOfMessage=numOfMessage+1;
        //chatListener.onNewMessage(Chat.this, message);
        this.chatEventManager.notifyNewMessage(this,message);
        }catch (NullPointerException e){
            Timber.tag("CHAT").w(e);
        }
    }

    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * add a user in list
     *
     * @param user
     */
    public void addParticipants(final User user) {

        if (this.numOfParticipants == null) {
            this.numOfParticipants = 0.0;
        }
        this.participants.add(user);
        this.numOfParticipants = numOfParticipants + 1;
        //this.chatListener.onNewParticipant();
        this.chatEventManager.notifyNewParticipantAdded();
        // add message type LOG
    }

    public void saveThisChat() {
        ChatData.saveChat(this);
    }


    public User findParticipantByIdGoogle(String id) {
        for (User user : participants) {
            if (user.getIdGoogle().equals(id)) {
                return user;
            }
        }
        return null;
    }



    private void addMessageToListFromDB(MessageData messageData) {
        Message message;
        // get  a Message clone
        if (isMyMessage(messageData.userId)) {
            message = MessagePrototypeFactory.getPrototype("myMessage");
        } else {
            message = MessagePrototypeFactory.getPrototype("otherMessage");
            User user = findParticipantByIdGoogle(messageData.userId);
            message.setUser(user);
        }

        message.setCreateAt(new Date(messageData.createAtLong));
        message.setText(messageData.text);
        Chat.this.addMessage(message);
    }

    ChildEventListener onNewMessageListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            MessageData messageData = dataSnapshot.getValue(MessageData.class);
            Log.i("CHAT","onNewMessageListener");
            addMessageToListFromDB(messageData);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ///////////////////////////////////////////////////////
    /////////////// Get messages from DB
    /////////////////////////////////////////////
    private ValueEventListener myMessagesSaved = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                MessageData messageData = postSnapshot.getValue(MessageData.class);
                Log.i("CHAT","myMessagesSaved");
                addMessageToListFromDB(messageData);

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private boolean isMyMessage(String userId) {
        return userId.equals(User.getCurrentUser().getIdGoogle());
    }


    ///////////////////////////////////////////////////////
    /////////////// Get participants from DB
    /////////////////////////////////////////////

    ValueEventListener getParticipantsFromDB = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot p : dataSnapshot.getChildren()) {
                UserData userData =p.getValue(UserData.class);
                User user=userData.toUser();
                Chat.this.addParticipants(user);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    ///////////////////////////////////////////////////////
    /////////////// GETTERS
    /////////////////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Double getNumOfParticipants() {
        return numOfParticipants;
    }

    public void setNumOfParticipants(Double numOfParticipants) {
        this.numOfParticipants = numOfParticipants;
    }

    public Double getNumOfMessage() {
        return numOfMessage;
    }

    public void setNumOfMessage(Double numOfMessage) {
        this.numOfMessage = numOfMessage;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<User> getParticipants() {
        return participants;
    }

    @Override
    public boolean equals(Object obj) {
        Chat a = (Chat) obj;
        return this.key.equals(a.key);
    }

    public interface IChatListener{
        /**
         * new message x in chat chat
         *
         * @param chat chat
         * @param x    message
         */
        void onNewMessage(Chat chat, Message x);
        void onNewParticipant();

    }

}
