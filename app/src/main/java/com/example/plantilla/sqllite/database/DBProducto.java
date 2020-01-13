package com.example.plantilla.sqllite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.plantilla.sqllite.helper.DatabaseHelpers;
import com.example.plantilla.sqllite.helper.DatabaseHelpersProducto;

public class DBProducto {

    //<editor-fold desc="Atributos">
    private DatabaseHelpersProducto dbHelper;
    private String TAG="DBProduto";
    private Context context;
    private SQLiteDatabase database;
    //</editor-fold>

    //<editor-fold desc="Constuctores">
    public DBProducto(Context context) {
        this.context = context;
    }

    public DBProducto open() throws SQLException {
        dbHelper= new DatabaseHelpersProducto(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    //</editor-fold>

    //Metodo de cierre
    public  void close (){
        dbHelper.close();
    }
    //Insertar
    public void insert(String name, boolean active) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelpersProducto.NOMBRE, name);
        //contentValue.put(DatabaseHelpers.DESCRIPCION, desc);
        contentValue.put(String.valueOf(DatabaseHelpersProducto.ACTIVO), active);
        database.insert(DatabaseHelpersProducto.TABLE_NAMES, null, contentValue);
    }
    //Metodo de busqueda
    public Cursor fetch() {

        //String[] columns = new String[] { DatabaseHelpers._ID, DatabaseHelpers.NOMBRE, DatabaseHelpers.DESCRIPCION, DatabaseHelpers.ACTIVO };
        String[] columns = new String[] { DatabaseHelpersProducto._ID, DatabaseHelpersProducto.NOMBRE, String.valueOf(DatabaseHelpersProducto.ACTIVO)};
        Cursor cursor = database.query(DatabaseHelpersProducto.TABLE_NAMES, columns, null, null, null, null, null);
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
    public int update(long _id, String name, boolean activo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelpersProducto.NOMBRE, name);
        contentValues.put(String.valueOf(DatabaseHelpersProducto.ACTIVO), activo);
        //String datoActivo = String.valueOf((boolean) activo);
        //contentValues.put(DatabaseHelpers.ACTIVO, datoActivo);
        int i = database.update(DatabaseHelpersProducto.TABLE_NAMES, contentValues, DatabaseHelpersProducto._ID + " = " + _id, null);
        return i;
    }
    //Eliminar
    public void delete(long _id) {
        database.delete(DatabaseHelpers.TABLE_NAMES, DatabaseHelpers._ID + "=" + _id, null);
    }
    //Listar
    public Cursor getAll() {
        //String[] columns={DatabaseHelpers._ID,DatabaseHelpers.NOMBRE, DatabaseHelpers.DESCRIPCION, DatabaseHelpers.ACTIVO};
        String[] columns={DatabaseHelpersProducto._ID,DatabaseHelpersProducto.NOMBRE, String.valueOf(DatabaseHelpersProducto.ACTIVO)};
        return database.query(DatabaseHelpersProducto.TABLE_NAMES,columns,null,null,null,null,null);

    }
}
