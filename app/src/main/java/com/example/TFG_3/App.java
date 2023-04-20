package com.example.TFG_3;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.TFG_3.DB.DbHelper;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class App extends Application implements MonitorNotifier {
    private static final String TAG = "App";
    public static final Region escanRegion = new Region("escanRegion", null, null, null);
    public static boolean insideRegion = false;

    public void onCreate() {
        super.onCreate();
        BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));


        Log.d(TAG, "setting up background monitoring in app onCreate");
        beaconManager.addMonitorNotifier(this);

        for (Region region: beaconManager.getMonitoredRegions()) {
            beaconManager.stopMonitoring(region);
        }

        beaconManager.startMonitoring(escanRegion);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null) {
            Toast.makeText(this, "Base de datos creada", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void didEnterRegion(Region arg0) {
        Log.d(TAG, "did enter region.");
        insideRegion = true;
    }

    @Override
    public void didExitRegion(Region region) {
        insideRegion = false;

    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {

    }

}
