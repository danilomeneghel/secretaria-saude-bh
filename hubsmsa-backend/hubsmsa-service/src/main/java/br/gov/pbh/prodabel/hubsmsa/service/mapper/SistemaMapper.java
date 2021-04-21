package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.SistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.VisualizarSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;

public class SistemaMapper {

	private SistemaMapper() {

	}

	public static List<VisualizarSistemaDTO> mapper(List<Sistema> consultarSistema) {
		List<VisualizarSistemaDTO> sistemaDTO = new ArrayList<>();

		for (Sistema sistema : consultarSistema) {
			VisualizarSistemaDTO filtroSistema = new VisualizarSistemaDTO();
			filtroSistema.setId(sistema.getId());
			filtroSistema.setEmpresa(EmpresaMapper.mapper(sistema.getEmpresa()));
			filtroSistema.setNome(sistema.getNome());
			filtroSistema.setDescricao(sistema.getDescricao());
			filtroSistema.setStatus(sistema.getAtivo());
			filtroSistema.setDataAtualizacao(sistema.getDataAtualizacao());
			filtroSistema.setFormaCadastro(sistema.getFormaCadastro());
			sistemaDTO.add(filtroSistema);
		}

		return sistemaDTO;
	}

	public static Sistema mapper(SistemaDTO cadastrarSistemaDTO) {

		Sistema sistema = new Sistema();
		sistema.setEmpresa(EmpresaMapper.mapper(cadastrarSistemaDTO.getIdEmpresa()));
		sistema.setNome(cadastrarSistemaDTO.getNome());
		sistema.setDescricao(cadastrarSistemaDTO.getDescricao());
		sistema.setAtivo(StatusEnum.valueOf(cadastrarSistemaDTO.getStatus()));
		sistema.setFormaCadastro(FormaCadastroEnum.C);
		sistema.setDataAtualizacao(LocalDate.now());

		return sistema;
	}

	public static void mapper(SistemaDTO editarSistemaDTO, Sistema sistema) {

		sistema.setEmpresa(EmpresaMapper.mapper(editarSistemaDTO.getIdEmpresa()));
		sistema.setDataAtualizacao(LocalDate.now());
		sistema.setNome(editarSistemaDTO.getNome());
		sistema.setDescricao(editarSistemaDTO.getDescricao());
		sistema.setAtivo(StatusEnum.valueOf(editarSistemaDTO.getStatus()));
		if (FormaCadastroEnum.C.equals(sistema.getFormaCadastro())) {
			sistema.setFormaCadastro(FormaCadastroEnum.C);
		}
		if (FormaCadastroEnum.I.equals(sistema.getFormaCadastro())) {
			sistema.setFormaCadastro(FormaCadastroEnum.A);
		}

	}

	public static VisualizarSistemaDTO mapper(Sistema sistema) {
		VisualizarSistemaDTO visualizarSistemaDTO = new VisualizarSistemaDTO();
		visualizarSistemaDTO.setEmpresa(EmpresaMapper.mapper(sistema.getEmpresa()));
		visualizarSistemaDTO.setId(sistema.getId());
		visualizarSistemaDTO.setNome(sistema.getNome());
		visualizarSistemaDTO.setDescricao(sistema.getDescricao());
		visualizarSistemaDTO.setStatus(sistema.getAtivo());
		visualizarSistemaDTO.setDataAtualizacao(sistema.getDataAtualizacao());
		visualizarSistemaDTO.setFormaCadastro(sistema.getFormaCadastro());
		
		return visualizarSistemaDTO;
	}
	
	public static Sistema mapper(Long id, Long idEmpresa) {
		Sistema sistema = new Sistema();
		sistema.setId(id);
		sistema.setEmpresa(EmpresaMapper.mapper(idEmpresa));
		return sistema;
	}

	public static String[] getAtributosValidos() {
		String[] atributosValidos = new String[4];
		atributosValidos[0] = "nomeEmpresa";
		atributosValidos[1] = "nome";
		atributosValidos[2] = "descricao";
		atributosValidos[3] = "ativo";
		return atributosValidos;
	}
}
