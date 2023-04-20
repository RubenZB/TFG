package com.example.TFG_3;



import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;

import java.util.ArrayList;


public class Escaner extends Activity {
    protected static final String TAG = "Escaner";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private Trasmisor t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear);
    }

    @Override
    protected void onResume() {
        super.onResume();
        t = null;
        RangeNotifier rangeNotifier = (beacons, region) -> {
            int d = 100;
            Beacon beaconcer = null;
            for(Beacon beacon : beacons){
               if(beacon.getDistance() < d){
                    d = (int)beacon.getDistance();
                    beaconcer = beacon;
                }
            }
            if (beaconcer.getId2().toString().equals("0xc36a2cf3dd73")){
                mostrarPorPantalla("Has entrado en la zona del baño");
                mostrarPorPantalla("El beacon mas cercano es: " + beaconcer.getId2().toString() + " is about " + beaconcer.getDistance() + " meters away.");
            }else if (beaconcer.getId2().toString().equals("0x369586a8a974")) {
                mostrarPorPantalla("Has entrado en la zona de la habitacion");
                mostrarPorPantalla("El beacon mas cercano es: " + beaconcer.getId2().toString() + " is about " + beaconcer.getDistance() + " meters away.");
            }else if (beaconcer.getId2().toString().equals("0x640b6d242840")) {
                mostrarPorPantalla("Has entrado en la zona del salón");
                mostrarPorPantalla("El beacon mas cercano es: " + beaconcer.getId2().toString() + " is about " + beaconcer.getDistance() + " meters away.");
           }
                       Log.d(TAG, "didRangeBeaconsInRegion called with beacon: " + beaconcer.toString());
        };
        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.setForegroundScanPeriod(10000);
        beaconManager.startRangingBeacons(App.escanRegion);
    }

    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.stopRangingBeacons(App.escanRegion);
        beaconManager.removeAllRangeNotifiers();
    }

    private void mostrarPorPantalla(final String line) {
        runOnUiThread(() -> {
            EditText editText = Escaner.this.findViewById(R.id.rangingText);
            editText.append(line+"\n");
        });
    }
}
