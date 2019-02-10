package com.example.mrrobot.ihome.Config;

import com.example.mrrobot.ihome.models.Device;
import com.example.mrrobot.ihome.R;

public class Devices {

    public static Device[] getArray={

            new Device("LED","OUTPUT", R.drawable.ic_light),
            new Device("FIRE","INPUT",R.drawable.ic_fire),
            new Device("TEMPERATURE","INPUT",R.drawable.ic_thermometer),
            new Device("HUMIDITY","INPUT",R.drawable.ic_humidity),
            new Device("ENERGY","INPUT",R.drawable.ic_flash)
    };
    // this.icon=R.drawable.ic_settings_white_24dp;

    /*public static Device[] get(){
        List<Device> devices = new ArrayList<>();

        // is this the list of devices
        devices.add(new Device("LED","OUTPUT"));
        devices.add(new Device("FIRE","INPUT"));


        return devices;
    }*/
}
