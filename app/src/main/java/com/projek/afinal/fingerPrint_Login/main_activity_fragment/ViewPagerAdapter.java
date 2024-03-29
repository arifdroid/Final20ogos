package com.projek.afinal.fingerPrint_Login.main_activity_fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);

    }

    @Override
    public Fragment getItem(int i) {

        return fragmentArrayList.get(i);
    }

    @Override
    public int getCount() {

        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment){

        fragmentArrayList.add(fragment);
    }

}
