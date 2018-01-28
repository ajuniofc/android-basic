package br.com.android.androidbasico.agenda.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import br.com.android.androidbasico.agenda.model.Aluno;

/**
 * Created by JHUNIIN on 12/01/2018.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "agenda.db";
    private static final int VERSION = 5;

    public DataBaseOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DataBaseHelper.createTable(db,"Alunos",getHashMapAlunos());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
            case 1:
                DataBaseHelper.alterTableAddColumn(db,Alunos.TABELA,Alunos.CAMINHO_FOTO, "TEXT");
            case 2:
                DataBaseHelper.createTable(db,"Alunos_novo",getHashMapAlunos());
                DataBaseHelper.migrationTable(db, Alunos.TABELA, "Alunos_novo", Alunos.COLUNAS);
                DataBaseHelper.dropTable(db, Alunos.TABELA);
                DataBaseHelper.alterTableName(db, "Alunos_novo", Alunos.TABELA);
            case 3:
                DataBaseHelper.updateAlunosWithUUID(db, Alunos.TABELA, Alunos.ID);
            case 4:
                DataBaseHelper.updateAlunosWithUUID(db, Alunos.TABELA, Alunos.ID);
        }
    }

    private HashMap<String, String> getHashMapAlunos() {
        HashMap hashMap = new HashMap();
        hashMap.put(Alunos.ID,"CHAR(36) PRIMARY KEY");
        hashMap.put(Alunos.NOME,"TEXT NOT NULL");
        hashMap.put(Alunos.ENDERECO,"TEXT");
        hashMap.put(Alunos.TELEFONE,"TEXT");
        hashMap.put(Alunos.SITE,"TEXT");
        hashMap.put(Alunos.NOTA,"REAL");
        hashMap.put(Alunos.CAMINHO_FOTO,"TEXT");

        return hashMap;
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
