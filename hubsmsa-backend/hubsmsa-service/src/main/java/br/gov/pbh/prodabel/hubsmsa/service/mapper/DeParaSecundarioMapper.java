package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import br.gov.pbh.prodabel.hubsmsa.dto.sistemaprimariosecundario.DeParaPrimarioSecundarioDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistemaprimariosecundario.VisualizarDeParaPrimarioSecundarioDTO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario_;

// TODO: Auto-generated Javadoc
public class DeParaSecundarioMapper {

  /**
   * Instantiates a new de para secundario mapper.
   */
  public DeParaSecundarioMapper() {

  }

  /**
   * Mapper.
   *
   * @param listaDeParaSecundario the lista de para secundario
   * @param dePara the de para
   * @return the sets the
   */
  public static Set<VisualizarDeParaPrimarioSecundarioDTO> mapper(
      final Set<DeParaSecundario> listaDeParaSecundario, final DePara dePara) {
    final Set<VisualizarDeParaPrimarioSecundarioDTO> lista =
        new HashSet<>();
    for (final DeParaSecundario deParaSecundario : listaDeParaSecundario) {
      final VisualizarDeParaPrimarioSecundarioDTO deParaSecundarioInstance =
          new VisualizarDeParaPrimarioSecundarioDTO();
      deParaSecundarioInstance.setIdDePara(dePara.getId());
      deParaSecundarioInstance.setId(deParaSecundario.getId());
      deParaSecundarioInstance.setCodigo(deParaSecundario.getCodigo());
      deParaSecundarioInstance.setDescricao(deParaSecundario.getDescricao());
      lista.add(deParaSecundarioInstance);
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
  public static Set<DeParaSecundario> mapper(
      final List<DeParaPrimarioSecundarioDTO> listaDeParaSecundario, final DePara dePara) {
    final Set<DeParaSecundario> lista = new HashSet<>();
    for (final DeParaPrimarioSecundarioDTO deParaPrimario : listaDeParaSecundario) {
      final DeParaSecundario deParaSecundarioInstance = new DeParaSecundario();
      deParaSecundarioInstance.setDePara(dePara);
      deParaSecundarioInstance.setCodigo(deParaPrimario.getCodigo());
      deParaSecundarioInstance.setDescricao(deParaPrimario.getDescricao());
      lista.add(deParaSecundarioInstance);
    }
    return lista;
  }

  public static String[] getAtributosValidos() {
    String[] atributosValidos = new String[2];
    atributosValidos[0] = DeParaSecundario_.CODIGO;
    atributosValidos[1] = DeParaSecundario_.DESCRICAO;

    return atributosValidos;
  }

}
