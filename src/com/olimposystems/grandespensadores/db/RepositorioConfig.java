package com.olimposystems.grandespensadores.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.olimposystems.grandespensadores.domain.Config;
import com.olimposystems.grandespensadores.domain.Config.Configs;
import com.olimposystems.grandespensadores.type.GrandesPensadores;

/**
 * Repositório para configs que utiliza o SQLite internamente
 * 
 * @author rlecheta
 * @author Fernando Boaglio
 */
public class RepositorioConfig {

	public static final int ID_CONFIG = 0;

	// Nome do banco
	private static final String NOME_BANCO = "gp_" + GrandesPensadores.autor.value();
	// Nome da tabela
	public static final String NOME_TABELA = "config";

	protected SQLiteDatabase db;

	public RepositorioConfig(Context ctx) {
		// Abre o banco de dados já existente
		db = ctx.openOrCreateDatabase(NOME_BANCO,Context.MODE_PRIVATE,null);
	}

	protected RepositorioConfig() {
		// Apenas para criar uma subclasse...
	}

	// Salva o config, insere um novo ou atualiza
	public long salvar(Config config) {
		long id = config.id;

		if (id != 0) {
			atualizar(config);
			Log.i(GrandesPensadores.categoriaLog.value(),"atualizar config [" + id + "]");
		} else {
			// Insere novo
			id = inserir(config);
			Log.i(GrandesPensadores.categoriaLog.value(),"nova config [" + id + "]");
		}

		return id;
	}

	// Insere um novo config
	public long inserir(Config config) {
		ContentValues values = new ContentValues();
		values.put(Configs.NOTIFICACAO,config.getNotifica());

		long id = inserir(values);
		return id;
	}

	// Insere um novo config
	public long inserir(ContentValues valores) {
		long id = db.insert(NOME_TABELA,"",valores);
		return id;
	}

	// Atualiza o config no banco. O id do config é utilizado.
	public int atualizar(Config config) {
		ContentValues values = new ContentValues();
		values.put(Configs.NOTIFICACAO,config.getNotifica());

		String _id = String.valueOf(config.id);

		String where = Configs._ID + "=?";
		String[] whereArgs = new String[]{_id};

		int count = atualizar(values,where,whereArgs);

		return count;
	}

	// Atualiza o config com os valores abaixo
	// A cláusula where é utilizada para identificar o config a ser atualizado
	public int atualizar(ContentValues valores,String where,String[] whereArgs) {
		int count = db.update(NOME_TABELA,valores,where,whereArgs);
		Log.i(GrandesPensadores.categoriaLog.value(),"Atualizou [" + count + "] registros");
		return count;
	}

	// Deleta o config com o id fornecido
	public int deletar(long id) {
		String where = Configs._ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[]{_id};

		int count = deletar(where,whereArgs);

		return count;
	}

	// Deleta o config com os argumentos fornecidos
	public int deletar(String where,String[] whereArgs) {
		int count = db.delete(NOME_TABELA,where,whereArgs);
		Log.i(GrandesPensadores.categoriaLog.value(),"Deletou [" + count + "] registros");
		return count;
	}

	// Busca o config pelo id
	public Config buscarConfig(long id) {
		// select * from config where _id=?
		Cursor c = db.query(true,NOME_TABELA,Config.colunas,Configs._ID + "=" + id,null,null,null,null,null);

		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			Config config = new Config();

			// Lê os dados
			config.id = c.getLong(ID_CONFIG);
			config.setNotifica(c.getString(1));

			return config;
		}

		return null;
	}

	// Retorna um cursor com todos os configs
	public Cursor getCursor() {
		try {
			// select * from configs
			return db.query(NOME_TABELA,Config.colunas,null,null,null,null,null,null);
		} catch (SQLException e) {
			Log.e(GrandesPensadores.categoriaLog.value(),"Erro ao buscar os configs: " + e.toString());
			return null;
		}
	}

	// Retorna uma lista com todos os configs
	public List<Config> listarConfigs() {
		Cursor c = getCursor();

		List<Config> configs = new ArrayList<Config>();

		if (c.moveToFirst()) {

			// Recupera os índices das colunas
			int idxId = c.getColumnIndex(Configs._ID);
			int idxNotifica = c.getColumnIndex(Configs.NOTIFICACAO);

			// Loop até o final
			do {
				Config config = new Config();
				configs.add(config);

				// recupera os atributos de config
				config.id = c.getLong(idxId);
				config.setNotifica(c.getString(idxNotifica));

			} while (c.moveToNext());
		}

		return configs;
	}

	// Busca um config utilizando as configurações definidas no
	// SQLiteQueryBuilder
	// Utilizado pelo Content Provider de config
	public Cursor query(SQLiteQueryBuilder queryBuilder,String[] projection,String selection,String[] selectionArgs,String groupBy,String having,String orderBy) {
		Cursor c = queryBuilder.query(db,projection,selection,selectionArgs,groupBy,having,orderBy);
		return c;
	}

	// Fecha o banco
	public void fechar() {
		// fecha o banco de dados
		if (db != null) {
			db.close();
		}
	}
}
