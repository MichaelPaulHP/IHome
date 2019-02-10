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


    private Context context;
    private int theme = ThemeConstants.DEFAULT;
    private String themeMap;
    private SharedPreferences sharedPreferences;


    private ThemeManager(Context context) {
        this.context = context;
        // get current theme
        sharedPreferences = this.context.getSharedPreferences("VALUES", this.context.MODE_PRIVATE);
        this.theme = sharedPreferences.getInt("THEME", ThemeConstants.DEFAULT);//theme  defect DEFAULT
        this.themeMap = sharedPreferences.getString("StyleMap", StyleMapConstants.DARK);
    }

    public static ThemeManager getInstance(Context context) {

        if (instance == null) {
            instance = new ThemeManager(context);
        }
        return instance;
    }

    public int getCurrentTheme() {
        return this.theme;
    }

    public void setTheme(int theme) {
        sharedPreferences.edit().putInt("THEME", theme).apply();
        this.theme = theme;
    }

    public String getCurrentThemeMap() {

        return this.themeMap;
    }

    public void setThemeMap(String themeMap) {

        sharedPreferences.edit().putString("StyleMap", themeMap).apply();
        this.themeMap = themeMap;
    }

    public boolean isSave(int theme, String themeMap) {

        if (this.theme != theme || !this.themeMap.equals(themeMap)) {
            // save
            setTheme(theme);
            setThemeMap(themeMap);
            return true;
        } else {
            return false;
        }
    }

    public int getItemOfStyle(int id) {


        int attr[] = {id};
        //TypedArray a =this.context.getTheme().obtainStyledAttributes(attr);
        TypedArray tar = this.context.obtainStyledAttributes(getCurrentTheme(), attr);

        return tar.getColor(0, Color.BLACK);
//        TypedArray a = obtainStyledAttributes(this.theme,attr);

    }
}
