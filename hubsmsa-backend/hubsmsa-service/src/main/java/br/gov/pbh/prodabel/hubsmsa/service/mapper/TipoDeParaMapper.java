package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.TipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.VisualizarTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.TipoDePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.TipoDeParaAud;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;

// TODO: Auto-generated Javadoc
public class TipoDeParaMapper {
	
	/**
	 * Instantiates a new tipo de para mapper.
	 */
	private TipoDeParaMapper() {

	}

	/**
	 * Mapper.
	 *
	 * @param consultarTipoDePara the consultar tipo de para
	 * @return the list
	 */
	public static List<VisualizarTipoDeParaDTO> mapper(List<TipoDePara> consultarTipoDePara) {
		List<VisualizarTipoDeParaDTO> tipoDeParaDTO = new ArrayList<>();

		for (TipoDePara tipoDePara : consultarTipoDePara) {
			VisualizarTipoDeParaDTO filtroTipoDePara = new VisualizarTipoDeParaDTO();
			filtroTipoDePara.setId(tipoDePara.getId());
			filtroTipoDePara.setNome(tipoDePara.getNome());
			filtroTipoDePara.setDescricao(tipoDePara.getDescricao());
			filtroTipoDePara.setStatus(tipoDePara.getStatus());
			filtroTipoDePara.setDataAtualizacao(tipoDePara.getDataAtualizacao());
			filtroTipoDePara.setFormaCadastro(tipoDePara.getFormaCadastro());

			tipoDeParaDTO.add(filtroTipoDePara);
		}
		return tipoDeParaDTO;
	}

	/**
	 * Mapper.
	 *
	 * @param tipoDeParaDTO the tipo de para DTO
	 * @return the tipo de para
	 */
	public static TipoDePara mapper(TipoDeParaDTO tipoDeParaDTO) {

		TipoDePara tipoDePara = new TipoDePara();
		tipoDePara.setNome(tipoDeParaDTO.getNome());
		tipoDePara.setDescricao(tipoDeParaDTO.getDescricao());
		tipoDePara.setStatus(StatusEnum.valueOf(tipoDeParaDTO.getStatus()));
		tipoDePara.setFormaCadastro(FormaCadastroEnum.C);
		tipoDePara.setDataAtualizacao(LocalDate.now());

		return tipoDePara;
	}

	/**
	 * Mapper.
	 *
	 * @param editarTipoDeParaDTO the editar tipo de para DTO
	 * @param tipoDePara the tipo de para
	 */
	public static void mapper(TipoDeParaDTO editarTipoDeParaDTO, TipoDePara tipoDePara) {

		tipoDePara.setNome(editarTipoDeParaDTO.getNome());
		tipoDePara.setDescricao(editarTipoDeParaDTO.getDescricao());
		tipoDePara.setStatus(StatusEnum.valueOf(editarTipoDeParaDTO.getStatus()));
		tipoDePara.setDataAtualizacao(LocalDate.now());
		if (FormaCadastroEnum.C.equals(tipoDePara.getFormaCadastro())) {
			tipoDePara.setFormaCadastro(FormaCadastroEnum.C);
		}
		if (FormaCadastroEnum.I.equals(tipoDePara.getFormaCadastro())) {
			tipoDePara.setFormaCadastro(FormaCadastroEnum.A);
		}

	}

	/**
	 * Mapper.
	 *
	 * @param tipoDePara the tipo de para
	 * @return the visualizar tipo de para DTO
	 */
	public static VisualizarTipoDeParaDTO mapper(TipoDePara tipoDePara) {
		VisualizarTipoDeParaDTO dto = new VisualizarTipoDeParaDTO();
		dto.setId(tipoDePara.getId());
		dto.setDescricao(tipoDePara.getDescricao());
		dto.setNome(tipoDePara.getNome());
		dto.setStatus(tipoDePara.getStatus());
		dto.setDataAtualizacao(tipoDePara.getDataAtualizacao());
		dto.setFormaCadastro(tipoDePara.getFormaCadastro());
		
		return dto;
	}
	
	/**
	 * Mapper.
	 *
	 * @param id the id
	 * @return the tipo de para
	 */
	public static TipoDePara mapper(Long id) {
		TipoDePara tipoDePara = new TipoDePara();
		tipoDePara.setId(id);
		return tipoDePara;
	}

    /**
     * Mapper.
     *
     * @param Historico tipoDeParas the tipoDeParas
     * @return the list
     */
    public static List<HistoricoTipoDeParaDTO> mapperHistorico(List<TipoDeParaAud> tipoDeParasAud) {
      List<HistoricoTipoDeParaDTO> tipoDeParasAudDto = new ArrayList<>();

      for (TipoDeParaAud tipoDeParaAud : tipoDeParasAud) {

        HistoricoTipoDeParaDTO historico = new HistoricoTipoDeParaDTO();

        historico.setDescricao(tipoDeParaAud.getDescricao());
        historico.setIdTipoDePara(tipoDeParaAud.getId());
        historico.setNome(tipoDeParaAud.getNome());
        historico.setStatus(tipoDeParaAud.getStatus().getName());
        historico.setRevision(tipoDeParaAud.getRevisao());
        historico.setRevisao(String.valueOf(tipoDeParaAud.getRevisao().getId()));
        historico.setDataEvento(TimeUtil.convertDataToStr(tipoDeParaAud.getRevisao().getDtRevisao(),
            "dd/MM/yyyy HH:mm"));
        historico.setTipoRevisao(tipoDeParaAud.getTipoRevisao());
        tipoDeParasAudDto.add(historico);
      }
      return tipoDeParasAudDto;
    }

	public static String[] getAtributosValidos() {
		String [] atributosValidos = new String[3];
		atributosValidos[0] = "nome";
		atributosValidos[1] = "descricao";
		atributosValidos[2] = "status";
		return atributosValidos;
	}

}
