package com.example.mrrobot.ihome.ui.home;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.Bindable;
import android.databinding.Observable;

import android.view.View;
import android.widget.Toast;

import com.example.mrrobot.ihome.Firebase.Auth;
import com.example.mrrobot.ihome.Services.Utils;
import com.example.mrrobot.ihome.models.Device;
import com.example.mrrobot.ihome.models.House;
import com.example.mrrobot.ihome.models.User;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements Observable {

    private Auth user;
    public House house;
    public String isConnected;

    public int contadorer;
    private Context context;

    public HomeViewModel() {

        this.user = Auth.getInstance();
        this.house = House.getInstance();
        isConnected = this.house.isConnected() ? "conectado" : "si conexio";
        this.contadorer = this.house.getCounter();

    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Bindable
    public String getUserName() {
        return this.user.getUserName();
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
    public void testMessage() {
        int num = (int) (Math.random() * 50) + 1;
        ;
        Device device = this.house.findDeviceByName("LED");
        device.setMessage("me: " + num);
        String qwe= device.getValue().equals("true")? "false":"true";
        device.setValue(qwe);
        Device deviceTemperature=this.house.findDeviceByName("TEMPERATURE");
        deviceTemperature.setValue(""+num);


    }

    // funciona homeVM.house.count


    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {

    }
}
