package com.example.mrrobot.ihome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;

import com.example.mrrobot.ihome.Services.ServiceLocation;
import com.example.mrrobot.ihome.components.MenuLeftComponent;
import com.example.mrrobot.ihome.ui.devices.DevicesFragment;
import com.example.mrrobot.ihome.ui.family.LocationFragment;
import com.example.mrrobot.ihome.ui.home.HomeFragment;
import com.example.mrrobot.ihome.ui.RoomsFragment;
import com.example.mrrobot.ihome.ui.SecurityFragment;

import com.example.mrrobot.ihome.Theme.ThemeManager;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements
        View.OnClickListener
{


    // FIREBASE
    private FirebaseAuth mAuth;

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigation;
    private FragmentTransaction fragmentTransaction;
    //attributes fragments
    private HomeFragment homeFragment;
    private RoomsFragment roomsFragment;
    private DevicesFragment devicesFragment;
    private SecurityFragment securityFragment;
    private LocationFragment locationFragment;
    //private FamilyFragment familyFragment;

    // /drawer navigationView
    DrawerLayout drawer;
    // user Data
    TextView userName;
    CircleImageView userImage;
    SharedPreferences sharedPreferences;

    ThemeManager themeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //start serviceLocation
        startService( new Intent(this.getApplicationContext(),ServiceLocation.class));


        // theme config
        this.themeManager= ThemeManager.getInstance(getApplicationContext());
        this.setTheme(this.themeManager.getCurrentTheme());// set theme

        // set contentView
        setContentView(R.layout.activity_main);

        // MENU MANAGER
        MenuLeftComponent menuLeftComponent= new MenuLeftComponent(
                this,
                getSupportFragmentManager(),
                R.id.navigationDrawer,R.id.frameLayout);

        // USER
        mAuth = FirebaseAuth.getInstance();



    }
    /*public static class Person{
        private String name;
        private int age;
        public Person(String name , int age){
            this.name=name;
            this.age=age;
        }

        @Override
        public String toString() {
            String str=this.name+" "+this.age ;
            return str;
        }
    }*/

    /*public static void  main(String args[]){

        IHomeApiService apiService = HomeApiService.getInstance();
        try {
            apiService.saveMyLocation(new Localization(3434,34343))
                    .execute().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }*/

    @Override
    protected void onStart() {
        super.onStart();
        //updateImageUser();
    }

    @Override
    protected void onDestroy() {
        //House.getInstance().disconnect();
        super.onDestroy();
    }
    /*
    private void updateImageUser(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser !=null){
            this.userName.setText(currentUser.getDisplayName());
            GlideApp
                    .with(MainActivity.this)
                    .load(currentUser.getPhotoUrl())
                    .centerCrop()
                    .into(this.userImage);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case(R.id.navigation_search):
                return true;
            case R.id.navigation_menu:
                this.drawer.openDrawer(GravityCompat.END);
                return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return onItemMenuSelected(item);
    }
    public boolean onItemMenuSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                setFragment(this.homeFragment);
                break;
            case R.id.navigation_rooms:
                setFragment(this.roomsFragment);
                break;
            case R.id.navigation_devices:
                setFragment(this.devicesFragment);
                break;
            case R.id.navigation_security:
                setFragment(this.securityFragment);
                break;
            case R.id.navigation_family:
                setFragment(this.locationFragment);
                break;
            case R.id.configurationOption:
                showDialogTheme();
                break;
        }
        this.drawer.closeDrawer(GravityCompat.END);
        return true;

    }
    */

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

    /*
    @Override
    public boolean isVisibleBotNav() {

        return sharedPreferences.getBoolean("BotNav",true);
    }

    @Override
    public boolean styleMap() {
        //DEFAULT DARK
         return sharedPreferences.getString ("StyleMap", StyleMapConstants.DARK).equals( StyleMapConstants.DARK);
    }

    @Override
    public void saveChanges(int theme, boolean botNav,boolean mapStyle) {

        sharedPreferences.edit().putBoolean("BotNav",botNav).apply();

        String toSaveStyle=mapStyle? StyleMapConstants.DARK:StyleMapConstants.LIGHT;
        sharedPreferences.edit().putString("StyleMap",toSaveStyle ).apply();


        int themeAct = sharedPreferences.getInt("THEME",ThemeConstants.DEFAULT);
        if(theme!=themeAct){
            showTheme(theme);
        }
    }
    @Override
    public void toggleBotNavigation() {
        toggleBotNav();
    }
    */
    // LOCATION FRAGMENT LISTENER
    /*
    @Override
    public String mapStyle() {
        return sharedPreferences.getString ("StyleMap", StyleMapConstants.DARK);
    }
    */
    // FIN LOCATION FRAGMENT LISTENER




}
