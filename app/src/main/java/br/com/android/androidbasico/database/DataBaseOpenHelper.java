package br.com.android.androidbasico.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JHUNIIN on 12/01/2018.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "agenda.db";
    private static final int VERSION = 1;

    public DataBaseOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
