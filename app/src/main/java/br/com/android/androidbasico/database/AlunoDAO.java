package br.com.android.androidbasico.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JHUNIIN on 12/01/2018.
 */

public class AlunoDAO {
    private DataBaseOpenHelper banco;
    private SQLiteDatabase db;

    public AlunoDAO(Context context) {
        this.banco = new DataBaseOpenHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if (db == null){
            db = banco.getWritableDatabase();
        }
        return db;
    }
}
