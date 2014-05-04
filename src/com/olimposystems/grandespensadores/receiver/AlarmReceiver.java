package com.olimposystems.grandespensadores.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.olimposystems.grandespensadores.CitacaoActivity;
import com.olimposystems.grandespensadores.R;
import com.olimposystems.grandespensadores.domain.Config;
import com.olimposystems.grandespensadores.type.FlagSimNao;
import com.olimposystems.grandespensadores.type.GrandesPensadores;
import com.olimposystems.grandespensadores.util.ArquivoUtil;

/**
 * Classe responsavel por agendar a notificacao
 * 
 * @author Fernando Boaglio
 */
public class AlarmReceiver extends BroadcastReceiver {

	private NotificationManager notificationManager;
	private Notification myNotification;
	private static final String FRASE_DO_DIA = "Frase do dia";

	private static int contador = 0;

	@Override
	public void onReceive(Context context,Intent intent) {

		Log.e(GrandesPensadores.categoriaLog.value(),"chamando AlarmReceiver...");
		ArquivoUtil arquivoUtil = new ArquivoUtil(GrandesPensadores.arquivoDeconfiguracao.value(),context);
		Config configGravada = arquivoUtil.ler();

		if (configGravada.getNotifica() != null && configGravada.getNotifica().contains(FlagSimNao.sim.value())) {

			Log.e(GrandesPensadores.categoriaLog.value(),"flag ativa, chamando notificacao...");

			notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			myNotification = new Notification(R.drawable.ic_launcher,"Notification!",System.currentTimeMillis());
			String notificationTitle = GrandesPensadores.autor.value();
			String notificationText = FRASE_DO_DIA;
			Intent notificationIntent = new Intent(context,CitacaoActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,Intent.FLAG_ACTIVITY_NEW_TASK);
			myNotification.defaults |= Notification.DEFAULT_SOUND;
			myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
			myNotification.setLatestEventInfo(context,notificationTitle,notificationText,pendingIntent);
			notificationManager.notify(contador,myNotification);

		} else {

			Log.e(GrandesPensadores.categoriaLog.value(),"flag inativa, ignorando notificacao...");

		}

	}
}
