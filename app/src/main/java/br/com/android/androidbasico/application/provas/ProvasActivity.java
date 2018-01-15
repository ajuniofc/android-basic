package br.com.android.androidbasico.application.provas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.model.Prova;

public class ProvasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        List<String> topicosPort = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        Prova provaPortugues = new Prova("Portugues", "25/05/2016", topicosPort);

        List<String> topicosMat = Arrays.asList("Equacoes de segundo grau", "Trigonometria");
        Prova provaMatematica = new Prova("Matematica", "27/05/2016", topicosMat);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provas);
        lista = (ListView) findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Prova prova = (Prova) lista.getItemAtPosition(position);
        Toast.makeText(this,"Prova de "+prova.getMateria(),Toast.LENGTH_SHORT).show();
    }
}
