package com.example.mrrobot.ihome.ui.family;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.Services.ServiceLocation;
import com.example.mrrobot.ihome.databinding.FragmentLocationBinding;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;


public class LocationFragment extends Fragment  {

    LocationViewModel locationViewModel;
    FragmentLocationBinding binding;
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




        this.binding.setLocationVM(this.locationViewModel);

        return binding.getRoot();
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
