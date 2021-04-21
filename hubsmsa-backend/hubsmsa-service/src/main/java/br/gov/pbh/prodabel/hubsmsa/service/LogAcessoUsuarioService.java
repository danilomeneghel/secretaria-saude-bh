package br.gov.pbh.prodabel.hubsmsa.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.FiltroPesquisaLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.VisualizarLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.LogAcessoUsuarioDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogAcessoUsuario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.LogAcessoUsuarioMapper;
import br.gov.pbh.prodabel.hubsmsa.util.LogAcessoUsuarioUtil;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;


// TODO: Auto-generated Javadoc
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class LogAcessoUsuarioService extends GenericService<Long, LogAcessoUsuario> {

  private static final String HORA_ZERO = " 00:00:00";
  private static final String HORA_23_59 = " 23:59:59";
	@EJB
	private LogAcessoUsuarioDAO logAcessoUsuarioDAO;
	
	/**
	 * Registrar log.
	 *
	 * @param usuario the usuario
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registrarLog(Usuario usuario) {
		LogAcessoUsuario logAcessoUsuario = new LogAcessoUsuario();
		logAcessoUsuario.setUsuario(usuario);
		logAcessoUsuario.setDataAcesso(new Timestamp(System.currentTimeMillis()));
		logAcessoUsuarioDAO.gravar(logAcessoUsuario);
	}
	
	/**
	 * Consultar log acesso usuario.
	 *
	 * @param filtroPesquisa the filtro pesquisa
	 * @return the paginacao publica DTO
	 */
	public PaginacaoPublicaDTO<VisualizarLogAcessoUsuarioDTO> consultarLogAcessoUsuario(final FiltroPesquisaLogAcessoUsuarioDTO filtroPesquisa) {
		try {
			final PaginacaoPublicaDTO<VisualizarLogAcessoUsuarioDTO> logAcessoUsuarios = new PaginacaoPublicaDTO<>();
			logAcessoUsuarios.setItens(LogAcessoUsuarioMapper.mapper(this.logAcessoUsuarioDAO.consultarLogAcessoUsuario(filtroPesquisa)));					
			logAcessoUsuarios.setTotalRegistros(this.logAcessoUsuarioDAO.consultarTotalRegistros(filtroPesquisa));
			return logAcessoUsuarios;
		} catch (final DAOException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
		} catch (final RegistroNaoEncontradoException e) {
			throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
		}
	}

    /**
     * Gerar csv.
     *
     * @param filtro the filtro
     * @return the response
     */
    public Response gerarCsv(FiltroPesquisaLogAcessoUsuarioDTO filtro) {
      try {

        List<VisualizarLogAcessoUsuarioDTO> dados = LogAcessoUsuarioMapper
            .mapper(logAcessoUsuarioDAO.consultarLogAcessoUsuarioSemPaginacao(filtro));

        Object gerarLogCsv = LogAcessoUsuarioUtil.gerarCsv(dados);
        final ResponseBuilder response = Response.ok(gerarLogCsv);
        response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_CSV);
        response.header(ConstanteUtil.CONTENT_DISPOSITION,
            ConstanteUtil.ATTACHMENT_FILENAME_LOG_ACESSO_USUARIO
                + TimeUtil.formatarNomeArquivoData(new Date())
                + ConstanteUtil.ATTACHMENT_EXTENSION_CSV);
        response.header(ConstanteUtil.ACCESS_CONTROL_EXPOSE_HEADERS,
            ConstanteUtil.CONTENT_DISPOSITION);
        return response.build();

      } catch (final RegistroNaoEncontradoException | DAOException e) {
        throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME002));
      }
    }

    /**
     * Gerar excel.
     *
     * @param filtroPesquisa the filtro pesquisa
     * @return the response
     */
    public Response gerarExcel(final FiltroPesquisaLogAcessoUsuarioDTO filtro) {
      try {
        final Date dataGeracao = new Date();
        final List<VisualizarLogAcessoUsuarioDTO> dados =
            LogAcessoUsuarioMapper
                .mapper(this.logAcessoUsuarioDAO.consultarLogAcessoUsuarioSemPaginacao(filtro));
        final ResponseBuilder response =
            Response.ok(LogAcessoUsuarioUtil.gerarExcel(dados, dataGeracao));
        response.header(ConstanteUtil.CONTENT_TYPE, ConstanteUtil.CONTENT_TYPE_DOCUMENT_XLSX);
        response.header(ConstanteUtil.CONTENT_DISPOSITION,
            ConstanteUtil.ATTACHMENT_FILENAME_LOG_ACESSO_USUARIO
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

    /**
     * Sets the log acesso usuario service DAO.
     *
     * @param logAcessoUsuarioDAO the new log acesso usuario service DAO
     */
    public void setlogAcessoUsuarioServiceDAO(LogAcessoUsuarioDAO logAcessoUsuarioDAO) {
      this.logAcessoUsuarioDAO = logAcessoUsuarioDAO;

    }

}
