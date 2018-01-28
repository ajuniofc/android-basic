package br.com.android.androidbasico.agenda.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.agendaAPI.converter.AlunoConverter;
import br.com.android.androidbasico.agenda.database.AlunoDAO;
import br.com.android.androidbasico.agenda.model.Aluno;
import br.com.android.androidbasico.agendaAPI.servico.WebClient;

/**
 * Created by JHUNIIN on 14/01/2018.
 */

public class EnviarAlunosTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog progressDialog;

    public EnviarAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context,
                context.getString(R.string.lista_aguarde),
                context.getString(R.string.lista_enviando_notas),true,true);
    }

    @Override
    protected String doInBackground(Void... params) {
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
        progressDialog.dismiss();
        if (resposta != null){
            Toast.makeText(context, resposta,Toast.LENGTH_SHORT).show();
        }
    }
}
