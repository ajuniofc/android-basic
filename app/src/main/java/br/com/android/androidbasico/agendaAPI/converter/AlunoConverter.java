package br.com.android.androidbasico.agendaAPI.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.android.androidbasico.agenda.model.Aluno;

/**
 * Created by JHUNIIN on 14/01/2018.
 */

public class AlunoConverter {
    private List<Aluno> alunos;

    public AlunoConverter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public AlunoConverter() {
    }

    public String convertToJSON(){
        JSONStringer js = new JSONStringer();
        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : getAlunos()) {
                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public String convertToCompleteJSON(Aluno aluno) {
        JSONStringer js = new JSONStringer();
        try {
            js.object()
                .key("id").value(aluno.getId())
                .key("nome").value(aluno.getNome())
                .key("endereco").value(aluno.getEndereco())
                .key("telefone").value(aluno.getTelefone())
                .key("site").value(aluno.getSite())
                .key("nota").value(aluno.getNota())
                .key("caminhoFoto").value(aluno.getCaminhoFoto())
                .endObject();
            return js.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
