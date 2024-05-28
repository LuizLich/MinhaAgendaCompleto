package com.example.minhaagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class AppointmentDB {
    private Context context;
    private SQLiteDatabase db;

    public AppointmentDB(Context _context) {
        context = _context.getApplicationContext();
        db = new DBHelper(context).getWritableDatabase();
    }

    private static ContentValues getContentValues(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(DBSchema.AppointmentsTbl.Cols.uuid, appointment.getUUID().toString());
        values.put(DBSchema.AppointmentsTbl.Cols.description, appointment.getDescription());
        values.put(DBSchema.AppointmentsTbl.Cols.date, appointment.getData().toString());
        values.put(DBSchema.AppointmentsTbl.Cols.time, appointment.getTime().toString());
        return values;
    }

    public void insertAppointment(Appointment appointment) {
        ContentValues values = getContentValues(appointment);
        db.insert(DBSchema.AppointmentsTbl.NAME, null, values);
    }

    public ArrayList<Appointment> selectAppointments(String where, String[] args) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Cursor cursor = db.query(DBSchema.AppointmentsTbl.NAME, null, where, args, null, null,  null);

        if(cursor != null) {
            try {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.AppointmentsTbl.Cols.uuid)));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.AppointmentsTbl.Cols.description));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.AppointmentsTbl.Cols.date));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.AppointmentsTbl.Cols.time));
                    Appointment appointment = new Appointment(uuid, date, time, description);
                    appointments.add(appointment);
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
        }

        return appointments;
    }

    public ArrayList<Appointment> selectAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Cursor cursor = db.query(DBSchema.AppointmentsTbl.NAME, null, null, null, null, null,  null);

        if(cursor != null) {
            try {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.AppointmentsTbl.Cols.uuid)));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.AppointmentsTbl.Cols.description));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.AppointmentsTbl.Cols.date));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.AppointmentsTbl.Cols.time));
                    Appointment appointment = new Appointment(uuid, date, time, description);
                    appointments.add(appointment);
                    cursor.moveToNext();
                }
            } catch (Exception e) {
                Log.d("ERROR", e.getLocalizedMessage());
            } finally {
                cursor.close();
            }
        }

        return appointments;
    }

    public void close() {
        db.close();
    }
}

