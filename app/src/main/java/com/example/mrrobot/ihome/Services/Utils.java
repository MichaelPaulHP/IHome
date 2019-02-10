package com.example.mrrobot.ihome.Services;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void toast(String x,Context context){
        Toast.makeText(context,x,Toast.LENGTH_LONG).show();
    }

}
