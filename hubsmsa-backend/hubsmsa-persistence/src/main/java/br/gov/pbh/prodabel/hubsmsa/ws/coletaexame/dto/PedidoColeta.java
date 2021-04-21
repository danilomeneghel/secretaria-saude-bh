package br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto;

import java.io.Serializable;

public class PedidoColeta implements Serializable {
  
  private static final long serialVersionUID = 4198659509693683824L;

  private Long numeroPedido;

  private Long codigoCentroSaude;

  private Long cnesEstabelecimentoSolicitante;

  private String indicadorPedidoSus;

  private String tipoPedido;

  private String statusPedido;

  public Long getNumeroPedido() {
    return numeroPedido;
  }

  public void setNumeroPedido(Long numeroPedido) {
    this.numeroPedido = numeroPedido;
  }

  public Long getCodigoCentroSaude() {
    return codigoCentroSaude;
  }

  public void setCodigoCentroSaude(Long codigoCentroSaude) {
    this.codigoCentroSaude = codigoCentroSaude;
  }

  public Long getCnesEstabelecimentoSolicitante() {
    return cnesEstabelecimentoSolicitante;
  }

  public void setCnesEstabelecimentoSolicitante(Long cnesEstabelecimentoSolicitante) {
    this.cnesEstabelecimentoSolicitante = cnesEstabelecimentoSolicitante;
  }

  public String getIndicadorPedidoSus() {
    return indicadorPedidoSus;
  }

  public void setIndicadorPedidoSus(String indicadorPedidoSus) {
    this.indicadorPedidoSus = indicadorPedidoSus;
  }

  public String getTipoPedido() {
    return tipoPedido;
  }

  public void setTipoPedido(String tipoPedido) {
    this.tipoPedido = tipoPedido;
  }

  public String getStatusPedido() {
    return statusPedido;
  }

  public void setStatusPedido(String statusPedido) {
    this.statusPedido = statusPedido;
  }

}
