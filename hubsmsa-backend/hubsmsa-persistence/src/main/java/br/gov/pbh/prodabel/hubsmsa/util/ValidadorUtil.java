package br.gov.pbh.prodabel.hubsmsa.util;

import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;


/**
 * Classe utilitária responsável por realizar validações
 * 
 */
public final class ValidadorUtil {
	
	
	private ValidadorUtil() {
	}
	
	/**
	 * Método Responsavel por validar se a lista retornada em uma consulta tem resultados, 
	 * se não tiver será lançado a RegistroNaoEncontradoException.
	 *
	 * @param listResult
	 * @param nomeFuncionalidade - nome da funcionalidade consultada
	 * @throws RegistroNaoEncontradoException
	 */
	public static <T> List<T> validarNoResultList (List<T> listResult) throws RegistroNaoEncontradoException {
		if (listResult.isEmpty()) {
			throw new RegistroNaoEncontradoException();
		}
		return listResult;
	}
	
	/**
	 * Valida se o registro foi encontrado com o nome da funcionalidade
	 * 
	 * @param <T>
	 * 
	 * @param object - objeto a ser validado
	 * @param nomeFuncionalidade - nome da funcionalidade consultada
	 * @return 
	 * @throws RegistroNaoEncontradoException se o objeto não for válido
	 */
	public static <T> void validarRegistroNaoEncontrado(T entity) throws RegistroNaoEncontradoException {
		if(entity == null) {
			throw new RegistroNaoEncontradoException();
		}
	}
}
