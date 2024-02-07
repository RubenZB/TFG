package com.example.TFG_3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;

public class Monitorear extends Activity implements MonitorNotifier {
    protected static final String TAG = "Monitorear";
    private final BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private Transmisor t;
    private final Basedatos dbTransmisor = App.dbTransmisor;
    public static String nTransmisor1;
    public static String nTransmisor2;
    private String aux = null;
    public static boolean exterior = true;
    private final ArrayList<Button> botonesp0 = new ArrayList<>();
    private final ArrayList<Button> botonesp1 = new ArrayList<>();
    private final ArrayList<Button> botonesp2 = new ArrayList<>();
    private ArrayList<Button> botones = new ArrayList<>();
    private ImageView foto;
    private Spinner spinner;
    private ImageButton ruta;
    private Button planta0;
    private Button planta1;
    private Button planta2;



    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitorear);
        BeaconManager.getInstanceForApplication(this).addMonitorNotifier(this);
        foto = findViewById(R.id.imageView);
        foto.setImageResource(R.drawable.mapaaulario);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mapa, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        incializarBotones();
        botones = botonesp0;
        colorBotones(botones);
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
                t = dbTransmisor.getTransmisor(beaconcer.getId2().toString());
                nTransmisor1 = t.getNombre();
                Log.d(TAG, nTransmisor1);
                botonActual(nTransmisor1);
            }


        };
        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.setForegroundScanPeriod(1000);
        beaconManager.setForegroundBetweenScanPeriod(500);
        beaconManager.startRangingBeacons(App.escanRegion);

    }

    public void incializarBotones(){
        //Definimos botones
        planta0 = findViewById(R.id.planta0);
        planta0.setBackgroundColor(Color.DKGRAY);
        planta1 = findViewById(R.id.planta1);
        planta1.setBackgroundColor(Color.LTGRAY);
        planta2 = findViewById(R.id.planta2);
        planta2.setBackgroundColor(Color.LTGRAY);
        ruta = findViewById(R.id.ruta);

        //Definimos botones planta 0
        Button aula1 = findViewById(R.id.baula1);
        botonesp0.add(aula1);
        Button aula2 = findViewById(R.id.baula2);
        botonesp0.add(aula2);
        Button aula3 = findViewById(R.id.baula3);
        botonesp0.add(aula3);
        Button aula4 = findViewById(R.id.baula4);
        botonesp0.add(aula4);
        Button aula5 = findViewById(R.id.baula5);
        botonesp0.add(aula5);
        Button aula6 = findViewById(R.id.baula6);
        botonesp0.add(aula6);
        Button aula7 = findViewById(R.id.baula7);
        botonesp0.add(aula7);
        Button aula8 = findViewById(R.id.baula8);
        botonesp0.add(aula8);
        Button aula9 = findViewById(R.id.baula9);
        botonesp0.add(aula9);
        Button aula10 = findViewById(R.id.baula10);
        botonesp0.add(aula10);
        Button aula11 = findViewById(R.id.baula11);
        botonesp0.add(aula11);
        Button aula12 = findViewById(R.id.baula12);
        botonesp0.add(aula12);
        Button aula13 = findViewById(R.id.baula13);
        botonesp0.add(aula13);
        Button aula14 = findViewById(R.id.baula14);
        botonesp0.add(aula14);
        Button baño1 = findViewById(R.id.bbano1);
        botonesp0.add(baño1);
        Button baño2 = findViewById(R.id.bbano2);
        botonesp0.add(baño2);
        Button baño3 = findViewById(R.id.bbano3);
        botonesp0.add(baño3);
        Button baño4 = findViewById(R.id.bbano4);
        botonesp0.add(baño4);
        Button reprografia = findViewById(R.id.brepo);
        botonesp0.add(reprografia);
        Button cafeteria = findViewById(R.id.bcafeteria);
        botonesp0.add(cafeteria);
        Button libreria = findViewById(R.id.blibreria);
        botonesp0.add(libreria);
        Button banco = findViewById(R.id.bbanco);
        botonesp0.add(banco);
        Button capilla = findViewById(R.id.bcapilla);
        botonesp0.add(capilla);
        Button entrada1 = findViewById(R.id.bentrada1);
        botonesp0.add(entrada1);
        Button entrada2 = findViewById(R.id.bentrada2);
        botonesp0.add(entrada2);
        Button entrada3 = findViewById(R.id.bentrada3);
        botonesp0.add(entrada3);
        Button entrada4 = findViewById(R.id.bentrada4);
        botonesp0.add(entrada4);

        //Definimos botones planta 1
        Button vestibulo101 = findViewById(R.id.bvestibulo101);
        botonesp1.add(vestibulo101);
        Button vestibulo102 = findViewById(R.id.bvestibulo102);
        botonesp1.add(vestibulo102);
        Button vestibulo103 = findViewById(R.id.bvestibulo103);
        botonesp1.add(vestibulo103);
        Button aula101 = findViewById(R.id.baula101);
        botonesp1.add(aula101);
        Button aula102 = findViewById(R.id.baula102);
        botonesp1.add(aula102);
        Button aula103 = findViewById(R.id.baula103);
        botonesp1.add(aula103);
        Button aula104 = findViewById(R.id.baula104);
        botonesp1.add(aula104);
        Button aula105 = findViewById(R.id.baula105);
        botonesp1.add(aula105);
        Button aula106 = findViewById(R.id.baula106);
        botonesp1.add(aula106);
        Button aula107 = findViewById(R.id.baula107);
        botonesp1.add(aula107);
        Button aula108 = findViewById(R.id.baula108);
        botonesp1.add(aula108);
        Button aula109 = findViewById(R.id.baula109);
        botonesp1.add(aula109);
        Button aula110 = findViewById(R.id.baula110);
        botonesp1.add(aula110);
        Button aula111 = findViewById(R.id.baula111);
        botonesp1.add(aula111);
        Button aula112 = findViewById(R.id.baula112);
        botonesp1.add(aula112);
        Button aula113 = findViewById(R.id.baula113);
        botonesp1.add(aula113);
        Button aula114 = findViewById(R.id.baula114);
        botonesp1.add(aula114);
        Button aula115 = findViewById(R.id.baula115);
        botonesp1.add(aula115);
        Button aula116 = findViewById(R.id.baula116);
        botonesp1.add(aula116);
        Button aula117 = findViewById(R.id.baula117);
        botonesp1.add(aula117);
        Button baño101 = findViewById(R.id.bbano101);
        botonesp1.add(baño101);
        Button baño102 = findViewById(R.id.bbano102);
        botonesp1.add(baño102);
        Button baño103 = findViewById(R.id.bbano103);
        botonesp1.add(baño103);
        Button baño104 = findViewById(R.id.bbano104);
        botonesp1.add(baño104);

        //Definimos botones planta 2
        Button vestibulo201 = findViewById(R.id.bvestibulo201);
        botonesp2.add(vestibulo201);
        Button vestibulo202 = findViewById(R.id.bvestibulo202);
        botonesp2.add(vestibulo202);
        Button vestibulo203 = findViewById(R.id.bvestibulo203);
        botonesp2.add(vestibulo203);
        Button aula201 = findViewById(R.id.baula201);
        botonesp2.add(aula201);
        Button aula202 = findViewById(R.id.baula202);
        botonesp2.add(aula202);
        Button aula203 = findViewById(R.id.baula203);
        botonesp2.add(aula203);
        Button aula204 = findViewById(R.id.baula204);
        botonesp2.add(aula204);
        Button aula205 = findViewById(R.id.baula205);
        botonesp2.add(aula205);
        Button aula206 = findViewById(R.id.baula206);
        botonesp2.add(aula206);
        Button aula207 = findViewById(R.id.baula207);
        botonesp2.add(aula207);
        Button aula208 = findViewById(R.id.baula208);
        botonesp2.add(aula208);
        Button aula209 = findViewById(R.id.baula209);
        botonesp2.add(aula209);
        Button aula210 = findViewById(R.id.baula210);
        botonesp2.add(aula210);
        Button aula211 = findViewById(R.id.baula211);
        botonesp2.add(aula211);
        Button aula212 = findViewById(R.id.baula212);
        botonesp2.add(aula212);
        Button aula213 = findViewById(R.id.baula213);
        botonesp2.add(aula213);
        Button aula214 = findViewById(R.id.baula214);
        botonesp2.add(aula214);
        Button aula215 = findViewById(R.id.baula215);
        botonesp2.add(aula215);
        Button aula216 = findViewById(R.id.baula216);
        botonesp2.add(aula216);
        Button aula217 = findViewById(R.id.baula217);
        botonesp2.add(aula217);
        Button baño201 = findViewById(R.id.bbano201);
        botonesp2.add(baño201);
        Button baño202 = findViewById(R.id.bbano202);
        botonesp2.add(baño202);
        Button baño203 = findViewById(R.id.bbano203);
        botonesp2.add(baño203);
        Button baño204 = findViewById(R.id.bbano204);
        botonesp2.add(baño204);
    }
    //Método para mostrar por pantalla los botones que se filtran
    public void mostrarMapa(View view){
       String opcion = spinner.getSelectedItem().toString().replaceAll(" ","");
       opcion = opcion.toLowerCase();
       colorBotones(botones);
       for(Button b : botones){
           if(opcion.equals("aulas")){
               if(b.getText().toString().contains("aula")){
                   b.setBackgroundColor(Color.BLUE);
               }
           }else if(opcion.equals("baños")){
                if(b.getText().toString().contains("baño")){
                     b.setBackgroundColor(Color.BLUE);
                }
           }else if(opcion.equals("entradas")){
                if(b.getText().toString().contains("entrada")){
                     b.setBackgroundColor(Color.BLUE);
                }
           }else if(opcion.equals("vestibulos")){
                if(b.getText().toString().contains("vestibulo")){
                     b.setBackgroundColor(Color.BLUE);
                }
           }else if(b.getText().toString().contains(opcion)){
                   b.setBackgroundColor(Color.BLUE);
           }
       }
    }

    //Método para ocultar los botones
    public void ocultarBotones(ArrayList<Button> botones){
        for (Button b : botones){
            b.setVisibility(View.INVISIBLE);
        }
    }

    //Método para mostrar los botones
    public void mostrarBotones(ArrayList<Button> botones){
        for (Button b : botones){
            b.setVisibility(View.VISIBLE);
        }
    }
    //Método para colorear los botones
    public void colorBotones(ArrayList<Button> botones){
            for (Button b : botones ) {
                if (b.getText().toString().contains("entrada") || b.getText().toString().contains("baño") ||b.getText().toString().contains("vestibulo") ) {
                    b.setBackgroundColor(Color.LTGRAY);
                } else {
                    b.setBackgroundColor(Color.TRANSPARENT);
                }
        }
    }

    //Método para colorear el botón que indica la posición del usuario
    private void botonActual(String opcion) {
        if(aux != null){
            botonAntiguo(aux);
        }
        aux = opcion;
        for (Button b : botones){
            if(b.getText().toString().equals(opcion)) {
                b.setBackgroundColor(Color.GREEN);
            }
        }
    }
    //Método para colorear el botón que indica la posición anterior del usuario
    public void botonAntiguo(String opcion) {
        for (Button b : botones){
            if(b.getText().toString().contains(opcion)) {
                if (b.getText().toString().contains("entrada") || b.getText().toString().contains("baño") || b.getText().toString().contains("vestibulo")) {
                    b.setBackgroundColor(Color.LTGRAY);
                } else {
                    b.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        }
    }

    //Método para mostrar una ventana flotante con la información del botón
    public void ventanaBoton(View view){
        Button button = (Button) view;
        nTransmisor2 = button.getText().toString();
        Transmisor tAux = dbTransmisor.buscarTransmisorNombre(nTransmisor2);
        String info = tAux.getInfo();
        Log.d(TAG, nTransmisor2);

        // Mostrar una ventana flotante temporal con el nombre del botón
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" ");
        builder.setMessage(tAux.getDescripcion());
        builder.setCancelable(true);
        builder.setPositiveButton("Info", (dialog, which) -> {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(Monitorear.this);
            builder2.setTitle("Información");
            builder2.setMessage(Html.fromHtml(info));
            builder2.setCancelable(true);
            builder2.setPositiveButton("Aceptar",null);
            builder2.show();
        });
        builder.setNegativeButton("Ir", (dialog, which) -> {
            Intent myIntent = new Intent(Monitorear.this, Escaner.class);
            beaconManager.stopRangingBeacons(App.escanRegion);
            beaconManager.removeAllRangeNotifiers();
            Monitorear.this.startActivity(myIntent);
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

    //Método para mostrar el mapa la planta 0
    public void mostrarPlanta0(View view){
        foto.setImageResource(R.drawable.mapaaulario);
        ocultarBotones(botones);
        botones = botonesp0;
        mostrarBotones(botones);
        planta0.setBackgroundColor(Color.DKGRAY);
        planta1.setBackgroundColor(Color.LTGRAY);
        planta2.setBackgroundColor(Color.LTGRAY);
    }
    //Método para mostrar el mapa la planta 1
    public void mostrarPlanta1(View view){
        ocultarBotones(botones);
        botones = botonesp1;
        colorBotones(botones);
        mostrarBotones(botones);
        foto.setImageResource(R.drawable.mapaaulario2);
        planta0.setBackgroundColor(Color.LTGRAY);
        planta1.setBackgroundColor(Color.DKGRAY);
        planta2.setBackgroundColor(Color.LTGRAY);
    }
    //Método para mostrar el mapa la planta 2
    public void mostrarPlanta2(View view){
        ocultarBotones(botones);
        botones = botonesp2;
        colorBotones(botones);
        mostrarBotones(botones);
        foto.setImageResource(R.drawable.mapaaulario2);
        planta0.setBackgroundColor(Color.LTGRAY);
        planta1.setBackgroundColor(Color.LTGRAY);
        planta2.setBackgroundColor(Color.DKGRAY);
    }
    public void cambiarRuta(View view){
        if (exterior){
            exterior = false;
            ruta.setImageResource(R.drawable.snow);
            Toast.makeText(this, "La ruta será por el interior", Toast.LENGTH_SHORT).show();
        }else{
            exterior = true;
            ruta.setImageResource(R.drawable.sunny);
            Toast.makeText(this, "La ruta podrá ir por el exterior", Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarTutorial(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tutorial");
        builder.setMessage("Para ver la información de un lugar del mapa, pulsa sobre el botón correspondiente.\n" +
                "Para ver la ruta hasta un lugar del mapa, pulsa sobre el botón correspondiente y después pulsa en el botón 'Ir'.\n" +
                "Para filtrar los lugares que se muestran en el mapa, selecciona una opción del desplegable y pulsa en el botón 'Mostrar'.\n" +
                "Para cambiar de planta, pulsa sobre el botón de la planta que quieras ver.\n" +
                "Para cambiar entre ruta por el interior o por el exterior, pulsa sobre el botón de arriba a la izquierda.");
        builder.setCancelable(true);
        builder.setPositiveButton("Aceptar",null);
        builder.show();
    }
}
