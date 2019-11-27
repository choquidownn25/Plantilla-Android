/*
 *  Copyright 2014 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package com.example.plantilla.webrtc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantilla.R;
import com.example.plantilla.error.MessajeError;
import com.example.plantilla.webrtc.api.ApiCliente;
import com.example.plantilla.webrtc.api.IVideo;
import com.example.plantilla.webrtc.datos.Persona;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Handles the initial setup where the user selects which room to join.
 * AppCompatActivity
 */
public class ConnectActivity extends AppCompatActivity {
  public static final String RAUL = "Raul";
  private static final String TAG = "ConnectActivity";
  private static final int CONNECTION_REQUEST = 1;
  private static final int REMOVE_FAVORITE_INDEX = 0;
  private static boolean commandLineRun = false;

  private ImageButton connectButton;
  private ImageButton addFavoriteButton;
  private EditText roomEditText;
  private ListView roomListView;
  private SharedPreferences sharedPref;
  private String keyprefVideoCallEnabled;
  private String keyprefCamera2;
  private String keyprefResolution;
  private String keyprefFps;
  private String keyprefCaptureQualitySlider;
  private String keyprefVideoBitrateType;
  private String keyprefVideoBitrateValue;
  private String keyprefVideoCodec;
  private String keyprefAudioBitrateType;
  private String keyprefAudioBitrateValue;
  private String keyprefAudioCodec;
  private String keyprefHwCodecAcceleration;
  private String keyprefCaptureToTexture;
  private String keyprefNoAudioProcessingPipeline;
  private String keyprefAecDump;
  private String keyprefOpenSLES;
  private String keyprefDisableBuiltInAec;
  private String keyprefDisableBuiltInAgc;
  private String keyprefDisableBuiltInNs;
  private String keyprefEnableLevelControl;
  private String keyprefDisplayHud;
  private String keyprefTracing;
  private String keyprefRoomServerUrl;
  private String keyprefRoom;
  private String keyprefRoomList;
  private ArrayList<String> roomList;
  private ArrayAdapter<String> adapter;

  private ArrayList<Persona> roomLists;
  private ArrayList<Persona> listapersonas;
  private ArrayAdapter<Persona> adapters;
  private ImageButton add_favorite_chat;

  static LocationManager locationManager;
  static MyLocationListener miLocationListener;
  private LocationManager locManager;
  private LocationListener locListener;

  private String horas;
  private String fecha;
  private String lat;
  private String lon;
  private String latitud;
  private String longitud;
  private String rehoras;
  private String username;
  private float latitudes;
  private float longitudes;
  private TextView txtgps;
  private TextView txtgpslongitud;
  private ImageButton googlemapsicno;
  // private List<NameValuePair> nameValuePairs;
  static final String RED = "Interferencia";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    // Get setting keys.
    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    keyprefVideoCallEnabled = getString(R.string.pref_videocall_key);
    keyprefCamera2 = getString(R.string.pref_camera2_key);
    keyprefResolution = getString(R.string.pref_resolution_key);
    keyprefFps = getString(R.string.pref_fps_key);
    keyprefCaptureQualitySlider = getString(R.string.pref_capturequalityslider_key);
    keyprefVideoBitrateType = getString(R.string.pref_startvideobitrate_key);
    keyprefVideoBitrateValue = getString(R.string.pref_startvideobitratevalue_key);
    keyprefVideoCodec = getString(R.string.pref_videocodec_key);
    keyprefHwCodecAcceleration = getString(R.string.pref_hwcodec_key);
    keyprefCaptureToTexture = getString(R.string.pref_capturetotexture_key);
    keyprefAudioBitrateType = getString(R.string.pref_startaudiobitrate_key);
    keyprefAudioBitrateValue = getString(R.string.pref_startaudiobitratevalue_key);
    keyprefAudioCodec = getString(R.string.pref_audiocodec_key);
    keyprefNoAudioProcessingPipeline = getString(R.string.pref_noaudioprocessing_key);
    keyprefAecDump = getString(R.string.pref_aecdump_key);
    keyprefOpenSLES = getString(R.string.pref_opensles_key);
    keyprefDisableBuiltInAec = getString(R.string.pref_disable_built_in_aec_key);

    keyprefDisableBuiltInAgc = getString(R.string.pref_disable_built_in_agc_key);

    keyprefDisableBuiltInNs = getString(R.string.pref_disable_built_in_ns_key);
    keyprefEnableLevelControl = getString(R.string.pref_enable_level_control_key);
    keyprefDisplayHud = getString(R.string.pref_displayhud_key);
    keyprefTracing = getString(R.string.pref_tracing_key);
    keyprefRoomServerUrl = getString(R.string.pref_room_server_url_key);
    keyprefRoom = getString(R.string.pref_room_key);
    keyprefRoomList = getString(R.string.pref_room_list_key);

    setContentView(R.layout.activity_connect);
    Context context = getApplicationContext();
    //comenzarLocalizacion();
    new Insertar(ConnectActivity.this).execute();
    roomEditText = (EditText) findViewById(R.id.room_edittext);
//    Context context = getApplicationContext();
    roomEditText.setOnEditorActionListener(
            new TextView.OnEditorActionListener() {
              @Override
              public boolean onEditorAction(
                      TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                  addFavoriteButton.performClick();
                  return true;
                }
                return false;
              }
            });
    roomEditText.requestFocus();

    roomListView = (ListView) findViewById(R.id.room_listview);
    roomListView.setEmptyView(findViewById(android.R.id.empty));

    add_favorite_chat = (ImageButton) findViewById(R.id.add_favorite_chat);
    add_favorite_chat.setOnClickListener(new View.OnClickListener()   {

      @Override
      public void onClick(View v) {
       /* Intent intent  = new Intent(ConnectActivity.this, SplashActivityChat.class);
        startActivity(intent);*/
      }
    }  );

    googlemapsicno  = (ImageButton) findViewById(R.id.add_favorite_mapa);
    googlemapsicno.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        /*Intent intents  = new Intent(ConnectActivity.this, MainActivityLocalizacion.class);
        startActivity(intents);*/
      }
    });


    roomListView.setOnItemClickListener(roomListClickListener);
    registerForContextMenu(roomListView);
    connectButton = (ImageButton) findViewById(R.id.connect_button);
    connectButton.setOnClickListener(connectListener);
    addFavoriteButton = (ImageButton) findViewById(R.id.add_favorite_button);
    addFavoriteButton.setOnClickListener(addFavoriteListener);

    // If an implicit VIEW intent is launching the app, go directly to that URL.
    final Intent intent = getIntent();
    if ("android.intent.action.VIEW".equals(intent.getAction())
            && !commandLineRun) {
      boolean loopback = intent.getBooleanExtra(
              CallActivity.EXTRA_LOOPBACK, false);
      int runTimeMs = intent.getIntExtra(
              CallActivity.EXTRA_RUNTIME, 0);
      String room = sharedPref.getString(keyprefRoom, "");
      connectToRoom(room, true, loopback, runTimeMs);
    }

    txtgps = (TextView)findViewById(R.id.txtgps);
    txtgpslongitud = (TextView)findViewById(R.id.txtgpslongitud);
    txtgps.setText("Latitud " + latitud);
    txtgpslongitud.setText( "Longitud " + longitud);
  }

  //region GPS
  //Metodo de localizaicon GPS
  @SuppressLint("MissingPermission")
  public void comenzarLocalizacion()
  {
    //Obtenemos una referencia al LocationManager
    locManager =
            (LocationManager)getSystemService(Context.LOCATION_SERVICE);

    //Obtenemos la ?ltima posici?n conocida
    @SuppressLint("MissingPermission") Location loc =
            locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    //Mostramos la ?ltima posici?n conocida
    mostrarPosicion(loc);

    //Nos registramos para recibir actualizaciones de la posici?n
    locListener = new LocationListener() {
      public void onLocationChanged(Location location) {
        mostrarPosicion(location);
      }
      public void onProviderDisabled(String provider){
        // lblEstado.setText("Provider OFF");
      }
      public void onProviderEnabled(String provider){
        //lblEstado.setText("Provider ON ");
      }
      public void onStatusChanged(String provider, int status, Bundle extras){
        Log.i("", "Provider Status: " + status);
        //lblEstado.setText("Provider Status: " + status);
      }
    };

    locManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 30000, 0, locListener);
  }

  //Metodo para mostrar la posicion del gps
  private void mostrarPosicion(Location loc) {
    username="Prueba";
    if(loc != null)
    {
      //latitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
      latitud = String.valueOf(loc.getLatitude());
      //longitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
      longitud = String.valueOf(loc.getLongitude());

      latitudes = Float.valueOf((float) loc.getLatitude());
      longitudes = Float.valueOf((float) loc.getLongitude());
      //lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
      Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
      //cargaJsonAutorizacion(latitud, longitud);

    }
    else
    {
      //latitud.setText("Latitud: (sin_datos)");
      latitud = "Latitud: (sin_datos)";
      //longitud.setText("L on: (sin_datos)");
      longitud = "Longitud sin Datos";

      latitudes = (float) 0.00;
      longitudes = (float) 0.00;
    }

//    insertar ();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.connect_menu, menu);
    return true;
  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    if (v.getId() == R.id.room_listview) {
      AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
      menu.setHeaderTitle(roomList.get(info.position));
      String[] menuItems = getResources().getStringArray(R.array.roomListContextMenu);
      for (int i = 0; i < menuItems.length; i++) {
        menu.add(Menu.NONE, i, i, menuItems[i]);
      }
    } else {
      super.onCreateContextMenu(menu, v, menuInfo);
    }
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    if (item.getItemId() == REMOVE_FAVORITE_INDEX) {
      AdapterView.AdapterContextMenuInfo info =
              (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
      roomList.remove(info.position);
      adapter.notifyDataSetChanged();
      return true;
    }

    return super.onContextItemSelected(item);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle presses on the action bar items.
    if (item.getItemId() == R.id.action_settings) {
      Intent intent = new Intent(this, SettingsActivity.class);
      startActivity(intent);
      return true;
    } else if (item.getItemId() == R.id.action_loopback) {
      connectToRoom(null, false, true, 0);
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    String room = roomEditText.getText().toString();
    String roomListJson = new JSONArray(roomList).toString();
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString(keyprefRoom, room);
    editor.putString(keyprefRoomList, roomListJson);
    editor.commit();
  }

  @Override
  public void onResume() {
    super.onResume();
    String room = sharedPref.getString(keyprefRoom, "");
    roomEditText.setText(room);
    roomList = new ArrayList<String>();


    roomList.add("General_102");
    roomList.add("Consultorio_3");
    roomList.add("Consultorio_4");
    roomList.add("Consultorio_5");
    roomList.add("Consultorio_6");



    //Lista de personas
    listapersonas=new ArrayList<Persona>();
    listapersonas.add(new Persona("Juan", 'm'));
    listapersonas.add(new Persona("pedro",'m'));
    listapersonas.add(new Persona("luis",'m'));
    listapersonas.add(new Persona("ana",'f'));
    listapersonas.add(new Persona("carla",'f'));
    listapersonas.add(new Persona("maria",'f'));
    listapersonas.add(new Persona("gustavo",'m'));
    listapersonas.add(new Persona("carlos",'m'));
    listapersonas.add(new Persona("marta",'f'));
    listapersonas.add(new Persona("luisa",'f'));
    listapersonas.add(new Persona("fernanda",'f'));
    listapersonas.add(new Persona("jose",'m'));



//      AdaptadorPersonas adaptador = new AdaptadorPersonas(this);

    String roomListJson = sharedPref.getString(keyprefRoomList, null);
    if (roomListJson != null) {
      try {
        JSONArray jsonArray = new JSONArray(roomListJson);
        for (int i = 0; i < jsonArray.length(); i++) {
          //roomList.add(jsonArray.get(i).toString()); //Adiciona lista
        }
      } catch (JSONException e) {
        Log.e(TAG, "Failed to load room list: " + e.toString());
      }
    }


    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, roomList){
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        // Set the color here
        textView.setTextColor(getContext().getResources().getColor(R.color.light_blue_900));
        return textView;
      }
    };
    //adapters = new ArrayAdapter<Persona>(this, android.R.layout.simple_list_item_1, roomLists);

    roomListView.setAdapter(adapter);
    //roomListView.setAdapter(adapters);

    if (adapter.getCount() > 0) {
      roomListView.requestFocus(); //Agrega a la lista
      roomListView.setItemChecked(0, true);
    }





  }


  private  void errorMessege() {
    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Oops...")
            .setContentText("Something went wrong!")
            .show();
  }
  @Override
  protected void onActivityResult(
          int requestCode, int resultCode, Intent data) {
    if (requestCode == CONNECTION_REQUEST && commandLineRun) {
      Log.d(TAG, "Return: " + resultCode);
      setResult(resultCode);
      commandLineRun = false;
      finish();
    }
  }

  private void connectToRoom(
          String roomId, boolean commandLineRun, boolean loopback, int runTimeMs) {
    this.commandLineRun = commandLineRun;

    // roomId is random for loopback.
    if (loopback) {
      roomId = Integer.toString((new Random()).nextInt(100000000));
    }

    String roomUrl = sharedPref.getString(
            keyprefRoomServerUrl,
            getString(R.string.pref_room_server_url_default));

    // Video call enabled flag.
    boolean videoCallEnabled = sharedPref.getBoolean(keyprefVideoCallEnabled,
            Boolean.valueOf(getString(R.string.pref_videocall_default)));

    // Use Camera2 option.
    boolean useCamera2 = sharedPref.getBoolean(keyprefCamera2,
            Boolean.valueOf(getString(R.string.pref_camera2_default)));

    // Get default codecs.
    String videoCodec = sharedPref.getString(keyprefVideoCodec,
            getString(R.string.pref_videocodec_default));
    String audioCodec = sharedPref.getString(keyprefAudioCodec,
            getString(R.string.pref_audiocodec_default));

    // Check HW codec flag.
    boolean hwCodec = sharedPref.getBoolean(keyprefHwCodecAcceleration,
            Boolean.valueOf(getString(R.string.pref_hwcodec_default)));

    // Check Capture to texture.
    boolean captureToTexture = sharedPref.getBoolean(keyprefCaptureToTexture,
            Boolean.valueOf(getString(R.string.pref_capturetotexture_default)));

    // Check Disable Audio Processing flag.
    boolean noAudioProcessing = sharedPref.getBoolean(
            keyprefNoAudioProcessingPipeline,
            Boolean.valueOf(getString(R.string.pref_noaudioprocessing_default)));

    // Check Disable Audio Processing flag.
    boolean aecDump = sharedPref.getBoolean(
            keyprefAecDump,
            Boolean.valueOf(getString(R.string.pref_aecdump_default)));

    // Check OpenSL ES enabled flag.
    boolean useOpenSLES = sharedPref.getBoolean(
            keyprefOpenSLES,
            Boolean.valueOf(getString(R.string.pref_opensles_default)));

    // Check Disable built-in AEC flag.
    boolean disableBuiltInAEC = sharedPref.getBoolean(
            keyprefDisableBuiltInAec,
            Boolean.valueOf(getString(R.string.pref_disable_built_in_aec_default)));

    // Check Disable built-in AGC flag.
    boolean disableBuiltInAGC = sharedPref.getBoolean(
            keyprefDisableBuiltInAgc,
            Boolean.valueOf(getString(R.string.pref_disable_built_in_agc_default)));

    // Check Disable built-in NS flag.
    boolean disableBuiltInNS = sharedPref.getBoolean(
            keyprefDisableBuiltInNs,
            Boolean.valueOf(getString(R.string.pref_disable_built_in_ns_default)));

    // Check Enable level control.
    boolean enableLevelControl = sharedPref.getBoolean(
            keyprefEnableLevelControl,
            Boolean.valueOf(getString(R.string.pref_enable_level_control_key)));

    // Get video resolution from settings.
    int videoWidth = 0;
    int videoHeight = 0;
    String resolution = sharedPref.getString(keyprefResolution,
            getString(R.string.pref_resolution_default));
    String[] dimensions = resolution.split("[ x]+");
    if (dimensions.length == 2) {
      try {
        videoWidth = Integer.parseInt(dimensions[0]);
        videoHeight = Integer.parseInt(dimensions[1]);
      } catch (NumberFormatException e) {
        videoWidth = 0;
        videoHeight = 0;
        Log.e(TAG, "Wrong video resolution setting: " + resolution);
      }
    }

    // Get camera fps from settings.
    int cameraFps = 0;
    String fps = sharedPref.getString(keyprefFps,
            getString(R.string.pref_fps_default));
    String[] fpsValues = fps.split("[ x]+");
    if (fpsValues.length == 2) {
      try {
        cameraFps = Integer.parseInt(fpsValues[0]);
      } catch (NumberFormatException e) {
        Log.e(TAG, "Wrong camera fps setting: " + fps);
      }
    }

    // Check capture quality slider flag.
    boolean captureQualitySlider = sharedPref.getBoolean(keyprefCaptureQualitySlider,
            Boolean.valueOf(getString(R.string.pref_capturequalityslider_default)));

    // Get video and audio start bitrate.
    int videoStartBitrate = 0;
    String bitrateTypeDefault = getString(
            R.string.pref_startvideobitrate_default);
    String bitrateType = sharedPref.getString(
            keyprefVideoBitrateType, bitrateTypeDefault);
    if (!bitrateType.equals(bitrateTypeDefault)) {
      String bitrateValue = sharedPref.getString(keyprefVideoBitrateValue,
              getString(R.string.pref_startvideobitratevalue_default));
      videoStartBitrate = Integer.parseInt(bitrateValue);
    }
    int audioStartBitrate = 0;
    bitrateTypeDefault = getString(R.string.pref_startaudiobitrate_default);
    bitrateType = sharedPref.getString(
            keyprefAudioBitrateType, bitrateTypeDefault);
    if (!bitrateType.equals(bitrateTypeDefault)) {
      String bitrateValue = sharedPref.getString(keyprefAudioBitrateValue,
              getString(R.string.pref_startaudiobitratevalue_default));
      audioStartBitrate = Integer.parseInt(bitrateValue);
    }

    // Check statistics display option.
    boolean displayHud = sharedPref.getBoolean(keyprefDisplayHud,
            Boolean.valueOf(getString(R.string.pref_displayhud_default)));

    boolean tracing = sharedPref.getBoolean(
            keyprefTracing, Boolean.valueOf(getString(R.string.pref_tracing_default)));

    // Start AppRTCDemo activity.
    Log.d(TAG, "Connecting to room " + roomId + " at URL " + roomUrl);
    if (validateUrl(roomUrl)) {
      Uri uri = Uri.parse(roomUrl);
      Intent intent = new Intent(this, CallActivity.class);
      intent.setData(uri);
      intent.putExtra(CallActivity.EXTRA_ROOMID, roomId);
      intent.putExtra(CallActivity.EXTRA_LOOPBACK, loopback);
      intent.putExtra(CallActivity.EXTRA_VIDEO_CALL, videoCallEnabled);
      intent.putExtra(CallActivity.EXTRA_CAMERA2, useCamera2);
      intent.putExtra(CallActivity.EXTRA_VIDEO_WIDTH, videoWidth);
      intent.putExtra(CallActivity.EXTRA_VIDEO_HEIGHT, videoHeight);
      intent.putExtra(CallActivity.EXTRA_VIDEO_FPS, cameraFps);
      intent.putExtra(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
              captureQualitySlider);
      intent.putExtra(CallActivity.EXTRA_VIDEO_BITRATE, videoStartBitrate);
      intent.putExtra(CallActivity.EXTRA_VIDEOCODEC, videoCodec);
      intent.putExtra(CallActivity.EXTRA_HWCODEC_ENABLED, hwCodec);
      intent.putExtra(CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture);
      intent.putExtra(CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED,
              noAudioProcessing);
      intent.putExtra(CallActivity.EXTRA_AECDUMP_ENABLED, aecDump);
      intent.putExtra(CallActivity.EXTRA_OPENSLES_ENABLED, useOpenSLES);
      intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC);
      intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC);
      intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS);
      intent.putExtra(CallActivity.EXTRA_ENABLE_LEVEL_CONTROL, enableLevelControl);
      intent.putExtra(CallActivity.EXTRA_AUDIO_BITRATE, audioStartBitrate);
      intent.putExtra(CallActivity.EXTRA_AUDIOCODEC, audioCodec);
      intent.putExtra(CallActivity.EXTRA_DISPLAY_HUD, displayHud);
      intent.putExtra(CallActivity.EXTRA_TRACING, tracing);
      intent.putExtra(CallActivity.EXTRA_CMDLINE, commandLineRun);
      intent.putExtra(CallActivity.EXTRA_RUNTIME, runTimeMs);
      try{

      startActivityForResult(intent, CONNECTION_REQUEST);


      } catch (Exception e){
        Log.i(TAG, "Wrong setting for: " + e.getMessage());

        errorMessege();
      }

    }
  }

  private boolean validateUrl(String url) {
    if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
      return true;
    }

    new AlertDialog.Builder(this)
            .setTitle(getText(R.string.invalid_url_title))
            .setMessage(getString(R.string.invalid_url_text, url))
            .setCancelable(false)
            .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
              }
            }).create().show();
    return false;
  }
  private final AdapterView.OnItemClickListener
          roomListClickListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
      String roomId = ((TextView) view).getText().toString();
      connectToRoom(roomId, false, false, 0);
    }
  };

  private final OnClickListener addFavoriteListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      String newRoom = roomEditText.getText().toString();
      if (newRoom.length() > 10 && !roomList.contains(newRoom)) {
        adapter.add(newRoom); //ingresa nueva sala
        adapter.notifyDataSetChanged();
      }
    }
  };

  private final OnClickListener connectListener = new OnClickListener() {
    @Override
    public void onClick(View view) {
      connectToRoom(roomEditText.getText().toString(), false, false, 0);
    }
  };


  class AdaptadorPersonas extends ArrayAdapter<Persona> {

    AppCompatActivity appCompatActivity;

    AdaptadorPersonas(AppCompatActivity context) {
      super(context, R.layout.persona, roomLists);
      appCompatActivity = context;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = appCompatActivity.getLayoutInflater();
      View item = inflater.inflate(R.layout.persona, null);

      TextView textView1 = (TextView)item.findViewById(R.id.textView);
      textView1.setText(roomLists.get(position).getNombre());

      ImageView imageView1 = (ImageView)item.findViewById(R.id.imageView);
      if (roomLists.get(position).getGenero()=='m')
        imageView1.setImageResource(R.mipmap.hombre);
      else
        imageView1.setImageResource(R.mipmap.mujer);
      return(item);
    }
  }

  //Clase qu caturo los datos del GPS
  public class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {

      DecimalFormat f = new DecimalFormat("###.#####");

      lat = f.format(location.getLatitude()).replace(",", ".");
      lon = f.format(location.getLongitude()).replace(",", ".");

      //latitud.setText(lat);
      latitud = lat.toString();
      //longitud.setText(lon);
      longitud = lon.toString();
    }
    /*
    Este metodo se llama cuando un proveedor no puede buscar una
    ubicacion o si el proveedor se ha convertido recientemente
    disponible despues de un periodo de indisponibilidad
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  }
  //endregion
  private void cargaJsonAutorizacion(String latitud, String longitud){

    username="Prueba";
    String datolatitud  = String.valueOf(latitud);
    String datologitud = String.valueOf(longitud);
    IVideo servicioCatService   = ApiCliente.getCliente().create(IVideo.class);

    //ServicioCatService servicioCatService  = retrofit.create(ServicioCatService.class);
    Call call = servicioCatService.ordenBasica(username, latitud, longitud);
    call.enqueue(new Callback() {
      @Override
      public void onResponse(Call call, Response response) {

        //  mostrarProgress(false);
        if (!response.isSuccessful()) {
          String error = "Ha ocurrido un error. Contacte al administrador";
          Log.e(TAG, "request was aborted");
//           datoError=  getResources().getString(R.string.errroradmin) + response.code();
          //  callError(datoError);
        }else {
          //Ultimo registro
          // ultimodelosRegistros();
          Toast.makeText(getApplicationContext(),
                  "insertado", Toast.LENGTH_SHORT);

        }

      }

      @Override
      public void onFailure(Call call, Throwable t) {

        Toast.makeText(getApplicationContext(),
                t.getMessage(), Toast.LENGTH_SHORT);

      }
    });


  }


  public boolean insertar(){

    HttpClient httpclient;

    List<NameValuePair> formData = new ArrayList<NameValuePair>();


    HttpPost httppost;
    httpclient=new DefaultHttpClient();
    //httppost= new HttpPost("http://192.168.1.124/pruebasigti/insertaposicion.php"); // Url del Servidor 192.168.1.124/pruebasigti/
    //httppost= new HttpPost("http://192.168.1.195/pruebasigti/insert.php"); /home/sigti/public_html/directv/insertaposicion.php
    //httppost= new HttpPost("http://sigti.co/directv/insertaposicion.php");

    httppost= new HttpPost("http://sigti.webcol.net/directv/videoconferencia.php");
    username  = "Prueba";
    String latt = String.valueOf(latitudes);
    String lonn = String.valueOf(longitudes);


    try {


      formData.add(new BasicNameValuePair("nombre", username.toString().trim()));
      formData.add(new BasicNameValuePair("lat", latt.toString().trim()));
      formData.add(new BasicNameValuePair("lon", lonn.toString().trim()));



      //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
      httppost.setEntity(new UrlEncodedFormEntity(formData, "UTF-8"));
      httpclient.execute(httppost);
      return true;
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      Log.d(RED, "Error Internet");
      e.printStackTrace();
      //showErrorVista();

    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      Log.e(RED, "Error Internet");
      e.printStackTrace();
      //showErrorVista();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      Log.e(RED, "Error Internet");
      //e.printStackTrace();
      //showErrorVista();


    }

    return false;
  }

  public class Insertar extends AsyncTask<String,String,String> {

    private Activity context;

    Insertar(Activity context){
      this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {

      if(insertar())
        context.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            latitud = null;
            longitud = null;
            latitudes = (float) 0.00;
            longitudes = (float) 0.00;
          }
        });
      else
        context.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            // TODO Auto-generated method stub
            Log.d("Error", "Error de Conexion de Internet");
            Toast.makeText(context, "Error de Conexion de Internet", Toast.LENGTH_LONG).show();
            //showErrorVista();

          }
        });
      return null;
    }
  }
}


