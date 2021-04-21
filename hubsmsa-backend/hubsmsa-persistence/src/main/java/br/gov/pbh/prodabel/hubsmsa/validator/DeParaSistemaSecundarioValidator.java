package br.gov.pbh.prodabel.hubsmsa.validator;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.collections.CollectionUtils;
import br.gov.pbh.prodabel.hubsmsa.annotations.DeParaSistemaSecundarioValido;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarSistemaDeParaDTO;

public class DeParaSistemaSecundarioValidator
    implements ConstraintValidator<DeParaSistemaSecundarioValido, CadastrarSistemaDeParaDTO> {

  @Override
  public boolean isValid(CadastrarSistemaDeParaDTO value, ConstraintValidatorContext context) {

    context.disableDefaultConstraintViolation();
    
    boolean isValid = Objects.nonNull(value) && Objects.nonNull(value.getIdSistema())
        && Objects.nonNull(value.getIdEmpresa());

    if (!isValid) {

      if (value == null) {
        context
            .buildConstraintViolationWithTemplate("Os dados do Sistema Secundário são obrigatórios.")
            .addConstraintViolation();
        return false;
      }

      if (value.getIdSistema() == null) {
        context
            .buildConstraintViolationWithTemplate(
                "O campo Sistema Secundário do De/Para é obrigatório.")
            .addPropertyNode("idSistema").addConstraintViolation();
      }

      if (value.getIdEmpresa() == null) {
        context
            .buildConstraintViolationWithTemplate(
                "O campo Empresa Secundária do De/Para é obrigatório")
            .addPropertyNode("idEmpresa").addConstraintViolation();
      }
      
      if (CollectionUtils.isEmpty(value.getCamposDePara())) {
        context
            .buildConstraintViolationWithTemplate(
                "O campo Codigo e Descrição do De/Para são obrigatório(a). (Sistema Secundário)")
            .addPropertyNode("camposDePara").addConstraintViolation();
      }

      return false;

    }
    
    return true;
    
  }
}
