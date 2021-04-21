package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.FiltroPesquisaAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.VisualizarAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.CadastrarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.EditarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.VisualizarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.AssuntoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Exemplo;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ExemploAud;

public final class ExemploMapper {

	private ExemploMapper() {

	}

	public static List<VisualizarExemploDTO> mapper(List<Exemplo> consultarExemplo) {
		List<VisualizarExemploDTO> exemploDTO = new ArrayList<>();

		for (Exemplo exemplo : consultarExemplo) {
			VisualizarExemploDTO filtroExemplo = new VisualizarExemploDTO();
			filtroExemplo.setCodigo(exemplo.getCodigo());
			filtroExemplo.setDataAtualizacao(exemplo.getDataAtualizacao());
			filtroExemplo.setNomeExemplo(exemplo.getDescricao());
			filtroExemplo.setFormaCadastro(exemplo.getFormaCadastro());
			filtroExemplo.setId(exemplo.getId());
			filtroExemplo.setStatus(exemplo.getStatus());

			exemploDTO.add(filtroExemplo);
		}
		return exemploDTO;
	}

	public static Exemplo mapper(CadastrarExemploDTO exemploDTO) {

		Exemplo exemplo = new Exemplo();
		exemplo.setCodigo(exemploDTO.getCodigo());
		exemplo.setDescricao(exemploDTO.getNomeExemplo());
		exemplo.setStatus(StatusEnum.valueOf(exemploDTO.getStatus()));
		exemplo.setFormaCadastro(FormaCadastroEnum.C);
		exemplo.setDataAtualizacao(LocalDate.now());

		return exemplo;
	}

	public static void mapper(EditarExemploDTO editarExemploDTO, Exemplo exemplo) {

		exemplo.setDataAtualizacao(LocalDate.now());
		exemplo.setDescricao(editarExemploDTO.getNomeExemplo());
		exemplo.setStatus(StatusEnum.valueOf(editarExemploDTO.getStatus()));
		exemplo.setCodigo(editarExemploDTO.getCodigo());
		if (FormaCadastroEnum.C.equals(exemplo.getFormaCadastro())) {
			exemplo.setFormaCadastro(FormaCadastroEnum.C);
		}
		if (FormaCadastroEnum.I.equals(exemplo.getFormaCadastro())) {
			exemplo.setFormaCadastro(FormaCadastroEnum.A);
		}

	}

	public static VisualizarExemploDTO mapper(Exemplo exemplo) {
		VisualizarExemploDTO dto = new VisualizarExemploDTO();
		dto.setCodigo(exemplo.getCodigo());
		dto.setDataAtualizacao(exemplo.getDataAtualizacao());
		dto.setFormaCadastro(exemplo.getFormaCadastro());
		dto.setId(exemplo.getId());
		dto.setNomeExemplo(exemplo.getDescricao());
		dto.setStatus(exemplo.getStatus());
		
		return dto;
	}

	public static List<VisualizarAlteracaoDTO> mapperAlteracoes(List<ExemploAud> list, FiltroPesquisaAlteracaoDTO filtro) {
		List<VisualizarAlteracaoDTO> alteracoesCadastro = new ArrayList<>();
		for (ExemploAud entidadeAuditoria : list) {
			VisualizarAlteracaoDTO dto = new VisualizarAlteracaoDTO();
			dto.setAssunto(AssuntoEnum.EX);			 
			dto.setDataAlteracao(entidadeAuditoria.getRevisao() != null ? entidadeAuditoria.getRevisao().getDtRevisao() : null);
			dto.setDescricao(entidadeAuditoria.getDescricao());
			dto.setId(entidadeAuditoria.getId());
			
			alteracoesCadastro.add(dto);
		}	
		return alteracoesCadastro;
	}

	public static String[] getAtributosValidos() {
		String [] atributosValidos = new String[3];
		atributosValidos[0] = "codigo";
		atributosValidos[1] = "descricao";
		atributosValidos[2] = "status";
		return atributosValidos;
	}	
}
