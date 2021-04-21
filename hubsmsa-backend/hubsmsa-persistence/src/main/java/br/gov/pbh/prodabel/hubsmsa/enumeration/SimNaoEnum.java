package br.gov.pbh.prodabel.hubsmsa.enumeration;



public enum SimNaoEnum {
	
  S("Sim"), N("Não");


	private String name;

	/**
	 * Instantiates a new sim nao enum.
	 *
	 * @param name the name
	 */
	private SimNaoEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	

}
