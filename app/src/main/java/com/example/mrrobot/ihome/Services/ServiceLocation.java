package com.example.mrrobot.ihome.Services;


import android.app.Service;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;

import android.location.Location;

import android.os.IBinder;

import android.support.annotation.Nullable;

import android.util.Log;

import com.example.mrrobot.ihome.models.User;


import io.socket.client.Socket;


/**
 * this class is for get my Localization on change and emit(socketIO) location
 */

public class ServiceLocation extends Service {


    private static final int MIN_DISTANCE = 5; //example:5 Km
    private static final int MIN_TIME = 1000 * 60 * 1; // example 1 minutes
    private static final int TWO_MINUTES = 1000 * 60 * 2;


    MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>();
    private Context ctx;
    //private LocationManager locationManager;
    private Socket socketIO;
    private User user;
    private Location location;
    private IServiceLocation iServiceLocation;

    FollowLocationThread followLocationThread;

    //METHODS
    public ServiceLocation() {
        super();

    }

    public ServiceLocation(Context context) {
        super();
        //this.ctx = context;
        //this.locationConfig();
    }


    public MutableLiveData<Location> getLocationMutableLiveData() {
        return locationMutableLiveData;
    }

    //
    // methods Override from Service
    //

    @Override
    public void onCreate() {


        super.onCreate();
        Log.println(Log.INFO, "ServiceLocation", "service created");
        this.ctx=getBaseContext();
        this.followLocationThread = new FollowLocationThread(
                this.ctx, SocketIO.getSocket(), User.getInstance());

        //this.followLocationThread.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.println(Log.INFO, "ServiceLocation", "service running in "+Thread.currentThread().getName());
        Log.println(Log.INFO, "ServiceLocation", "state 1 " + this.followLocationThread.getState());
        this.followLocationThread.start();
        /*if (this.followLocationThread.getState().equals(Thread.State.TERMINATED)) {
            this.followLocationThread.start();
        }*/

        Log.println(Log.INFO, "ServiceLocation", "state 2 " + this.followLocationThread.getState());
        /*try {
            this.followLocationThread.wait();
            Log.println(Log.INFO, "ServiceLocation", "state 2  "+this.followLocationThread.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {

        Log.println(Log.INFO, "ServiceLocation", "Thread follow.. is "+this.followLocationThread.getState());
        Log.println(Log.INFO, "ServiceLocation", "service destroy");
        //emit event "service suspended"
        //iServiceLocation.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    /**
     * INTERFACE
     */
    public interface IServiceLocation {
        void onDestroy();
    }
}
