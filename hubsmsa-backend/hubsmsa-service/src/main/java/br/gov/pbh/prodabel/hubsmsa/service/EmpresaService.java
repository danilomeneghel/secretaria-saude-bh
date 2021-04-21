package br.gov.pbh.prodabel.hubsmsa.service;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.HTTPS_SITE_PREFIX;
import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.HTTP_SITE_PREFIX;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.apache.commons.lang3.StringUtils;
import br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.EmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaLogEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.VisualizarEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.EmpresaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.EmpresaMapper;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.RevisionMapper;
import br.gov.pbh.prodabel.hubsmsa.util.EmpresaUtil;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ResponseUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

// TODO: Auto-generated Javadoc
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EmpresaService extends GenericService<Long, Empresa> {

  @EJB
  private EmpresaDAO empresaDAO;

  @EJB
  private UsuarioService usuarioService;

  @EJB
  private SistemaService sistemaService;

  @EJB
  private ContatoEmpresaService contatoEmpresaService;


  /**
   *
   * Metodo responsavel por realizar a consulta de Empresas.
   *
   * @param filtroPesquisa - Parametros de Pequisa.
   * @return PaginacaoPublicaDTO<VisualizarEmpresaDTO> - Lista de Empresas correspondente aos
   *         filtros.
   */
  public PaginacaoPublicaDTO<VisualizarEmpresaDTO> consultarEmpresa(
      final FiltroPesquisaEmpresaDTO filtroPesquisa) {
    try {
      final PaginacaoPublicaDTO<VisualizarEmpresaDTO> empresas = new PaginacaoPublicaDTO<>();
      empresas.setItens(EmpresaMapper.mapper(this.empresaDAO.consultarEmpresa(filtroPesquisa)));
      empresas.setTotalRegistros(this.empresaDAO.consultarTotalRegistros(filtroPesquisa));
      return empresas;
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }
  }

  /**
   * Consultar empresa droplist.
   *
   * @param filtroPesquisa the filtro pesquisa
   * @return the paginacao publica DTO
   */
  public PaginacaoPublicaDTO<VisualizarEmpresaDTO> consultarEmpresaDroplist(
      final FiltroPesquisaEmpresaDTO filtroPesquisa) {
    try {
      final PaginacaoPublicaDTO<VisualizarEmpresaDTO> empresas = new PaginacaoPublicaDTO<>();
      empresas
          .setItens(EmpresaMapper.mapper(this.empresaDAO.consultarEmpresaDroplist(filtroPesquisa)));
      empresas.setTotalRegistros(this.empresaDAO.consultarTotalRegistros(filtroPesquisa));
      return empresas;
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }
  }

  /**
   * Metodo responsavel por criar uma empresa.
   *
   * @param empresaDTO - Empresa que será realizado a persistencia
   * @return ResponseDTO<EntityDTO> - Entidade Empresa que foi criada
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> cadastrarEmpresa(final EmpresaDTO empresaDTO) {

    try {
      verificarExistenciaDeDados(empresaDTO);
      validarSite(empresaDTO);
      final Empresa empresa = EmpresaMapper.mapper(empresaDTO);
      this.empresaDAO.gravar(empresa);
      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(empresa.getId()));
    } catch (PersistenceException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }

  }

  /**
   * Metodo responsavel por editar uma empresa.
   *
   * @param editarEmpresaDTO - Empresa que será editado.
   * @param id - Identificador da empresa.
   * @return ResponseDTO<EntityDTO> - Entidade que foi editada.
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> editarEmpresa(final EmpresaDTO editarEmpresaDTO, final Long id) {
    try {
      verificarExistenciaDeDados(editarEmpresaDTO, id);

      final Empresa empresa = this.empresaDAO.consultarPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(empresa);
      validarSite(editarEmpresaDTO);
      EmpresaMapper.mapper(editarEmpresaDTO, empresa);
      this.empresaDAO.merge(empresa);
      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(empresa.getId()));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME014, id));
    }

  }

  /**
   * Metodo responsavel por verificar a unidadde de dados referente a edição da empresa.
   *
   * @param editarEmpresaDTO - Empresa que será verificada.
   * @param id - Indentificação da Empresa.
   */
  private void verificarExistenciaDeDados(final EmpresaDTO editarEmpresaDTO, final Long id) {
    verificarSeNomeEmpresarialExiste(editarEmpresaDTO.getNomeEmpresarial(), id);
    verificarSeNomeFantisiaExiste(editarEmpresaDTO.getNomeFantasia(), id);
    verificarSeCnpjExiste(editarEmpresaDTO.getCnpj(), id);
    verificarSeCnesExiste(editarEmpresaDTO.getCnes(), id);
  }

  /**
   * Metodo responsavel por consultar uma empresa segundo seu ID.
   *
   * @param id - Indentifcação da Empresa.
   * @return VisualizarEmpresaDTO - Empresa referente ao id.
   */
  public VisualizarEmpresaDTO consultarEmpresa(final Long id) {
    try {
      final Empresa empresa = this.empresaDAO.consultarPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(empresa);
      return EmpresaMapper.mapper(empresa);

    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME014, id));
    }
  }

  /**
   * Metodo responsavel pela a exclusão de uma Empresa.
   *
   * @param id - identificador da empresa.
   * @return ResponseDTO<EntityDTO> - Empresa excluida.
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> excluirEmpresa(final Long id) {
    try {
      verificarSistemaVinculadoAEmpresa(id);
      final Empresa empresa = this.empresaDAO.consultarPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(empresa);
      this.contatoEmpresaService.excluirContatosDessaEmpresa(id);
      this.empresaDAO.excluir(empresa);
      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG005),
          new EntityDTO(empresa.getId()));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME014, id));
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum sistema vinculado a empresa.
   *
   * @param id - Identificador da Empresa.
   */
  private void verificarSistemaVinculadoAEmpresa(final Long id) {
    try {
      if (this.sistemaService.verificarExistenciaSistemaParaUmaEmpresa(id)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME020));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Metodo responsavel por verificar a unidadde de dados referente a edição da empresa.
   *
   * @param editarEmpresaDTO - Empresa que será verificada.
   */
  private void verificarExistenciaDeDados(final EmpresaDTO empresaDTO) {
    verificarSeNomeEmpresarialExiste(empresaDTO.getNomeEmpresarial());
    verificarSeNomeFantisiaExiste(empresaDTO.getNomeFantasia());
    verificarSeCnpjExiste(empresaDTO.getCnpj());
    verificarSeCnesExiste(empresaDTO.getCnes());
  }

  /**
   * Valida se o site inicia com HTTP_SITE_PREFIX (argumento 3) ou HTTPS_SITE_PREFIX (argumento 4) e
   * se nao iniciar adiciona ao inicio o valor de HTTP_SITE_PREFIX (argumento 2)
   * 
   * @param empresaDTO
   */
  private void validarSite(final EmpresaDTO empresaDTO) {
    if (empresaDTO.getSite() != null) {
      if (empresaDTO.getSite().trim().isEmpty()) {
        empresaDTO.setSite(null);
      } else {

        empresaDTO.setSite(StringUtils.prependIfMissing(empresaDTO.getSite(), HTTP_SITE_PREFIX,
            HTTP_SITE_PREFIX, HTTPS_SITE_PREFIX));
      }
    }
  }

  /**
   * Metodo responsavel por verificar se o CNES inserido já existe na base de dados.
   *
   * @param cnesEmpresa - CNES da empresa.
   */
  private void verificarSeCnesExiste(final Long cnesEmpresa) {
    try {
      if (this.empresaDAO.verificarCnesExistente(cnesEmpresa)) {
        throw new NegocioException(
            MensagemUtil.getMessage(MensagemEnum.ME019, cnesEmpresa.toString()));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Metodo responsavel por verificar se o CNPJ inserido já existe na base de dados.
   *
   * @param cnpjEmpresa - CNPJ da Empresa.
   */
  private void verificarSeCnpjExiste(final String cnpjEmpresa) {
    try {
      if (this.empresaDAO.verificarCnpjExistente(cnpjEmpresa)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME018,
            EmpresaUtil.adicionarMaskCNPJ(cnpjEmpresa)));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Metodo responsavel por verificar se o Nome Fantasia inserido já existe na base de dados.
   *
   * @param nomeFantasiaEmpresa - Nome Fantasia da Empresa.
   */
  private void verificarSeNomeFantisiaExiste(final String nomeFantasiaEmpresa) {
    try {
      if (this.empresaDAO.verificarNomeFantasiaExistente(nomeFantasiaEmpresa)) {
        throw new NegocioException(
            MensagemUtil.getMessage(MensagemEnum.ME017, nomeFantasiaEmpresa));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Metodo responsavel por verificar se o Nome Empresarial inserido já existe na base de dados.
   *
   * @param nomeEmpresarial - Nome Empresarial da Empresa
   */
  private void verificarSeNomeEmpresarialExiste(final String nomeEmpresarial) {
    try {
      if (this.empresaDAO.verificarNomeEmpresarialExistente(nomeEmpresarial)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME016, nomeEmpresarial));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Metodo responsavel por verificar se o CNES inserido já existe na base de dados e não ta
   * vinculado ao id informado.
   *
   * @param cnes - CNES da empresa.
   * @param id - Identificador da Empresa.
   */
  private void verificarSeCnesExiste(final Long cnes, final Long id) {
    try {
      if (this.empresaDAO.verificarCnesExistente(cnes, id)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME019, cnes.toString()));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Metodo responsavel por verificar se o CNPJ inserido já existe na base de dados e não ta
   * vinculado ao id informado.
   *
   * @param cnpj - CNPJ da Empresa.
   * @param id - Identificador da Empresa.
   */
  private void verificarSeCnpjExiste(final String cnpj, final Long id) {
    try {
      if (this.empresaDAO.verificarCnpjExistente(cnpj, id)) {
        throw new NegocioException(
            MensagemUtil.getMessage(MensagemEnum.ME018, EmpresaUtil.adicionarMaskCNPJ(cnpj), id));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Metodo responsavel por verificar se o Nome Fantasia inserido já existe na base de dados e não
   * ta vinculado ao id informado.
   *
   * @param nomeFantasia - Nome Fantasia da Empresa.
   * @param id - Identificador da Empresa.
   */
  private void verificarSeNomeFantisiaExiste(final String nomeFantasia, final Long id) {
    try {
      if (this.empresaDAO.verificarNomeFantasiaExistente(nomeFantasia, id)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME017, nomeFantasia));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Metodo responsavel por verificar se o Nome Empresarial inserido já existe na base de dados e
   * não ta vinculado ao id informado.
   *
   * @param nomeEmpresarial - Nome Empresarial da Empresa
   * @param id - Identificador da Empresa.
   */
  private void verificarSeNomeEmpresarialExiste(final String nomeEmpresarial, final Long id) {
    try {
      if (this.empresaDAO.verificarNomeEmpresarialExistente(nomeEmpresarial, id)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME016, nomeEmpresarial));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Gerar excel.
   *
   * @param pesquisarEmpresa the pesquisar empresa
   * @return the response
   */
  public Response gerarExcel(final FiltroPesquisaEmpresaDTO pesquisarEmpresa) {
    try {
      final Date dataGeracao = new Date();
      final List<VisualizarEmpresaDTO> empresas =
          EmpresaMapper.mapper(this.empresaDAO.consultarEmpresaSemPaginacao(pesquisarEmpresa));
      final ResponseBuilder response = Response.ok(EmpresaUtil.gerarExcel(empresas, dataGeracao));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
      response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_EMPRESAS
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
   * Gerar csv.
   *
   * @param pesquisarEmpresa the pesquisar empresa
   * @return the response
   */
  public Response gerarCsv(final FiltroPesquisaEmpresaDTO pesquisarEmpresa) {
    try {
      final List<VisualizarEmpresaDTO> empresas =
          EmpresaMapper.mapper(this.empresaDAO.consultarEmpresaSemPaginacao(pesquisarEmpresa));
      final ResponseBuilder response = Response.ok(EmpresaUtil.gerarCsv(empresas));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
      response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_EMPRESAS
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
   * Consultar selecao empresa.
   *
   * @return the list
   */
  public List<SelecaoDTO> consultarSelecaoEmpresa() {
    try {
      return this.empresaDAO.consultarSelecaoEmpresa();
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      return Collections.emptyList();
    }
  }

  /**
   * Consultar sistemas da empresa.
   *
   * @param idEmpresa the id empresa
   * @return the list
   */
  public List<SelecaoDTO> consultarSistemasDaEmpresa(Long idEmpresa) {
    try {
      return this.empresaDAO.consultarSistemasDaEmpresa(idEmpresa);
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME021));
    }
  }

  /**
   * Consultar historico revisoes.
   *
   * @param filtroPesquisa the filtro pesquisa
   * @return the paginacao publica DTO
   */
  public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(
      FiltroPesquisaLogEmpresaDTO filtroPesquisa) {
    try {
      PaginacaoPublicaDTO<HistoricoAlteracaoDTO> revisoes = new PaginacaoPublicaDTO<>();

      List<EnversDTO> enversDto =
          empresaDAO.buscarHistoricoRevisoes(filtroPesquisa, EmpresaMapper.getAtributosValidos());

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
      enversDto = empresaDAO.buscarDetalheRevisao(id, EmpresaMapper.getAtributosValidos());
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
   * Gerar historico csv.
   *
   * @param pesquisarEmpresa the pesquisar empresa
   * @return the response
   */
  public Response gerarHistoricoCsv(FiltroPesquisaLogEmpresaDTO pesquisarEmpresa) {
    try {
      

      List<HistoricoEmpresaDTO> historicos =
          empresaDAO.montarHistorico(this.empresaDAO.consultarLog(pesquisarEmpresa));
            
      adicionarNomeUsuario(historicos);

      final ResponseBuilder response =
          Response.ok(EmpresaUtil.gerarHistoricoCsv(historicos));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
      response.header(ConstanteUtil.CONTENT_DISPOSITION,
          ConstanteUtil.ATTACHMENT_FILENAME_LOG_EMPRESAS
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
   * Gerar historico excel.
   *
   * @param pesquisarEmpresa the pesquisar empresa
   * @return the response
   */
  public Response gerarHistoricoExcel(FiltroPesquisaLogEmpresaDTO pesquisarEmpresa) {
    try {
      final Date dataGeracao = new Date();

      List<HistoricoEmpresaDTO> historicos =
          empresaDAO.montarHistorico(this.empresaDAO.consultarLog(pesquisarEmpresa));

      adicionarNomeUsuario(historicos);

      final ResponseBuilder response =
          Response.ok(EmpresaUtil.gerarExcelHistorico(historicos, dataGeracao));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
      response.header(ConstanteUtil.CONTENT_DISPOSITION,
          ConstanteUtil.ATTACHMENT_FILENAME_LOG_EMPRESAS
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

  public void setEmpresaDAO(EmpresaDAO empresaDAO) {
    this.empresaDAO = empresaDAO;
  }

  public void setUsuarioService(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
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

      case "nomeEmpresarial":
        nomeCampo = "Nome Empresarial";
        break;
      case "cnpj":
        nomeCampo = "CNPJ";
        break;
      case "nomeFantasia":
        nomeCampo = "Nome Fantasia";
        break;
      case "codigoCnes":
        nomeCampo = "CNES";
        break;
      case "site":
        nomeCampo = "Site";
        break;
      case "ativo":
        nomeCampo = "Empresa Ativa";
        break;
      default:
        return nomeCampo;
    }

    return nomeCampo;
  }
  
  /**
   * Adiciona o nome do usuario recuperado pelo login.
   *
   * @param enversDto the envers dto
   */
  private void adicionarNomeUsuario(List<HistoricoEmpresaDTO> historicoDTO) {
    Set<String> logins = historicoDTO.stream().map(e -> e.getLogin()).collect(Collectors.toSet());
    Map<String, Usuario> nomeUsuario = new HashMap<>();
    for (String login : logins) {
      nomeUsuario.put(login, usuarioService.consultarUsuario(login));
    }

    for (HistoricoEmpresaDTO dto : historicoDTO) {
      Usuario usuario = nomeUsuario.get(dto.getLogin());
      dto.setNomeUsuario(usuario.getNome());
      dto.setEmail(usuario.getEmail());
      dto.setLogin(usuario.getLogin());
    }
  }


}
