package com.example.TFG_3;



import android.app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;



import java.util.List;


public class Escaner extends Activity {
    protected static final String TAG = "Escaner";
    private final BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private Transmisor tdetectado;
    private Transmisor tantiguo;
    private final Basedatos dbTransmisor = App.dbTransmisor;
    private final Grafo grafo = App.grafo;
    private final Grafo grafo2 = App.grafo2;
    private final String nT1 = Monitorear.nT1;
    private final String nT2 = Monitorear.nT2;
    public List<Transmisor> camino;
    private ImageView img;
    private ImageView entrada1;
    private ImageView aula1 ;
    private ImageView aula2 ;
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
    private ImageView  baños3;
    private ImageView reprografia;
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
    private Button nav;
    private Button otruta;
    private TextView texto;
    private Grafo g;
    private Transmisor torigen;
    private Transmisor tdestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear);
        incicializarImagen();
    }
    @Override
    protected void onResume() {
        super.onResume();
        nav = findViewById(R.id.nav);
        otruta = findViewById(R.id.otruta);
        texto = findViewById(R.id.texto);
        g = grafo;

        torigen = g.buscarTransmisorPorNombre(nT1);
        tantiguo = torigen;
        tdestino = g.buscarTransmisorPorNombre(nT2);
        mostrarCamino(torigen,tdestino,g);
        double p = g.pesototalRuta(camino);

        RangeNotifier rangeNotifier = (beacons, region) -> {
            int d = 100;
            Beacon beaconcer = null;
            for(Beacon beacon : beacons){
                if(beacon.getDistance() < d){
                    d = (int)beacon.getDistance();
                    beaconcer = beacon;
                }
            }
            try {
                tdetectado = g.buscarTransmisorPorId(beaconcer.getId2().toString());
                Log.d(TAG, "Transmisor detectado: " + tdetectado.toString());

                if (!tdetectado.getNombre().equals(tantiguo.getNombre()) && comprobarPunto(camino, tdetectado)) {
                    if (tdetectado.getNombre().equals(tdestino.getNombre())) {
                        ocultarArcos();
                        ocultarPuntos();
                        mostrarPunto(tdetectado.getNombre(), 'a');
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Llegada");
                        builder.setMessage("Ha llegado a su destino");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Aceptar", null);
                        builder.show();
                        texto.setText("Has llegado a tu destino");

                    } else {
                        mostrarCamino(tdetectado, tdestino, g);
                        texto.setText("La distancia al destino es de: " + g.pesototalRuta(camino) + " metros");

                    }
                        tantiguo = tdetectado;
                }
            }catch (Exception e){
                    Log.d(TAG,  e.toString());
            }
        };
        nav.setOnClickListener(v -> {
            beaconManager.addRangeNotifier(rangeNotifier);
            beaconManager.setForegroundScanPeriod(1000);
            beaconManager.setForegroundBetweenScanPeriod(500);
            beaconManager.startRangingBeacons(App.escanRegion);
            nav.setVisibility(Button.INVISIBLE);
            otruta.setVisibility(Button.INVISIBLE);
            texto.setText("La distancia al destino es de: "+p+" metros");
        });

        otruta.setOnClickListener(v -> {
            if(g.equals(grafo)){
                g = grafo2;
            }else if(g.equals(grafo2)){
                g = grafo;
            }
            torigen = g.buscarTransmisorPorNombre(nT1);
            tdestino = g.buscarTransmisorPorNombre(nT2);
            mostrarCamino(torigen,tdestino,g);
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        //beaconManager.stopRangingBeacons(App.escanRegion);
        //beaconManager.removeAllRangeNotifiers();
    }
    public void menuClick() {
        beaconManager.stopRangingBeacons(App.escanRegion);
        beaconManager.removeAllRangeNotifiers();
        Intent myIntent = new Intent(this, Menu.class);
        this.startActivity(myIntent);
    }
    public void mostrarCamino(Transmisor t1,Transmisor t2,Grafo g){
        ocultarArcos();
        ocultarPuntos();
        camino = g.obtenerCaminoMasRapido(t1,t2);
        if(camino.size() > 0) {
            for(Transmisor t : camino){
                mostrarPunto(t.getNombre(),'a');
            }
        }
        mostrarPunto(camino.get(0).getNombre(),'u');
        mostrarPunto(camino.get(camino.size()-1).getNombre(),'f');

        List<String> arcos = g.obtenerArcos(t1,t2);
        if(arcos.size() > 0) {
            for(String a : arcos){
                mostrarArco(a);
            }
        }
    }
    public boolean comprobarPunto(List<Transmisor> camino, Transmisor t){
        boolean b = false;
        if(camino.contains(t)){
            if(t.equals(camino.get(0))||t.equals(camino.get(1))){
                b = true;
            }else{
                b = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Advertencia");
                builder.setMessage("Vaya, parece que te has saltado algun punto del camino. Pero no te preocupes, vas por buen camino");
                builder.setCancelable(true);
                builder.setPositiveButton("Aceptar", null);
                builder.show();
            }
        }else{
            boolean c = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Advertencia");
            builder.setMessage("Vaya, parece que te has equivocado de camino. Vuelve atras y sigue el camino indicado");
            builder.setCancelable(true);
            builder.setPositiveButton("Punto Anterior", null);
            builder.setNegativeButton("Recalcular ruta", (dialog, which) -> {
                mostrarCamino(t,tdestino,g);
                texto.setText("La distancia al destino es de: " + g.pesototalRuta(camino) + " metros");

            });
            builder.show();
            tantiguo =t;
        }
        return b;
    }
    public void mostrarPunto(String punto,char c){
        switch (punto){
            case "entrada1":
                entrada1.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    entrada1.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    entrada1.setColorFilter(Color.RED);
                }
                break;
            case "aula1":
                aula1.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula1.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula1.setColorFilter(Color.RED);
                }
                break;
            case "aula2":
                aula2.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula2.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula2.setColorFilter(Color.RED);
                }
                break;
            case"baños1":
                baños1.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    baños1.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    baños1.setColorFilter(Color.RED);
                }
                break;
            case "aula3":
                aula3.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula3.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula3.setColorFilter(Color.RED);
                }
                break;
            case "aula4":
                aula4.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula4.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula4.setColorFilter(Color.RED);
                }
                break;
            case "aula5":
                aula5.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula5.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula5.setColorFilter(Color.RED);
                }
                break;
            case "entrada2":
                entrada2.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    entrada2.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    entrada2.setColorFilter(Color.RED);
                }
                break;
            case "capilla":
                capilla.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    capilla.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    capilla.setColorFilter(Color.RED);
                }
                break;
            case "aula6":
                aula6.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula6.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula6.setColorFilter(Color.RED);
                }
                break;
            case "aula7":
                aula7.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula7.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula7.setColorFilter(Color.RED);
                }
                break;
            case "baños2":
                baños2.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    baños2.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    baños2.setColorFilter(Color.RED);
                }
                break;
            case "aula8":
                aula8.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula8.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula8.setColorFilter(Color.RED);
                }
                break;
            case "aula9":
                aula9.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula9.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula9.setColorFilter(Color.RED);
                }
                break;
            case "entrada3":
                entrada3.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    entrada3.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    entrada3.setColorFilter(Color.RED);
                }
                break;
            case "cafeteria":
                cafeteria.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    cafeteria.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    cafeteria.setColorFilter(Color.RED);
                }
                break;
            case "baños3":
                baños3.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    baños3.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    baños3.setColorFilter(Color.RED);
                }
                break;
            case "reprografia":
                reprografia.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    reprografia.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    reprografia.setColorFilter(Color.RED);
                }
                break;
            case "aula10":
                aula10.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula10.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula10.setColorFilter(Color.RED);
                }
                break;
            case "banco":
                banco.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    banco.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    banco.setColorFilter(Color.RED);
                }
                break;
            case "entrada4":
                entrada4.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    entrada4.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    entrada4.setColorFilter(Color.RED);
                }
                break;
            case "aula11":
                aula11.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula11.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula11.setColorFilter(Color.RED);
                }
                break;
            case "aula12":
                aula12.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula12.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula12.setColorFilter(Color.RED);
                }
                break;
            case "baños4":
                baños4.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    baños4.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    baños4.setColorFilter(Color.RED);
                }
                break;
            case "aula13":
                aula13.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula13.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula13.setColorFilter(Color.RED);
                }
                break;
            case "aula14":
                aula14.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    aula14.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    aula14.setColorFilter(Color.RED);
                }
                break;
            case "patio1":
                patio1.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio1.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio1.setColorFilter(Color.RED);
                }
                break;
            case "patio2":
                patio2.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio2.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio2.setColorFilter(Color.RED);
                }
                break;
            case "patio3":
                patio3.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio3.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio3.setColorFilter(Color.RED);
                }
                break;
            case "patio4":
                patio4.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio4.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio4.setColorFilter(Color.RED);
                }
                break;
            case "patio5":
                patio5.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio5.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio5.setColorFilter(Color.RED);
                }
                break;
            case "patio6":
                patio6.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio6.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio6.setColorFilter(Color.RED);
                }
                break;
            case "patio7":
                patio7.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio7.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio7.setColorFilter(Color.RED);
                }
                break;
            case "patio8":
                patio8.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio8.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio8.setColorFilter(Color.RED);
                }
                break;
            case "patio9":
                patio9.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio9.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio9.setColorFilter(Color.RED);
                }
                break;
            case "patio10":
                patio10.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio10.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio10.setColorFilter(Color.RED);
                }
                break;
            case "patio11":
                patio11.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio11.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio11.setColorFilter(Color.RED);
                }
                break;
            case "patio12":
                patio12.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio12.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio12.setColorFilter(Color.RED);
                }
                break;
            case "patio13":
                patio13.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio13.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio13.setColorFilter(Color.RED);
                }
                break;
            case "patio14":
                patio14.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio14.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio14.setColorFilter(Color.RED);
                }
                break;
            case "patio15":
                patio15.setVisibility(ImageView.VISIBLE);
                if (c == 'u'){
                    patio15.setColorFilter(Color.YELLOW);
                }else if(c == 'f'){
                    patio15.setColorFilter(Color.RED);
                }
                break;
        }
    }
    public void ocultarPuntos(){
        entrada1.setVisibility(ImageView.INVISIBLE);
        aula1.setVisibility(ImageView.INVISIBLE);
        aula2.setVisibility(ImageView.INVISIBLE);
        baños1.setVisibility(ImageView.INVISIBLE);
        aula3.setVisibility(ImageView.INVISIBLE);
        aula4.setVisibility(ImageView.INVISIBLE);
        aula5.setVisibility(ImageView.INVISIBLE);
        entrada2.setVisibility(ImageView.INVISIBLE);
        capilla.setVisibility(ImageView.INVISIBLE);
        aula6.setVisibility(ImageView.INVISIBLE);
        aula7.setVisibility(ImageView.INVISIBLE);
        baños2.setVisibility(ImageView.INVISIBLE);
        aula8.setVisibility(ImageView.INVISIBLE);
        aula9.setVisibility(ImageView.INVISIBLE);
        entrada3.setVisibility(ImageView.INVISIBLE);
        cafeteria.setVisibility(ImageView.INVISIBLE);
        baños3.setVisibility(ImageView.INVISIBLE);
        reprografia.setVisibility(ImageView.INVISIBLE);
        aula10.setVisibility(ImageView.INVISIBLE);
        banco.setVisibility(ImageView.INVISIBLE);
        entrada4.setVisibility(ImageView.INVISIBLE);
        aula11.setVisibility(ImageView.INVISIBLE);
        aula12.setVisibility(ImageView.INVISIBLE);
        baños4.setVisibility(ImageView.INVISIBLE);
        aula13.setVisibility(ImageView.INVISIBLE);
        aula14.setVisibility(ImageView.INVISIBLE);
        patio1.setVisibility(ImageView.INVISIBLE);
        patio2.setVisibility(ImageView.INVISIBLE);
        patio3.setVisibility(ImageView.INVISIBLE);
        patio4.setVisibility(ImageView.INVISIBLE);
        patio5.setVisibility(ImageView.INVISIBLE);
        patio6.setVisibility(ImageView.INVISIBLE);
        patio7.setVisibility(ImageView.INVISIBLE);
        patio8.setVisibility(ImageView.INVISIBLE);
        patio9.setVisibility(ImageView.INVISIBLE);
        patio10.setVisibility(ImageView.INVISIBLE);
        patio11.setVisibility(ImageView.INVISIBLE);
        patio12.setVisibility(ImageView.INVISIBLE);
        patio13.setVisibility(ImageView.INVISIBLE);
        patio14.setVisibility(ImageView.INVISIBLE);
        patio15.setVisibility(ImageView.INVISIBLE);
    }
    public void mostrarArco(String a){
        switch (a) {
            case "aula2baños1":
            case "baños1aula2":
                a2b1.setVisibility(ImageView.VISIBLE);
                break;
            case "aula2aula1":
            case "aula1aula2":
                a1a2.setVisibility(ImageView.VISIBLE);
                break;
            case "entrada1aula1":
            case "aula1entrada1":
                e1a1.setVisibility(ImageView.VISIBLE);
                break;
            case "aula14entrada1":
            case "entrada1aula14":
                a14e1.setVisibility(ImageView.VISIBLE);
                break;
            case "aula14aula13":
            case "aula13aula14":
                a14a13.setVisibility(ImageView.VISIBLE);
                break;
            case "aula13baños4":
            case "baños4aula13":
                a13b4.setVisibility(ImageView.VISIBLE);
                break;
            case "baños2aula8":
            case "aula8baños2":
                b2a8.setVisibility(ImageView.VISIBLE);
                break;
            case "aula8aula9":
            case "aula9aula8":
                a8a9.setVisibility(ImageView.VISIBLE);
                break;
            case "aula9entrada3":
            case "entrada3aula9":
                a9e3.setVisibility(ImageView.VISIBLE);
                break;
            case "entrada3cafeteria":
            case "cafeteriaentrada3":
                e3caf.setVisibility(ImageView.VISIBLE);
                break;
            case "cafeteriabaños3":
            case "baños3cafeteria":
                cafb3.setVisibility(ImageView.VISIBLE);
                break;
            case "entrada2patio7":
            case "patio7entrada2":
                e2p7.setVisibility(ImageView.VISIBLE);
                break;
            case "patio7patio8":
            case "patio8patio7":
                p7p8.setVisibility(ImageView.VISIBLE);
                break;
            case "patio8patio9":
            case "patio9patio8":
                p8p9.setVisibility(ImageView.VISIBLE);
                break;
            case "patio9entrada4":
            case "entrada4patio9":
                p9e4.setVisibility(ImageView.VISIBLE);
                break;
            case "baños1aula3":
            case "aula3baños1":
                b1a3.setVisibility(ImageView.VISIBLE);
                break;
            case "aula3aula4":
            case "aula4aula3":
                a3a4.setVisibility(ImageView.VISIBLE);
                break;
            case "aula4aula5":
            case "aula5aula4":
                a4a5.setVisibility(ImageView.VISIBLE);
                break;
            case "aula5entrada2":
            case "entrada2aula5":
                a5e2.setVisibility(ImageView.VISIBLE);
                break;
            case "entrada2capilla":
            case "capillaentrada2":
                e2cap.setVisibility(ImageView.VISIBLE);
                break;
            case "capillaaula6":
            case "aula6capilla":
                capa6.setVisibility(ImageView.VISIBLE);
                break;
            case "aula6aula7":
            case "aula7aula6":
                a6a7.setVisibility(ImageView.VISIBLE);
                break;
            case "aula7baños2":
            case "baños2aula7":
                a7b2.setVisibility(ImageView.VISIBLE);
                break;
            case "baños4aula12":
            case "aula12baños4":
                b4a12.setVisibility(ImageView.VISIBLE);
                break;
            case "aula12aula11":
            case "aula11aula12":
                a12a11.setVisibility(ImageView.VISIBLE);
                break;
            case "aula11entrada4":
            case "entrada4aula11":
                a11e4_1.setVisibility(ImageView.VISIBLE);
                a11e4_2.setVisibility(ImageView.VISIBLE);
                break;
            case "entrada4banco":
            case "bancoentrada4":
                e4ban.setVisibility(ImageView.VISIBLE);
                break;
            case "bancoaula10":
            case "aula10banco":
                bana10.setVisibility(ImageView.VISIBLE);
                break;
            case "aula10reprografia":
            case "reprografiaaula10":
                a10rep.setVisibility(ImageView.VISIBLE);
                break;
            case "reprografiabaños3":
            case "baños3reprografia":
                repb3.setVisibility(ImageView.VISIBLE);
                break;
            case "entrada1patio2":
            case "patio2entrada1":
                e1p2.setVisibility(ImageView.VISIBLE);
                break;
            case "patio2patio6":
            case "patio6patio2":
                p2p6.setVisibility(ImageView.VISIBLE);
                break;
            case "patio6patio8":
            case "patio8patio6":
                p6p8.setVisibility(ImageView.VISIBLE);
                break;
            case "patio8patio10":
            case "patio10patio8":
                p8p10.setVisibility(ImageView.VISIBLE);
                break;
            case "patio10patio14":
            case "patio14patio10":
                p10p14.setVisibility(ImageView.VISIBLE);
                break;
            case "patio14entrada3":
            case "entrada3patio14":
                p14e3.setVisibility(ImageView.VISIBLE);
                break;
            case "baños1patio1":
            case "patio1baños1":
                b1p1.setVisibility(ImageView.VISIBLE);
                break;
            case "patio1patio4":
            case "patio4patio1":
                p1p4.setVisibility(ImageView.VISIBLE);
                break;
            case "patio4patio6":
            case "patio6patio4":
                p4p6.setVisibility(ImageView.VISIBLE);
                break;
            case "baños4patio3":
            case "patio3baños4":
                b4p3.setVisibility(ImageView.VISIBLE);
                break;
            case "patio3patio5":
            case "patio5patio3":
                p3p5.setVisibility(ImageView.VISIBLE);
                break;
            case "patio5patio6":
            case "patio6patio5":
                p5p6.setVisibility(ImageView.VISIBLE);
                break;
            case "baños2patio13":
            case "patio13baños2":
                b2p13.setVisibility(ImageView.VISIBLE);
                break;
            case "patio13patio11":
            case "patio11patio13":
                p13p11.setVisibility(ImageView.VISIBLE);
                break;
            case "baños3patio15":
            case "patio15baños3":
                b3p15.setVisibility(ImageView.VISIBLE);
                break;
            case "patio15patio12":
            case "patio12patio15":
                p15p12.setVisibility(ImageView.VISIBLE);
                break;
            case "patio11patio10":
            case "patio10patio11":
                p11p10.setVisibility(ImageView.VISIBLE);
                break;
            case "patio12patio10":
            case "patio10patio12":
                p12p10.setVisibility(ImageView.VISIBLE);
                break;
        }
    }
    public void ocultarArcos(){
            a2b1.setVisibility(ImageView.INVISIBLE);
            a1a2.setVisibility(ImageView.INVISIBLE);
            e1a1.setVisibility(ImageView.INVISIBLE);
            a14e1.setVisibility(ImageView.INVISIBLE);
            a14a13.setVisibility(ImageView.INVISIBLE);
            a13b4.setVisibility(ImageView.INVISIBLE);
            b2a8.setVisibility(ImageView.INVISIBLE);
            a8a9.setVisibility(ImageView.INVISIBLE);
            a9e3.setVisibility(ImageView.INVISIBLE);
            e3caf.setVisibility(ImageView.INVISIBLE);
            cafb3.setVisibility(ImageView.INVISIBLE);
            e2p7.setVisibility(ImageView.INVISIBLE);
            p7p8.setVisibility(ImageView.INVISIBLE);
            p8p9.setVisibility(ImageView.INVISIBLE);
            p9e4.setVisibility(ImageView.INVISIBLE);
            b1a3.setVisibility(ImageView.INVISIBLE);
            a3a4.setVisibility(ImageView.INVISIBLE);
            a4a5.setVisibility(ImageView.INVISIBLE);
            a5e2.setVisibility(ImageView.INVISIBLE);
            e2cap.setVisibility(ImageView.INVISIBLE);
            capa6.setVisibility(ImageView.INVISIBLE);
            a6a7.setVisibility(ImageView.INVISIBLE);
            a7b2.setVisibility(ImageView.INVISIBLE);
            b4a12.setVisibility(ImageView.INVISIBLE);
            a12a11.setVisibility(ImageView.INVISIBLE);
            a11e4_1.setVisibility(ImageView.INVISIBLE);
            a11e4_2.setVisibility(ImageView.INVISIBLE);
            e4ban.setVisibility(ImageView.INVISIBLE);
            bana10.setVisibility(ImageView.INVISIBLE);
            a10rep.setVisibility(ImageView.INVISIBLE);
            repb3.setVisibility(ImageView.INVISIBLE);
            e1p2.setVisibility(ImageView.INVISIBLE);
            p2p6.setVisibility(ImageView.INVISIBLE);
            p6p8.setVisibility(ImageView.INVISIBLE);
            p8p10.setVisibility(ImageView.INVISIBLE);
            p10p14.setVisibility(ImageView.INVISIBLE);
            p14e3.setVisibility(ImageView.INVISIBLE);
            b1p1.setVisibility(ImageView.INVISIBLE);
            p1p4.setVisibility(ImageView.INVISIBLE);
            p4p6.setVisibility(ImageView.INVISIBLE);
            b4p3.setVisibility(ImageView.INVISIBLE);
            p3p5.setVisibility(ImageView.INVISIBLE);
            p5p6.setVisibility(ImageView.INVISIBLE);
            b2p13.setVisibility(ImageView.INVISIBLE);
            p13p11.setVisibility(ImageView.INVISIBLE);
            b3p15.setVisibility(ImageView.INVISIBLE);
            p15p12.setVisibility(ImageView.INVISIBLE);
            p11p10.setVisibility(ImageView.INVISIBLE);
            p12p10.setVisibility(ImageView.INVISIBLE);

    }

    public void incicializarImagen(){
        img = findViewById(R.id.imagen);
        entrada1 = findViewById(R.id.pent1);
        aula1 = findViewById(R.id.paula1);
        aula2 = findViewById(R.id.paula2);
        baños1 = findViewById(R.id.pbaños1);
        aula3 = findViewById(R.id.paula3);
        aula4 = findViewById(R.id.paula4);
        aula5 = findViewById(R.id.paula5);
        entrada2 = findViewById(R.id.pent2);
        capilla = findViewById(R.id.pcap);
        aula6 = findViewById(R.id.paula6);
        aula7 = findViewById(R.id.paula7);
        baños2 = findViewById(R.id.pbaños2);
        aula8 = findViewById(R.id.paula8);
        aula9 = findViewById(R.id.paula9);
        entrada3 = findViewById(R.id.pent3);
        cafeteria = findViewById(R.id.pcafe);
        baños3 = findViewById(R.id.pbaños3);
        reprografia = findViewById(R.id.prepo);
        aula10 = findViewById(R.id.paula10);
        banco = findViewById(R.id.pbanco);
        entrada4 = findViewById(R.id.pent4);
        aula11 = findViewById(R.id.paula11);
        aula12 = findViewById(R.id.paula12);
        baños4 = findViewById(R.id.pbaños4);
        aula13 = findViewById(R.id.paula13);
        aula14 = findViewById(R.id.paula14);
        patio1 = findViewById(R.id.ppatio1);
        patio2 = findViewById(R.id.ppatio2);
        patio3 = findViewById(R.id.ppatio3);
        patio4 = findViewById(R.id.ppatio4);
        patio5 = findViewById(R.id.ppatio5);
        patio6 = findViewById(R.id.ppatio6);
        patio7 = findViewById(R.id.ppatio7);
        patio8 = findViewById(R.id.ppatio8);
        patio9 = findViewById(R.id.ppatio9);
        patio10 = findViewById(R.id.ppatio10);
        patio11 = findViewById(R.id.ppatio11);
        patio12 = findViewById(R.id.ppatio12);
        patio13 = findViewById(R.id.ppatio13);
        patio14 = findViewById(R.id.ppatio14);
        patio15 = findViewById(R.id.ppatio15);
        a2b1 = findViewById(R.id.a2b1);
        a1a2 = findViewById(R.id.a1a2);
        e1a1 = findViewById(R.id.e1a1);
        a14e1 = findViewById(R.id.a14e1);
        a14a13 = findViewById(R.id.a14a13);
        a13b4 = findViewById(R.id.a13b4);
        b2a8 = findViewById(R.id.b2a8);
        a8a9 = findViewById(R.id.a8a9);
        a9e3 = findViewById(R.id.a9e3);
        e3caf = findViewById(R.id.e3caf);
        cafb3 = findViewById(R.id.cafb3);
        e2p7 = findViewById(R.id.e2p7);
        p7p8 = findViewById(R.id.p7p8);
        p8p9 = findViewById(R.id.p8p9);
        p9e4 = findViewById(R.id.p9e4);
        b1a3 = findViewById(R.id.b1a3);
        a3a4= findViewById(R.id.a3a4);
        a4a5= findViewById(R.id.a4a5);
        a5e2= findViewById(R.id.a5e2);
        e2cap = findViewById(R.id.e2cap);
        capa6= findViewById(R.id.capa6);
        a6a7 = findViewById(R.id.a6a7);
        a7b2 = findViewById(R.id.a7b2);
        b4a12 = findViewById(R.id.b4a12);
        a12a11 = findViewById(R.id.a12a11);
        a11e4_1 = findViewById(R.id.a11e4_1);
        a11e4_2 = findViewById(R.id.a11e4_2);
        e4ban = findViewById(R.id.e4ban);
        bana10 = findViewById(R.id.bana10);
        a10rep = findViewById(R.id.a10rep);
        repb3 = findViewById(R.id.repb3);
        e1p2 = findViewById(R.id.e1p2);
        p2p6 = findViewById(R.id.p2p6);
        p6p8 = findViewById(R.id.p6p8);
        p8p10 = findViewById(R.id.p8p10);
        p10p14 = findViewById(R.id.p10p14);
        p14e3 = findViewById(R.id.p14e3);
        b1p1 = findViewById(R.id.b1p1);
        p1p4 = findViewById(R.id.p1p4);
        p4p6 = findViewById(R.id.p4p6);
        b4p3 = findViewById(R.id.b4p3);
        p3p5= findViewById(R.id.p3p5);
        p5p6= findViewById(R.id.p5p6);
        b2p13 = findViewById(R.id.b2p13);
        p13p11 = findViewById(R.id.p13p11);
        b3p15 = findViewById(R.id.b3p15);
        p15p12 = findViewById(R.id.p15p12);
        p11p10 = findViewById(R.id.p11p10);
        p12p10 = findViewById(R.id.p12p10);
    }

    private void mostrarPorPantalla(final String line) {
        runOnUiThread(() -> {
            EditText editText = Escaner.this.findViewById(R.id.rangingText);
            editText.setText("");
            editText.append(line+"\n");
        });
    }
}
