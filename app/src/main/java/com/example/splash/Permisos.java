package com.example.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.Serializable;

public class Permisos implements Serializable {

    public boolean PermisoCamara = false, PermisoInternet= false;
    public static final int CODIGO_PERMISOS_CAMARA = 1, CODIGO_PERMISOS_INTERNET=2;

    public void verificarYPedirPermisosDeInternet(Context context, Activity activity) {

        int estadoDePermiso = ContextCompat.checkSelfPermission(context, android.Manifest.permission.INTERNET);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            permisoDeInternetConcedido(context);
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.INTERNET},
                    CODIGO_PERMISOS_INTERNET);
        }
    }
    public void verificarYPedirPermisosDeCamara(Context context, Activity activity) {
        int estadoDePermiso = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            permisoDeCamaraConcedido(context);
        } else {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    CODIGO_PERMISOS_CAMARA);
        }
    }
    public void permisoDeCamaraConcedido(Context context) {
        Toast.makeText(context, "El permiso para la cámara está concedido", Toast.LENGTH_SHORT).show();
        PermisoCamara = true;
    }

    public void permisoDeCamaraDenegado(Context context) {
        Toast.makeText(context, "El permiso para la cámara está denegado", Toast.LENGTH_SHORT).show();
    }
    public void permisoDeInternetConcedido(Context context) {
        Toast.makeText(context, "El permiso para el internet está concedido", Toast.LENGTH_SHORT).show();
        PermisoInternet = true;
    }

    public void permisoDeInternetDenegado(Context context) {
        Toast.makeText(context, "El permiso para el internet está denegado", Toast.LENGTH_SHORT).show();
    }
}
