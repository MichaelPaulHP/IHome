package com.example.mrrobot.ihome.ui.home;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mrrobot.ihome.DialogsFragment.ThemeDialogFragment;
import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.Services.SocketIO;
import com.example.mrrobot.ihome.databinding.FragmentHomeBinding;
import com.example.mrrobot.ihome.ui.device.DeviceAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel mViewModel;
    private FragmentHomeBinding binding;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //return inflater.inflate(R.layout.fragment_home, container, false);


        // DATA BINDING
        binding =
                DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);

        // TO VIEW MODEL
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mViewModel.setContext(getContext());
        binding.setHomeVM(mViewModel);
        // set events
        setEvents();
        // config deviceList
        setupListDevice(binding.listSensor); // listSensor is recyclerView

        View view = binding.getRoot();

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getContext(),"werewr "+ SocketIO.getSocket().connected(),Toast.LENGTH_LONG).show();

        initViews();
    }

    public void initViews(){
        /*final SwitchIconComponent switchIconComponent = this.binding.swIconLed;

        switchIconComponent.setListener(new SwitchIconComponent.SwitchIconListener() {
            @Override
            public void onClick(View view) {


            }

            @Override
            public void onChangeState(View view) {

            }
        });*/
    }
    public void showDialogTheme(){

        DialogFragment themeDialogFragment = new ThemeDialogFragment();
        themeDialogFragment.show(getFragmentManager(), "themeDialogFragment");

    }

    private void setEvents(){

        binding.btnSettingsHome.setOnClickListener(this);
        //binding.btnTest.setOnClickListener(this);
    }

    private void setupListDevice(RecyclerView listDevice) {
        DeviceAdapter deviceAdapter= new DeviceAdapter();
        listDevice.setAdapter(deviceAdapter);
        deviceAdapter.setDevices(this.mViewModel.getINPUTSdevices());
        listDevice.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }
    @Override
    public void onClick(View view) {
        int id= view.getId();
        switch (id) {
            case R.id.btnSettingsHome:
                showDialogTheme();
                break;
//            case R.id.btnTest:
//                this.mViewModel.testMessage();
//                break;
        }

    }
}
