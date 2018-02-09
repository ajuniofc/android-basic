package br.com.android.androidbasico.agenda.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.android.androidbasico.agenda.model.Aluno;

/**
 * Created by JHUNIIN on 12/01/2018.
 */

public class AlunoDAO {
    private DataBaseOpenHelper banco;
    private SQLiteDatabase db;

    public AlunoDAO(Context context) {
        this.banco = new DataBaseOpenHelper(context);
    }

    public AlunoDAO(SQLiteDatabase db) {
        this.db = db;
    }

    private SQLiteDatabase getDataBase(){
        if (db == null){
            db = banco.getWritableDatabase();
        }
        return db;
    }

    public void insere(Aluno aluno){
        if (aluno.getId() == null){
            aluno.setId(getUUID());
        }
        getDataBase().insert(DataBaseOpenHelper.Alunos.TABELA, null, getContentValues(aluno));
    }

    public void sincroniza(List<Aluno> alunos){
        for (Aluno aluno : alunos) {
            aluno.sincroniza();
            if (existe(aluno)){
                if (aluno.estaDesativado()){
                    deleta(aluno);
                }else {
                    atualiza(aluno);
                }
            }else if(!aluno.estaDesativado()){
                insere(aluno);
            }
        }
    }

    private boolean existe(Aluno aluno) {
        String existe = "SELECT "+DataBaseOpenHelper.Alunos.ID+" FROM Alunos "
                +"WHERE "+DataBaseOpenHelper.Alunos.ID+"=? LIMIT 1";
        Cursor cursor = getDataBase().rawQuery(existe, new String[]{aluno.getId()});
        int quantidade = cursor.getCount();
        cursor.close();
        return quantidade > 0;
    }

    public List<Aluno> buscarAlunos() {
        String sql = "SELECT * FROM "+DataBaseOpenHelper.Alunos.TABELA+" WHERE "+
                DataBaseOpenHelper.Alunos.DESATIVADO + " = 0";
        Cursor cursor = getDataBase().rawQuery(sql, null);
        return recuperaAlunos(cursor);
    }

    public void deleta(Aluno aluno) {
        String where = DataBaseOpenHelper.Alunos.ID+" = ?";
        String[] params = {String.valueOf(aluno.getId())};
        if (aluno.estaDesativado()) {
            getDataBase().delete(DataBaseOpenHelper.Alunos.TABELA, where, params);
        }else {
            aluno.desativa();
            atualiza(aluno);
        }
    }

    public void atualiza(Aluno aluno) {
        String where = DataBaseOpenHelper.Alunos.ID+" = ?";
        String[] params = {aluno.getId()};
        getDataBase().update(DataBaseOpenHelper.Alunos.TABELA,getContentValues(aluno),where,params);
    }

    public void atualiza(Aluno aluno, String uuid) {
        String where = DataBaseOpenHelper.Alunos.ID+" = ?";
        String[] params = {aluno.getId()};
        ContentValues values = getContentValues(aluno);
        values.put(DataBaseOpenHelper.Alunos.ID,uuid);
        getDataBase().update(DataBaseOpenHelper.Alunos.TABELA,values,where,params);
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

    public List<Aluno> buscaAlunosNaoSincronizados(){
        String sql = "SELECT * FROM "+DataBaseOpenHelper.Alunos.TABELA+" WHERE "+
                DataBaseOpenHelper.Alunos.SINCRONIZADO+" = 0";
        Cursor cursor = getDataBase().rawQuery(sql, null);
        return recuperaAlunos(cursor);
    }

    private List<Aluno> recuperaAlunos(Cursor cursor){
        List<Aluno> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Aluno aluno = recuperaAluno(cursor);
            list.add(aluno);
        }
        cursor.close();
        return list;
    }

    private Aluno recuperaAluno(Cursor cursor) {
        Aluno aluno = new Aluno();
        aluno.setId(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.ID)));
        aluno.setNome(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.NOME)));
        aluno.setEndereco(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.ENDERECO)));
        aluno.setTelefone(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.TELEFONE)));
        aluno.setSite(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.SITE)));
        aluno.setNota(cursor.getDouble(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.NOTA)));
        aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.CAMINHO_FOTO)));
        aluno.setSincronizado(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.SINCRONIZADO)));
        aluno.setDesativado(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.Alunos.DESATIVADO)));
        return aluno;
    }

    private ContentValues getContentValues(Aluno aluno){
        ContentValues dados = new ContentValues();
        dados.put(DataBaseOpenHelper.Alunos.ID, aluno.getId());
        dados.put(DataBaseOpenHelper.Alunos.NOME, aluno.getNome());
        dados.put(DataBaseOpenHelper.Alunos.ENDERECO, aluno.getEndereco());
        dados.put(DataBaseOpenHelper.Alunos.TELEFONE, aluno.getTelefone());
        dados.put(DataBaseOpenHelper.Alunos.SITE, aluno.getSite());
        dados.put(DataBaseOpenHelper.Alunos.NOTA, aluno.getNota());
        dados.put(DataBaseOpenHelper.Alunos.CAMINHO_FOTO, aluno.getCaminhoFoto());
        dados.put(DataBaseOpenHelper.Alunos.SINCRONIZADO, aluno.getSincronizado());
        dados.put(DataBaseOpenHelper.Alunos.DESATIVADO, aluno.getDesativado());
        return dados;
    }

    public String getUUID(){
        return UUID.randomUUID().toString();
    }

    public void close(){
        getDataBase().close();
    }
}
