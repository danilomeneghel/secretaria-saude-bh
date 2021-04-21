package br.gov.pbh.prodabel.hubsmsa.dto.servico;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.VisualizarSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;

// TODO: Auto-generated Javadoc
@JsonInclude(Include.NON_NULL)
public class VisualizarServicoDTO extends BaseDTO {


  private static final long serialVersionUID = 4656040055151867137L;

  private Long id;

    private VisualizarSistemaDTO sistemaPrimario;

    private VisualizarSistemaDTO sistemaSecundario;

	private String nome;

	private String descricao;

	private StatusEnum status;


    /**
     * Instantiates a new visualizar servico DTO.
     */
    public VisualizarServicoDTO() {

    }

    /**
     * Instantiates a new visualizar servico DTO.
     *
     * @param id the id
     * @param sistemaPrimario the sistema primario
     * @param sistemaSecundario the sistema secundario
     * @param nome the nome
     * @param descricao the descricao
     * @param status the status
     */
    public VisualizarServicoDTO(Long id, VisualizarSistemaDTO sistemaPrimario,
        VisualizarSistemaDTO sistemaSecundario, String nome, String descricao, StatusEnum status) {
      super();
      this.id = id;
      this.sistemaPrimario = sistemaPrimario;
      this.sistemaSecundario = sistemaSecundario;
      this.nome = nome;
      this.descricao = descricao;
      this.status = status;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public VisualizarSistemaDTO getSistemaPrimario() {
      return sistemaPrimario;
    }


    public void setSistemaPrimario(VisualizarSistemaDTO sistemaPrimario) {
      this.sistemaPrimario = sistemaPrimario;
    }


    public VisualizarSistemaDTO getSistemaSecundario() {
      return sistemaSecundario;
    }


    public void setSistemaSecundario(VisualizarSistemaDTO sistemaSecundario) {
      this.sistemaSecundario = sistemaSecundario;
    }


    public String getNome() {
      return nome;
    }


    public void setNome(String nome) {
      this.nome = nome;
    }


    public String getDescricao() {
      return descricao;
    }


    public void setDescricao(String descricao) {
      this.descricao = descricao;
    }


    public StatusEnum getStatus() {
      return status;
    }


    public void setStatus(StatusEnum status) {
      this.status = status;
    }



}
