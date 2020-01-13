package com.example.plantilla.sqllite.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelpersProducto extends SQLiteOpenHelper {

    //<editor-fold desc="Atributos">
    private  String TAG = "DatabaseHelpersProducto";
    // Nombre Tabla
    public static final String TABLE_NAMES= "PRODUCTOS";

    // Clumnas de una tabla
    public static final String _ID = "_id";
    public static final String NOMBRE = "nombre";
    public static final String DESCRIPCION = "descripcion";
    public static final  boolean ACTIVO= true;
    // Informacion de la base de datos
    static final String DB_NAME = "BeneficiarProducto.DB";
    private static final String DATABASE_NAME = "DBProducto.db";
    // version de la base de datos
    static final int DB_VERSION = 1;

    // Creacion de del query de la base de datos
    private static final String CREATE_TABLE = "create table " + TABLE_NAMES + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOMBRE + " TEXT NOT NULL, " + ACTIVO + " TEXT NOT NULL);";
    //</editor-fold>

    //<editor-fold desc="Sobre escritura de metodos">
    public DatabaseHelpersProducto(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DatabaseHelpersProducto(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelpersProducto(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DatabaseHelpersProducto(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }
    //</editor-fold>

    //<editor-fold desc="Metodos Acciones">
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES);
        onCreate(db);
    }
    //</editor-fold>
}
