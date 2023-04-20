package com.example.TFG_3.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "TFG_3.db";
    public static final String TABLE_TRANSMISORES = "t_trasmisores";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TRANSMISORES + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT,"
                + "descripcion TEXT,"
                + "ubicacion TEXT"
                + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE " + TABLE_TRANSMISORES);
        onCreate(db);
    }
}
