package com.example.mrrobot.ihome.Services.LocationService;

import android.annotation.SuppressLint;
import android.content.Context;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;

import android.util.Log;

import com.example.mrrobot.ihome.Config.Traking;
import com.example.mrrobot.ihome.Services.ApiHome.HomeApiService;
import com.example.mrrobot.ihome.Services.ApiHome.IHomeApiService;
import com.example.mrrobot.ihome.models.Localization;
import com.example.mrrobot.ihome.models.Message;
import com.example.mrrobot.ihome.models.User;


import java.io.IOException;


public class FollowLocationThread extends Thread
        implements LocationListener {

    private final Context ctx;
    private LocationManager locationManager;
    private Location location;
    private static final int TWO_MINUTES = 1000 * 60 * 2;// test
    private IHomeApiService apiService; // API

    public FollowLocationThread(Context context) {
        super("");
        this.ctx = context;
        this.setName("FollowLocation HandlerThread");
        apiService = HomeApiService.getInstance();



    }


    @Override
    public void run() {

        Log.println(Log.INFO, "ServiceLocation ", "RUN " + Thread.currentThread().getName());
        locationConfig();
        //task();

    }

    @Override
    public void interrupt() {
        Log.println(Log.INFO, "ServiceLocation", "thread interrupt");
        super.interrupt();

    }

    @SuppressLint("MissingPermission")
    private void locationConfig() {
        try {


            //  permission to GPS
            /*if (ActivityCompat.checkSelfPermission
                    (this.ctx, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                throw new Exception("no permission");
            }*/

            // Now get the Looper from the HandlerThread
            // NOTE: This call will block until the HandlerThread gets control and initializes its Looper


            this.locationManager = (LocationManager) this.ctx.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) {
                throw new Exception("not get LOCATION_SERVICE");
            }
            boolean isGPSEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled) {
                HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
                handlerThread.start();
                Looper looper = handlerThread.getLooper();

                this.locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        Traking.MIN_TIME,
                        Traking.MIN_DISTANCE,
                        this,
                        looper
                );

                this.location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.println(Log.INFO, "ServiceLocation", "my location" + location.getLatitude() + "  " + location.getLongitude());

            } else {
                throw new Exception("GPS not enable");
            }
        } catch (Exception e) {
            Log.println(Log.INFO, "ServiceLocation", "exception: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    private void task() {

        try {
            //emit my location


            while (true) {

                Location newLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                boolean isBetterLocation = isBetterLocation(newLocation, location);

                //location = newLocation;
                Log.println(Log.INFO, "ServiceLocation", "isBetterLocation " + isBetterLocation);

                Thread.sleep(1000 * 60 * 1);

            }
        } catch (InterruptedException e) {
            Log.println(Log.INFO, "ServiceLocation", "InterruptedException | JSONException");
            e.printStackTrace();
            Thread.currentThread().interrupt();

        }
    }


    @Override
    public void onLocationChanged(Location location) {

        this.location = location;
        Log.println(Log.INFO, "ServiceLocation", "onLocationChanged");
        try {
            this.apiService.saveMyLocalization(new Localization(location)).execute();

        } catch (IOException e) {
            Log.println(Log.INFO, "ServiceLocation", "Exception" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.println(Log.INFO, "ServiceLocation", "onStatusChanged" + s);
        sendMessage("onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.println(Log.INFO, "ServiceLocation", "onProviderEnabled" + s);
        sendMessage("onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.println(Log.INFO, "ServiceLocation", "onProviderDisabled" + s);

        sendMessage("onProviderDisabled");
    }
    public void sendMessage(String message){
        try {
            this.apiService.sendMessage(new Message(message)).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //INTERFACE WITH SERVICE,


    // EVENTS

//    private void emitLocation(Location location) throws JSONException {
//        //user
//        JSONObject toSend = new JSONObject();
//        toSend.put("user", this.user.getId());
//        //location
//        JSONObject locationSend = new JSONObject();
//        locationSend.put("latitude", location.getLatitude());
//        locationSend.put("longitude", location.getLongitude());
//
//        toSend.put("location", locationSend);
//
//        // {user:1324646134,
//        // location:{
//        //           latitude:234234242, longitude:564646
//        //          }
//        // }
//
//        this.socketIO.emit("myLocation", toSend);
//    }
//    private void emitEventDisconnect() throws JSONException {
//        JSONObject toSend = new JSONObject();
//        toSend.put("user", this.user.getId());
//        this.socketIO.emit("disconnectService", toSend);
//    }

    /**
     * Determines whether one Localization reading is better than the current Localization fix
     *
     * @param location            The new Localization that you want to evaluate
     * @param currentBestLocation The current Localization fix, to which you want to compare the new one
     */
    private boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        Log.println(Log.INFO, "ServiceLocation", "timeDelta " + timeDelta);
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
