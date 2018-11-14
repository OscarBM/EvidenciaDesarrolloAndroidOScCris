package com.tecmi.oscar.calendardemo3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDSQLite extends SQLiteOpenHelper {

    //Se define si es abstract o no en la declaración de clase
    private String sql = "create table events("+
            "idEvent in identity," +
            "nameEvent varchar(40)," +
            "location varchar(60)," +
            "startDate date," +
            "startHour time," +
            "endDate date," +
            "endHour time," +
            "description varchar(60)";
    public BDSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
