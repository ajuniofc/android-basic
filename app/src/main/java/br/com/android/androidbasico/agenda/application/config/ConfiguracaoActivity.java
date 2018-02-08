package br.com.android.androidbasico.agenda.application.config;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.agenda.application.listaAlunos.ListaAlunosActivity;
import br.com.android.androidbasico.agenda.application.preferences.UserPreferences;

public class ConfiguracaoActivity extends AppCompatActivity {
    private EditText urlBase;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        urlBase = (EditText) findViewById(R.id.configuracao_url_base);
        String urlBase = new UserPreferences(this).getUrlBase();
        this.urlBase.setText(urlBase);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.configuracao_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_configuracao_ok:
                url = urlBase.getText().toString();
                if (validaUrl(url)){
                    String urlBase = new UserPreferences(this).getUrlBase();
                    saveURLBase();
                    if (urlBase == null || urlBase.isEmpty()){
                        startActivity(new Intent(this, ListaAlunosActivity.class));
                    }
                    finish();
                }else {
                    mostraAlertaDeErro();
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void mostraAlertaDeErro() {
        urlBase.setError("URL inválida!");
    }

    private boolean validaUrl(String url) {
        Pattern urlPattern = Pattern.compile(Patterns.WEB_URL.pattern());
        Matcher matcher = urlPattern.matcher(url);
        if (!matcher.find()){
            return false;
        }
        return true;
    }


    private void saveURLBase() {
        new UserPreferences(this).saveURLBase(url);
    }
}
