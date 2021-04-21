package br.gov.pbh.prodabel.hubsmsa.dto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public class DadosDaConsultaDTO<T, K> extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2380848573534248710L;
	
	private T dtoParaConsulta;
	
	private Root<K> root;
	
	private CriteriaBuilder criteriaBuilder;

	public T getDtoParaConsulta() {
		return dtoParaConsulta;
	}

	public void setDtoParaConsulta(T dtoParaConsulta) {
		this.dtoParaConsulta = dtoParaConsulta;
	}

	public Root<K> getRoot() {
		return root;
	}

	public void setRoot(Root<K> root) {
		this.root = root;
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return criteriaBuilder;
	}

	public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
		this.criteriaBuilder = criteriaBuilder;
	}
	
	

}
