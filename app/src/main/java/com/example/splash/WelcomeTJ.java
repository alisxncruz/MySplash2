package com.example.splash;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.splash.Adapter.MyAdapter;
import com.example.splash.BaseDatos.BDContras;
import com.example.splash.Des.MyDesUtil;

import java.util.List;

public class WelcomeTJ extends AppCompatActivity {

    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public static String TAG = "mensaje";
    public static String json = null;
    public List<infoRegistro> list;
    private ListView listView;
    private List<infoPass> listo;
    String aux;
    public boolean bandera = false;
    public int pos=0;
    protected ActivityResultLauncher<Intent> someActivityResultLauncher;
    public static infoRegistro infoRegistro= null;
    EditText red,contrasena;
    Button mas,modifica,elimina, tomaF, agregaF;
    infoPass pass = new infoPass();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object object = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_tj);
        Intent intent = getIntent();
        if (intent != null){
            if(intent.getExtras() != null){
                object = intent.getExtras().get("Objeto");
                if (object != null){
                    if (object instanceof infoRegistro){
                        infoRegistro = (infoRegistro) object;
                    }
                }
            }
        }
        red = findViewById(R.id.editText1);
        contrasena = findViewById(R.id.editText2);
        mas = findViewById(R.id.buttonA);
        modifica = findViewById(R.id.buttonM);
        elimina = findViewById(R.id.buttonE);
        tomaF = findViewById(R.id.tomaFoto);
        agregaF = findViewById(R.id.agregaGal);
        listView = (ListView) findViewById(R.id.listViewId);

        BDContras bdContras = new BDContras(WelcomeTJ.this);
        listo = bdContras.getContras(infoRegistro.getId_usr());
        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
        listView.setAdapter(myAdapter);
        modifica.setEnabled(false);
        elimina.setEnabled(false);
        if(listo==null){
            Toast.makeText(getApplicationContext(), "Para agregar una contraseña de clic en el boton +", Toast.LENGTH_LONG);
            Toast.makeText(getApplicationContext(), "Escriba en los campos", Toast.LENGTH_LONG);
            Toast.makeText(getApplicationContext(), String.valueOf(infoRegistro.getId_usr()), Toast.LENGTH_LONG);
        }
        Toast.makeText(getApplicationContext(), "Para modificar o eliminar una contraseña de click en ella", Toast.LENGTH_LONG).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pass = listo.get(i);
                red.setText(pass.getRed());
                contrasena.setText(pass.getContra());
                pos=i;
                modifica.setEnabled(true);
                elimina.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Para guardar los cambios de click en guardar cambios", Toast.LENGTH_LONG).show();
            }
        });

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               BDContras bdContras = new BDContras(WelcomeTJ.this);
               boolean id = bdContras.EliminaContra(infoRegistro.getId_usr(), pass.getRed(), pass.getContra());
               if (id){
                   listo = bdContras.getContras(infoRegistro.getId_usr());
                   MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                   listView.setAdapter(myAdapter);
                   red.setText("");
                   contrasena.setText("");
                   Toast.makeText(getApplicationContext(), "Se eliminó la contraseña", Toast.LENGTH_LONG).show();
                   elimina.setEnabled(false);
                   modifica.setEnabled(false);
               }else{
                   Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
               }
            }
        });

        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String redS = String.valueOf(red.getText());
                String contra = String.valueOf(contrasena.getText());
                if (redS.equals("") || contra.equals("")) {
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
                } else {
                    BDContras bdContras = new BDContras(WelcomeTJ.this);
                    boolean id = bdContras.EditContra(redS, contra, infoRegistro.getId_usr(), pass.getId_contra());
                    if (id) {
                        listo = bdContras.getContras(infoRegistro.getId_usr());
                        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                        listView.setAdapter(myAdapter);
                        red.setText("");
                        contrasena.setText("");
                        Toast.makeText(getApplicationContext(), "Se modificó la contraseña", Toast.LENGTH_LONG).show();
                        elimina.setEnabled(false);
                        modifica.setEnabled(false);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al modificar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String redS = String.valueOf(red.getText());
                String contra = String.valueOf(contrasena.getText());
                if(redS.equals("")||contra.equals("")){
                    Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
                }else {
                    infoPass pass = new infoPass();
                    pass.setContra(contra);
                    pass.setRed(redS);
                    pass.setId_red(infoRegistro.getId_usr());
                    Toast.makeText(getApplicationContext(), String.valueOf(infoRegistro.getId_usr()), Toast.LENGTH_LONG).show();
                    BDContras dbContras = new BDContras(WelcomeTJ.this);
                    long id = dbContras.guardaContra(pass);
                    if (id > 0) {
                        listo = dbContras.getContras(infoRegistro.getId_usr());
                        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                        listView.setAdapter(myAdapter);
                        red.setText("");
                        contrasena.setText("");
                        Toast.makeText(getApplicationContext(), pass.getRed() + " " + pass.getContra(), Toast.LENGTH_LONG).show();
                        Toast.makeText(WelcomeTJ.this, "REGISTRO GURADADO", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(WelcomeTJ.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        tomaF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
                someActivityResultLauncher.launch( cameraIntent );
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu ,  menu);
        return flag;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if(id==R.id.item2){
            Intent intent= new Intent(WelcomeTJ.this,mainApi.class);
            startActivity(intent);
            //List2Json(myInfo,list);
            return true;
        }
        if(id==R.id.item3){
            Intent intent= new Intent(WelcomeTJ.this,mapa.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.item4){
            Intent intent= new Intent(WelcomeTJ.this,Login.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Permisos permisos = new Permisos();
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permisos.permisoDeCamaraConcedido(getApplicationContext());
        } else {
            permisos.permisoDeCamaraDenegado(getApplicationContext());
        }
    }

    public void tomarfoto(View view) {

    }

    public void elegirfoto(View view) {
        cargarImagen();
    }
    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"),10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path=data.getData();
        }
    }
}