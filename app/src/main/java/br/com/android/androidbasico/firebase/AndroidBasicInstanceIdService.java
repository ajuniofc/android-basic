package br.com.android.androidbasico.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.android.androidbasico.agenda.application.preferences.UserPreferences;
import br.com.android.androidbasico.agendaAPI.service.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JHUNIIN on 30/01/2018.
 */

public class AndroidBasicInstanceIdService extends FirebaseInstanceIdService implements Callback<Void> {
    private String URL = "http://192.168.0.11:8080/api/";
    private String token;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("onTokenRefresh", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        enviaTokenParaServidor(refreshedToken);
    }

    private void enviaTokenParaServidor(String token) {
        this.token = token;
        Call<Void> call = new RetrofitBuilder(URL).getDispositivoService().enviaToken(token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()){
            Log.i("Envia Token", "onResponse: "+ token);
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Log.e("Envia Token", "onFailure: "+t.getMessage());
    }
}
