package com.olimposystems.grandespensadores;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.olimposystems.grandespensadores.domain.Config;
import com.olimposystems.grandespensadores.type.FlagSimNao;
import com.olimposystems.grandespensadores.type.GrandesPensadores;
import com.olimposystems.grandespensadores.util.ArquivoUtil;

public class ConfigActivity extends Activity {

	private Button btnSubmit;
	private CheckBox cbxBoxNotificarFrase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Intent i = new Intent(getApplicationContext(),NotificacaoActivity.class);
		// startActivity(i);

		setTitle(R.string.config);
		setContentView(R.layout.config);

		cbxBoxNotificarFrase = (CheckBox) findViewById(R.id.cbxBoxNotificarFrase);

		// busca config gravada
		ArquivoUtil arquivoUtil = new ArquivoUtil(GrandesPensadores.arquivoDeconfiguracao.value(),this);
		Config configGravada = arquivoUtil.ler();
		Log.e(GrandesPensadores.categoriaLog.value(),"config buscada=" + configGravada);
		if (configGravada != null) {
			if (configGravada.getNotifica() != null && configGravada.getNotifica().contains(FlagSimNao.sim.value())) {

				cbxBoxNotificarFrase.setChecked(true);

			}
		}

		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		btnSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (GrandesPensadores.debugAtivo.value().equals("s")) {
					Toast.makeText(ConfigActivity.this,"OnClickListener : " + "\n notifica?: " + cbxBoxNotificarFrase.isChecked(),Toast.LENGTH_SHORT).show();
				}

				String notifica = FlagSimNao.nao.value();
				if (cbxBoxNotificarFrase.isChecked()) {
					notifica = FlagSimNao.sim.value();
				}

				Config config = new Config();
				config.setNotifica(notifica);
				salvarConfig(config);

				Toast.makeText(ConfigActivity.this,"Configuração gravada com sucesso.",Toast.LENGTH_SHORT).show();

				ConfigActivity.this.finish();
			}

		});
	}

	protected void salvarConfig(Config c) {

		ArquivoUtil arquivoUtil = new ArquivoUtil(GrandesPensadores.arquivoDeconfiguracao.value(),this,c);
		arquivoUtil.grava();

	}
}
