package com.example.TFG_3;



import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;


public class Escaner extends Activity {
    protected static final String TAG = "Escaner";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);

    private Transmisor t;

    private DbHelper dbTransmisor = App.dbTransmisor;

    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear);


    }

    @Override
    protected void onResume() {
        super.onResume();
        img = findViewById(R.id.imagen);
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
            if(t == null){
                mostrarPorPantalla("No se ha encontrado el transmisor");
                return;
            }else {
                mostrarPorPantalla("Has entrado en la zona de " + t.getNombre()+" con id:" + beaconcer.getId2().toString() + " is about " + beaconcer.getDistance() + " meters away.");
                Log.d(TAG, "didRangeBeaconsInRegion called with beacon: " + beaconcer.toString());
                Integer im = getResources().getIdentifier(t.getImagen(), "drawable", getPackageName());
                img.setImageDrawable(getResources().getDrawable(im));
            }

        };
        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.setForegroundScanPeriod(500);
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
            editText.setText("");
            editText.append(line+"\n");
        });
    }
}
