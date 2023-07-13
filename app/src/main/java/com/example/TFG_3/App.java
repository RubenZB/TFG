package com.example.TFG_3;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;


public class App extends Application implements MonitorNotifier {

    private static final String TAG = "App";
    public static final Region escanRegion = new Region("escanRegion", null, null, null);

    public static DbHelper dbTransmisor;

    public static boolean insideRegion = false;

    private Grafo grafo;

    public void onCreate() {
        super.onCreate();
        dbTransmisor = new DbHelper(getApplicationContext());
        SQLiteDatabase db = dbTransmisor.getWritableDatabase();
        grafo = new Grafo();
        if (db != null) {
            Toast.makeText(this, "Base de datos creada", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
        }
        dbTransmisor.cargarDatosCSV(db);
        dbTransmisor.mostrarContenidoBaseDatos();
        grafo.leerTransmisoresDesdeCSV(this,"nodos.csv");
        grafo.cargarConexionesDesdeCSV(this,"grafos.csv");


        BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));

        Log.d(TAG, "setting up background monitoring in app onCreate");
        beaconManager.addMonitorNotifier(this);

        for (Region region: beaconManager.getMonitoredRegions()) {
            beaconManager.stopMonitoring(region);
        }

        beaconManager.startMonitoring(escanRegion);

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
