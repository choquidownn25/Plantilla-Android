package com.example.plantilla.ui.tab.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.plantilla.ui.fragment.RecycleSegViewFragment;
import com.example.plantilla.ui.tab.fragment.TabAFragment;
import com.example.plantilla.ui.tab.fragment.TabBFragment;
import com.example.plantilla.ui.tab.fragment.TabCFragment;

public class SectionsPagerAdapters extends FragmentPagerAdapter {
    public SectionsPagerAdapters(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment tabFragment = null;

        switch (i){
            case 0:
                tabFragment = new TabAFragment();
                break;
            case 1:
                tabFragment = new TabBFragment();
                break;
            case 2:
                tabFragment = new RecycleSegViewFragment();
                break;
        }
        return tabFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {

        String section = null;

        switch (position) {
            case 0:
                section = "Seccion 1";
                break;
            case 1:
                section = "Seccion 2";
                break;
            case 2:
                section = "Seccion 3";
                break;
        }
        return section;
    }
}
