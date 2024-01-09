package com.example.TFG_3;



import android.app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;


import java.util.ArrayList;
import java.util.List;


public class Escaner extends Activity {
    protected static final String TAG = "Escaner";
    private final BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private Transmisor tdetectado;
    private Transmisor tantiguo;
    private TextView tsup;
    private final Basedatos dbTransmisor = App.dbTransmisor;
    private final Grafo grafo = App.grafo;
    private final Grafo grafo2 = App.grafo2;
    private final boolean exterior = Monitorear.exterior;
    private final String nT1 = Monitorear.nTransmisor1;
    private final String nT2 = Monitorear.nTransmisor2;
    private int piso = 0;
    private ArrayList<ImageView> nodos = new ArrayList<>();
    private ArrayList<ImageView> arcos = new ArrayList<>();
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
    private ImageView escalera1;
    private ImageView escalera2;
    private ImageView escalera3;
    private ImageView escalera4;
    private ImageView escalera5;
    private ImageView escalera6;
    private ImageView escalera7;
    private ImageView escalera8;
    private ImageView escalera9;
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
    private ImageView e1esc1;
    private ImageView b1esc2;
    private ImageView e2esc3;
    private ImageView b2esc4;
    private ImageView e3esc5;
    private ImageView b3esc6;
    private ImageView banesc7;
    private ImageView e4esc7;
    private ImageView e4esc8;
    private ImageView esc8a11;
    private ImageView b4esc9;


    private ImageView vestibulo101;
    private ImageView aula101 ;
    private ImageView aula102 ;
    private ImageView baños101;
    private ImageView aula103;
    private ImageView aula104;
    private ImageView aula105;
    private ImageView vestibulo102;
    private ImageView aula106;
    private ImageView aula107;
    private ImageView baños102;
    private ImageView aula108;
    private ImageView aula109;
    private ImageView vestibulo103;
    private ImageView  baños103;
    private ImageView aula110;

    private ImageView aula111;
    private ImageView aula112;
    private ImageView baños104;
    private ImageView aula113;
    private ImageView aula114;
    private ImageView aula115;
    private ImageView aula116;
    private ImageView aula117;
    private ImageView aula118;
    private ImageView aula119;
    private ImageView escalera101;
    private ImageView escalera102;
    private ImageView escalera103;
    private ImageView escalera104;
    private ImageView escalera105;
    private ImageView escalera106;
    private ImageView escalera107;
    private ImageView escalera108;
    private ImageView escalera109;

    private ImageView v101a101;
    private ImageView a101a102;
    private ImageView a102b101;
    private ImageView b101a103;
    private ImageView a103a104;
    private ImageView a104a105;
    private ImageView a105v102;
    private ImageView v102a106;
    private ImageView a106a107;
    private ImageView a108b102;
    private ImageView b102a109;
    private ImageView a109a110;
    private ImageView a110v103;
    private ImageView v103a111;
    private ImageView a111a112;
    private ImageView a112b103;
    private ImageView b103a113;
    private ImageView a113a114;
    private ImageView a114a115;
    private ImageView a115p1;
    private ImageView p1a116_1;
    private ImageView p1a116_2;
    private ImageView a116a117;
    private ImageView a117b104;
    private ImageView b104a118;
    private ImageView a118a119;
    private ImageView a119v101;
    private ImageView v101esc101;
    private ImageView b101esc102;
    private ImageView v102esc103;
    private ImageView b102esc104;
    private ImageView v103esc105;
    private ImageView b103esc106;
    private ImageView a115esc107;
    private ImageView esc107p1;
    private ImageView p1esc108;
    private ImageView esc108a116;
    private ImageView b104esc109;

    private ImageView vestibulo201;
    private ImageView aula201 ;
    private ImageView aula202 ;
    private ImageView baños201;
    private ImageView aula203;
    private ImageView aula204;
    private ImageView aula205;
    private ImageView vestibulo202;
    private ImageView aula206;
    private ImageView aula207;
    private ImageView baños202;
    private ImageView aula208;
    private ImageView aula209;
    private ImageView vestibulo203;
    private ImageView  baños203;
    private ImageView aula210;

    private ImageView aula211;
    private ImageView aula212;
    private ImageView baños204;
    private ImageView aula213;
    private ImageView aula214;
    private ImageView aula215;
    private ImageView aula216;
    private ImageView aula217;
    private ImageView aula218;
    private ImageView aula219;
    private ImageView escalera201;
    private ImageView escalera202;
    private ImageView escalera203;
    private ImageView escalera204;
    private ImageView escalera205;
    private ImageView escalera206;
    private ImageView escalera207;
    private ImageView escalera208;
    private ImageView escalera209;

    private ImageView v201a201;
    private ImageView a201a202;
    private ImageView a202b201;
    private ImageView b201a203;
    private ImageView a203a204;
    private ImageView a204a205;
    private ImageView a205v202;
    private ImageView v202a206;
    private ImageView a206a207;
    private ImageView a208b202;
    private ImageView b202a209;
    private ImageView a209a210;
    private ImageView a210v203;
    private ImageView v203a211;
    private ImageView a211a212;
    private ImageView a212b203;
    private ImageView b203a213;
    private ImageView a213a214;
    private ImageView a214a215;
    private ImageView a215p2;
    private ImageView a216p2_1;
    private ImageView a216p2_2;
    private ImageView a216a217;
    private ImageView a217b204;
    private ImageView b204a218;
    private ImageView a218a219;
    private ImageView a219v201;
    private ImageView v201esc201;
    private ImageView b201esc202;
    private ImageView v202esc203;
    private ImageView b202esc204;
    private ImageView v203esc205;
    private ImageView b203esc206;
    private ImageView a215esc207;
    private ImageView esc207p2;
    private ImageView p2esc208;
    private ImageView esc208a216;
    private ImageView b204esc209;
    private TextView texto;
    private Grafo g;
    private Transmisor torigen;
    private Transmisor tdestino;
    private RelativeLayout layoutp0;
    private RelativeLayout layoutp1;
    private RelativeLayout layoutp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear);
        layoutp0 = findViewById(R.id.layoutp0);
        layoutp1 = findViewById(R.id.layoutp1);
        layoutp2 = findViewById(R.id.layoutp2);
        incicializarImagen();
        interfazPiso(nT1);
    }
    @Override
    protected void onResume() {
        super.onResume();
        tsup = findViewById(R.id.tsup);
        texto = findViewById(R.id.texto);
        if(exterior){
            g = grafo;
        }else{
            g = grafo2;
        };
        ocultarArcos();
        ocultarPuntos();
        torigen = g.buscarTransmisorPorNombre(nT1);
        tantiguo = torigen;
        tdestino = g.buscarTransmisorPorNombre(nT2);
        mostrarCamino(torigen,tdestino,g);
        double p = g.pesototalRuta(camino);
        texto.setText("La distancia al destino es de: " + p + " metros");


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
                        if(tdetectado.getNombre().contains("escalera")) {
                            interfazPiso(tdetectado.getNombre());
                            mostrarCamino(tdetectado, tdestino, g);
                            texto.setText("La distancia al destino es de: " + g.pesototalRuta(camino) + " metros\n Sube las escaleras");
                        }else{
                            mostrarCamino(torigen, tdestino, g);
                            texto.setText("La distancia al destino es de: " + g.pesototalRuta(camino) + " metros");
                        }
                    }
                        tantiguo = tdetectado;
                }
            }catch (Exception e){
                    Log.d(TAG,  e.toString());
            }
        };

        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.setForegroundScanPeriod(1000);
        beaconManager.setForegroundBetweenScanPeriod(500);
        beaconManager.startRangingBeacons(App.escanRegion);
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
        Log.d(TAG, "Mostrando punto: " + punto);
        for(ImageView i : nodos){
            Log.d(TAG, "Punto: " + i.getContentDescription());
            if(i.getContentDescription().equals(punto)){
                i.setVisibility(View.VISIBLE);
                if (c == 'u'){
                    i.setColorFilter(Color.YELLOW);
                    break;
                }else if(c == 'f'){
                    i.setColorFilter(Color.RED);
                    break;
                }else if(c == 'a'){
                    i.setColorFilter(Color.GREEN);
                    break;
                }
            }
        }
    }

    public void ocultarPuntos(){
        for (ImageView i : nodos){
            i.setVisibility(ImageView.INVISIBLE);

        }
    }
    public void mostrarArco(String a){
        Log.d(TAG, "Mostrando arco: " + a);
        for(ImageView i : arcos){
            Log.d(TAG, "Arco: " + i.getContentDescription());
            if(i.getContentDescription().toString().contains(a)){
                i.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    public void ocultarArcos(){
        for (ImageView i : arcos){
            i.setVisibility(ImageView.INVISIBLE);
        }

    }

    public void incicializarImagen(){
        img = findViewById(R.id.imagen);


        //Definimos los puntos del piso 0

        entrada1 = findViewById(R.id.pent1);
        nodos.add(entrada1);
        aula1 = findViewById(R.id.paula1);
        nodos.add(aula1);
        aula2 = findViewById(R.id.paula2);
        nodos.add(aula2);
        baños1 = findViewById(R.id.pbaños1);
        nodos.add(baños1);
        aula3 = findViewById(R.id.paula3);
        nodos.add(aula3);
        aula4 = findViewById(R.id.paula4);
        nodos.add(aula4);
        aula5 = findViewById(R.id.paula5);
        nodos.add(aula5);
        entrada2 = findViewById(R.id.pent2);
        nodos.add(entrada2);
        capilla = findViewById(R.id.pcap);
        nodos.add(capilla);
        aula6 = findViewById(R.id.paula6);
        nodos.add(aula6);
        aula7 = findViewById(R.id.paula7);
        nodos.add(aula7);
        baños2 = findViewById(R.id.pbaños2);
        nodos.add(baños2);
        aula8 = findViewById(R.id.paula8);
        nodos.add(aula8);
        aula9 = findViewById(R.id.paula9);
        nodos.add(aula9);
        entrada3 = findViewById(R.id.pent3);
        nodos.add(entrada3);
        cafeteria = findViewById(R.id.pcafe);
        nodos.add(cafeteria);
        baños3 = findViewById(R.id.pbaños3);
        nodos.add(baños3);
        reprografia = findViewById(R.id.prepo);
        nodos.add(reprografia);
        aula10 = findViewById(R.id.paula10);
        nodos.add(aula10);
        banco = findViewById(R.id.pbanco);
        nodos.add(banco);
        entrada4 = findViewById(R.id.pent4);
        nodos.add(entrada4);
        aula11 = findViewById(R.id.paula11);
        nodos.add(aula11);
        aula12 = findViewById(R.id.paula12);
        nodos.add(aula12);
        baños4 = findViewById(R.id.pbaños4);
        nodos.add(baños4);
        aula13 = findViewById(R.id.paula13);
        nodos.add(aula13);
        aula14 = findViewById(R.id.paula14);
        nodos.add(aula14);
        patio1 = findViewById(R.id.ppatio1);
        nodos.add(patio1);
        patio2 = findViewById(R.id.ppatio2);
        nodos.add(patio2);
        patio3 = findViewById(R.id.ppatio3);
        nodos.add(patio3);
        patio4 = findViewById(R.id.ppatio4);
        nodos.add(patio4);
        patio5 = findViewById(R.id.ppatio5);
        nodos.add(patio5);
        patio6 = findViewById(R.id.ppatio6);
        nodos.add(patio6);
        patio7 = findViewById(R.id.ppatio7);
        nodos.add(patio7);
        patio8 = findViewById(R.id.ppatio8);
        nodos.add(patio8);
        patio9 = findViewById(R.id.ppatio9);
        nodos.add(patio9);
        patio10 = findViewById(R.id.ppatio10);
        nodos.add(patio10);
        patio11 = findViewById(R.id.ppatio11);
        nodos.add(patio11);
        patio12 = findViewById(R.id.ppatio12);
        nodos.add(patio12);
        patio13 = findViewById(R.id.ppatio13);
        nodos.add(patio13);
        patio14 = findViewById(R.id.ppatio14);
        nodos.add(patio14);
        patio15 = findViewById(R.id.ppatio15);
        nodos.add(patio15);
        escalera1 = findViewById(R.id.pesc1);
        nodos.add(escalera1);
        escalera2 = findViewById(R.id.pesc2);
        nodos.add(escalera2);
        escalera3 = findViewById(R.id.pesc3);
        nodos.add(escalera3);
        escalera4 = findViewById(R.id.pesc4);
        nodos.add(escalera4);
        escalera5 = findViewById(R.id.pesc5);
        nodos.add(escalera5);
        escalera6 = findViewById(R.id.pesc6);
        nodos.add(escalera6);
        escalera7 = findViewById(R.id.pesc7);
        nodos.add(escalera7);
        escalera8 = findViewById(R.id.pesc8);
        nodos.add(escalera8);
        escalera9 = findViewById(R.id.pesc9);
        nodos.add(escalera9);
        a2b1 = findViewById(R.id.a2b1);
        arcos.add(a2b1);
        a1a2 = findViewById(R.id.a1a2);
        arcos.add(a1a2);
        e1a1 = findViewById(R.id.e1a1);
        arcos.add(e1a1);
        a14e1 = findViewById(R.id.a14e1);
        arcos.add(a14e1);
        a14a13 = findViewById(R.id.a14a13);
        arcos.add(a14a13);
        a13b4 = findViewById(R.id.a13b4);
        arcos.add(a13b4);
        b2a8 = findViewById(R.id.b2a8);
        arcos.add(b2a8);
        a8a9 = findViewById(R.id.a8a9);
        arcos.add(a8a9);
        a9e3 = findViewById(R.id.a9e3);
        arcos.add(a9e3);
        e3caf = findViewById(R.id.e3caf);
        arcos.add(e3caf);
        cafb3 = findViewById(R.id.cafb3);
        arcos.add(cafb3);
        e2p7 = findViewById(R.id.e2p7);
        arcos.add(e2p7);
        p7p8 = findViewById(R.id.p7p8);
        arcos.add(p7p8);
        p8p9 = findViewById(R.id.p8p9);
        arcos.add(p8p9);
        p9e4 = findViewById(R.id.p9e4);
        arcos.add(p9e4);
        b1a3 = findViewById(R.id.b1a3);
        arcos.add(b1a3);
        a3a4= findViewById(R.id.a3a4);
        arcos.add(a3a4);
        a4a5= findViewById(R.id.a4a5);
        arcos.add(a4a5);
        a5e2= findViewById(R.id.a5e2);
        arcos.add(a5e2);
        e2cap = findViewById(R.id.e2cap);
        arcos.add(e2cap);
        capa6= findViewById(R.id.capa6);
        arcos.add(capa6);
        a6a7 = findViewById(R.id.a6a7);
        arcos.add(a6a7);
        a7b2 = findViewById(R.id.a7b2);
        arcos.add(a7b2);
        b4a12 = findViewById(R.id.b4a12);
        arcos.add(b4a12);
        a12a11 = findViewById(R.id.a12a11);
        arcos.add(a12a11);
        a11e4_1 = findViewById(R.id.a11e4_1);
        arcos.add(a11e4_1);
        a11e4_2 = findViewById(R.id.a11e4_2);
        arcos.add(a11e4_2);
        e4ban = findViewById(R.id.e4ban);
        arcos.add(e4ban);
        bana10 = findViewById(R.id.bana10);
        arcos.add(bana10);
        a10rep = findViewById(R.id.a10rep);
        arcos.add(a10rep);
        repb3 = findViewById(R.id.repb3);
        arcos.add(repb3);
        e1p2 = findViewById(R.id.e1p2);
        arcos.add(e1p2);
        p2p6 = findViewById(R.id.p2p6);
        arcos.add(p2p6);
        p6p8 = findViewById(R.id.p6p8);
        arcos.add(p6p8);
        p8p10 = findViewById(R.id.p8p10);
        arcos.add(p8p10);
        p10p14 = findViewById(R.id.p10p14);
        arcos.add(p10p14);
        p14e3 = findViewById(R.id.p14e3);
        arcos.add(p14e3);
        b1p1 = findViewById(R.id.b1p1);
        arcos.add(b1p1);
        p1p4 = findViewById(R.id.p1p4);
        arcos.add(p1p4);
        p4p6 = findViewById(R.id.p4p6);
        arcos.add(p4p6);
        b4p3 = findViewById(R.id.b4p3);
        arcos.add(b4p3);
        p3p5= findViewById(R.id.p3p5);
        arcos.add(p3p5);
        p5p6= findViewById(R.id.p5p6);
        arcos.add(p5p6);
        b2p13 = findViewById(R.id.b2p13);
        arcos.add(b2p13);
        p13p11 = findViewById(R.id.p13p11);
        arcos.add(p13p11);
        b3p15 = findViewById(R.id.b3p15);
        arcos.add(b3p15);
        p15p12 = findViewById(R.id.p15p12);
        arcos.add(p15p12);
        p11p10 = findViewById(R.id.p11p10);
        arcos.add(p11p10);
        p12p10 = findViewById(R.id.p12p10);
        arcos.add(p12p10);
        e1esc1 = findViewById(R.id.e1esc1);
        arcos.add(e1esc1);
        b1esc2 = findViewById(R.id.b1esc2);
        arcos.add(b1esc2);
        e2esc3 = findViewById(R.id.e2esc3);
        arcos.add(e2esc3);
        b2esc4 = findViewById(R.id.b2esc4);
        arcos.add(b2esc4);
        e3esc5 = findViewById(R.id.e3esc5);
        arcos.add(e3esc5);
        b3esc6 = findViewById(R.id.b3esc6);
        arcos.add(b3esc6);
        banesc7 = findViewById(R.id.bancoesc7);
        arcos.add(banesc7);
        e4esc7 = findViewById(R.id.e4esc7);
        arcos.add(e4esc7);
        e4esc8 = findViewById(R.id.esc8e4);
        arcos.add(e4esc8);
        esc8a11 = findViewById(R.id.esc8a11);
        arcos.add(esc8a11);
        b4esc9 = findViewById(R.id.b4esc9);
        arcos.add(b4esc9);



        //Definimos los botones del piso 1
        vestibulo101 = findViewById(R.id.pvest101);
        nodos.add(vestibulo101);
        aula101 = findViewById(R.id.paula101);
        nodos.add(aula101);
        aula102 = findViewById(R.id.paula102);
        nodos.add(aula102);
        baños101 = findViewById(R.id.pbaños101);
        nodos.add(baños101);
        aula103 = findViewById(R.id.paula103);
        nodos.add(aula103);
        aula104 = findViewById(R.id.paula104);
        nodos.add(aula104);
        aula105 = findViewById(R.id.paula105);
        nodos.add(aula105);
        vestibulo102 = findViewById(R.id.pvest102);
        nodos.add(vestibulo102);
        aula106 = findViewById(R.id.paula106);
        nodos.add(aula106);
        aula107 = findViewById(R.id.paula107);
        nodos.add(aula107);
        baños102 = findViewById(R.id.pbaños102);
        nodos.add(baños102);
        aula108 = findViewById(R.id.paula108);
        nodos.add(aula108);
        aula109 = findViewById(R.id.paula109);
        nodos.add(aula109);
        vestibulo103 = findViewById(R.id.pvest103);
        nodos.add(vestibulo103);
        baños103 = findViewById(R.id.pbaños103);
        nodos.add(baños103);
        aula110 = findViewById(R.id.paula110);
        nodos.add(aula110);
        aula111 = findViewById(R.id.paula111);
        nodos.add(aula111);
        aula112 = findViewById(R.id.paula112);
        nodos.add(aula112);
        baños104 = findViewById(R.id.pbaños104);
        nodos.add(baños104);
        aula113 = findViewById(R.id.paula113);
        nodos.add(aula113);
        aula114 = findViewById(R.id.paula114);
        nodos.add(aula114);
        aula115 = findViewById(R.id.paula115);
        nodos.add(aula115);
        aula116 = findViewById(R.id.paula116);
        nodos.add(aula116);
        aula117 = findViewById(R.id.paula117);
        nodos.add(aula117);
        aula118 = findViewById(R.id.paula118);
        nodos.add(aula118);
        aula119 = findViewById(R.id.paula119);
        nodos.add(aula119);
        escalera101 = findViewById(R.id.pesc101);
        nodos.add(escalera101);
        escalera102 = findViewById(R.id.pesc102);
        nodos.add(escalera102);
        escalera103 = findViewById(R.id.pesc103);
        nodos.add(escalera103);
        escalera104 = findViewById(R.id.pesc104);
        nodos.add(escalera104);
        escalera105 = findViewById(R.id.pesc105);
        nodos.add(escalera105);
        escalera106 = findViewById(R.id.pesc106);
        nodos.add(escalera106);
        escalera107 = findViewById(R.id.pesc107);
        nodos.add(escalera107);
        escalera108 = findViewById(R.id.pesc108);
        nodos.add(escalera108);
        escalera109 = findViewById(R.id.pesc109);
        nodos.add(escalera109);
        v101a101 = findViewById(R.id.v101a101);
        arcos.add(v101a101);
        a101a102 = findViewById(R.id.a101a102);
        arcos.add(a101a102);
        a102b101 = findViewById(R.id.a102b101);
        arcos.add(a102b101);
        b101a103 = findViewById(R.id.b101a103);
        arcos.add(b101a103);
        a103a104 = findViewById(R.id.a103a104);
        arcos.add(a103a104);
        a104a105 = findViewById(R.id.a104a105);
        arcos.add(a104a105);
        a105v102 = findViewById(R.id.a105v102);
        arcos.add(a105v102);
        v102a106 = findViewById(R.id.v102a106);
        arcos.add(v102a106);
        a106a107 = findViewById(R.id.a106a107);
        arcos.add(a106a107);
        a108b102 = findViewById(R.id.a108b102);
        arcos.add(a108b102);
        b102a109 = findViewById(R.id.b102a109);
        arcos.add(b102a109);
        a109a110 = findViewById(R.id.a109a110);
        arcos.add(a109a110);
        a110v103 = findViewById(R.id.a110v103);
        arcos.add(a110v103);
        v103a111 = findViewById(R.id.v103a111);
        arcos.add(v103a111);
        a111a112 = findViewById(R.id.a111a112);
        arcos.add(a111a112);
        a112b103 = findViewById(R.id.a112b103);
        arcos.add(a112b103);
        b103a113 = findViewById(R.id.a113b103);
        arcos.add(b103a113);
        a113a114 = findViewById(R.id.a114a113);
        arcos.add(a113a114);
        a114a115 = findViewById(R.id.a115a114);
        arcos.add(a114a115);
        a115p1 = findViewById(R.id.a115p1);
        arcos.add(a115p1);
        p1a116_1 = findViewById(R.id.a116p1_1);
        arcos.add(p1a116_1);
        p1a116_2 = findViewById(R.id.a116p1_2);
        arcos.add(p1a116_2);
        a116a117 = findViewById(R.id.a116a117);
        arcos.add(a116a117);
        a117b104 = findViewById(R.id.a117b104);
        arcos.add(a117b104);
        b104a118 = findViewById(R.id.b104a118);
        arcos.add(b104a118);
        a118a119 = findViewById(R.id.a118a119);
        arcos.add(a118a119);
        a119v101 = findViewById(R.id.a119v101);
        arcos.add(a119v101);
        v101esc101 = findViewById(R.id.v101esc101);
        arcos.add(v101esc101);
        b101esc102 = findViewById(R.id.b101esc102);
        arcos.add(b101esc102);
        v102esc103 = findViewById(R.id.v102esc103);
        arcos.add(v102esc103);
        b102esc104 = findViewById(R.id.b102esc104);
        arcos.add(b102esc104);
        v103esc105 = findViewById(R.id.v103esc105);
        arcos.add(v103esc105);
        b103esc106 = findViewById(R.id.b103esc106);
        arcos.add(b103esc106);
        a115esc107 = findViewById(R.id.a115esc107);
        arcos.add(a115esc107);
        esc107p1 = findViewById(R.id.esc107p1);
        arcos.add(esc107p1);
        p1esc108 = findViewById(R.id.esc108p1);
        arcos.add(p1esc108);
        esc108a116 = findViewById(R.id.esc108a116);
        arcos.add(esc108a116);
        b104esc109 = findViewById(R.id.b104esc109);
        arcos.add(b104esc109);

        //Definimos los botones del piso 2
        vestibulo201 = findViewById(R.id.pvest201);
        nodos.add(vestibulo201);
        aula201 = findViewById(R.id.paula201);
        nodos.add(aula201);
        aula202 = findViewById(R.id.paula202);
        nodos.add(aula202);
        baños201 = findViewById(R.id.pbaños201);
        nodos.add(baños201);
        aula203 = findViewById(R.id.paula203);
        nodos.add(aula203);
        aula204 = findViewById(R.id.paula204);
        nodos.add(aula204);
        aula205 = findViewById(R.id.paula205);
        nodos.add(aula205);
        vestibulo202 = findViewById(R.id.pvest202);
        nodos.add(vestibulo202);
        aula206 = findViewById(R.id.paula206);
        nodos.add(aula206);
        aula207 = findViewById(R.id.paula207);
        nodos.add(aula207);
        baños202 = findViewById(R.id.pbaños202);
        nodos.add(baños202);
        aula208 = findViewById(R.id.paula208);
        nodos.add(aula208);
        aula209 = findViewById(R.id.paula209);
        nodos.add(aula209);
        vestibulo203 = findViewById(R.id.pvest203);
        nodos.add(vestibulo203);
        baños203 = findViewById(R.id.pbaños203);
        nodos.add(baños203);
        aula210 = findViewById(R.id.paula210);
        nodos.add(aula210);
        aula211 = findViewById(R.id.paula211);
        nodos.add(aula211);
        aula212 = findViewById(R.id.paula212);
        nodos.add(aula212);
        baños204 = findViewById(R.id.pbaños204);
        nodos.add(baños204);
        aula213 = findViewById(R.id.paula213);
        nodos.add(aula213);
        aula214 = findViewById(R.id.paula214);
        nodos.add(aula214);
        aula215 = findViewById(R.id.paula215);
        nodos.add(aula215);
        aula216 = findViewById(R.id.paula216);
        nodos.add(aula216);
        aula217 = findViewById(R.id.paula217);
        nodos.add(aula217);
        aula218 = findViewById(R.id.paula218);
        nodos.add(aula218);
        aula219 = findViewById(R.id.paula219);
        nodos.add(aula219);
        escalera201 = findViewById(R.id.pesc201);
        nodos.add(escalera201);
        escalera202 = findViewById(R.id.pesc202);
        nodos.add(escalera202);
        escalera203 = findViewById(R.id.pesc203);
        nodos.add(escalera203);
        escalera204 = findViewById(R.id.pesc204);
        nodos.add(escalera204);
        escalera205 = findViewById(R.id.pesc205);
        nodos.add(escalera205);
        escalera206 = findViewById(R.id.pesc206);
        nodos.add(escalera206);
        escalera207 = findViewById(R.id.pesc207);
        nodos.add(escalera207);
        escalera208 = findViewById(R.id.pesc208);
        nodos.add(escalera208);
        escalera209 = findViewById(R.id.pesc209);
        nodos.add(escalera209);

        v201a201 = findViewById(R.id.v201a201);
        arcos.add(v201a201);
        a201a202 = findViewById(R.id.a201a202);
        arcos.add(a201a202);
        a202b201 = findViewById(R.id.a202b201);
        arcos.add(a202b201);
        b201a203 = findViewById(R.id.b201a203);
        arcos.add(b201a203);
        a203a204 = findViewById(R.id.a203a204);
        arcos.add(a203a204);
        a204a205 = findViewById(R.id.a204a205);
        arcos.add(a204a205);
        a205v202 = findViewById(R.id.a205v202);
        arcos.add(a205v202);
        v202a206 = findViewById(R.id.v202a206);
        arcos.add(v202a206);
        a206a207 = findViewById(R.id.a206a207);
        arcos.add(a206a207);
        a208b202 = findViewById(R.id.a208b202);
        arcos.add(a208b202);
        b202a209 = findViewById(R.id.b202a209);
        arcos.add(b202a209);
        a209a210 = findViewById(R.id.a209a210);
        arcos.add(a209a210);
        a210v203 = findViewById(R.id.a210v203);
        arcos.add(a210v203);
        v203a211 = findViewById(R.id.v203a211);
        arcos.add(v203a211);
        a211a212 = findViewById(R.id.a211a212);
        arcos.add(a211a212);
        a212b203 = findViewById(R.id.a212b203);
        arcos.add(a212b203);
        b203a213 = findViewById(R.id.a213b203);
        arcos.add(b203a213);
        a213a214 = findViewById(R.id.a214a213);
        arcos.add(a213a214);
        a214a215 = findViewById(R.id.a215a214);
        arcos.add(a214a215);
        a215p2 = findViewById(R.id.a215p2);
        arcos.add(a215p2);
        a216p2_1 = findViewById(R.id.a216p2_1);
        arcos.add(a216p2_1);
        a216p2_2 = findViewById(R.id.a216p2_2);
        arcos.add(a216p2_2);
        a216a217 = findViewById(R.id.a216a217);
        arcos.add(a216a217);
        a217b204 = findViewById(R.id.a217b204);
        arcos.add(a217b204);
        b204a218 = findViewById(R.id.b204a218);
        arcos.add(b204a218);
        a218a219 = findViewById(R.id.a218a219);
        arcos.add(a218a219);
        a219v201 = findViewById(R.id.a219v201);
        arcos.add(a219v201);
        v201esc201 = findViewById(R.id.v201esc201);
        arcos.add(v201esc201);
        b201esc202 = findViewById(R.id.b201esc202);
        arcos.add(b201esc202);
        v202esc203 = findViewById(R.id.v202esc203);
        arcos.add(v202esc203);
        b202esc204 = findViewById(R.id.b202esc204);
        arcos.add(b202esc204);
        v203esc205 = findViewById(R.id.v203esc205);
        arcos.add(v203esc205);
        b203esc206 = findViewById(R.id.b203esc206);
        arcos.add(b203esc206);
        a215esc207 = findViewById(R.id.a215esc207);
        arcos.add(a215esc207);
        esc207p2 = findViewById(R.id.esc207p2);
        arcos.add(esc207p2);
        p2esc208 = findViewById(R.id.esc208p2);
        arcos.add(p2esc208);
        esc208a216 = findViewById(R.id.esc208a216);
        arcos.add(esc208a216);
        b204esc209 = findViewById(R.id.b204esc209);
        arcos.add(b204esc209);
    }

    public void interfazPiso(String nomTransmisor){
        Transmisor t = dbTransmisor.buscarTransmisorNombre(nomTransmisor);
        TextView txt = Escaner.this.findViewById(R.id.tsup);
        if (t.getPiso() == 0){
            layoutp0.setVisibility(View.VISIBLE);
            layoutp1.setVisibility(View.GONE);
            layoutp2.setVisibility(View.GONE);
            img.setImageResource(R.drawable.mapaaulario);
            txt.setText("Planta 0");
        }else if(t.getPiso() == 1){
            layoutp0.setVisibility(View.GONE);
            layoutp1.setVisibility(View.VISIBLE);
            layoutp2.setVisibility(View.GONE);
            img.setImageResource(R.drawable.mapaaulario1);
            txt.setText("Planta 1");
        }else if(t.getPiso() == 2){
            layoutp0.setVisibility(View.GONE);
            layoutp1.setVisibility(View.GONE);
            layoutp2.setVisibility(View.VISIBLE);
            img.setImageResource(R.drawable.mapaaulario1);
            txt.setText("Planta 2");
        }
    }

    public void mostrarTutorial(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tutorial");
        builder.setMessage("El usuario deberá avanzar a traves de la ruta porporcionada para llegar a su destino.\n" +
                "Para ello, deberá seguir las indicaciones que se le proporcionen en la pantalla.\n" +
                "Cuando el usuario avance en su ruta, se irá actualizando en tiempo real hasta llegar a su destino.\n" +
                "En caso de tomar una indicacion de forma erronea,la aplicación le avisará y le proporcionará una nueva ruta.");
        builder.setCancelable(true);
        builder.setPositiveButton("Aceptar",null);
        builder.show();
    }

    private void mostrarPorPantalla(final String line) {
        runOnUiThread(() -> {
            EditText editText = Escaner.this.findViewById(R.id.text);
            editText.setText("");
            editText.append(line+"\n");
        });
    }

    public void planta1(View view) {
        layoutp0.setVisibility(View.GONE);
        layoutp1.setVisibility(View.VISIBLE);
        layoutp2.setVisibility(View.GONE);
    }

    public void planta2(View view) {
        layoutp0.setVisibility(View.GONE);
        layoutp1.setVisibility(View.GONE);
        layoutp2.setVisibility(View.VISIBLE);
    }
}
