package com.example.androidapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import java.util.List;
import java.util.Locale;

public class displayMap extends FragmentActivity implements OnMapReadyCallback {

    static LatLng latLng = new LatLng(0,0);
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
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }

    }
    public void onMapReady(GoogleMap map) {
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng,100);
        map.animateCamera(yourLocation);
        map.setMinZoomPreference(6.0f);
        map.setMaxZoomPreference(14.0f);

        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Marker"));
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
