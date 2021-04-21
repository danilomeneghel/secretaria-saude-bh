package br.gov.pbh.prodabel.hubsmsa.validator;

import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.DeParaValido;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaDeParaDTO;

public class DeParaValidator implements ConstraintValidator<DeParaValido, FiltroPesquisaDeParaDTO> {
	
	@Override
  public void initialize(DeParaValido ca) {
		
	}
	
	@Override
	public boolean isValid(FiltroPesquisaDeParaDTO filtro, ConstraintValidatorContext cvc) {
		boolean isValid = Boolean.FALSE;
		if(filtro != null) {
			for (Field iterableField : filtro.getClass().getDeclaredFields()) {
				iterableField.setAccessible(true);
				if(validarCampoNome(iterableField.getName())){
					try {
						isValid = iterableField.get(filtro) != null;
						if(isValid) {
							break;							
						}
					} catch (Exception e) {
						//continua falso
					}
				}
			}
		}
		return isValid;
	}
	
	private boolean validarCampoNome(String name) {
		return name.equals("idTipoDePara") || name.equals("nome") || name.equals("status")
				 || name.equals("idEmpresaPrimaria")  || name.equals("idSistemaPrimario")  || name.equals("codigoPrimario")
				 || name.equals("descricaoPrimaria")  || name.equals("idEmpresaSecundaria")  || name.equals("idSistemaSecundario") 
				 || name.equals("codigoSecundario") || name.equals("descricaoSecundaria");
	}

}
