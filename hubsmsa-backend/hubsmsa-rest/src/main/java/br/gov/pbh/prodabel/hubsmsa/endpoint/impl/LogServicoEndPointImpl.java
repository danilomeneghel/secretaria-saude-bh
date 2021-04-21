package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import javax.ejb.EJB;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.logservico.FiltroPesquisaLogServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.logservico.VisualizarLogServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.LogServicoEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.LogServicoService;

public class LogServicoEndPointImpl implements LogServicoEndPoint {
	
	@EJB
    private LogServicoService logService;

    @Override
    public PaginacaoPublicaDTO<VisualizarLogServicoDTO> consultarLogServico(
        FiltroPesquisaLogServicoDTO filtro) {
      return logService.consultarLogServico(filtro);

    }


}
