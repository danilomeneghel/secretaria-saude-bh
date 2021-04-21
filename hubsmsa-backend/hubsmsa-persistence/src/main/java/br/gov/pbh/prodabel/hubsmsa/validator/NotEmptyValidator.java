package br.gov.pbh.prodabel.hubsmsa.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.gov.pbh.prodabel.hubsmsa.annotations.NotEmpty;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {

	@Override
	public void initialize(NotEmpty constraintAnnotation) {
	
	}

	@Override
	public boolean isValid(String valor, ConstraintValidatorContext context) {
		return !valor.isEmpty();
	}
}
