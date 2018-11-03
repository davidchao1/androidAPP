package com.example.androidapp;
import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE= "hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void sendMessage(android.view.View v ){
        Intent intent = new Intent(this, displayMap.class);
        EditText editText =(EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        //Geocoder geocoder = new Geocoder(this, Locale.getDefault());
       // List<Address> addressList = null;
//        if(message !=null || !message.equals("")){
//            try{
//                while(addressList==null) {
//                    addressList = geocoder.getFromLocationName(message, 1);
//                }
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            Address address = addressList.get(0);
//            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
//            //System.out.println(latLng);
//        }
        intent.putExtra(EXTRA_MESSAGE, message);

         startActivity(intent);
    }

}
