package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PATH_USUARIO_ID;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_LOG_ACESSO_USUARIO;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.FiltroPesquisaLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoLogAcessoUsuarioEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogAcessoUsuario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogAcessoUsuario_;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
import br.gov.pbh.prodabel.hubsmsa.util.query.EntityQuery;

// TODO: Auto-generated Javadoc
/**
 * Classe DAO responsável por manipular os dados de LogAcessoUsuario
 *
 * @author andre.moreira@ctis.com.br
 */
@LocalBean
@Stateless
public class LogAcessoUsuarioDAO extends GenericoDAO<Long, LogAcessoUsuario> {
	
	private static final Logger LOG = LoggerFactory.getLogger(TipoDeParaDAO.class);
	
	/**
	 * Consultar log acesso usuario.
	 *
	 * @param filtroPesquisa the filtro pesquisa
	 * @return the list
	 * @throws DAOException the DAO exception
	 * @throws RegistroNaoEncontradoException the registro nao encontrado exception
	 */
	public List<LogAcessoUsuario> consultarLogAcessoUsuario(
			final FiltroPesquisaLogAcessoUsuarioDTO filtroPesquisa)
			throws DAOException, RegistroNaoEncontradoException {

		try {

			final String orderBy = ColunaOrdenacaoLogAcessoUsuarioEnum
					.valueOf(filtroPesquisa.getOrderBy().toUpperCase()).getName();
			
			final Integer paginaAtual = PaginacaoUtil.calcularPaginacaoAtual(filtroPesquisa.getNumeroPagina(),
					filtroPesquisa.getItensPorPagina());

			final List<LogAcessoUsuario> resultado = EntityQuery.create(getEntityManager(), LogAcessoUsuario.class)
					.innerJoinFetch(LogAcessoUsuario_.USUARIO)
                .greaterThanOrEqualsTo(LogAcessoUsuario_.DATA_ACESSO,
                    filtroPesquisa.getDataInicial())
                .lessThanOrEqualsTo(LogAcessoUsuario_.DATA_ACESSO,
                    filtroPesquisa.getDataFinal())
					.objectEqualsTo(PATH_USUARIO_ID, filtroPesquisa.getIdUsuario())					
					.addOrderBy(orderBy, filtroPesquisa.getTipoOrdenacao())
					.setFirstResult(paginaAtual).setMaxResults(filtroPesquisa.getItensPorPagina())
					.setMaxResults(filtroPesquisa.getItensPorPagina()).list();

			return ValidadorUtil.validarNoResultList(resultado);

		} catch (final PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_LOG_ACESSO_USUARIO, e);
			throw new DAOException();
		}
	}

    /**
     * Consultar log acesso usuario.
     *
     * @param filtroPesquisa the filtro pesquisa
     * @return the list
     * @throws DAOException the DAO exception
     * @throws RegistroNaoEncontradoException the registro nao encontrado exception
     */
    public List<LogAcessoUsuario> consultarLogAcessoUsuarioSemPaginacao(
        final FiltroPesquisaLogAcessoUsuarioDTO filtroPesquisa)
        throws DAOException, RegistroNaoEncontradoException {

      try {

        final String orderBy = ColunaOrdenacaoLogAcessoUsuarioEnum
            .valueOf(filtroPesquisa.getOrderBy().toUpperCase()).getName();

        final List<LogAcessoUsuario> resultado = EntityQuery
            .create(getEntityManager(), LogAcessoUsuario.class)
            .innerJoinFetch(LogAcessoUsuario_.USUARIO)
            .greaterThanOrEqualsTo(LogAcessoUsuario_.DATA_ACESSO,
                filtroPesquisa.getDataInicial())
            .lessThanOrEqualsTo(LogAcessoUsuario_.DATA_ACESSO, filtroPesquisa.getDataFinal())
            .objectEqualsTo(PATH_USUARIO_ID, filtroPesquisa.getIdUsuario())
            .addOrderBy(orderBy, filtroPesquisa.getTipoOrdenacao()).list();

        return ValidadorUtil.validarNoResultList(resultado);

      } catch (final PersistenceException e) {
        LOG.error(ERRO_AO_CONSULTAR_LOG_ACESSO_USUARIO, e);
        throw new DAOException();
      }
    }

	/**
	 * Consultar total registros.
	 *
	 * @param filtroPesquisa the filtro pesquisa
	 * @return the integer
	 * @throws DAOException the DAO exception
	 */
	public Integer consultarTotalRegistros(FiltroPesquisaLogAcessoUsuarioDTO filtroPesquisa) 
		throws DAOException {

		try {

			return EntityQuery.createCount(getEntityManager(), LogAcessoUsuario.class)
					.innerJoin(LogAcessoUsuario_.USUARIO)
                .greaterThanOrEqualsTo(LogAcessoUsuario_.DATA_ACESSO,
                    filtroPesquisa.getDataInicial())
                .lessThanOrEqualsTo(LogAcessoUsuario_.DATA_ACESSO,
                    filtroPesquisa.getDataFinal())
					.objectEqualsTo(PATH_USUARIO_ID, filtroPesquisa.getIdUsuario())
					.count().intValue();

		} catch (final PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_LOG_ACESSO_USUARIO, e);
			throw new DAOException();
		}
	}

}
