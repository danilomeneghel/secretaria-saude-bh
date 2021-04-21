package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoEmpresaEnum {

	NOMEFANTASIA("nomeFantasia"), CNPJ("cnpj"), CNES("codigoCnes"), SITE("site"), NOMEEMPRESARIAL("nomeEmpresarial"),
	STATUS("ativo");

	private String name;  

	private ColunaOrdenacaoEmpresaEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
