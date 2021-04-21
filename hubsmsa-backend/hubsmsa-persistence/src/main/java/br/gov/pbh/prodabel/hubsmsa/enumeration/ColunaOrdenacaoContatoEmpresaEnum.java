package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoContatoEmpresaEnum {

	EMPRESA("empresa"),NOME("nome"), STATUS("status"), EMAIL("email"), TELEFONE("telefone");

	private String name;

	private ColunaOrdenacaoContatoEmpresaEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
