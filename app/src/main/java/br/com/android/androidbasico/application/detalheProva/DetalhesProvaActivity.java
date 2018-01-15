package br.com.android.androidbasico.application.detalheProva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.application.constant.Constantes;
import br.com.android.androidbasico.model.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {
    private TextView txtMateria, txtData;
    private ListView listaAlunos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Prova prova = (Prova) getIntent().getSerializableExtra(Constantes.PROVA);

        txtMateria = (TextView) findViewById(R.id.detalhe_prova_materiaId);
        txtData = (TextView) findViewById(R.id.detalhe_prova_dataId);
        listaAlunos = (ListView) findViewById(R.id.detalhe_prova_listaId);

        txtMateria.setText(prova.getMateria());
        txtData.setText(prova.getData());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getTopicos());
        listaAlunos.setAdapter(adapter);

    }
}
