package com.example.task91p;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPlace extends AppCompatActivity {

    EditText placeName, placeLocation;
    Button currentLocation, showOnMap, saveLocation;

    String latLang;
    String latitude;
    String longitude;

    ArrayList<String> arrayList1 = new ArrayList<String>();
    ArrayList<String> arrayList2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        placeName = findViewById(R.id.place_name);
        placeLocation = findViewById(R.id.location_details);
        saveLocation = findViewById(R.id.save_location);
        currentLocation = findViewById(R.id.get_currentLocation);
        showOnMap = findViewById(R.id.show_locations);

        Places.initialize(getApplicationContext(),"AIzaSyCh4xhcibPH5w36QpbsSrZLlSPf6PM8Yxs");

        placeLocation.setFocusable(false);
        placeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(AddPlace.this);

                startActivityForResult(intent, 100);
            }
        });

        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlace.this, MapsActivity.class);

                intent.putExtra("Latitude", latitude);
                intent.putExtra("Longitude", longitude);
                intent.putExtra("LatArray", arrayList1);
                intent.putExtra("LongArray", arrayList2);

                startActivity(intent);
            }
        });

        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList1.add(latitude);
                arrayList2.add(longitude);

                System.out.println(arrayList1);
                System.out.println(arrayList2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            placeLocation.setText(place.getAddress());
            latLang = String.valueOf(place.getLatLng());
            String answer = latLang.substring(latLang.indexOf("(")+1, latLang.indexOf(")"));

            String [] separated = answer.split(",");
            latitude = separated[0];
            longitude = separated[1];
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}