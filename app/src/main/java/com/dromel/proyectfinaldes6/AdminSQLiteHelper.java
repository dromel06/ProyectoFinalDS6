package com.dromel.proyectfinaldes6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteHelper extends SQLiteOpenHelper {

    public AdminSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tareas(" +
                "idTareas INTEGER primary key AUTOINCREMENT," +
                "nombre text, fecha date, estado text)");
        db.execSQL("create table login(" +
                "idlogin INTEGER primary key AUTOINCREMENT," +
                "usuario text, password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
