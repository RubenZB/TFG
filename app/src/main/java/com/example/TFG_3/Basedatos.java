package com.example.TFG_3;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Basedatos extends SQLiteOpenHelper {

    public static final String COLUMNA_ID = "idBeacon";
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_DESCRIPCION = "descripcion";
    public static final String COLUMNA_INFORMACION = "info";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "TFG_3.db";
    public static final String TABLE_TRANSMISORES = "t_trasmisores";
    public static Context context;


    public Basedatos(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TRANSMISORES + " ("
                + COLUMNA_ID + " TEXT PRIMARY KEY, " +
                COLUMNA_NOMBRE + " TEXT, " +
                COLUMNA_DESCRIPCION + " TEXT, " +
                COLUMNA_INFORMACION + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE " + TABLE_TRANSMISORES);
        onCreate(db);
    }
    public long insertarTransmisor(String idBeacon, String nombre, String descripcion, String info) {

        long id = 0;

        try {
            Basedatos dbHelper = new Basedatos(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idBeacon", idBeacon);
            values.put("nombre", nombre);
            values.put("descripcion", descripcion);
            values.put("info", info);
            id = db.insert(TABLE_TRANSMISORES, null, values);
            db.close();
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }

    public void borrarBaseDeDatos() {
        Context context = this.context;
        context.deleteDatabase("TFG_3.db");
    }
    public ArrayList<Transmisor> mostrarTransmisores(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_TRANSMISORES, null, null, null, null, null, null);

        ArrayList<Transmisor> transmisores = new ArrayList<>();

        while (cursor.moveToNext()) {
            String idBeacon = cursor.getString(0);
            String nombre = cursor.getString(1);
            String descripcion = cursor.getString(2);
            String info = cursor.getString(3);

            Transmisor transmisor = new Transmisor(idBeacon, nombre, descripcion, info);
            transmisores.add(transmisor);
        }

        cursor.close();
        db.close();

        return transmisores;
    }

    public Transmisor getTransmisor(String id){
        ArrayList<Transmisor> listaTransmisores = mostrarTransmisores();
        Transmisor transmisor = null;
        for(Transmisor t : listaTransmisores){
            if (t.getBeacon().equals(id)){
                transmisor = t;
            }
        }
        return transmisor;
    }
    public void cargarDatosCSV(SQLiteDatabase db) {
        InputStream inputStream = null;
        BufferedReader reader = null;

        try {
            inputStream = context.getAssets().open("transmisores.csv");
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                String idBeacon = datos[0];
                String nombre = datos[1];
                String descripcion = datos[2];
                String info = datos[3];

                if(getTransmisor(idBeacon) == null){
                    long n = insertarTransmisor(idBeacon, nombre, descripcion, info);
                }else{
                    Log.d("BASE_DATOS", "El transmisor ya existe");
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error al leer el archivo CSV: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error al cerrar el BufferedReader: " + e.getMessage());
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error al cerrar el InputStream: " + e.getMessage());
                }
            }
        }
    }
    public void mostrarContenidoBaseDatos() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM t_trasmisores", null);

        if (cursor.moveToFirst()) {
            do {
                String idBeacon = cursor.getString(0);
                String nombre = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String info = cursor.getString(3);


                Log.d("BASE_DATOS", "idBeacon: " + idBeacon + ", nombre: " + nombre + ", descripcion: " + descripcion + ", informacion: " + info);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
    public Transmisor buscarTransmisorNombre(String nombre) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM t_trasmisores WHERE nombre LIKE '" +nombre+"'", null);

        Transmisor transmisor = null;
        if (cursor.moveToFirst()) {
            String idBeacon = cursor.getString(0);
            String nom = cursor.getString(1);
            String descripcion = cursor.getString(2);
            String info = cursor.getString(3);

            transmisor = new Transmisor(idBeacon, nom, descripcion, info);
        }

        cursor.close();
        db.close();

        return transmisor;
    }
}
