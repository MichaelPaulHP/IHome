package com.example.mrrobot.ihome.models;

public class Localization {

    double time;
    double longitude;
    double latitude;

    public Localization(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.time=0;
    }

    public Localization(android.location.Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.time=  location.getTime();
    }


}
