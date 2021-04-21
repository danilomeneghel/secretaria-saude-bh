package br.gov.pbh.prodabel.hubsmsa.validator;

import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.SistemaValido;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaSistemaDTO;

public class SistemaValidator implements ConstraintValidator<SistemaValido, FiltroPesquisaSistemaDTO> {

	@Override
	public boolean isValid(FiltroPesquisaSistemaDTO filtro, ConstraintValidatorContext cvc) {
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
		return name.equals("idEmpresa") || name.equals("nome") || name.equals("status");
	}

}
