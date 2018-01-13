package br.com.android.androidbasico.application.listaAlunos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.application.constant.Constantes;
import br.com.android.androidbasico.application.formAlunos.FormularioActivity;
import br.com.android.androidbasico.database.AlunoDAO;
import br.com.android.androidbasico.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    private Button mButaoAdicionar;
    private ListView listaAlunos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_listaId);
        listaAlunos.setOnItemClickListener(this);

        mButaoAdicionar = (Button) findViewById(R.id.lista_btn_adicionarId);
        mButaoAdicionar.setOnClickListener(this);

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem deletar = menu.add(R.string.lista_menu_contexto_deletar);
        deletar.setOnMenuItemClickListener(onMenuDeletarClickListener(aluno));

        MenuItem site = menu.add(R.string.lista_menu_contexto_site);
        site.setOnMenuItemClickListener(onMenuSiteClickListener(aluno));
    }

    private MenuItem.OnMenuItemClickListener onMenuSiteClickListener(final Aluno aluno) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intentSite = new Intent(Intent.ACTION_VIEW);

                String site = aluno.getSite();
                if (!site.startsWith("https://")) {
                    site = "https://" + site;
                }
                intentSite.setData(Uri.parse(site));
                startActivity(intentSite);
                return false;
            }
        };
    }

    @NonNull
    private MenuItem.OnMenuItemClickListener onMenuDeletarClickListener(final Aluno aluno) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();
                carregaLista();
                return false;
            }
        };
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lista_btn_adicionarId:
                goToFormulario();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
        goToFormulario(aluno);

    }

    private void goToFormulario(Aluno aluno) {
        Intent intentFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
        intentFormulario.putExtra(Constantes.ALUNO,aluno);
        startActivity(intentFormulario);
    }

    private void goToFormulario() {
        Intent intentFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
        startActivity(intentFormulario);
    }
}
