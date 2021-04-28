package uk.ac.tees.aad.W9511357;

import androidx.appcompat.app.AppCompatActivity;

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

    }


        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            GoogleMap mMap = googleMap;
            float lat = getIntent().getExtras().getFloat("lat");
            float lng = getIntent().getExtras().getFloat("lng");
            LatLng location = new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions().position(location).title("MY Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));


        }
}
