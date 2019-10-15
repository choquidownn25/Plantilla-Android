package com.example.plantilla.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.plantilla.mapa.activity.MapActivity;
import com.example.plantilla.mapa.activity.MapsActivity;
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
        // Se llama a getItem para crear una instancia del fragmento para la página dada.
        switch (position) {
            case 0:

                return PlaceholderFragment.newInstance(position);
            case 1:
                //return  MainActivity_Copidrogras_Col.newInstance(position);
                return MapsActivity.newInstance(position);
                //return MapActivity.newInstance(position);  MapasActivity

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
        // Mostrar 3  paginas.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Lista de ofertas";
            case 1:
                return "Geolocalización";
            case 2:
                return "Estado del servicio";
            case 3:
                return "Calcular Costo";
        }
        return null;
    }
}
