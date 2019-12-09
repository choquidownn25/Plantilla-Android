package com.example.plantilla.sqllite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.plantilla.sqllite.helper.DatabaseHelper;

public class DBManager {
    //<editor-fold desc="Atributos">
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;
    //</editor-fold>

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() throws SQLException {
        dbHelper= new DatabaseHelper(context);
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
        contentValue.put(DatabaseHelper.SUBJECT, name);
        contentValue.put(DatabaseHelper.DESC, desc);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }
    //Metodo de busqueda
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT, DatabaseHelper.DESC };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    //Actualizar
    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }
    //Eliminar
    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

    //Listar
    public Cursor getAll()
    {
        String[] columns={DatabaseHelper._ID,DatabaseHelper.SUBJECT,DatabaseHelper.DESC};
        return database.query(DatabaseHelper.TABLE_NAME,columns,null,null,null,null,null);

    }
}
