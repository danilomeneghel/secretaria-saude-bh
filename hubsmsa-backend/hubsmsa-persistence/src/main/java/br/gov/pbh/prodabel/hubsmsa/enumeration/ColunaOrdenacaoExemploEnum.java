package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoExemploEnum {

	CODIGO("codigo"), NOMEEXEMPLO("descricao"), FORMACADASTRO("formaCadastro"), DATAATUALIZACAO("dataAtualizacao"),
	STATUS("status");

	private String name;

	private ColunaOrdenacaoExemploEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
