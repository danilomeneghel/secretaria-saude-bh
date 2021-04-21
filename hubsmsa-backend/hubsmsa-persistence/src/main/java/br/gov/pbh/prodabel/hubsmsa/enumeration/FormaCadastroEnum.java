package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum FormaCadastroEnum {

	I("Carga Inicial"), 
	C("Cadastro Manual"), 
	A("Carga Inicial Alterada");
	
	private String name;

	private FormaCadastroEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
