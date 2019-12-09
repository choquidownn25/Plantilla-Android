package com.example.plantilla.sqllite.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //<editor-fold desc="Atributos">
    private  String TAG = "DatabaseHelper";
    // Nombre Tabla
    public static final String TABLE_NAME = "PAIS";

    // Clumnas de una tabla
    public static final String _ID = "_id";
    public static final String SUBJECT = "nombre";
    public static final String DESC = "description";

    // Informacion de la base de datos
    static final String DB_NAME = "JOURNALDEV_COUNTRIES.DB";

    // version de la base de datos
    static final int DB_VERSION = 1;

    // Creacion de del query de la base de datos
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + DESC + " TEXT);";
    //</editor-fold>

    //<editor-fold desc="Sobre escrutura del metodo constructor">
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }
    //</editor-fold>

    //<editor-fold desc="Acciones">
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            //Crea la tabla
            db.execSQL(CREATE_TABLE);
        }catch (Exception ex){
            Log.i(TAG, ex.getMessage());
            ex.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    //</editor-fold>
}
