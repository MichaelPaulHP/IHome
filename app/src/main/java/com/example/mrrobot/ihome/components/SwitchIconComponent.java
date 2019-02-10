package com.example.mrrobot.ihome.components;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.graphics.drawable.Icon;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrrobot.ihome.R;


import com.example.mrrobot.ihome.models.Device;
import com.github.zagum.switchicon.SwitchIconView;
import com.victor.loading.rotate.RotateLoading;

import java.util.Observer;
import java.util.zip.Inflater;

public class SwitchIconComponent extends LinearLayout implements View.OnClickListener {

    LinearLayout layout;
    TextView textView; // title
    RotateLoading rotateLoading;
    SwitchIconView switchIconView; //sw
    Context context;

    private boolean isActive = false;// false: disable, true:enable

    SwitchIconListener listener;


    public SwitchIconComponent(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SwitchIconComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

    }

    public SwitchIconComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public SwitchIconComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }


    private void init() {

        // add layout

//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.switch_icon_component,this,false);
        /*this.binding = DataBindingUtil.inflate(
                LayoutInflater.from(this.context), R.layout.switch_icon_component, this, false);
        */
        /*this.binding = DataBindingUtil.inflate(
                inflater, R.layout.switch_icon_component, this, false);

        this.binding.setSwIcon(this);*/

        View rootView = inflate(context, R.layout.switch_icon_component, this);
        initComponents(rootView);
        setEvents();

    }

    private void initComponents(View rootView) {

        /*this.textView = this.binding.swicTitle;
        this.switchIconView = this.binding.swicSwitchIcon;
        this.rotateLoading = this.binding.swicLoading;
        this.layout=this.binding.swicContainer;*/

//        this.textView = findViewById(R.id.swicTitle);
//        this.switchIconView = findViewById(R.id.swicSwitchIcon);
//        this.rotateLoading = findViewById(R.id.swicLoading);
//        this.layout=findViewById(R.id.swicContainer);
        this.textView = rootView.findViewById(R.id.swicTitle);
        this.switchIconView = rootView.findViewById(R.id.swicSwitchIcon);
        this.rotateLoading = rootView.findViewById(R.id.swicLoading);
        this.layout = rootView.findViewById(R.id.swicContainer);

    }

    private void setEvents() {
        this.layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (this.listener != null) {
            if (view.equals(this.layout)) {
                this.listener.onClick(this);
            }
        }
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.switchIconView.setIconEnabled(active);
        isActive = active;
    }

    public void setIcon(int icon) {

        this.switchIconView.setImageResource(R.drawable.ic_fiber_manual_record_black_24dp);
        //this.switchIconView.setImageIcon(new Icon());

    }

    public void setTitle(String title) {
        this.textView.setText(title);

    }

    public void startLoading() {
        this.rotateLoading.start();
        this.switchIconView.setVisibility(GONE);
    }

    public void stopLoading() {
        this.rotateLoading.stop();
        this.switchIconView.setVisibility(VISIBLE);
    }

    public void setListener(SwitchIconListener listener) {
        this.listener = listener;
    }

    public interface SwitchIconListener {
        public void onClick(View view);

        public void onChangeState(View view);
    }

}
