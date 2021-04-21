package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.logservico.VisualizarLogServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;

// TODO: Auto-generated Javadoc
public class LogServicoMapper {

	/**
     * Instantiates a new Log servico mapper.
     */
	private LogServicoMapper() {

	}

    /**
     * Mapper.
     *
     * @param logservicos the consultar sistema
     * @return the list
     */
    public static List<VisualizarLogServicoDTO> mapper(List<LogServico> logservicos) {
      List<VisualizarLogServicoDTO> logServicosDTO = new ArrayList<>();

      for (LogServico logServico : logservicos) {
        VisualizarLogServicoDTO logServicoDTO = new VisualizarLogServicoDTO();
        logServicoDTO.setId(logServico.getId());
        logServicoDTO.setServico(ServicoMapper.mapper(logServico.getServico()));
        logServicoDTO.setDataInicioEvento(TimeUtil
            .convertDataToStr(logServico.getDataInicioEvento(),
            TimeUtil.DD_MM_YYYY_HH_MM_SS));
        logServicoDTO.setDataFimEvento(
            TimeUtil.convertDataToStr(logServico.getDataFimEvento(), TimeUtil.DD_MM_YYYY_HH_MM_SS));
        logServicoDTO.setStatus(logServico.getStatus());

        logServicosDTO.add(logServicoDTO);
		}

      return logServicosDTO;
	}


}
