package br.com.android.androidbasico.agenda.application.provas;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.agenda.model.Prova;

public class ProvasActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.frame_principal, new ListaProvaFragment());
        if (isModoPaisagem()) {
            transaction.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        transaction.commit();
    }

    private boolean isModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (isModoPaisagem()) {
            DetalhesProvaFragment detalhesProvaFragmente = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            detalhesProvaFragmente.populoProva(prova);
        }else {
            DetalhesProvaFragment fragment = new DetalhesProvaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Prova", prova);
            fragment.setArguments(bundle);
            transaction.replace(R.id.frame_principal, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
