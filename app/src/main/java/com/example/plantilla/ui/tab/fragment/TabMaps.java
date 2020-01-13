package com.example.plantilla.ui.tab.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantilla.R;
import com.example.plantilla.mapa.activity.MapActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.maps.GeoApiContext;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.RoadsApi;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.SnappedPoint;
import com.google.maps.model.SpeedLimit;
import com.gsanthosh91.decoderoutekey.DecodeAddress;
import com.gsanthosh91.decoderoutekey.DecodeRoute;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.LongSparseArray;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Roads API Demo App.
 *
 * Before you can start, you will need to obtain the relevant keys and add them to the api_keys.xml
 * file. The steps are detailed in the README file in the top level of this package.
 *
 * This app will load a map with 3 buttons. Press each of the buttons in sequence to demonstrate
 * various features of the Roads API and the supporting demo snippets.
 *
 * Find out more about the Roads API here: https://developers.google.com/maps/documentation/roads
 */
public class TabMaps extends Fragment implements OnMapReadyCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View view;
    public static TabMaps newInstance(int sectionNumber) {
        TabMaps fragment = new TabMaps();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño de este fragmento.
        view = inflater.inflate(R.layout.activity_map, container, false);

        // mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //mContext = new GeoApiContext().setApiKey(getString(R.string.google_maps_key));

        return view;
    }

    public void onMapReady(GoogleMap googleMap) {
        DecodeRoute decodeRoute = new DecodeRoute(getContext(), googleMap, "{punAgqyhNIgAWAeE[iBKLh@VdA\\z@t@rALr@SrDdC@BHj@lDPvB?t@Ax@DRBf@Df@f@lFdHcBp@QnDy@jBi@bCiA|E_CrDaBhAu@BIWQ_BmCQF_C~@");
        decodeRoute.setSourceAddress(new DecodeAddress("Prestige palladium bayan", "4 mins"));
        decodeRoute.setDestinationAddress(new DecodeAddress("Anna nagar west", null));
        decodeRoute.start();
    }
}
