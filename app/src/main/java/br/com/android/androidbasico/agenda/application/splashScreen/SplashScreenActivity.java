package br.com.android.androidbasico.agenda.application.splashScreen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.agenda.application.config.ConfiguracaoActivity;
import br.com.android.androidbasico.agenda.application.listaAlunos.ListaAlunosActivity;
import br.com.android.androidbasico.agenda.application.preferences.UserPreferences;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String urlBase = new UserPreferences(this).getUrlBase();
        if (urlBase == null || urlBase.isEmpty()){
            startActivity(new Intent(this, ConfiguracaoActivity.class));
        }else {
            startActivity(new Intent(this, ListaAlunosActivity.class));
        }
        finish();
    }
}
