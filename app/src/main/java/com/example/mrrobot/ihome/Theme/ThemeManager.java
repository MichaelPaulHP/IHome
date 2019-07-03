package com.example.mrrobot.ihome.Theme;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.TintTypedArray;

import com.example.mrrobot.ihome.R;

import static android.support.v7.widget.TintTypedArray.obtainStyledAttributes;

public class ThemeManager {

    private static ThemeManager instance;

    private AppStyleManager  appStyleManager;
    private MapStyleManager  mapStyleManager;

    private Context context;


    private ThemeManager(Context context) {
        this.context = context;
        this.appStyleManager=AppStyleManager.getInstance(context);
        this.mapStyleManager= MapStyleManager.getInstance(context);
    }

    public static ThemeManager getInstance(Context context) {

        if (instance == null) {
            instance = new ThemeManager(context);
        } else {
            instance.setContext(context);
        }
        return instance;
    }

    public void setTheme(Mode mode){
        switch (mode){
            case BLACK:
                this.mapStyleManager.setStyle(MapStyleManager.DARK);
                this.appStyleManager.setStyle(AppStyleManager.BLACK);
                break;
            case DARK:
                this.mapStyleManager.setStyle(MapStyleManager.DARK);
                this.appStyleManager.setStyle(AppStyleManager.DARK);
                break;
            case LIGHT:
                this.mapStyleManager.setStyle(MapStyleManager.LIGHT);
                this.appStyleManager.setStyle(AppStyleManager.LIGHT);
                break;
            case WHITE:
                this.mapStyleManager.setStyle(MapStyleManager.WHITE);
                this.appStyleManager.setStyle(AppStyleManager.WHITE);
                break;
            case DEFAULT:
                this.mapStyleManager.setStyle(MapStyleManager.DEFAULT);
                this.appStyleManager.setStyle(AppStyleManager.DEFAULT);
                break;
        }

    }

    public String getMapStyle(){
        return this.mapStyleManager.getCurrentStyle();
    }
    public int getAppStyle(){
        return this.appStyleManager.getCurrentStyle();
    }



    public void setContext(Context context) {
        this.appStyleManager=AppStyleManager.getInstance(context);
        this.mapStyleManager= MapStyleManager.getInstance(context);
    }

    public int getItemOfStyle(int id) {


        int attr[] = {id};
        //TypedArray a =this.context.getTheme().obtainStyledAttributes(attr);
        TypedArray tar = this.context.obtainStyledAttributes(getAppStyle(), attr);

        return tar.getColor(0, Color.BLACK);
//        TypedArray a = obtainStyledAttributes(this.theme,attr);

    }
    public enum Mode{
        DEFAULT,
        LIGHT,
        DARK,
        BLACK,
        WHITE
    }
}
