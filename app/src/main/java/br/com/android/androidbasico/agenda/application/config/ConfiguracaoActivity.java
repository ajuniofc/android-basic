package br.com.android.androidbasico.agenda.application.config;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.agenda.application.preferences.UserPreferences;

public class ConfiguracaoActivity extends AppCompatActivity {
    private EditText urlBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        urlBase = (EditText) findViewById(R.id.configuracao_url_base);
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
                saveURLBase();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveURLBase() {
        new UserPreferences(this).saveURLBase(urlBase.getText().toString());
    }
}
