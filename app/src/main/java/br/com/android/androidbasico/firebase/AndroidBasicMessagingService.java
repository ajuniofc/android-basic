package br.com.android.androidbasico.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by JHUNIIN on 31/01/2018.
 */

public class AndroidBasicMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> notificacao = remoteMessage.getData();
        Log.d("Notificacao recebida", "=================================onMessageReceived: "+notificacao);

    }
}
