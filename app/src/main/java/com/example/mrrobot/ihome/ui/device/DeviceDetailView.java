package com.example.mrrobot.ihome.ui.device;

import android.animation.ArgbEvaluator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.ArrayRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.databinding.DeviceBinding;
import com.example.mrrobot.ihome.databinding.DeviceDetailBinding;
import com.example.mrrobot.ihome.models.Device;

public class DeviceDetailView extends LinearLayout {

    private Paint gradientPaint;
    private int[] currentGradient;

    private TextView weatherDescription;
    private TextView weatherTemperature;
    private ImageView weatherImage;

    private ArgbEvaluator evaluator;

    public DeviceDetailView(Context context) {
        super(context);
    }

    public DeviceDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DeviceDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DeviceDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    */

    private final DeviceDetailBinding binding;
    private DeviceViewModel deviceViewModel;
    DeviceBinding qwe;

    {
        evaluator = new ArgbEvaluator();

        gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setWillNotDraw(false);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        //inflate(getContext(), R.layout.device_detail, this);

        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.device_detail,this,true);
         /*this.binding = DataBindingUtil.inflate(
                inflater, R.layout.device_detail, this, false);*/
            this.binding = DeviceDetailBinding.inflate(inflater,this,true);
         this.deviceViewModel= new DeviceViewModel();


         this.binding.setDeviceVM(deviceViewModel);
//        weatherDescription = (TextView) findViewById(R.id.weather_description);
//        weatherImage = (ImageView) findViewById(R.id.weather_image);
//        weatherTemperature = (TextView) findViewById(R.id.weather_temperature);


    }

    private void initGradient() {
        float centerX = getWidth() * 0.5f;
        Shader gradient = new LinearGradient(
                centerX, 0, centerX, getHeight(),
                currentGradient, null,
                Shader.TileMode.MIRROR);
        gradientPaint.setShader(gradient);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (currentGradient != null) {
            initGradient();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), gradientPaint);
        super.onDraw(canvas);
    }

    public void setDevice(Device device) {
        //Weather weather = forecast.getWeather();
//        this.deviceViewModel.device=device;
//        this.binding.setDevice(this.deviceViewModel.device);
        this.qwe.setDevice(device);
        currentGradient = weatherToGradient();
        if (getWidth() != 0 && getHeight() != 0) {
            initGradient();
        }
        weatherDescription.setText(device.getMessage());
        weatherTemperature.setText(device.getValue());
        //Glide.with(getContext()).load(weatherToIcon(weather)).into(weatherImage);
        weatherImage.setImageResource(device.getIconState());
        invalidate();

        weatherImage.animate()
                .scaleX(1f).scaleY(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(300)
                .start();
    }

    public void onScroll(float fraction, Device oldF, Device newF) {
        weatherImage.setScaleX(fraction);
        weatherImage.setScaleY(fraction);
        /*currentGradient = mix(fraction,
                weatherToGradient(newF.getWeather()),
                weatherToGradient(oldF.getWeather()));
        */
        currentGradient = mix(fraction,
                weatherToGradient(),
                weatherToGradient());
        initGradient();
        invalidate();
    }

    private int[] mix(float fraction, int[] c1, int[] c2) {
        return new int[]{
                (Integer) evaluator.evaluate(fraction, c1[0], c2[0]),
                (Integer) evaluator.evaluate(fraction, c1[1], c2[1]),
                (Integer) evaluator.evaluate(fraction, c1[2], c2[2])
        };
    }

    private int[] weatherToGradient() {
        return colors(R.array.gradientClear);
        /*switch (weather) {
            case PERIODIC_CLOUDS:
                return colors(R.array.gradientPeriodicClouds);
            case CLOUDY:
                return colors(R.array.gradientCloudy);
            case MOSTLY_CLOUDY:
                return colors(R.array.gradientMostlyCloudy);
            case PARTLY_CLOUDY:
                return colors(R.array.gradientPartlyCloudy);
            case CLEAR:
                return colors(R.array.gradientClear);
            default:
                return colors(R.array.gradientClear);
        }*/
    }

    private int weatherToIcon(Device device) {
        /*switch (weather) {
            case PERIODIC_CLOUDS:
                return R.drawable.periodic_clouds;
            case CLOUDY:
                return R.drawable.cloudy;
            case MOSTLY_CLOUDY:
                return R.drawable.mostly_cloudy;
            case PARTLY_CLOUDY:
                return R.drawable.partly_cloudy;
            case CLEAR:
                return R.drawable.clear;
            default:
                throw new IllegalArgumentException();
        }*/
        return device.getIconState();
    }

    private int[] colors(@ArrayRes int res) {
        return getContext().getResources().getIntArray(res);
    }
}
