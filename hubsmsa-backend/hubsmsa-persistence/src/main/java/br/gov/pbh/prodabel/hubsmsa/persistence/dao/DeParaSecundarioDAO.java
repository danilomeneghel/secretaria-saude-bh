package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_OS_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ItensDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaLogDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ContatoEmpresaAud_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.DeParaSecundarioAud;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.DeParaSecundarioAud_;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
import br.gov.pbh.prodabel.hubsmsa.util.query.EntityQuery;

// TODO: Auto-generated Javadoc
@LocalBean
@Stateless
public class DeParaSecundarioDAO extends GenericoDAO<Long, DeParaSecundario> {
  
  private static final Logger LOG = LoggerFactory.getLogger(DeParaSecundarioDAO.class);

  protected static final String DEPARA_SECUNDARIO_REMOVIDA = "De/Para Secundário Removido";
  protected static final String DEPARA_SECUNDARIO_CRIADA = "De/Para Secundário Criado";

  /**
   * Excluir de para secundario do de para.
   *
   * @param idsDePara the ids de para
   * @throws DAOException the DAO exception
   */
  public void excluirDeParaSecundarioDoDePara(List<Long> idsDePara) throws DAOException {
    try {
       String qlString = new StringBuilder()
           .append("DELETE FROM DeParaSecundario dps WHERE dps.dePara.id in (:idDePara)")
           .toString();
       
       getEntityManager().createQuery(qlString).setParameter("idDePara", idsDePara).executeUpdate();
       
     } catch (final PersistenceException e) {
       LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
       throw new DAOException(e);
     }
    
  }
  
  /**
   * Excluir de para secundarios.
   *
   * @param idsDeParaSecundarios the ids de para secundarios
   * @throws DAOException the DAO exception
   */
  public void excluirDeParaSecundarios(List<Long> idsDeParaSecundarios) throws DAOException {
    try {

      String qlString = new StringBuilder()
          .append("DELETE FROM DeParaSecundario dpp WHERE dpp.id in (:idsDeParaSecundarios)")
          .toString();

      getEntityManager().createQuery(qlString).setParameter("idsDeParaSecundarios", idsDeParaSecundarios).executeUpdate();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
      throw new DAOException(e);
    }
  }
  
  /**
   * Consulta codigos secundarios.
   *
   * @param idDePara the id de para
   * @return the list
   * @throws DAOException the DAO exception
   */
  public List<DeParaSecundario> consultaCodigosSecundarios(Long idDePara) throws DAOException {
	    try {

	      String qlString = new StringBuilder()
              .append("FROM DeParaSecundario dps WHERE dps.dePara.id = :idDePara")
	          .toString();

	      List<DeParaSecundario> resultado = getEntityManager().createQuery(qlString).
	    		  setParameter("idDePara", idDePara).getResultList();
	      
	      return resultado;
	      
	    } catch (final PersistenceException e) {
	      LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
	      throw new DAOException(e);
	    }

	  }

      /**
       * Consulta codigos secundarios aud.
       *
       * @param idDePara the id de para
       * @param idRevisao the id revisao
       * @return the list
       * @throws DAOException the DAO exception
       */
      public List<DeParaSecundarioAud> consultaCodigosSecundariosAud(Long idDePara, Long idRevisao)
          throws DAOException {
        try {

          String qlString = new StringBuilder().append(
              "FROM DeParaSecundarioAud dps WHERE dps.dePara.id = :idDePara and dps.revisao.id = :idRevisao")
              .toString();

          List<DeParaSecundarioAud> resultado =
              getEntityManager().createQuery(qlString).setParameter("idDePara", idDePara)
                  .setParameter("idRevisao", idRevisao).getResultList();

          return resultado;

        } catch (final PersistenceException e) {
          LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
          throw new DAOException(e);
        }

      }

      /**
       * Consultar lista entidades.
       *
       * @param filtro the filtro
       * @return the list
       * @throws RegistroNaoEncontradoException the registro nao encontrado exception
       */
      @SuppressWarnings("unchecked")
      private List<ItensDeParaDTO> consultarListaEntidades(
          FiltroPesquisaLogDeParaDTO filtro)
          throws RegistroNaoEncontradoException {

        StringBuilder jpql = new StringBuilder(
            "SELECT new br.gov.pbh.prodabel.hubsmsa.dto.ItensDeParaDTO(deParaSecAud.id,"
                + " deParaSecAud.revisao,"
            + " deParaSecAud.revType," + " deParaSecAud.codigo," + " deParaSecAud.descricao,"
            + " (SELECT deParaAud.tipoDePara.nome FROM DeParaAud deParaAud"
            + "    WHERE deParaAud.revisao = (SELECT MAX(deParaAud2.revisao) "
                + "    FROM DeParaAud deParaAud2 WHERE deParaAud2.id = deParaSecAud.dePara)) as tipoDePara,"
                + " deParaSecAud.dePara.id as idDePara)")
                .append(" FROM DeParaSecundarioAud deParaSecAud," + " Revision revisao"
                    + " WHERE deParaSecAud.revisao = revisao.id");


        if (filtro.getId() != null) {
          jpql.append(" AND deParaSecAud.id = " + filtro.getId());
        }

        if (filtro.getCodigosDePara() != null) {
          jpql.append(" AND deParaSecAud.dePara.id IN (:idsDePara)");
        }

        jpql.append(" order by deParaSecAud.revisao.id");

        TypedQuery<ItensDeParaDTO> createQuery =
            getEntityManager().createQuery(jpql.toString(), ItensDeParaDTO.class);

        if (filtro.getCodigosDePara() != null) {
          createQuery.setParameter("idsDePara", filtro.getCodigosDePara());
        }

        List<ItensDeParaDTO> resultado = createQuery.getResultList();

        return ValidadorUtil.validarNoResultList(resultado);

      }

      /**
       * Buscar historico revisoes.
       *
       * @param filtro the filtro
       * @param atributos the atributos
       * @return the list
       * @throws RegistroNaoEncontradoException the registro nao encontrado exception
       * @throws DAOException the DAO exception
       */
      public List<EnversDTO> buscarHistoricoRevisoes(FiltroPesquisaLogDeParaDTO filtro,
          String[] atributos) throws RegistroNaoEncontradoException, DAOException {
        List<EnversDTO> listaDeRevisoes = new ArrayList<>();
        setAuditReader(AuditReaderFactory.get(getEntityManager()));

        DeParaSecundario entidade = new DeParaSecundario();
        List<ItensDeParaDTO> revisoesPorId = consultarListaEntidades(filtro);
        DeParaSecundario revisaoObjetoTemporario = null;

        try {
          iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario,
              entidade,
              atributos);

        } catch (Exception e) {
          LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
          throw new DAOException();
        }

        return listaDeRevisoes;
      }

      /**
       * Buscar detalhe revisao.
       *
       * @param id the id
       * @param atributosValidos the atributos validos
       * @param filtroPesquisa the filtro pesquisa
       * @return the list
       * @throws RegistroNaoEncontradoException the registro nao encontrado exception
       */
      public List<EnversDTO> buscarDetalheRevisao(Long id, String[] atributosValidos,
          FiltroPesquisaLogDeParaDTO filtroPesquisa) throws RegistroNaoEncontradoException {

        List<EnversDTO> listaDeRevisoes = new ArrayList<>();

        try {
          DeParaSecundario entidade = new DeParaSecundario();

          DeParaSecundario deParaSencundario =
              (DeParaSecundario) consultarEntidadePorIdRevisao(DeParaSecundario.class, id);

          if (deParaSencundario != null) {
            filtroPesquisa.setId(deParaSencundario.getId());

            List<ItensDeParaDTO> revisoesPorId = consultarListaEntidades(filtroPesquisa);

            DeParaSecundario revisaoObjetoTemporario = null;

            iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario,
                entidade,
                atributosValidos);
          }
        } catch (Exception e) {
          LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
          throw new RegistroNaoEncontradoException();
        }

        return listaDeRevisoes.stream().filter(e -> e.getIdRevision().equals(id))
            .collect(Collectors.toList());
      }

      /**
       * Iterar sobre revisoes.
       *
       * @param revisoesPorId the revisoes por id
       * @param listaDeRevisoes the lista de revisoes
       * @param revisaoObjetoTemporario the revisao objeto temporario
       * @param entidade the entidade
       * @param atributosValidos the atributos validos
       * @throws IllegalAccessException the illegal access exception
       * @throws CloneNotSupportedException the clone not supported exception
       * @throws InvocationTargetException the invocation target exception
       * @throws NoSuchMethodException the no such method exception
       */
      public void iterarSobreRevisoes(List<ItensDeParaDTO> revisoesPorId,
          List<EnversDTO> listaDeRevisoes,
          DeParaSecundario revisaoObjetoTemporario, DeParaSecundario entidade,
          String... atributosValidos) throws IllegalAccessException, CloneNotSupportedException,
          InvocationTargetException, NoSuchMethodException {

        HashMap<Long, Object> mapObjetosAnteriores = new HashMap<>();

        for (ItensDeParaDTO item : revisoesPorId) {
          DeParaSecundario objetoRevisao = new DeParaSecundario();
          objetoRevisao.setId(item.getId());
          Revision rev = item.getRevisao();
          RevisionType revType = item.getRevisaoType();
          objetoRevisao.setCodigo(item.getCodigo());
          objetoRevisao.setDescricao(item.getDescricao());

          revisaoObjetoTemporario =
              (DeParaSecundario) mapObjetosAnteriores.get(objetoRevisao.getId());

          if (revType != RevisionType.ADD && revisaoObjetoTemporario == null) {
            revisaoObjetoTemporario = consultarRevisaoAnterior(objetoRevisao.getId(), rev.getId());
          }

          Map<String, Object> listaCamposAlterados = new HashMap<>();

          if (revType == RevisionType.DEL) {
            for (String attr : atributosValidos) {
              listaCamposAlterados.put(attr, DEPARA_SECUNDARIO_REMOVIDA);
            }
          } else {
            listaCamposAlterados =
                consultarValorAlterado(revisaoObjetoTemporario, objetoRevisao, atributosValidos);
          }

          for (Entry<String, Object> itemAlterado : listaCamposAlterados.entrySet()) {
            EnversDTO revisaoDTO = mapearRevisaoDTO(itemAlterado, revType, rev, entidade,
                objetoRevisao.getId(), revisaoObjetoTemporario);
            revisaoDTO.setTipoDePara(item.getTipoDePara());
            formatarValores(revisaoDTO);

            listaDeRevisoes.add(revisaoDTO);
          }

          mapObjetosAnteriores.put(objetoRevisao.getId(), objetoRevisao);
        }

      }

      /**
       * Consultar revisao anterior.
       *
       * @param idDeParaSecmario the id de para secundario
       * @param idRevisaoAtual the id revisao atual
       * @return the de para secundario
       */
      private DeParaSecundario consultarRevisaoAnterior(Long idDeParaSecundario,
          Long idRevisaoAtual) {

        final String orderBy = ContatoEmpresaAud_.REVISAO;

        DeParaSecundarioAud deParaSecundarioAud =
            EntityQuery.create(getEntityManager(), DeParaSecundarioAud.class)
                .objectEqualsTo(DeParaSecundarioAud_.ID, idDeParaSecundario)
                .objectNotEqualsTo(DeParaSecundarioAud_.REVISAO, idRevisaoAtual)
                .addOrderBy(orderBy, "DESC").setMaxResults(1).uniqueResult();

        DeParaSecundario deParaSecundario = new DeParaSecundario();
        deParaSecundario.setCodigo(deParaSecundarioAud.getCodigo());
        deParaSecundario.setDescricao(deParaSecundarioAud.getDescricao());

        return deParaSecundario;

      }

      /**
       * Formatar valores.
       *
       * @param revisaoDTO the revisao DTO
       */
      private void formatarValores(EnversDTO revisaoDTO) {

        if (revisaoDTO.getRevisionType() == RevisionType.ADD)
          revisaoDTO.setOldValue(DEPARA_SECUNDARIO_CRIADA);
        if (revisaoDTO.getRevisionType() == RevisionType.DEL)
          revisaoDTO.setValue(DEPARA_SECUNDARIO_REMOVIDA);

      }


}
