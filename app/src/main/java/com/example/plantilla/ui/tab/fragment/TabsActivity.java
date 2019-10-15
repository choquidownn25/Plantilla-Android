package com.example.plantilla.ui.tab.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.plantilla.R;
import com.example.plantilla.ui.tab.adapter.SectionsPagerAdapters;


/**
        * El {@link android.support.v4.view.PagerAdapter} que proporcionará
        * Fragmentos para cada una de las secciones. Usamos un
        * {@link FragmentPagerAdapter} derivado, que mantendrá cada
        * Fragmento cargado en la memoria. Si esto se vuelve demasiado intensivo de memoria,
        * puede ser mejor cambiar a un
        * {@link android.support.v4.app.FragmentStatePagerAdapter}.
        */
public class TabsActivity  extends AppCompatActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private SectionsPagerAdapters mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} que albergará los contenidos de la sección.
     */
    private ViewPager mViewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Cree el adaptador que devolverá un fragmento para cada uno de los tres
        // Secciones primarias de la actividad.
        mSectionsPagerAdapter = new SectionsPagerAdapters(getSupportFragmentManager());

        // Configure el ViewPager con el adaptador de secciones.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        getSupportActionBar().setSelectedNavigationItem(i);

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
