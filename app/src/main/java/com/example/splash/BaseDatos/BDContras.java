package com.example.splash.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.splash.infoPass;

import java.util.ArrayList;
import java.util.List;

public class BDContras extends UsuarioService
{
    Context context;

    public BDContras(@Nullable Context context) {
        super(context);
        this.context=context;
    }
    public long guardaContra (infoPass ipass){
        long id = 0;
        try{
            UsuarioService usuarioService = new UsuarioService(context);
            SQLiteDatabase bd = usuarioService.getWritableDatabase();

            ContentValues values = new ContentValues();
            id = bd.insert(TABLE_CONTRA, null, UsuariosContract.PassEntry.toContentValues(ipass));
        }catch (Exception ex){
            ex.toString();
        }finally {
            return id;
        }
    }
    public List<infoPass> getContras (int id){
        SQLiteDatabase database = null;
        Cursor cursor = null;
        List<infoPass>contras=null;
        infoPass ipass = null;
        database = getReadableDatabase();
        cursor = database.rawQuery("SELECT*FROM "+TABLE_CONTRA + " WHERE id = " +id, null);
        if( cursor == null )
        {
            return new ArrayList<infoPass>();
        }
        if( cursor.getCount() < 1)
        {
            return new ArrayList<infoPass>();
        }
        if( !cursor.moveToFirst() )
        {
            return new ArrayList<infoPass>();
        }

        Log.d(TAG, "" + cursor.getCount());
        contras = new ArrayList<infoPass>();
        for (int i = 0; i < cursor.getCount(); i++){
            ipass = new infoPass();
            ipass.setId_contra(cursor.getInt(0));
            ipass.setContra(cursor.getString(1));
            ipass.setRed(cursor.getString(2));
            ipass.setId_red(cursor.getInt(3));
            contras.add(ipass);
            cursor.moveToNext();
        }
        Log.d("Contraseñas", contras.toString());
        return contras;
    }

    public boolean EditContra (String red, String contrasena, int id, int id_contra){
        boolean si = false;
        UsuarioService usuarioService = new UsuarioService(context);
        SQLiteDatabase bd = usuarioService.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("contrasena", contrasena);
        values.put("red", red);

        try{
            bd.execSQL("UPDATE " + TABLE_CONTRA + " SET contrasena ='"+contrasena+"', red = '"+red+"' WHERE id = '"+id+"' AND id_contra = '"+id_contra+"' ");
            si=true;
        }catch (Exception ex){
            ex.toString();
            si=false;
        }finally{
            bd.close();
        }
        return si;
    }

    public boolean EliminaContra (int id, String red, String contrasena){
        boolean si = false;
        UsuarioService usuarioService = new UsuarioService(context);
        SQLiteDatabase bd = usuarioService.getWritableDatabase();

        try{
            bd.execSQL("DELETE FROM " + TABLE_CONTRA + " WHERE id = '"+id+"' AND contrasena ='"+contrasena+"' AND red='"+red+"'");
            si=true;
        }catch (Exception ex){
            ex.toString();
            si=false;
        }finally{
            bd.close();
        }
        return si;
    }

    public void EditContraS (String red, String contrasena, int id, int id_contra){
        UsuarioService usuariosDBService = new UsuarioService(context);
        SQLiteDatabase bd =usuariosDBService.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("contrasena",contrasena);
        values.put("red",red);
    }
}
