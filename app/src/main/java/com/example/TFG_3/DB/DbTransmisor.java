package com.example.TFG_3.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

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
             values.put("id", idBeacon);
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
}
