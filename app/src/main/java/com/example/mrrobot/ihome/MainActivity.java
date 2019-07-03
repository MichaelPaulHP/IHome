package com.example.mrrobot.ihome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.example.mrrobot.ihome.Services.LocationService.ServiceLocation;
import com.example.mrrobot.ihome.components.MenuLeftComponent;
import com.example.mrrobot.ihome.models.House;

import com.example.mrrobot.ihome.Theme.ThemeManager;
import com.example.mrrobot.ihome.models.User;
import com.google.firebase.auth.FirebaseAuth;




public class MainActivity extends AppCompatActivity
        implements
        View.OnClickListener
{


    // FIREBASE
    private FirebaseAuth mAuth;
    ThemeManager themeManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // theme config
        this.themeManager= ThemeManager.getInstance(getApplicationContext());
        this.setTheme(this.themeManager.getAppStyle());// set theme

        // set contentView
        setContentView(R.layout.activity_main);

        // MENU MANAGER
        MenuLeftComponent menuLeftComponent= new MenuLeftComponent(
                this,
                getSupportFragmentManager(),
                R.id.navigationDrawer,R.id.frameLayout);

        // USER
        mAuth = FirebaseAuth.getInstance();
        User.getCurrentUser().requestMyChats();


    }

    @Override
    protected void onStart() {
        super.onStart();
        //updateImageUser();
    }

    @Override
    protected void onDestroy() {
        House.getInstance().disconnect();
        //start serviceLocation
        startService( new Intent(this.getApplicationContext(),ServiceLocation.class));
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        switch (id) {
            case R.id.sign_out_button:
                signOut();
                return ;
        }
    }
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }




}
