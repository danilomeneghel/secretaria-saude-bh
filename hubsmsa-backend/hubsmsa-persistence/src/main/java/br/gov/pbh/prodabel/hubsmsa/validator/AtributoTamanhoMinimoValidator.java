package br.gov.pbh.prodabel.hubsmsa.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import br.gov.pbh.prodabel.hubsmsa.annotations.AtributoTamanhoMinimoValido;


public class AtributoTamanhoMinimoValidator implements ConstraintValidator<AtributoTamanhoMinimoValido, String>  {
	
	private long tamanhoMinimo;
	
	@Override
	public void initialize(AtributoTamanhoMinimoValido ca) {
		this.tamanhoMinimo = ca.tamanhoMinimo();
	}

	@Override
	public boolean isValid(String nome, ConstraintValidatorContext cvc) {

		boolean isValid = Boolean.FALSE;
		if( (StringUtils.isBlank(nome)) || (StringUtils.isNotBlank(nome) && nome.length() >= tamanhoMinimo)) {
			isValid = Boolean.TRUE;
		}
		
		return isValid;
	}
}
