package br.com.android.androidbasico.agendaAPI.service;

import br.com.android.androidbasico.agendaAPI.api.AlunosAPI;
import br.com.android.androidbasico.agendaAPI.api.DispositivoAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JHUNIIN on 26/01/2018.
 */

public class RetrofitBuilder {
    private final Retrofit retrofit;

    public RetrofitBuilder(String urlBase) {

        retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient().build())
                .build();
    }

    public AlunosAPI getAlunoService() {
        return retrofit.create(AlunosAPI.class);
    }

    public DispositivoAPI getDispositivoService() {
        return retrofit.create(DispositivoAPI.class);
    }

    private OkHttpClient.Builder getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        return client;
    }

}
