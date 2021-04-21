package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import java.util.List;
import javax.ejb.EJB;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.servico.VisualizarServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.ServicoEndPoint;
import br.gov.pbh.prodabel.hubsmsa.service.ServicoService;

public class ServicoEndPointImpl implements ServicoEndPoint {
	
	@EJB
  private ServicoService service;


	@Override
    public List<VisualizarServicoDTO> consultarServicoPorSistema(Long idSistema) {
      return service.consultarServicos(idSistema);
	}

    @Override
    public List<VisualizarServicoDTO> consultarServico() {
      return service.consultarServico();
    }

    @Override
    public VisualizarServicoDTO consultarServico(Long id) {
      return service.consultarServico(id);
    }

    @Override
    public List<SelecaoDTO> selecaoServico(Long idSistema) {
      return service.consultarSelecaoServico(idSistema);
    }
	
}
