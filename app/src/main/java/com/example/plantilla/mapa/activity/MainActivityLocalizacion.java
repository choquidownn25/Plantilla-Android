package com.example.plantilla.mapa.activity;

import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.plantilla.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DESARROLLADOR_JOSE on 12/1/2018.
 * Clase que mostra el json en google map de nuestro server
 */
public class MainActivityLocalizacion extends AppCompatActivity implements OnMapReadyCallback {

    // Google Map
    private GoogleMap googleMap;

    // Latitude & Longitude
    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    Location mCurrentLocation;
    ArrayList<HashMap<String, String>> location = null;
    //Creacion de la actividad
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);


        //*** Permiso versiones antiguas
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setContentView(R.layout.activity_main_localizacion);
        //String url = "http://www.thaicreate.com/android/getLatLon.php";
        //String url = "http://181.57.139.182:83/wilson/api/Localizaciones/GellAll";
        String url = "http://sigti.webcol.net/directv/getLatLon.php";
        try {

            JSONArray data = new JSONArray(getHttpGet(url));

            location = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
//                map.put("IdLocalizacion", c.getString("IdLocalizacion"));
//                map.put("Latitude", c.getString("Latitude"));
//                map.put("Longitude", c.getString("Longitude"));
//                map.put("IdUser", c.getString("IdUser"));


                map.put("id", c.getString("id"));
                map.put("lat", c.getString("lat"));
                map.put("lon", c.getString("lon"));
                map.put("nombre", c.getString("nombre"));

                location.add(map);

            }




            // *** Display Google Map
            //googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMapAsync();
            SupportMapFragment googleMap = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            googleMap.getMapAsync(this);
            //((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMapAsync(this);

            // *** Focus & Zoom


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.e("Log", "Failed to download result..");
            e.printStackTrace();

            Toast.makeText(getApplicationContext(),  "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Metodo get del servidor
    public static String getHttpGet(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Si hay respuesta OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        Latitude = Double.parseDouble(location.get(0).get("lat").toString());
        Longitude = Double.parseDouble(location.get(0).get("lon").toString());
        LatLng coordinate = new LatLng(Latitude, Longitude);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 11));

        // *** Marker (Loop)
        for (int i = 0; i < location.size(); i++) {
            Latitude = Double.parseDouble(location.get(i).get("lat").toString());
            Longitude = Double.parseDouble(location.get(i).get("lon").toString());
            String name = location.get(i).get("nombre").toString();
            MarkerOptions marker = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(name);
            googleMap.addMarker(marker);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
