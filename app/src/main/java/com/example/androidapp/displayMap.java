package com.example.androidapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class displayMap extends FragmentActivity implements OnMapReadyCallback {

    static LatLng latLng = new LatLng(0,0);
    static double lat =0;
    static double lng =0;
    static String adminArea ="";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_map);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList = null;
        if(message !=null || !message.equals("")) {
            try {
                while (addressList == null) {
                    addressList = geocoder.getFromLocationName(message, 1);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            latLng = new LatLng(address.getLatitude(), address.getLongitude());
            adminArea = address.getSubAdminArea();
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            lat=latLng.latitude;
            lng = latLng.longitude;


        }

    }
    public void onMapReady(GoogleMap map) {
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng,15);
        map.animateCamera(yourLocation);
        DecimalFormat df = new DecimalFormat("#.###");
        double newLat = lat;
        double newLong = lng;
        df.format(newLat);
        String title = "County: " + adminArea ;
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title));
    }
    public static class FirstFragment extends MapFragment implements OnMapReadyCallback {
        GoogleMap googleMap;
        MapView mapView;
        View view;

        public void FirstFragment(){}

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){
            view = inflater.inflate(R.layout.display_map, container, false);
            //googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            return mapView;
        }

        public View onCreateViewCreated(View view, Bundle savedInstances){
           // super.onCreateViewCreated(view, savedInstances);

            mapView = (MapView) view.findViewById(R.id.map);

            if(mapView == null){
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(this);
            }
            return view;
        }
        public GoogleMap getMap(){
            return googleMap;
        }

        @Override
        public void onMapReady(GoogleMap gMap) {
            //MapsInitializer.initialize(getContext());
            googleMap = gMap;
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.addMarker(new MarkerOptions().position(latLng));
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 5);
            gMap.animateCamera(yourLocation);
        }
    }

}
