package com.example.minhaagenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE = "db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBSchema.AppointmentsTbl.NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBSchema.AppointmentsTbl.Cols.uuid + "," +
                DBSchema.AppointmentsTbl.Cols.description + "," +
                DBSchema.AppointmentsTbl.Cols.date + "," +
                DBSchema.AppointmentsTbl.Cols.time + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBSchema.AppointmentsTbl.NAME);
        onCreate(db);
    }
}
