package com.example.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.splash.BaseDatos.BDContras;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapa extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    GoogleMap mMap;
    Button regresar;
    TextView lat,lon;
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        Intent intent = getIntent();
        Object object = null;
        infoRegistro info = null;
        startGps();
        lat = findViewById(R.id.Lat);
        lon = findViewById(R.id.Long);
        int idusu;
        object = intent.getExtras().get("MyInfo");
        info = (infoRegistro) object;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        regresar = findViewById(R.id.button3);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = null;
                infoRegistro info = null;
                int i = 0;
                BDContras contrasbd = null;
                contrasbd = new BDContras(getBaseContext());
                object = intent.getExtras().get("MyInfo");
                info = (infoRegistro) object;
                Intent intent2 = new Intent(mapa.this, WelcomeTJ.class);
                intent2.putExtra("MyInfo", info);
                startActivity(intent2);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);
        String latitud = getIntent().getStringExtra("Latitud");
        String longitud = getIntent().getStringExtra("Longitud");
        lat = findViewById(R.id.Lat);
        lon = findViewById(R.id.Long);
        lat.setText("Latitud: "+latitud);
        lon.setText("Longitud"+longitud);
        LatLng mexico = new LatLng(Double.parseDouble(latitud),Double.parseDouble(longitud));
        mMap.addMarker(new MarkerOptions().position(mexico).title("ubicacion"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mexico));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }

    private void startGps()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions( new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION} , 3);
            return;
        }
        if( locationManager == null )
        {
            locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
            lat.setText( "" );
            lon.setText( "" );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 3:
                if( android.Manifest.permission.ACCESS_FINE_LOCATION.equals( permissions[ 0 ]) && grantResults[ 0 ] == 0 )
                {
                    startGps();
                    return;
                }
                break;
        }
    }


    public void onLocationChanged(@NonNull Location location)
    {
        lat.setText("" + location.getLatitude( ) );
        lon.setText("" + location.getLongitude( ) );
    }


    public void onProviderEnabled(@NonNull String provider)
    {
    }


    public void onProviderDisabled(@NonNull String provider)
    {
    }

    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }
    private void stopGps( )
    {
        locationManager.removeUpdates((LocationListener) this);
        locationManager = null;
    }

}