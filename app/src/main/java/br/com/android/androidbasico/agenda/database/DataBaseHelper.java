package br.com.android.androidbasico.agenda.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.android.androidbasico.agenda.model.Aluno;

/**
 * Created by JHUNIIN on 27/01/2018.
 */

public class DataBaseHelper {

    public static void createTable(SQLiteDatabase db, String tableName, HashMap<String, String> map){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE "+tableName+" (");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            sql.append(entry.getKey()+" "+entry.getValue()+",");
            iterator.remove();
        }
        int length = sql.length();
        sql.replace(length-1, length,")");
        db.execSQL(sql.toString());
    }

    public static void migrationTable(SQLiteDatabase db, String origin, String destiny, String[] columns){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO "+destiny+" (");
        for (String string : columns) {
            sql.append(string + ",");
        }
        int length = sql.length();
        sql.replace(length-1, length,") ");
        sql.append("SELECT * FROM "+origin);
        db.execSQL(sql.toString());
    }


    public static void dropTable(SQLiteDatabase db, String tabela) {
        db.execSQL("DROP TABLE "+tabela);
    }

    public static void alterTableName(SQLiteDatabase db, String lastName, String newName) {
        db.execSQL("ALTER TABLE "+lastName+ " RENAME TO "+newName);
    }

    public static void updateAlunosWithUUID(SQLiteDatabase db, String name, String columnId) {
        AlunoDAO dao = new AlunoDAO(db);
        List<Aluno> alunos = dao.buscarAlunos();
        for (Aluno aluno: alunos) {
            dao.atualiza(aluno, UUID.randomUUID().toString());
        }
    }

    public static void alterTableAddColumn(SQLiteDatabase db, String tabela, String column, String columnType) {
        String sql = "ALTER TABLE "+ tabela+" ADD COLUMN "+ column+" "+columnType;
        db.execSQL(sql);
    }
}
