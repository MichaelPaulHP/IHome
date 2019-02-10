package com.example.mrrobot.ihome.ui.devices;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.databinding.DeviceBinding;
import com.example.mrrobot.ihome.databinding.DeviceDetailBinding;
import com.example.mrrobot.ihome.databinding.FragmentDevicesBinding;
import com.example.mrrobot.ihome.models.Device;

import com.example.mrrobot.ihome.ui.device.DeviceMinimalAdapter;
import com.example.mrrobot.ihome.ui.device.DeviceViewModel;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;


public class DevicesFragment extends Fragment implements
        DiscreteScrollView.ScrollStateChangeListener<DeviceMinimalAdapter.DeviceMinimalAdapterViewHolder>,
        DiscreteScrollView.OnItemChangedListener<DeviceMinimalAdapter.DeviceMinimalAdapterViewHolder>,
        View.OnClickListener {

    private FragmentDevicesBinding binding;
    private DevicesViewModel mViewModel;

    private DiscreteScrollView picker;
    private DeviceDetailBinding deviceDetail;
    private DeviceViewModel deviceViewModel;
    //private DeviceDetailView deviceDetailView;


    //
    // METHODS
    //
    public DevicesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // DATA BINDING
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_devices, container, false);

        // TO VIEW MODEL
        mViewModel = ViewModelProviders.of(this).get(DevicesViewModel.class);
        //mViewModel.setContext(getContext());
        binding.setDevicesVM(mViewModel);
        // set events
        //setEvents();
        // config deviceList


        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    public void initUI(View view) {
        this.picker = this.binding.devicePicker;

        this.picker.setSlideOnFling(true);
        this.picker.setAdapter(new DeviceMinimalAdapter(this.mViewModel.house.devicesList));
        this.picker.addOnItemChangedListener(this);
        this.picker.addScrollStateChangeListener(this);
        this.picker.scrollToPosition(1);

        //this.picker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
        this.picker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        deviceDetail = this.binding.body;
        // setViewModel
        this.deviceViewModel = new DeviceViewModel();
        deviceDetail.setDeviceVM(this.deviceViewModel);

        // Set first Device To DeviceDetail
        this.deviceDetail.setDevice(this.mViewModel.house.findDevice(1));


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable DeviceMinimalAdapter.DeviceMinimalAdapterViewHolder holder, int adapterPosition) {
        //viewHolder will never be null, because we never remove items from adapter's list
        if (holder != null) {
            //this.deviceDetailView.setDevice(this.mViewModel.house.devicesList.get(adapterPosition));
            this.deviceDetail.setDevice(this.mViewModel.house.findDevice(adapterPosition));
            holder.showText();
        }
    }

    @Override
    public void onScrollStart(@NonNull DeviceMinimalAdapter.DeviceMinimalAdapterViewHolder currentItemHolder, int adapterPosition) {
        currentItemHolder.hideText();
    }

    @Override
    public void onScrollEnd(@NonNull DeviceMinimalAdapter.DeviceMinimalAdapterViewHolder currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable DeviceMinimalAdapter.DeviceMinimalAdapterViewHolder currentHolder, @Nullable DeviceMinimalAdapter.DeviceMinimalAdapterViewHolder newCurrent) {

    }
}
