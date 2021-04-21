package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoRevisaoEnum {
	
	DADOANTERIOR("dadoAnterior"),
	DADOATUAL("dadoAtual"),
	DATAALTERACAODADOATUAL("dataAlteracaoDadoAtual"),
	USUARIORESPONSAVELALTERACAO("usuarioResponsavelAlteracao"),
	EMPRESA("empresa"),
	REVISAO("idRevisao"),
  SISTEMARESPONSAVELALTERACAO("sistemaResponsavelAlteracao"), TIPODEPARA("tipoDePara");
	
	private String name;
	
	/**
	 * Instantiates a new coluna ordenacao revisao enum.
	 *
	 * @param name the name
	 */
	private ColunaOrdenacaoRevisaoEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
