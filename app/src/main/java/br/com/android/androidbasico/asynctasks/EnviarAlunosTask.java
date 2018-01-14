package br.com.android.androidbasico.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.converter.AlunoConverter;
import br.com.android.androidbasico.database.AlunoDAO;
import br.com.android.androidbasico.model.Aluno;
import br.com.android.androidbasico.servico.WebClient;

/**
 * Created by JHUNIIN on 14/01/2018.
 */

public class EnviarAlunosTask extends AsyncTask<Object, Object, String> {
    private Context context;

    public EnviarAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        Toast.makeText(context, R.string.lista_enviando_notas,Toast.LENGTH_SHORT).show();

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscarAlunos();
        dao.close();

        AlunoConverter converter = new AlunoConverter(alunos);
        String json = converter.convertToJSON();
        WebClient client = new WebClient();

        return client.post(json);
    }

    @Override
    protected void onPostExecute(String resposta) {
        if (resposta != null){
            Toast.makeText(context, resposta,Toast.LENGTH_SHORT).show();
        }
    }
}
