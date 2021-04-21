package br.gov.pbh.prodabel.hubsmsa.dto.tipodepara;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.FormaCadastroEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.util.LocalDateSerializer;

@JsonInclude(Include.NON_NULL)
public class VisualizarTipoDeParaDTO extends BaseDTO {

  private static final long serialVersionUID = -1084241721917488482L;

  private Long id;

  private String nome;

  private String descricao;

  private FormaCadastroEnum formaCadastro;

  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate dataAtualizacao;

  private StatusEnum status;

  public VisualizarTipoDeParaDTO() {

  }

  public VisualizarTipoDeParaDTO(Long id, String nome, String descricao,
      FormaCadastroEnum formaCadastro, LocalDate dataAtualizacao, StatusEnum status) {
    super();
    this.id = id;
    this.nome = nome;
    this.descricao = descricao;
    this.formaCadastro = formaCadastro;
    this.dataAtualizacao = dataAtualizacao;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public FormaCadastroEnum getFormaCadastro() {
    return formaCadastro;
  }

  public void setFormaCadastro(FormaCadastroEnum formaCadastro) {
    this.formaCadastro = formaCadastro;
  }

  public LocalDate getDataAtualizacao() {
    return dataAtualizacao;
  }

  public void setDataAtualizacao(LocalDate dataAtualizacao) {
    this.dataAtualizacao = dataAtualizacao;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

}
