package com.example.TFG_3.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.TFG_3.Transmisor;

import java.util.ArrayList;

public class DbTransmisor extends DbHelper  {

    Context context;

    public DbTransmisor(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarTransmisor(String idBeacon, String nombre, String descripcion, String ubicacion) {

         long id = 0;

         try {
             DbHelper dbHelper = new DbHelper(context);
             SQLiteDatabase db = dbHelper.getWritableDatabase();

             ContentValues values = new ContentValues();
             values.put("idBeacon", idBeacon);
             values.put("nombre", nombre);
             values.put("descripcion", descripcion);
             values.put("ubicacion", ubicacion);
             id = db.insert(TABLE_TRANSMISORES, null, values);
             db.close();
         } catch (Exception e) {
             e.toString();
         }
            return id;
    }
  public ArrayList<Transmisor> mostrarTransmisores(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Transmisor> listaTransmisores = new ArrayList<>();
        Transmisor transmisor = null;
        Cursor cursorTransmisores = null;

        cursorTransmisores = db.rawQuery("SELECT * FROM " + TABLE_TRANSMISORES, null);
        if(cursorTransmisores.moveToFirst()){
            do{
                transmisor = new Transmisor(cursorTransmisores.getString(0), cursorTransmisores.getString(1), cursorTransmisores.getString(2), cursorTransmisores.getString(3));
                listaTransmisores.add(transmisor);
            }while(cursorTransmisores.moveToNext());
        }
        cursorTransmisores.close();

        return listaTransmisores;
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
}
