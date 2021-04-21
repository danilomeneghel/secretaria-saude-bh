package br.gov.pbh.prodabel.hubsmsa.client.siom.dto;

import java.io.Serializable;

public class EstruturaAtualSiomDTO implements Serializable {

	private static final long serialVersionUID = 4785883013505191192L;

	private Long idUnidadeOrganizacional;
	private Long idUnidadeOrganizacionalSuperior;
	private String nomeUnidadeOrgacional;
	private String siglaUnidadeOrganizacional;
	private Long idTipoVinculoUO;
	private String descTipoVinculoUO;
	private Long idNivelUnidade;
	private String descNivelUnidadeOrg;

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

}
