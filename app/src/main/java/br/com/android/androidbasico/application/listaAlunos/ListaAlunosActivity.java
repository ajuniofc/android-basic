package br.com.android.androidbasico.application.listaAlunos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.application.formAlunos.FormularioActivity;

public class ListaAlunosActivity extends AppCompatActivity {
    private Button mButaoAdicionar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        mButaoAdicionar = (Button) findViewById(R.id.lista_btn_adicionarId);
        mButaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentFormulario);
            }
        });
    }
}
