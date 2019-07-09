package com.example.mrrobot.ihome.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.mrrobot.ihome.BR;
import com.example.mrrobot.ihome.Config.DevicePool;
import com.example.mrrobot.ihome.Services.SocketIO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class House extends BaseObservable {

    private static House instance;
    //private Device[] devices;
    private Socket socketIO;
    private boolean isConnected;
    //public  ObservableArrayMap<String, Device> devices = new ObservableArrayMap<>();
    public List<Device> devicesList = new ArrayList<>();


    private House() {
        this.isConnected = false;

        // updateDevices
        updateDevices();

        this.socketIO = SocketIO.getSocket();
        // define events
        defineEvents();
    }

    public static House getInstance() {
        if (instance == null) {
            instance = new House();
        }
        return instance;
    }

    private void defineEvents() {
        this.socketIO.on("changeStateHouse", onChangeStateHouse);
        this.socketIO.on("changeState", onChangeStateOfDevice);
    }

    private void updateDevices() {

        /*for (Device device : DevicePool.getArray) {
            this.devices.put(device.getUserName(), device);

        }*/
        this.devicesList.addAll(Arrays.asList(DevicePool.devices));
    }
    public Device findDeviceByName(String x){

        for (Device device : this.devicesList) {
            if(device.getName().equals(x)){
                return device;
            }

        }
        return null;
    }
    public Device findDevice(int x){
        return  this.devicesList.get(x);
    }
    private Emitter.Listener onChangeStateHouse = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONObject data = (JSONObject) args[0];
                String state = data.getString("state");
                setConnected(state.equals("true"));

            } catch (JSONException e) {
                return;
            }
        }
    };
    /*private Emitter.Listener onChangeState = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONObject data = (JSONObject) args[0];
                String state = data.getString("state");
                // find device in data

            } catch (JSONException e) {
                return;
            }
        }
    };*/

    public void disconnect() {
        SocketIO.getSocket().disconnect();
    }

    @Bindable
    public boolean isConnected() {
        return isConnected;
    }

    private void setConnected(boolean connected) {
        isConnected = connected;
        notifyPropertyChanged(BR.connected);
    }
    private boolean toBoolean(String x){
        if(x.equals("0")){
            return false;
        }
        else{return true;}
        //return Boolean.parseBoolean(x);
    }
    private Emitter.Listener onChangeStateOfDevice = new Emitter.Listener() {

        @Override
        public void call(Object... args) {


            try {
                JSONObject data = (JSONObject) args[0];
                if (data == null) {
                    return;
                }
                String name = data.getString("name");
                String state = data.getString("state");
                Device device=findDeviceByName(name);

                if(device==null){
                    return ;
                }
                boolean newActive=toBoolean(state);
                if(!device.getValue().equals(state)){
                    device.changeValueBoolean();
                }


            } catch (JSONException e) {

                return;
            }
            // show in list
        }
    };

}
