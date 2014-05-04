package com.olimposystems.grandespensadores.util;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.olimposystems.grandespensadores.type.GrandesPensadores;

public class AlarmHelper {

	// Repetir a cada dia
	private static final int tempoRepetir = 24 * 60 * 60 * 1000;

	public static void agendar(Activity activity) {

		// Intent para disparar o BroadcastReceiver
		Intent it = new Intent(GrandesPensadores.executarAlarme.value());
		PendingIntent p = PendingIntent.getBroadcast(activity,0,it,0);

		// Agenda o alarme
		AlarmManager alarme = (AlarmManager) activity.getSystemService(android.content.Context.ALARM_SERVICE);

		Calendar time = Calendar.getInstance();
		time.set(Calendar.HOUR_OF_DAY,Integer.parseInt(GrandesPensadores.horaDeNotificacao.value()));
		time.set(Calendar.MINUTE,00);
		time.set(Calendar.SECOND,0);

		// ajusta o alarme
		alarme.setRepeating(AlarmManager.RTC_WAKEUP,time.getTimeInMillis(),tempoRepetir,p);

		Log.i(GrandesPensadores.categoriaLog.value(),"Alarme agendado para :  " + time.getTime());

	}

}
