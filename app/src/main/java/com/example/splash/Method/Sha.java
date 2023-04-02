package com.example.splash.Method;

import androidx.core.util.PatternsCompat;

import com.example.splash.Login;
import com.example.splash.OlvideContra;
import com.example.splash.Registro;
import com.example.splash.infoRegistro;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Sha
{
    public static final String TAG = "Alison";
    private static final char [] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static byte[] createSha1 (String text){
        MessageDigest messageDigest = null;
        byte[] bytes = null;
        byte[] bytesResult = null;
        try
        {
            messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = text.getBytes("iso-8859-1");
            messageDigest.update(bytes, 0, bytes.length);
            bytesResult = messageDigest.digest();
            return bytesResult;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static boolean emailValidado (String correo){
        boolean bandera;
        if(correo.isEmpty()){
            bandera=false;
        }else{
            if(PatternsCompat.EMAIL_ADDRESS.matcher(correo).matches()){
                bandera=true;
            }else{
                bandera=false;
            }
        }
        return bandera;
    }

    public static boolean usuarios(List<infoRegistro> list, String usuario, String correo){
        boolean bandera = false;
        for(infoRegistro info : list){
            if(info.getUsuario().equals(usuario) || info.getCorreo().equals(correo)){
                bandera=true;
            }
        }
        return bandera;
    }

    public static void myInfo (infoRegistro info){
        info.setUsuario(Registro.usuarioo);
        String contra = Registro.passwordd;
        info.setContra(contra);
        info.setNombre(Registro.nombree);
        info.setEdad(Registro.edadd);
        info.setCorreo(Registro.correoo);
        info.setSexo(Registro.sw);
        info.setActivado(Registro.activado);
    }

    /*public static void encuentra(String cadena){
        for(infoRegistro info: OlvideContra.list){
            if(Login.usuario.equals(info.getUsuario())){
                cadena = "El usuario existe, recuerde la contraseña";
            }else{
                cadena = "El usuario no existe, recuerde todo";
            }
        }*/
}
