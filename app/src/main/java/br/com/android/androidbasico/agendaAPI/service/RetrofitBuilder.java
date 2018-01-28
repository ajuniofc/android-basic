package br.com.android.androidbasico.agendaAPI.service;

import br.com.android.androidbasico.agendaAPI.api.AlunosAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JHUNIIN on 26/01/2018.
 */

public class RetrofitBuilder {
    private final Retrofit retrofit;

    public RetrofitBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.11:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient().build())
                .build();
    }

    public AlunosAPI getAlunoService() {
        return retrofit.create(AlunosAPI.class);
    }

    private OkHttpClient.Builder getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        return client;
    }
}
