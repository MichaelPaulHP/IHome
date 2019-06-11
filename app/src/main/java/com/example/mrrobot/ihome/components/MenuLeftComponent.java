package com.example.mrrobot.ihome.components;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.mrrobot.ihome.ui.family.LocationFragment;
import com.example.mrrobot.ihome.ui.devices.DevicesFragment;
import com.example.mrrobot.ihome.ui.home.HomeFragment;
import com.example.mrrobot.ihome.ui.SecurityFragment;
import com.example.mrrobot.ihome.R;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class MenuLeftComponent  {
    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;

    public MenuLeftComponent(Activity activity,
                             final FragmentManager fragmentManager ,
                             int navigationDrawer,
                             int frameLayout){


        sNavigationDrawer =activity.findViewById(navigationDrawer);

        List<MenuItem> menuItems = new ArrayList<>();

        //Use the MenuItem given by this library and not the default one.
        //First parameter is the title of the menu item and then the second parameter is the image which will be the background of the menu item.

        menuItems.add(new MenuItem("    Home",R.drawable.gradient));
        //menuItems.add(new MenuItem("        ",R.drawable.feed_bg));
        menuItems.add(new MenuItem("    Devices",R.drawable.feed_bg));
        menuItems.add(new MenuItem("    Security",R.drawable.message_bg));
        menuItems.add(new MenuItem("    Family",R.drawable.music_bg));

        //then add them to navigation drawer

        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {

            //FragmentManager fragmentManager =getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(frameLayout, fragment).commit();
        }
        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                //System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        fragmentClass = HomeFragment.class;
                        break;
                    }
                    case 1:{
                        fragmentClass = DevicesFragment.class;
                        break;
                    }
                    case 2:{
                        fragmentClass = SecurityFragment.class;
                        break;
                    }
                    case 3:{
                        fragmentClass = LocationFragment.class;
                        break;
                    }

                }

                //Listener for drawer events such as opening and closing.
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        //System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            //FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().
                                    setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).
                                    replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        //System.out.println("State "+newState);
                    }
                });
            }
        });

    }


}
