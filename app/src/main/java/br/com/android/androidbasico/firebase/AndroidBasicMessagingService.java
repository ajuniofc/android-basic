package br.com.android.androidbasico.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import br.com.android.androidbasico.agenda.database.AlunoDAO;
import br.com.android.androidbasico.agenda.model.Aluno;
import br.com.android.androidbasico.agenda.sinc.AlunoSync;
import br.com.android.androidbasico.agendaAPI.dto.AlunoDTO;
import br.com.android.androidbasico.eventBus.AtualizaListaAlunosEvent;

/**
 * Created by JHUNIIN on 31/01/2018.
 */

public class AndroidBasicMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> notificacao = remoteMessage.getData();
        Log.d("Notificacao recebida", "=================================onMessageReceived: "+notificacao);

        converteParaAluno(notificacao);

    }

    private void converteParaAluno(Map<String, String> notificacao) {
        String chaveDeAcesso = "alunoSync";
        if (notificacao.containsKey("alunoSync")){
            String json = notificacao.get(chaveDeAcesso);
            Gson gson = new Gson();
            AlunoDTO alunoDTO = gson.fromJson(json, AlunoDTO.class);
            if (alunoDTO != null){
                new AlunoSync(AndroidBasicMessagingService.this).sincroniza(alunoDTO);
                EventBus.getDefault().post(new AtualizaListaAlunosEvent());
            }
        }
    }
}
