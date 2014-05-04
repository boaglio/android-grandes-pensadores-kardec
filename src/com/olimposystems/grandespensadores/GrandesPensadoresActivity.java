package com.olimposystems.grandespensadores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.olimposystems.grandespensadores.type.GrandesPensadores;
import com.olimposystems.grandespensadores.util.AlarmHelper;

public class GrandesPensadoresActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal_layout);

		Button botaoConfig = (Button) findViewById(R.id.botao_config);
		botaoConfig.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Log.i(GrandesPensadores.categoriaLog.value(),"chamando Config");
				Intent i = new Intent(getApplicationContext(),ConfigActivity.class);
				startActivity(i);

			}
		});

		Button botaoCitacoes = (Button) findViewById(R.id.botao_citacoes);
		botaoCitacoes.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Log.i(GrandesPensadores.categoriaLog.value(),"chamando Citacoes");
				Intent i = new Intent(getApplicationContext(),CitacoesActivity.class);
				// Intent i = new Intent(getApplicationContext(),CitacaoActivity.class);
				startActivity(i);
			}
		});

		Button botaoSair = (Button) findViewById(R.id.botao_saida);
		botaoSair.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Log.i(GrandesPensadores.categoriaLog.value(),"chamando Home");
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);

			}
		});

		Log.i(GrandesPensadores.categoriaLog.value(),"tela principal Ok");

		AlarmHelper.agendar(this);

		Log.i(GrandesPensadores.categoriaLog.value(),"Agendamento Ok");

	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem item = menu.add(0,1,0,R.string.sobre);
		item.setIcon(R.drawable.ic_sobre);
		item = menu.add(0,2,0,R.string.sair);
		item.setIcon(R.drawable.ic_sair);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId,MenuItem item) {

		switch (item.getItemId()) {
			case 1:
				startActivity(new Intent(GrandesPensadoresActivity.this,SobreActivity.class));

				return true;
			case 2:
				Log.i(GrandesPensadores.categoriaLog.value(),"chamando Home");
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
				return true;
		}
		return false;
	}

}
