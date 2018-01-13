package br.com.android.androidbasico.application.formAlunos;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.application.constant.Constantes;
import br.com.android.androidbasico.database.AlunoDAO;
import br.com.android.androidbasico.model.Aluno;

public class FormularioActivity extends AppCompatActivity implements View.OnClickListener {
    private FormHelper formHelper;
    private Button btnFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        formHelper = new FormHelper(this);
        Aluno aluno = (Aluno) getIntent().getSerializableExtra(Constantes.ALUNO);
        if(aluno != null){
            formHelper.preencherFormulario(aluno);
        }

        btnFoto = (Button) findViewById(R.id.formulario_btn_foto);
        btnFoto.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.formulario_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = formHelper.getAluno();
                salva(aluno);
                Toast.makeText(FormularioActivity.this,aluno.getNome()+" Salvo",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salva(Aluno aluno) {
        AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
        if (aluno.getId() != null){
            dao.atualiza(aluno);
        }else {
            dao.insere(aluno);
        }
        dao.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.formulario_btn_foto:
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String caminhoFoto = getExternalFilesDir(null) + "/"+ System.currentTimeMillis() +".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                break;
        }
    }
}
