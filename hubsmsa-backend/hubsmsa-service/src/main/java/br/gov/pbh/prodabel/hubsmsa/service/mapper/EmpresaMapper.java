package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.util.ArrayList;
import java.util.List;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.EmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.VisualizarEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.EmpresaAud;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;

// TODO: Auto-generated Javadoc
public class EmpresaMapper {

  /**
   * Mapper.
   *
   * @param empresas the empresas
   * @return the list
   */
  public static List<VisualizarEmpresaDTO> mapper(List<Empresa> empresas) {
    List<VisualizarEmpresaDTO> empresasDto = new ArrayList<>();

    for (Empresa empresa : empresas) {

      VisualizarEmpresaDTO filtroEmpresaDTO = new VisualizarEmpresaDTO();
      filtroEmpresaDTO.setNomeEmpresarial(empresa.getNomeEmpresarial());
      filtroEmpresaDTO.setNomeFantasia(empresa.getNomeFantasia());
      filtroEmpresaDTO.setCnpj(empresa.getCnpj());
      filtroEmpresaDTO.setCnes(empresa.getCodigoCnes());
      filtroEmpresaDTO.setSite(empresa.getSite());
      filtroEmpresaDTO.setStatus(empresa.getAtivo());
      filtroEmpresaDTO.setId(empresa.getId());

      empresasDto.add(filtroEmpresaDTO);
    }
    return empresasDto;
  }

  /**
   * Mapper.
   *
   * @param empresaDTO the empresa DTO
   * @return the empresa
   */
  public static Empresa mapper(EmpresaDTO empresaDTO) {
    Empresa empresa = new Empresa();
    empresa.setAtivo(StatusEnum.valueOf(empresaDTO.getStatus()));
    empresa.setCnpj(empresaDTO.getCnpj());
    empresa.setCodigoCnes(empresaDTO.getCnes());
    empresa.setSite(empresaDTO.getSite());
    empresa.setNomeEmpresarial(empresaDTO.getNomeEmpresarial());
    empresa.setNomeFantasia(empresaDTO.getNomeFantasia());

    return empresa;
  }

  /**
   * Mapper.
   *
   * @param editarEmpresaDTO the editar empresa DTO
   * @param empresa the empresa
   */
  public static void mapper(EmpresaDTO editarEmpresaDTO, Empresa empresa) {
    empresa.setAtivo(StatusEnum.valueOf(editarEmpresaDTO.getStatus()));
    empresa.setCnpj(editarEmpresaDTO.getCnpj());
    empresa.setCodigoCnes(editarEmpresaDTO.getCnes());
    empresa.setSite(editarEmpresaDTO.getSite());
    empresa.setNomeEmpresarial(editarEmpresaDTO.getNomeEmpresarial());
    empresa.setNomeFantasia(editarEmpresaDTO.getNomeFantasia());

  }

  /**
   * Mapper.
   *
   * @param empresa the empresa
   * @return the visualizar empresa DTO
   */
  public static VisualizarEmpresaDTO mapper(Empresa empresa) {
    VisualizarEmpresaDTO empresaDTO = new VisualizarEmpresaDTO();
    empresaDTO.setNomeEmpresarial(empresa.getNomeEmpresarial());
    empresaDTO.setNomeFantasia(empresa.getNomeFantasia());
    empresaDTO.setCnpj(empresa.getCnpj());
    empresaDTO.setCnes(empresa.getCodigoCnes());
    empresaDTO.setSite(empresa.getSite());
    empresaDTO.setId(empresa.getId());
    empresaDTO.setStatus(empresa.getAtivo());

    return empresaDTO;
  }


  /**
   * Mapper.
   *
   * @param id the id
   * @return the empresa
   */
  public static Empresa mapper(Long id) {
    Empresa empresa = new Empresa();
    empresa.setId(id);
    return empresa;
  }

  public static String[] getAtributosValidos() {
    String[] atributosValidos = new String[6];
    atributosValidos[0] = "nomeEmpresarial";
    atributosValidos[1] = "nomeFantasia";
    atributosValidos[2] = "cnpj";
    atributosValidos[3] = "codigoCnes";
    atributosValidos[4] = "site";
    atributosValidos[5] = "ativo";

    return atributosValidos;
  }

  /**
   * Mapper.
   *
   * @param Historico empresas the empresas
   * @return the list
   */
  public static List<HistoricoEmpresaDTO> mapperHistorico(List<EmpresaAud> empresasAud) {
    List<HistoricoEmpresaDTO> empresasAudDto = new ArrayList<>();

    for (EmpresaAud empresaAud : empresasAud) {

      HistoricoEmpresaDTO historico = new HistoricoEmpresaDTO();
      historico.setNomeEmpresarial(empresaAud.getNomeEmpresarial());
      historico.setNomeFantasia(empresaAud.getNomeFantasia());
      historico.setCnpj(empresaAud.getCnpj());
      historico.setCnes(empresaAud.getCodigoCnes());
      historico.setSite(empresaAud.getSite());
      historico.setStatus(empresaAud.getAtivo().getName());
      historico.setRevision(empresaAud.getRevisao());
      historico.setRevisao(empresaAud.getRevisao().getId().toString());
      historico.setDataEvento(
          TimeUtil.convertDataToStr(empresaAud.getRevisao().getDtRevisao(), "dd/MM/yyyy HH:mm"));

      empresasAudDto.add(historico);
    }
    return empresasAudDto;
  }

}
