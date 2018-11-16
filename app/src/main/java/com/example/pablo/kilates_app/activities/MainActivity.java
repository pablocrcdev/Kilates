package com.example.pablo.kilates_app.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.pablo.kilates_app.R;
import com.example.pablo.kilates_app.utils.ErrorController;
import com.example.pablo.kilates_app.webview.ManagerChromeClient;
import com.example.pablo.kilates_app.webview.ManagerWebClient;

public class MainActivity extends AppCompatActivity {
    //==================================Variables Globales========================================//
    private WebView gvWebView;
    private static final String gvURL = "http://192.168.1.100:9090/crccoding/f?p=500";
    // variables de error
    public static String ERROR_WEB = "WEB";
    public static String ERROR_RED = "RED";

    //********************************************************************************************//
    // Metodos de validacion
    //********************************************************************************************//
    // Validacion del estado del acceso a la web
    protected boolean validarEstadoRed() {
        ConnectivityManager vConnectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo vNetworkInfo = vConnectivityManager.getActiveNetworkInfo();
        if (vNetworkInfo != null && vNetworkInfo.isConnected())
            return true;  // Si encuentra que hay conexion
        else
            return false; // De no encontrar conexion arroja falso
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(validarEstadoRed()) {
            // Declaracion del elemento xml en la clase para configuraciones
            gvWebView = (WebView) findViewById(R.id.WebView);
            // Seteo de Cliente Web, para manejo de navegador interno
            gvWebView.setWebViewClient(new ManagerWebClient(this));
            // Habilitacion de Javascript en el webview
            gvWebView.getSettings().setJavaScriptEnabled(true);
            // Carga de URL en el elemento Webview
            gvWebView.loadUrl(gvURL);
            // Seteo Default de Cliente de Google para el webview (Otras funcionalidades)
            gvWebView.setWebChromeClient(new ManagerChromeClient(this));
        } else {
            // indica que no hay red para accesar la pagina
            new ErrorController(this).showNetworkDialog();
        }
    }
}
