package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PARAM_USERNAME;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_PROCURAR_USUARIO;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

// TODO: Auto-generated Javadoc
/**
 * Classe DAO responsável por manipular os dados de Usuario
 *
 * @author andre.moreira@ctis.com.br
 */
@LocalBean
@Stateless
public class UsuarioDAO extends GenericoDAO<Long, Usuario> {

  private static final Logger LOG = LoggerFactory.getLogger(UsuarioDAO.class);

  /**
   * Verificar cadastro usuario.
   *
   * @param username the username
   * @return the boolean
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public Boolean verificarCadastroUsuario(String username) throws DAOException, RegistroNaoEncontradoException {
    try {
      StringBuilder jpql = new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
          .append(" FROM Usuario usuario where usuario.login = :username");
      
      return getEntityManager()
      .createQuery(jpql.toString(), Boolean.class)
      .setParameter(PARAM_USERNAME, username)
          .getSingleResult();
    
    } catch (PersistenceException e) {
      LOG.error(ERRO_AO_PROCURAR_USUARIO, e);
      throw new DAOException();
    }
  }

  /**
   * Consultar por login.
   *
   * @param username the username
   * @return the usuario
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public Usuario consultarPorLogin(String username) throws DAOException, RegistroNaoEncontradoException {
    try {
      StringBuilder jpql = new StringBuilder("SELECT usuario FROM Usuario usuario where usuario.login = :username");
      
      return getEntityManager()
          .createQuery(jpql.toString(), Usuario.class)
          .setParameter(PARAM_USERNAME, username)
          .getSingleResult();
      
    } catch (PersistenceException e) {
      LOG.error(ERRO_AO_PROCURAR_USUARIO, e);
      throw new DAOException();
    }
  }
  
  /**
   * Consultar selecao usuario.
   *
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<SelecaoDTO> consultarSelecaoUsuario() throws DAOException, RegistroNaoEncontradoException {
		try {
			final StringBuilder builder = new StringBuilder();
			builder.append("SELECT new br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO(e.id, e.nome) ");
			builder.append("FROM Usuario e ");
			builder.append("ORDER BY e.nome");

			final List<SelecaoDTO> resultado = getEntityManager().createQuery(builder.toString(), SelecaoDTO.class)
					.getResultList();

			return ValidadorUtil.validarNoResultList(resultado);

		} catch (final PersistenceException e) {
			LOG.error(ERRO_AO_PROCURAR_USUARIO, e);
			throw new DAOException();
		}
   }

}
