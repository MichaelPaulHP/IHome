package com.example.mrrobot.ihome.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mrrobot.ihome.DialogsFragment.ThemeDialogFragment;
import com.example.mrrobot.ihome.Firebase.Auth;
import com.example.mrrobot.ihome.models.User;
import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.Services.GlideApp;
import com.github.zagum.switchicon.SwitchIconView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecurityFragment extends Fragment implements View.OnClickListener{

    private Auth user;

    CircleImageView userImage;
    private SwitchIconView swNotification;
    ImageButton configButton;
    public SecurityFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.user=Auth.getInstance();

        View view = inflater.inflate(R.layout.fragment_security, container, false);
        // user image
        userImage = (CircleImageView)view.findViewById(R.id.circleImageUser);
        // btn config
        view.findViewById(R.id.configButton).setOnClickListener(this);
        // notification
        view.findViewById(R.id.notificationSwContainer).setOnClickListener(this);
        swNotification = (SwitchIconView)view.findViewById(R.id.swNotification);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateImageUser();


    }

    private void updateImageUser(){
        if(user !=null){
            GlideApp
                    .with(this)
                    .load(user.getPhotoUrl())
                    .centerCrop()
                    .into(this.userImage);

        }
    }
    private void showDialogTheme(){
        DialogFragment themeDialogFragment = new ThemeDialogFragment();

        themeDialogFragment.show(getFragmentManager(), "themeDialogFragment");

    }

    @Override
    public void onClick(View view) {

        int id= view.getId();
        switch (id) {
            case R.id.configButton:
                showDialogTheme();
                break;
            case R.id.notificationSwContainer:
                this.swNotification.switchState();
                break;
        }

    }
}
