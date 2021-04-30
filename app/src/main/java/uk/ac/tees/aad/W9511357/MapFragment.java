package uk.ac.tees.aad.W9511357;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        LatLng location = new LatLng(25,25);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
        getCurrentLatLng(googleMap);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.map_fragment, container, false);
        View view = inflater.inflate(R.layout.map_fragment, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mMapView.getMapAsync(getActivity());
    }

    private void getCurrentLatLng(final GoogleMap googleMap){


        ActivityCompat.requestPermissions( getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            new AlertDialog.Builder(getActivity()).setMessage("Turn on the GPS").create().show();
        }
        else {

            boolean grantedPermissions =(
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            );

            if (grantedPermissions)
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else {
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location)
                            {
                                mMap = googleMap;
                                LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 12.0f));
                                ArrayList<LatLng> locations = new ArrayList();
                                locations.add(loc);
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
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("location",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editer = sharedPreferences.edit();
                                editer.putFloat("lat",(float)location.getLatitude());
                                editer.putFloat("lng",(float)location.getLongitude());
                                editer.apply();
                            }
                        });
            }

        }
    }


}
