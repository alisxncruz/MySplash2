package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.splash.BaseDatos.BDUsuarios;
import com.example.splash.Method.Sha;

import java.util.List;

public class Restaura extends AppCompatActivity {

    public static String usuario, codigo, contrasena;
    public static List<infoRegistro> lista;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaura);

         EditText useer = findViewById(R.id.userR);
         EditText codigoo = findViewById(R.id.codigo);
         Button restaura = findViewById(R.id.restaura);

         restaura.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 usuario = String.valueOf(useer.getText());
                 codigo = Sha.bytesToHex(Sha.createSha1(String.valueOf(codigoo.getText())));
                 guardaPass (usuario, codigo);

             }
         });
    }

    public void guardaPass (String usuario, String codigo){

        Boolean entra = Boolean.FALSE;
        EditText newwpass = findViewById(R.id.newpass);
        String pass = String.valueOf(newwpass.getText());

        BDUsuarios bd = new BDUsuarios(Restaura.this);
        infoRegistro info = bd.GetUsuario(usuario);

        if (info!=null){
            if (info.getContra().equals(codigo)){
                contrasena = pass;
                boolean f = bd.EditUser(usuario, contrasena);
                if (f){
                    Toast.makeText(getApplicationContext(), "Nueva contraseña", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Restaura.this, Login.class);
                    startActivity(intent);
                    entra = Boolean.TRUE;
                }
                if (entra == Boolean.FALSE){
                    Toast.makeText(getApplicationContext(), "Información incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}