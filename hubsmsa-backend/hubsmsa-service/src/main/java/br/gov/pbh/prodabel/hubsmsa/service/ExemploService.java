package br.gov.pbh.prodabel.hubsmsa.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.CadastrarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.EditarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.FiltroPesquisaExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.VisualizarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.ExemploDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Exemplo;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.ExemploMapper;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.RevisionMapper;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ResponseUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ExemploService extends GenericService<Long, Exemplo> {

	@EJB
	private ExemploDAO exemploDAO;

	public PaginacaoPublicaDTO<VisualizarExemploDTO> consultarExemplo(FiltroPesquisaExemploDTO filtroPesquisa) {

		try {
			PaginacaoPublicaDTO<VisualizarExemploDTO> exemplos = new PaginacaoPublicaDTO<>();
			exemplos.setItens(ExemploMapper.mapper(exemploDAO.consultarExemplo(filtroPesquisa)));
			exemplos.setTotalRegistros(exemploDAO.consultarTotalRegistros(filtroPesquisa));
			return exemplos;
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseDTO<EntityDTO> cadastrarExemplo(CadastrarExemploDTO exemploDTO) {

		verificarSeExemploExiste(exemploDTO.getNomeExemplo());
		verificarSeCodigoExiste(exemploDTO.getCodigo());
		Exemplo exemplo = ExemploMapper.mapper(exemploDTO);
		exemploDAO.gravar(exemplo);
		return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002), new EntityDTO(exemplo.getId()));

	}

	private void verificarSeCodigoExiste(String codigo) {
		try {
			if (exemploDAO.verificarSeCodigoExiste(codigo)) {
				throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME006));
			}
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}

	private void verificarSeExemploExiste(String descricao) {
		try {
			if (exemploDAO.verificarNomeExistente(descricao)) {
				throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME006));
			}
		} catch (DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ResponseDTO<EntityDTO> editarExemplo(EditarExemploDTO editarExemploDTO, Long id) {
		try {
			Exemplo exemplo = exemploDAO.consultarPorId(id);
			ValidadorUtil.validarRegistroNaoEncontrado(exemplo);
			ExemploMapper.mapper(editarExemploDTO, exemplo);
			exemploDAO.merge(exemplo);
			return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
					new EntityDTO(exemplo.getId()));
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME003, id));
		}
	}

	public VisualizarExemploDTO consultarExemplo(Long id) {
		try {
			Exemplo exemplo = exemploDAO.consultarPorId(id);
			ValidadorUtil.validarRegistroNaoEncontrado(exemplo);

			return ExemploMapper.mapper(exemplo);
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME025, id));
		} catch (Exception e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}

	public ResponseDTO<EntityDTO> excluirExemplo(Long id) {
		try {
			Exemplo exemplo = exemploDAO.consultarPorId(id);
			ValidadorUtil.validarRegistroNaoEncontrado(exemplo);
			verificarTipoCadastro(exemplo);
			exemplo.setStatus(StatusEnum.I);
			exemploDAO.merge(exemplo);
			return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG005),
					new EntityDTO(exemplo.getId()));
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME025, id));
		}
	}

	private void verificarTipoCadastro(Exemplo exemplo) {
		if (FormaCadastroEnum.I.equals(exemplo.getFormaCadastro())
				|| FormaCadastroEnum.A.equals(exemplo.getFormaCadastro())) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME023));
		}
	}

	public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(Long id, FiltroRevisaoDTO revisaoDTO) {
		try {
			PaginacaoPublicaDTO<HistoricoAlteracaoDTO> revisoes = new PaginacaoPublicaDTO<>();
			List<HistoricoAlteracaoDTO> historico = RevisionMapper.mapperRevisions(
					exemploDAO.consultarEntidadesRevisaoPorId(id, ExemploMapper.getAtributosValidos()), revisaoDTO);

			revisoes.setItens(PaginacaoUtil.definirPaginacao(historico, revisaoDTO.getNumeroPagina(),
					revisaoDTO.getItensPorPagina()));
			revisoes.setTotalRegistros(PaginacaoUtil.definirTotalRegistros(historico));

			return revisoes;
		} catch (RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME026, id));
		} catch (Exception e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		}
	}

	public void setExemploDAO(ExemploDAO exemploDAO) {
		this.exemploDAO = exemploDAO;
	}
}
