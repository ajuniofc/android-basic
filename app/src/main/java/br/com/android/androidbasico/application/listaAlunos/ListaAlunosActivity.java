package br.com.android.androidbasico.application.listaAlunos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.adapter.AlunosAdapter;
import br.com.android.androidbasico.application.constant.Constantes;
import br.com.android.androidbasico.application.formAlunos.FormularioActivity;
import br.com.android.androidbasico.asynctasks.EnviarAlunosTask;
import br.com.android.androidbasico.converter.AlunoConverter;
import br.com.android.androidbasico.database.AlunoDAO;
import br.com.android.androidbasico.model.Aluno;
import br.com.android.androidbasico.servico.WebClient;

public class ListaAlunosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    public static final int PERMISSION_CAMERA_CODE = 123;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_alunos_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_lista_enviar:
                EnviarAlunosTask alunosTask = new EnviarAlunosTask(this);
                alunosTask.execute();
                break;
            case R.id.menu_baixar_provas:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem deletar = menu.add(R.string.lista_menu_contexto_deletar);
        deletar.setOnMenuItemClickListener(onMenuDeletarClickListener(aluno));

        MenuItem site = menu.add(R.string.lista_menu_contexto_site);
        site.setOnMenuItemClickListener(onMenuSiteClickListener(aluno));

        MenuItem ligar = menu.add(R.string.lista_menu_contexto_ligar);
        ligar.setOnMenuItemClickListener(onMenuLigarClickListener(aluno));

        MenuItem sms = menu.add(R.string.lista_menu_contexto_sms);
        sms.setOnMenuItemClickListener(onMenuSMSClickListener(aluno));

        MenuItem mapa = menu.add(R.string.lista_menu_contexto_mapa);
        mapa.setOnMenuItemClickListener(onMenuMapaClickListener(aluno));
    }

    private MenuItem.OnMenuItemClickListener onMenuLigarClickListener(final Aluno aluno) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CAMERA_CODE);
                }else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_CAMERA_CODE:

                break;
        }
    }

    private MenuItem.OnMenuItemClickListener onMenuMapaClickListener(final Aluno aluno) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intentMapa = new Intent(Intent.ACTION_VIEW);
                intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
                startActivity(intentMapa);
                return false;
            }
        };
    }

    private MenuItem.OnMenuItemClickListener onMenuSMSClickListener(final Aluno aluno) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intentSMS = new Intent(Intent.ACTION_VIEW) ;
                intentSMS.setData(Uri.parse("sms:"+aluno.getTelefone()));
                startActivity(intentSMS);
                return false;
            }
        };
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
        AlunosAdapter adapter = new AlunosAdapter(this,buscaAlunos());
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
