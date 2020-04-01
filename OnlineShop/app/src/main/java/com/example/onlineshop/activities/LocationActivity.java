package com.example.onlineshop.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshop.MainActivity;
import com.example.onlineshop.R;
import com.example.onlineshop.model.Product;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LocationActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private static final String TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        this.setTitle("Location");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        findViewById(R.id.get_location_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("coords", ((TextView) findViewById(R.id.coordinates_text)).getText().toString());
        outState.putString("address", ((TextView) findViewById(R.id.address_text)).getText().toString());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(Objects.equals(savedInstanceState.getString("coords"), "") && Objects.equals(savedInstanceState.getString("address"), ""))
            ((TextView)findViewById(R.id.location_prompter_text)).setText("Click the button to get your current location");
        else{
            ((TextView)findViewById(R.id.location_prompter_text)).setText("Your location: ");
        }
        ((TextView)findViewById(R.id.address_text)).setText(savedInstanceState.getString("address"));
        ((TextView)findViewById(R.id.coordinates_text)).setText(savedInstanceState.getString("coords"));

        super.onRestoreInstanceState(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length <= 0) {
                Log.i("LOCATION", "User cancelled");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permissions required", Toast.LENGTH_LONG).show();
                ((TextView) findViewById(R.id.address_text)).setText("Permissions required");
                ((TextView) findViewById(R.id.coordinates_text)).setText("");
            }
        }
    }

    private boolean checkUserHasPermissions() {
        return  ! (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    private void requestUserPermissions(){
        Log.d("LOCATION", "Requesting permissions for location");

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private void getCurrentLocation() throws SecurityException {
        if (!checkUserHasPermissions()) {
            requestUserPermissions();
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            ((TextView)findViewById(R.id.location_prompter_text)).setText("Your location: ");
                            ((TextView) findViewById(R.id.coordinates_text)).setText("Lat: " + location.getLatitude() + " Long: " + location.getLongitude());

                            Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
                            List<Address> addresses  = null;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);

                                ((TextView) findViewById(R.id.address_text)).setText(addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
        });
    }

}
