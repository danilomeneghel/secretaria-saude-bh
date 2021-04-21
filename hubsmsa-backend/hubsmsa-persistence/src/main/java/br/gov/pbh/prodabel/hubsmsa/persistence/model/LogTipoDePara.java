package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import java.util.Date;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

public class LogTipoDePara {
	
	private Long id;
	private Long revisao;
	private Long idRevisao;
	private RevisionType revType;
	private String descricao;
	private FormaCadastroEnum formaCadastro;
	private String nome;
	private StatusEnum status;
	private Date dtRevisao;
	private String matricula; /*Matrícula do usuário na Prodabel*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRevisao() {
		return revisao;
	}
	public void setRevisao(Long revisao) {
		this.revisao = revisao;
	}
	public Long getIdRevisao() {
		return idRevisao;
	}
	public void setIdRevisao(Long idRevisao) {
		this.idRevisao = idRevisao;
	}
	public RevisionType getRevType() {
		return revType;
	}
	public void setRevType(RevisionType revType) {
		this.revType = revType;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public FormaCadastroEnum getFormaCadastro() {
		return formaCadastro;
	}
	public void setFormaCadastro(FormaCadastroEnum formaCadastro) {
		this.formaCadastro = formaCadastro;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public Date getDtRevisao() {
		return dtRevisao;
	}
	public void setDtRevisao(Date dtRevisao) {
		this.dtRevisao = dtRevisao;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
