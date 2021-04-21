package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.servico.VisualizarServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;

// TODO: Auto-generated Javadoc
public class ServicoMapper {

	/**
	 * Instantiates a new servico mapper.
	 */
	private ServicoMapper() {

	}

    /**
     * Mapper.
     *
     * @param servicos the consultar sistema
     * @return the list
     */
    public static List<VisualizarServicoDTO> mapper(List<Servico> servicos) {
      List<VisualizarServicoDTO> sistemaDTO = new ArrayList<>();

      for (Servico servico : servicos) {
        VisualizarServicoDTO filtroServico = new VisualizarServicoDTO();
        filtroServico.setId(servico.getId());
        filtroServico.setNome(servico.getNome());
        filtroServico.setDescricao(servico.getDescricao());
        filtroServico.setStatus(servico.getStatus());
        filtroServico
            .setSistemaPrimario(SistemaMapper.mapper(servico.getSistemaPrimario()));
        filtroServico.setSistemaSecundario(SistemaMapper.mapper(servico.getSistemaSecundario()));
        sistemaDTO.add(filtroServico);
		}

		return sistemaDTO;
	}

    /**
     * Mapper.
     *
     * @param servicos the consultar sistema
     * @return the list
     */
    public static VisualizarServicoDTO mapper(Servico servico) {

      VisualizarServicoDTO servicoDTO = new VisualizarServicoDTO();
      servicoDTO.setId(servico.getId());
      servicoDTO.setNome(servico.getNome());
      servicoDTO.setDescricao(servico.getDescricao());
      servicoDTO.setStatus(servico.getStatus());
      servicoDTO.setSistemaPrimario(SistemaMapper.mapper(servico.getSistemaPrimario()));
      servicoDTO.setSistemaSecundario(SistemaMapper.mapper(servico.getSistemaSecundario()));

      return servicoDTO;
    }

    /**
     * Mapper.
     *
     * @param idServico the id servico
     * @return the servico
     */
    public static Servico mapper(Long idServico) {
      Servico servico = new Servico();
      servico.setId(idServico);
      return servico;
    }

}
