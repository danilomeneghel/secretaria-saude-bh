package br.gov.pbh.prodabel.hubsmsa.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.GenericoDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;

// TODO: Auto-generated Javadoc
public abstract class GenericService<K extends Serializable, T extends EntidadeBase<K>> {

    @Inject
    private GenericoDAO<K, T> dao;

    /**
     * Lista todas as entidades existentes
     * 
     * @return lista de entidades existentes
     */
    public List<T> listar() {
    	return dao.listar();
    }

    /**
     * Recupera a entidade de acordo com o tipo e com o id.
     * 
     * @param tipo tipo da entidade
     * @param id   identificador
     * @return entidade encontrada, <code>null</code> caso não seja encontrada
     */
    public T consultarPorId(K id) {
    	return dao.consultarPorId(id);
    }

    /**
     * Realiza a persistência de uma entidade, caso ela tenha um id será chamado o
     * método {@link javax.persistence.EntityManager#persist(Object)} caso exista
     * será chamado o método {@link javax.persistence.EntityManager#merge(Object)}
     * do {@link javax.persistence.EntityManager}
     * 
     * @param objeto objeto que será persistido
     * @return retorna o objeto persistido, caso seja um novo objeto ele terá o id
     *         configurado
     */
    public T gravar(T objeto) {
    	return dao.gravar(objeto);
    }

    /**
     * Exclui uma entidade de acordo com o tipo e o id.
     * 
     * @param tipo tipo da entidae
     * @param id   identificador
     * @throws RegistroNaoEncontradoException 
     */
    public void excluir(K id) throws RegistroNaoEncontradoException {
    	dao.excluir(id);
    }

    /**
     * Exclui uma entidade.
     * 
     * @param objeto entidade que será excluída
     */
    public void excluir(T objeto) {
    	dao.excluir(objeto);
    }
    
    public GenericoDAO<K, T> getDaoGeneric() {
		return dao;
	}

	public void setDaoGeneric(GenericoDAO<K, T> dao) {
		this.dao = dao;
	}

    /**
     * Inserir dados revisao.
     *
     * @param historico the historico
     * @param historicoDetalhe the historico detalhe
     */
    public void inserirDadosRevisao(List<HistoricoAlteracaoDTO> historico,
        HistoricoAlteracaoDetalheDTO historicoDetalhe) {
      Optional<HistoricoAlteracaoDTO> historicoOpt = historico.stream().findFirst();
      if (historicoOpt.isPresent()) {
        historicoDetalhe.setAlteracoes(historico);
        historicoDetalhe
            .setDataEvento(historicoOpt.get().getDataAlteracaoDadoAtual().substring(0, 16));
        historicoDetalhe.setRevisao(historicoOpt.get().getIdRevisao());
      }
    }
    
}
