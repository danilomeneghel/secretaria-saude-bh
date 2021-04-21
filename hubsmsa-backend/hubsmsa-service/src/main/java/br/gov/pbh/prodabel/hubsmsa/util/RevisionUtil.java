package br.gov.pbh.prodabel.hubsmsa.util;

import java.util.Comparator;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoRevisaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;

// TODO: Auto-generated Javadoc
public final class RevisionUtil {

	/**
	 * Instantiates a new revision util.
	 */
	private RevisionUtil() {
	}


	
	/**
	 * Ordenar lista revisao.
	 *
	 * @param revisoes the revisoes
	 * @param colunaNome the coluna nome
	 * @param ordem the ordem
	 */
	public static void ordenarListaRevisao(List<HistoricoAlteracaoDTO> revisoes, String colunaNome, String ordem) {
		switch (ColunaOrdenacaoRevisaoEnum.valueOf(colunaNome.toUpperCase())) {
		case DADOANTERIOR:
			if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getDadoAnterior));
			} else {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getDadoAnterior).reversed());
			}
			break;
		case DADOATUAL:
			if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getDadoAtual));
			} else {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getDadoAtual).reversed());
			}
			break;
		case DATAALTERACAODADOATUAL:
			if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getIdRevisao));
			} else {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getIdRevisao).reversed());
			}
			break;
		case USUARIORESPONSAVELALTERACAO:
			if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getUsuarioResponsavelAlteracao));
			} else {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getUsuarioResponsavelAlteracao).reversed());
			}
			break;
		case EMPRESA:
			if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getEmpresa));
			} else {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getEmpresa).reversed());
			}
			break;
		case REVISAO:
			if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getIdRevisao));
			} else {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getIdRevisao).reversed());
			}
			break;			
		case SISTEMARESPONSAVELALTERACAO:
			if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getSistemaResponsavelAlteracao));
			} else {
				revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getSistemaResponsavelAlteracao).reversed());
			}
			break;
          case TIPODEPARA:
            if (TipoOrdenacaoEnum.ASC.equals(TipoOrdenacaoEnum.valueOf(ordem.toUpperCase()))) {
              revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getTipoDePara));
            } else {
              revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getTipoDePara).reversed());
            }
            break;
		default:
			revisoes.sort(Comparator.comparing(HistoricoAlteracaoDTO::getDadoAnterior));
			break;
		}
	}
}
