package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoTipoDeParaEnum {

	NOME("nome"),	
	STATUS("status");
	
	private String name;
	
	private ColunaOrdenacaoTipoDeParaEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
