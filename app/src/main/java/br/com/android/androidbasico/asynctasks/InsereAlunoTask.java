package br.com.android.androidbasico.asynctasks;

import android.os.AsyncTask;

import br.com.android.androidbasico.converter.AlunoConverter;
import br.com.android.androidbasico.model.Aluno;
import br.com.android.androidbasico.servico.WebClient;

/**
 * Created by JHUNIIN on 25/01/2018.
 */

public class InsereAlunoTask extends AsyncTask {
    private Aluno aluno;

    public InsereAlunoTask(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String json = new AlunoConverter().convertToCompleteJSON(getAluno());
        new WebClient().insere(json);
        return null;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
