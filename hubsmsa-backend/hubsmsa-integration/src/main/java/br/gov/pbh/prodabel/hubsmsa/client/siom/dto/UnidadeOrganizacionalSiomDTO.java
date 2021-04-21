package br.gov.pbh.prodabel.hubsmsa.client.siom.dto;

import java.io.Serializable;

public class UnidadeOrganizacionalSiomDTO implements Serializable {

	private static final long serialVersionUID = 3910237638221965113L;

	private Long idUnidadeOrganizacional;
	private Long idUnidadeOrganizacionalSuperior;
	private String nomeUnidadeOrgacional;
	private String siglaUnidadeOrganizacional;
	private Long idTipoVinculoUO;
	private String descTipoVinculoUO;
	private Long idNivelUnidade;
	private String descNivelUnidadeOrg;
	private Long idClassificacaoFuncional;
	private String nomeClassificacaoFuncional;
	private Long idSubclassificacaoFuncional;
	private String nomeSubclassificacaoFuncional;
	private Long idCategoriaUnidadeOrganizacional;
	private String nomeCategoriaUnidadeOrganizacional;
	private Long idSubcategoriaUnidadeOrganizacional;
	private String nomeSubcategoriaUnidadeOrganizacional;
	private String emailUnidadeOrganizacional;
	private String nomeTitular;

	public Long getIdUnidadeOrganizacional() {
		return idUnidadeOrganizacional;
	}

	public void setIdUnidadeOrganizacional(Long idUnidadeOrganizacional) {
		this.idUnidadeOrganizacional = idUnidadeOrganizacional;
	}

	public Long getIdUnidadeOrganizacionalSuperior() {
		return idUnidadeOrganizacionalSuperior;
	}

	public void setIdUnidadeOrganizacionalSuperior(Long idUnidadeOrganizacionalSuperior) {
		this.idUnidadeOrganizacionalSuperior = idUnidadeOrganizacionalSuperior;
	}

	public String getNomeUnidadeOrgacional() {
		return nomeUnidadeOrgacional;
	}

	public void setNomeUnidadeOrgacional(String nomeUnidadeOrgacional) {
		this.nomeUnidadeOrgacional = nomeUnidadeOrgacional;
	}

	public String getSiglaUnidadeOrganizacional() {
		return siglaUnidadeOrganizacional;
	}

	public void setSiglaUnidadeOrganizacional(String siglaUnidadeOrganizacional) {
		this.siglaUnidadeOrganizacional = siglaUnidadeOrganizacional;
	}

	public Long getIdTipoVinculoUO() {
		return idTipoVinculoUO;
	}

	public void setIdTipoVinculoUO(Long idTipoVinculoUO) {
		this.idTipoVinculoUO = idTipoVinculoUO;
	}

	public String getDescTipoVinculoUO() {
		return descTipoVinculoUO;
	}

	public void setDescTipoVinculoUO(String descTipoVinculoUO) {
		this.descTipoVinculoUO = descTipoVinculoUO;
	}

	public Long getIdNivelUnidade() {
		return idNivelUnidade;
	}

	public void setIdNivelUnidade(Long idNivelUnidade) {
		this.idNivelUnidade = idNivelUnidade;
	}

	public String getDescNivelUnidadeOrg() {
		return descNivelUnidadeOrg;
	}

	public void setDescNivelUnidadeOrg(String descNivelUnidadeOrg) {
		this.descNivelUnidadeOrg = descNivelUnidadeOrg;
	}

	public Long getIdClassificacaoFuncional() {
		return idClassificacaoFuncional;
	}

	public void setIdClassificacaoFuncional(Long idClassificacaoFuncional) {
		this.idClassificacaoFuncional = idClassificacaoFuncional;
	}

	public String getNomeClassificacaoFuncional() {
		return nomeClassificacaoFuncional;
	}

	public void setNomeClassificacaoFuncional(String nomeClassificacaoFuncional) {
		this.nomeClassificacaoFuncional = nomeClassificacaoFuncional;
	}

	public Long getIdSubclassificacaoFuncional() {
		return idSubclassificacaoFuncional;
	}

	public void setIdSubclassificacaoFuncional(Long idSubclassificacaoFuncional) {
		this.idSubclassificacaoFuncional = idSubclassificacaoFuncional;
	}

	public String getNomeSubclassificacaoFuncional() {
		return nomeSubclassificacaoFuncional;
	}

	public void setNomeSubclassificacaoFuncional(String nomeSubclassificacaoFuncional) {
		this.nomeSubclassificacaoFuncional = nomeSubclassificacaoFuncional;
	}

	public Long getIdCategoriaUnidadeOrganizacional() {
		return idCategoriaUnidadeOrganizacional;
	}

	public void setIdCategoriaUnidadeOrganizacional(Long idCategoriaUnidadeOrganizacional) {
		this.idCategoriaUnidadeOrganizacional = idCategoriaUnidadeOrganizacional;
	}

	public String getNomeCategoriaUnidadeOrganizacional() {
		return nomeCategoriaUnidadeOrganizacional;
	}

	public void setNomeCategoriaUnidadeOrganizacional(String nomeCategoriaUnidadeOrganizacional) {
		this.nomeCategoriaUnidadeOrganizacional = nomeCategoriaUnidadeOrganizacional;
	}

	public Long getIdSubcategoriaUnidadeOrganizacional() {
		return idSubcategoriaUnidadeOrganizacional;
	}

	public void setIdSubcategoriaUnidadeOrganizacional(Long idSubcategoriaUnidadeOrganizacional) {
		this.idSubcategoriaUnidadeOrganizacional = idSubcategoriaUnidadeOrganizacional;
	}

	public String getNomeSubcategoriaUnidadeOrganizacional() {
		return nomeSubcategoriaUnidadeOrganizacional;
	}

	public void setNomeSubcategoriaUnidadeOrganizacional(String nomeSubcategoriaUnidadeOrganizacional) {
		this.nomeSubcategoriaUnidadeOrganizacional = nomeSubcategoriaUnidadeOrganizacional;
	}

	public String getEmailUnidadeOrganizacional() {
		return emailUnidadeOrganizacional;
	}

	public void setEmailUnidadeOrganizacional(String emailUnidadeOrganizacional) {
		this.emailUnidadeOrganizacional = emailUnidadeOrganizacional;
	}

	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}

}
