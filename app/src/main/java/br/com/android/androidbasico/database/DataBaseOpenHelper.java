package br.com.android.androidbasico.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JHUNIIN on 12/01/2018.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "agenda.db";
    private static final int VERSION = 2;

    public DataBaseOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableAlunos = "CREATE TABLE "+Alunos.TABELA+ " ("+
                Alunos.ID+" INTEGER PRIMARY KEY," +
                Alunos.NOME+" TEXT NOT NULL," +
                Alunos.ENDERECO+" TEXT," +
                Alunos.TELEFONE+" TEXT," +
                Alunos.SITE+" TEXT," +
                Alunos.NOTA+" REAL," +
                Alunos.CAMINHO_FOTO+" TEXT);";

        db.execSQL(sqlCreateTableAlunos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE "+Alunos.TABELA+" ADD COLUMN "+Alunos.CAMINHO_FOTO+" TEXT";
                db.execSQL(sql);
        }
    }

    public static class Alunos{
        public static final String TABELA = "alunos";
        public static final String ID = "_id";
        public static final String NOME = "nome";
        public static final String ENDERECO = "endereco";
        public static final String TELEFONE = "telefone";
        public static final String SITE = "site";
        public static final String NOTA = "nota";
        public static final String CAMINHO_FOTO = "caminho_foto";

        public static final String[] COLUNAS = new String[]{
                ID, NOME, ENDERECO, TELEFONE, SITE, NOTA, CAMINHO_FOTO
        };
    }
}
