package com.example.mrrobot.ihome.ui.family;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.mrrobot.ihome.Services.Utils;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;


import com.example.mrrobot.ihome.Theme.ThemeManager;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.maps.Style;

import static android.os.Looper.getMainLooper;

public class LocationViewModel extends ViewModel implements
        OnMapReadyCallback,

        LocationEngineCallback<LocationEngineResult> {

    private MapView mapView;
    private Context context;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;

    private Location location;
    private MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
    private LocationEngine locationEngine;

    private int cont=0;
    private MutableLiveData<String> counter= new MutableLiveData<>();
    //private static LocationViewModel INSTANCE = null;

    public LocationViewModel() {


    }

    /*public static LocationViewModel getInstance(Context  context){
        if(INSTANCE==null){
            INSTANCE = new LocationViewModel(context);
        }
        return (INSTANCE);
    }*/

    /*public boolean requestLocationPermissions(){
        if (PermissionsManager.areLocationPermissionsGranted(this.context)) {
            // Permission sensitive logic called here, such as activating the Maps SDK's LocationComponent to show the device's location
            return true;

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this.context);
        }
        return false;

    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }
    */


    public void setMapView(MapView mapView, Bundle savedInstanceState) {

        this.mapView = mapView;
        this.context = this.mapView.getContext();
        this.mapView.onCreate(savedInstanceState);
        this.mapView.getMapAsync(this);
    }

    public MapView getMapView() {

        return this.mapView;

    }

    // this.mapView.getMapAsync(this);
    @Override
    public void onMapReady(MapboxMap mapboxMap) {

        this.mapboxMap = mapboxMap;
        // SET STYLE
        String themeCurrent = ThemeManager.getInstance(this.context).getCurrentThemeMap();
        this.mapboxMap.setStyle(themeCurrent, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent();
                enableLocationEngine();



            }
        });

        /*CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(this.location.getLatitude(), this.location.getLongitude()))
                .zoom(12)                            // enable zoom feature
                .build();

        this.mapboxMap.setCameraPosition(cameraPosition);
        this.mapboxMap.setMinZoomPreference(10);
        this.mapboxMap.setMaxZoomPreference(16);
        // marker in my position

        LatLng my = new LatLng(this.myLocation.getLatitude(),this.myLocation.getLongitude());
        this.mapboxMap.addM

        MarkerOptions markerOptions =new MarkerOptions();
        markerOptions.setPosition(my);
        this.mapboxMap.addMarker(markerOptions);
        markerOptions.setPosition( new )
        this.mapboxMap.setOnMarkerClickListener(this);


        this.mapboxMap.removeMarker(markerOptions);
        */

    }


    @SuppressLint("MissingPermission")
    private void enableLocationComponent() {
        // Get an instance of the component
        LocationComponent locationComponent = mapboxMap.getLocationComponent();


        // Activate
        Style style = mapboxMap.getStyle();
        locationComponent.activateLocationComponent(context, style);

        // Enable to make component visible
        locationComponent.setLocationComponentEnabled(true);

        // Set the component's camera mode
        locationComponent.setCameraMode(CameraMode.TRACKING);

        // Set the component's render mode
        locationComponent.setRenderMode(RenderMode.COMPASS);
    }

    @SuppressLint("MissingPermission")
    private void enableLocationEngine() {

        int min = 1000 * 60 * 1;// 2 min
        int distance=5; //metros
        this.locationEngine = LocationEngineProvider.getBestLocationEngine(this.context);

        LocationEngineRequest request = new LocationEngineRequest.Builder(min)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setDisplacement(distance)
                .setFastestInterval(min)
                .setMaxWaitTime(min*2)
                .build();

        locationEngine.requestLocationUpdates(request, this, getMainLooper());


    }


    // locationEngine.requestLocationUpdates
    @Override
    public void onSuccess(LocationEngineResult result) {
        // Location logic here

        Location lastLocation = result.getLastLocation();

        this.cont++;
        this.counter.postValue(cont+"");

        if (lastLocation != null) {
            this.locationLiveData.postValue(lastLocation);
        }
    }

    // locationEngine.requestLocationUpdates
    @Override
    public void onFailure(@NonNull Exception exception) {
        Utils.toast("error:" +exception.getMessage(),this.context);
    }

    //GET
    public MutableLiveData<Location> getLocationLiveData() {
        return this.locationLiveData;
    }
    public MutableLiveData<String> getCounterLiveData() {
        return this.counter;
    }
    //LIFECYCLE
    public void onStart() {
        mapView.onStart();

        /*if (locationEngine != null) {

            locationEngine.requestLocationUpdates(request, this, getMainLooper());
        }*/
    }


    public void onStop() {


        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(this);
        }

        mapView.onStop();
    }
}
