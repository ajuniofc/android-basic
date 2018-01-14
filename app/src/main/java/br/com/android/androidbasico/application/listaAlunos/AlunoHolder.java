package br.com.android.androidbasico.application.listaAlunos;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.android.androidbasico.R;

/**
 * Created by JHUNIIN on 14/01/2018.
 */

public class AlunoHolder {
    public final ImageView foto;
    public final TextView nome;
    public final TextView telefone;

    public AlunoHolder(View view) {
        foto     = (ImageView) view.findViewById(R.id.item_foto);
        nome     = (TextView) view.findViewById(R.id.item_nome);
        telefone = (TextView) view.findViewById(R.id.item_telefone);
    }
}
