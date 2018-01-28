package br.com.android.androidbasico.agenda.application.listaAlunos;

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
    public final TextView endereco;
    public final TextView site;

    public AlunoHolder(View view) {
        foto     = (ImageView) view.findViewById(R.id.item_foto);
        nome     = (TextView) view.findViewById(R.id.item_nome);
        telefone = (TextView) view.findViewById(R.id.item_telefone);
        endereco = (TextView) view.findViewById(R.id.item_endereco);
        site = (TextView) view.findViewById(R.id.item_site);
    }
}
