package com.example.plantilla.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.plantilla.R;
import com.github.captain_miao.optroundcardview.OptRoundCardView;

import java.util.ArrayList;

public class MainActivity_Copidrogras_Col extends Fragment {

    // Store instance variables
    private static final String ARG_SECTION_NUMBER = "section_number";
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

    public  static MainActivity_Copidrogras_Col newInstance(int page){
        MainActivity_Copidrogras_Col mainActivityVideoFras = new MainActivity_Copidrogras_Col();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, page);
        //args.putString("someTitle", title);
        mainActivityVideoFras.setArguments(args);

        return mainActivityVideoFras;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int section = getArguments().getInt(ARG_SECTION_NUMBER);
        View rootView = inflater.inflate(R.layout.activity_web, container, false);
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
        webView.setWebViewClient(new MainActivity_Copidrogras_Col.MyAppWebViewClient());

        //progress = (ProgressBar) findViewById(R.id.progressBar);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        //progress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        // FIN AGREGADO



        return rootView;
    }



    public class MyAppWebViewClient extends WebViewClient {
        ArrayList<String> alName;
        ArrayList<Integer> alImage;
        private WebView webView;
        private ProgressBar progressBar;
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
            //progressBar.setVisibility(View.VISIBLE);
            MainActivity_Copidrogras_Col.this.progressBar.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            //progressBar.setVisibility(View.GONE);
            MainActivity_Copidrogras_Col.this.progressBar.setProgress(100);

            super.onPageFinished(view, url);
        }

    }
}
