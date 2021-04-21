package br.gov.pbh.prodabel.hubsmsa.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.ContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.FiltroPesquisaContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.FiltroPesquisaLogContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.VisualizarContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.UserRevisionService;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ContatoEmpresaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.ContatoEmpresaMapper;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.RevisionMapper;
import br.gov.pbh.prodabel.hubsmsa.util.ContatoEmpresaUtil;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ResponseUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

// TODO: Auto-generated Javadoc
/**
 * Implementação do Service responsável por acessar os dados de Contato Empresa
 *
 * @author andre.moreira@ctis.com.br
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ContatoEmpresaService extends GenericService<Long, ContatoEmpresa> {

  @EJB
  private ContatoEmpresaDAO contatoEmpresaDAO;

  @EJB
  private UserRevisionService usuarioLogadoService;

  @EJB
  private UsuarioService usuarioService;

  /**
   * Retorna um {@linkPaginacaoPublicaDTO<VisualizarContatoEmpresaDTO>} contendo uma lista de
   * ContatoEmpresa.
   *
   * @param filtroPesquisa - Objeto que encapsula os atributos da entidade
   *        FiltroPesquisaContatoEmpresaDTO para consulta.
   *
   * @return {@link PaginacaoPublicaDTO<VisualizarContatoEmpresaDTO>} - Tipo de retorno da operação.
   *
   */
  public PaginacaoPublicaDTO<VisualizarContatoEmpresaDTO> consultarContatoEmpresa(
      final FiltroPesquisaContatoEmpresaDTO filtroPesquisa) {

    try {
      final PaginacaoPublicaDTO<VisualizarContatoEmpresaDTO> contatoEmpresas =
          new PaginacaoPublicaDTO<>();
      contatoEmpresas.setItens(ContatoEmpresaMapper
          .mapper(this.contatoEmpresaDAO.consultarContatoEmpresa(filtroPesquisa)));
      contatoEmpresas
          .setTotalRegistros(this.contatoEmpresaDAO.consultarTotalRegistros(filtroPesquisa));
      return contatoEmpresas;
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
    }

  }

  /**
   * Retorna um contatoEmpresa de visualização
   *
   * @param id - id do ContatoEmpresa
   *
   * @return {@link VisualizarContatoEmpresaDTO} - Tipo de retorno da operação.
   *
   */
  public VisualizarContatoEmpresaDTO consultarContatoEmpresa(final Long id) {
    try {
      final ContatoEmpresa contatoEmpresa = this.contatoEmpresaDAO.consultaContatoEmpresaPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(contatoEmpresa);
      return ContatoEmpresaMapper.mapper(contatoEmpresa);
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME022, id));
    } catch (final Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Cadastra um ContatoEmpresa
   *
   * @param cadastrarContatoEmpresaDTO - Objeto que encapsula os atributos da entidade
   *        ContatoEmpresa para cadastro
   *
   * @return {@link ResponseDTO<EntityDTO>} - Tipo de retorno da operação.
   *
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> cadastrarContatoEmpresa(
      final ContatoEmpresaDTO cadastrarContatoEmpresaDTO) {
    try {
      
      verificarExistenciaDeDados(cadastrarContatoEmpresaDTO);
      final ContatoEmpresa contatoEmpresa = ContatoEmpresaMapper.mapper(cadastrarContatoEmpresaDTO);
      this.contatoEmpresaDAO.merge(contatoEmpresa);
      
      return ResponseUtil
          .montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(contatoEmpresa.getId()));
      
    } catch (PersistenceException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Edita um ContatoEmpresa por Id
   *
   * @param id - Id do contatoEmpresa que deseja-se editar
   *
   * @param editarContatoEmpresaDTO - Objeto que encapsula os atributos da entidade ContatoEmpresa
   *        para uma edição
   *
   * @return {@link ResponseDTO<EntityDTO>} - Tipo de retorno da operação.
   *
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> editarContatoEmpresa(final Long id,
      final ContatoEmpresaDTO editarContatoEmpresaDTO) {
    try {
      verificarExistenciaDeDados(editarContatoEmpresaDTO, id);
      final ContatoEmpresa contatoEmpresa = this.contatoEmpresaDAO.consultarPorId(id);
      ValidadorUtil.validarRegistroNaoEncontrado(contatoEmpresa);
      ContatoEmpresaMapper.mapper(editarContatoEmpresaDTO, contatoEmpresa);
      
      this.contatoEmpresaDAO.merge(contatoEmpresa);
      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          new EntityDTO(contatoEmpresa.getId()));
      
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME022, id));
    } catch (PersistenceException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Exclui um determinado contato de empresa por Id
   *
   * @param id - id da empresa
   *
   * @throws NegocioException
   *
   */
  public ResponseDTO<EntityDTO> excluirContatoEmpresa(final Long id) {
    try {
      excluir(id);
      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG005),
          new EntityDTO(id));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME022, id));
    }
  }

  /**
   * Metodo responsavel por verificar a unidade de alguns dados referente a edição do contato da
   * empresa.
   *
   * @param editarContatoEmpresaDTO - DTO do Contato da empresa.
   * @param id - Identificador da empresa
   */
  private void verificarExistenciaDeDados(final ContatoEmpresaDTO editarContatoEmpresaDTO,
      final Long id) {
    verificarSeNomeExiste(editarContatoEmpresaDTO.getNome(), editarContatoEmpresaDTO.getIdEmpresa(),
        id);
    // verificarSeEmailExiste(editarContatoEmpresaDTO.getEmail(), id);
  }

  /**
   *
   * Metodo responsavel por verificar a unidade de alguns dados referente ao cadastro do contato da
   * empresa.
   *
   * @param editarContatoEmpresaDTO - DTO do Contato da empresa.
   */
  private void verificarExistenciaDeDados(final ContatoEmpresaDTO contatoEmpresaDTO) {
    verificarSeNomeExiste(contatoEmpresaDTO.getNome(), contatoEmpresaDTO.getIdEmpresa());
    // verificarSeEmailExiste(contatoEmpresaDTO.getEmail());
  }

  /**
   * Metodo responsavel por verificar se existe algum contato de empresa cadastrado com esse email
   *
   * @param email - email para verificar.
   */
  private void verificarSeEmailExiste(final String email) {
    try {
      if (this.contatoEmpresaDAO.verificarEmailExiste(email)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME023, email));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum contato de empresa cadastrado com esse nome
   *
   * @param nome - nome para verificar.
   * @param idEmpresa
   */
  private void verificarSeNomeExiste(final String nome, final Long idEmpresa) {
    try {
      if (this.contatoEmpresaDAO.verificarNomeExiste(nome, idEmpresa)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME024, nome));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.MSG001));
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum contato de empresa cadastrado com esse email e
   * que não tenha o id igual ao informado.
   *
   * @param email - email para verificar.
   * @param id - id para verificar.
   */
  private void verificarSeEmailExiste(final String email, final Long id) {
    try {
      if (this.contatoEmpresaDAO.verificarEmailExiste(email, id)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME023, email));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.MSG001));
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum contato de empresa cadastrado com esse nome e
   * que não tenha o id igual ao informado.
   *
   * @param nome - nome para verificar.
   * @param id - id para verificar.
   * @param id
   */
  private void verificarSeNomeExiste(final String nome, final Long idEmpresa, final Long id) {
    try {
      if (this.contatoEmpresaDAO.verificarNomeExiste(nome, idEmpresa, id)) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME024, nome));
      }
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.MSG001));
    }
  }

  /**
   * Metodo responsanvel por excluir todos os contatos referente a empresa que contem o id
   * informado.
   *
   * @param idEmpresa - identificador da empresa.
   * @throws DAOException
   */
  public void excluirContatosDessaEmpresa(final Long idEmpresa) {
    try {
      this.contatoEmpresaDAO.excluirContatosDessaEmpresa(idEmpresa);
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME015, idEmpresa));
    }

  }
  
  /**
   * Gerar excel.
   *
   * @param pesquisarContatoEmpresa the pesquisar contato empresa
   * @return the response
   */
  public Response gerarExcel(final FiltroPesquisaContatoEmpresaDTO pesquisarContatoEmpresa) {
	try {
		final Date dataGeracao = new Date();
		final List<VisualizarContatoEmpresaDTO> contatoEmpresas = ContatoEmpresaMapper
				.mapper(this.contatoEmpresaDAO.consultarContatoEmpresaSemPaginacao(pesquisarContatoEmpresa));
		final ResponseBuilder response = Response.ok(ContatoEmpresaUtil.gerarExcel(contatoEmpresas, dataGeracao));
		response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
		response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_CONTATO_EMPRESAS
				+ formatarNomeArquivoData(dataGeracao) + ConstanteUtil.ATTACHMENT_EXTENSION_XLSX);
		response.header(ConstanteUtil.ACCESS_CONTROL_EXPOSE_HEADERS, ConstanteUtil.CONTENT_DISPOSITION);
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
   * @param pesquisarContatoEmpresa the pesquisar contato empresa
   * @return the response
   */
  public Response gerarCsv(final FiltroPesquisaContatoEmpresaDTO pesquisarContatoEmpresa) {
	try {
		final List<VisualizarContatoEmpresaDTO> contatoEmpresas = ContatoEmpresaMapper
				.mapper(this.contatoEmpresaDAO.consultarContatoEmpresaSemPaginacao(pesquisarContatoEmpresa));
      final ResponseBuilder response = Response.ok(ContatoEmpresaUtil.gerarCsv(contatoEmpresas));
		response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
		response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_CONTATO_EMPRESAS
				+ formatarNomeArquivoData(new Date()) + ConstanteUtil.ATTACHMENT_EXTENSION_CSV);
		response.header(ConstanteUtil.ACCESS_CONTROL_EXPOSE_HEADERS, ConstanteUtil.CONTENT_DISPOSITION);
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
   * Consultar historico revisoes.
   *
   * @param filtroPesquisa the filtro pesquisa
   * @return the paginacao publica DTO
   */
  public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(FiltroPesquisaLogContatoEmpresaDTO filtroPesquisa) {
		try {
			PaginacaoPublicaDTO<HistoricoAlteracaoDTO> revisoes = new PaginacaoPublicaDTO<>();
			
			List<EnversDTO> enversDto = contatoEmpresaDAO.buscarHistoricoRevisoes(filtroPesquisa, ContatoEmpresaMapper.getAtributosValidos());
			
            usuarioService.adicionarNomeUsuario(enversDto);

			List<HistoricoAlteracaoDTO> historico = RevisionMapper.mapperRevisions(enversDto, filtroPesquisa);

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

      List<EnversDTO> enversDto = new ArrayList<EnversDTO>();
      try {
        FiltroPesquisaLogContatoEmpresaDTO filtroPesquisa =
            new FiltroPesquisaLogContatoEmpresaDTO();
        enversDto = contatoEmpresaDAO.buscarDetalheRevisao(id,
            ContatoEmpresaMapper.getAtributosValidos(), filtroPesquisa);
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
   * Gerar log csv.
   *
   * @param pesquisarTipoDePara the pesquisar tipo de para
   * @return the response
   */
  public Response gerarLogCsv(FiltroPesquisaLogContatoEmpresaDTO filtro) {
    try {

      List<HistoricoContatoEmpresaDTO> historicos =
          contatoEmpresaDAO.montarHistorico(contatoEmpresaDAO.consultarListaEntidades(filtro));

      adicionarNomeUsuario(historicos);

      Object gerarLogCsv = ContatoEmpresaUtil.gerarLogCsv(historicos);
      final ResponseBuilder response = Response.ok(gerarLogCsv);
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
      response.header(ConstanteUtil.CONTENT_DISPOSITION,
          ConstanteUtil.ATTACHMENT_FILENAME_LOG_CONTATO_EMPRESAS
              + formatarNomeArquivoData(new Date())
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
  public Response gerarLogExcel(FiltroPesquisaLogContatoEmpresaDTO filtro) {

    final Date dataGeracao = new Date();

    try {

      List<HistoricoContatoEmpresaDTO> historicos =
          contatoEmpresaDAO.montarHistorico(contatoEmpresaDAO.consultarListaEntidades(filtro));

      adicionarNomeUsuario(historicos);

      final ResponseBuilder response =
          Response.ok(ContatoEmpresaUtil.gerarExcelLog(historicos, dataGeracao));
      response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
      response.header(ConstanteUtil.CONTENT_DISPOSITION,
          ConstanteUtil.ATTACHMENT_FILENAME_LOG_CONTATO_EMPRESAS
              + formatarNomeArquivoData(dataGeracao)
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

  /**
   * Adicionar nome usuario.
   *
   * @param historicoDTO the historico DTO
   */
  private void adicionarNomeUsuario(List<HistoricoContatoEmpresaDTO> historicoDTO) {
    Set<String> logins = historicoDTO.stream().map(e -> e.getLogin()).collect(Collectors.toSet());
    Map<String, Usuario> nomeUsuario = new HashMap<>();
    for (String login : logins) {
      nomeUsuario.put(login, usuarioService.consultarUsuario(login));
    }

    for (HistoricoContatoEmpresaDTO dto : historicoDTO) {
      Usuario usuario = nomeUsuario.get(dto.getLogin());
      dto.setNomeUsuario(usuario.getNome());
      dto.setEmailUsuario(usuario.getEmail());
      dto.setLogin(usuario.getLogin());
    }
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

        case "nomeEmpresa":
          nomeCampo = "Empresa";
          break;
        case "nome":
          nomeCampo = "Nome do Contato";
          break;
        case "email":
          nomeCampo = "E-mail do Contato";
          break;
        case "setor":
          nomeCampo = "Setor do Contato";
          break;
        case "status":
          nomeCampo = "Contato Ativo";
          break;
        case "telefone":
          nomeCampo = "Telefone do Contato";
          break;
        default:
          return nomeCampo;
      }

      return nomeCampo;
    }

  public void setContatoEmpresaDAO(ContatoEmpresaDAO contatoEmpresaDAO) {
    this.contatoEmpresaDAO = contatoEmpresaDAO;
  }

  public void setUsuarioService(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  /**
   * Consultar contatos da empresa.
   *
   * @param idEmpresa the id empresa
   * @return the list
   */
  public List<SelecaoDTO> consultarContatosDaEmpresa(Long idEmpresa) {
    try {
      return ValidadorUtil
          .validarNoResultList(this.contatoEmpresaDAO.consultarContatosDaEmpresa(idEmpresa));
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME037));
    }
  }

}
