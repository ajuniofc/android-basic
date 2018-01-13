package br.com.android.androidbasico.application.formAlunos;

import android.widget.EditText;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.model.Aluno;

/**
 * Created by JHUNIIN on 12/01/2018.
 */

public class FormHelper {
    private final EditText edtNome;
    private final EditText edtEndereco;
    private final EditText edtTelefone;
    private final EditText edtSite;
    private final EditText edtNota;

    public FormHelper(FormularioActivity activity) {
        edtNome = (EditText) activity.findViewById(R.id.formulario_nome);
        edtEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        edtTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        edtSite = (EditText) activity.findViewById(R.id.formulario_site);
        edtNota = (EditText) activity.findViewById(R.id.formulario_nome);
    }

    public Aluno getAluno(){
        Aluno aluno = new Aluno();
        aluno.setNome(edtNome.getText().toString());
        aluno.setEndereco(edtEndereco.getText().toString());
        aluno.setTelefone(edtTelefone.getText().toString());
        aluno.setSite(edtSite.getText().toString());
        aluno.setNota(Double.valueOf(edtNota.getText().toString()));
        return aluno;
    }
}
