package com.example.plantilla.webrtc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.plantilla.R;
import com.example.plantilla.cardio.MainActivityCardio;
import com.example.plantilla.email.MainActivity_Email;
import com.example.plantilla.ui.adapter.CardAdapter;
import com.example.plantilla.ui.adapter.GridAdapter;
import com.example.plantilla.ui.adapter.GridViewAdapter;
import com.example.plantilla.ui.models.Movie;
import com.example.plantilla.ui.utilidades.Utils;
import com.example.plantilla.utilidad.Container;
import com.github.captain_miao.optroundcardview.OptRoundCardView;


import java.util.ArrayList;
import java.util.Arrays;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;

/**
 * Created by choqu_000 on 24/07/2016.
 */
public class MainActivitys extends AppCompatActivity implements CardAdapter.Listener{

    private static final String ARG_SECTION_NUMBER = "section_number";
    //Atributoas
    private WebView webView;
    private ProgressBar progressBar;
    //Creamos la vista de la actividad
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SectionsPagerAdapter mSectionsPagerAdapters;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ViewPager mViewPagers;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CardAdapter mAdapters;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> alName;
    ArrayList<Integer> alImage;


    //Dependencias de instancias globales
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    //String stringConsulta = getString(R.string.apConsulta);
    //String stringapDrugstores = getString(R.string.apDrugstores);
    //Botom
    //private Button button;
    Context context;
    Activity contexts;
    // Bitmaps will only be decoded once and stored in this cache
    public static SparseArray<Bitmap> sPhotoCache = new SparseArray<Bitmap>(4);
    int posicion = 0;
    private int CAMREA_CODE = 1;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    //This will be used in handling callback
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = getApplicationContext();
        contexts = getParent();
        //mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //mSectionsPagerAdapters = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //
        //mViewPagers = (ViewPager) findViewById(R.id.containers);
        //mViewPagers.setAdapter(mSectionsPagerAdapters);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);


        validaPermisos();
        requestAudioPermissions();

    }

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now

        }
    }
    //VAlidar permirsos de camara
    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }


        return false;
    }

    //Mostrar Mensaje
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(MainActivitys.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
//                botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }

        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    // recordAudio();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }

    }
    //Solicitar permisos
    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(MainActivitys.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }



    /**
     * Loads drawables into the given image view efficiently. Uses the method described
     * <a href="http://developer.android.com/training/displaying-bitmaps/load-bitmap.html">here.</a>
     *
     * @param imageView
     * @param resId     Resource identifier of the drawable to load from memory
     */
    private void setImageBitmap(ImageView imageView, int resId) {
        Bitmap bitmap = Utils.decodeSampledBitmapFromResource(getResources(),
                resId, imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
        sPhotoCache.put(resId, bitmap);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //subCarca(); WebRegistro
            //Intent about = new Intent(getApplicationContext(), WebRegistro.class);
            Intent about = new Intent(getApplicationContext(), MainActivityCardio.class);
            startActivity(about);


            return true;
        }

        if(id==R.id.edits){
            Intent email = new Intent(getApplicationContext(), MainActivity_Email.class);
            startActivity(email);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(Movie movie) {
        if(movie != null){
            Toast.makeText(this, "You just selected " + movie.name + "!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */


        //Atributos
        RecyclerView mRecyclerView;
        //CardView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView.Adapter mAdapter;
        ArrayList<String> alName;
        ArrayList<Integer> alImage;


        //Atributos
        private GridView gridView;
        private GridViewAdapter gridViewAdapter;
        private ImageButton OrdenServicio;
        private ImageButton Auditoria;

        //Dependencias de instancias globales
        private RecyclerView recycler;
        private RecyclerView.Adapter adapter;
        private RecyclerView.LayoutManager lManager;

        Context context;
        Activity contexts;

        Context contexto;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            contexts = getActivity();
            OptRoundCardView roundCardView;
            context=getActivity();
            switch (section){
                case 1:
                    roundCardView = (OptRoundCardView) rootView.findViewById(R.id.bottom_card_view);
                    roundCardView.showCorner(false, false, false, false);
                    break;
                //Otro botom de las cita
                case 2:
                    roundCardView = (OptRoundCardView) rootView.findViewById(R.id.top_card_view);
                    roundCardView.showCorner(false, false, false, false);
                    break;
                default:
                    break;
            }


            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, section));

            Button  button = (Button)rootView.findViewById(R.id.actualizar);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Intent intent = new Intent(this, MonitorCardiaco.class);  Container
                    //context.startActivity(new Intent(context, MonitorCardiaco.class));
                    // context.startActivity(new Intent(context, ConnectActivity.class));
                    context.startActivity(new Intent(context, Container.class));
                }
            });

            /*
            Button buttonQr = (Button)rootView.findViewById(R.id.consulta);
            buttonQr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //context.startActivity(new Intent(context, AndroidBarcodeQr.class));
                    context.startActivity(new Intent(context, WebRegistro.class));
                }
            });*/

            alName = new ArrayList<>(Arrays.asList(getString(R.string.apBotomHistoriaClinica), getString(R.string.apExamenes), getString(R.string.apCitasMedicas), getString(R.string.apCuidaSalud),  getString(R.string.pref_fps_title),getString(R.string.chat), getString(R.string.psicologia)));
            ///alName = new ArrayList<>(Arrays.asList("Orden de Servicio", "Auditoria", "En construccion"));
            alImage = new ArrayList<>(Arrays.asList(R.drawable.histoclinica, R.drawable.examen, R.drawable.disponible,
                    R.drawable.cuidsalud, R.drawable. consulta,   R.drawable.disponible, R.drawable. chat));



            // Calling the RecyclerView
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);

            // The number of Columns
            mLayoutManager = new GridLayoutManager(context, 2);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new GridAdapter(context, alName, alImage);
            mRecyclerView.setAdapter(mAdapter);



            return rootView;
        }

    }

    public  static  class  MainActivityAutoVideo extends Fragment{
        // Store instance variables
        private String title;
        private int page;
        //private static final String ARG_SECTION_NUMBER = "section_number";

        public static  MainActivityAutoVideo newInstance(int page){
            MainActivityAutoVideo mainActivityVideoFras = new MainActivityAutoVideo();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, page);
            //args.putString("someTitle", title);
            mainActivityVideoFras.setArguments(args);

            return mainActivityVideoFras;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.activity_main_video_local, container, false);

            //Nuevos parametros para el view del fragmento
            RelativeLayout.LayoutParams params =    new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

            final VideoView videoView =
                    (VideoView)view.findViewById(R.id.myVideo);

            videoView.setVideoPath(
                    "http://www.ebookfrenzy.com/android_book/movie.mp4");

            videoView.start();

            return view;
        }

    }

    public static class Mapa_Activity_Copidrogras_Col extends Fragment {

        // Store instance variables
        private String title;
        private int page;
        Context contexto;
        ArrayList<String> alNames;
        ArrayList<Integer> alImages;

        //Atributos
        RecyclerView mRecyclerView;
        //CardView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView.Adapter mAdapter;
        ArrayList<String> alName;
        ArrayList<Integer> alImage;
        private WebView webView;
        private ProgressBar progressBar;

        //private static final String ARG_SECTION_NUMBER = "section_number";

        public  static Mapa_Activity_Copidrogras_Col newInstance(int page){
            Mapa_Activity_Copidrogras_Col mainActivityVideoFras = new Mapa_Activity_Copidrogras_Col();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, page);
            //args.putString("someTitle", title);
            mainActivityVideoFras.setArguments(args);

            return mainActivityVideoFras;
        }


        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.web, container, false);
            contexto = getActivity();
            OptRoundCardView roundCardView;
            contexto=getActivity();

            //Casting del webviewer
             webView = (WebView) rootView.findViewById(R.id.webview);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            // Url que carga la app (webview)
            //mWebView.loadUrl("http://www.clinicalacolina.com/formulario-para-solicitud-de-historia-clinica");
            webView.loadUrl("http://www.tudrogueriamascercana.com/");
            // Forzamos el webview para que abra los enlaces internos dentro de la la APP
            // http://doctorhelp.com.co/historia_clinica/usuario
            webView.setWebViewClient(new WebViewClient());
            // Forzamos el webview para que abra los enlaces externos en el navegador
            webView.setWebViewClient(new Mapa_Activity_Copidrogras_Col.MyAppWebViewClient());

            //progress = (ProgressBar) findViewById(R.id.progressBar);
            progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
            //progress.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

            // FIN AGREGADO



            return rootView;
        }


        public class MyAppWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                // Url base de la APP (al salir de esta url, abre el navegador) poner como se muestra, sin http://
                //https://appear.in/drhelponline video conferencia
                //Drogurias  tudrogueriamascercana.com
                if (Uri.parse(url).getHost().endsWith("tudrogueriamascercana.com")) {
                    return false;
                }

                if (Uri.parse(url).getHost().endsWith("tudrogueriamascercana.com")) {
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                Mapa_Activity_Copidrogras_Col.this.progressBar.setProgress(0);
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                Mapa_Activity_Copidrogras_Col.this.progressBar.setProgress(100);

                super.onPageFinished(view, url);
            }
        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position){

                case 0:

                    return PlaceholderFragment.newInstance(position);
                case 1:
                    //return MainActivityVideoFras.newInstance(position);
                    //return MainActivityAutoVideo.newInstance(position); //Aca esta el video de la niña tab2
                    return  Mapa_Activity_Copidrogras_Col.newInstance(position);
                default:
                    return  null;
            }
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            //return 3;
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return getString(R.string.apConsulta);
                case 1:
                    return getString(R.string.apDrugstores);
               /* case 2:
                    return "Full corner";*/
            }
            return null;
        }
    }



}
