package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoLogAplicacaoEnum {

	DATAHORA("dataOcorrencia"), 
	NOMEUSUARIOSISTEMA("nomeUsuarioSistema"),
	NOMESISTEMACLIENTE("nomeSistemaCliente"), 
	MENSAGEM("mensagem");

	private String name;

	private ColunaOrdenacaoLogAplicacaoEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
