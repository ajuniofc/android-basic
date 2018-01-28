package br.com.android.androidbasico.agendaAPI.dto;

import java.util.List;

import br.com.android.androidbasico.agenda.model.Aluno;

/**
 * Created by JHUNIIN on 27/01/2018.
 */

public class AlunoDTO {
    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
