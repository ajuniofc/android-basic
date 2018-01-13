package br.com.android.androidbasico.application.listaAlunos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.application.formAlunos.FormularioActivity;
import br.com.android.androidbasico.database.AlunoDAO;
import br.com.android.androidbasico.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {
    private Button mButaoAdicionar;
    private ListView listaAlunos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_listaId);

        mButaoAdicionar = (Button) findViewById(R.id.lista_btn_adicionarId);
        mButaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentFormulario);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista(){
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this,android.R.layout.simple_list_item_1, buscaAlunos());
        listaAlunos.setAdapter(adapter);
    }

    private List<Aluno> buscaAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscarAlunos();
        dao.close();
        return alunos;
    }
}
