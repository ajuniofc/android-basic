package br.com.android.androidbasico.agendaAPI.api;

import java.util.List;

import br.com.android.androidbasico.agenda.model.Aluno;
import br.com.android.androidbasico.agendaAPI.dto.AlunoDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by JHUNIIN on 26/01/2018.
 */

public interface AlunosAPI {

    @GET("aluno")
    Call<AlunoDTO> lista();

    @POST("aluno")
    Call<Void> insere(@Body Aluno aluno);

    @DELETE("aluno/{id}")
    Call<Void> deleta(@Path("id") String id);

    @GET("aluno/diff")
    Call<AlunoDTO> novos(@Header("datahora") String versao);
}
