package com.example.TFG_3;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private final Basedatos dbTransmisor = App.dbTransmisor;
    private final Grafo grafo = App.grafo;
    private final Grafo grafo2 = App.grafo2;
    private final boolean exterior = Monitorear.exterior;
    private final String nT1 = Monitorear.nTransmisor1;
    private final String nT2 = Monitorear.nTransmisor2;
    private final ArrayList<ImageView> nodos = new ArrayList<>();
    private final ArrayList<ImageView> arcos = new ArrayList<>();
    public List<Transmisor> camino;
    private ImageView img;


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
    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        texto = findViewById(R.id.texto);
        if(exterior){
            g = grafo;
        }else{
            g = grafo2;
        }
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
                if (beaconcer != null) {
                    tdetectado = g.buscarTransmisorPorId(beaconcer.getId2().toString());
                }
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
        beaconManager.stopRangingBeacons(App.escanRegion);
        beaconManager.removeAllRangeNotifiers();
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
    @SuppressLint("SetTextI18n")
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
                interfazPiso(t.getNombre());
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
    public void mostrarArco(String a) {
        Log.d(TAG, "Mostrando arco: " + a);
        int k = 0;
        for (ImageView i : arcos) {
            Log.d(TAG, "Arco: " + k + " " + i.getContentDescription());
            k++;
            try {
                if (i.getContentDescription().toString().contains(a)) {
                    i.setVisibility(View.VISIBLE);
                    break;
                }
            }catch (Exception e){
                Log.d(TAG, e.toString());
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

        ImageView entrada1 = findViewById(R.id.pent1);
        nodos.add(entrada1);
        ImageView aula1 = findViewById(R.id.paula1);
        nodos.add(aula1);
        ImageView aula2 = findViewById(R.id.paula2);
        nodos.add(aula2);
        ImageView baños1 = findViewById(R.id.pbaños1);
        nodos.add(baños1);
        ImageView aula3 = findViewById(R.id.paula3);
        nodos.add(aula3);
        ImageView aula4 = findViewById(R.id.paula4);
        nodos.add(aula4);
        ImageView aula5 = findViewById(R.id.paula5);
        nodos.add(aula5);
        ImageView entrada2 = findViewById(R.id.pent2);
        nodos.add(entrada2);
        ImageView capilla = findViewById(R.id.pcap);
        nodos.add(capilla);
        ImageView aula6 = findViewById(R.id.paula6);
        nodos.add(aula6);
        ImageView aula7 = findViewById(R.id.paula7);
        nodos.add(aula7);
        ImageView baños2 = findViewById(R.id.pbaños2);
        nodos.add(baños2);
        ImageView aula8 = findViewById(R.id.paula8);
        nodos.add(aula8);
        ImageView aula9 = findViewById(R.id.paula9);
        nodos.add(aula9);
        ImageView entrada3 = findViewById(R.id.pent3);
        nodos.add(entrada3);
        ImageView cafeteria = findViewById(R.id.pcafe);
        nodos.add(cafeteria);
        ImageView baños3 = findViewById(R.id.pbaños3);
        nodos.add(baños3);
        ImageView reprografia = findViewById(R.id.prepo);
        nodos.add(reprografia);
        ImageView aula10 = findViewById(R.id.paula10);
        nodos.add(aula10);
        ImageView banco = findViewById(R.id.pbanco);
        nodos.add(banco);
        ImageView entrada4 = findViewById(R.id.pent4);
        nodos.add(entrada4);
        ImageView aula11 = findViewById(R.id.paula11);
        nodos.add(aula11);
        ImageView aula12 = findViewById(R.id.paula12);
        nodos.add(aula12);
        ImageView baños4 = findViewById(R.id.pbaños4);
        nodos.add(baños4);
        ImageView aula13 = findViewById(R.id.paula13);
        nodos.add(aula13);
        ImageView aula14 = findViewById(R.id.paula14);
        nodos.add(aula14);
        ImageView patio1 = findViewById(R.id.ppatio1);
        nodos.add(patio1);
        ImageView patio2 = findViewById(R.id.ppatio2);
        nodos.add(patio2);
        ImageView patio3 = findViewById(R.id.ppatio3);
        nodos.add(patio3);
        ImageView patio4 = findViewById(R.id.ppatio4);
        nodos.add(patio4);
        ImageView patio5 = findViewById(R.id.ppatio5);
        nodos.add(patio5);
        ImageView patio6 = findViewById(R.id.ppatio6);
        nodos.add(patio6);
        ImageView patio7 = findViewById(R.id.ppatio7);
        nodos.add(patio7);
        ImageView patio8 = findViewById(R.id.ppatio8);
        nodos.add(patio8);
        ImageView patio9 = findViewById(R.id.ppatio9);
        nodos.add(patio9);
        ImageView patio10 = findViewById(R.id.ppatio10);
        nodos.add(patio10);
        ImageView patio11 = findViewById(R.id.ppatio11);
        nodos.add(patio11);
        ImageView patio12 = findViewById(R.id.ppatio12);
        nodos.add(patio12);
        ImageView patio13 = findViewById(R.id.ppatio13);
        nodos.add(patio13);
        ImageView patio14 = findViewById(R.id.ppatio14);
        nodos.add(patio14);
        ImageView patio15 = findViewById(R.id.ppatio15);
        nodos.add(patio15);
        ImageView escalera1 = findViewById(R.id.pesc1);
        nodos.add(escalera1);
        ImageView escalera2 = findViewById(R.id.pesc2);
        nodos.add(escalera2);
        ImageView escalera3 = findViewById(R.id.pesc3);
        nodos.add(escalera3);
        ImageView escalera4 = findViewById(R.id.pesc4);
        nodos.add(escalera4);
        ImageView escalera5 = findViewById(R.id.pesc5);
        nodos.add(escalera5);
        ImageView escalera6 = findViewById(R.id.pesc6);
        nodos.add(escalera6);
        ImageView escalera7 = findViewById(R.id.pesc7);
        nodos.add(escalera7);
        ImageView escalera8 = findViewById(R.id.pesc8);
        nodos.add(escalera8);
        ImageView escalera9 = findViewById(R.id.pesc9);
        nodos.add(escalera9);

        ImageView a2b1 = findViewById(R.id.a2b1);
        arcos.add(a2b1);
        ImageView a1a2 = findViewById(R.id.a1a2);
        arcos.add(a1a2);
        ImageView e1a1 = findViewById(R.id.e1a1);
        arcos.add(e1a1);
        ImageView a14e1 = findViewById(R.id.a14e1);
        arcos.add(a14e1);
        ImageView a14a13 = findViewById(R.id.a14a13);
        arcos.add(a14a13);
        ImageView a13b4 = findViewById(R.id.a13b4);
        arcos.add(a13b4);
        ImageView b2a8 = findViewById(R.id.b2a8);
        arcos.add(b2a8);
        ImageView a8a9 = findViewById(R.id.a8a9);
        arcos.add(a8a9);
        ImageView a9e3 = findViewById(R.id.a9e3);
        arcos.add(a9e3);
        ImageView e3caf = findViewById(R.id.e3caf);
        arcos.add(e3caf);
        ImageView cafb3 = findViewById(R.id.cafb3);
        arcos.add(cafb3);
        ImageView e2p7 = findViewById(R.id.e2p7);
        arcos.add(e2p7);
        ImageView p7p8 = findViewById(R.id.p7p8);
        arcos.add(p7p8);
        ImageView p8p9 = findViewById(R.id.p8p9);
        arcos.add(p8p9);
        ImageView p9e4 = findViewById(R.id.p9e4);
        arcos.add(p9e4);
        ImageView b1a3 = findViewById(R.id.b1a3);
        arcos.add(b1a3);
        ImageView a3a4 = findViewById(R.id.a3a4);
        arcos.add(a3a4);
        ImageView a4a5 = findViewById(R.id.a4a5);
        arcos.add(a4a5);
        ImageView a5e2 = findViewById(R.id.a5e2);
        arcos.add(a5e2);
        ImageView e2cap = findViewById(R.id.e2cap);
        arcos.add(e2cap);
        ImageView capa6 = findViewById(R.id.capa6);
        arcos.add(capa6);
        ImageView a6a7 = findViewById(R.id.a6a7);
        arcos.add(a6a7);
        ImageView a7b2 = findViewById(R.id.a7b2);
        arcos.add(a7b2);
        ImageView b4a12 = findViewById(R.id.b4a12);
        arcos.add(b4a12);
        ImageView a12a11 = findViewById(R.id.a12a11);
        arcos.add(a12a11);
        ImageView a11e4_1 = findViewById(R.id.a11e4_1);
        arcos.add(a11e4_1);
        ImageView a11e4_2 = findViewById(R.id.a11e4_2);
        arcos.add(a11e4_2);
        ImageView e4ban = findViewById(R.id.e4ban);
        arcos.add(e4ban);
        ImageView bana10 = findViewById(R.id.bana10);
        arcos.add(bana10);
        ImageView a10rep = findViewById(R.id.a10rep);
        arcos.add(a10rep);
        ImageView repb3 = findViewById(R.id.repb3);
        arcos.add(repb3);
        ImageView e1p2 = findViewById(R.id.e1p2);
        arcos.add(e1p2);
        ImageView p2p6 = findViewById(R.id.p2p6);
        arcos.add(p2p6);
        ImageView p6p8 = findViewById(R.id.p6p8);
        arcos.add(p6p8);
        ImageView p8p10 = findViewById(R.id.p8p10);
        arcos.add(p8p10);
        ImageView p10p14 = findViewById(R.id.p10p14);
        arcos.add(p10p14);
        ImageView p14e3 = findViewById(R.id.p14e3);
        arcos.add(p14e3);
        ImageView b1p1 = findViewById(R.id.b1p1);
        arcos.add(b1p1);
        ImageView p1p4 = findViewById(R.id.p1p4);
        arcos.add(p1p4);
        ImageView p4p6 = findViewById(R.id.p4p6);
        arcos.add(p4p6);
        ImageView b4p3 = findViewById(R.id.b4p3);
        arcos.add(b4p3);
        ImageView p3p5 = findViewById(R.id.p3p5);
        arcos.add(p3p5);
        ImageView p5p6 = findViewById(R.id.p5p6);
        arcos.add(p5p6);
        ImageView b2p13 = findViewById(R.id.b2p13);
        arcos.add(b2p13);
        ImageView p13p11 = findViewById(R.id.p13p11);
        arcos.add(p13p11);
        ImageView b3p15 = findViewById(R.id.b3p15);
        arcos.add(b3p15);
        ImageView p15p12 = findViewById(R.id.p15p12);
        arcos.add(p15p12);
        ImageView p11p10 = findViewById(R.id.p11p10);
        arcos.add(p11p10);
        ImageView p12p10 = findViewById(R.id.p12p10);
        arcos.add(p12p10);
        ImageView e1esc1 = findViewById(R.id.e1esc1);
        arcos.add(e1esc1);
        ImageView b1esc2 = findViewById(R.id.b1esc2);
        arcos.add(b1esc2);
        ImageView e2esc3 = findViewById(R.id.e2esc3);
        arcos.add(e2esc3);
        ImageView b2esc4 = findViewById(R.id.b2esc4);
        arcos.add(b2esc4);
        ImageView e3esc5 = findViewById(R.id.e3esc5);
        arcos.add(e3esc5);
        ImageView b3esc6 = findViewById(R.id.b3esc6);
        arcos.add(b3esc6);
        ImageView banesc7 = findViewById(R.id.bancoesc7);
        arcos.add(banesc7);
        ImageView e4esc7 = findViewById(R.id.e4esc7);
        arcos.add(e4esc7);
        ImageView e4esc8 = findViewById(R.id.esc8e4);
        arcos.add(e4esc8);
        ImageView esc8a11 = findViewById(R.id.esc8a11);
        arcos.add(esc8a11);
        ImageView b4esc9 = findViewById(R.id.b4esc9);
        arcos.add(b4esc9);

        //Definimos los botones del piso 1
        ImageView vestibulo101 = findViewById(R.id.pvest101);
        nodos.add(vestibulo101);
        ImageView aula101 = findViewById(R.id.paula101);
        nodos.add(aula101);
        ImageView aula102 = findViewById(R.id.paula102);
        nodos.add(aula102);
        ImageView baños101 = findViewById(R.id.pbaños101);
        nodos.add(baños101);
        ImageView aula103 = findViewById(R.id.paula103);
        nodos.add(aula103);
        ImageView aula104 = findViewById(R.id.paula104);
        nodos.add(aula104);
        ImageView aula105 = findViewById(R.id.paula105);
        nodos.add(aula105);
        ImageView vestibulo102 = findViewById(R.id.pvest102);
        nodos.add(vestibulo102);
        ImageView aula106 = findViewById(R.id.paula106);
        nodos.add(aula106);
        ImageView aula107 = findViewById(R.id.paula107);
        nodos.add(aula107);
        ImageView baños102 = findViewById(R.id.pbaños102);
        nodos.add(baños102);
        ImageView aula108 = findViewById(R.id.paula108);
        nodos.add(aula108);
        ImageView aula109 = findViewById(R.id.paula109);
        nodos.add(aula109);
        ImageView vestibulo103 = findViewById(R.id.pvest103);
        nodos.add(vestibulo103);
        ImageView baños103 = findViewById(R.id.pbaños103);
        nodos.add(baños103);
        ImageView aula110 = findViewById(R.id.paula110);
        nodos.add(aula110);
        ImageView aula111 = findViewById(R.id.paula111);
        nodos.add(aula111);
        ImageView aula112 = findViewById(R.id.paula112);
        nodos.add(aula112);
        ImageView baños104 = findViewById(R.id.pbaños104);
        nodos.add(baños104);
        ImageView aula113 = findViewById(R.id.paula113);
        nodos.add(aula113);
        ImageView aula114 = findViewById(R.id.paula114);
        nodos.add(aula114);
        ImageView aula115 = findViewById(R.id.paula115);
        nodos.add(aula115);
        ImageView aula116 = findViewById(R.id.paula116);
        nodos.add(aula116);
        ImageView aula117 = findViewById(R.id.paula117);
        nodos.add(aula117);
        ImageView aula118 = findViewById(R.id.paula118);
        nodos.add(aula118);
        ImageView aula119 = findViewById(R.id.paula119);
        nodos.add(aula119);
        ImageView escalera101 = findViewById(R.id.pesc101);
        nodos.add(escalera101);
        ImageView escalera102 = findViewById(R.id.pesc102);
        nodos.add(escalera102);
        ImageView escalera103 = findViewById(R.id.pesc103);
        nodos.add(escalera103);
        ImageView escalera104 = findViewById(R.id.pesc104);
        nodos.add(escalera104);
        ImageView escalera105 = findViewById(R.id.pesc105);
        nodos.add(escalera105);
        ImageView escalera106 = findViewById(R.id.pesc106);
        nodos.add(escalera106);
        ImageView escalera107 = findViewById(R.id.pesc107);
        nodos.add(escalera107);
        ImageView escalera108 = findViewById(R.id.pesc108);
        nodos.add(escalera108);
        ImageView escalera109 = findViewById(R.id.pesc109);
        nodos.add(escalera109);
        ImageView pasarela1 = findViewById(R.id.ppasarela1);
        nodos.add(pasarela1);

        ImageView v101a101 = findViewById(R.id.v101a101);
        arcos.add(v101a101);
        ImageView a101a102 = findViewById(R.id.a101a102);
        arcos.add(a101a102);
        ImageView a102b101 = findViewById(R.id.a102b101);
        arcos.add(a102b101);
        ImageView b101a103 = findViewById(R.id.b101a103);
        arcos.add(b101a103);
        ImageView a103a104 = findViewById(R.id.a103a104);
        arcos.add(a103a104);
        ImageView a104a105 = findViewById(R.id.a104a105);
        arcos.add(a104a105);
        ImageView a105v102 = findViewById(R.id.a105v102);
        arcos.add(a105v102);
        ImageView v102a106 = findViewById(R.id.v102a106);
        arcos.add(v102a106);
        ImageView a106a107 = findViewById(R.id.a106a107);
        arcos.add(a106a107);
        ImageView a107a108 = findViewById(R.id.a107a108);
        arcos.add(a107a108);
        ImageView a108b102 = findViewById(R.id.a108b102);
        arcos.add(a108b102);
        ImageView b102a109 = findViewById(R.id.b102a109);
        arcos.add(b102a109);
        ImageView a109a110 = findViewById(R.id.a109a110);
        arcos.add(a109a110);
        ImageView a110v103 = findViewById(R.id.a110v103);
        arcos.add(a110v103);
        ImageView v103a111 = findViewById(R.id.v103a111);
        arcos.add(v103a111);
        ImageView a111a112 = findViewById(R.id.a111a112);
        arcos.add(a111a112);
        ImageView a112b103 = findViewById(R.id.a112b103);
        arcos.add(a112b103);
        ImageView b103a113 = findViewById(R.id.a113b103);
        arcos.add(b103a113);
        ImageView a113a114 = findViewById(R.id.a114a113);
        arcos.add(a113a114);
        ImageView a114a115 = findViewById(R.id.a115a114);
        arcos.add(a114a115);
        ImageView a115p1 = findViewById(R.id.a115p1);
        arcos.add(a115p1);
        ImageView p1a116_1 = findViewById(R.id.a116p1_1);
        arcos.add(p1a116_1);
        ImageView p1a116_2 = findViewById(R.id.a116p1_2);
        arcos.add(p1a116_2);
        ImageView a116a117 = findViewById(R.id.a116a117);
        arcos.add(a116a117);
        ImageView a117b104 = findViewById(R.id.a117b104);
        arcos.add(a117b104);
        ImageView b104a118 = findViewById(R.id.b104a118);
        arcos.add(b104a118);
        ImageView a118a119 = findViewById(R.id.a118a119);
        arcos.add(a118a119);
        ImageView a119v101 = findViewById(R.id.a119v101);
        arcos.add(a119v101);
        ImageView v101esc101 = findViewById(R.id.v101esc101);
        arcos.add(v101esc101);
        ImageView b101esc102 = findViewById(R.id.b101esc102);
        arcos.add(b101esc102);
        ImageView v102esc103 = findViewById(R.id.v102esc103);
        arcos.add(v102esc103);
        ImageView b102esc104 = findViewById(R.id.b102esc104);
        arcos.add(b102esc104);
        ImageView v103esc105 = findViewById(R.id.v103esc105);
        arcos.add(v103esc105);
        ImageView b103esc106 = findViewById(R.id.b103esc106);
        arcos.add(b103esc106);
        ImageView a115esc107 = findViewById(R.id.a115esc107);
        arcos.add(a115esc107);
        ImageView esc107p1 = findViewById(R.id.esc107p1);
        arcos.add(esc107p1);
        ImageView p1esc108 = findViewById(R.id.esc108p1);
        arcos.add(p1esc108);
        ImageView esc108a116 = findViewById(R.id.esc108a116);
        arcos.add(esc108a116);
        ImageView b104esc109 = findViewById(R.id.b104esc109);
        arcos.add(b104esc109);

        //Definimos los botones del piso 2
        ImageView vestibulo201 = findViewById(R.id.pvest201);
        nodos.add(vestibulo201);
        ImageView aula201 = findViewById(R.id.paula201);
        nodos.add(aula201);
        ImageView aula202 = findViewById(R.id.paula202);
        nodos.add(aula202);
        ImageView baños201 = findViewById(R.id.pbaños201);
        nodos.add(baños201);
        ImageView aula203 = findViewById(R.id.paula203);
        nodos.add(aula203);
        ImageView aula204 = findViewById(R.id.paula204);
        nodos.add(aula204);
        ImageView aula205 = findViewById(R.id.paula205);
        nodos.add(aula205);
        ImageView vestibulo202 = findViewById(R.id.pvest202);
        nodos.add(vestibulo202);
        ImageView aula206 = findViewById(R.id.paula206);
        nodos.add(aula206);
        ImageView aula207 = findViewById(R.id.paula207);
        nodos.add(aula207);
        ImageView baños202 = findViewById(R.id.pbaños202);
        nodos.add(baños202);
        ImageView aula208 = findViewById(R.id.paula208);
        nodos.add(aula208);
        ImageView aula209 = findViewById(R.id.paula209);
        nodos.add(aula209);
        ImageView vestibulo203 = findViewById(R.id.pvest203);
        nodos.add(vestibulo203);
        ImageView baños203 = findViewById(R.id.pbaños203);
        nodos.add(baños203);
        ImageView aula210 = findViewById(R.id.paula210);
        nodos.add(aula210);
        ImageView aula211 = findViewById(R.id.paula211);
        nodos.add(aula211);
        ImageView aula212 = findViewById(R.id.paula212);
        nodos.add(aula212);
        ImageView baños204 = findViewById(R.id.pbaños204);
        nodos.add(baños204);
        ImageView aula213 = findViewById(R.id.paula213);
        nodos.add(aula213);
        ImageView aula214 = findViewById(R.id.paula214);
        nodos.add(aula214);
        ImageView aula215 = findViewById(R.id.paula215);
        nodos.add(aula215);
        ImageView aula216 = findViewById(R.id.paula216);
        nodos.add(aula216);
        ImageView aula217 = findViewById(R.id.paula217);
        nodos.add(aula217);
        ImageView aula218 = findViewById(R.id.paula218);
        nodos.add(aula218);
        ImageView aula219 = findViewById(R.id.paula219);
        nodos.add(aula219);
        ImageView escalera201 = findViewById(R.id.pesc201);
        nodos.add(escalera201);
        ImageView escalera202 = findViewById(R.id.pesc202);
        nodos.add(escalera202);
        ImageView escalera203 = findViewById(R.id.pesc203);
        nodos.add(escalera203);
        ImageView escalera204 = findViewById(R.id.pesc204);
        nodos.add(escalera204);
        ImageView escalera205 = findViewById(R.id.pesc205);
        nodos.add(escalera205);
        ImageView escalera206 = findViewById(R.id.pesc206);
        nodos.add(escalera206);
        ImageView escalera207 = findViewById(R.id.pesc207);
        nodos.add(escalera207);
        ImageView escalera208 = findViewById(R.id.pesc208);
        nodos.add(escalera208);
        ImageView escalera209 = findViewById(R.id.pesc209);
        nodos.add(escalera209);
        ImageView pasarela2 = findViewById(R.id.ppasarela2);
        nodos.add(pasarela2);

        ImageView v201a201 = findViewById(R.id.v201a201);
        arcos.add(v201a201);
        ImageView a201a202 = findViewById(R.id.a201a202);
        arcos.add(a201a202);
        ImageView a202b201 = findViewById(R.id.a202b201);
        arcos.add(a202b201);
        ImageView b201a203 = findViewById(R.id.b201a203);
        arcos.add(b201a203);
        ImageView a203a204 = findViewById(R.id.a203a204);
        arcos.add(a203a204);
        ImageView a204a205 = findViewById(R.id.a204a205);
        arcos.add(a204a205);
        ImageView a205v202 = findViewById(R.id.a205v202);
        arcos.add(a205v202);
        ImageView v202a206 = findViewById(R.id.v202a206);
        arcos.add(v202a206);
        ImageView a206a207 = findViewById(R.id.a206a207);
        arcos.add(a206a207);
        ImageView a208b202 = findViewById(R.id.a208b202);
        arcos.add(a208b202);
        ImageView b202a209 = findViewById(R.id.b202a209);
        arcos.add(b202a209);
        ImageView a209a210 = findViewById(R.id.a209a210);
        arcos.add(a209a210);
        ImageView a210v203 = findViewById(R.id.a210v203);
        arcos.add(a210v203);
        ImageView v203a211 = findViewById(R.id.v203a211);
        arcos.add(v203a211);
        ImageView a211a212 = findViewById(R.id.a211a212);
        arcos.add(a211a212);
        ImageView a212b203 = findViewById(R.id.a212b203);
        arcos.add(a212b203);
        ImageView b203a213 = findViewById(R.id.a213b203);
        arcos.add(b203a213);
        ImageView a213a214 = findViewById(R.id.a214a213);
        arcos.add(a213a214);
        ImageView a214a215 = findViewById(R.id.a215a214);
        arcos.add(a214a215);
        ImageView a215p2 = findViewById(R.id.a215p2);
        arcos.add(a215p2);
        ImageView a216p2_1 = findViewById(R.id.a216p2_1);
        arcos.add(a216p2_1);
        ImageView a216p2_2 = findViewById(R.id.a216p2_2);
        arcos.add(a216p2_2);
        ImageView a216a217 = findViewById(R.id.a216a217);
        arcos.add(a216a217);
        ImageView a217b204 = findViewById(R.id.a217b204);
        arcos.add(a217b204);
        ImageView b204a218 = findViewById(R.id.b204a218);
        arcos.add(b204a218);
        ImageView a218a219 = findViewById(R.id.a218a219);
        arcos.add(a218a219);
        ImageView a219v201 = findViewById(R.id.a219v201);
        arcos.add(a219v201);
        ImageView v201esc201 = findViewById(R.id.v201esc201);
        arcos.add(v201esc201);
        ImageView b201esc202 = findViewById(R.id.b201esc202);
        arcos.add(b201esc202);
        ImageView v202esc203 = findViewById(R.id.v202esc203);
        arcos.add(v202esc203);
        ImageView b202esc204 = findViewById(R.id.b202esc204);
        arcos.add(b202esc204);
        ImageView v203esc205 = findViewById(R.id.v203esc205);
        arcos.add(v203esc205);
        ImageView b203esc206 = findViewById(R.id.b203esc206);
        arcos.add(b203esc206);
        ImageView a215esc207 = findViewById(R.id.a215esc207);
        arcos.add(a215esc207);
        ImageView esc207p2 = findViewById(R.id.esc207p2);
        arcos.add(esc207p2);
        ImageView p2esc208 = findViewById(R.id.esc208p2);
        arcos.add(p2esc208);
        ImageView esc208a216 = findViewById(R.id.esc208a216);
        arcos.add(esc208a216);
        ImageView b204esc209 = findViewById(R.id.b204esc209);
        arcos.add(b204esc209);
    }

    @SuppressLint("SetTextI18n")
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


}
