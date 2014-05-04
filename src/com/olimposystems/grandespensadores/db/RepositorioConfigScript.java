package com.olimposystems.grandespensadores.db;

import android.content.Context;

import com.olimposystems.grandespensadores.type.GrandesPensadores;

/**
 * <pre>
 * Repositório para carros que utiliza o SQLite internamente
 * Para visualizar o banco pelo adb shell:
 * &gt;&gt; sqlite3 /data/data/br.livro.android.exemplos.banco/databases/BancoCarro
 * &gt;&gt; Mais info dos comandos em: http://www.sqlite.org/sqlite.html
 * &gt;&gt; .exit para sair
 * </pre>
 * 
 * @author rlecheta
 */
public class RepositorioConfigScript extends RepositorioConfig {

	// Script para fazer drop na tabela
	private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS config";

	// Cria a tabela com o "_id" sequencial
	private static final String[] SCRIPT_DATABASE_CREATE = new String[]{"create table config ( _id integer primary key autoincrement, notificacao text not null,hora text not null);","insert into config(notificacao,hora) values('N','09:00h');"};

	// Nome do banco
	private static final String NOME_BANCO = "gp_" + GrandesPensadores.autor.value();

	// Controle de versão
	private static final int VERSAO_BANCO = 1;

	// Nome da tabela
	public static final String TABELA_CARRO = "carro";

	// Classe utilitária para abrir, criar, e atualizar o banco de dados
	private final SQLiteHelper dbHelper;

	// Cria o banco de dados com um script SQL
	public RepositorioConfigScript(Context ctx) {
		// Criar utilizando um script SQL
		dbHelper = new SQLiteHelper(ctx,RepositorioConfigScript.NOME_BANCO,RepositorioConfigScript.VERSAO_BANCO,RepositorioConfigScript.SCRIPT_DATABASE_CREATE,RepositorioConfigScript.SCRIPT_DATABASE_DELETE);

		// abre o banco no modo escrita para poder alterar também
		db = dbHelper.getWritableDatabase();
	}

	// Fecha o banco
	@Override
	public void fechar() {
		super.fechar();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
}
