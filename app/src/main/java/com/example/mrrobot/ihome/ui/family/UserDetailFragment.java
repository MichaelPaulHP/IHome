package com.example.mrrobot.ihome.ui.family;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mrrobot.ihome.R;
import com.github.zagum.switchicon.SwitchIconView;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment  implements View.OnClickListener{

    CircleImageView imageUser;// image of user
    TextView nameUser;// username
    SwitchIconView chatOption;// option for chat
    SwitchIconView locationOption;// option for location

    ChatFragment chatFragment;
    LocationFragment locationFragment;

    FrameLayout frameLayout;

    boolean qwe;

    public UserDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.chatFragment= new ChatFragment();
        this.locationFragment = new LocationFragment();// get location
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view   =  inflater.inflate(R.layout.fragment_user_detail, container, false);

        this.frameLayout =(FrameLayout)view.findViewById(R.id.plusOptionFrameLayout);
        //this.frameLayout.setVisibility(View.GONE);
        this.chatOption =(SwitchIconView)view.findViewById(R.id.chatSwitchIcon);
        this.locationOption =(SwitchIconView)view.findViewById(R.id.locationSwitchIcon);
        // on click
        this.chatOption.setOnClickListener(this);
        this.locationOption.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.locationSwitchIcon:
                // location fragment;
                toggleFrameLayout(this.locationOption,this.chatOption);
                setFragment(this.locationFragment);
                return;
            case R.id.chatSwitchIcon:
                // show chat fragment
                toggleFrameLayout(this.chatOption,this.locationOption);
                setFragment(this.chatFragment);
                return;

        }
    }
    private void toggleFrameLayout(SwitchIconView x,SwitchIconView y ){
        // x is click
        if(this.frameLayout.getVisibility()==View.VISIBLE){
            if(x.isIconEnabled()){
                this.frameLayout.setVisibility(View.GONE);
                x.setIconEnabled(false);

            }else{
                //si esta abierto pero x no esta activo
                // sigua visible
                x.setIconEnabled(true);
                y.setIconEnabled(false);
            }
        }
        else{
            this.frameLayout.setVisibility(View.VISIBLE);
            x.setIconEnabled(true);
        }

    }
    private  void setFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.plusOptionFrameLayout,fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public interface UserDatailListener{
        public void setLocation();
        public void setImage();
        public void setNameUser();
    }
}
