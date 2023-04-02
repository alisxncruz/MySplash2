package com.example.splash;

import static com.example.splash.R.id.correoRecupera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.splash.BaseDatos.BDUsuarios;
import com.example.splash.Des.MyDesUtil;

import java.util.List;

public class OlvideContra extends AppCompatActivity {
    public static List<infoRegistro> list;
    public static String json = null;
    public static String TAG = "mensaje";
    public static String TOG = "error";
    public static String cadena = null;
    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public String usuarioo = null;
    public String correoo, mensaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_contra);

        EditText user = findViewById(R.id.usuarioOlvi);
        EditText correo = findViewById(R.id.correoRecupera);
        Button recupera = findViewById(R.id.recuperabtn);

        BDUsuarios bdUsuarios = new BDUsuarios(OlvideContra.this);

        recupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioo = String.valueOf(user.getText());
                correoo = String.valueOf(correo.getText());
                infoRegistro myinfo = bdUsuarios.GetUsuarioo(usuarioo, correoo);
                if(usuarioo.equals("")&& correoo.equals("")){
                    Toast.makeText(getApplicationContext(), "Asegurese de completar los campos", Toast.LENGTH_LONG).show();
                }else{
                    if (myinfo == null){
                        Toast.makeText(getApplicationContext(), "El usuario o correo no fueron encontrados", Toast.LENGTH_LONG).show();
                    }else{
                        correoo = myinfo.getCorreo();
                        String contra = myinfo.getContra();
                        
                    }
                }
            }
        });
    }
}