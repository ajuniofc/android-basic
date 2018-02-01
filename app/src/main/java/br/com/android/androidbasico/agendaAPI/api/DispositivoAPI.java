package br.com.android.androidbasico.agendaAPI.api;

import retrofit2.Call;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by JHUNIIN on 31/01/2018.
 */

public interface DispositivoAPI {

    @POST("firebase/dispositivo")
    Call<Void> enviaToken(@Header("token") String token);

}
