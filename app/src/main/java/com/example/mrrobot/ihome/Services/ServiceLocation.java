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
 * this class is for get my Localization and send to API
 */

public class ServiceLocation extends Service {


    private Context ctx;
    FollowLocationThread followLocationThread;


    //METHODS
    public ServiceLocation() {
        super();

    }


    //
    // methods Override from Service
    //

    @Override
    public void onCreate() {


        super.onCreate();
        Log.println(Log.INFO, "ServiceLocation", "service created");
        this.ctx = getBaseContext();
        this.followLocationThread = new FollowLocationThread(this.ctx);

        //this.followLocationThread.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.println(Log.INFO, "ServiceLocation", "service running in " + Thread.currentThread().getName());
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
        Log.println(Log.INFO, "ServiceLocation", "Thread follow.. is " + this.followLocationThread.getState());
        Log.println(Log.INFO, "ServiceLocation", "service destroy");
        this.followLocationThread.sendMessage("service destroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
