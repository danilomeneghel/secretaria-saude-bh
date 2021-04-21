package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoSistemaEnum {
	
	EMPRESA("empresa"),
	NOME("nome"),	
	DESCRICAO("descricao"),
	FORMACADASTRO("formaCadastro"),
	DATAATUALIZACAO("dataAtualizacao"),
	STATUS("status");
	
	private String name;
	
	private ColunaOrdenacaoSistemaEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
