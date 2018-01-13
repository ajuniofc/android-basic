package br.com.android.androidbasico.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.android.androidbasico.model.Aluno;

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

    public void insere(Aluno aluno){
        getDataBase().insert(DataBaseOpenHelper.Alunos.TABELA,null,getContentValues(aluno));
    }

    public void close(){
        getDataBase().close();
    }

    private ContentValues getContentValues(Aluno aluno){
        ContentValues dados = new ContentValues();
        dados.put(DataBaseOpenHelper.Alunos.NOME, aluno.getNome());
        dados.put(DataBaseOpenHelper.Alunos.ENDERECO, aluno.getEndereco());
        dados.put(DataBaseOpenHelper.Alunos.TELEFONE, aluno.getTelefone());
        dados.put(DataBaseOpenHelper.Alunos.SITE, aluno.getSite());
        dados.put(DataBaseOpenHelper.Alunos.NOTA, aluno.getNota());
        return dados;
    }
}
