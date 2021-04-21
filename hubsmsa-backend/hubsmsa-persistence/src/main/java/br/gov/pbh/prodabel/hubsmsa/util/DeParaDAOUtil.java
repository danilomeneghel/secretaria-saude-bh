package br.gov.pbh.prodabel.hubsmsa.util;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PORCENTAGEM;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.CampoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoDeParaEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.TipoDePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.TipoDePara_;

/**
 * 
 * @author claudivan.moreira
 *
 */
public final class DeParaDAOUtil {

  private DeParaDAOUtil() {}
  private static String STRING_VAZIA = "";

  
  public static String montarParametrosMaisDeUmCodigoPrimario(FiltroPesquisaDeParaDTO filtroPesquisaDePara) {
	  
	  StringBuilder sb = new StringBuilder();
	  sb.append("SELECT dp FROM DePara dp");
	  sb.append(" INNER JOIN FETCH dp.sistemaPrimario AS sistemaPrimario ");
	  sb.append(" INNER JOIN FETCH dp.sistemaSecundario AS sistemaSecundario ");
	  sb.append(" INNER JOIN FETCH sistemaPrimario.empresa ");
	  sb.append(" INNER JOIN FETCH sistemaSecundario.empresa ");
	  sb.append(" INNER JOIN FETCH dp.tipoDePara ");

	  sb.append(" WHERE 1 = 1");
	  
	  /*Filtros De Para Inicial*/
	  if(null != filtroPesquisaDePara.getIdTipoDePara()) {
		  sb.append(" AND dp.tipoDePara = ").append(filtroPesquisaDePara.getIdTipoDePara());
	  }
	  if (null != filtroPesquisaDePara.getNomeDePara() && !STRING_VAZIA.equals(filtroPesquisaDePara.getNomeDePara())){
		  sb.append(" AND UPPER (dp.nome) LIKE '%").append(filtroPesquisaDePara.getNomeDePara().toUpperCase()).append("%'");
	  }
	  if(null != filtroPesquisaDePara.getStatus() && !filtroPesquisaDePara.getStatus().isEmpty()){
		  sb.append(" AND dp.status IN ('");
		  for(String status : filtroPesquisaDePara.getStatus()) {
			  sb.append(status).append("','");
		  }
		  sb = sb.delete(sb.length() - 2, sb.length());
		  sb.append(")");
	  }
	  /*Filtros De Para -> Sistema Primário*/
	  if(null != filtroPesquisaDePara.getSistemaPrimario().getIdEmpresa()) {
		  sb.append(" AND dp.sistemaPrimario = sistemaPrimario "
		  		+ " AND sistemaPrimario.empresa = ").append(filtroPesquisaDePara.getSistemaPrimario().getIdEmpresa());
		  if(null != filtroPesquisaDePara.getSistemaPrimario().getIdSistema()) {
			  sb.append(" AND dp.sistemaPrimario = ").append(filtroPesquisaDePara.getSistemaPrimario().getIdSistema());
		  }
	  }
		if (null != filtroPesquisaDePara.getSistemaPrimario().getCampos()
				&& !filtroPesquisaDePara.getSistemaPrimario().getCampos().isEmpty()) {
			for (CampoDeParaDTO codigosPrimarios : filtroPesquisaDePara.getSistemaPrimario().getCampos()) {
				if (null != codigosPrimarios.getCodigo()) {
					sb.append(" AND dp.id = (SELECT dpp.idDePara FROM DeParaPrimario dpp "
							+ " WHERE dp.id = dpp.idDePara AND dpp.codigo = '")
							.append(codigosPrimarios.getCodigo()).append("')");
				}
				if (null != codigosPrimarios.getDescricao() && !STRING_VAZIA.equals(codigosPrimarios.getDescricao())) {
					sb.append(" AND dp.id = (SELECT dpp.idDePara FROM DeParaPrimario dpp "
							+ " WHERE dp.id = dpp.idDePara AND UPPER (dpp.descricao) LIKE '%")
							.append(codigosPrimarios.getDescricao().toUpperCase()).append("%')");
				}
			}

		}
	  /*Filtros De Para -> Sistema Secundário*/
	  if(null != filtroPesquisaDePara.getSistemaSecundario().getIdEmpresa()) {
		  sb.append(" AND dp.sistemaSecundario = sistemaSecundario "
		  		+ " AND sistemaSecundario.empresa = ").append(filtroPesquisaDePara.getSistemaSecundario().getIdEmpresa());
		  if(null != filtroPesquisaDePara.getSistemaSecundario().getIdSistema()) {
			  sb.append(" AND dp.sistemaSecundario = ").append(filtroPesquisaDePara.getSistemaSecundario().getIdSistema());
		  }
	  }
		if (null != filtroPesquisaDePara.getSistemaSecundario().getCampos()
				&& !filtroPesquisaDePara.getSistemaSecundario().getCampos().isEmpty()) {
			for (CampoDeParaDTO codigosSecundarios : filtroPesquisaDePara.getSistemaSecundario().getCampos()) {
				if (null != codigosSecundarios.getCodigo()) {
					sb.append(" AND dp.id = (SELECT dps.idDePara FROM DeParaSecundario dps "
							+ " WHERE dp = dps.idDePara AND dps.codigo = '")
							.append(codigosSecundarios.getCodigo()).append("')");
				}
				if (null != codigosSecundarios.getDescricao() && !STRING_VAZIA.equals(codigosSecundarios.getDescricao())) {
					sb.append(" AND dp.id = (SELECT dps.idDePara FROM DeParaSecundario dps "
							+ " WHERE dp.id = dps.idDePara AND UPPER (dps.descricao) LIKE '%")
							.append(codigosSecundarios.getDescricao().toUpperCase()).append("%')");
				}
			}
		}
	  return sb.toString();
  }
 
  /**
   * Monta os predicates com base nos campos enviados para pesquisa
   * 
   * @param root Root da query
   * @param criteriaBuilder Instancia de CriteriaBuilder
   * @param filtroPesquisaDePara DTO da pesquisa
   * @return Lista de predicados para ser aplicados na consulta
   */
  public static List<Predicate> montarParametros(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {

    final List<Predicate> parametros = new ArrayList<>();

    parametros.add(getPredicateTipoDePara(root, criteriaBuilder, filtroPesquisaDePara));
    parametros.add(getPredicateEmpresaPrimaria(root, criteriaBuilder, filtroPesquisaDePara));
    parametros.add(getPredicateSistemaPrimario(root, criteriaBuilder, filtroPesquisaDePara));
    parametros.add(getPredicateEmpresaSecundaria(root, criteriaBuilder, filtroPesquisaDePara));
    parametros.add(getPredicateSistemaSecundario(root, criteriaBuilder, filtroPesquisaDePara));
    parametros.add(getPredicateNomeDePara(root, criteriaBuilder, filtroPesquisaDePara));
    parametros.add(getPredicateStatusDePara(root, criteriaBuilder, filtroPesquisaDePara));

    parametros
        .addAll(getPredicateCodigosCamposPrimarios(root, criteriaBuilder, filtroPesquisaDePara));
    parametros
        .addAll(getPredicateDescricaoCamposPrimarios(root, criteriaBuilder, filtroPesquisaDePara));

    parametros
        .addAll(getPredicateCodigosCamposSecundarios(root, criteriaBuilder, filtroPesquisaDePara));
    parametros.addAll(
        getPredicateDescricaoCamposSecundarios(root, criteriaBuilder, filtroPesquisaDePara));

    // remove null predicates
    parametros.removeIf(o -> Objects.isNull(o));

    return parametros;
  }

  /**
   * Monta os predicates para a ordenacao do resultado da consulta
   * 
   * @param root Root da query
   * @param criteriaBuilder Instancia de CriteriaBuilder
   * @param filtroPesquisaDePara DTO da pesquisa
   * @return Lista de predicados para ordenacao
   */
  @SuppressWarnings("unchecked")
  public static List<Order> montarListaDeOrdenacao(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {

    final List<Order> orderList = new ArrayList<>();

    ColunaOrdenacaoDeParaEnum orderColumn =
        EnumUtils.getEnumMap(ColunaOrdenacaoDeParaEnum.class).get(filtroPesquisaDePara.getColuna());

    List<Expression<?>> expressions = new ArrayList<>();

    switch (orderColumn) {
      case NOME_TIPO_DEPARA: {
        Join<DePara, TipoDePara> joinTipoDePara = root.join(DePara_.tipoDePara);
        expressions.add(joinTipoDePara.get(TipoDePara_.nome));
        break;
      }
      case SISTEMA_PRIMARIO: {
        Join<DePara, Sistema> joinSistemaPrimario =
            (Join<DePara, Sistema>) root.fetch(DePara_.sistemaPrimario);
        expressions.add(joinSistemaPrimario.get(Sistema_.nome));
        expressions.add(joinSistemaPrimario.get(Sistema_.empresa).get(Empresa_.nomeFantasia));
        break;
      }
      case SISTEMA_SECUNDARIO: {
        Join<DePara, Sistema> joinSistemaSecundario =
            (Join<DePara, Sistema>) root.fetch(DePara_.sistemaSecundario, JoinType.INNER);
        expressions.add(joinSistemaSecundario.get(Sistema_.nome));
        expressions.add(joinSistemaSecundario.get(Sistema_.empresa).get(Empresa_.nomeFantasia));
        break;
      }
      default: {
        expressions.add(root.get(DePara_.nome));
        break;
      }
    }

    if (filtroPesquisaDePara.getOrdem().equals(TipoOrdenacaoEnum.ASC.toString())) {
      expressions.forEach(e -> orderList.add(criteriaBuilder.asc(e)));
    } else if (filtroPesquisaDePara.getOrdem().equals(TipoOrdenacaoEnum.DESC.toString())) {
      expressions.forEach(e -> orderList.add(criteriaBuilder.desc(e)));
    }

    return orderList;
  }

  private static List<Predicate> getPredicateCodigosCamposSecundarios(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {

    if (Objects.nonNull(filtroPesquisaDePara.getSistemaSecundario())
        && CollectionUtils.isNotEmpty(filtroPesquisaDePara.getSistemaSecundario().getCampos())) {

      final Join<DePara, DeParaSecundario> joinDeParaDeParaSecundario =
          root.join(DePara_.deParaSecundario);

      final List<CampoDeParaDTO> listaDescricaoPrimario =
          filtroPesquisaDePara.getSistemaPrimario().getCampos().stream()
              .filter(item -> Objects.nonNull(item.getCodigo())).collect(Collectors.toList());

      return listaDescricaoPrimario.stream().map(item -> {


        return criteriaBuilder.equal(joinDeParaDeParaSecundario.get(DeParaSecundario_.codigo),
            item.getCodigo());

      }).collect(Collectors.toList());

    }

    return Collections.emptyList();
  }

  private static List<Predicate> getPredicateCodigosCamposPrimarios(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {


    if (Objects.nonNull(filtroPesquisaDePara.getSistemaPrimario())
        && CollectionUtils.isNotEmpty(filtroPesquisaDePara.getSistemaPrimario().getCampos())) {

      final Join<DePara, DeParaPrimario> joinDeParaDeParaPrimario =
          root.join(DePara_.deParaPrimario);

      final List<CampoDeParaDTO> listaDescricaoPrimario =
          filtroPesquisaDePara.getSistemaPrimario().getCampos().stream()
              .filter(item -> Objects.nonNull(item.getCodigo())).collect(Collectors.toList());

      return listaDescricaoPrimario.stream().map(item -> {
        return criteriaBuilder.equal(joinDeParaDeParaPrimario.get(DeParaPrimario_.codigo),
            item.getCodigo());

      }).collect(Collectors.toList());

    }

    return Collections.emptyList();
  }

  private static List<Predicate> getPredicateDescricaoCamposSecundarios(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {

    if (Objects.nonNull(filtroPesquisaDePara.getSistemaSecundario())
        && CollectionUtils.isNotEmpty(filtroPesquisaDePara.getSistemaSecundario().getCampos())) {

      final Join<DePara, DeParaSecundario> joinDeParaDeParaSecundario =
          root.join(DePara_.deParaSecundario);

      final List<CampoDeParaDTO> listaDescricaoSecundario = filtroPesquisaDePara
          .getSistemaSecundario().getCampos().stream()
          .filter(item -> StringUtils.isNotBlank(item.getDescricao())).collect(Collectors.toList());

      return listaDescricaoSecundario.stream().map(item -> {
        final String likePattern = new StringBuilder().append(PORCENTAGEM)
            .append(item.getDescricao().trim().toUpperCase()).append(PORCENTAGEM).toString();

        return criteriaBuilder.like(
            criteriaBuilder.upper(joinDeParaDeParaSecundario.get(DeParaSecundario_.descricao)),
            likePattern);

      }).collect(Collectors.toList());

    }

    return Collections.emptyList();
  }



  private static List<Predicate> getPredicateDescricaoCamposPrimarios(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {

    if (Objects.nonNull(filtroPesquisaDePara.getSistemaPrimario())
        && CollectionUtils.isNotEmpty(filtroPesquisaDePara.getSistemaPrimario().getCampos())) {

      final Join<DePara, DeParaPrimario> joinDeParaDeParaPrimario =
          root.join(DePara_.deParaPrimario);

      final List<CampoDeParaDTO> listaDescricaoPrimario = filtroPesquisaDePara.getSistemaPrimario()
          .getCampos().stream().filter(item -> StringUtils.isNotBlank(item.getDescricao()))
          .collect(Collectors.toList());

      return listaDescricaoPrimario.stream().map(item -> {
        final String likePattern = new StringBuilder().append(PORCENTAGEM)
            .append(item.getDescricao().trim().toUpperCase()).append(PORCENTAGEM).toString();

        return criteriaBuilder.like(
            criteriaBuilder.upper(joinDeParaDeParaPrimario.get(DeParaPrimario_.descricao)),
            likePattern);

      }).collect(Collectors.toList());

    }

    return Collections.emptyList();
  }

  private static Predicate getPredicateStatusDePara(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {
    if (filtroPesquisaDePara.getStatus() != null) {
      final In<StatusEnum> inStatusList = criteriaBuilder.in(root.get(DePara_.status));
      EnumUtil.toStatusEnumList(filtroPesquisaDePara.getStatus()).stream()
          .forEach(value -> inStatusList.value(value));

      return inStatusList;
    }
    return null;
  }

  private static Predicate getPredicateNomeDePara(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {
    if (filtroPesquisaDePara.getNomeDePara() != null) {
      return criteriaBuilder.like(criteriaBuilder.upper(root.get(DePara_.nome)),
          new StringBuilder().append(PORCENTAGEM)
              .append(filtroPesquisaDePara.getNomeDePara().trim().toUpperCase()).append(PORCENTAGEM)
              .toString());
    }
    return null;
  }

  private static Predicate getPredicateSistemaSecundario(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {
    if (Objects.nonNull(filtroPesquisaDePara.getSistemaSecundario())
        && Objects.nonNull(filtroPesquisaDePara.getSistemaSecundario().getIdSistema())) {
      return criteriaBuilder.equal(root.get(DePara_.sistemaSecundario).get(Sistema_.id),
          filtroPesquisaDePara.getSistemaSecundario().getIdSistema());
    }
    return null;
  }

  private static Predicate getPredicateEmpresaSecundaria(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {
    if (Objects.nonNull(filtroPesquisaDePara.getSistemaSecundario())
        && Objects.nonNull(filtroPesquisaDePara.getSistemaSecundario().getIdEmpresa())) {
      return criteriaBuilder.equal(
          root.get(DePara_.sistemaSecundario).get(Sistema_.empresa).get(Empresa_.id),
          filtroPesquisaDePara.getSistemaSecundario().getIdEmpresa());

    }
    return null;
  }

  private static Predicate getPredicateSistemaPrimario(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {
    if (Objects.nonNull(filtroPesquisaDePara.getSistemaPrimario())
        && Objects.nonNull(filtroPesquisaDePara.getSistemaPrimario().getIdSistema())) {
      return criteriaBuilder.equal(root.get(DePara_.sistemaPrimario).get(Sistema_.id),
          filtroPesquisaDePara.getSistemaPrimario().getIdSistema());
    }
    return null;
  }

  private static Predicate getPredicateEmpresaPrimaria(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {
    if (Objects.nonNull(filtroPesquisaDePara.getSistemaPrimario())
        && Objects.nonNull(filtroPesquisaDePara.getSistemaPrimario().getIdEmpresa())) {
      return criteriaBuilder.equal(
          root.get(DePara_.sistemaPrimario).get(Sistema_.empresa).get(Empresa_.id),
          filtroPesquisaDePara.getSistemaPrimario().getIdEmpresa());
    }
    return null;
  }

  private static Predicate getPredicateTipoDePara(final Root<DePara> root,
      final CriteriaBuilder criteriaBuilder, final FiltroPesquisaDeParaDTO filtroPesquisaDePara) {
    if (filtroPesquisaDePara.getIdTipoDePara() != null) {
      return criteriaBuilder.equal(root.get(DePara_.tipoDePara).get(TipoDePara_.id),
          filtroPesquisaDePara.getIdTipoDePara());
    }
    return null;
  }

}
