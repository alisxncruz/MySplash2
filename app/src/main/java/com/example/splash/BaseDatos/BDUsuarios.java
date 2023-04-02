package com.example.splash.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.splash.infoRegistro;

import java.util.ArrayList;
import java.util.List;

public class BDUsuarios extends UsuarioService{

    Context context;
    public BDUsuarios(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public long guardaUsuario(infoRegistro info)
    {
        long id = 0;
        try{
            UsuarioService usuarioService = new UsuarioService(context);
            SQLiteDatabase bd = usuarioService.getWritableDatabase();

            ContentValues values = new ContentValues();
            id = bd.insert(TABLE_USERS, null, UsuariosContract.UsuarioEntry.toContentValues(info));

        }catch(Exception ex){
            ex.toString();
        }finally {
            return id;
        }
    }

    public List<infoRegistro> getUsuarios(){
        SQLiteDatabase database = null;
        Cursor cursor = null;
        List<infoRegistro> usuarios = null;
        infoRegistro usuario = null;

        database = getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        if(cursor==null){
            return null;
        }
        if (cursor.getCount() < 1){
            return null;
        }
        if (!cursor.moveToFirst()){
            return null;
        }

        Log.d(TAG, "" + cursor.getCount());
        usuarios = new ArrayList<infoRegistro>();
        for (int i = 0; i < cursor.getCount(); i++){
            usuario = new infoRegistro();
            usuario.setUsuario(cursor.getString(0));
            usuario.setContra(cursor.getString(1));
            usuario.setCorreo(cursor.getString(2));
            usuario.setSexo(cursor.getInt(3));
            usuario.setActivado(cursor.getInt(4));
            usuario.setNombre(cursor.getString(5));
            usuario.setEdad(cursor.getString(6));
            usuarios.add(usuario);
            cursor.moveToNext();
        }
        return usuarios;
    }

    public infoRegistro GetUsuario(String usuario){
        infoRegistro info = new infoRegistro();
        UsuarioService usuarioService = new UsuarioService(context);
        SQLiteDatabase bd = usuarioService.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM t_usuarios WHERE usuario = ?";
        String [] args = {usuario};

            cursor = bd.rawQuery(query,args);
            if(cursor.moveToFirst()){
                info.setId_usr(cursor.getInt(0));
                info.setUsuario(cursor.getString(1));
                info.setContra(cursor.getString(2));
                info.setCorreo(cursor.getString(3));
                info.setSexo(cursor.getInt(4));
                info.setActivado(cursor.getInt(5));
                info.setNombre(cursor.getString(6));
                info.setEdad(cursor.getString(7));
                return info;
            }

            cursor.close();
            return null;
    }

    public infoRegistro GetUsuarioo (String usuario, String correo){
        infoRegistro info = new infoRegistro();
        UsuarioService usuarioService = new UsuarioService(context);
        SQLiteDatabase bd = usuarioService.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM t_usuarios WHERE usuario = ? AND correo = ?";
        String []args = {usuario, correo};

        cursor = bd.rawQuery(query,args);
        if (cursor.moveToFirst()){
            info.setId_usr(cursor.getInt(0));
            info.setUsuario(cursor.getString(1));
            info.setContra(cursor.getString(2));
            info.setCorreo(cursor.getString(3));
            info.setSexo(cursor.getInt(4));
            info.setActivado(cursor.getInt(5));
            info.setNombre(cursor.getString(6));
            info.setEdad(cursor.getString(7));
            return info;
        }

        cursor.close();
        return null;
    }

    public boolean EditUser (String usuario, String contrasena){
        boolean si = false;
        UsuarioService usuarioService = new UsuarioService(context);
        SQLiteDatabase bd = usuarioService.getWritableDatabase();
        try{
            bd.execSQL("UPDATE "+ TABLE_USERS + " SET contrasena = '"+ contrasena +"' WHERE usuario = '"+usuario+"'");
            si = true;
        }catch (Exception ex){
            ex.toString();
        }
        return si;
    }
}
