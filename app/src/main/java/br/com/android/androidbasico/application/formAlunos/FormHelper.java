package br.com.android.androidbasico.application.formAlunos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.model.Aluno;

/**
 * Created by JHUNIIN on 12/01/2018.
 */

public class FormHelper {
    private final EditText edtNome;
    private final EditText edtEndereco;
    private final EditText edtTelefone;
    private final EditText edtSite;
    private final RatingBar edtNota;
    private final ImageView imgFoto;

    private Aluno aluno;

    public FormHelper(FormularioActivity activity) {
        edtNome = (EditText) activity.findViewById(R.id.formulario_nome);
        edtEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        edtTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        edtSite = (EditText) activity.findViewById(R.id.formulario_site);
        edtNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        imgFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        aluno = new Aluno();
    }

    public Aluno getAluno(){
        aluno.setNome(edtNome.getText().toString());
        aluno.setEndereco(edtEndereco.getText().toString());
        aluno.setTelefone(edtTelefone.getText().toString());
        aluno.setSite(edtSite.getText().toString());
        aluno.setNota(Double.valueOf(edtNota.getProgress()));
        aluno.setCaminhoFoto((String) imgFoto.getTag());
        return aluno;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void preencherFormulario(Aluno aluno) {
        edtNome.setText(aluno.getNome());
        edtEndereco.setText(aluno.getEndereco());
        edtTelefone.setText(aluno.getTelefone());
        edtSite.setText(aluno.getSite());
        edtNota.setProgress(aluno.getNota().intValue());
        carregaFoto(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void carregaFoto(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            imgFoto.setImageBitmap(bitmapReduzido);
            imgFoto.setBackground(null);
            imgFoto.setTag(caminhoFoto);
        }
    }
}
