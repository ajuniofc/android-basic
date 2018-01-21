package br.com.android.androidbasico.application.provas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.application.constant.Constantes;
import br.com.android.androidbasico.model.Prova;

public class DetalhesProvaFragment extends Fragment {
    private TextView txtMateria, txtData;
    private ListView listaAlunos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        Prova prova = getProvaFromArgs();

        initViews(view);

        populoProva(prova);

        return view;
    }

    private Prova getProvaFromArgs() {
        Bundle bundle = getArguments();
        if (bundle == null){
            return null;
        }

        return (Prova)bundle.getSerializable("Prova");
    }

    private void initViews(View view) {
        txtMateria = (TextView) view.findViewById(R.id.detalhe_prova_materiaId);
        txtData = (TextView) view.findViewById(R.id.detalhe_prova_dataId);
        listaAlunos = (ListView) view.findViewById(R.id.detalhe_prova_listaId);
    }

    public void populoProva(Prova prova){
        if (prova == null){
            return;
        }
        txtMateria.setText(prova.getMateria());
        txtData.setText(prova.getData());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());
        listaAlunos.setAdapter(adapter);
    }

}
