
package com.example.splash.BaseDatos;

import static com.example.splash.BaseDatos.UsuarioService.TABLE_CONTRA;
import static com.example.splash.BaseDatos.UsuarioService.TABLE_USERS;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.example.splash.infoPass;
import com.example.splash.infoRegistro;

import java.io.Serializable;

public class UsuariosContract implements Serializable {
    public static final String TAG = "UsuariosContract";

    public static abstract class UsuarioEntry implements BaseColumns
    {
        public static final String USUARIO = "usuario";
        public static final String getCreateTable()
        {
            String table = "CREATE TABLE " + TABLE_USERS + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "usuario TEXT NOT NULL UNIQUE, " +
                    "contrasena TEXT NOT NULL, " +
                    "correo TEXT NOT NULL, " +
                    "sexo INTEGER, " +
                    "terminos INTEGER, " +
                    "nombre TEXT, " +
                    "edad TEXT" +
                    ")";
            return table;
        }

        public static ContentValues toContentValues (infoRegistro info){
            ContentValues values = new ContentValues();
            values.put("usuario", info.getUsuario());
            values.put("contrasena", info.getContra());
            values.put("correo", info.getCorreo());
            values.put("sexo", info.getSexo());
            values.put("terminos", info.getActivado());
            values.put("nombre", info.getNombre());
            values.put("edad", info.getEdad());
            return values;
        }
    }

    public abstract static class PassEntry implements BaseColumns{
        public static final String getCreateTable(){
            String table ="CREATE TABLE "+TABLE_CONTRA+"(" +
                    "id_contra INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "contrasena TEXT NOT NULL," +
                    "red TEXT NOT NULL," +
                    "img BLOB," +
                    "long DOUBLE," +
                    "lat DOUBLE," +
                    "id INTEGER NOT NULL)";
            return table;
        }

        public static ContentValues toContentValues (infoPass pass){
            ContentValues values = new ContentValues();
            values.put("contrasena", pass.getContra());
            values.put("red", pass.getRed());
            values.put("img", pass.getData());
            values.put("long", pass.getLongitud());
            values.put("lat", pass.getLatitud());
            values.put("id", pass.getId_red());
            return values;
        }
    }
}
