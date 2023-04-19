package com.example.splash;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.splash.BaseDatos.BDContras;
import com.example.splash.Des.MyDesUtil;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class WelcomeTJ extends AppCompatActivity implements LocationListener {

    public MyDesUtil myDesUtil = new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public static String TAG = "mensaje";
    public static String json = null;
    public List<infoRegistro> list;
    private ListView listView;
    private List<infoPass> listo;
    String aux;
    public boolean bandera = false;
    public int pos = 0;
    int REQUEST_CODE = 200;
    public static Double latitud;
    public static Double longitud;
    private LocationManager locationManager;
    protected ActivityResultLauncher<Intent> someActivityResultLauncher;
    public static infoRegistro infoRegistro = null;
    Object object = null;
    EditText red, contrasena;
    ImageView imagen;
    Button mas, modifica, elimina, tomaF, agregaF, verU;
    infoPass pass = new infoPass();

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_tj);

        startGps();
        verificarPermisoU();
        takePhotoRegister();

        mas = findViewById(R.id.buttonA);
        modifica = findViewById(R.id.buttonM);
        elimina = findViewById(R.id.buttonE);
        tomaF = findViewById(R.id.tomaFoto);
        agregaF = findViewById(R.id.agregaGal);
        imagen = findViewById(R.id.imagen);
        verU = findViewById(R.id.buttonU);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null) {
                object = intent.getExtras().get("Objeto");
                if (object != null) {
                    if (object instanceof infoRegistro) {
                        infoRegistro = (infoRegistro) object;
                    }
                }
            }
        }
        red = findViewById(R.id.editText1);
        contrasena = findViewById(R.id.editText2);

        BDContras bdContras = new BDContras(WelcomeTJ.this);
        listo = bdContras.getContras(infoRegistro.getId_usr());

        if (listo == null) {
            Toast.makeText(getApplicationContext(), "Para agregar una contraseña de clic en el boton +", Toast.LENGTH_LONG);

        }
        Toast.makeText(getApplicationContext(), "Para modificar o eliminar una contraseña de click en ella", Toast.LENGTH_LONG).show();

        listView = (ListView) findViewById(R.id.listViewId);
        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
        listView.setAdapter(myAdapter);
        modifica.setEnabled(false);
        elimina.setEnabled(false);
        verU.setEnabled(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pass = listo.get(i);
                red.setText(pass.getRed());
                contrasena.setText(pass.getContra());
                pos = i;
                toast(i);
                modifica.setEnabled(true);
                elimina.setEnabled(true);
                agregaF.setEnabled(false);
                tomaF.setEnabled(false);
                verU.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Para guardar los cambios de click en guardar cambios", Toast.LENGTH_LONG).show();
            }
        });

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BDContras bdContras = new BDContras(WelcomeTJ.this);
                boolean id = bdContras.EliminaContra(infoRegistro.getId_usr(), pass.getRed(), pass.getContra());
                if (id) {
                    listo = bdContras.getContras(infoRegistro.getId_usr());
                    MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                    listView.setAdapter(myAdapter);
                    red.setText("");
                    contrasena.setText("");
                    Toast.makeText(getApplicationContext(), "Se eliminó la contraseña", Toast.LENGTH_LONG).show();
                    elimina.setEnabled(false);
                    modifica.setEnabled(false);
                    agregaF.setEnabled(false);
                    tomaF.setEnabled(false);
                    verU.setEnabled(false);
                } else {
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
                        tomaF.setEnabled(false);
                        agregaF.setEnabled(false);
                        verU.setEnabled(false);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al modificar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        tomaF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarPermisoCamara();

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(cameraIntent);
            }
        });

        agregaF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirfoto(v);
            }
        });

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String redS = String.valueOf(red.getText());
                String contra = String.valueOf(contrasena.getText());
                ImageView imagen = findViewById(R.id.imagen);


                if (redS.equals("") || contra.equals("")) {
                    Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
                } else {
                    Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    byte[] byteArray = bytes.toByteArray();

                    infoPass pass = new infoPass();
                    pass.setContra(contra);
                    pass.setRed(redS);
                    pass.setData(byteArray);
                    pass.setId_red(infoRegistro.getId_usr());

                    Toast.makeText(getApplicationContext(), String.valueOf(infoRegistro.getId_usr()), Toast.LENGTH_LONG).show();
                    BDContras dbContras = new BDContras(WelcomeTJ.this);
                    long id = dbContras.guardaContra(pass);
                    if (id > 0) {
                        listo = dbContras.getContras(infoRegistro.getId_usr());
                        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                        listView.setAdapter(myAdapter);
                        Toast.makeText(WelcomeTJ.this, "Contraseña guardada", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(WelcomeTJ.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        verU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeTJ.this, mapa.class);
                startActivity(intent);
            }
        });
    }

    private void toast(int i) {
        Toast.makeText(getBaseContext(), listo.get(i).getContra(), Toast.LENGTH_SHORT);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void verificarPermisoU() {
        int permisoUbi = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permisoUbi2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);


        if (permisoUbi == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso Ubicación ", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        if (permisoUbi2 == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso Ubicación ", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void verificarPermisoCamara() {
        int permisoCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permisoCamara == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso Camara ", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
        }
    }

    public void takePhotoRegister() {
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getExtras() != null) {
                                Bitmap photo = (Bitmap) data.getExtras().get("data");
                                imagen.setImageBitmap(photo);
                            }
                        }
                    }
                });
    }

    public void elegirfoto(View view) {
        cargar();
    }

    public void cargar() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccionar"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        longitud = location.getLongitude();
        latitud = location.getLatitude();

        if (latitud != 0 && longitud != 0) {

            infoPass infop = new infoPass();
            infop.setLongitud(longitud);
            infop.setLatitud(latitud);
            Toast.makeText(this, String.valueOf(longitud), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, String.valueOf(latitud), Toast.LENGTH_SHORT).show();
        }
            stopGps();
    }

    private void startGps() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
            return;
        }
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, (float) 0, (LocationListener) this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 3:
                if (android.Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[0]) && grantResults[0] == 0) {
                    startGps();
                    return;
                }
                break;
        }
    }

    private void stopGps() {
        locationManager.removeUpdates((LocationListener) this);
        locationManager = null;
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item2) {
            Intent intent = new Intent(WelcomeTJ.this, mainApi.class);
            startActivity(intent);
            //List2Json(myInfo,list);
            return true;
        }
        if (id == R.id.item3) {
            Intent intent = new Intent(WelcomeTJ.this, Login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

