package com.example.sistemalistview.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConnectionFactory extends SQLiteOpenHelper {
    private static final String NAME = "banco.db";
    private  static final  int VERSION = 1;

    public ConnectionFactory(@Nullable Context context) {
        super(context, NAME,null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table aluno(id integer primary key autoincrement, nome varchar(50), cpf varchar(50) unique, telefone varchar(50), data_criacao datetime)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS ALUNO";
        db.execSQL(sql);
        onCreate(db);
    }
}
