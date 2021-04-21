package br.gov.pbh.prodabel.hubsmsa.enumeration;


/***
 * Enum com o estado do status - A - Ativo ou I - Inativo 
 */
public enum StatusEnum {
	
	A ("Sim"),
	I ("Não");


	private String name;

	private StatusEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
