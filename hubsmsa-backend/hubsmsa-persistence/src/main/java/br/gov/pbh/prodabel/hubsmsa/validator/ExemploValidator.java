package br.gov.pbh.prodabel.hubsmsa.validator;

import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.ExemploValido;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.FiltroPesquisaExemploDTO;

public class ExemploValidator implements ConstraintValidator<ExemploValido, FiltroPesquisaExemploDTO>  {	
	
	@Override
  public void initialize(ExemploValido ca) {
		
	}

	@Override
	public boolean isValid(FiltroPesquisaExemploDTO filtro, ConstraintValidatorContext cvc) {
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
		return name.equals("codigo") || name.equals("nomeExemplo") || name.equals("status");
	}
}
