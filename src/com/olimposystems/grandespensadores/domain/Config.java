package com.olimposystems.grandespensadores.domain;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Config {

	public static final String AUTHORITY = "com.olymposystems.grandespensadores.provider.config";

	public static String[] colunas = new String[]{Configs._ID,Configs.NOTIFICACAO};

	public long id;
	private String notifica;
	private String codigoUltimaCitacao;;

	public String getNotifica() {
		return notifica;
	}

	public void setNotifica(String notifica) {
		this.notifica = notifica;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigoUltimaCitacao() {
		return codigoUltimaCitacao;
	}

	public void setCodigoUltimaCitacao(String codigoUltimaCitacao) {
		this.codigoUltimaCitacao = codigoUltimaCitacao;
	}

	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrão
	 * Android
	 */
	public static final class Configs implements BaseColumns {

		// Não pode instanciar esta Classe
		private Configs() {}

		// content://com.olymposystems.grandespensadores.provider.config/configs
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/configs");

		// Mime Type para todos os configs
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.configs";

		// Mime Type para um único carro
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.configs";

		// Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";

		public static final String NOTIFICACAO = "notificacao";

		// Método que constrói uma Uri para um Carro específico, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.carro/configs/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /configs
			Uri uri = ContentUris.withAppendedId(Configs.CONTENT_URI,id);
			return uri;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ id >>> 32);
		result = prime * result + (notifica == null ? 0 : notifica.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		Config other = (Config) obj;
		if (id != other.id) { return false; }
		if (notifica == null) {
			if (other.notifica != null) { return false; }
		} else if (!notifica.equals(other.notifica)) { return false; }
		return true;
	}

	@Override
	public String toString() {
		return "Config [id=" + id + ", notifica=" + notifica + ", codigoUltimaCitacao=" + codigoUltimaCitacao + "]";
	}

}
