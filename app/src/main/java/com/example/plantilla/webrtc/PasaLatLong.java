package com.example.plantilla.webrtc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class PasaLatLong extends BroadcastReceiver {

    //Atributos
    private float latitudes;
    private float longitudes;
    Context contexts;
    TextView mDisplay;
    //GoogleCloudMessaging gcm; //Para regito del movil
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;
    String regid;
    String msg;

    public String NombrePoseeCelular;
    public String id_gcm_Google_Messeging;
    static final String TAG = "pavan";
    String User_name;
    String Id_User_Name;
    String Email_User_Name;
    static LocationManager locationManager;

    private LocationManager locManager;
    private LocationListener locListener;

    Calendar HoraSystem = Calendar.getInstance();
    SimpleDateFormat foratoTiempo = new SimpleDateFormat("hh:mm:ss");
    String strHoradelSistema = foratoTiempo.format(HoraSystem.getTime());

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String strDate = sdf.format(c.getTime());

    private String horas;
    private String fecha;
    private String lat;
    private String lon;
    private String latitud;
    private String longitud;
    private String rehoras;
    private String Dato_id_usuarioNotificacion;
    private String Dato_Id_Google_Messeging;
    private String Dato_id_Gooogle_email;


    @Override
    public void onReceive(Context context, Intent intent) {


        //Intent pupInt = new Intent(context, NotificaMinMainActivity.class);
        Intent pupInt = new Intent(context, ProgresbarDialogoLatLong.class);
        pupInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.getApplicationContext().startActivity(pupInt);
    }
}
