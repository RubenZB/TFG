package com.example.TFG_3;



import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;


public class Escaner extends Activity {
    protected static final String TAG = "Escaner";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);

    private Transmisor t;

    private DbHelper dbTransmisor = App.dbTransmisor;
    private Grafo grafo = App.grafo;
    private String nT1 = Monitorear.nT1;
    private String nT2 = Monitorear.nT2;

    private ImageView img;
    private ImageView entrada1;
    private ImageView aula1;
    private ImageView aula2;
    private ImageView baños1;
    private ImageView aula3;
    private ImageView aula4;
    private ImageView aula5;
    private ImageView entrada2;
    private ImageView capilla;
    private ImageView aula6;
    private ImageView aula7;
    private ImageView baños2;
    private ImageView aula8;
    private ImageView aula9;
    private ImageView entrada3;
    private ImageView cafeteria;
    private ImageView baños3;
    private ImageView repografia;
    private ImageView aula10;
    private ImageView banco;
    private ImageView entrada4;
    private ImageView aula11;
    private ImageView aula12;
    private ImageView baños4;
    private ImageView aula13;
    private ImageView aula14;
    private ImageView patio1;
    private ImageView patio2;
    private ImageView patio3;
    private ImageView patio4;
    private ImageView patio5;
    private ImageView patio6;
    private ImageView patio7;
    private ImageView patio8;
    private ImageView patio9;
    private ImageView patio10;
    private ImageView patio11;
    private ImageView patio12;
    private ImageView patio13;
    private ImageView patio14;
    private ImageView patio15;
    private ImageView a2b1;
    private ImageView a1a2;
    private ImageView e1a1;
    private ImageView a14e1;
    private ImageView a14a13;
    private ImageView a13b4;
    private ImageView b2a8;
    private ImageView a8a9;
    private ImageView a9e3;
    private ImageView e3caf;
    private ImageView cafb3;
    private ImageView e2p7;
    private ImageView p7p8;
    private ImageView p8p9;
    private ImageView p9e4;
    private ImageView b1a3;
    private ImageView a3a4;
    private ImageView a4a5;
    private ImageView a5e2;
    private ImageView e2cap;
    private ImageView capa6;
    private ImageView a6a7;
    private ImageView a7b2;
    private ImageView b4a12;
    private ImageView a12a11;
    private ImageView a11e4_1;
    private ImageView a11e4_2;
    private ImageView e4ban;
    private ImageView bana10;
    private ImageView a10rep;
    private ImageView repb3;
    private ImageView e1p2;
    private ImageView p2p6;
    private ImageView p6p8;
    private ImageView p8p10;
    private ImageView p10p14;
    private ImageView p14e3;
    private ImageView b1p1;
    private ImageView p1p4;
    private ImageView p4p6;
    private ImageView b4p3;
    private ImageView p3p5;
    private ImageView p5p6;
    private ImageView b2p13;
    private ImageView p13p11;
    private ImageView b3p15;
    private ImageView p15p12;
    private ImageView p11p10;
    private ImageView p12p10;

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
        /*RangeNotifier rangeNotifier = (beacons, region) -> {
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
                //mostrarPorPantalla("No se ha encontrado el transmisor");
                return;
            }else {
                //mostrarPorPantalla("Has entrado en la zona de " + t.getNombre()+" con id:" + beaconcer.getId2().toString() + " is about " + beaconcer.getDistance() + " meters away.");
                Log.d(TAG, "didRangeBeaconsInRegion called with beacon: " + beaconcer.toString());
            }

        };
        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.setForegroundScanPeriod(500);
        beaconManager.startRangingBeacons(App.escanRegion);*/

        Transmisor t1 = grafo.buscarTransmisorPorNombre(nT1);
        Log.d(TAG, "Transmisor 1: " + t1.toString());
        Transmisor t2 = grafo.buscarTransmisorPorNombre(nT2);
        Log.d(TAG, "Transmisor 2: " + t2.toString());

        List<Transmisor> camino = grafo.obtenerCaminoMasRapido(t1,t2);

        if(camino.size() == 0) {
            Log.d(TAG, "No se ha encontrado camino");
        }else{
            Log.d(TAG, "Camino encontrado");
            for(Transmisor t : camino){
                mostrarPunto(t.getNombre());
            }
        }
        List<String> arcos = grafo.obtenerArcos(t1,t2);
        if(arcos.size() == 0) {
            Log.d(TAG, "No se ha encontrado camino");
        }else{
            for(String a : arcos){
                Log.d(TAG, "Arco: " + a);
                mostrarArco(a);
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //beaconManager.stopRangingBeacons(App.escanRegion);
        //beaconManager.removeAllRangeNotifiers();
    }

    private void mostrarPorPantalla(final String line) {
        runOnUiThread(() -> {
            EditText editText = Escaner.this.findViewById(R.id.rangingText);
            editText.setText("");
            editText.append(line+"\n");
        });
    }

    public void menuClick() {
        beaconManager.stopRangingBeacons(App.escanRegion);
        beaconManager.removeAllRangeNotifiers();
        Intent myIntent = new Intent(this, Menu.class);
        this.startActivity(myIntent);
    }


    public void mostrarPunto(String punto){
        switch (punto){
            case "entrada1":
                entrada1 = findViewById(R.id.pent1);
                entrada1.setVisibility(ImageView.VISIBLE);
                break;
            case "aula1":
                aula1 = findViewById(R.id.paula1);
                aula1.setVisibility(ImageView.VISIBLE);
                break;
            case "aula2":
                aula2 = findViewById(R.id.paula2);
                aula2.setVisibility(ImageView.VISIBLE);
                break;
            case"baños1":
                baños1 = findViewById(R.id.pbaños1);
                baños1.setVisibility(ImageView.VISIBLE);
                break;
            case "aula3":
                aula3 = findViewById(R.id.paula3);
                aula3.setVisibility(ImageView.VISIBLE);
                break;
            case "aula4":
                aula4 = findViewById(R.id.paula4);
                aula4.setVisibility(ImageView.VISIBLE);
                break;
            case "aula5":
                aula5 = findViewById(R.id.paula5);
                aula5.setVisibility(ImageView.VISIBLE);
                break;
            case "entrada2":
                entrada2 = findViewById(R.id.pent2);
                entrada2.setVisibility(ImageView.VISIBLE);
                break;
            case "capilla":
                capilla = findViewById(R.id.pcap);
                capilla.setVisibility(ImageView.VISIBLE);
                break;
            case "aula6":
                aula6 = findViewById(R.id.paula6);
                aula6.setVisibility(ImageView.VISIBLE);
                break;
            case "aula7":
                aula7 = findViewById(R.id.paula7);
                aula7.setVisibility(ImageView.VISIBLE);
                break;
            case "baños2":
                baños2 = findViewById(R.id.pbaños2);
                baños2.setVisibility(ImageView.VISIBLE);
                break;
            case "aula8":
                aula8 = findViewById(R.id.paula8);
                aula8.setVisibility(ImageView.VISIBLE);
                break;
            case "aula9":
                aula9 = findViewById(R.id.paula9);
                aula9.setVisibility(ImageView.VISIBLE);
                break;
            case "entrada3":
                entrada3 = findViewById(R.id.pent3);
                entrada3.setVisibility(ImageView.VISIBLE);
                break;
            case "cafeteria":
                cafeteria = findViewById(R.id.pcafe);
                cafeteria.setVisibility(ImageView.VISIBLE);
                break;
            case "baños3":
                baños3 = findViewById(R.id.pbaños3);
                baños3.setVisibility(ImageView.VISIBLE);
                break;
            case "repografia":
                repografia = findViewById(R.id.prepo);
                repografia.setVisibility(ImageView.VISIBLE);
                break;
            case "aula10":
                aula10 = findViewById(R.id.paula10);
                aula10.setVisibility(ImageView.VISIBLE);
                break;
            case "banco":
                banco = findViewById(R.id.pbanco);
                banco.setVisibility(ImageView.VISIBLE);
                break;
            case "entrada4":
                entrada4 = findViewById(R.id.pent4);
                entrada4.setVisibility(ImageView.VISIBLE);
                break;
            case "aula11":
                aula11 = findViewById(R.id.paula11);
                aula11.setVisibility(ImageView.VISIBLE);
                break;
            case "aula12":
                aula12 = findViewById(R.id.paula12);
                aula12.setVisibility(ImageView.VISIBLE);
                break;
            case "baños4":
                baños4 = findViewById(R.id.pbaños4);
                baños4.setVisibility(ImageView.VISIBLE);
                break;
            case "aula13":
                aula13 = findViewById(R.id.paula13);
                aula13.setVisibility(ImageView.VISIBLE);
                break;
            case "aula14":
                aula14 = findViewById(R.id.paula14);
                aula14.setVisibility(ImageView.VISIBLE);
                break;
            case "patio1":
                patio1 = findViewById(R.id.ppatio1);
                patio1.setVisibility(ImageView.VISIBLE);
                break;
            case "patio2":
                patio2 = findViewById(R.id.ppatio2);
                patio2.setVisibility(ImageView.VISIBLE);
                break;
            case "patio3":
                patio3 = findViewById(R.id.ppatio3);
                patio3.setVisibility(ImageView.VISIBLE);
                break;
            case "patio4":
                patio4 = findViewById(R.id.ppatio4);
                patio4.setVisibility(ImageView.VISIBLE);
                break;
            case "patio5":
                patio5 = findViewById(R.id.ppatio5);
                patio5.setVisibility(ImageView.VISIBLE);
                break;
            case "patio6":
                patio6 = findViewById(R.id.ppatio6);
                patio6.setVisibility(ImageView.VISIBLE);
                break;
            case "patio7":
                patio7 = findViewById(R.id.ppatio7);
                patio7.setVisibility(ImageView.VISIBLE);
                break;
            case "patio8":
                patio8 = findViewById(R.id.ppatio8);
                patio8.setVisibility(ImageView.VISIBLE);
                break;
            case "patio9":
                patio9 = findViewById(R.id.ppatio9);
                patio9.setVisibility(ImageView.VISIBLE);
                break;
            case "patio10":
                patio10 = findViewById(R.id.ppatio10);
                patio10.setVisibility(ImageView.VISIBLE);
                break;
            case "patio11":
                patio11 = findViewById(R.id.ppatio11);
                patio11.setVisibility(ImageView.VISIBLE);
                break;
            case "patio12":
                patio12 = findViewById(R.id.ppatio12);
                patio12.setVisibility(ImageView.VISIBLE);
                break;
            case "patio13":
                patio13 = findViewById(R.id.ppatio13);
                patio13.setVisibility(ImageView.VISIBLE);
                break;
            case "patio14":
                patio14 = findViewById(R.id.ppatio14);
                patio14.setVisibility(ImageView.VISIBLE);
                break;
            case "patio15":
                patio15 = findViewById(R.id.ppatio15);
                patio15.setVisibility(ImageView.VISIBLE);
                break;
        }
    }
    public void mostrarArco(String a){
        if (a.equals("aula2baños1")|| a.equals("baños1aula2")){
            a2b1 = findViewById(R.id.a2b1);
            a2b1.setVisibility(ImageView.VISIBLE);
        } else if (a.equals("aula2aula1")||a.equals("aula1aula2")) {
                a1a2 = findViewById(R.id.a1a2);
                a1a2.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("entrada1aula1")||a.equals("aula1entrada1")){
            e1a1 = findViewById(R.id.e1a1);
            e1a1.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula14entrada1")||a.equals("entrada1aula14")) {
            a14e1 = findViewById(R.id.a14e1);
            a14e1.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula14aula13")||a.equals("aula13aula14") ){
            a14a13 = findViewById(R.id.a14a13);
            a14a13.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula13baños4")||a.equals("baños4aula13")) {
            a13b4 = findViewById(R.id.a13b4);
            a13b4.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("baños2aula8")||a.equals("aula8baños2")) {
            b2a8 = findViewById(R.id.b2a8);
            b2a8.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula8aula9")||a.equals("aula9aula8")) {
            a8a9 = findViewById(R.id.a8a9);
            a8a9.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula9entrada3")||a.equals("entrada3aula9")){
            a9e3 = findViewById(R.id.a9e3);
            a9e3.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("entrada3cafeteria")||a.equals("cafeteriaentrada3")){
            e3caf = findViewById(R.id.e3caf);
            e3caf.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("cafeteriabaños3")||a.equals("baños3cafeteria")) {
            cafb3 = findViewById(R.id.cafb3);
            cafb3.setVisibility(ImageView.VISIBLE);
        } else if (a.equals("entrada2patio7")||a.equals("patio7entrada2")) {
            e2p7 = findViewById(R.id.e2p7);
            e2p7.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio7patio8")||a.equals("patio8patio7")) {
            p7p8 = findViewById(R.id.p7p8);
            p7p8.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio8patio9")||a.equals("patio9patio8")) {
            p8p9 = findViewById(R.id.p8p9);
            p8p9.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio9entrada4")||a.equals("entrada4patio9")){
            p9e4 = findViewById(R.id.p9e4);
            p9e4.setVisibility(ImageView.VISIBLE);
        } else if (a.equals("baños1aula3")||a.equals("aula3baños1")) {
            b1a3 = findViewById(R.id.b1a3);
            b1a3.setVisibility(ImageView.VISIBLE);
        } else if (a.equals("aula3aula4")||a.equals("aula4aula3")){
            a3a4= findViewById(R.id.a3a4);
            a3a4.setVisibility(ImageView.VISIBLE);
        } else if (a.equals("aula4aula5")||a.equals("aula5aula4")){
            a4a5= findViewById(R.id.a4a5);
            a4a5.setVisibility(ImageView.VISIBLE);
        } else if(a.equals("aula5entrada2")||a.equals("entrada2aula5")){
            a5e2= findViewById(R.id.a5e2);
            a5e2.setVisibility(ImageView.VISIBLE);
        } else if(a.equals("entrada2capilla")||a.equals("capillaentrada2")){
            e2cap = findViewById(R.id.e2cap);
            e2cap.setVisibility(ImageView.VISIBLE);
        } else if (a.equals("capillaaula6")||a.equals("aula6capilla")){
            capa6= findViewById(R.id.capa6);
            capa6.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula6aula7")||a.equals("aula7aula6")) {
            a6a7 = findViewById(R.id.a6a7);
            a6a7.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula7baños2")||a.equals("baños2aula7")) {
            a7b2 = findViewById(R.id.a7b2);
            a7b2.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("baños4aula12")||a.equals("aula12baños4")) {
            b4a12 = findViewById(R.id.b4a12);
            b4a12.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula12aula11")||a.equals("aula11aula12")) {
            a12a11 = findViewById(R.id.a12a11);
            a12a11.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula11entrada4")||a.equals("entrada4aula11")) {
            a11e4_1 = findViewById(R.id.a11e4_1);
            a11e4_1.setVisibility(ImageView.VISIBLE);
            a11e4_2 = findViewById(R.id.a11e4_2);
            a11e4_2.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("entrada4banco")||a.equals("bancoentrada4")) {
            e4ban = findViewById(R.id.e4ban);
            e4ban.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("bancoaula10")||a.equals("aula10banco")) {
            bana10 = findViewById(R.id.bana10);
            bana10.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("aula10repografia")||a.equals("repografiaaula10")){
            a10rep = findViewById(R.id.a10rep);
            a10rep.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("repografiabaños3")||a.equals("baños3repografia")) {
            repb3 = findViewById(R.id.repb3);
            repb3.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("entrada1patio2")||a.equals("patio2entrada1")) {
            e1p2 = findViewById(R.id.e1p2);
            e1p2.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio2patio6")||a.equals("patio6patio2")) {
            p2p6 = findViewById(R.id.p2p6);
            p2p6.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio6patio8")||a.equals("patio8patio6")) {
            p6p8 = findViewById(R.id.p6p8);
            p6p8.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio8patio10")||a.equals("patio10patio8")) {
            p8p10 = findViewById(R.id.p8p10);
            p8p10.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio10patio14")||a.equals("patio14patio10")) {
            p10p14 = findViewById(R.id.p10p14);
            p10p14.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio14entrada3")||a.equals("entrada3patio14")) {
            p14e3 = findViewById(R.id.p14e3);
            p14e3.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("baños1patio1")||a.equals("patio1baños1")){
            b1p1 = findViewById(R.id.b1p1);
            b1p1.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio1patio4")||a.equals("patio4patio1")) {
            p1p4 = findViewById(R.id.p1p4);
            p1p4.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio4patio6")||a.equals("patio6patio4")) {
            p4p6 = findViewById(R.id.p4p6);
            p4p6.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("baños4patio3")||a.equals("patio3baños4")) {
            b4p3 = findViewById(R.id.b4p3);
            b4p3.setVisibility(ImageView.VISIBLE);
        } else if (a.equals("patio3patio5")||a.equals("patio5patio3")){
            p3p5= findViewById(R.id.p3p5);
            p3p5.setVisibility(ImageView.VISIBLE);
        } else if (a.equals("patio5patio6")||a.equals("patio6patio5")){
            p5p6= findViewById(R.id.p5p6);
            p5p6.setVisibility(ImageView.VISIBLE);
        } else if(a.equals("baños2patio13")||a.equals("patio13baños2")) {
            b2p13 = findViewById(R.id.b2p13);
            b2p13.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio13patio11")||a.equals("patio11patio13")) {
            p13p11 = findViewById(R.id.p13p11);
            p13p11.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("baños3patio15")||a.equals("patio15baños3")) {
            b3p15 = findViewById(R.id.b3p15);
            b3p15.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio15patio12")||a.equals("patio12patio15")) {
            p15p12 = findViewById(R.id.p15p12);
            p15p12.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio11patio10")||a.equals("patio10patio11")) {
            p11p10 = findViewById(R.id.p11p10);
            p11p10.setVisibility(ImageView.VISIBLE);
        }else if(a.equals("patio12patio10")||a.equals("patio10patio12")) {
            p12p10 = findViewById(R.id.p12p10);
            p12p10.setVisibility(ImageView.VISIBLE);
        }
    }
}
