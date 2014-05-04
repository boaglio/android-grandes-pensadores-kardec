package com.olimposystems.grandespensadores.domain;

public class Citacao {

	private long id;

	private String autor;

	private String citacao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCodigo(long codigo) {
		id = codigo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getCitacao() {
		return citacao;
	}

	public void setCitacao(String citacao) {
		this.citacao = citacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ id >>> 32);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		Citacao other = (Citacao) obj;
		if (id != other.id) { return false; }
		return true;
	}

	@Override
	public String toString() {
		return "Citacao [codigo=" + id + ", autor=" + autor + ", citacao=" + citacao + "]";
	}

}
