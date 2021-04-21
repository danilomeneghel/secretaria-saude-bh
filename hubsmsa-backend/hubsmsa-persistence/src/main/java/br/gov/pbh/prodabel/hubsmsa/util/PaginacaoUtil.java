package br.gov.pbh.prodabel.hubsmsa.util;

import java.util.List;

/**
 * Classe utilitária para paginação 
 */
public final class PaginacaoUtil {
	
	private static final Integer NUMERO_AUXILIAR_SUBTRACAO = 1;
	
	public static final Integer NUMERO_MAXIMO_REGISTROS_POR_PAGINA = 10;
	
	public static final Integer NUMERO_MAXIMO_REGISTROS_POR_PAGINA_20 = 20;
	
	public static final Integer NUMERO_MAXIMO_REGISTROS_PAGINACAO = 50;
	
	private PaginacaoUtil() {
	}

	/**
	 * Método auxiliar que calcula a página atual que será exibido ao usuário
	 * 
	 * @param numeroPagina - número da página atual
	 * 
	 * @return - O valor que será usado no setFistResult
	 */
	public static Integer calcularPaginacaoAtual(Integer numeroPagina) {
		return (numeroPagina - NUMERO_AUXILIAR_SUBTRACAO) * NUMERO_MAXIMO_REGISTROS_POR_PAGINA;
	}
	
	public static Integer calcularPaginacaoAtual(Integer numeroPagina, Integer itensPorPagina) {
		return (numeroPagina - NUMERO_AUXILIAR_SUBTRACAO) * itensPorPagina;
	}
	
	public static <T> List<T> definirPaginacao(List<T> totalItens, Integer numeroPagina, Integer itensPorPagina) {
		int paginaAtual = calcularPaginacaoAtual(numeroPagina, itensPorPagina);
		int indiceFinal = paginaAtual + itensPorPagina;
		if (indiceFinal > totalItens.size()) {
			indiceFinal = totalItens.size();
		}
		return totalItens.subList(paginaAtual, indiceFinal);		
	}
	public static <T> Integer definirTotalRegistros(List<T> totalItens) {
		return totalItens != null ? totalItens.size() : 0;
	}

}
