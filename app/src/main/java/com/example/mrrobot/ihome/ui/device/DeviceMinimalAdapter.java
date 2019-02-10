package com.example.mrrobot.ihome.ui.device;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.Theme.ThemeManager;
import com.example.mrrobot.ihome.models.Device;

import java.util.List;

public class DeviceMinimalAdapter
        extends RecyclerView.Adapter<DeviceMinimalAdapter.DeviceMinimalAdapterViewHolder> {

    private List<Device> devices;
    private static RecyclerView parentRecycler;

    public DeviceMinimalAdapter(List<Device> devices) {

        this.devices = devices;

    }

    @NonNull
    @Override
    public DeviceMinimalAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.device_minimal, parent, false);
        return new DeviceMinimalAdapterViewHolder(v);

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler=recyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceMinimalAdapterViewHolder holder, int position) {
        Device device = this.devices.get(position);

        //int iconTint = ContextCompat.getColor(holder.itemView.getContext(), R.color.grayIconTint);

        holder.imageView.setImageResource(device.getIcon());
        /*Forecast forecast = data.get(position);
        Glide.with(holder.itemView.getContext())
                .load(forecast.getCityIcon())
                .listener(new TintOnLoad(holder.imageView, iconTint))
                .into(holder.imageView);
        */
        holder.nameTextView.setText(device.getName());


    }

    @Override
    public int getItemCount() {
        return this.devices.size();
    }


    // HOLDER
    public static class DeviceMinimalAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameTextView;
        private ImageView imageView;
        private ThemeManager themeManager;
        public DeviceMinimalAdapterViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.device_image);
            this.nameTextView = itemView.findViewById(R.id.device_name);

            itemView.findViewById(R.id.device_container).setOnClickListener(this);

            this.themeManager=ThemeManager.getInstance(itemView.getContext());
        }

        @Override
        public void onClick(View view) {
            parentRecycler.smoothScrollToPosition(getAdapterPosition());
            // re
        }

        public void showText() {
            int parentHeight = ((View) imageView.getParent()).getHeight();
            float scale = (parentHeight - nameTextView.getHeight()) / (float) imageView.getHeight();
            imageView.setPivotX(imageView.getWidth() * 0.5f);
            imageView.setPivotY(0);
            imageView.animate().scaleX(scale)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            nameTextView.setVisibility(View.VISIBLE);
                            imageView.setColorFilter(themeManager.getItemOfStyle(R.attr.color_accent));
                        }
                    })
                    .scaleY(scale).setDuration(200)
                    .start();

        }

        public void hideText() {

            //imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.attr.color_disable));
            imageView.setColorFilter(themeManager.getItemOfStyle(R.attr.color_disable));
            nameTextView.setVisibility(View.INVISIBLE);
            imageView.animate().scaleX(1f).scaleY(1f)
                    .setDuration(200)
                    .start();
            //imageView.getContext().getTheme().getResources().getColor(R.attr.color_disable,this.)
        }

    }
}
