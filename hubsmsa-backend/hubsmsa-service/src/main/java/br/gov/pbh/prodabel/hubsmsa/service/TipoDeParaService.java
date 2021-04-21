package br.gov.pbh.prodabel.hubsmsa.service;

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
import org.apache.commons.collections.CollectionUtils;
import br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaLogTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.TipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.VisualizarTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.TipoDeParaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.TipoDePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.TipoDePara_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.RevisionMapper;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.TipoDeParaMapper;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ResponseUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TipoDeParaUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

// TODO: Auto-generated Javadoc
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TipoDeParaService extends GenericService<Long, TipoDePara> {

	@EJB
	private TipoDeParaDAO tipoDeParaDAO;

	@EJB
	private DeParaService deParaService;

  @EJB
  private UsuarioService usuarioService;

	/**
	 * Retorna um {@link PaginacaoPublicaDTO<VisualizarTipoDeParaDTO>} contendo uma
	 * lista de TipoDePara.
	 *
	 * @param filtroPesquisa - Objeto que encapsula os atributos da entidade
	 *                       FiltroPesquisaTipoDeParaDTO para consulta.
	 * @return {@link PaginacaoPublicaDTO<VisualizarTipoDeParaDTO>} - Tipo de
	 *         retorno da operação.
	 */
	public PaginacaoPublicaDTO<VisualizarTipoDeParaDTO> consultarTiposDePara(
			FiltroPesquisaTipoDeParaDTO filtroPesquisa) {

		try {

			PaginacaoPublicaDTO<VisualizarTipoDeParaDTO> tiposDePara = new PaginacaoPublicaDTO<>();
			tiposDePara.setItens(TipoDeParaMapper.mapper(tipoDeParaDAO.consultarSistema(filtroPesquisa)));
			tiposDePara.setTotalRegistros(tipoDeParaDAO.consultarTotalRegistros(filtroPesquisa));
			return tiposDePara;

		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
		}

	}

	/**
	 * Cadastra um TipoDePara
	 *
	 * @param cadastrartTipoDeParaDTO - Objeto que encapsula os atributos da
	 *                                entidade Sistema para cadastro
	 * @return {@link ResponseDTO<EntityDTO>} - Tipo de retorno da operação.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseDTO<EntityDTO> cadastrarTipoDePara(TipoDeParaDTO cadastrartTipoDeParaDTO) {

		verificarExitenciaTipoDePara(cadastrartTipoDeParaDTO.getNome());
		TipoDePara tipoDePara = TipoDeParaMapper.mapper(cadastrartTipoDeParaDTO);
		tipoDeParaDAO.gravar(tipoDePara);
		return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
				new EntityDTO(tipoDePara.getId()));

	}

	/**
	 * Verificar exitencia tipo de para.
	 *
	 * @param nome the nome
	 */
	private void verificarExitenciaTipoDePara(String nome) {
		try {
      if (Boolean.TRUE.equals(tipoDeParaDAO.verificarExitenciaTipoDePara(nome))) {
				throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME026, nome));
			}
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}

	}

	/**
	 * Edita um TipoDePara por Id
	 *
	 * @param id                  - Id do Tipo De/Para que deseja-se editar
	 * @param editarTipoDeParaDTO - Objeto que encapsula os atributos da entidade
	 *                            TipoDePara para uma edição
	 * @return {@link ResponseDTO<EntityDTO>} - Tipo de retorno da operação.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseDTO<EntityDTO> editarTipoDePara(Long id, TipoDeParaDTO editarTipoDeParaDTO) {
		try {
			TipoDePara tipoDePara = tipoDeParaDAO.consultarPorId(id);
			ValidadorUtil.validarRegistroNaoEncontrado(tipoDePara);
			TipoDeParaMapper.mapper(editarTipoDeParaDTO, tipoDePara);
			tipoDeParaDAO.merge(tipoDePara);

			return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
					new EntityDTO(tipoDePara.getId()));

		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME025, id));
		} catch (PersistenceException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}

	/**
	 * Retorna um TipoDePara de visualização
	 *
	 * @param id - id do TipoDePara
	 * @return {@link VisualizarTipoDeParaDTO} - Tipo de retorno da operação.
	 */
	public VisualizarTipoDeParaDTO consultarTipoDePara(Long id) {
		try {
			TipoDePara tipoDePara = tipoDeParaDAO.consultarPorId(id);
			ValidadorUtil.validarRegistroNaoEncontrado(tipoDePara);

			return TipoDeParaMapper.mapper(tipoDePara);
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME025, id));
		} catch (Exception e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}

	/**
	 * Exclui um determinado TipoDePara por Id
	 *
	 * @param id - id do TipoDePara
	 * @throws NegocioException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseDTO<EntityDTO> excluirTipoDePara(Long idTipoDePara) {
		try {
			List<DePara> deParas = this.deParaService.consultarDeParaDoTipoDePara(idTipoDePara);
			if (CollectionUtils.isNotEmpty(deParas)) {
				List<Long> idsDePara = deParas.stream().map(DePara::getId).collect(Collectors.toList());
				this.deParaService.excluir(idsDePara);
			}
			excluir(idTipoDePara);

			return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG005),
					new EntityDTO(idTipoDePara));

		} catch (Exception e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}

	/**
	 * Gerar excel.
	 *
	 * @param filtroPesquisaTipoDePara the filtro pesquisa tipo de para
	 * @return the response
	 */
	public Response gerarExcel(final FiltroPesquisaTipoDeParaDTO filtroPesquisaTipoDePara) {
		try {
			final Date dataGeracao = new Date();
			final List<VisualizarTipoDeParaDTO> tiposDePara = TipoDeParaMapper
					.mapper(this.tipoDeParaDAO.consultarSistemaSemPaginacao(filtroPesquisaTipoDePara));
			final ResponseBuilder response = Response.ok(TipoDeParaUtil.gerarExcel(tiposDePara, dataGeracao));
			response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
			response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_TIPOSDEPARA
                + TimeUtil.formatarNomeArquivoData(dataGeracao)
                + ConstanteUtil.ATTACHMENT_EXTENSION_XLSX);
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
	 * @param filtroPesquisaTipoDePara the filtro pesquisa tipo de para
	 * @return the response
	 */
	public Response gerarCsv(final FiltroPesquisaTipoDeParaDTO filtroPesquisaTipoDePara) {
		try {
			final List<VisualizarTipoDeParaDTO> tiposDePara = TipoDeParaMapper
					.mapper(this.tipoDeParaDAO.consultarSistemaSemPaginacao(filtroPesquisaTipoDePara));
      final ResponseBuilder response = Response.ok(TipoDeParaUtil.gerarCsv(tiposDePara));
			response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
			response.header(ConstanteUtil.CONTENT_DISPOSITION, ConstanteUtil.ATTACHMENT_FILENAME_TIPOSDEPARA
                + TimeUtil.formatarNomeArquivoData(new Date())
                + ConstanteUtil.ATTACHMENT_EXTENSION_CSV);
			response.header(ConstanteUtil.ACCESS_CONTROL_EXPOSE_HEADERS, ConstanteUtil.CONTENT_DISPOSITION);
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
		return tipoDeParaDAO.consultarSelecao();
	}
	
	/**
	 * Consultar log tipo de para.
	 *
	 * @param filtroPesquisa the filtro pesquisa
	 * @return the paginacao publica DTO
	 */
    public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarLogTipoDePara(
        FiltroPesquisaLogTipoDeParaDTO filtroPesquisa) {
		try {
			PaginacaoPublicaDTO<HistoricoAlteracaoDTO> revisoes = new PaginacaoPublicaDTO<>();
			
			List<EnversDTO> enversDto = tipoDeParaDAO.buscarHistoricoRevisoes(filtroPesquisa, TipoDeParaMapper.getAtributosValidos());
			
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

      List<EnversDTO> enversDto = new ArrayList<>();
      try {
        enversDto = tipoDeParaDAO.buscarDetalheRevisao(id, TipoDeParaMapper.getAtributosValidos());
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
    public Response gerarLogCsv(FiltroPesquisaLogTipoDeParaDTO filtro) {
      try {

        List<HistoricoTipoDeParaDTO> historicos =
            tipoDeParaDAO.montarHistorico(tipoDeParaDAO.consultarListaEntidades(filtro));

        adicionarNomeUsuario(historicos);

        Object gerarLogCsv = TipoDeParaUtil.gerarLogCsv(historicos);
        final ResponseBuilder response = Response.ok(gerarLogCsv);
        response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
        response.header(ConstanteUtil.CONTENT_DISPOSITION,
            ConstanteUtil.ATTACHMENT_FILENAME_LOG_TIPODEPARA
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
     * Gerar historico excel.
     *
     * @param pesquisarTipoDePara the pesquisar tipo de para
     * @return the response
     */
    public Response gerarLogExcel(FiltroPesquisaLogTipoDeParaDTO filtro) {
      try {
        final Date dataGeracao = new Date();

        List<HistoricoTipoDeParaDTO> historicos =
            tipoDeParaDAO.montarHistorico(tipoDeParaDAO.consultarListaEntidades(filtro));

        adicionarNomeUsuario(historicos);

        final ResponseBuilder response =
            Response.ok(TipoDeParaUtil.gerarExcelLog(historicos, dataGeracao));
        response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
        response.header(ConstanteUtil.CONTENT_DISPOSITION,
            ConstanteUtil.ATTACHMENT_FILENAME_LOG_TIPODEPARA
                + TimeUtil.formatarNomeArquivoData(dataGeracao)
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

    public void setTipoDeParaDAO(TipoDeParaDAO tipoDeParaDAO) {
      this.tipoDeParaDAO = tipoDeParaDAO;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
      this.usuarioService = usuarioService;
    }


    /**
     * Adicionar nome usuario.
     *
     * @param historicoDTO the historico DTO
     */
    private void adicionarNomeUsuario(List<HistoricoTipoDeParaDTO> historicoDTO) {
      Set<String> logins = historicoDTO.stream().map(e -> e.getLogin())
          .collect(Collectors.toSet());
      Map<String, Usuario> nomeUsuario = new HashMap<>();
      for (String login : logins) {
        nomeUsuario.put(login, usuarioService.consultarUsuario(login));
      }

      for (HistoricoTipoDeParaDTO dto : historicoDTO) {
        Usuario usuario = nomeUsuario.get(dto.getLogin());
        dto.setNomeUsuario(usuario.getNome());
        dto.setEmail(usuario.getEmail());
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

        case TipoDePara_.NOME:
          nomeCampo = "Nome do Tipo";
          break;
        case TipoDePara_.DESCRICAO:
          nomeCampo = "Descrição";
          break;
        case TipoDePara_.STATUS:
          nomeCampo = "Ativo";
          break;
        default:
          return nomeCampo;
      }

      return nomeCampo;
    }


}
