package com.example.plantilla.webrtc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


import com.example.plantilla.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;

public class ProgresbarDialogoLatLong extends Activity {
    ProgressDialog dialog;

    private ProgressDialog progressDialog;
    private Context context;
    static LocationManager locationManager;
    static MyLocationListener miLocationListener;
    private LocationManager locManager;
    private LocationListener locListener;
    Calendar HoraSystem = Calendar.getInstance();
    SimpleDateFormat foratoTiempo = new SimpleDateFormat("hh:mm:ss");
    String strHoradelSistema = foratoTiempo.format(HoraSystem.getTime());

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String strDate = sdf.format(c.getTime());

    private float latitudes;
    private float longitudes;
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
    String User_name;
    String Id_User_Name;
    int id_usuario_codigo; //ID_USUARIO
    String Codigo_id_Usuario;

    String Email_User_Name;
    public String NombrePoseeCelular;
    public String id_gcm_Google_Messeging;
    static final String TAG = "pavan";
    static final String RED = "Interferencia";

    private String Dato_Pasa_Consulta_Id_Tecnico;
    private String jsonResult; //json
    private JSONObject jsonResultID;
    private String urlIDtecnicoLg = "http://sigti.webcol.net/directv/solo_idtecnico.php";
    private int datoDevuelteIDTecnico; //El dato que devuelde el tecnico
    private DatoRegistro objetoRegistro;
    private List<DatoRegistro> listaPersonas;

    //Creacion de la actividad
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasa_lat_long);
        context = getApplicationContext();
        //Si hay internet
        if (isNetworkAvaliable(context)) {
            //Si hay reta registrado
            //if (isUserRegistered(context)) {
                comenzarLocalizacion();
                //MostramosMensajes();
                String Datoporvenir = id_gcm_Google_Messeging;
                new ConsultaIdTecnicosLongLat(Datoporvenir).execute();
                new Insertar(ProgresbarDialogoLatLong.this).execute();

                new MyNewTask().execute();
            //}

        } else {
            showErrorVista();

        }


    }

    //Metodo si hay internet
    public boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {


            return false;

        }
    }


    //Metodo para mostrar el error

    private void showErrorVista() {
        ProgresbarDialogoLatLong.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(ProgresbarDialogoLatLong.this);

                builder.setTitle("Sigti");
                builder.setMessage("Error de Conexion")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    //region Registro

    //Metodo de almacenamiento


    //Metodo de obtener la version
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }



    //
    byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int count; (count = in.read(buffer)) != -1; ) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }
    //endregion

    //region GPS

    //Inicia el  progress dialog
    @SuppressWarnings("ResourceType")
    private class MyNewTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            //Caracateristicas de nuestro progresdialog
            dialog = new ProgressDialog(ProgresbarDialogoLatLong.this);
            dialog.setIndeterminate(true);

            dialog.setIcon(getResources().getDrawable(R.drawable.icono_cuenta));
            //dialog.setIndeterminate(getResources().getDrawable(R.drawable.icono_cuenta));
            dialog.setCancelable(false);
            dialog.setTitle("Sigti");
            dialog.setMessage("Procesando \n" +
                    "Se esta llamando Consulta al servidor...!");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.dismiss();
            dialog.show();

        }


        @Override
        protected String doInBackground(String... params) {

            // no interactuar con la interfaz de usuario
            // hacer algo en el fondo aquí
            //String url=params[0];

            for (int i = 0; i <= 100; i += 5) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Log.e(RED, "Error Internet");
                    e.printStackTrace();
                    //showErrorVista();

                }

            }

            return "Done!";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("result", "" + result);
            if (result != null) {
                dialog.dismiss();
                finish();
            }

        }

    } //end MyNewTask

    //Metodo de localizaicon GPS
    public void comenzarLocalizacion() {
        //Obtenemos una referencia al LocationManager
        locManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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

            public void onProviderDisabled(String provider) {
                // lblEstado.setText("Provider OFF");
            }

            public void onProviderEnabled(String provider) {
                //lblEstado.setText("Provider ON ");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("", "Provider Status: " + status);
                //lblEstado.setText("Provider Status: " + status);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }
    //Metodo para mostrar la posicion del gps
    private void mostrarPosicion(Location loc) {
        if(loc != null)
        {
            //latitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            latitud = String.valueOf(loc.getLatitude());
            //longitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            longitud = String.valueOf(loc.getLongitude());
            //lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));

            latitudes = Float.valueOf((float) loc.getLatitude());
            longitudes = Float.valueOf((float) loc.getLongitude());

            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
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

    //region Insertar Coordenadas GPS
    //AsyncTask para insertar tbl_notificaciones
    class Insertar extends AsyncTask<String,String,String> {

        private Activity context;

        Insertar(Activity context){
            this.context=context;
        }


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(insertar())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //Toast.makeText(context, "Persona insertada con Exito", Toast.LENGTH_LONG).show();
                        /*
                        nombre.setText("");
                        dni.setText("");
                        telefono.setText("");
                        email.setText("");*/
                        horas = null;
                        fecha = null;
                        lat = null;
                        lon = null;
                        latitud = null;
                        longitud = null;
                        Dato_Id_Google_Messeging = null;
                        Dato_id_usuarioNotificacion=null;
                        Dato_id_Gooogle_email=null;
                        //finish();
                        // no interactuar con la interfaz de usuario
                        // hacer algo en el fondo aquí
                        //String url=params[0];
                        Toast.makeText(context, "Insercion con Exito", Toast.LENGTH_LONG).show();


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

    //Inserta los datos de las Personas en el servidor. 192.168.1.124/pruebasigti/insertaposicion.php
    public boolean insertar(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        //httppost= new HttpPost("http://192.168.1.124/pruebasigti/insertaposicion.php"); // Url del Servidor 192.168.1.124/pruebasigti/
        //httppost= new HttpPost("http://192.168.1.195/pruebasigti/insert.php"); /home/sigti/public_html/directv/insertaposicion.php
        //httppost= new HttpPost("http://sigti.co/directv/insertaposicion.php");

        httppost= new HttpPost("http://sigti.webcol.net/directv/insertaposicion.php");

        String datoIdTec = String.valueOf(datoDevuelteIDTecnico);

        rehoras = strHoradelSistema;
        fecha = strDate;

        //Anadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>();

        String latt = String.valueOf(latitudes);
        String lonn = String.valueOf(longitudes);

        nameValuePairs.add(new BasicNameValuePair("fecha_auxlliar_activo",fecha.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("lat", latt.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("lon", lonn.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("hora", rehoras.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("gcm_regid", Dato_Id_Google_Messeging.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("name", Dato_id_usuarioNotificacion.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("email", Dato_id_Gooogle_email.toString().trim()));
        //nameValuePairs.add(new BasicNameValuePair("id_tecnico", Codigo_id_Usuario.toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("id_usuario", datoIdTec.toString().trim()));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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


    //Classe para consultar el id
    public class ConsultaIdTecnicosLongLat extends AsyncTask<String, String, String>{
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */


        private Activity context;
        private String datoPId;
        ConsultaIdTecnicosLongLat(String datoPId){
            //this.context=context;
            this.datoPId=datoPId;
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
        //String datoIdConsultaIdTecnico;

      /*  protected String doInBackground(String... params) {
            try {
                //se trae el dato del idgcm
                //strgcm_regid = datosConsultaID.getGcm_id();

                //Lista de parametros
                List<NameValuePair> parametros = new ArrayList<NameValuePair>();
                parametros.add(new BasicNameValuePair("gcm_regid", id_gcm_Google_Messeging));//Para gcmid
                //Intancia de clase para el serividor web apache
                ServiceHandler serviceHandler = new ServiceHandler();
                //Creamos el servicio
                jsonResult = serviceHandler.makeServiceCall(urlIDtecnicoLg,
                        ServiceHandler.POST, parametros); //Para url con parametros
                //Meremos en consola el json
                Log.d("Create Prediction Request: ", "> " + jsonResult);
                //instancia de clase para la respueta al cliente
                datoDevuelteIDTecnico= ListaID(jsonResult);

            }catch (Exception e){
                e.printStackTrace();
            }
            return jsonResult;
        }*/
    }

    public int ListaID(String jsonResult){

        int datoTecnicoID = 0;

        try {
            String datoJsonnull="null\n";
            if(jsonResult.equals(datoJsonnull)){
                Log.i(TAG, "Registration not found.");
            }else {
                JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("sigti_instalaciones");


                //JSONArray jsonMainNode = jsonResponse.optJSONArray(" ");

                for (int i = 0; i < jsonMainNode.length(); i++) {

                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    //Clase DatoRegistro
                    objetoRegistro = new DatoRegistro();
                    JSONObject jsonArrayChild = jsonMainNode.getJSONObject(i);
                    objetoRegistro.setDatoRegistro(jsonArrayChild.optInt("id_usuario"));

                    datoTecnicoID = objetoRegistro.setDatoRegistro(jsonArrayChild.optInt("id_usuario"));
                    //listaPersonas.add(objetoRegistro);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return datoTecnicoID;
    }

    //endregion


}
