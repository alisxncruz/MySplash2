package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.splash.BaseDatos.BDUsuarios;
import com.example.splash.Des.MyDesUtil;
import com.example.splash.Method.Sha;

import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity{

    private RadioButton femenino, masculino;
    private static final String TAG = "MainActivity";
    public static final String archivo = "S.json";
    public static String nombree, usuarioo, correoo, edadd, passwordd;
    private Switch tyc;
    public static int sw = 0;
    public static int activado;

    public static List<infoRegistro> list = new ArrayList<>();
    public static List<infoPass> lista;
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(KEY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //nueva pass
        lista = new ArrayList<>();
        infoPass ipass = null;
        Button acepta = findViewById(R.id.button);
        EditText nombre = findViewById(R.id.nombre);
        EditText usuario = findViewById(R.id.usuario);
        EditText correo = findViewById(R.id.correoe);
        EditText edad = findViewById(R.id.edad);
        EditText password = findViewById(R.id.password);
        femenino = findViewById(R.id.fem);
        masculino = findViewById(R.id.mascu);
        tyc = findViewById(R.id.tyc);

        acepta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoRegistro info = new infoRegistro();
                nombree = String.valueOf(nombre.getText());
                usuarioo = String.valueOf(usuario.getText());
                correoo = String.valueOf(correo.getText());
                edadd = String.valueOf(edad.getText());
                passwordd = String.valueOf(password.getText());

                if (femenino.isChecked()==true){
                    activado = 1;
                }
                if (masculino.isChecked()==true){
                    activado = 1;
                }
                if (tyc.isChecked()){
                    sw = 1;
                }

                if(usuarioo.equals("")||passwordd.equals("")||correoo.equals("")){
                    Log.d(TAG, "vacio");
                    Log.d(TAG,usuarioo);
                    Log.d(TAG, passwordd);
                    Log.d(TAG, correoo);
                    Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
                }else{
                    if (Sha.emailValidado(correoo)) {
                        Sha.myInfo(info);
                        BDUsuarios bdUsuarios = new BDUsuarios(Registro.this);
                        long id = bdUsuarios.guardaUsuario(info);
                        if (id > 0){
                            Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Registro.this, Login.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(Registro.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Introduzca un correo válido", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}