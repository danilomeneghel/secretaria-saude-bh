package br.gov.pbh.prodabel.hubsmsa.enumeration;

public enum AssuntoEnum {
	EX ("Exemplo");
	
	private String name;
	
	private AssuntoEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
