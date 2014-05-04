package com.olimposystems.grandespensadores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.olimposystems.grandespensadores.type.GrandesPensadores;

public class SobreActivity extends Activity {

	private static final String SOBRE_GRANDES_PENSADORES = "sobre Grandes Pensadores - ";
	private static final String EMAIL_CONTATO = "contato@olymposystems.com";

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.sobre);

		Button botao1 = (Button) findViewById(R.id.botao_comments);
		botao1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

				emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{EMAIL_CONTATO});
				emailIntent.putExtra(Intent.EXTRA_SUBJECT,SOBRE_GRANDES_PENSADORES + GrandesPensadores.autor.value());
				emailIntent.setType("plain/text");

				startActivity(emailIntent);

			}
		});
	}

}
