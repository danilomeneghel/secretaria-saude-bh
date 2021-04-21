package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum ColunaOrdenacaoLogAcessoUsuarioEnum {

	DATAACESSO("dataAcesso"), NOME("usuario.nome"), EMAIL("usuario.email"), LOGIN("usuario.login");

	private String name;  

	private ColunaOrdenacaoLogAcessoUsuarioEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
