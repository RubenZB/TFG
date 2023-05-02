package com.example.TFG_3;



import android.app.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.TFG_3.DB.DbTransmisor;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;


public class Escaner extends Activity {
    protected static final String TAG = "Escaner";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);

    private Transmisor t;

    private DbTransmisor dbTransmisor = App.dbTransmisor;

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

            t = dbTransmisor.getTransmisor(beaconcer.getId2().toString());
            mostrarPorPantalla("Has entrado en la zona del " + t.getNombre());
            mostrarPorPantalla("El beacon mas cercano es: " + beaconcer.getId2().toString() + " is about " + beaconcer.getDistance() + " meters away.");
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
