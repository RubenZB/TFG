package com.example.TFG_3;
import android.content.Context;
import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.SimpleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private Graph<Transmisor, DefaultEdge> grafo;
    private DbHelper dbTransmisor = App.dbTransmisor;
    private Context context = DbHelper.context;

    public Grafo() {
        // Crear un grafo no dirigido y multigrafo
        grafo = new Multigraph<>(DefaultEdge.class);

    }

    public void agregarTransmisor(Transmisor transmisor) {
        grafo.addVertex(transmisor);
    }

    public void conectarTransmisores(Transmisor transmisor1, Transmisor transmisor2) {
        grafo.addEdge(transmisor1, transmisor2);
        grafo.addEdge(transmisor2, transmisor1);
    }

    public void leerTransmisoresDesdeCSV(Context context, String nombreArchivoCSV) {
        try {
            InputStream inputStream = context.getAssets().open(nombreArchivoCSV);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String linea;
;
            while ((linea = reader.readLine()) !=  null) {
                    String nombreTransmisor = linea.trim();
                    Transmisor transmisor = dbTransmisor.buscarTransmisorNombre(nombreTransmisor);
                    if (transmisor != null) {
                        agregarTransmisor(transmisor);
                        Log.d("Grafo", "Agregando " + transmisor.getNombre());
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarConexionesDesdeCSV(Context context, String nombreArchivoCSV) {
        try {
            InputStream inputStream = context.getAssets().open(nombreArchivoCSV);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 2) {
                    String nombreTransmisor1 = datos[0];
                    String nombreTransmisor2 = datos[1];
                    Transmisor transmisor1 = buscarTransmisorPorNombre(datos[0]);
                    Transmisor transmisor2 = buscarTransmisorPorNombre(datos[1]);
                    if (transmisor1 != null && transmisor2 != null) {
                        conectarTransmisores(transmisor1, transmisor2);
                        Log.d("Grafo", "Conectando " + transmisor1.getNombre() + " con " + transmisor2.getNombre());

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Transmisor buscarTransmisorPorNombre(String nombre) {
        for (Transmisor transmisor : grafo.vertexSet()) {
            if (transmisor.getNombre().equals(nombre)) {
                return transmisor;
            }
        }
        return null;
    }

    public List<Transmisor> obtenerCamino(Transmisor nodoPartida, Transmisor nodoDestino) {
        // Realizar un recorrido en profundidad (DFS) para obtener el camino
        List<Transmisor> camino = new ArrayList<>();
        DFSRecursivo(nodoPartida, nodoDestino, camino);
        return camino;
    }

    private boolean DFSRecursivo(Transmisor nodoActual, Transmisor nodoDestino, List<Transmisor> camino) {
        camino.add(nodoActual);

        if (nodoActual.equals(nodoDestino)) {
            return true;
        }

        for (DefaultEdge edge : grafo.edgesOf(nodoActual)) {
            Transmisor vecino = grafo.getEdgeTarget(edge);
            if (!vecino.equals(nodoActual)) {
                if (DFSRecursivo(vecino, nodoDestino, camino)) {
                    return true;
                }
            }
        }

        camino.remove(nodoActual);
        return false;
    }
}
