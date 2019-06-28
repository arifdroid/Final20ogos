package com.example.afinal.fingerPrint_Login.main_activity_fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.afinal.R;

public class Main_BottomNav_Activity extends AppCompatActivity {


    private FragmentTimeStamp fragmentTimeStamp;
    private FragmentUserScore fragmentUserScore;

    private ViewPager viewPager;

    private MenuItem prevMenu;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_checkadmin:
                    return true;
                case R.id.navigation_registeruser:
                    return true;
            }
            return false;
        }
    };

    //17 june

    public static String dayToday;
    public static String dateToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__bottom_nav_);

        viewPager = findViewById(R.id.bottomNav_view_pager_frameID);

        Intent intent = getIntent();

        dayToday = intent.getStringExtra("today");
        dateToday= intent.getStringExtra("date");

        setupViewPager(viewPager);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNav_view_pager_menuOptionsiD);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


                if(prevMenu!=null){

                    prevMenu.setChecked(false);

                }else {

                    navigation.getMenu().getItem(0).setChecked(false);

                }

                navigation.getMenu().getItem(i).setChecked(true);
                prevMenu = navigation.getMenu().getItem(i);


            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        fragmentTimeStamp = new FragmentTimeStamp();
        fragmentUserScore = new FragmentUserScore();

        viewPagerAdapter.addFragment(fragmentTimeStamp);
        viewPagerAdapter.addFragment(fragmentUserScore);

        viewPager.setAdapter(viewPagerAdapter);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        moveTaskToBack(false);

    }


}
