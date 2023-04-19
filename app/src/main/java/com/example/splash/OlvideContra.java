package com.example.splash;

import static com.example.splash.R.id.correoRecupera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.splash.BaseDatos.BDUsuarios;
import com.example.splash.Des.MyDesUtil;
import com.example.splash.Method.Sha;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

public class OlvideContra extends AppCompatActivity {
    public static List<infoRegistro> list;
    public static String json = null;
    public static String TAG = "mensaje";
    public static String TOG = "error";
    public static String cadena= null;
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public String usr=null;
    public String correo,mensaje, contra, nueva, nuevaa;
    EditText usuario,email;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_contra);
        usuario= findViewById(R.id.usuarioOlvi);
        email=findViewById(R.id.correoRecupera);
        button = findViewById(R.id.recuperabtn);

        BDUsuarios dbUsuarios = new BDUsuarios(OlvideContra.this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr = String.valueOf(usuario.getText());
                correo= String.valueOf(email.getText());
                infoRegistro User = dbUsuarios.GetUsuarioo(usr, correo);
                if(usr.equals("")&&email.equals("")){
                    Toast.makeText(getApplicationContext(), "Complete algún campo", Toast.LENGTH_LONG).show();
                }else{
                    if(User == null){
                        Toast.makeText(getApplicationContext(), "Revise bien sus datos, no coinciden", Toast.LENGTH_LONG).show();
                    }else{
                        correo=User.getCorreo();
                        contra=User.getContra();
                        nueva = String.format("%d", (int)(Math.random()*1000));
                        nuevaa = Sha.bytesToHex(Sha.createSha1(nueva));
                        mensaje="<!DOCTYPE html>\n" +
                                "<html lang =\"en\">\n" +
                                "\n" +
                                "  <head>\n" +
                                "    <meta charset = \"UTF-8\">\n" +
                                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                                "    <meta name=\"viewport\" content=\"width=device-width, inicial scale=1.0\">\n" +
                                "    <title>Codigo de recuperación</title>" +
                                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                                "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                                "    <link href=\"https://fonts.googleapis.com/css2?family=Montserrat:wght@300&display=swap\" rel=\"stylesheet\">\n" +
                                "        <style type=\"text/css\">\n" +
                                "         p { color: black; font-family: 'Montserrat' ; font-size: 30px; }\n" +
                                "         </style>\n" +
                                "        <title>Restauración de contraseña</title>\n" +

                                "        <meta charset=\"UTF-8\">\n" +
                                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                                "        \n" +
                                "  </head>\n" +
                                "    \n" +
                                "    <body >\n" +
                                "        <div style=\"text-align: center;\"><center>\n" +
                                "            <p>RECUPERANDO TU CONTRASEÑA</p>\n" +
                                "            <img src=\"https://cdn-icons-png.flaticon.com/512/616/616545.png\" width=\"150\" height=\"150\"  />\n" +
                                "            <br>\n" +
                                "            <p>Código de recuperación: "+ nueva +"</p>\n" +
                                "            </center>\n" +
                                "        </div>\n" +
                                "    </body>\n" +
                                "</html>\n";
                        correo=myDesUtil.cifrar(correo);
                        mensaje=myDesUtil.cifrar(mensaje);
                        boolean f = dbUsuarios.EditUser(usr,nuevaa);
                        if(f){
                            if(sendInfo(correo,mensaje)){
                                Toast.makeText(getApplicationContext(), "Se ha enviado el código a su correo", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(OlvideContra.this, Restaura.class);
                                startActivity(in);
                            }else{Toast.makeText(getApplicationContext(), "Error con sendinfo", Toast.LENGTH_LONG).show();}

                        }else{
                            Toast.makeText(getApplicationContext(), "Error al enviar correo", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });


    }
    public boolean sendInfo( String correo ,String mensaje)
    {
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/envio_correo";
        RequestQueue requestQueue = null;
        if( correo == null || correo.length() == 0 )
        {
            return false;
        }
        jsonObject = new JSONObject( );
        try
        {
            jsonObject.put("correo" , correo );
            jsonObject.put("mensaje", mensaje);
            String hola = jsonObject.toString();
            Log.i(TAG,hola);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.i(TAG, response.toString());
            }
        } , new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e  (TOG, error.toString());
            }
        } );
        requestQueue = Volley.newRequestQueue( getBaseContext() );
        requestQueue.add(jsonObjectRequest);

        return true;
    }

}