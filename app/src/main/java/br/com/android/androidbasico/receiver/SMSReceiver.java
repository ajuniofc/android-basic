package br.com.android.androidbasico.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.android.androidbasico.R;
import br.com.android.androidbasico.database.AlunoDAO;

/**
 * Created by JHUNIIN on 14/01/2018.
 */

public class SMSReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        byte[] pdu = getPDU(intent);

        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        String telefone = getTelefone(sms);
        alertAluno(context,telefone);

    }

    private byte[] getPDU(Intent intent){
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        return (byte[]) pdus[0];
    }

    private void alertAluno(Context context, String telefone) {
        AlunoDAO dao = new AlunoDAO(context);
        if (dao.isAluno(telefone)){
            playSong(context);
        }
        dao.close();
    }

    private String getTelefone(SmsMessage sms) {
        String addressTelefone = sms.getDisplayOriginatingAddress();
        return addressTelefone.substring(5);
    }

    private void playSong(Context context) {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
        mp.start();
    }
}
