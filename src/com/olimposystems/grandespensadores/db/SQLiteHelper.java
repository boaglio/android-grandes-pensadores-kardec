package com.olimposystems.grandespensadores.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.olimposystems.grandespensadores.type.GrandesPensadores;

/**
 * Implementacao de SQLiteOpenHelper
 * Classe utilitária para abrir, criar, e atualizar o banco de dados
 * 
 * @author ricardo
 */
class SQLiteHelper extends SQLiteOpenHelper {

	private final String[] scriptSQLCreate;
	private final String scriptSQLDelete;

	/**
	 * Cria uma instância de SQLiteHelper
	 * 
	 * @param context
	 * @param nomeBanco nome do banco de dados
	 * @param versaoBanco versão do banco de dados (se for diferente é para atualizar)
	 * @param scriptSQLCreate SQL com o create table..
	 * @param scriptSQLDelete SQL com o drop table...
	 */
	SQLiteHelper(Context context,String nomeBanco,int versaoBanco,String[] scriptSQLCreate,String scriptSQLDelete) {
		super(context,nomeBanco,null,versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}

	@Override
	// Criar novo banco...
	public void onCreate(SQLiteDatabase db) {
		Log.i(GrandesPensadores.categoriaLog.value(),"Criando banco com sql");
		int qtdeScripts = scriptSQLCreate.length;

		// Executa cada sql passado como parâmetro
		for (int i = 0 ; i < qtdeScripts ; i++) {
			String sql = scriptSQLCreate[i];
			Log.i(GrandesPensadores.categoriaLog.value(),sql);
			// Cria o banco de dados executando o script de criação
			db.execSQL(sql);
		}
	}

	@Override
	// Mudou a versão...
	public void onUpgrade(SQLiteDatabase db,int versaoAntiga,int novaVersao) {
		Log.w(GrandesPensadores.categoriaLog.value(),"Atualizando da versão " + versaoAntiga + " para " + novaVersao + ". Todos os registros serão deletados.");
		Log.i(GrandesPensadores.categoriaLog.value(),scriptSQLDelete);
		// Deleta as tabelas...
		db.execSQL(scriptSQLDelete);
		// Cria novamente...
		onCreate(db);
	}
}
