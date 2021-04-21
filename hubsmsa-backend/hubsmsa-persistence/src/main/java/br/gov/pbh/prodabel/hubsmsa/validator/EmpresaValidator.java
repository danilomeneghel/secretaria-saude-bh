package br.gov.pbh.prodabel.hubsmsa.validator;

import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.EmpresaValido;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaEmpresaDTO;

public class EmpresaValidator implements ConstraintValidator<EmpresaValido, FiltroPesquisaEmpresaDTO>  {	
	
	@Override
  public void initialize(EmpresaValido ca) {
		
	}

	@Override
	public boolean isValid(FiltroPesquisaEmpresaDTO filtro, ConstraintValidatorContext cvc) {
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
		return name.equals("codigo") || name.equals("nomeEmpresa") || name.equals("status");
	}
}
