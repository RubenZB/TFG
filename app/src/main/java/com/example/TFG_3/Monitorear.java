package com.example.TFG_3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;


import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

public class Monitorear extends Activity implements MonitorNotifier {
    protected static final String TAG = "Monitorear";
    private final BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private Transmisor t;
    private final DbHelper dbTransmisor = App.dbTransmisor;

    public static String nT1;
    public static String nT2;

    private ImageView foto;
    private Spinner spinner;
    private Button mostrar;
    private Button baula1;
    private Button baula2;
    private Button baula3;
    private Button baula4;
    private Button baula5;
    private Button baula6;
    private Button baula7;
    private Button baula8;
    private Button baula9;
    private Button baula10;
    private Button baula11;
    private Button baula12;
    private Button baula13;
    private Button baula14;
    private Button bbano1;
    private Button bbano2;
    private Button bbano3;
    private Button bbano4;
    private Button brepo;
    private Button bcafeteria;
    private Button blibreria;
    private Button bbanco;
    private Button bcapilla;
    private Button bentrada1;
    private Button bentrada2;
    private Button bentrada3;
    private Button bentrada4;


    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarmapa);
        BeaconManager.getInstanceForApplication(this).addMonitorNotifier(this);
        foto = findViewById(R.id.imageView);
        foto.setImageResource(R.drawable.mapaaulario);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mapa, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mostrar = findViewById(R.id.bmostrar);
        baula1 = findViewById(R.id.baula1);
        baula2 = findViewById(R.id.baula2);
        baula3 = findViewById(R.id.baula3);
        baula4 = findViewById(R.id.baula4);
        baula5 = findViewById(R.id.baula5);
        baula6 = findViewById(R.id.baula6);
        baula7 = findViewById(R.id.baula7);
        baula8 = findViewById(R.id.baula8);
        baula9 = findViewById(R.id.baula9);
        baula10 = findViewById(R.id.baula10);
        baula11 = findViewById(R.id.baula11);
        baula12 = findViewById(R.id.baula12);
        baula13 = findViewById(R.id.baula13);
        baula14 = findViewById(R.id.baula14);
        bbano1 = findViewById(R.id.bbano1);
        bbano2 = findViewById(R.id.bbano2);
        bbano3 = findViewById(R.id.bbano3);
        bbano4 = findViewById(R.id.bbano4);
        brepo = findViewById(R.id.brepo);
        bcafeteria = findViewById(R.id.bcafeteria);
        blibreria = findViewById(R.id.blibreria);
        bbanco = findViewById(R.id.bbanco);
        bcapilla = findViewById(R.id.bcapilla);
        bentrada1 = findViewById(R.id.bentrada1);
        bentrada2 = findViewById(R.id.bentrada2);
        bentrada3 = findViewById(R.id.bentrada3);
        bentrada4 = findViewById(R.id.bentrada4);

        colorBotones();


    }

    public void onResume(){
        super.onResume();
        t = null;
        RangeNotifier rangeNotifier = (beacons, region) -> {
            int d = 100;
            Beacon beaconcer = null;
            for (Beacon beacon : beacons) {
                if (beacon.getDistance() < d) {
                    d = (int) beacon.getDistance();
                    beaconcer = beacon;
                    Log.d(TAG, "Beacon: " + beacon.getId2() + " - " + beacon.getDistance() + " metros.");
                }
            }
            if (beaconcer != null) {
                colorBotones();
                t = dbTransmisor.getTransmisor(beaconcer.getId2().toString());
                nT1 = t.getNombre();
                Log.d(TAG,nT1);
                botonActual(nT1);
            }


        };
        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.setForegroundScanPeriod(1000);
        beaconManager.setForegroundBetweenScanPeriod(500);
        beaconManager.startRangingBeacons(App.escanRegion);

    }


    public void mostrarMapa(View view){
        ocultarBotones();
        String opcion = spinner.getSelectedItem().toString();


        switch (opcion) {
            case "Aulas":
                baula1.setVisibility(View.VISIBLE);
                baula2.setVisibility(View.VISIBLE);
                baula3.setVisibility(View.VISIBLE);
                baula4.setVisibility(View.VISIBLE);
                baula5.setVisibility(View.VISIBLE);
                baula6.setVisibility(View.VISIBLE);
                baula7.setVisibility(View.VISIBLE);
                baula8.setVisibility(View.VISIBLE);
                baula9.setVisibility(View.VISIBLE);
                baula10.setVisibility(View.VISIBLE);
                baula11.setVisibility(View.VISIBLE);
                baula12.setVisibility(View.VISIBLE);
                baula13.setVisibility(View.VISIBLE);
                baula14.setVisibility(View.VISIBLE);
                break;
            case "Baños":
                bbano1.setVisibility(View.VISIBLE);
                bbano2.setVisibility(View.VISIBLE);
                bbano3.setVisibility(View.VISIBLE);
                bbano4.setVisibility(View.VISIBLE);

                break;
            case "Entradas":
                bentrada1.setVisibility(View.VISIBLE);
                bentrada2.setVisibility(View.VISIBLE);
                bentrada3.setVisibility(View.VISIBLE);
                bentrada4.setVisibility(View.VISIBLE);

                break;
            case "Cafeteria":
                bcafeteria.setVisibility(View.VISIBLE);

                break;
            case "Libreria":
                blibreria.setVisibility(View.VISIBLE);

                break;
            case "Banco":
                bbanco.setVisibility(View.VISIBLE);

                break;
            case "Capilla":
                bcapilla.setVisibility(View.VISIBLE);

                break;
            case "Reprografia":
                brepo.setVisibility(View.VISIBLE);
                break;
            case "Baños 1":
                bbano1.setVisibility(View.VISIBLE);
                break;
            case "Baños 2":
                bbano2.setVisibility(View.VISIBLE);
                break;
            case "Baños 3":
                bbano3.setVisibility(View.VISIBLE);
                break;
            case "Baños 4":
                bbano4.setVisibility(View.VISIBLE);
                break;
            case "Entrada 1":
                bentrada1.setVisibility(View.VISIBLE);
                break;
            case "Entrada 2":
                bentrada2.setVisibility(View.VISIBLE);
                break;
            case "Entrada 3":
                bentrada3.setVisibility(View.VISIBLE);
                break;
            case "Entrada 4":
                bentrada4.setVisibility(View.VISIBLE);
                break;
            case "Aula 001":
                baula1.setVisibility(View.VISIBLE);
                break;
            case "Aula 002":
                baula2.setVisibility(View.VISIBLE);
                break;
            case "Aula 003":
                baula3.setVisibility(View.VISIBLE);
                break;
            case "Aula 004":
                baula4.setVisibility(View.VISIBLE);
                break;
            case "Aula 005":
                baula5.setVisibility(View.VISIBLE);
                break;
            case "Aula 006":
                baula6.setVisibility(View.VISIBLE);
                break;
            case "Aula 007":
                baula7.setVisibility(View.VISIBLE);
                break;
            case "Aula 008":
                baula8.setVisibility(View.VISIBLE);
                break;
            case "Aula 009":
                baula9.setVisibility(View.VISIBLE);
                break;
            case "Aula 010":
                baula10.setVisibility(View.VISIBLE);
                break;
            case "Aula 011":
                baula11.setVisibility(View.VISIBLE);
                break;
            case "Aula 012":
                baula12.setVisibility(View.VISIBLE);
                break;
            case "Aula 013":
                baula13.setVisibility(View.VISIBLE);
                break;
            case "Aula 014":
                baula14.setVisibility(View.VISIBLE);
                break;
            case "Todo":
                baula1.setVisibility(View.VISIBLE);
                baula2.setVisibility(View.VISIBLE);
                baula3.setVisibility(View.VISIBLE);
                baula4.setVisibility(View.VISIBLE);
                baula5.setVisibility(View.VISIBLE);
                baula6.setVisibility(View.VISIBLE);
                baula7.setVisibility(View.VISIBLE);
                baula8.setVisibility(View.VISIBLE);
                baula9.setVisibility(View.VISIBLE);
                baula10.setVisibility(View.VISIBLE);
                baula11.setVisibility(View.VISIBLE);
                baula12.setVisibility(View.VISIBLE);
                baula13.setVisibility(View.VISIBLE);
                baula14.setVisibility(View.VISIBLE);
                bbano1.setVisibility(View.VISIBLE);
                bbano2.setVisibility(View.VISIBLE);
                bbano3.setVisibility(View.VISIBLE);
                bbano4.setVisibility(View.VISIBLE);
                brepo.setVisibility(View.VISIBLE);
                bcafeteria.setVisibility(View.VISIBLE);
                blibreria.setVisibility(View.VISIBLE);
                bbanco.setVisibility(View.VISIBLE);
                bcapilla.setVisibility(View.VISIBLE);
                bentrada1.setVisibility(View.VISIBLE);
                bentrada2.setVisibility(View.VISIBLE);
                bentrada3.setVisibility(View.VISIBLE);
                bentrada4.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void ocultarBotones(){
        baula1.setVisibility(View.INVISIBLE);
        baula2.setVisibility(View.INVISIBLE);
        baula3.setVisibility(View.INVISIBLE);
        baula4.setVisibility(View.INVISIBLE);
        baula5.setVisibility(View.INVISIBLE);
        baula6.setVisibility(View.INVISIBLE);
        baula7.setVisibility(View.INVISIBLE);
        baula8.setVisibility(View.INVISIBLE);
        baula9.setVisibility(View.INVISIBLE);
        baula10.setVisibility(View.INVISIBLE);
        baula11.setVisibility(View.INVISIBLE);
        baula12.setVisibility(View.INVISIBLE);
        baula13.setVisibility(View.INVISIBLE);
        baula14.setVisibility(View.INVISIBLE);
        bbano1.setVisibility(View.INVISIBLE);
        bbano2.setVisibility(View.INVISIBLE);
        bbano3.setVisibility(View.INVISIBLE);
        bbano4.setVisibility(View.INVISIBLE);
        brepo.setVisibility(View.INVISIBLE);
        bcafeteria.setVisibility(View.INVISIBLE);
        blibreria.setVisibility(View.INVISIBLE);
        bbanco.setVisibility(View.INVISIBLE);
        bcapilla.setVisibility(View.INVISIBLE);
        bentrada1.setVisibility(View.INVISIBLE);
        bentrada2.setVisibility(View.INVISIBLE);
        bentrada3.setVisibility(View.INVISIBLE);
        bentrada4.setVisibility(View.INVISIBLE);
    }

    public void colorBotones(){
        baula1.setBackgroundColor(Color.DKGRAY);
        baula2.setBackgroundColor(Color.DKGRAY);
        baula3.setBackgroundColor(Color.DKGRAY);
        baula4.setBackgroundColor(Color.DKGRAY);
        baula5.setBackgroundColor(Color.DKGRAY);
        baula6.setBackgroundColor(Color.DKGRAY);
        baula7.setBackgroundColor(Color.DKGRAY);
        baula8.setBackgroundColor(Color.DKGRAY);
        baula9.setBackgroundColor(Color.DKGRAY);
        baula10.setBackgroundColor(Color.DKGRAY);
        baula11.setBackgroundColor(Color.DKGRAY);
        baula12.setBackgroundColor(Color.DKGRAY);
        baula13.setBackgroundColor(Color.DKGRAY);
        baula14.setBackgroundColor(Color.DKGRAY);
        bbano1.setBackgroundColor(Color.DKGRAY);
        bbano2.setBackgroundColor(Color.DKGRAY);
        bbano3.setBackgroundColor(Color.DKGRAY);
        bbano4.setBackgroundColor(Color.DKGRAY);
        brepo.setBackgroundColor(Color.DKGRAY);
        bcafeteria.setBackgroundColor(Color.DKGRAY);
        blibreria.setBackgroundColor(Color.DKGRAY);
        bbanco.setBackgroundColor(Color.DKGRAY);
        bcapilla.setBackgroundColor(Color.DKGRAY);
        bentrada1.setBackgroundColor(Color.DKGRAY);
        bentrada2.setBackgroundColor(Color.DKGRAY);
        bentrada3.setBackgroundColor(Color.DKGRAY);
        bentrada4.setBackgroundColor(Color.DKGRAY);
    }
    public void botonActual(String opcion) {
        switch (opcion) {
            case "cafeteria":
                bcafeteria.setBackgroundColor(Color.GREEN);
                break;

            case "banco":
                bbanco.setBackgroundColor(Color.GREEN);
                break;
            case "capilla":
                bcapilla.setBackgroundColor(Color.GREEN);
                break;
            case "reprografia":
                brepo.setBackgroundColor(Color.GREEN);
                break;
            case "baños1":
                bbano1.setBackgroundColor(Color.GREEN);
                break;
            case "baños2":
                bbano2.setBackgroundColor(Color.GREEN);
                break;
            case "baños3":
                bbano3.setBackgroundColor(Color.GREEN);
                break;
            case "baños4":
                bbano4.setBackgroundColor(Color.GREEN);
                break;
            case "entrada1":
                bentrada1.setBackgroundColor(Color.GREEN);
                break;
            case "entrada2":
                bentrada2.setBackgroundColor(Color.GREEN);
                break;
            case "entrada3":
                bentrada3.setBackgroundColor(Color.GREEN);
                break;
            case "entrada4":
                bentrada4.setBackgroundColor(Color.GREEN);
                break;
            case "aula1":
                baula1.setBackgroundColor(Color.GREEN);
                break;
            case "aula2":
                baula2.setBackgroundColor(Color.GREEN);
                break;
            case "aula3":
                baula3.setBackgroundColor(Color.GREEN);
                break;
            case "aula4":
                baula4.setBackgroundColor(Color.GREEN);
                break;
            case "aula5":
                baula5.setBackgroundColor(Color.GREEN);
                break;
            case "aula6":
                baula6.setBackgroundColor(Color.GREEN);
                break;
            case "aula7":
                baula7.setBackgroundColor(Color.GREEN);
                break;
            case "aula8":
                baula8.setBackgroundColor(Color.GREEN);
                break;
            case "aula9":
                baula9.setBackgroundColor(Color.GREEN);
                break;
            case "aula10":
                baula10.setBackgroundColor(Color.GREEN);
                break;
            case "aula11":
                baula11.setBackgroundColor(Color.GREEN);
                break;
            case "aula12":
                baula12.setBackgroundColor(Color.GREEN);
                break;
            case "aula13":
                baula13.setBackgroundColor(Color.GREEN);
                break;
            case "aula14":
                baula14.setBackgroundColor(Color.GREEN);
                break;
        }
    }
    public void mostrarBoton(View view){
        Button button = (Button) view;
        nT2 = button.getText().toString();
        Transmisor tAux = dbTransmisor.buscarTransmisorNombre(nT2);
        String info = tAux.getInfo();
        Log.d(TAG,nT2);

        // Mostrar una ventana flotante temporal con el nombre del botón
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" ");
        builder.setMessage(tAux.getDescripcion());
        builder.setCancelable(true);
        builder.setPositiveButton("Info", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(Monitorear.this);
                builder2.setTitle("Información");
                builder2.setMessage(Html.fromHtml(info));
                builder2.setCancelable(true);
                builder2.setPositiveButton("Aceptar",null);
                builder2.show();
            }
        });
        builder.setNegativeButton("Ir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent myIntent = new Intent(Monitorear.this, Escaner.class);
                beaconManager.stopRangingBeacons(App.escanRegion);
                beaconManager.removeAllRangeNotifiers();
                Monitorear.this.startActivity(myIntent);
            }
        });
        builder.show();
    }
    @Override
    public void didEnterRegion(Region region) { /*mostrarPorPantalla("didEnterRegion called");*/ }
    @Override
    public void didExitRegion(Region region) {/*mostrarPorPantalla("didExitRegion called");*/
    }
    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        //mostrarPorPantalla("didDetermineStateForRegion called with state: " + (state == 1 ? "INSIDE ("+state+")" : "OUTSIDE ("+state+")"));
    }
    public void menuClick(){
        beaconManager.stopRangingBeacons(App.escanRegion);
        beaconManager.removeAllRangeNotifiers();
        Intent myIntent = new Intent(this, Menu.class);
        this.startActivity(myIntent);
    }
}
