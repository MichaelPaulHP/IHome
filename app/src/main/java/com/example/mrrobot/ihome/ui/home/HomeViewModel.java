package com.example.mrrobot.ihome.ui.home;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.Bindable;
import android.databinding.Observable;

import android.databinding.ObservableBoolean;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mrrobot.ihome.Firebase.Auth;
import com.example.mrrobot.ihome.Services.Utils;
import com.example.mrrobot.ihome.models.Chat;
import com.example.mrrobot.ihome.models.Device;
import com.example.mrrobot.ihome.models.House;
import com.example.mrrobot.ihome.models.Message;
import com.example.mrrobot.ihome.models.User;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements Observable, User.IUserListeners,Chat.IChatListener {

    private Auth auth;
    private User user;
    public House house;
    public String isConnected;
   public ObservableBoolean hasNewMessage= new ObservableBoolean(false) ;
    //public Boolean hasNewMessage=false;


    public HomeViewModel() {

        this.auth = Auth.getInstance();
        this.user = User.getCurrentUser();
        this.user.userListeners=this;
        this.house = House.getInstance();
        //this.user.requestMyChats();
        Log.i("HomeVM","HomeViewModel constructor");
        isConnected = this.house.isConnected() ? "conectado" : "sin conexion";


    }




    @Bindable
    public String getUserName() {
        return this.auth.getUserName();
    }


    public Device getDeviceByName(String x){
        return this.house.findDeviceByName(x);
    }
    /*public String isConnected(){
        return this.house.isConnected()? "conectado":"si conexio";

    }*/




    public List<Device> getDevices() {
        return this.house.devicesList;
    }
    public List<Device> getINPUTSdevices(){
        List<Device> devices = new ArrayList<>();
        for(Device x :this.house.devicesList){
            if(!x.getMode().equals("OUTPUT")){
                devices.add(x);
            }
        }
        return devices;
    }


    // funciona homeVM.house.count


    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {

    }

    /**
     * when this user join to chat
     */
    @Override
    public void onJoinToChat() {

    }

    @Override
    public void onAddChat(Chat chat) {
        chat.chatEventManager.subscribe(this);
    }

    /**
     * new message x in chat chat
     *
     * @param chat chat
     * @param x    message
     */
    @Override
    public void onNewMessage(Chat chat, Message x) {
        //this.hasNewMessage=true;
        this.hasNewMessage.set(true);
        Log.i("HomeVM","onNewMessage"+x.getText());
    }

    @Override
    public void onNewParticipant() {

    }
}
