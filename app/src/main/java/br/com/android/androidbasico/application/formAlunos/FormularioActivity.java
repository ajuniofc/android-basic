package br.com.android.androidbasico.application.formAlunos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.android.androidbasico.R;

public class FormularioActivity extends AppCompatActivity {
    private Button mBotaoSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        mBotaoSalvar = (Button) findViewById(R.id.formulario_salvar);
        mBotaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FormularioActivity.this,"Evento de click",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
