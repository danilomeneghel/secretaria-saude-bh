package br.gov.pbh.prodabel.hubsmsa.validator;

import java.lang.reflect.Field;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.TipoDeParaValido;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaTipoDeParaDTO;

public class TipoDeParaValidator implements ConstraintValidator<TipoDeParaValido, FiltroPesquisaTipoDeParaDTO>   {
	
	@Override
  public void initialize(TipoDeParaValido ca) {
		
	}
	
	@Override
	public boolean isValid(FiltroPesquisaTipoDeParaDTO filtro, ConstraintValidatorContext context) {
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
		return name.equals("nome") || name.equals("status");
	}
}
