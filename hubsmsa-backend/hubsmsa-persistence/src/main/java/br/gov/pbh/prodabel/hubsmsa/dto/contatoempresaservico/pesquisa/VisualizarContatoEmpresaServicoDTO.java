package br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa;

import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.VisualizarContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.VisualizarEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.servico.VisualizarServicoDTO;

public class VisualizarContatoEmpresaServicoDTO extends BaseDTO {

  private static final long serialVersionUID = 8456675526868851556L;
  private Long id;
  private VisualizarContatoEmpresaDTO contato;
  private VisualizarEmpresaDTO empresa;
  private VisualizarServicoDTO servico;
  private Boolean notificacaoSucesso;
  private Boolean notificacaoFalha;
  private String notificacao;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }



  public String getNotificacao() {
    return notificacao;
  }

  public void setNotificacao(String notificacao) {
    this.notificacao = notificacao;
  }

  public Boolean getNotificacaoSucesso() {
    return notificacaoSucesso;
  }

  public void setNotificacaoSucesso(Boolean notificacaoSucesso) {
    this.notificacaoSucesso = notificacaoSucesso;
  }

  public Boolean getNotificacaoFalha() {
    return notificacaoFalha;
  }

  public void setNotificacaoFalha(Boolean notificacaoFalha) {
    this.notificacaoFalha = notificacaoFalha;
  }
  public VisualizarContatoEmpresaDTO getContato() {
    return contato;
  }

  public void setContato(VisualizarContatoEmpresaDTO contato) {
    this.contato = contato;
  }

  public VisualizarServicoDTO getServico() {
    return servico;
  }

  public void setServico(VisualizarServicoDTO servico) {
    this.servico = servico;
  }
  public VisualizarEmpresaDTO getEmpresa() {
    return empresa;
  }

  public void setEmpresa(VisualizarEmpresaDTO empresa) {
    this.empresa = empresa;
  }

}
