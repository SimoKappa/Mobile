package com.example.progetto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database contenente le materie salvate tramite la funzione di orario, contiene anche le informazioni su orario e aula sebbene non siano strettamente necessarie
 */
public class DataManager extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public static final String TABLE_ROW_ID = "_id";//id dell'elemento
    public static final String TABLE_ROW_C = "c";   //codice
    public static final String TABLE_ROW_M = "m";   //materia
    public static final String TABLE_ROW_O = "o";   //ora
    public static final String TABLE_ROW_A = "a";   //aula

    private static final String DB_NAME = "c_m_o_a_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_C_M_AND_O_AND_A = "c_m_and_o_and_a";

    public DataManager (Context context){
        super(context, DB_NAME, null, DB_VERSION);
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper (context);
        db = helper.getWritableDatabase();
    }

    /**
     * Inserimento degli elementi nella tabella
     * @param m
     * @param o
     * @param a
     * @param c
     */
    public void insert(String m, String o, String a, String c){
        String query = "INSERT INTO " + TABLE_C_M_AND_O_AND_A + " (" + TABLE_ROW_C + ", " + TABLE_ROW_M + ", " + TABLE_ROW_O + ", " + TABLE_ROW_A + ") " + "VALUES (" + "'" + c + "'" + ", " + "'" + m + "'" + ", " + "'" + o + "'" + ", " + "'" + a + "'" + ");";
        Log.i("insert() = ", query);
        db.execSQL(query);
    }

    /**
     * Eliminazione dell'elemento della tabella con ricerca sulla materia
     * @param m
     */
    public void delete(String m){
        String query = "DELETE FROM " + TABLE_C_M_AND_O_AND_A + " WHERE " + TABLE_ROW_M + " = '" + m + "';";
        Log.i("delete() = ", query);
        db.execSQL(query);
    }

    /**
     * Estrazione di tutti gli elementi dalla tabella
     * @return
     */
    public Cursor selectAll(){
        Cursor c = db.rawQuery("SELECT* " + "FROM " + TABLE_C_M_AND_O_AND_A , null);
        return c;
    }

    /**
     * Ricerca dell'elemento della tabella sul nome della materia
     * @param m
     * @return
     */
    public Cursor searchM(String m){
        String query = "SELECT " + TABLE_ROW_ID + ", " + TABLE_ROW_C + ", " + TABLE_ROW_M + ", " + TABLE_ROW_O + ", " + TABLE_ROW_A + " from " + TABLE_C_M_AND_O_AND_A + " WHERE " + TABLE_ROW_M + " = '" + m + "';";
        Log.i("searchM() = ", query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * Necessario per utilizzare SQLiteOpenHelper
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper{
        public CustomSQLiteOpenHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        /**
         * Creazione della tabella
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db){
            String newTableQueryString = "create table " + TABLE_C_M_AND_O_AND_A + " (" + TABLE_ROW_ID + " integer primary key autoincrement not null, " + TABLE_ROW_C + " text not null, " + TABLE_ROW_M + " text not null, " + TABLE_ROW_O + " text not null, " + TABLE_ROW_A + " text not null);";
            db.execSQL(newTableQueryString);
        }

        /**
         * Necessario per utilizzare SQLiteOpenHelper
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        }
    }
}

