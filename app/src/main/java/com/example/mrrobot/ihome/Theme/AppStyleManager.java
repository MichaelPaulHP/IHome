package com.example.mrrobot.ihome.Theme;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mrrobot.ihome.R;

public class AppStyleManager {

    public static final int DEFAULT= R.style.AppTheme;
    public static final int LIGHT= R.style.AppThemeLight;
    public static final int DARK= R.style.AppThemeDark;
    public static final int BLACK= R.style.AppThemeBlack;
    public static final int WHITE= R.style.AppThemeWhite;

    private Context context;
    private int currentTheme;
    private SharedPreferences sharedPreferences;

    private static AppStyleManager instance;


    private AppStyleManager(Context context){

        this.context = context;
        // get current theme
        sharedPreferences = this.context.getSharedPreferences("VALUES", this.context.MODE_PRIVATE);
        this.currentTheme = sharedPreferences.getInt("THEME", DEFAULT);//theme  defect DEFAULT

    }

    public int getCurrentStyle(){
        return this.currentTheme;
    }

    //getCurrentUser
    public void setStyle(int theme){
        saveStyle(theme);
        this.currentTheme=theme;
    }
    private void saveStyle(int theme){
        this.sharedPreferences.edit().putInt("THEME", theme).apply();
    }

    public static AppStyleManager getInstance(Context context){
        if(instance==null){
            instance = new AppStyleManager(context);
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
