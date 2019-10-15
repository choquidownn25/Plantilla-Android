package com.example.plantilla.mapa.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plantilla.R;
import com.example.plantilla.mapa.api.Conecion;
import com.example.plantilla.mapa.api.RetrofitMaps;
import com.example.plantilla.mapa.models.Mapa;
import com.example.plantilla.mapa.util.Helper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapasActivity extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    //<editor-fold desc="Atributos">
    private ArrayList<LatLng> markerPoints;
    private TextView tvDistanceDuration;
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String TAG = MapActivity.class.getSimpleName();
    private GoogleMap mMap;
    private List<LatLng> latLngList;
    private TextView distanceValue;
    private TextView durationValue;
    private Context contexts;
    // Latitude & Longitude
    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    private LatLng origin;
    private LatLng dest;
    Polyline line;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };
    private static final String[] CONTACTS_PERMS = {
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST = 1337;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;
    private static final int CONTACTS_REQUEST = INITIAL_REQUEST + 2;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;
    private int TAG_CODE_PERMISSION_LOCATION;
    //</editor-fold>

    public static MapasActivity newInstance(int sectionNumber) {
        MapasActivity fragment = new MapasActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);
        contexts = getActivity();

        distanceValue = (TextView) rootView.findViewById(R.id.distance_value);
        durationValue = (TextView) rootView.findViewById(R.id.duration_value);
        latLngList = new ArrayList<LatLng>();
        // Initializing
        markerPoints = new ArrayList<LatLng>();


        //show error dialog if Google Play Services not available
        if (!isGooglePlayServicesAvailable()) {
            Log.d("onCreate", "Google Play Services not available. Ending Test case.");

        }
        else {
            Log.d("onCreate", "Google Play Services available. Continuing.");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Getting Map for the SupportMapFragment
        //map = fm.getMap();
        mapFragment.getMapAsync(this);


        return rootView;
    }

    @Override
    public void onMapClick(LatLng latLng) {

        if (latLngList.size() > 1) {
            refreshMap(mMap);
            latLngList.clear();
            distanceValue.setText("");
            durationValue.setText("");
        }
        latLngList.add(latLng);
        Log.d(TAG, "Marker number " + latLngList.size());
        createMarker(latLng, latLngList.size());
        if (latLngList.size() == 2) {
            LatLng origin = latLngList.get(0);
            LatLng destination = latLngList.get(1);
            //use Google Direction API to get the route between these Locations
            String directionApiPath = Helper.getUrl(String.valueOf(origin.latitude), String.valueOf(origin.longitude),
                    String.valueOf(destination.latitude), String.valueOf(destination.longitude));
            Log.d(TAG, "Path " + directionApiPath);
            build_retrofit_and_get_response(directionApiPath);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng coordinate = new LatLng(Latitude, Longitude);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            // Toast.makeText(this, R.string.error_permission_map, Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions((Activity) getContext(), new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    TAG_CODE_PERMISSION_LOCATION);
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 11));

        mMap.setOnMapClickListener(this);
    }

    private void createMarker(LatLng latLng, int position) {
        MarkerOptions mOptions = new MarkerOptions().position(latLng);
        if (position == 1) {
            mOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        } else {
            mOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        addCameraToMap(latLng);
        mMap.addMarker(mOptions);
    }

    private void refreshMap(GoogleMap mapInstance) {
        mapInstance.clear();
    }


    private void addCameraToMap(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(8)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void build_retrofit_and_get_response(String type) {

//        String url = "https://maps.googleapis.com/maps/";
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        //RetrofitMaps service = retrofit.create(RetrofitMaps.class);
        RetrofitMaps service   = Conecion.getCliente().create(RetrofitMaps.class);
        Call<Mapa> call = service.getDistanceDuration("metric", origin.latitude + "," + origin.longitude,dest.latitude + "," + dest.longitude, type);

        call.enqueue(new Callback<Mapa>() {
            @Override
            public void onResponse(Call<Mapa> call, Response<Mapa> response) {
                try {
                    //Remove previous line from map
                    if (line != null) {
                        line.remove();
                    }
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                        //ShowDistanceDuration.setText("Distance:" + distance + ", Duration:" + time);
                        distanceValue.setText(distance);
                        durationValue.setText(time);
                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = decodePoly(encodedString);
                        line = mMap.addPolyline(new PolylineOptions()
                                .addAll(list)
                                .width(20)
                                .color(Color.RED)
                                .geodesic(true)
                        );
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Mapa> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

    // Checking if Google Play Services Available or not
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        0).show();
            }
            return false;
        }
        return true;
    }

}