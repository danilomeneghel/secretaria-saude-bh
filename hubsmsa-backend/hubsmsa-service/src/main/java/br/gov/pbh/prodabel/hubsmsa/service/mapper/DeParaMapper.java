package br.gov.pbh.prodabel.hubsmsa.service.mapper;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.CampoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.VisualizarDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.VisualizarSistemaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarCampoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarSistemaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.PesquisaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.TipoDePara;
import br.gov.pbh.prodabel.hubsmsa.util.BeanUtil;
import br.gov.pbh.prodabel.hubsmsa.util.DeParaUtil;

// TODO: Auto-generated Javadoc
public class DeParaMapper {

  /**
   * Instantiates a new de para mapper.
   */
  public DeParaMapper() {
  }

  /**
   * Mapper pesquisa.
   *
   * @param consultarDePara the consultar de para
   * @return the list
   */
  public static List<PesquisaDeParaDTO> mapperPesquisa(final List<DePara> consultarDePara) {

    return consultarDePara.stream().map(deParaItem -> {
      
      String nomeEmpresaPrimario = BeanUtil.getString(deParaItem, "sistemaPrimario.empresa.nomeFantasia", StringUtils.EMPTY);
      String nomeSistemaPrimario = BeanUtil.getString(deParaItem, "sistemaPrimario.nome", StringUtils.EMPTY);
      String sistemaPrimario = new StringBuilder(nomeEmpresaPrimario).append(" - ").append(nomeSistemaPrimario).toString();

      String nomeEmpresaSecundario = BeanUtil.getString(deParaItem, "sistemaSecundario.empresa.nomeFantasia", StringUtils.EMPTY);
      String nomeSistemaSecundario = BeanUtil.getString(deParaItem, "sistemaSecundario.nome", StringUtils.EMPTY);
      String sistemaSecundario = new StringBuilder(nomeEmpresaSecundario).append(" - ").append(nomeSistemaSecundario).toString();
            
      String status = new StringBuilder(deParaItem.getStatus().getName()).toString();

      PesquisaDeParaDTO deParaDTO = new PesquisaDeParaDTO();
      
      deParaDTO.setId(deParaItem.getId());
      deParaDTO.setNomeDePara(deParaItem.getNome());
      deParaDTO.setNomeTipoDePara(deParaItem.getTipoDePara().getNome());
      deParaDTO.setSistemaPrimario(sistemaPrimario);
      deParaDTO.setSistemaSecundario(sistemaSecundario);
      deParaDTO.setStatus(status);

      return deParaDTO;
    }).collect(Collectors.toList());
    

  }

  /**
   * Mapper cadastrar.
   *
   * @param cadastrarDeParaDTO the cadastrar de para DTO
   * @return the de para
   */
  public static DePara mapperCadastrar(final CadastrarDeParaDTO cadastrarDeParaDTO) {

    TipoDePara tipoDePara = new TipoDePara();
    tipoDePara.setId(cadastrarDeParaDTO.getIdTipoDePara());

    final DePara dePara = new DePara();
    dePara.setTipoDePara(tipoDePara);
    dePara.setNome(cadastrarDeParaDTO.getNomeDePara());
    dePara.setStatus(StatusEnum.valueOf(cadastrarDeParaDTO.getStatus()));
    dePara.setFormaCadastro(FormaCadastroEnum.C);
    dePara.setDataAtualizacao(LocalDate.now());

    Set<DeParaPrimario> deParaPrimarioItens =
        cadastrarDeParaDTO.getSistemaPrimario().getCamposDePara().stream().map(deParaPrimario -> {
          final DeParaPrimario deParaPrimarioInstance = new DeParaPrimario();
          deParaPrimarioInstance.setDePara(dePara);
          deParaPrimarioInstance.setId(deParaPrimario.getId());
          deParaPrimarioInstance.setCodigo(deParaPrimario.getCodigo());
          deParaPrimarioInstance.setDescricao(deParaPrimario.getDescricao());
          return deParaPrimarioInstance;
        }).collect(Collectors.toSet());

    Set<DeParaSecundario> deParaSecundarioItens =
        cadastrarDeParaDTO.getSistemaSecundario().getCamposDePara().stream()
            .map(deParaSecundario -> {
              final DeParaSecundario deParaSecundarioInstance = new DeParaSecundario();
              deParaSecundarioInstance.setDePara(dePara);
              deParaSecundarioInstance.setId(deParaSecundario.getId());
              deParaSecundarioInstance.setCodigo(deParaSecundario.getCodigo());
              deParaSecundarioInstance.setDescricao(deParaSecundario.getDescricao());
              return deParaSecundarioInstance;
        }).collect(Collectors.toSet());

    dePara.setDeParaPrimario(deParaPrimarioItens);
    dePara.setDeParaSecundario(deParaSecundarioItens);

    return dePara;
  }

  /**
   * Mapper editar.
   *
   * @param dtoEdicao the dto edicao
   * @param dePara the de para
   */
  public static void mapperEditar(final CadastrarDeParaDTO dtoEdicao, final DePara dePara) {
    
    DeParaUtil.atualizarCamposPrimarios(dtoEdicao, dePara);
    DeParaUtil.atualizarCamposSecundarios(dtoEdicao, dePara);
    DeParaUtil.atualizarSistemaPrimario(dtoEdicao, dePara);
    DeParaUtil.atualizarSistemaSecundario(dtoEdicao, dePara);
    
    dePara.setTipoDePara(TipoDeParaMapper.mapper(dtoEdicao.getIdTipoDePara()));
    dePara.setNome(dtoEdicao.getNomeDePara());
    dePara.setStatus(StatusEnum.valueOf(dtoEdicao.getStatus()));
    dePara.setDataAtualizacao(LocalDate.now());
    
    if (FormaCadastroEnum.C.equals(dePara.getFormaCadastro())) {
      dePara.setFormaCadastro(FormaCadastroEnum.C);
    }
    
    if (FormaCadastroEnum.I.equals(dePara.getFormaCadastro())) {
      dePara.setFormaCadastro(FormaCadastroEnum.A);
    }

  }

  /**
   * Mapper visualizar.
   *
   * @param dePara the de para
   * @return the visualizar de para DTO
   */
  public static VisualizarDeParaDTO mapperVisualizar(final DePara dePara) {
    
    String nomeSistemaPrimario = BeanUtil.getString(dePara, "sistemaPrimario.nome", StringUtils.EMPTY);
    String nomeEmpresaPrimario = BeanUtil.getString(dePara, "sistemaPrimario.empresa.nomeFantasia", StringUtils.EMPTY);
    
    VisualizarSistemaDeParaDTO sistemaPrimario = new VisualizarSistemaDeParaDTO();
    sistemaPrimario.setNomeSistema(nomeSistemaPrimario);
    sistemaPrimario.setNomeEmpresa(nomeEmpresaPrimario);
    
    String nomeSistemaSecundario = BeanUtil.getString(dePara, "sistemaSecundario.nome", StringUtils.EMPTY);
    String nomeEmpresaSecundario = BeanUtil.getString(dePara, "sistemaSecundario.empresa.nomeFantasia", StringUtils.EMPTY);
    
    VisualizarSistemaDeParaDTO sistemaSecundario = new VisualizarSistemaDeParaDTO();
    sistemaSecundario.setNomeSistema(nomeSistemaSecundario);
    sistemaSecundario.setNomeEmpresa(nomeEmpresaSecundario);
    
    List<CampoDeParaDTO> camposDeParaPrimario = dePara.getDeParaPrimario()
        .stream()
        .map(item -> {
          CampoDeParaDTO dto = new CampoDeParaDTO();
          dto.setCodigo(item.getCodigo());
          dto.setDescricao(item.getDescricao());
          return dto;
        }).sorted(Comparator.comparing(CampoDeParaDTO::getCodigo))
        .collect(Collectors.toList());
    
    List<CampoDeParaDTO> camposDeParaSecundario = dePara.getDeParaSecundario()
        .stream()
        .map(item -> {
          CampoDeParaDTO dto = new CampoDeParaDTO();
          dto.setCodigo(item.getCodigo());
          dto.setDescricao(item.getDescricao());
          return dto;
        }).sorted(Comparator.comparing(CampoDeParaDTO::getCodigo))
        .collect(Collectors.toList());
    
    sistemaPrimario.setCamposDePara(camposDeParaPrimario);
    sistemaSecundario.setCamposDePara(camposDeParaSecundario);
    
    final VisualizarDeParaDTO visualizarDeParaDTO = new VisualizarDeParaDTO();
    visualizarDeParaDTO.setNomeTipoDePara(dePara.getTipoDePara().getNome());
    visualizarDeParaDTO.setNomeDePara(dePara.getNome());
    visualizarDeParaDTO.setStatus(dePara.getStatus().getName());
    visualizarDeParaDTO.setSistemaPrimario(sistemaPrimario);
    visualizarDeParaDTO.setSistemaSecundario(sistemaSecundario);
    
    return visualizarDeParaDTO;
    
  }

  /**
   * Mapper.
   *
   * @param id the id
   * @return the de para
   */
  public static DePara mapper(final Long id) {
    final DePara dePara = new DePara();
    dePara.setId(id);
    return dePara;
  }

  /**
   * Mapper selecionar.
   *
   * @param dePara the de para
   * @return the cadastrar de para DTO
   */
  public static CadastrarDeParaDTO mapperSelecionar(DePara dePara) {
    
    List<CadastrarCampoDeParaDTO> camposDeParaPrimario = dePara.getDeParaPrimario()
    .stream().map(item -> {
      CadastrarCampoDeParaDTO campoDePara = new CadastrarCampoDeParaDTO();
          campoDePara.setId(item.getId());
          campoDePara.setCodigo(item.getCodigo());
      campoDePara.setDescricao(item.getDescricao());
      return campoDePara;
    }).collect(Collectors.toList());
    
    CadastrarSistemaDeParaDTO sistemaPrimario = new CadastrarSistemaDeParaDTO();
    sistemaPrimario.setCamposDePara(camposDeParaPrimario);
    sistemaPrimario.setIdEmpresa(dePara.getSistemaPrimario().getEmpresa().getId());
    sistemaPrimario.setIdSistema(dePara.getSistemaPrimario().getId());
    
    List<CadastrarCampoDeParaDTO> camposDeParaSecundario = dePara.getDeParaSecundario()
        .stream().map(item -> {
          CadastrarCampoDeParaDTO campoDePara = new CadastrarCampoDeParaDTO();
          campoDePara.setId(item.getId());
          campoDePara.setCodigo(item.getCodigo());
          campoDePara.setDescricao(item.getDescricao());
          return campoDePara;
        }).collect(Collectors.toList());

    CadastrarSistemaDeParaDTO sistemaSecundario = new CadastrarSistemaDeParaDTO();
    sistemaSecundario.setCamposDePara(camposDeParaSecundario);
    sistemaSecundario.setIdEmpresa(dePara.getSistemaSecundario().getEmpresa().getId());
    sistemaSecundario.setIdSistema(dePara.getSistemaSecundario().getId());
    
    CadastrarDeParaDTO selecaoDeParaDTO = new CadastrarDeParaDTO();
    selecaoDeParaDTO.setIdTipoDePara(dePara.getTipoDePara().getId());
    selecaoDeParaDTO.setNomeDePara(dePara.getNome());
    selecaoDeParaDTO.setStatus(dePara.getStatus().toString());
    selecaoDeParaDTO.setSistemaPrimario(sistemaPrimario);
    selecaoDeParaDTO.setSistemaSecundario(sistemaSecundario);
    
    return selecaoDeParaDTO;
  }
  
  /**
   * Mapper pesquisa relatorio.
   *
   * @param consultarDePara the consultar de para
   * @return the list
   */
  public static List<PesquisaDeParaDTO> mapperPesquisaRelatorio(final List<DePara> consultarDePara) {

    return consultarDePara.stream().map(deParaItem -> {
	      
	      String nomeEmpresaPrimario = BeanUtil.getString(deParaItem, "sistemaPrimario.empresa.nomeFantasia", StringUtils.EMPTY);
	      String nomeSistemaPrimario = BeanUtil.getString(deParaItem, "sistemaPrimario.nome", StringUtils.EMPTY);
	      String sistemaPrimario = new StringBuilder(nomeEmpresaPrimario).append(" - ").append(nomeSistemaPrimario).toString();

	      String nomeEmpresaSecundario = BeanUtil.getString(deParaItem, "sistemaSecundario.empresa.nomeFantasia", StringUtils.EMPTY);
	      String nomeSistemaSecundario = BeanUtil.getString(deParaItem, "sistemaSecundario.nome", StringUtils.EMPTY);
	      String sistemaSecundario = new StringBuilder(nomeEmpresaSecundario).append(" - ").append(nomeSistemaSecundario).toString();
	            
	      String status = new StringBuilder(deParaItem.getStatus().getName()).toString();

	      PesquisaDeParaDTO deParaDTO = new PesquisaDeParaDTO();
	      
	      deParaDTO.setId(deParaItem.getId());
	      deParaDTO.setNomeDePara(deParaItem.getNome());
	      deParaDTO.setNomeTipoDePara(deParaItem.getTipoDePara().getNome());
	      deParaDTO.setSistemaPrimario(sistemaPrimario);
	      deParaDTO.setSistemaSecundario(sistemaSecundario);
	      deParaDTO.setStatus(status);

	      return deParaDTO;
	    }).collect(Collectors.toList());

	  }
  
      public static String[] getAtributosValidos() {
        String[] atributosValidos = new String[7];
        atributosValidos[0] = DePara_.NOME;
        atributosValidos[1] = DePara_.STATUS;
        atributosValidos[2] = "tipoDeParaNome";
        atributosValidos[3] = "sistemaPrimarioNome";
        atributosValidos[4] = "sistemaSecundarioNome";
        atributosValidos[5] = "empresaPrimarioNome";
        atributosValidos[6] = "empresaSecundarioNome";

        return atributosValidos;
      }

}
