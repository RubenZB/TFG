package com.example.TFG_3;
import android.content.Context;
import android.util.Log;


import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Grafo {
    private final Graph<Transmisor, DefaultWeightedEdge> grafo;
    private final Basedatos dbTransmisor = App.dbTransmisor;


    public Grafo() {
        grafo = new WeightedMultigraph<>(DefaultWeightedEdge.class);
    }

    public void agregarTransmisor(Transmisor transmisor) {
        grafo.addVertex(transmisor);
    }

    public void conectarTransmisores(Transmisor transmisor1, Transmisor transmisor2, double peso) {
        grafo.addEdge(transmisor1, transmisor2);
        grafo.addEdge(transmisor2, transmisor1);
        grafo.setEdgeWeight(grafo.getEdge(transmisor1, transmisor2), peso);
        grafo.setEdgeWeight(grafo.getEdge(transmisor2, transmisor1), peso);
    }

    public void leerTransmisoresDesdeCSV(Context context, String nombreArchivoCSV) {
        try {
            InputStream inputStream = context.getAssets().open(nombreArchivoCSV);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String linea;
            while ((linea = reader.readLine()) !=  null) {
                    String nombreTransmisor = linea.trim();
                    Transmisor transmisor = dbTransmisor.buscarTransmisorNombre(nombreTransmisor);
                    if (transmisor != null) {
                        agregarTransmisor(transmisor);
                        //Log.d("Grafo", "Agregando " + transmisor.getNombre());
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
                    Transmisor transmisor1 = buscarTransmisorPorNombre(datos[0]);
                    Transmisor transmisor2 = buscarTransmisorPorNombre(datos[1]);
                    double p = Double.parseDouble(datos[2]);
                    if (transmisor1 != null && transmisor2 != null) {
                        conectarTransmisores(transmisor1, transmisor2,p);
                        //Log.d("Grafo", "Conectando " + transmisor1.getNombre() + " con " + transmisor2.getNombre());

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

    public Transmisor buscarTransmisorPorId(String id) {
        for (Transmisor transmisor : grafo.vertexSet()) {
            if (transmisor.getBeacon().equals(id)) {
                return transmisor;
            }
        }
        return null;
    }

    public ArrayList<Transmisor> obtenerCaminoMasRapido(Transmisor origen, Transmisor destino) {
        DijkstraShortestPath<Transmisor, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(grafo);
        List<DefaultWeightedEdge> edgeList = dijkstra.getPath(origen, destino).getEdgeList();

        ArrayList<Transmisor> camino = new ArrayList<>();
        camino.add(origen);

        if (edgeList != null) {
            for (DefaultWeightedEdge edge : edgeList) {
                Transmisor siguienteTransmisor = Graphs.getOppositeVertex(grafo, edge, camino.get(camino.size() - 1));
                Transmisor transmisorActual = camino.get(camino.size() - 1);
                String ar = transmisorActual.getNombre() + siguienteTransmisor.getNombre();
                Log.d("Grafo", "arco " + ar);
                camino.add(siguienteTransmisor);

            }
        }

        return camino;
    }

    public ArrayList<String> obtenerArcos(Transmisor origen, Transmisor destino) {
        DijkstraShortestPath<Transmisor, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(grafo);
        List<DefaultWeightedEdge> edgeList = dijkstra.getPath(origen, destino).getEdgeList();

        ArrayList<Transmisor> camino = new ArrayList<>();
        ArrayList<String> arcos = new ArrayList<>();
        camino.add(origen);

        if (edgeList != null) {
            for (DefaultWeightedEdge edge : edgeList) {
                Transmisor siguienteTransmisor = grafo.getEdgeSource(edge);
                Transmisor transmisorActual = grafo.getEdgeTarget(edge);
                if (!transmisorActual.getNombre().contains("escalera") || !siguienteTransmisor.getNombre().contains("escalera")) {
                    String ar = transmisorActual.getNombre() + siguienteTransmisor.getNombre();
                    camino.add(siguienteTransmisor);
                    arcos.add(ar);
                }
            }
        }

        return arcos;
    }
    public double pesototalRuta(List<Transmisor> camino) {
        double peso = 0;
        for (int i = 0; i < camino.size() - 1; i++) {
            Transmisor origen = camino.get(i);
            Transmisor destino = camino.get(i + 1);
            DefaultWeightedEdge arco = grafo.getEdge(origen, destino);
            if (arco != null) {
                peso += grafo.getEdgeWeight(arco);
            } else {

                System.out.println("No se han encontrado arcos entre" + origen + " y " + destino);
            }
        }
        return peso;
    }
}
