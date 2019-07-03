package com.example.mrrobot.ihome.models;

import java.util.ArrayList;
import java.util.List;

public class ChatEventManager {

    private List<Chat.IChatListener> listeners;

    public ChatEventManager() {
        this.listeners = new ArrayList<>();
    }

    public void subscribe(Chat.IChatListener listener) {
        this.listeners.add(listener);
    }

    public void unsubscribe(Chat.IChatListener listener) {
        this.listeners.remove(listener);
    }

    public void notifyNewMessage(Chat chat,Message message){
        for (Chat.IChatListener x: listeners) {
            x.onNewMessage(chat,message);
        }
    }
    public void notifyNewParticipantAdded(){
        for (Chat.IChatListener x: listeners) {
            x.onNewParticipant();
        }
    }

    /*private List<T> listeners;

    public ChatEventManager() {
        this.listeners = new ArrayList<>();
    }

    public void subscribe(T listener) {
        this.listeners.add(listener);
    }

    public void unsubscribe(T listener) {
        this.listeners.remove(listener);
    }

    public void notifyChange(){
        for (T x: listeners) {
            x.notifyChange();
        }
    }*/

}
