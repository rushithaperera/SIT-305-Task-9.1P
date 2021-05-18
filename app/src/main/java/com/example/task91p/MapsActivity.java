package com.example.task91p;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private LatLng latLng;
    String latitude, longitude;

    ArrayList<String> latitudes;
    ArrayList<String> longitudes;


    double[] latitudess;
    double[] longitudess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        latitude = getIntent().getExtras().getString("Latitude");
        longitude = getIntent().getExtras().getString("Longitude");

        latitudes = (ArrayList<String>) getIntent().getSerializableExtra("LatArray");
        longitudes = (ArrayList<String>) getIntent().getSerializableExtra("LongArray");

        latitudess = new double[latitudes.size()];
        longitudess = new double[longitudes.size()];

        for(int i = 0; i < latitudes.size(); i++){
            latitudess[i] = Double.parseDouble(latitudes.get(i));
            longitudess[i] = Double.parseDouble(longitudes.get(i));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try{
                    for(int i = 0; i < latitudes.size(); i++){
                        latLng = new LatLng(latitudess[i], longitudess[i]);
                        mMap.addMarker(new MarkerOptions().position(latLng));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng((latLng)));
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }
        catch(SecurityException e){
            e.printStackTrace();
        }
    }
}