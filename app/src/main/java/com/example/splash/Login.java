package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.splash.BaseDatos.BDUsuarios;
import com.example.splash.Des.MyDesUtil;

import java.util.List;

public class Login extends AppCompatActivity {

    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private String testClaro = "Hola mundo";
    private String testDesCifrado;

    public String correo;
    public String mensaje;
    public static List<infoRegistro> list;
    public static String TAG = "mensaje";
    public static String TOG = "error";
    public static String json = null;
    public static String usuarioo,contraa;
    private Button acepta, olvide, registra;
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(KEY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        acepta = findViewById(R.id.acepta);
        olvide = findViewById(R.id.olvide);
        registra = findViewById(R.id.registrame);
        EditText usuario = findViewById(R.id.usuarioText);
        EditText contra = findViewById(R.id.passText);

        acepta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioo = String.valueOf(usuario.getText());
                contraa = String.valueOf(contra.getText());
                entra(usuarioo, contraa);
            }
        });

        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

        olvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, OlvideContra.class);
                startActivity(intent);
            }
        });
    }

    public void entra (String usuario, String contrasena){
        if (usuario.equals("")||contrasena.equals("")){
            Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
        }else{
            BDUsuarios bdUsuarios = new BDUsuarios(Login.this);
            infoRegistro info = bdUsuarios.GetUsuario(usuario);
            if (info!=null){
                if (info.getContra().equals(contrasena)){
                    Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, WelcomeTJ.class);
                    intent.putExtra("Objeto", info);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_LONG).show();
            }
        }
    }
}