package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.ContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.VisualizarContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresa_;

// TODO: Auto-generated Javadoc
public class ContatoEmpresaMapper {

	/**
	 * Mapper.
	 *
	 * @param consultarContatoEmpresa the consultar contato empresa
	 * @return the list
	 */
	public static List<VisualizarContatoEmpresaDTO> mapper(List<ContatoEmpresa> consultarContatoEmpresa) {
		List<VisualizarContatoEmpresaDTO> contatosDeEmpresas = new ArrayList<VisualizarContatoEmpresaDTO>();
		consultarContatoEmpresa.forEach(contato -> {
			VisualizarContatoEmpresaDTO contatoDTO = new VisualizarContatoEmpresaDTO();
			contatoDTO.setId(contato.getId());
			contatoDTO.setEmpresa(EmpresaMapper.mapper(contato.getEmpresa()));
			contatoDTO.setNome(contato.getNome());
			contatoDTO.setEmail(contato.getEmail());
			contatoDTO.setTelefone(contato.getTelefone());
			contatoDTO.setSetor(contato.getSetor());
			contatoDTO.setStatus(contato.getStatus());
			
			contatosDeEmpresas.add(contatoDTO);
		});
		return contatosDeEmpresas;
	}

	/**
	 * Mapper.
	 *
	 * @param contatoEmpresa the contato empresa
	 * @return the visualizar contato empresa DTO
	 */
	public static VisualizarContatoEmpresaDTO mapper(ContatoEmpresa contatoEmpresa) {
		VisualizarContatoEmpresaDTO contatoDTO = new VisualizarContatoEmpresaDTO();
		contatoDTO.setId(contatoEmpresa.getId());
		contatoDTO.setEmpresa(EmpresaMapper.mapper(contatoEmpresa.getEmpresa()));
		contatoDTO.setNome(contatoEmpresa.getNome());
		contatoDTO.setEmail(contatoEmpresa.getEmail());
		contatoDTO.setTelefone(contatoEmpresa.getTelefone());
		contatoDTO.setSetor(contatoEmpresa.getSetor());
		contatoDTO.setStatus(contatoEmpresa.getStatus());
		return contatoDTO;
	}

	/**
	 * Mapper.
	 *
	 * @param cadastrarContatoEmpresaDTO the cadastrar contato empresa DTO
	 * @return the contato empresa
	 */
	public static ContatoEmpresa mapper(ContatoEmpresaDTO cadastrarContatoEmpresaDTO) {
		ContatoEmpresa contato = new ContatoEmpresa();
		contato.setEmpresa(EmpresaMapper.mapper(cadastrarContatoEmpresaDTO.getIdEmpresa()));
		contato.setNome(cadastrarContatoEmpresaDTO.getNome());
		contato.setEmail(cadastrarContatoEmpresaDTO.getEmail());
		contato.setTelefone(cadastrarContatoEmpresaDTO.getTelefone());
		contato.setSetor(cadastrarContatoEmpresaDTO.getSetor());
		contato.setStatus(StatusEnum.valueOf(cadastrarContatoEmpresaDTO.getStatus()));
		return contato;
	}

	/**
	 * Mapper.
	 *
	 * @param editarContatoEmpresaDTO the editar contato empresa DTO
	 * @param contatoEmpresa the contato empresa
	 */
	public static void mapper(ContatoEmpresaDTO editarContatoEmpresaDTO, ContatoEmpresa contatoEmpresa) {
		contatoEmpresa.setEmpresa(EmpresaMapper.mapper(editarContatoEmpresaDTO.getIdEmpresa()));
		contatoEmpresa.setNome(editarContatoEmpresaDTO.getNome());
		contatoEmpresa.setEmail(editarContatoEmpresaDTO.getEmail());
		contatoEmpresa.setTelefone(editarContatoEmpresaDTO.getTelefone());
		contatoEmpresa.setSetor(editarContatoEmpresaDTO.getSetor());
		contatoEmpresa.setStatus(StatusEnum.valueOf(editarContatoEmpresaDTO.getStatus()));
	}
	
    /**
     * Mapper.
     *
     * @param id the id
     * @return the contato empresa
     */
    public static ContatoEmpresa mapper(Long id) {
      ContatoEmpresa contatoEmpresa = new ContatoEmpresa();
      contatoEmpresa.setId(id);
      return contatoEmpresa;
    }

	public static String[] getAtributosValidos() {
      String[] atributosValidos = new String[6];
      atributosValidos[0] = ContatoEmpresa_.NOME;
      atributosValidos[1] = ContatoEmpresa_.EMAIL;
      atributosValidos[2] = ContatoEmpresa_.TELEFONE;
      atributosValidos[3] = ContatoEmpresa_.SETOR;
      atributosValidos[4] = ContatoEmpresa_.STATUS;
      atributosValidos[5] = "nomeEmpresa";
		
		return atributosValidos;
	}

}
