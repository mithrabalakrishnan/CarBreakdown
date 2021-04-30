package uk.ac.tees.aad.W9511357;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Success extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        ((TextView)findViewById(R.id.name)).setText(getIntent().getExtras().get("name").toString());
        ((TextView)findViewById(R.id.email)).setText(getIntent().getExtras().get("email").toString());
        ((TextView)findViewById(R.id.mobile)).setText(getIntent().getExtras().get("mobile").toString());
         String car = getIntent().getExtras().get("car").toString()+"/"+getIntent().getExtras().get("brand").toString();
        ((TextView)findViewById(R.id.car)).setText(car);
        ((TextView)findViewById(R.id.number )).setText(getIntent().getExtras().get("number").toString());

    }


        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            GoogleMap mMap = googleMap;
            float lat = getIntent().getExtras().getFloat("lat");
            float lng = getIntent().getExtras().getFloat("lng");
            LatLng location = new LatLng(lat,lng);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
            ArrayList<LatLng> locations = new ArrayList();
            locations.add(location);
            locations.add(new LatLng(54.568989,-1.239270));
            locations.add(new LatLng(54.563200,-1.233610));
            locations.add(new LatLng(54.568230,-1.314430));
            locations.add(new LatLng(54.536720,-1.293970));
            for(LatLng locationNew : locations){
                int i = 1;
                mMap.addMarker(new MarkerOptions()
                        .position(locationNew)
                        .title("My Location"+i));
                i++;

            }

        }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Success.this, HomeActivity.class);
        startActivity(intent);
    }
}
