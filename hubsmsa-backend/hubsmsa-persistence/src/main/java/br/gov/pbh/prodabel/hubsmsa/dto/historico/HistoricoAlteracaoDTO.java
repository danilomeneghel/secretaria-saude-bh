package br.gov.pbh.prodabel.hubsmsa.dto.historico;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

@JsonInclude(Include.NON_NULL)
public class HistoricoAlteracaoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1359207379988747297L;

	private String campo;
	
	private String dadoAnterior;
	
	private String dadoAtual;
	
	private String dataAlteracaoDadoAtual;
	
	private String usuarioResponsavelAlteracao;
	
	private String empresaResponsavelAlteracao;
	
	private String sistemaResponsavelAlteracao;

	private StatusEnum status;
	
	private Long idRevisao;
	
	private String empresa;
	
    private String tipoDePara;

    private String sistemaPrimarioNome;

    public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getDadoAnterior() {
		return dadoAnterior;
	}

	public void setDadoAnterior(String dadoAnterior) {
		this.dadoAnterior = dadoAnterior;
	}

	public String getDadoAtual() {
		return dadoAtual;
	}

	public void setDadoAtual(String dadoAtual) {
		this.dadoAtual = dadoAtual;
	}

	public String getDataAlteracaoDadoAtual() {
		return dataAlteracaoDadoAtual;
	}

	public void setDataAlteracaoDadoAtual(String dataAlteracaoDadoAtual) {
		this.dataAlteracaoDadoAtual = dataAlteracaoDadoAtual;
	}

	public String getUsuarioResponsavelAlteracao() {
		return usuarioResponsavelAlteracao;
	}

	public void setUsuarioResponsavelAlteracao(String usuarioResponsavelAlteracao) {
		this.usuarioResponsavelAlteracao = usuarioResponsavelAlteracao;
	}

	public String getEmpresaResponsavelAlteracao() {
		return empresaResponsavelAlteracao;
	}

	public void setEmpresaResponsavelAlteracao(String empresaResponsavelAlteracao) {
		this.empresaResponsavelAlteracao = empresaResponsavelAlteracao;
	}

	public String getSistemaResponsavelAlteracao() {
		return sistemaResponsavelAlteracao;
	}

	public void setSistemaResponsavelAlteracao(String sistemaResponsavelAlteracao) {
		this.sistemaResponsavelAlteracao = sistemaResponsavelAlteracao;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Long getIdRevisao() {
		return idRevisao;
	}

	public void setIdRevisao(Long idRevisao) {
		this.idRevisao = idRevisao;
	}

    public String getTipoDePara() {
      return tipoDePara;
    }

    public void setTipoDePara(String tipoDePara) {
      this.tipoDePara = tipoDePara;
    }

    public String getSistemaPrimarioNome() {
      return sistemaPrimarioNome;
    }

    public void setSistemaPrimarioNome(String sistemaPrimarioNome) {
      this.sistemaPrimarioNome = sistemaPrimarioNome;
    }
	
}
