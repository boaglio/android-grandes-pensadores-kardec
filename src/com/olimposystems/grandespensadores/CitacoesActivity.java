package com.olimposystems.grandespensadores;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.olimposystems.grandespensadores.db.CitacaoRepositorio;
import com.olimposystems.grandespensadores.domain.Citacao;
import com.olimposystems.grandespensadores.domain.Config;
import com.olimposystems.grandespensadores.type.GrandesPensadores;
import com.olimposystems.grandespensadores.util.ArquivoUtil;

public class CitacoesActivity extends Activity {

	private NotificationManager notificationManager;
	private Notification myNotification;
	private CitacaoRepositorio citacaoRepositorio;
	private Button botaoMaisUma;
	private int codigoCitacao;
	private Config configGravada;
	private ArquivoUtil arquivoUtil;

	private static int contador = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citacao);
		setTitle(R.string.citacao);

		citacaoRepositorio = new CitacaoRepositorio(this);

		Citacao citacaoDoDia = buscaCitacaoDoDia();
		atualizaTela(citacaoDoDia);
		atualizaConfigProximaCitacao();

		// ajusta botao mais uma
		botaoMaisUma = (Button) findViewById(R.id.btnMaisUma);
		botaoMaisUma.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Citacao citacaoDoDia = buscaCitacaoDoDia();
				atualizaTela(citacaoDoDia);
				atualizaConfigProximaCitacao();

			}
		});

	}

	private void atualizaConfigProximaCitacao() {
		long proximoCodigo = citacaoRepositorio.getProximoCodigo(codigoCitacao);
		Log.e(GrandesPensadores.categoriaLog.value(),"proximoCodigo =  " + proximoCodigo);
		configGravada.setCodigoUltimaCitacao(String.valueOf(proximoCodigo));
		arquivoUtil.setConfig(configGravada);
		arquivoUtil.grava();
	}

	private void atualizaTela(Citacao citacaoDoDia) {
		String citacaoInteira = citacaoDoDia.getCitacao() + "\n\n   [ " + codigoCitacao + " de " + citacaoRepositorio.getTotalDeLinhas() + " ]";
		TextView text = (TextView) findViewById(R.id.textoCitacao);
		text.setText(citacaoInteira);
	}

	public void enviaNotificacao() {
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		myNotification = new Notification(R.drawable.ic_launcher,"Notification!",System.currentTimeMillis());
		Context context = getApplicationContext();
		String notificationTitle = "Grandes pensadores";
		String notificationText = "Frase do dia";
		PendingIntent pendingIntent = PendingIntent.getActivity(CitacoesActivity.this,0,null,Intent.FLAG_ACTIVITY_NEW_TASK);
		myNotification.defaults |= Notification.DEFAULT_SOUND;
		myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		myNotification.setLatestEventInfo(context,notificationTitle,notificationText,pendingIntent);
		notificationManager.notify(contador,myNotification);
	}

	private Citacao buscaCitacaoDoDia() {

		arquivoUtil = new ArquivoUtil(GrandesPensadores.arquivoDeconfiguracao.value(),this);
		configGravada = arquivoUtil.ler();
		codigoCitacao = 1;
		String ultimoCodigoLido = configGravada.getCodigoUltimaCitacao();
		if (ultimoCodigoLido != null && !ultimoCodigoLido.equals("null") && ultimoCodigoLido.length() > 0) {
			codigoCitacao = Integer.parseInt(ultimoCodigoLido);
		}
		Log.e(GrandesPensadores.categoriaLog.value(),"Lendo citacao " + codigoCitacao);

		return citacaoRepositorio.buscarCitacao(codigoCitacao);

	}

}
