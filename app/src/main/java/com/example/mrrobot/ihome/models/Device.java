package com.example.mrrobot.ihome.models;

import android.arch.lifecycle.LiveData;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.Services.SocketIO;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import com.example.mrrobot.ihome.BR;
import com.github.zagum.switchicon.SwitchIconView;

public class Device extends BaseObservable {

    private Boolean isActive=false; // on  or off ??
    private String name; // example: led
    private String mode; // example: output: led  or input: temperature
    private Socket socketIO; // for emits events
    private int icon; // icon of device
    private String message; // example: last move detect is 8:00 pm
    private stateDevice state; // connect || disconnect || warning
    private String value; // example: output: on|off  or input: 30

    private String unit; // °C | W | %
    public Device(String name) {
        this.name = name;
    }

    public Device(String name, String mode, int icon) {
        this.name = name;
        this.mode = mode;

        this.socketIO = SocketIO.getSocket();
        this.icon = icon;
        this.message = "not change";
        this.state = stateDevice.DISCONNECT;
        this.value="false";
    }


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) throws JSONException {
        // emit event
        JSONObject toSend = new JSONObject();
        toSend.put("data", "hola mundo");// {data:holamundo}
        this.socketIO.emit("changeStare", toSend);

        // on changeStateComplete
        //isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }




    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;

    }
    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }
    public void changeValueBoolean(){
        String x = this.value.equals("true")? "false": "true";
        this.setValue(x);
    }
    public boolean valueToBoolean(){
        return this.value.equals("true");
    }
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {

        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    @Override
    public boolean equals(Object obj) {
        Device x = (Device)obj;
        return this.name.equals(x.getName());
    }

    @Override
    public String toString() {
        String str="name: "+this.name;
        return str;
    }

    @Bindable
    public int getIconState() {
        int icon = R.drawable.ic_portable_wifi_off_black_24dp;

        switch (this.state) {
            case CONNECTED:
                icon = R.drawable.ic_check_black_24dp;
                break;
            case WARNING:
                icon = R.drawable.ic_warning_black_48dp;
                break;
            case DISCONNECT:
                icon = R.drawable.ic_portable_wifi_off_black_24dp;
                break;


        }
        return icon;
    }
    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }
    @BindingAdapter("app:si_enabled")
    public static void setSi_enabled(SwitchIconView sw, boolean value){
        sw.setIconEnabled(value);
    }
    @BindingAdapter("app:srcCompat")
    public static void setSrcCompat(SwitchIconView sw, int value){
        sw.setImageResource(value);
    }
    public void setState(stateDevice state) {
        this.state = state;
        notifyPropertyChanged(BR.iconState);
    }
}
