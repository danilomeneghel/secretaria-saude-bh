package br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.cadastro;

import javax.validation.constraints.NotNull;
import br.gov.pbh.prodabel.hubsmsa.dto.BaseDTO;

public class CadastrarContatoEmpresaServicoDTO extends BaseDTO {

  private static final long serialVersionUID = -6713100168903805623L;

  @NotNull(message = "Empresa é obrigatório")
	private Long idEmpresa;

    @NotNull(message = "Contato da Empresa é obrigatório")
    private Long idContatoEmpresa;

    @NotNull(message = "Serviço é obrigatório")
    private Long idServico;

    private Boolean notificacaoSucesso;

    private Boolean notificacaoFalha;


    public Long getIdEmpresa() {
      return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
      this.idEmpresa = idEmpresa;
    }

    public Long getIdContatoEmpresa() {
      return idContatoEmpresa;
    }

    public void setIdContatoEmpresa(Long idContatoEmpresa) {
      this.idContatoEmpresa = idContatoEmpresa;
    }

    public Long getIdServico() {
      return idServico;
    }

    public void setIdServico(Long idServico) {
      this.idServico = idServico;
    }

    public Boolean getNotificacaoSucesso() {
      return notificacaoSucesso;
    }

    public void setNotificacaoSucesso(Boolean notificacaoSucesso) {
      this.notificacaoSucesso = notificacaoSucesso;
    }

    public Boolean getNotificacaoFalha() {
      return notificacaoFalha;
    }

    public void setNotificacaoFalha(Boolean notificacaoFalha) {
      this.notificacaoFalha = notificacaoFalha;
    }


}
