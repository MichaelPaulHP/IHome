package com.example.mrrobot.ihome.ui.family;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrrobot.ihome.Config.Traking;
import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.databinding.FragmentLocationBinding;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;


public class LocationFragment extends Fragment  {

    LocationViewModel locationViewModel;
    FragmentLocationBinding binding;

    RecyclerView recyclerViewFamiliars;
    //private LocationListener mListener;

    //private OnMapReadyCallback mOnMapReadyCallback;
    // DELETE THIS

    public LocationFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        String token="pk.eyJ1IjoibXJtaWNoYWVsYm90IiwiYSI6ImNqZHpiamNnNzBwMXYycXA5cXh2M2xnZjcifQ.iqfPeoVbpWQcLG8bvf9qzw";
        Mapbox.getInstance(getActivity(), token);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // DATA BINDING
        binding =
                DataBindingUtil.inflate(inflater,R.layout.fragment_location, container, false);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
//

        this.locationViewModel.setMapView((MapView)binding.mapView,savedInstanceState);
        this.locationViewModel.getLocationLiveData().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                try{
                    String str= "Longi: "+location.getLongitude()+"  lat: "+location.getLatitude();
                    binding.locationInfo.setText(str);
                }catch (Exception exepction){
                    exepction.printStackTrace();
                }

            }
        });
        /*this.locationViewModel.getCounterLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.locationCounter.setText("     COUNTER: "+s);
            }
        });
        */
        recyclerViewFamiliars=binding.recyclerViewListFamiliars;
        binding.locationCounter.setText("my position");
        locationConfig();
        initRecyclerViewAdapter();



        this.binding.setLocationVM(this.locationViewModel);

        return binding.getRoot();
    }
    private void initRecyclerViewAdapter() {

        this.recyclerViewFamiliars.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.recyclerViewFamiliars.setAdapter(this.locationViewModel.participantsAdapter);
    }

    private void locationConfig() {
        try {


            //  permission to GPS
            if (ActivityCompat.checkSelfPermission
                    (this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                throw new Exception("no permission");
            }

            LocationManager locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
            if(locationManager == null){
                throw new Exception("not get LOCATION_SERVICE");
            }
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        Traking.MIN_TIME,
                        Traking.MIN_DISTANCE,
                        new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                String str= "Longi: "+location.getLongitude()+"  lat: "+location.getLatitude();
                                binding.locationCounter.setText(str);
                            }

                            @Override
                            public void onStatusChanged(String s, int i, Bundle bundle) {

                            }

                            @Override
                            public void onProviderEnabled(String s) {

                            }

                            @Override
                            public void onProviderDisabled(String s) {

                            }
                        }
                );



            } else {
                throw new Exception("GPS not enable");
            }
        } catch (Exception e) {
            Log.println(Log.INFO, "ServiceLocation", "exception: "+e.getMessage());
            e.printStackTrace();
        }

    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof Activity) {
                Activity a = (Activity) context;
                mListener = (LocationListener)a;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement themeDialogListener");
            }
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            Log.d("ERRORRROR",e.getMessage());

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */

    @Override
    public void onStart() {
         this.locationViewModel.getMapView().onStart();
        //this.mapView.getMapAsync(mOnMapReadyCallback);
        super.onStart();

    }

    @Override
    public void onResume() {
        this.locationViewModel.getMapView().onResume();
        super.onResume();

    }

    @Override
    public void onPause() {
        this.locationViewModel.getMapView().onPause();
        super.onPause();

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        this.locationViewModel.getMapView().onDestroy();
    }

    @Override
    public void onStop() {
        this.locationViewModel.onStop();
        super.onStop();

    }
    /*
    @Override
    public void onDestroy() {
        this.mapView.onDestroy();
        super.onDestroy();

    }
    */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.locationViewModel.getMapView().onLowMemory();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.locationViewModel.getMapView().onSaveInstanceState(outState);

    }
    /*
    public void getMapAsync(@NonNull final OnMapReadyCallback onMapReadyCallback) {
        mOnMapReadyCallback = onMapReadyCallback;
    }
    */
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other UI</a> for more information.
     */

    /*public interface LocationListener {
        // TODO: Update argument type and name
        String mapStyle ();
    }
    */
}
