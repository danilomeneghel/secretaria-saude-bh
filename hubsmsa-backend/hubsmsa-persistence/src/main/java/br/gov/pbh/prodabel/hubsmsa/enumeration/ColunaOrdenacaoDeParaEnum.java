package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoDeParaEnum {
	
	NOME_TIPO_DEPARA("nomeTipoDePara"),
	NOME_DEPARA("nomeDePara"),
	SISTEMA_PRIMARIO("sistemaPrimario"),
	SISTEMA_SECUNDARIO("sistemaSecundario"),
	CODIGOS_PRIMARIOS("codigosPrimarios"),
	CODIGOS_SECUNDARIOS("codigosSecundarios");

	private String name;
	
	private ColunaOrdenacaoDeParaEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
