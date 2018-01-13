package br.com.android.androidbasico.application.formAlunos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.application.constant.Constantes;
import br.com.android.androidbasico.database.AlunoDAO;
import br.com.android.androidbasico.model.Aluno;

public class FormularioActivity extends AppCompatActivity {
    private FormHelper formHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        formHelper = new FormHelper(this);
        Aluno aluno = (Aluno) getIntent().getSerializableExtra(Constantes.ALUNO);
        if(aluno != null){
            formHelper.preencherFormulario(aluno);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.formulario_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = formHelper.getAluno();
                AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
                dao.insere(aluno);
                dao.close();
                Toast.makeText(FormularioActivity.this,"Aluno "+aluno.getNome()+" Salvo",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
