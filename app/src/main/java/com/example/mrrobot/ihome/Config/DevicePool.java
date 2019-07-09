package com.example.mrrobot.ihome.Config;

import com.example.mrrobot.ihome.models.Device;
import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.models.stateDevice;

import java.util.HashMap;
import java.util.Map;

public class DevicePool {

    public static Device[] devices={
            new Device("LED","OUTPUT", R.drawable.ic_light),
            new Device("LED_SALA","OUTPUT", R.drawable.ic_light),
            new Device("GARAGE","OUTPUT", R.drawable.ic_menu_black_24dp),
            new Device("VENTILADOR","OUTPUT", R.drawable.ic_av_timer_black_24dp),
            new Device("FIRE","INPUT",R.drawable.ic_fire,"Normal, sin Humo",stateDevice.CONNECTED),
            new Device("FIRE","INPUT",R.drawable.ic_tap,"Abierto",stateDevice.WARNING),
            new Device("FIRE","INPUT",R.drawable.ic_tap,"Abierto",stateDevice.WARNING),
            new Device("Movimiento","INPUT",R.drawable.ic_footsteps),
            getHUM(),getTEMP(),getENERGY()
    };

    private static Device getTEMP(){
        Device temp= new Device("TEMPERATURE","INPUT",R.drawable.ic_thermometer);
        temp.setValue("21");
        temp.setUnit("°C");
        temp.setState(stateDevice.CONNECTED);
        temp.setMessage("Esta En Un Rango Normal");
        return temp;
    }
    private static Device getHUM(){
        Device temp= new Device("HUMIDITY","INPUT",R.drawable.ic_humidity);
        temp.setValue("48");
        temp.setUnit("%");
        temp.setState(stateDevice.CONNECTED);
        return temp;
    }
    private static Device getENERGY(){
        Device temp= new Device("ENERGY","INPUT",R.drawable.ic_flash);
        temp.setValue("-");
        temp.setUnit("KW");
        temp.setState(stateDevice.WARNING);
        return temp;
    }
    // this.icon=R.drawable.ic_settings_white_24dp;

    /*public static Device[] get(){
        List<Device> devices = new ArrayList<>();

        // is this the list of devices
        devices.add(new Device("LED","OUTPUT"));
        devices.add(new Device("FIRE","INPUT"));


        return devices;
    }*/
}
