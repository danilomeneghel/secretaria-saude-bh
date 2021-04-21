package br.gov.pbh.prodabel.hubsmsa.handler;

import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.validator.CPFValidator;

// TODO: Auto-generated Javadoc
public class ValidacoesHandler {

  /**
   * Validar empresa ativa.
   *
   * @param empresa the empresa
   */
  public static void validarEmpresaAtiva(Empresa empresa) {
    if (empresa.getAtivo() == StatusEnum.I) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME033, "Empresa"));
    }
  }


  /**
   * Validar sistema ativo.
   *
   * @param sistema the sistema
   */
  public static void validarSistemaAtivo(Sistema sistema) {
    if (sistema.getAtivo() == StatusEnum.I) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME033, "Sistema"));
    }
  }

  /**
   * Validar servico ativo.
   *
   * @param servico the servico
   */
  public static void validarServicoAtivo(Servico servico) {
    if (servico.getStatus() == StatusEnum.I) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME033, "Servico"));
    }
  }

  /**
   * Validar CPF.
   *
   * @param cpf the cpf
   */
  public static void validarCPF(String cpf) {
    if (!CPFValidator.isValidCPF(cpf)) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME034, "CPF"));
    }

  }

}
