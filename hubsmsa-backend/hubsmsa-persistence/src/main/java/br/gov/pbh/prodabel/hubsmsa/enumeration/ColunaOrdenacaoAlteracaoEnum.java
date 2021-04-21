package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoAlteracaoEnum {
	
	DATAALTERACAO("dtRevisao"),
	ASSUNTO("assunto"),
	DESCRICAO("descricao");
	
	private String name;
	
	private ColunaOrdenacaoAlteracaoEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
