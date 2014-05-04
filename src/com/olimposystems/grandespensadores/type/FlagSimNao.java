package com.olimposystems.grandespensadores.type;

public enum FlagSimNao {

	sim("S"),
	nao("N");

	FlagSimNao(String value) {
		this.value = value;
	}

	private String value;

	public String value() {
		return value;
	}

}
