package com.example.mrrobot.ihome.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mrrobot.ihome.ui.family.LocationFragment;
import com.example.mrrobot.ihome.R;

/**
 *no utilizado.
 */

public class FamilyFragment extends Fragment  implements View.OnClickListener{


    private FloatingActionButton locationFab;

    private static boolean forToogle;// true ? chat:location
    private static LocationFragment locationFragment;

    ViewPager vpPager;
    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.forToogle=true;
        this.locationFragment = new LocationFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_family, container, false);
        initUI(view);

        toggleOption();

        return view;
    }
    private void initUI(View view){
        // Float Button
        this.locationFab= (FloatingActionButton) view.findViewById(R.id.locationFab);
        this.locationFab.setOnClickListener(this);

        vpPager = (ViewPager) view.findViewById(R.id.familyViewPager);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getFragmentManager());
        vpPager.setAdapter(adapterViewPager);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.locationFab:
                // show map or chat

                toggleOption();

                return ;
        }
    }
    private void toggleOption(){
        this.forToogle=!forToogle;
       // fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(this.forToogle){
            //fragmentTransaction.replace(R.id.familyFrameLayout,this.chatFragment);
            this.locationFab.setImageResource(R.drawable.ic_location_on_black_24dp);
        }
        else{
           // fragmentTransaction.replace(R.id.familyFrameLayout,this.locationFragment);
            this.locationFab.setImageResource(R.drawable.ic_chat_black_24dp);
        }
        vpPager.setCurrentItem(this.forToogle? 1:0);
        //fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.commit();
    }
    /*
    private void initUI(View view){
        final NavigationTabBar navigationTabBar = (NavigationTabBar)view.findViewById(R.id.ntb_vertical);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();


        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_person_black_24dp),
                        Color.WHITE)
                        .title("All")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_search_black_24dp),
                        Color.WHITE)
                        .title("Maria")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                String  title =  model.getTitle();
                switch (title){
                    case "all":
                        FragmentTransaction fragmentTransaction;
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.familyFrameLayout,new ChatFragment());
                        //fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    case "Maria":
                        FragmentTransaction fragmentTransaction2;
                        fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.add(R.id.familyFrameLayout,new LocationFragment());
                        //fragmentTransaction.addToBackStack(null);
                        fragmentTransaction2.commit();
                }
        }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });
    }
    public  class SeccionPageAdaptar extends FragmentPagerAdapter{

        public SeccionPageAdaptar(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
    */

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            forToogle=!forToogle;
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return locationFragment;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return locationFragment;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
