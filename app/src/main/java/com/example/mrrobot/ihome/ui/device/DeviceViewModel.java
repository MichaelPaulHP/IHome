package com.example.mrrobot.ihome.ui.device;

import android.arch.lifecycle.ViewModel;

import com.example.mrrobot.ihome.models.Device;

public class DeviceViewModel extends ViewModel {

    private Device device;
    private String mode;
    // METHODS

    public DeviceViewModel() {

    }

    public void setDevice(Device device) {
        this.device = device;
        this.mode = device.getMode();
    }

    public void getValue() {
        String str = "";
        switch (this.mode) {
            case "OUTPUT": // example: LED
                str = this.device.getValue();
                break;
            case "INPUT":
                str = this.device.getValue() + " " + this.device.getUnit();
                break;


        }


    }

}
