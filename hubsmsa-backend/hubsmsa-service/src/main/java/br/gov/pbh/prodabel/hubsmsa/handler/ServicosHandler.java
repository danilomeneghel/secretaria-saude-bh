package br.gov.pbh.prodabel.hubsmsa.handler;

import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.service.ContatoEmpresaServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.ServicoService;
import br.gov.pbh.prodabel.hubsmsa.service.SistemaService;

// TODO: Auto-generated Javadoc
public class ServicosHandler {

  private Sistema sistemaPrimario;

  private Sistema sistemaSecundario;

  private Servico servico;

  private List<ContatoEmpresaServico> contatosEmpresaSucesso;

  private List<ContatoEmpresaServico> contatosEmpresaFalha;

  private SistemaService sistemaService;

  private ServicoService servicoService;

  private ContatoEmpresaServicoService contatoService;

  private String nomeSistemaPrimario;

  private String nomeSistemaSecundario;

  private String nomeServico;


  /**
   * Instantiates a new servicos handler.
   *
   * @param sistemaPrimario the sistema primario
   * @param sistemaSecundario the sistema secundario
   * @param sistema the sistema
   */
  public ServicosHandler(String nomeSistemaPrimario, String nomeSistemaSecundario,
      String nomeServico) {
    this.nomeSistemaPrimario = nomeSistemaPrimario;
    this.nomeSistemaSecundario = nomeSistemaSecundario;
    this.nomeServico = nomeServico;
  }

  /**
   * Sets the services.
   *
   * @param sistemaService the sistema service
   * @param servicoService the servico service
   * @param contatoService the contato service
   */
  public void setServices(SistemaService sistemaService, ServicoService servicoService,
      ContatoEmpresaServicoService contatoService) {

    this.sistemaService = sistemaService;
    this.servicoService = servicoService;
    this.contatoService = contatoService;

    inicializar(nomeSistemaPrimario, nomeSistemaSecundario, nomeServico);
  }

  /**
   * Inicializar.
   *
   * @param sistemaPrimario the sistema primario
   * @param sistemaSecundario the sistema secundario
   * @param servico the servico
   */
  private void inicializar(String nomeSistemaPrimario, String nomeSistemaSecundario,
      String nomeServico) {
    sistemaPrimario = sistemaService.consultarSistemaPorNome(nomeSistemaPrimario);
    sistemaSecundario = sistemaService.consultarSistemaPorNome(nomeSistemaSecundario);
    servico = servicoService
        .consultarServico(sistemaSecundario.getId(), sistemaPrimario.getId(), nomeServico).get(0);
    contatosEmpresaSucesso = contatoService.buscarContatosSucesso(servico);
    contatosEmpresaFalha = contatoService.buscarContatosFalha(servico);
    realizarValidacoes();
  }

  /**
   * Realizar validacoes.
   */
  private void realizarValidacoes() throws NegocioException {
    ValidacoesHandler.validarEmpresaAtiva(sistemaPrimario.getEmpresa());
    ValidacoesHandler.validarEmpresaAtiva(sistemaSecundario.getEmpresa());
    ValidacoesHandler.validarSistemaAtivo(sistemaPrimario);
    ValidacoesHandler.validarSistemaAtivo(sistemaSecundario);
    ValidacoesHandler.validarServicoAtivo(servico);
  }

  public Sistema getSistemaPrimario() {
    return sistemaPrimario;
  }

  public void setSistemaPrimario(Sistema sistemaPrimario) {
    this.sistemaPrimario = sistemaPrimario;
  }

  public Sistema getSistemaSecundario() {
    return sistemaSecundario;
  }

  public void setSistemaSecundario(Sistema sistemaSecundario) {
    this.sistemaSecundario = sistemaSecundario;
  }

  public Servico getServico() {
    return servico;
  }

  public void setServico(Servico servico) {
    this.servico = servico;
  }

  public List<ContatoEmpresaServico> getContatosEmpresaSucesso() {
    return contatosEmpresaSucesso;
  }

  public void setContatosEmpresaSucesso(List<ContatoEmpresaServico> contatosEmpresaSucesso) {
    this.contatosEmpresaSucesso = contatosEmpresaSucesso;
  }

  public List<ContatoEmpresaServico> getContatosEmpresaFalha() {
    return contatosEmpresaFalha;
  }

  public void setContatosEmpresaFalha(List<ContatoEmpresaServico> contatosEmpresaFalha) {
    this.contatosEmpresaFalha = contatosEmpresaFalha;
  }

}
