package com.example.mrrobot.ihome.Theme;

import android.content.Context;
import android.content.SharedPreferences;

public class MapStyleManager {

    public  static final String DARK="mapbox://styles/mrmichaelbot/cjgb51qz22dzm2sq5c6gzzzzn";
    public  static final String LIGHT="mapbox://styles/mrmichaelbot/cjl11jid42dus2smukx2nfmyh";
    public  static final String WHITE="mapbox://styles/mrmichaelbot/cjxm4ic2r1hj61cmpbnxpxp08";
    public  static final String DEFAULT="mapbox://styles/mrmichaelbot/cjtm1deqx162v1fpejha438k5";

    private  String[] styles={DARK,LIGHT};
    //public  static final String LIGHT = "mapbox://styles/mrmichaelbot/cji9qy5gm01d22ro39x5yrf89";


    private Context context;
    private String currentTheme;
    private SharedPreferences sharedPreferences;

    private static MapStyleManager instance;


    private MapStyleManager(Context context){

        this.context = context;
        // get current theme
        sharedPreferences = this.context.getSharedPreferences("VALUES", this.context.MODE_PRIVATE);
        this.currentTheme = sharedPreferences.getString("StyleMap", LIGHT);//theme  defect DEFAULT

    }

    public String getCurrentStyle(){
        return this.currentTheme;
    }

    //getCurrentUser
    public void setStyle(String theme){
        saveStyle(theme);
    }
    private void saveStyle(String theme){
        this.sharedPreferences.edit().putString("StyleMap", theme).apply();
    }

    public static MapStyleManager getInstance(Context context){
        if(instance==null){
            instance = new MapStyleManager(context);
        }
        else{
            instance.setContext(context);
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
