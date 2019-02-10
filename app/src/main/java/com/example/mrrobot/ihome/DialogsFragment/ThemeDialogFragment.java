package com.example.mrrobot.ihome.DialogsFragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import com.example.mrrobot.ihome.R;
import com.example.mrrobot.ihome.Theme.StyleMapConstants;
import com.example.mrrobot.ihome.Theme.ThemeConstants;
import com.example.mrrobot.ihome.Theme.ThemeManager;
import com.github.zagum.switchicon.SwitchIconView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThemeDialogFragment extends DialogFragment implements
        View.OnClickListener,RadioGroup.OnCheckedChangeListener {


    private Button  saveButton ;
    private RadioGroup themesRadioGroup;
    private int themeSelected;
    private String themeMapSelected;
    private SwitchIconView botBarSwitchIcon;
    private SwitchIconView styleMapSwitchIcon;
    private ThemeManager themeManager;
    public ThemeDialogFragment() {
        // Required empty public constructor
    }
    /*
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.fragment_theme_dialog);

        this.button= (Button)dialog.findViewById(R.id.themeCurrent);

        this.button.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_theme_dialog, null));
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ThemeDialogFragment.this.getDialog().cancel();
            }
        });
        //return dialog;
        //return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }
    */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_theme_dialog, container, false);
       // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //return super.onCreateView(inflater, container, savedInstanceState);
        this.themeManager=ThemeManager.getInstance(getContext());
        // set themes in field
        this.themeSelected=this.themeManager.getCurrentTheme();
        this.themeMapSelected= this.themeManager.getCurrentThemeMap();
        return  v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // button fro save changes
        view.findViewById(R.id.saveButton).setOnClickListener(this);


            // is disable
        // for  mapStyle
        this.styleMapSwitchIcon = (SwitchIconView)view.findViewById(R.id.styleMapSwitchIcon);

        this.styleMapSwitchIcon.setIconEnabled(themeManager.getCurrentThemeMap().equals(StyleMapConstants.DARK));

        view.findViewById(R.id.styleMapContainer).setOnClickListener(this);

        // themes change
        this.themesRadioGroup= (RadioGroup)view.findViewById(R.id.themesRadioGroup);
        this.themesRadioGroup.setOnCheckedChangeListener(this);

    }


    /*

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof Activity) {
                Activity a = (Activity) context;
                themeDialogListener = (ThemeDialogListener)a;
            } else {
            throw new RuntimeException(context.toString()
                    + " must implement themeDialogListener");
            }
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            Log.d("ERRORRROR",e.getMessage());

        }
    }
    */
    @Override
    public void onDestroyView() {
        // if change
        super.onDestroyView();
    }


    @Override
    public void onClick(View view) {
        // v
        switch (view.getId()){
            case R.id.styleMapContainer:
                this.styleMapSwitchIcon.switchState();
                themeMapSelected= themeMapSelected.equals(StyleMapConstants.DARK)?
                        StyleMapConstants.LIGHT:StyleMapConstants.DARK;
                break;
            case R.id.saveButton:
                saveConfig();
                break;
        }

    }

    private void saveConfig() {
        boolean isReset= this.themeManager.isSave(themeSelected,this.themeMapSelected);
        if(isReset){
            // reset activity
            Intent intent =getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.DEFAULT:
                themeSelected= ThemeConstants.DEFAULT;
                return;
            case R.id.LIGHT:
                themeSelected=ThemeConstants.LIGHT;
                return ;
            case R.id.DARK:
                themeSelected=ThemeConstants.DARK;
                return ;
            case R.id.BLACK:
                themeSelected=ThemeConstants.BLACK;
                return ;
            case R.id.WHITE:
                themeSelected=ThemeConstants.WHITE;
                return ;
        }

    }
    /*
    public interface ThemeDialogListener{

        public boolean styleMap();
        public void saveChanges(int theme, boolean botNav,boolean mapStyle );
    }
    */


}
