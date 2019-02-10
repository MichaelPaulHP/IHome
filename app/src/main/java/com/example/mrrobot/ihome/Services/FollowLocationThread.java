package com.example.mrrobot.ihome.Services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.mrrobot.ihome.Config.Traking;
import com.example.mrrobot.ihome.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;

public class FollowLocationThread extends Thread implements LocationListener, ServiceLocation.IServiceLocation {

    private final Context ctx;
    private LocationManager locationManager;
    private Socket socketIO;
    private User user;
    private Location location;
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    public FollowLocationThread(Context context,Socket socket,User user) {
        super();
        this.ctx=context;
        this.socketIO=socket;
        this.user=user;
        this.setName("FollowLocationThread");
    }

    @Override
    public void run() {
        locationConfig();
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //task();

    }



    private void locationConfig() {
        try {


            //  permission to GPS
            if (ActivityCompat.checkSelfPermission
                    (this.ctx, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                throw new Exception("no permission");
            }

            this.locationManager = (LocationManager) this.ctx.getSystemService(Context.LOCATION_SERVICE);

            boolean isGPSEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled) {

                this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Traking.MIN_TIME,Traking.MIN_DISTANCE, this);
                this.location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            } else {
                throw new Exception("GPS not enable");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @SuppressLint("MissingPermission")
    private void task(){

        try {
            //emit my location
            emitLocation(location);

            while (true) {

                Location newLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                boolean isBetterLocation = isBetterLocation(newLocation, location);
                if (isBetterLocation) {
                    emitLocation(newLocation);
                }
                //location = newLocation;
                Log.println(Log.INFO, "ServiceLocation", "isBetterLocation "+isBetterLocation);
                Log.println(Log.INFO, "ServiceLocation", "isConnected "+socketIO.connected());
                Thread.sleep(1000 * 60 * 1);

            }
        } catch (InterruptedException | JSONException e){
            Log.println(Log.INFO, "ServiceLocation", "InterruptedException | JSONException");
            e.printStackTrace();
            Thread.currentThread().interrupt();

        }
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location=location;
        Log.println(Log.INFO, "ServiceLocation", "onLocationChanged");
        try {
            this.emitLocation(this.location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //INTERFACE WITH SERVICE,

    @Override
    public void onDestroy() {
        try {
            emitEventDisconnect();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    // EVENTS

    private void emitLocation(Location location) throws JSONException {
        //user
        JSONObject toSend = new JSONObject();
        toSend.put("user", this.user.getId());
        //location
        JSONObject locationSend = new JSONObject();
        locationSend.put("latitude", location.getLatitude());
        locationSend.put("longitude", location.getLongitude());

        toSend.put("location", locationSend);

        // {user:1324646134,
        // location:{
        //           latitude:234234242, longitude:564646
        //          }
        // }

        this.socketIO.emit("myLocation", toSend);
    }
    private void emitEventDisconnect() throws JSONException {
        JSONObject toSend = new JSONObject();
        toSend.put("user", this.user.getId());
        this.socketIO.emit("disconnectService", toSend);
    }

    /**
     * Determines whether one Location reading is better than the current Location fix
     *
     * @param location            The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        Log.println(Log.INFO, "ServiceLocation", "timeDelta "+timeDelta);
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
