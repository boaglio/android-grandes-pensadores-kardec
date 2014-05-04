package com.olimposystems.grandespensadores.type;

public enum GrandesPensadores {

	versao("1.2"),
	build("2014-05-04"),
	autor("Allan Kardec"),
	database("kardec"),
	categoriaLog("gpjc"),
	arquivoDeconfiguracao("config.gp"),
	horaDeNotificacao("8"),
	executarAlarme("EXECUTAR_ALARME"),
	debugAtivo("n");

	GrandesPensadores(String value) {
		this.value = value;
	}

	private String value;

	public String value() {
		return value;
	}

}
