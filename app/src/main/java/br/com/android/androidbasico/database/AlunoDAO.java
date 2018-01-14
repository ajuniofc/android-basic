package br.com.android.androidbasico.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public List<Aluno> buscarAlunos() {
        List<Aluno> list = new ArrayList<>();
        String sql = "SELECT * FROM "+DataBaseOpenHelper.Alunos.TABELA;
        Cursor cursor = getDataBase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Aluno aluno = recuperaAluno(cursor);
            list.add(aluno);
        }
        cursor.close();
        return list;
    }

    public void deleta(Aluno aluno) {
        String where = DataBaseOpenHelper.Alunos.ID+" = ?";
        String[] params = {String.valueOf(aluno.getId())};
        getDataBase().delete(DataBaseOpenHelper.Alunos.TABELA,where,params);
    }

    public void atualiza(Aluno aluno) {
        String where = DataBaseOpenHelper.Alunos.ID+" = ?";
        String[] params = {String.valueOf(aluno.getId())};
        getDataBase().update(DataBaseOpenHelper.Alunos.TABELA,getContentValues(aluno),where,params);
    }

    public boolean isAluno(String telefone){
        String sql = "SELECT * from "+DataBaseOpenHelper.Alunos.TABELA+" WHERE "+
                DataBaseOpenHelper.Alunos.TELEFONE+" = ?";
        String[] params = new String[]{telefone};
        Cursor cursor = getDataBase().rawQuery(sql, params);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    private Aluno recuperaAluno(Cursor cursor) {
        Aluno aluno = new Aluno();
        aluno.setId(cursor.getLong(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.ID)));
        aluno.setNome(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.NOME)));
        aluno.setEndereco(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.ENDERECO)));
        aluno.setTelefone(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.TELEFONE)));
        aluno.setSite(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.SITE)));
        aluno.setNota(cursor.getDouble(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.NOTA)));
        aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.CAMINHO_FOTO)));
        return aluno;
    }

    private ContentValues getContentValues(Aluno aluno){
        ContentValues dados = new ContentValues();
        dados.put(DataBaseOpenHelper.Alunos.NOME, aluno.getNome());
        dados.put(DataBaseOpenHelper.Alunos.ENDERECO, aluno.getEndereco());
        dados.put(DataBaseOpenHelper.Alunos.TELEFONE, aluno.getTelefone());
        dados.put(DataBaseOpenHelper.Alunos.SITE, aluno.getSite());
        dados.put(DataBaseOpenHelper.Alunos.NOTA, aluno.getNota());
        dados.put(DataBaseOpenHelper.Alunos.CAMINHO_FOTO, aluno.getCaminhoFoto());
        return dados;
    }

    public void close(){
        getDataBase().close();
    }
}
