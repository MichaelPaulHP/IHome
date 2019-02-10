package com.example.mrrobot.ihome.ui.device;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mrrobot.ihome.models.Device;
import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.databinding.LayoutDeviceBinding;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceAdapterViewHolder> {

    //private ObservableArrayMap<String,Device> devices;
    private List<Device> devices;
    private int layout;
    public DeviceAdapter() {
        //this.devices= new ArrayList<>();
        //this.layout=layout;
    }


    @NonNull
    @Override
    public DeviceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutDeviceBinding layoutDeviceBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_device,
                        parent, false);

        return new DeviceAdapterViewHolder(layoutDeviceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapterViewHolder holder, int position) {

        holder.bindDevice(devices.get(position));
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }


    @Override
    public int getItemCount() {
        return this.devices == null ? 0 : this.devices.size();
    }


    public static class DeviceAdapterViewHolder extends RecyclerView.ViewHolder {
        private LayoutDeviceBinding layoutDeviceBinding;

        public DeviceAdapterViewHolder(LayoutDeviceBinding layoutDeviceBinding) {
            super(layoutDeviceBinding.getRoot());
            this.layoutDeviceBinding = layoutDeviceBinding;

        }

        public void bindDevice(Device device) {
            if (this.layoutDeviceBinding.getDevice() == null) {
                this.layoutDeviceBinding.setDevice(device);
            }

        }
    }


}
