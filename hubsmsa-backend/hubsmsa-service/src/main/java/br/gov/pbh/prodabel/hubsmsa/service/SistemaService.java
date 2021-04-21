package br.gov.pbh.prodabel.hubsmsa.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaLogSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.SistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.VisualizarSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.SistemaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.RevisionMapper;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.SistemaMapper;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ResponseUtil;
import br.gov.pbh.prodabel.hubsmsa.util.SistemaUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

/**
 * Implementação do Service responsável por acessar os dados de Sistema
 *
 * @author danilo.oliveiram@ctis.com.br
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SistemaService extends GenericService<Long, Sistema> {

  @EJB
  private SistemaDAO sistemaDAO;

  @EJB
  private DeParaService deParaService;

  @EJB
  private UsuarioService usuarioService;

  /**
   * Retorna um {@linkPaginacaoPublicaDTO<VisualizarSistemaDTO>} contendo uma lista de Sistema.
   *
   * @param filtroPesquisa - Objeto que encapsula os atributos da entidade FiltroPesquisaSistemaDTO
   *        para consulta.
   *
   * @return {@link PaginacaoPublicaDTO<VisualizarSistemaDTO>} - Tipo de retorno da operação.
   *
   */
  public PaginacaoPublicaDTO<VisualizarSistemaDTO> consultarSistema(
      FiltroPesquisaSistemaDTO filtroPesquisa) {

    try {
      PaginacaoPublicaDTO<VisualizarSistemaDTO> sistemas = new PaginacaoPublicaDTO<>();
      sistemas.setItens(SistemaMapper.mapper(sistemaDAO.consultarSistema(filtroPesquisa)));
      sistemas.setTotalRegistros(sistemaDAO.consultarTotalRegistros(filtroPesquisa));
      return sistemas;
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }

  }

  /**
   * Retorna um sistema de visualização
   *
   * @param id - id do Sistema
   *
   * @return {@link VisualizarSistemaDTO} - Tipo de retorno da operação.
   *
   */
  public VisualizarSistemaDTO consultarSistema(Long id) {
    try {
      Sistema sistema = sistemaDAO.consultaSistemaPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(sistema);
      return SistemaMapper.mapper(sistema);
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME012, id));
    } catch (Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Consultar sistema.
   *
   * @return the list
   */
  public List<VisualizarSistemaDTO> consultarSistema() {
    try {
      List<Sistema> sistema = sistemaDAO.consultaSistemas();
      ValidadorUtil.validarRegistroNaoEncontrado(sistema);
      return SistemaMapper.mapper(sistema);
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME012));
    } catch (Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Consultar sistemas por empresa.
   *
   * @param idEmpresa the id empresa
   * @return the list
   */
  public List<VisualizarSistemaDTO> consultarSistemasPorEmpresa(Long idEmpresa) {
    try {
      Empresa empresa = new Empresa();
      empresa.setId(idEmpresa);
      List<Sistema> sistema = sistemaDAO.consultaSistemaPorEmpresa(empresa);
      ValidadorUtil.validarRegistroNaoEncontrado(sistema);
      return SistemaMapper.mapper(sistema);
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME012));
    } catch (Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Consultar sistema por nome.
   *
   * @param sistema the sistema
   * @return the sistema
   */
  public Sistema consultarSistemaPorNome(String sistema) {

    try {
      return sistemaDAO.consultarSistemaPorNome(sistema);
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Cadastra um Sistema
   *
   * @param cadastrarSistemaDTO - Objeto que encapsula os atributos da entidade Sistema para
   *        cadastro
   *
   * @return {@link ResponseDTO<EntityDTO>} - Tipo de retorno da operação.
   *
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> cadastrarSistema(SistemaDTO cadastrarSistemaDTO) {

    try {
      verificarExistenciaSistemaParaMesmaEmpresa(cadastrarSistemaDTO.getNome(),
          cadastrarSistemaDTO.getIdEmpresa());
      Sistema sistema = SistemaMapper.mapper(cadastrarSistemaDTO);
      sistemaDAO.gravar(sistema);

      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(sistema.getId()));

    } catch (PersistenceException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Verifica se uma empresa já tem um sistema com determinado nome cadastrado
   *
   * @param nomeSistema - Nome da empresa
   *
   * @throws NegocioException
   *
   */
  public void verificarExistenciaSistemaParaMesmaEmpresa(String nomeSistema, Long idEmpresa) {
    try {
      if (sistemaDAO.verificarExistenciaSistemaParaMesmaEmpresa(nomeSistema, idEmpresa)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME013, nomeSistema));
      }
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Verifica se uma empresa já tem um sistema com determinado nome cadastrado
   *
   * @param nomeSistema - Nome da empresa
   * @param id - identificador do sistema.
   * 
   * @throws NegocioException
   */
  public void verificarExistenciaSistemaParaMesmaEmpresa(Long id, String nomeSistema,
      Long idEmpresa) {
    try {
      if (sistemaDAO.verificarExistenciaSistemaParaMesmaEmpresa(id, nomeSistema, idEmpresa)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME013, nomeSistema));
      }
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Edita um Sistema por Id
   *
   * @param id - Id do sistema que deseja-se editar
   *
   * @param editarSistemaDTO - Objeto que encapsula os atributos da entidade Sistema para uma edição
   *
   * @return {@link ResponseDTO<EntityDTO>} - Tipo de retorno da operação.
   *
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> editarSistema(Long id, SistemaDTO editarSistemaDTO) {
    try {
      verificarExistenciaSistemaParaMesmaEmpresa(id, editarSistemaDTO.getNome(),
          editarSistemaDTO.getIdEmpresa());
      Sistema sistema = sistemaDAO.consultaSistemaPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(sistema);
      SistemaMapper.mapper(editarSistemaDTO, sistema);
      sistemaDAO.merge(sistema);

      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(sistema.getId()));

    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME012, id));
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Exclui um determinado sistema por Id
   *
   * @param id - id da empresa
   *
   * @throws NegocioException
   *
   */
  public ResponseDTO<EntityDTO> excluirSistema(Long id) {
    try {
      Sistema sistema = sistemaDAO.consultaSistemaPorId(id);
      sistemaDAO.excluir(id);

      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(sistema.getId()));
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME012, id));
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  public void setSistemaDAO(SistemaDAO sistemaDAO) {
    this.sistemaDAO = sistemaDAO;
  }

  /**
   * Verificar existencia sistema para uma empresa.
   *
   * @param id the id
   * @return true, if successful
   * @throws DAOException the DAO exception
   */
  public boolean verificarExistenciaSistemaParaUmaEmpresa(Long id) throws DAOException {
    return sistemaDAO.verificarExistenciaSistemaParaUmaEmpresa(id);
  }

  /**
   * Formatar nome arquivo data.
   *
   * @param date the date
   * @return the string
   */
  private String formatarNomeArquivoData(final Date date) {
    final SimpleDateFormat formatadorData = new SimpleDateFormat("ddMMyyyy_HHmm");
    return formatadorData.format(date);
  }

  /**
   * Gerar excel.
   *
   * @param filtroPesquisaSistema the filtro pesquisa sistema
   * @return the response
   */
  public Response gerarExcel(final FiltroPesquisaSistemaDTO filtroPesquisaSistema) {
    try {
      final Date dataGeracao = new Date();
      final List<VisualizarSistemaDTO> sistema =
          SistemaMapper.mapper(this.sistemaDAO.consultarSistema(filtroPesquisaSistema));
      final ResponseBuilder response = Response.ok(SistemaUtil.gerarExcel(sistema, dataGeracao));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
      response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_SISTEMAS
          + formatarNomeArquivoData(dataGeracao) + ConstanteUtil.ATTACHMENT_EXTENSION_XLSX);
      response.header(ConstanteUtil.ACCESS_CONTROL_EXPOSE_HEADERS,
          ConstanteUtil.CONTENT_DISPOSITION);
      return response.build();
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }

  }

  /**
   * Gerar csv.
   *
   * @param filtroPesquisaSistema the filtro pesquisa sistema
   * @return the response
   */
  public Response gerarCsv(final FiltroPesquisaSistemaDTO filtroPesquisaSistema) {
    try {
      final List<VisualizarSistemaDTO> sistema =
          SistemaMapper.mapper(this.sistemaDAO.consultarSistema(filtroPesquisaSistema));
      final ResponseBuilder response = Response.ok(SistemaUtil.gerarCsv(sistema));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
      response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_SISTEMAS
          + formatarNomeArquivoData(new Date()) + ConstanteUtil.ATTACHMENT_EXTENSION_CSV);
      response.header(ConstanteUtil.ACCESS_CONTROL_EXPOSE_HEADERS,
          ConstanteUtil.CONTENT_DISPOSITION);
      return response.build();
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }
  }

  /**
   * Consultar selecao.
   *
   * @return the list
   */
  public List<SelecaoDTO> consultarSelecao() {
    return sistemaDAO.consultarSelecao();
  }

  /**
   * Consultar historico revisoes.
   *
   * @param filtroPesquisa the filtro pesquisa
   * @return the paginacao publica DTO
   */
  public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(
      FiltroPesquisaLogSistemaDTO filtroPesquisa) {
    try {
      PaginacaoPublicaDTO<HistoricoAlteracaoDTO> revisoes = new PaginacaoPublicaDTO<>();

      List<EnversDTO> enversDto =
          sistemaDAO.buscarHistoricoRevisoes(filtroPesquisa, SistemaMapper.getAtributosValidos());

      usuarioService.adicionarNomeUsuario(enversDto);

      List<HistoricoAlteracaoDTO> historico =
          RevisionMapper.mapperRevisions(enversDto, filtroPesquisa);

      revisoes.setItens(PaginacaoUtil.definirPaginacao(historico, filtroPesquisa.getNumeroPagina(),
          filtroPesquisa.getItensPorPagina()));
      revisoes.setTotalRegistros(PaginacaoUtil.definirTotalRegistros(historico));

      return revisoes;
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    } catch (Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Consultar detalhe revisao.
   *
   * @param id the id
   * @return the historico alteracao detalhe DTO
   */
  public HistoricoAlteracaoDetalheDTO consultarDetalheRevisao(Long id) {

    List<EnversDTO> enversDto = new ArrayList<>();
    try {
      enversDto = sistemaDAO.buscarDetalheRevisao(id, SistemaMapper.getAtributosValidos());
    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    } catch (Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }

    List<HistoricoAlteracaoDTO> historico = enversDto.stream()
        .collect(Collectors.mapping(e -> RevisionMapper.mapperRevision(e), Collectors.toList()));

    HistoricoAlteracaoDetalheDTO historicoDetalhe = new HistoricoAlteracaoDetalheDTO();
    formatarCampos(historico);
    inserirDadosRevisao(historico, historicoDetalhe);
    usuarioService.inserirDadosUsuario(historico, historicoDetalhe);

    return historicoDetalhe;
  }

  /**
   * Formatar campos.
   *
   * @param historico the historico
   */
  private void formatarCampos(List<HistoricoAlteracaoDTO> historico) {
    historico.stream().forEach(h -> h.setCampo(formatarCampo(h.getCampo())));
  }

  /**
   * Formatar campo.
   *
   * @param nomeCampo the nome campo
   * @return the string
   */
  private String formatarCampo(String nomeCampo) {
    switch (nomeCampo) {

      case "nome":
        nomeCampo = "Nome Sistema";
        break;
      case "nomeEmpresa":
        nomeCampo = "Empresa";
        break;
      case "descricao":
        nomeCampo = "Descrição";
        break;
      case "ativo":
        nomeCampo = "Sistema Ativo";
        break;
      default:
        return nomeCampo;
    }

    return nomeCampo;
  }

  /**
   * Inserir dados revisao.
   *
   * @param historico the historico
   * @param historicoDetalhe the historico detalhe
   */
  @Override
  public void inserirDadosRevisao(List<HistoricoAlteracaoDTO> historico,
      HistoricoAlteracaoDetalheDTO historicoDetalhe) {
    Optional<HistoricoAlteracaoDTO> historicoOpt = historico.stream().findFirst();
    if (historicoOpt.isPresent()) {
      historicoDetalhe.setAlteracoes(historico);
      historicoDetalhe
          .setDataEvento(historicoOpt.get().getDataAlteracaoDadoAtual().substring(0, 16));
      historicoDetalhe.setRevisao(historicoOpt.get().getIdRevisao());
    }
  }


  /**
   * Gerar log csv.
   *
   * @param filtro the filtro
   * @return the response
   */
  public Response gerarLogCsv(FiltroPesquisaLogSistemaDTO filtro) {
    try {

      List<HistoricoSistemaDTO> historicos =
          sistemaDAO.montarHistorico(sistemaDAO.consultarListaEntidades(filtro));

      adicionarNomeUsuario(historicos);

      Object gerarLogCsv = SistemaUtil.gerarLogCsv(historicos);
      final ResponseBuilder response = Response.ok(gerarLogCsv);
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
      response.header(ConstanteUtil.CONTENT_DISPOSITION,
          ConstanteUtil.ATTACHMENT_FILENAME_SISTEMAS
              + TimeUtil.formatarNomeArquivoData(new Date())
              + ConstanteUtil.ATTACHMENT_EXTENSION_CSV);
      response.header(ConstanteUtil.ACCESS_CONTROL_EXPOSE_HEADERS,
          ConstanteUtil.CONTENT_DISPOSITION);
      return response.build();

    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }
  }

  /**
   * Gerar log excel.
   *
   * @param filtro the filtro
   * @return the response
   */
  public Response gerarLogExcel(FiltroPesquisaLogSistemaDTO filtro) {
    try {
      final Date dataGeracao = new Date();

      List<HistoricoSistemaDTO> historicos =
          sistemaDAO.montarHistorico(sistemaDAO.consultarListaEntidades(filtro));

      adicionarNomeUsuario(historicos);

      final ResponseBuilder response =
          Response.ok(SistemaUtil.gerarExcelLog(historicos, dataGeracao));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
      response.header(ConstanteUtil.CONTENT_DISPOSITION,
          ConstanteUtil.ATTACHMENT_FILENAME_SISTEMAS + TimeUtil.formatarNomeArquivoData(dataGeracao)
              + ConstanteUtil.ATTACHMENT_EXTENSION_XLSX);
      response.header(ConstanteUtil.ACCESS_CONTROL_EXPOSE_HEADERS,
          ConstanteUtil.CONTENT_DISPOSITION);
      return response.build();
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }
  }
  
  

  public void setUsuarioService(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  /**
   * Adicionar nome usuario.
   *
   * @param historicoDTO the historico DTO
   */
  private void adicionarNomeUsuario(List<HistoricoSistemaDTO> historicoDTO) {
    Set<String> logins = historicoDTO.stream().map(e -> e.getLogin()).collect(Collectors.toSet());
    Map<String, Usuario> nomeUsuario = new HashMap<>();
    for (String login : logins) {
      nomeUsuario.put(login, usuarioService.consultarUsuario(login));
    }

    for (HistoricoSistemaDTO dto : historicoDTO) {
      Usuario usuario = nomeUsuario.get(dto.getLogin());
      dto.setNomeUsuario(usuario.getNome());
      dto.setEmail(usuario.getEmail());
      dto.setLogin(usuario.getLogin());
    }
  }


}
