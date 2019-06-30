package com.example.plantilla.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.plantilla.ui.activity.MainActivity_Copidrogras_Col;
import com.example.plantilla.ui.fragment.PlaceholderFragment;
import com.example.plantilla.ui.fragment.RecycleSegViewFragment;
import com.example.plantilla.ui.fragment.RecycleViewFragment;

/**
 * @author YanLu
 * @since 17/9/3
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:

                return PlaceholderFragment.newInstance(position);
            case 1:
                return  MainActivity_Copidrogras_Col.newInstance(position);
            case 2:
                return RecycleSegViewFragment.newInstance(position);
            case 3:
                return RecycleViewFragment.newInstance(position + 1);
            default:
                return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Top corner";
            case 1:
                return "Bottom corner";
            case 2:
                return "Full corner";
            case 3:
                return "RecycleView";
        }
        return null;
    }
}
