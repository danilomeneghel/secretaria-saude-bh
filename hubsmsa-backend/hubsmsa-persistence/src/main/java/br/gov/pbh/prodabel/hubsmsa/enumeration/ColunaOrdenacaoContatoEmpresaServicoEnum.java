package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoContatoEmpresaServicoEnum {

  EMPRESA("contatoEmpresa.empresa"), CONTATO("contatoEmpresa.nome"), SERVICO(
      "servico.nome"), SISTEMA("sistemaPrimario.nome, sistemaSecundario.nome");

	private String name;

	/**
	 * Instantiates a new coluna ordenacao contato empresa servico enum.
	 *
	 * @param name the name
	 */
	private ColunaOrdenacaoContatoEmpresaServicoEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
