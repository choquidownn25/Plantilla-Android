package com.example.plantilla.sqllite.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.SQLException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.plantilla.sqllite.models.Beneficiary;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //region Atributos
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    // Version Base de datos
    private static final int DATABASE_VERSION = 2;
    // Nombre Base de datos
    private static final String DATABASE_NAME = "BeneficiaryManager.db";
    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + BeneficiaryContracto.BeneficiaryEntry.TABLE_NAME;
    //endregion

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + BeneficiaryContracto.BeneficiaryEntry.TABLE_NAME + " (" +
                BeneficiaryContracto.BeneficiaryEntry._ID + " INTEGER NOT NULL," +
                BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME + " TEXT NOT NULL, " +
                BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL + " TEXT NOT NULL, " +
                BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_ADDRESS + " TEXT NOT NULL, " +
                BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_COUNTRY + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop tabla de usuario si existe
        db.execSQL(DROP_BENEFICIARY_TABLE);
        // Crea tabla otras vez
        onCreate(db);
    }

    //---opens the database---
    public DatabaseHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //Method to create beneficiary records

    public void addBeneficiary(Beneficiary beneficiary) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BeneficiaryContracto.BeneficiaryEntry._ID, beneficiary.getId());
        values.put(BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME, beneficiary.getName());
        values.put(BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL, beneficiary.getEmail());
        values.put(BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_ADDRESS, beneficiary.getAddress());
        values.put(BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_COUNTRY, beneficiary.getCountry());

        db.insert(BeneficiaryContracto.BeneficiaryEntry.TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String email) {

        // array de columnas a fetch
        String[] columns = {
                BeneficiaryContracto.BeneficiaryEntry._ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // creterios de sellecion
        String selection = BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL + " = ?";

        // selecion de arumentos
        String[] selectionArgs = {email};

        // simple query a la tabla usuario
        /**
         * Aquí, la función de consulta se usa para recuperar registros de la tabla de usuario,
         * esta función funciona como si usáramos la consulta SQL.
         * SQL consulta equivalente a esta función de consulta es
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(BeneficiaryContracto.BeneficiaryEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public List<Beneficiary> getAllBeneficiary() {
        // array de columnas a fetch
        String[] columns = {
                BeneficiaryContracto.BeneficiaryEntry._ID,
                BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME,
                BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL,
                BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_ADDRESS,
                BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_COUNTRY
        };
        // sorting orders
        String sortOrder =
                BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME + " ASC";
        List<Beneficiary> beneficiaryList = new ArrayList<Beneficiary>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BeneficiaryContracto.BeneficiaryEntry.TABLE_NAME, //Table to query
                columns,    //columanas a retirnas
                null,        //columnas para clausula WHERE
                null,        //valores para la cláusulaWHERE
                null,       //grupo de filas
                null,       //filtrar por grupos de filas
                sortOrder); //El orden de clasificación


        //Recorriendo todas las filas y agregando a la lista
        if (cursor.moveToFirst()) {
            do {
                Beneficiary beneficiary = new Beneficiary();
                beneficiary.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BeneficiaryContracto.BeneficiaryEntry._ID))));
                beneficiary.setName(cursor.getString(cursor.getColumnIndex(BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_NAME)));
                beneficiary.setEmail(cursor.getString(cursor.getColumnIndex(BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_EMAIL)));
                beneficiary.setAddress(cursor.getString(cursor.getColumnIndex(BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_ADDRESS)));
                beneficiary.setCountry(cursor.getString(cursor.getColumnIndex(BeneficiaryContracto.BeneficiaryEntry.COLUMN_BENEFICIARY_COUNTRY)));
                //Agregar registro de usuario a la lista
                beneficiaryList.add(beneficiary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        //retorna lista de usuarios
        return beneficiaryList;
    }

}
