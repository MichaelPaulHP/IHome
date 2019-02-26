package com.example.mrrobot.ihome.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.mrrobot.ihome.BR;
import com.example.mrrobot.ihome.Config.Devices;
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
    private int counter;

    private House() {
        this.isConnected = false;
        setCounter(0);
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
        this.socketIO.on("changeState", onChangeState);
    }

    private void updateDevices() {

        /*for (Device device : Devices.getArray) {
            this.devices.put(device.getUserName(), device);

        }*/
        this.devicesList.addAll(Arrays.asList(Devices.getArray));
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
    private Emitter.Listener onChangeState = new Emitter.Listener() {
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
    };

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

    @Bindable
    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
        notifyPropertyChanged(BR.counter);
    }
}
