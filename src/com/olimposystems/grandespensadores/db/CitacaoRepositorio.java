package com.olimposystems.grandespensadores.db;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.olimposystems.grandespensadores.domain.Citacao;
import com.olimposystems.grandespensadores.type.GrandesPensadores;
import com.olimposystems.grandespensadores.util.DataBaseHelper;

public class CitacaoRepositorio {

	private long totalDeLinhas = 0;

	public static final String NOME_TABELA = "citacao";
	public static String[] colunas = new String[]{Citacoes._ID,Citacoes.FRASE};
	private final DataBaseHelper dbHelper;

	public CitacaoRepositorio(Activity activity) {
		dbHelper = new DataBaseHelper(activity);
		registaTotalDeLinhas();
	}

	public Citacao buscarCitacao(int id) {

		SQLiteDatabase db = dbHelper.getBancoDeDados();

		Cursor c = db.query(true,NOME_TABELA,CitacaoRepositorio.colunas,Citacoes._ID + "=" + id,null,null,null,null,null);

		Citacao citacao = new Citacao();
		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			// Lê os dados
			citacao.setId(id);
			citacao.setCitacao(c.getString(1));
			citacao.setAutor(GrandesPensadores.autor.value());

		}

		dbHelper.close();

		return citacao;
	}

	private void registaTotalDeLinhas() {

		totalDeLinhas = dbHelper.contaLinhasDeTabela(NOME_TABELA);
		dbHelper.close();

		Log.i(GrandesPensadores.categoriaLog.value(),"totalDeLinhas de " + NOME_TABELA + " :" + totalDeLinhas);
	}

	public long getProximoCodigo(long codigoAtual) {

		if (totalDeLinhas == 0) {
			totalDeLinhas = dbHelper.contaLinhasDeTabela(NOME_TABELA);
		}

		long proximoCodigo = codigoAtual + 1;

		if (proximoCodigo > totalDeLinhas) {
			proximoCodigo = 1;
		}

		Log.i(GrandesPensadores.categoriaLog.value(),"proximo codigo = " + proximoCodigo);

		return proximoCodigo;
	}

	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrão
	 * Android
	 */
	public static final class Citacoes implements BaseColumns {

		private Citacoes() {}

		public static final String FRASE = "frase";

	}

	public long getTotalDeLinhas() {
		return totalDeLinhas;
	}

}
