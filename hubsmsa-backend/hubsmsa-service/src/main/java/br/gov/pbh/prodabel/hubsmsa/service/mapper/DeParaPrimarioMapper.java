package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import br.gov.pbh.prodabel.hubsmsa.dto.sistemaprimariosecundario.DeParaPrimarioSecundarioDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistemaprimariosecundario.VisualizarDeParaPrimarioSecundarioDTO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario_;

// TODO: Auto-generated Javadoc
public class DeParaPrimarioMapper {

	/**
	 * Instantiates a new de para primario mapper.
	 */
	public DeParaPrimarioMapper() {

	}

    /**
     * Mapper.
     *
     * @param listaDeParaPrimario the lista de para primario
     * @param dePara the de para
     * @return the sets the
     */
    public static Set<VisualizarDeParaPrimarioSecundarioDTO> mapper(
        final Set<DeParaPrimario> listaDeParaPrimario, final DePara dePara) {
      final Set<VisualizarDeParaPrimarioSecundarioDTO> lista =
          new HashSet<>();
      for (final DeParaPrimario deParaPrimario : listaDeParaPrimario) {
        final VisualizarDeParaPrimarioSecundarioDTO deParaPrimarioInstance =
            new VisualizarDeParaPrimarioSecundarioDTO();
        deParaPrimarioInstance.setIdDePara(dePara.getId());
			deParaPrimarioInstance.setId(deParaPrimario.getId());
			deParaPrimarioInstance.setCodigo(deParaPrimario.getCodigo());
			deParaPrimarioInstance.setDescricao(deParaPrimario.getDescricao());
			lista.add(deParaPrimarioInstance);
		}
		return lista;
	}

    /**
     * Mapper.
     *
     * @param listaDeParaSecundario the lista de para secundario
     * @param dePara the de para
     * @return the sets the
     */
    public static Set<DeParaPrimario> mapper(
        final List<DeParaPrimarioSecundarioDTO> listaDeParaSecundario, final DePara dePara) {
      final Set<DeParaPrimario> lista = new HashSet<>();
      for (final DeParaPrimarioSecundarioDTO deParaPrimario : listaDeParaSecundario) {
        final DeParaPrimario deParaPrimarioInstance = new DeParaPrimario();
			deParaPrimarioInstance.setDePara(dePara);
			deParaPrimarioInstance.setCodigo(deParaPrimario.getCodigo());
			deParaPrimarioInstance.setDescricao(deParaPrimario.getDescricao());
			lista.add(deParaPrimarioInstance);
		}
		return lista;
	}

    public static String[] getAtributosValidos() {
      String[] atributosValidos = new String[2];
      atributosValidos[0] = DeParaPrimario_.CODIGO;
      atributosValidos[1] = DeParaPrimario_.DESCRICAO;

      return atributosValidos;
    }

}
