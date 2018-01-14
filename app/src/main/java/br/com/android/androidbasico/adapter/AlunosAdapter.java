package br.com.android.androidbasico.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.application.listaAlunos.AlunoHolder;
import br.com.android.androidbasico.model.Aluno;

/**
 * Created by JHUNIIN on 12/01/2018.
 */

public class AlunosAdapter extends BaseAdapter{
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context,List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Aluno getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        AlunoHolder holder;
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_itens, parent, false);
            holder = new AlunoHolder(view);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (AlunoHolder) view.getTag();
        }

        showAluno(aluno, holder);

        return view;
    }

    private void showAluno(Aluno aluno, AlunoHolder holder) {
        holder.nome.setText(aluno.getNome());
        holder.telefone.setText(aluno.getTelefone());
        carregaImg(aluno, holder);
    }

    private void carregaImg(Aluno aluno, AlunoHolder holder) {
        String caminhoFoto = aluno.getCaminhoFoto();
        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            holder.foto.setImageBitmap(bitmapReduzido);
            holder.foto.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.foto.setTag(caminhoFoto);
        }
    }
}
