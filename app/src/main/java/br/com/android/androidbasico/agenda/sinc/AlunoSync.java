package br.com.android.androidbasico.agenda.sinc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.net.SocketTimeoutException;
import java.util.List;

import br.com.android.androidbasico.agenda.application.listaAlunos.ListaAlunosActivity;
import br.com.android.androidbasico.agenda.application.preferences.UserPreferences;
import br.com.android.androidbasico.agenda.database.AlunoDAO;
import br.com.android.androidbasico.agenda.model.Aluno;
import br.com.android.androidbasico.agendaAPI.dto.AlunoDTO;
import br.com.android.androidbasico.agendaAPI.service.RetrofitBuilder;
import br.com.android.androidbasico.eventBus.AtualizaListaAlunosEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoSync {
    private final Context context;
    private EventBus eventBus;
    private UserPreferences preferences;

    public AlunoSync(Context context) {
        this.context = context;
        this.eventBus = EventBus.getDefault();
        this.preferences = new UserPreferences(context);
    }

    public void sincronizaAlunos(){
        if (preferences.temVersao()){
            buscaNovos();
        }else {
            buscaTodos();
        }
    }

    private void buscaNovos() {
        String urlBase = preferences.getUrlBase();
        String versao = preferences.getVersao();
        Call<AlunoDTO> call = new RetrofitBuilder(urlBase).getAlunoService().novos(versao);
        call.enqueue(buscaAlunosCallback());
    }

    private void buscaTodos() {
        String urlBase = preferences.getUrlBase();
        Call<AlunoDTO> call = new RetrofitBuilder(urlBase).getAlunoService().lista();
        call.enqueue(buscaAlunosCallback());
    }

    @NonNull
    private Callback<AlunoDTO> buscaAlunosCallback() {
        return new Callback<AlunoDTO>() {
            @Override
            public void onResponse(Call<AlunoDTO> call, Response<AlunoDTO> response) {
                AlunoDTO alunoDTO = response.body();
                if (alunoDTO != null) {
                    String versao = alunoDTO.getMomentoDaUltimaModificacao();

                    UserPreferences preferences = new UserPreferences(context);
                    preferences.saveVersao(versao);
                    Log.d("PREFERENCES", "onResponse: "+preferences.getVersao());

                    AlunoDAO dao = new AlunoDAO(context);
                    dao.sincroniza(alunoDTO.getAlunos());
                    dao.close();
                }
                atualizaLista();
                sincronizaAlunosInternos();
            }

            @Override
            public void onFailure(Call<AlunoDTO> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Log.e("onFailure: ", "Tempo de requisição expirou");
                }
                atualizaLista();
            }
        };
    }

    private void sincronizaAlunosInternos(){
        final AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunosNaoSincronizados();
        Call<AlunoDTO> call = new RetrofitBuilder(preferences.getUrlBase()).getAlunoService().atualiza(alunos);
        call.enqueue(new Callback<AlunoDTO>() {
            @Override
            public void onResponse(Call<AlunoDTO> call, Response<AlunoDTO> response) {
                AlunoDTO alunoDTO = response.body();
                dao.sincroniza(alunoDTO.getAlunos());
                dao.close();
            }

            @Override
            public void onFailure(Call<AlunoDTO> call, Throwable t) {

            }
        });
    }

    private void atualizaLista() {
        eventBus.post(new AtualizaListaAlunosEvent());
    }

    public void deleta(final Aluno aluno) {
        String urlBase = new UserPreferences(context).getUrlBase();
        Call<Void> call = new RetrofitBuilder(urlBase).getAlunoService().deleta(aluno.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    AlunoDAO dao = new AlunoDAO(context);
                    dao.deleta(aluno);
                    dao.close();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}