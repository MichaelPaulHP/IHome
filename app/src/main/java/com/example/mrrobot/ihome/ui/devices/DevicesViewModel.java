package com.example.mrrobot.ihome.ui.devices;

import android.arch.lifecycle.ViewModel;

import com.example.mrrobot.ihome.models.House;

public class DevicesViewModel extends ViewModel {

    public House house;

    public DevicesViewModel() {
        this.house = House.getInstance();

    }

}
