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
import br.gov.pbh.prodabel.hubsmsa.dto.depara.DeParaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.VisualizarDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarCampoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaLogDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.PesquisaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.handler.DeParaHandler;
import br.gov.pbh.prodabel.hubsmsa.handler.ServicosHandler;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.DeParaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.DeParaPrimarioDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.DeParaSecundarioDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.DeParaMapper;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.DeParaPrimarioMapper;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.DeParaSecundarioMapper;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.RevisionMapper;
import br.gov.pbh.prodabel.hubsmsa.util.DeParaUtil;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ResponseUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacoesColetaExameResponse;
import br.gov.pbh.prodabel.hubsmsa.ws.dto.ExameSolicitadoDTO;

/**
 * Implementação do Service responsável por acessar os dados de De/Para
 *
 * @author alisson.souza@ctis.com.br
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DeParaService extends GenericService<Long, DePara> {

  private static final String PIPE = "|";

  @EJB
  private DeParaDAO deParaDAO;

  @EJB
  private DeParaPrimarioDAO deParaPrimarioDAO;

  @EJB
  private DeParaSecundarioDAO deParaSecundarioDAO;
  @EJB
  private SistemaService sistemaService;

  @EJB
  private DeParaSecundarioService deParaSecundarioService;

  @EJB
  private DeParaPrimarioService deParaPrimarioService;

  @EJB
  private UsuarioService usuarioService;



  /**
   * Retorna um {@linkPaginacaoPublicaDTO<VisualizarDeParaDTO>} contendo uma lista de DePara.
   *
   * @param filtroPesquisa - Objeto que encapsula os atributos da entidade FiltroPesquisaDeParaDTO
   *        para consulta.
   *
   * @return {@link PaginacaoPublicaDTO<VisualizarDeParaDTO>} - Tipo de retorno da operação.
   *
   */
  public PaginacaoPublicaDTO<PesquisaDeParaDTO> consultarDePara(
      final FiltroPesquisaDeParaDTO filtroPesquisa) {

    try {

      this.verficiarSeTemUmaPropriedade(filtroPesquisa);
      final PaginacaoPublicaDTO<PesquisaDeParaDTO> dePara = new PaginacaoPublicaDTO<>();
      dePara.setItens(DeParaMapper.mapperPesquisa(this.deParaDAO.consultarDePara(filtroPesquisa)));
      for (PesquisaDeParaDTO pesquisaDeParaDTO : dePara.getItens()) {
        List<DeParaPrimario> listaCodigosPrimario =
            this.deParaPrimarioDAO.consultaCodigosPrimarios(pesquisaDeParaDTO.getId());
        String codigosPrimarios =
            listaCodigosPrimario.stream().map(dpp -> String.valueOf(dpp.getCodigo())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setCodigosPrimarios(codigosPrimarios);

        List<DeParaSecundario> listaCodigosSecundario =
            this.deParaSecundarioDAO.consultaCodigosSecundarios(pesquisaDeParaDTO.getId());
        String codigosSecundarios =
            listaCodigosSecundario.stream().map(dpp -> String.valueOf(dpp.getCodigo())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setCodigosSecundarios(codigosSecundarios);
      }

      dePara.setTotalRegistros(this.deParaDAO.consultarTotalRegistros(filtroPesquisa));

      return dePara;

    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }

  }

  /**
   * Verficiar se tem uma propriedade.
   *
   * @param filtroPesquisa the filtro pesquisa
   */
  private void verficiarSeTemUmaPropriedade(FiltroPesquisaDeParaDTO filtroPesquisa) {
    if (filtroPesquisa.getIdTipoDePara() == (null)
        && filtroPesquisa.getSistemaPrimario().getCampos().isEmpty()
        && filtroPesquisa.getSistemaSecundario().getCampos().isEmpty()
        && filtroPesquisa.getNomeDePara() == (null) && filtroPesquisa.getStatus() == (null)
        && filtroPesquisa.getSistemaPrimario().getIdEmpresa() == (null)
        && filtroPesquisa.getSistemaPrimario().getIdSistema() == (null)
        && filtroPesquisa.getSistemaSecundario().getIdEmpresa() == (null)
        && filtroPesquisa.getSistemaSecundario().getIdSistema() == (null)) {

      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME028));

    }

  }

  /**
   * Retorna um DePara para visualização
   *
   * @param id - id do DePara
   *
   * @return {@link VisualizarDeParaDTO} - Tipo de retorno da operação.
   *
   */
  public VisualizarDeParaDTO visualizarDePara(final Long id) {
    try {
      final DePara dePara = this.deParaDAO.consultaDeParaPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(dePara);
      return DeParaMapper.mapperVisualizar(dePara);
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    } catch (final Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Cadastra um DePara
   *
   * @param cadastrarDeParaDTO - Objeto que encapsula os atributos da entidade DePara para cadastro
   *
   * @return {@link ResponseDTO<EntityDTO>} - Tipo de retorno da operação.
   *
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> cadastrarDePara(final CadastrarDeParaDTO cadastrarDeParaDTO) {
    try {
      final DePara dePara = DeParaMapper.mapperCadastrar(cadastrarDeParaDTO);
      Sistema sistemaPrimario =
          sistemaService.consultarPorId(cadastrarDeParaDTO.getSistemaPrimario().getIdSistema());
      Sistema sistemaSecundario =
          sistemaService.consultarPorId(cadastrarDeParaDTO.getSistemaSecundario().getIdSistema());
      dePara.setSistemaPrimario(sistemaPrimario);
      dePara.setSistemaSecundario(sistemaSecundario);

      this.deParaDAO.gravar(dePara);

      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(dePara.getId()));

    } catch (final PersistenceException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }

  }

  /**
   * Edita um DePara por Id
   *
   * @param id - Id do DePara que deseja-se editar
   *
   * @param editarDeParaDTO - Objeto que encapsula os atributos da entidade DePara para uma edição
   *
   * @return {@link ResponseDTO<EntityDTO>} - Tipo de retorno da operação.
   *
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> editarDePara(final Long id, final CadastrarDeParaDTO dtoEdicao) {
    try {
      final DePara dePara = this.deParaDAO.consultaDeParaPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(dePara);

      removerDeParaPrimarioSecundario(dtoEdicao, dePara);

      DeParaMapper.mapperEditar(dtoEdicao, dePara);

      this.deParaDAO.gravar(dePara);

      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(dePara.getId()));

    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME003, id));
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Remover de para primario.
   *
   * @param dtoEdicao the dto edicao
   * @param dePara the de para
   * @throws DAOException the DAO exception
   */
  private void removerDeParaPrimarioSecundario(final CadastrarDeParaDTO dtoEdicao,
      final DePara dePara)
      throws DAOException {
    List<DeParaPrimario> primariosList =
        deParaPrimarioDAO.consultaCodigosPrimarios(dePara.getId());

    List<DeParaSecundario> secundariosList =
        deParaSecundarioDAO.consultaCodigosSecundarios(dePara.getId());

    // Remover de/para Primario que foi removido da tela
    removerDeParaPrimario(dtoEdicao, primariosList);

    // Remover de/para Secundário que foi removido da tela
    removerDeParaSecundario(dtoEdicao, secundariosList);
  }

  /**
   * Remover de para secundario.
   *
   * @param dtoEdicao the dto edicao
   * @param secundariosList the secundarios list
   */
  private void removerDeParaSecundario(final CadastrarDeParaDTO dtoEdicao,
      List<DeParaSecundario> secundariosList) {
    for (DeParaSecundario secundario : secundariosList) {

      boolean existeRegistroBanco = false;

      for (CadastrarCampoDeParaDTO deParaDTO : dtoEdicao.getSistemaSecundario().getCamposDePara()) {
        if (secundario.getId().equals(deParaDTO.getId())) {
          existeRegistroBanco = true;
          break;
        }
      }

      if (!existeRegistroBanco) {
        deParaSecundarioDAO.excluir(secundario);
      }

    }
  }

  /**
   * Remover de para primario.
   *
   * @param dtoEdicao the dto edicao
   * @param primariosList the primarios list
   */
  private void removerDeParaPrimario(final CadastrarDeParaDTO dtoEdicao,
      List<DeParaPrimario> primariosList) {
    for (DeParaPrimario primario : primariosList) {

      boolean existeRegistroBanco = false;

      for (CadastrarCampoDeParaDTO deParaDTO : dtoEdicao.getSistemaPrimario().getCamposDePara()) {
        if (primario.getId().equals(deParaDTO.getId())) {
          existeRegistroBanco = true;
          break;
        }
      }

      if (!existeRegistroBanco) {

        deParaPrimarioDAO.excluir(primario);
      }

    }
  }

  /**
   * Exclui um determinado DePara por Id
   *
   * @param id - id do DePara
   *
   * @throws NegocioException
   *
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> excluirDePara(final Long id) {
    try {
      excluir(id);
      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG005),
          new EntityDTO(id));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME003, id));
    }
  }

  /**
   * Recupera a lista de DePara associados a um TipoDePara
   * 
   * @param idTipoDePara
   * @return
   */
  public List<DePara> consultarDeParaDoTipoDePara(final Long idTipoDePara) {
    try {
      return this.deParaDAO.consultarDeParaDoTipoDePara(idTipoDePara);
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Recupera a lista de DePara associados a um Sistema
   * 
   * @param idTipoDePara
   * @return
   */
  public List<DePara> consultarDeParaDoSistema(final Long idSistema) {
    try {
      return this.deParaDAO.consultarDeParasDoSistema(idSistema);
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001, idSistema));
    }
  }

  /**
   * Exclui uma lista de DePara e os DeParaPrimario e DeParaSecundarios a estes associados
   * 
   * @param idsDePara
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void excluir(List<Long> idsDePara) {
    try {
      this.deParaPrimarioService.excluirDeParaPrimarioDoDePara(idsDePara);
      this.deParaSecundarioService.excluirDeParaSecundarioDoDePara(idsDePara);
      this.deParaDAO.excluirDeParas(idsDePara);
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001, idsDePara));
    }
  }

  public void setDeParaDAO(final DeParaDAO deParaDAO) {
    this.deParaDAO = deParaDAO;
  }

  /**
   * Selecionar de para.
   *
   * @param id the id
   * @return the cadastrar de para DTO
   */
  public CadastrarDeParaDTO selecionarDePara(Long id) {
    try {

      final DePara dePara = this.deParaDAO.consultaDeParaPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(dePara);
      return DeParaMapper.mapperSelecionar(dePara);

    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME003, id));
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Gerar csv.
   *
   * @param pesquisarDePara the pesquisar de para
   * @return the response
   */
  public Response gerarCsv(final FiltroPesquisaDeParaDTO pesquisarDePara) {
    try {
      final List<PesquisaDeParaDTO> dePara = DeParaMapper
          .mapperPesquisaRelatorio(this.deParaDAO.consultarDeParaSemPaginacao(pesquisarDePara));

      for (PesquisaDeParaDTO pesquisaDeParaDTO : dePara) {
        List<DeParaPrimario> listaCodigosPrimario =
            this.deParaPrimarioDAO.consultaCodigosPrimarios(pesquisaDeParaDTO.getId());
        String codigosPrimarios =
            listaCodigosPrimario.stream().map(dpp -> String.valueOf(dpp.getCodigo())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setCodigosPrimarios(codigosPrimarios);
        String descricaoPrimario =
            listaCodigosPrimario.stream().map(dpp -> String.valueOf(dpp.getDescricao())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setDescricaoPrimario(descricaoPrimario);

        List<DeParaSecundario> listaCodigosSecundario =
            this.deParaSecundarioDAO.consultaCodigosSecundarios(pesquisaDeParaDTO.getId());
        String codigosSecundarios =
            listaCodigosSecundario.stream().map(dpp -> String.valueOf(dpp.getCodigo())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setCodigosSecundarios(codigosSecundarios);
        String descricaoSecundario =
            listaCodigosSecundario.stream().map(dpp -> String.valueOf(dpp.getDescricao())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setDescricaoSecundario(descricaoSecundario);
      }

      final ResponseBuilder response = Response.ok(DeParaUtil.gerarCsv(dePara));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
      response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_DE_PARA
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
   * @param pesquisarDePara the pesquisar de para
   * @return the response
   */
  public Response gerarExcel(final FiltroPesquisaDeParaDTO pesquisarDePara) {

    try {
      final Date date = new Date();

      final PaginacaoPublicaDTO<PesquisaDeParaDTO> dePara = new PaginacaoPublicaDTO<>();
      dePara.setItens(DeParaMapper
          .mapperPesquisaRelatorio(this.deParaDAO.consultarDeParaSemPaginacao(pesquisarDePara)));

      for (PesquisaDeParaDTO pesquisaDeParaDTO : dePara.getItens()) {
        List<DeParaPrimario> listaCodigosPrimario =
            this.deParaPrimarioDAO.consultaCodigosPrimarios(pesquisaDeParaDTO.getId());
        String codigosPrimarios =
            listaCodigosPrimario.stream().map(dpp -> String.valueOf(dpp.getCodigo())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setCodigosPrimarios(codigosPrimarios);
        String descricaoPrimario =
            listaCodigosPrimario.stream().map(dpp -> String.valueOf(dpp.getDescricao())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setDescricaoPrimario(descricaoPrimario);

        List<DeParaSecundario> listaCodigosSecundario =
            this.deParaSecundarioDAO.consultaCodigosSecundarios(pesquisaDeParaDTO.getId());
        String codigosSecundarios =
            listaCodigosSecundario.stream().map(dpp -> String.valueOf(dpp.getCodigo())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setCodigosSecundarios(codigosSecundarios);
        String descricaoSecundario =
            listaCodigosSecundario.stream().map(dpp -> String.valueOf(dpp.getDescricao())).sorted()
                .collect(Collectors.joining(" | "));
        pesquisaDeParaDTO.setDescricaoSecundario(descricaoSecundario);
      }
      final ResponseBuilder response = Response.ok(DeParaUtil.gerarExcel(dePara.getItens(), date));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
      response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_DE_PARA
          + formatarNomeArquivoData(new Date()) + ConstanteUtil.ATTACHMENT_EXTENSION_XLSX);
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
       * Consultar log de para.
       *
       * @param filtroPesquisa the filtro pesquisa
       * @return the paginacao publica DTO
       */
      public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarLogDePara(
          FiltroPesquisaLogDeParaDTO filtroPesquisa) {
        try {
          PaginacaoPublicaDTO<HistoricoAlteracaoDTO> revisoes = new PaginacaoPublicaDTO<>();

          if (filtroPesquisa.getIdUsuario() != null) {
            Usuario usuario = usuarioService.consultarPorId(filtroPesquisa.getIdUsuario());
            filtroPesquisa.setLoginUsuario(usuario.getLogin());
          }

          List<EnversDTO> enversDto = deParaDAO.buscarHistoricoRevisoes(filtroPesquisa,
              DeParaMapper.getAtributosValidos());

          Set<Long> codigosDePara =
              enversDto.stream().map(e -> (Long) e.getEntityId()).collect(Collectors.toSet());

          if (!codigosDePara.isEmpty()) {
            filtroPesquisa.setCodigosDePara(codigosDePara);

            List<EnversDTO> enversDtoDeParaPrimario = deParaPrimarioDAO.buscarHistoricoRevisoes(
                filtroPesquisa, DeParaPrimarioMapper.getAtributosValidos());

            List<EnversDTO> enversDtoDeParaSecundario = deParaSecundarioDAO.buscarHistoricoRevisoes(
                filtroPesquisa, DeParaSecundarioMapper.getAtributosValidos());

            enversDto.addAll(enversDtoDeParaPrimario);
            enversDto.addAll(enversDtoDeParaSecundario);
          }


          usuarioService.adicionarNomeUsuario(enversDto);

          List<HistoricoAlteracaoDTO> historicos =
              RevisionMapper.mapperRevisions(enversDto, filtroPesquisa);

          revisoes.setItens(PaginacaoUtil.definirPaginacao(historicos,
              filtroPesquisa.getNumeroPagina(), filtroPesquisa.getItensPorPagina()));
          revisoes.setTotalRegistros(PaginacaoUtil.definirTotalRegistros(historicos));

          return revisoes;
        } catch (RegistroNaoEncontradoException e) {
          throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
        } catch (Exception e) {
          throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
        }
      }
  

  /**
   * Consultar de para ativo.
   *
   * @param nomeTipo the nome tipo
   * @param idSistemaPrimario the id sistema primario
   * @param idSistemaSecundario the id sistema secundario
   * @param nome the nome
   * @return true, if successful
   */
  public DePara consultarDePara(String nomeTipo, Long idSistemaPrimario, Long idSistemaSecundario,
      String nome) {
    try {
      return deParaDAO.consultarDePara(nomeTipo, idSistemaPrimario, idSistemaSecundario, nome);
    } catch (DAOException e) {
      nomeTipo = formatarNomeCampo(nomeTipo);
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME032, nomeTipo));
    }
  }

  /**
   * Formatar nome campo.
   *
   * @param nomeTipo the nome tipo
   * @return the string
   */
  private String formatarNomeCampo(String nomeTipo) {
    if (nomeTipo.equals("Agendamento"))
      nomeTipo = "Status do Agendamento.";
    if (nomeTipo.equals("Procedimento"))
      nomeTipo = "Código do Procedimento.";
    return nomeTipo;
  }

  /**
   * Buscar DePara Handler. Retorna um handler com os deparas primario e secundario dos sistemas
   * informados.
   *
   * @param deparaHandler the depara handler
   * @param sistemaPrimario the sistema primario
   * @param sistemaSecundario the sistema secundario
   * @return
   */
  public DeParaHandler buscarDeParaHandler(Map<String, String> map, Sistema sistemaPrimario,
      Sistema sistemaSecundario) {
    DeParaHandler deparaHandler = new DeParaHandler();
    deparaHandler.setDeParaPrimarioMap(new HashMap<>());
    deparaHandler.setDeParaSecundarioMap(new HashMap<>());

    for (Map.Entry<String, String> entry : map.entrySet()) {
      DePara dePara = consultarDePara(entry.getKey(), sistemaPrimario.getId(),
          sistemaSecundario.getId(), entry.getValue());

      if (dePara.getStatus() == StatusEnum.I) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME033, "De/Para"));
      }

      if (dePara.getTipoDePara().getStatus() == StatusEnum.I) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME033, "Tipo de De/Para"));
      }

      Optional<DeParaPrimario> deParaPrimarioOpt = dePara.getDeParaPrimario().stream().findFirst();
      if (deParaPrimarioOpt.isPresent())
        deparaHandler.getDeParaPrimarioMap().put(entry.getKey(), deParaPrimarioOpt.get());

      Optional<DeParaSecundario> deParaSecundarioOpt =
          dePara.getDeParaSecundario().stream().findFirst();
      if (deParaSecundarioOpt.isPresent())
        deparaHandler.getDeParaSecundarioMap().put(entry.getKey(), deParaSecundarioOpt.get());

    }

    return deparaHandler;
  }

  /**
   * Realizar de para.
   *
   * @param response the response
   * @return the solicitacoes coleta exame response
   */
  public SolicitacoesColetaExameResponse realizarDePara(SolicitacoesColetaExameResponse response,
      ServicosHandler servicosHandler) {
    Map<String, String> map = getCamposSolicitacao(response);
    DeParaHandler handler = buscarDeParaHandler(map, servicosHandler.getSistemaPrimario(),
        servicosHandler.getSistemaSecundario());

    for (ExameSolicitadoDTO exame : response.getExamesSolicitados()) {
      exame.setCodigoExamePaciente(
          Long.valueOf(handler.getDeParaPrimarioMap().get("Exame").getCodigo()));
      exame.setCodigoMaterial(
          Long.valueOf(handler.getDeParaPrimarioMap().get("Material").getCodigo()));
      exame.setStatusColeta(Long.valueOf(handler.getDeParaPrimarioMap().get("Coleta").getCodigo()));
    }

    return response;
  }

  private Map<String, String> getCamposSolicitacao(SolicitacoesColetaExameResponse response) {
    Map<String, String> map = new HashMap<>();
    map.put("Material", String.valueOf(response.getExamesSolicitados().get(0).getCodigoMaterial()));
    map.put("Coleta", String.valueOf(response.getExamesSolicitados().get(0).getStatusColeta()));
    map.put("Exame",
        String.valueOf(response.getExamesSolicitados().get(0).getCodigoExamePaciente()));
    return map;
  }


  /**
   * Consultar detalhe revisao.
   *
   * @param id the id
   * @return the historico alteracao detalhe DTO
   */
  /*
   * Consultar detalhe revisao.
   *
   * @param id the id
   * 
   * @return the historico alteracao detalhe DTO
   */
  public HistoricoAlteracaoDetalheDTO consultarDetalheRevisao(Long id) {

    List<EnversDTO> enversDto = new ArrayList<>();
    try {
      FiltroPesquisaLogDeParaDTO filtroPesquisa = new FiltroPesquisaLogDeParaDTO();
      enversDto =
          deParaDAO.buscarDetalheRevisao(id, DeParaMapper.getAtributosValidos(),
          filtroPesquisa);

      List<EnversDTO> enversDtoDeParaPrimario = deParaPrimarioDAO.buscarDetalheRevisao(id,
          DeParaPrimarioMapper.getAtributosValidos(), filtroPesquisa);

      List<EnversDTO> enversDtoDeParaSecundario = deParaSecundarioDAO.buscarDetalheRevisao(id,
          DeParaSecundarioMapper.getAtributosValidos(), filtroPesquisa);

      enversDto.addAll(enversDtoDeParaPrimario);
      enversDto.addAll(enversDtoDeParaSecundario);

    } catch (RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    } catch (Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }

    List<HistoricoAlteracaoDTO> historico = enversDto.stream()
        .collect(Collectors.mapping(RevisionMapper::mapperRevision, Collectors.toList()));

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
        nomeCampo = "Nome do De/Para";
        break;
      case "tipoDeParaNome":
        nomeCampo = "Tipo de De/Para";
        break;
      case "empresaPrimarioNome":
        nomeCampo = "Empresa do Sistema Primário";
        break;
      case "empresaSecundarioNome":
        nomeCampo = "Empresa do Sistema Secundário";
        break;
      case "status":
        nomeCampo = "De/Para Ativo";
        break;
      case "sistemaPrimarioNome":
        nomeCampo = "Sistema Primário";
        break;
      case "sistemaSecundarioNome":
        nomeCampo = "Sistema Secundário";
        break;
      case "codigo":
        nomeCampo = "Código";
        break;
      case "descricao":
        nomeCampo = "Descrição";
        break;
      default:
        return nomeCampo;
    }

    return nomeCampo;

  }

  /**
   * Gerar log excel.
   *
   * @param filtro the filtro
   * @return the response
   */
  public Response gerarLogExcel(FiltroPesquisaLogDeParaDTO filtro) {

    final Date dataGeracao = new Date();

    try {
      List<DeParaAudDTO> lista = deParaDAO.consultarListaEntidades(filtro);

      List<HistoricoDeParaDTO> historicos =
          deParaDAO.montarHistorico(lista);

      adicionarDetalhesDePara(historicos);
      adicionarNomeUsuario(historicos);

      final ResponseBuilder response =
          Response.ok(DeParaUtil.gerarExcelLog(historicos, dataGeracao));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
      response.header(ConstanteUtil.CONTENT_DISPOSITION,
          ConstanteUtil.ATTACHMENT_FILENAME_LOG_DE_PARA
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
   * Adicionar codigos de para.
   *
   * @param historicos the historicos
   */
  private void adicionarDetalhesDePara(List<HistoricoDeParaDTO> historicos) {
    try {
      for (HistoricoDeParaDTO historico : historicos) {
        List<DeParaPrimario> deParasPrimarios =
            deParaPrimarioDAO.consultaCodigosPrimarios(historico.getIdDePara());
        historico.setCodigoSistemaPrimario(String.join(PIPE,
            deParasPrimarios.stream().map(DeParaPrimario::getCodigo).collect(Collectors.toList())));

        historico.setDeParaPrimario(String.join(PIPE,
            deParasPrimarios.stream().map(DeParaPrimario::getCodigo).collect(Collectors.toList())));

        List<DeParaSecundario> deParasSecundarios =
            deParaSecundarioDAO.consultaCodigosSecundarios(historico.getIdDePara());
        historico.setCodigoSistemaSecundario(String.join(PIPE,
            deParasSecundarios.stream().map(DeParaSecundario::getCodigo)
                .collect(Collectors.toList())));

        historico.setDeParaSecundario(String.join(PIPE,
            deParasSecundarios.stream().map(DeParaSecundario::getCodigo)
                .collect(Collectors.toList())));

      }
    } catch (DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME035));
    }

  }

  /**
   * Adicionar nome usuario.
   *
   * @param historicoDTO the historico DTO
   */
  private void adicionarNomeUsuario(List<HistoricoDeParaDTO> historicoDTO) {
    Set<String> logins =
        historicoDTO.stream().map(HistoricoDeParaDTO::getLogin).collect(Collectors.toSet());
    Map<String, Usuario> nomeUsuario = new HashMap<>();
    for (String login : logins) {
      nomeUsuario.put(login, usuarioService.consultarUsuario(login));
    }

    for (HistoricoDeParaDTO dto : historicoDTO) {
      Usuario usuario = nomeUsuario.get(dto.getLogin());
      dto.setNomeUsuario(usuario.getNome());
      dto.setEmailUsuario(usuario.getEmail());
      dto.setLogin(usuario.getLogin());
    }
  }

  /**
   * Gerar log csv.
   *
   * @param filtro the filtro
   * @return the response
   */
  public Response gerarLogCsv(FiltroPesquisaLogDeParaDTO filtro) {

    try {
      List<DeParaAudDTO> lista = deParaDAO.consultarListaEntidades(filtro);

      List<HistoricoDeParaDTO> historicos = deParaDAO.montarHistorico(lista);

      adicionarDetalhesDePara(historicos);
      adicionarNomeUsuario(historicos);

      Object gerarLogCsv = DeParaUtil.gerarLogCsv(historicos);
      final ResponseBuilder response = Response.ok(gerarLogCsv);
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
      response.header(ConstanteUtil.CONTENT_DISPOSITION,
          ConstanteUtil.ATTACHMENT_FILENAME_LOG_DE_PARA
              + formatarNomeArquivoData(new Date()) + ConstanteUtil.ATTACHMENT_EXTENSION_CSV);
      response.header(ConstanteUtil.ACCESS_CONTROL_EXPOSE_HEADERS,
          ConstanteUtil.CONTENT_DISPOSITION);
      return response.build();

    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }
  }

  public void setUsuarioService(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }


}
