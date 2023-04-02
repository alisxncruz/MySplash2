package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.splash.BaseDatos.UsuarioService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        
        UsuarioService usuarioService = new UsuarioService(MainActivity.this);
        SQLiteDatabase database = usuarioService.getWritableDatabase();

        if(database != null)
        {
            Toast.makeText(this, "BASE CREADA", Toast.LENGTH_LONG);
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}