package com.example.plantilla.sqllite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.plantilla.sqllite.helper.DatabaseHelper;
import com.example.plantilla.sqllite.helper.DatabaseHelpers;

public class DBArticulo {

    //<editor-fold desc="Atributos">
    private DatabaseHelpers dbHelper;
    private String TAG="DBArticulo";
    private Context context;

    private SQLiteDatabase database;
    //</editor-fold>

    public DBArticulo(Context context) {
        this.context = context;
    }

    public DBArticulo open() throws SQLException {
        dbHelper= new DatabaseHelpers(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    //Metodo de cierre
    public  void close (){
        dbHelper.close();
    }
    //Insertar
    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelpers.NOMBRE, name);
        contentValue.put(DatabaseHelpers.DESCRIPCION, desc);
        //contentValue.put(DatabaseHelpers.ACTIVO, active);
        database.insert(DatabaseHelpers.TABLE_NAMES, null, contentValue);
    }
    //Metodo de busqueda
    public Cursor fetch() {

        //String[] columns = new String[] { DatabaseHelpers._ID, DatabaseHelpers.NOMBRE, DatabaseHelpers.DESCRIPCION, DatabaseHelpers.ACTIVO };
        String[] columns = new String[] { DatabaseHelpers._ID, DatabaseHelpers.NOMBRE, DatabaseHelpers.DESCRIPCION };
        Cursor cursor = database.query(DatabaseHelpers.TABLE_NAMES, columns, null, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }

        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return cursor;
    }
    //Actualizar
    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelpers.NOMBRE, name);
        contentValues.put(DatabaseHelpers.DESCRIPCION, desc);
        //String datoActivo = String.valueOf((boolean) activo);
        //contentValues.put(DatabaseHelpers.ACTIVO, datoActivo);
        int i = database.update(DatabaseHelpers.TABLE_NAMES, contentValues, DatabaseHelpers._ID + " = " + _id, null);
        return i;
    }
    //Eliminar
    public void delete(long _id) {
        database.delete(DatabaseHelpers.TABLE_NAMES, DatabaseHelpers._ID + "=" + _id, null);
    }
    //Listar
    public Cursor getAll() {
        //String[] columns={DatabaseHelpers._ID,DatabaseHelpers.NOMBRE, DatabaseHelpers.DESCRIPCION, DatabaseHelpers.ACTIVO};
        String[] columns={DatabaseHelpers._ID,DatabaseHelpers.NOMBRE, DatabaseHelpers.DESCRIPCION};
        return database.query(DatabaseHelpers.TABLE_NAMES,columns,null,null,null,null,null);

    }

}
