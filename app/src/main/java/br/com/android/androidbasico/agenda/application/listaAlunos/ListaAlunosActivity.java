package br.com.android.androidbasico.agenda.application.listaAlunos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.agenda.adapter.AlunosAdapter;
import br.com.android.androidbasico.agenda.application.config.ConfiguracaoActivity;
import br.com.android.androidbasico.agenda.application.constant.Constantes;
import br.com.android.androidbasico.agenda.application.formAlunos.FormularioActivity;
import br.com.android.androidbasico.agenda.application.mapa.MapaActivity;
import br.com.android.androidbasico.agenda.application.preferences.UserPreferences;
import br.com.android.androidbasico.agenda.application.provas.ProvasActivity;
import br.com.android.androidbasico.agenda.asynctasks.EnviarAlunosTask;
import br.com.android.androidbasico.agenda.database.AlunoDAO;
import br.com.android.androidbasico.agenda.model.Aluno;
import br.com.android.androidbasico.agenda.sinc.AlunoSync;
import br.com.android.androidbasico.agendaAPI.service.RetrofitBuilder;
import br.com.android.androidbasico.eventBus.AtualizaListaAlunosEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAlunosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final int PERMISSION_CAMERA_CODE = 123;
    private AlunoSync alunoSync;
    private Button mButaoAdicionar;
    private ListView listaAlunos;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_listaId);
        listaAlunos.setOnItemClickListener(this);

        mButaoAdicionar = (Button) findViewById(R.id.lista_btn_adicionarId);
        mButaoAdicionar.setOnClickListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.lista_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        alunoSync = new AlunoSync(this);
        alunoSync.sincronizaAlunos();
        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void atualizaListaAlunoEvent(AtualizaListaAlunosEvent event){
        finalizaRefreshing();
        carregaLista();
    }

    private void finalizaRefreshing() {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_lista_enviar:
                EnviarAlunosTask alunosTask = new EnviarAlunosTask(this);
                alunosTask.execute();
                break;
            case R.id.menu_baixar_provas:
                Intent intentProvas = new Intent(this, ProvasActivity.class);
                startActivity(intentProvas);
                break;
            case R.id.menu_mapa:
                Intent intentMapa = new Intent(this, MapaActivity.class);
                startActivity(intentMapa);
                break;
            case R.id.menu_configuracao:
                startActivity(new Intent(this, ConfiguracaoActivity.class));
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
                String urlBase = new UserPreferences(ListaAlunosActivity.this).getUrlBase();
                Call<Void> call = new RetrofitBuilder(urlBase).getAlunoService().deleta(aluno.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            deletaAlunodoBanco(aluno);
                            carregaLista();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ListaAlunosActivity.this,"Não foi possivel remover o aluno",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        };
    }

    private void deletaAlunodoBanco(Aluno aluno) {
        AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
        dao.deleta(aluno);
        dao.close();
    }

    private void carregaLista(){
        AlunosAdapter adapter = new AlunosAdapter(this,buscaAlunos());
        listaAlunos.setAdapter(adapter);
    }

    private List<Aluno> buscaAlunos() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscarAlunos();
        for (Aluno aluno :
                alunos) {
            Log.i("aluno sincronizado", String.valueOf(aluno.getSincronizado()));
        }
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

    @Override
    public void onRefresh() {
        alunoSync.sincronizaAlunos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
